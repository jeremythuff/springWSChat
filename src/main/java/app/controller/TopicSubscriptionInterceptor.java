package app.controller;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptorAdapter;

import app.model.CurrentUserRepo;
import app.model.User;

@Configurable
public class TopicSubscriptionInterceptor extends ChannelInterceptorAdapter {
	
    private CurrentUserRepo currentUserRepo;
	
	public TopicSubscriptionInterceptor(CurrentUserRepo currentUserRepo) {
		this.currentUserRepo = currentUserRepo;
	}

	@Override
	public Message<?> preSend(Message<?> message, MessageChannel channel) {
    	
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(message);
        String  name = headerAccessor.getNativeHeader("name").get(0);
        String  sessionId = headerAccessor.getSessionId();


        if (StompCommand.SUBSCRIBE.equals(headerAccessor.getCommand())) {
            if(!validateSubscription(name, sessionId)) {
                System.out.println("STOP");
                throw new IllegalArgumentException("Name in use");
            }
        }
        
		return message;    
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
