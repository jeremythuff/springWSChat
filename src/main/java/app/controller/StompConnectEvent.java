package app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.web.socket.messaging.SessionConnectEvent;

import app.model.User;
import app.model.CurrentUserRepo;

public class StompConnectEvent implements ApplicationListener<SessionConnectEvent> {
	
	@Autowired
    private CurrentUserRepo currentUserRepo;

     public void onApplicationEvent(SessionConnectEvent event) {
    	
        StompHeaderAccessor sha = StompHeaderAccessor.wrap(event.getMessage());        
        String  name = sha.getNativeHeader("name").get(0);
        String sessionId = sha.getSessionId();
        
        /*if(currentUserRepo.findAll() == null) {
        	currentUserRepo.save(new User(sessionId, name));
    		System.out.println("Connect event [sessionId: " + sessionId +"; name: "+ name + " ]");
        } 
        
        if(currentUserRepo.getUserByName(name) == null) {
        	currentUserRepo.save(new User(sessionId, name));
    		System.out.println("Connect event [sessionId: " + sessionId +"; user: "+ name + " ]");
    	}*/   
    }
    
   
}
