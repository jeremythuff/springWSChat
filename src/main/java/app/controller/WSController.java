package app.controller;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;

import app.model.WSIn;
import app.model.WSOut;


@Controller
public class WSController {

    @MessageMapping("/hello")
    @SendTo("/WSRes/hello")
    public WSOut greeting(@RequestParam(value="message", defaultValue="World") WSIn message) throws Exception {
        return new WSOut("Hello, " + message.getName() + "!");
    }
}

