package com.example.PileOfStones;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class GameController {

    @MessageMapping("/message")
    @SendTo("/topic/messages")
    public GameRound sendMessage(GameRound gameRound) throws Exception {
        return gameRound;
    }

}

