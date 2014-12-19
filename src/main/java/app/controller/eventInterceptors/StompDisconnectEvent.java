package app.controller.eventInterceptors;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import app.model.User;
import app.model.CurrentUserRepo;

public class StompDisconnectEvent implements ApplicationListener<SessionDisconnectEvent> {
	
	@Autowired
    private CurrentUserRepo currentUserRepo;

	@Override
	public void onApplicationEvent(SessionDisconnectEvent event) {		
		
        String  sessionId = event.getSessionId();

        System.out.println("Disconnected event detected!");
        
        User oldUser = currentUserRepo.getUserBySessionId(sessionId);
        
        if(oldUser != null) {
        	System.out.println("Disconnected the user " + oldUser.getName() + ".");
        	
        	List<String> userNames = new ArrayList<String>();
        	
        	for(User user : currentUserRepo.findAll()) {        		
        		if(!user.getName().equals(oldUser.getName()))
        			userNames.add(user.getName());
        	}
        	
        	
        	SimpMessagingTemplate messageTemplate = new SimpMessagingTemplate(oldUser.getChannel());
        	messageTemplate.convertAndSend("/ws/user/update", userNames);
        	
        	oldUser.setChannel(null);
        	currentUserRepo.delete(oldUser);
             	  	
        }
		
	}
  
}
