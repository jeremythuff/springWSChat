package app.controller;

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
    	return new WSOut("CHAT",message.getMessage(), message.getName());
    }
    
    @MessageMapping("/update")
    @SendTo("/WSRes/chat")
    public WSOut update(String user) throws Exception {
    	return new WSOut("UPDATE", user, "");
    }
}

