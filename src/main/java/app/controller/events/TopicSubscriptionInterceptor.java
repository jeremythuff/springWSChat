package app.controller.events;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptorAdapter;
import org.springframework.web.bind.annotation.RequestParam;

import app.model.CurrentUserRepo;
import app.model.User;
import app.model.WSIn;
import app.model.WSOut;

public class TopicSubscriptionInterceptor extends ChannelInterceptorAdapter {
	
    private CurrentUserRepo currentUserRepo;
	
	public TopicSubscriptionInterceptor(CurrentUserRepo currentUserRepo) {
		this.currentUserRepo = currentUserRepo;
	}

	@Override
	public Message<?> preSend(Message<?> message, MessageChannel channel) {
    	
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(message);
                
        if (StompCommand.SUBSCRIBE.equals(headerAccessor.getCommand())) {
	        String  name = headerAccessor.getNativeHeader("name").get(0);
	        String  sessionId = headerAccessor.getSessionId();
        
            if(!validateSubscription(name, sessionId)) {
                throw new IllegalArgumentException("Name in use");
            } else {
            	try {
					addUser(name);
				} catch (Exception e) {
					e.printStackTrace();
				}
            }
            
        }
          
		return message;    
    }
	
     @SendTo("/WSRes/chat")
     public WSOut addUser(String name) throws Exception {
     	return new WSOut("add", name);
     }
	
	private boolean validateSubscription(String name, String sessionId)
	{
		Boolean validated = false;
		
		 if(currentUserRepo.findAll() == null) {
        	currentUserRepo.save(new User(sessionId, name));
    		System.out.println("Connect event [sessionId: " + sessionId +"; name: "+ name + " ]");
    		validated = true;
		 } 
        
        if(currentUserRepo.getUserByName(name) == null) {
        	currentUserRepo.save(new User(sessionId, name));
    		System.out.println("Connect event [sessionId: " + sessionId +"; user: "+ name + " ]");
    		validated = true;
    	}
		
		return validated;
	}
    
}
