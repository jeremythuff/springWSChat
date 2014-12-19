package app.controller;

import java.util.List;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import app.model.WSIn;
import app.model.WSOut;

@Controller
public class WSController {

    @MessageMapping("/chat")
    @SendTo("/WSRes/chat")
    public WSOut message(WSIn message) throws Exception {
    	return new WSOut("CHAT", message.getMessage(), message.getName());
    }
    
    @MessageMapping("/user/update")
    @SendTo("/WSRes/chat")
    public WSOut update(List<String> users) throws Exception {
    	System.out.println("Send update");
    	return new WSOut("UPDATE", users);
    }
}

