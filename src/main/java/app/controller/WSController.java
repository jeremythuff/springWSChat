package app.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;

import app.model.WSIn;
import app.model.WSOut;

@Controller
public class WSController {

    @MessageMapping("/chat")
    @SendTo("/WSRes/chat")
    public WSOut greeting(@RequestParam(value="message", defaultValue="World") WSIn message) throws Exception {
    	return new WSOut(message.getMessage(), message.getName());
    }
}

