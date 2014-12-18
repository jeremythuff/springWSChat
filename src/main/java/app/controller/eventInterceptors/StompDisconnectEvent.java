package app.controller.eventInterceptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
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
        
        User user = currentUserRepo.getUserBySessionId(sessionId);
        
        if(user != null) {
        	System.out.println("Disconnected the user " + user.getName() + ".");
        	currentUserRepo.delete(user);
        }
		
	}
  
}
