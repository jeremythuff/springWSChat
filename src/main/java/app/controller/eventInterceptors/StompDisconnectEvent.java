package app.controller.eventInterceptors;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import app.model.User;
import app.model.CurrentUserRepo;

public class StompDisconnectEvent implements ApplicationListener<SessionDisconnectEvent> {
	
	@Autowired
    private CurrentUserRepo currentUserRepo;
	
	@Autowired
	private SimpMessagingTemplate messagingTemplate;

	

	@Override
	public void onApplicationEvent(SessionDisconnectEvent event) {		
		
        String  sessionId = event.getSessionId();

        System.out.println("Disconnected event detected!");
        
        User oldUser = currentUserRepo.getUserBySessionId(sessionId);
        
        if(oldUser != null) {
        	System.out.println("Disconnected the user " + oldUser.getName() + ".");
        	currentUserRepo.delete(oldUser);
        	
        	List<String> userNames = new ArrayList<String>();
        	
        	for(User user : currentUserRepo.findAll()) {
        		userNames.add(user.getName());
        	}
        	
        	messagingTemplate.convertAndSend("/ws/user/update", userNames);
        	
        }
		
	}
  
}
