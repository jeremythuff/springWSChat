package app.controller.eventInterceptors;


import java.util.ArrayList;
import java.util.List;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptorAdapter;

import app.model.CurrentUserRepo;
import app.model.User;

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
        
            if(!validateSubscription(name, sessionId, channel)) {
                throw new IllegalArgumentException("Name in use");
            } else {
            	System.out.println("User " + name + " added");
            	 	
            	List<String> userNames = new ArrayList<String>();
            	
            	for(User user : currentUserRepo.findAll()) {
            		userNames.add(user.getName());
            	}
            	
            	System.out.println("usersNames is "+userNames.size());
            	
            	SimpMessagingTemplate messageTemplate = new SimpMessagingTemplate(channel);
            	messageTemplate.convertAndSend("/ws/user/update", userNames);
            }
            
        }
          
		return message;    
    }
	
	private boolean validateSubscription(String name, String sessionId, MessageChannel channel)
	{
		Boolean validated = false;
		
		
		
		 if((currentUserRepo.findAll() == null) || (currentUserRepo.getUserByName(name) == null)) {
        	 User user = new User();
        	 user.setName(name);
        	 user.setSessionId(sessionId);
        	 user.setChannel(channel);
			 currentUserRepo.save(user);
    		System.out.println("Connect event [sessionId: " + sessionId +"; name: "+ name + " ]");
    		validated = true;
		 }
		
		return validated;
	}
    
}
