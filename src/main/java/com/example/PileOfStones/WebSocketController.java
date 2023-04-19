package com.example.PileOfStones;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;


@Controller
public class WebSocketController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;


    @GetMapping("/admin")
    public String admin(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        GameRound gameRound = new GameRound();
        gameRound.setStatusMessage("Please Ask Admin to start the game first...");
        session.setAttribute("gameRoundObj", new GameRound());
        model.addAttribute("gameRound", new GameRound());
        return "admin";
    }

    @PostMapping("/startgame")
    public String startGame(@ModelAttribute GameRound gameRound,
                            Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        gameRound.setStatusMessage("Piles of Stones Game");
        if(gameRound.getCurrentPlayer().equals("player1")) {
            gameRound.setCurrentPlayer(gameRound.getPlayer1Name());
        }
        else {
            gameRound.setCurrentPlayer(gameRound.getPlayer2Name());
        }
        gameRound.setGameStatus("Started");
        gameRound.setWinnerName("");
        session.setAttribute("gameRoundObj", gameRound);
        gameRound.setTimeLeft(60);
        messagingTemplate.convertAndSend("/topic/messages", gameRound);
        model.addAttribute("gameRound", gameRound);
        return "admin";
    }

    @GetMapping("/play/{player}")
    public String player(@PathVariable String player
            , Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        GameRound gameRoundObj = (GameRound) session.getAttribute("gameRoundObj");

        if (gameRoundObj == null) {
            model.addAttribute("message", "Please Ask Admin to start the game first");
            gameRoundObj = new GameRound();
            model.addAttribute("Score", 0);
            gameRoundObj.setStatusMessage("Please Ask Admin to start the game first...");
        } else {
            model.addAttribute("Score",
                    player.equals("player1") ? gameRoundObj.getPlayer1Score() : gameRoundObj.getPlayer2Score());
            model.addAttribute("message", "Piles of Stones Game");
            gameRoundObj.setStatusMessage("Piles of Stones Game");
            if (player.equals("player1")) {
                model.addAttribute("player", gameRoundObj.getPlayer1Name());
            } else {
                model.addAttribute("player", gameRoundObj.getPlayer2Name());
            }
        }

        return "player";
    }


    @GetMapping("/getName/{player}")
    public ResponseEntity<?> getName(@PathVariable String player
            , Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        GameRound gameRoundObj = (GameRound) session.getAttribute("gameRoundObj");
        String name = "";
        if(gameRoundObj ==null){
            name = "None";
        }
        if (player.equals("player1")) {
            name = gameRoundObj.getPlayer1Name();
        } else {
            name = gameRoundObj.getPlayer2Name();
        }

        return ResponseEntity.ok(name);
    }

    @PostMapping("/pickup/{player}")
    public ResponseEntity<?> pickupStones(@ModelAttribute Pickup pickup, Model model,
                               HttpServletRequest request,
                               @PathVariable String player) {
        HttpSession session = request.getSession();
        GameRound gameRoundObj = (GameRound) session.getAttribute("gameRoundObj");
        if (player.equals("player1")) {
            player = gameRoundObj.getPlayer1Name();
        }
        else {
            player = gameRoundObj.getPlayer2Name();
        }
        if (!player.equals(gameRoundObj.getCurrentPlayer())) {
            return null;
        }

        if(gameRoundObj.getGameStatus().equals("Completed")){
          return null;
        }

        if (gameRoundObj.getCurrentPlayer().equals(gameRoundObj.getPlayer1Name())
        && pickup.getStonesPickedUp()>= gameRoundObj.getMinStonesToPickUp()
        && pickup.getStonesPickedUp() <= gameRoundObj.getMaxStonesToPickUp()) {

            if(gameRoundObj.getPickupHistory()==null){
                gameRoundObj.setPickupHistory("");

            }
            if (pickup.getSelectedPile()==1  &&
                    gameRoundObj.getPile1Size()-pickup.getStonesPickedUp()>=0) {
                gameRoundObj.setCurrentPlayer(gameRoundObj.getPlayer2Name());
                gameRoundObj.setPlayer1Score(gameRoundObj.getPlayer1Score() + pickup.getStonesPickedUp());
                gameRoundObj.setPile1Size(gameRoundObj.getPile1Size() - pickup.getStonesPickedUp());
                String history = "\n"+gameRoundObj.getCurrentPlayer() + " picked up " + pickup.getStonesPickedUp()
                        + " stones from pile " + pickup.getSelectedPile() +", and players score is " + gameRoundObj.getPlayer1Score();

                gameRoundObj.setPickupHistory(history);
            }
            else if (pickup.getSelectedPile()==2 &&
                    gameRoundObj.getPile2Size()-pickup.getStonesPickedUp()>=0) {
                gameRoundObj.setCurrentPlayer(gameRoundObj.getPlayer2Name());
                gameRoundObj.setPlayer1Score(gameRoundObj.getPlayer1Score() + pickup.getStonesPickedUp());
                gameRoundObj.setPile2Size(gameRoundObj.getPile2Size() - pickup.getStonesPickedUp());
                String history = "\n"+gameRoundObj.getCurrentPlayer() + " picked up " + pickup.getStonesPickedUp()
                        + " stones from pile " + pickup.getSelectedPile() +", and player score is " + gameRoundObj.getPlayer1Score();

                gameRoundObj.setPickupHistory(history);
            }
            else if (pickup.getSelectedPile()==3 &&
                    gameRoundObj.getPile3Size()-pickup.getStonesPickedUp()>=0) {
                gameRoundObj.setCurrentPlayer(gameRoundObj.getPlayer2Name());
                gameRoundObj.setPlayer1Score(gameRoundObj.getPlayer1Score() + pickup.getStonesPickedUp());
                gameRoundObj.setPile3Size(gameRoundObj.getPile3Size() - pickup.getStonesPickedUp());
                String history = "\n"+gameRoundObj.getCurrentPlayer() + " picked up " + pickup.getStonesPickedUp()
                        + " stones from pile " + pickup.getSelectedPile() +", and player score is " + gameRoundObj.getPlayer1Score();

                gameRoundObj.setPickupHistory(history);
            }
            gameRoundObj.setGameStatus("Playing");
        }
        else if (gameRoundObj.getCurrentPlayer().equals(gameRoundObj.getPlayer2Name())
                && pickup.getStonesPickedUp()>= gameRoundObj.getMinStonesToPickUp()
                && pickup.getStonesPickedUp() <= gameRoundObj.getMaxStonesToPickUp()) {
            if(gameRoundObj.getPickupHistory()==null){
                gameRoundObj.setPickupHistory("");

            }


            if (pickup.getSelectedPile()==1  &&
                    gameRoundObj.getPile1Size()-pickup.getStonesPickedUp()>=0) {
                gameRoundObj.setPile1Size(gameRoundObj.getPile1Size() - pickup.getStonesPickedUp());
                gameRoundObj.setCurrentPlayer(gameRoundObj.getPlayer1Name());
                gameRoundObj.setPlayer2Score(gameRoundObj.getPlayer2Score() + pickup.getStonesPickedUp());
                String history = "\n"+gameRoundObj.getCurrentPlayer() + " picked up " + pickup.getStonesPickedUp()
                        + " stones from pile " + pickup.getSelectedPile() + ", and player Score is " + gameRoundObj.getPlayer2Score();
                gameRoundObj.setPickupHistory(history);
            }
            else if (pickup.getSelectedPile()==2 &&
                    gameRoundObj.getPile2Size()-pickup.getStonesPickedUp()>=0) {
                gameRoundObj.setPile2Size(gameRoundObj.getPile2Size() - pickup.getStonesPickedUp());
                gameRoundObj.setCurrentPlayer(gameRoundObj.getPlayer1Name());
                gameRoundObj.setPlayer2Score(gameRoundObj.getPlayer2Score() + pickup.getStonesPickedUp());
                String history = "\n"+gameRoundObj.getCurrentPlayer() + " picked up " + pickup.getStonesPickedUp()
                        + " stones from pile " + pickup.getSelectedPile() + ", and player Score is " + gameRoundObj.getPlayer2Score();
                gameRoundObj.setPickupHistory(history);
            }
            else if (pickup.getSelectedPile()==3
                    && gameRoundObj.getPile3Size()-pickup.getStonesPickedUp()>=0) {
                gameRoundObj.setPile3Size(gameRoundObj.getPile3Size() - pickup.getStonesPickedUp());
                gameRoundObj.setCurrentPlayer(gameRoundObj.getPlayer1Name());
                gameRoundObj.setPlayer2Score(gameRoundObj.getPlayer2Score() + pickup.getStonesPickedUp());
                String history = "\n"+gameRoundObj.getCurrentPlayer() + " picked up " + pickup.getStonesPickedUp()
                        + " stones from pile " + pickup.getSelectedPile() + ", and player Score is " + gameRoundObj.getPlayer2Score();
                gameRoundObj.setPickupHistory(history);
            }

            gameRoundObj.setGameStatus("Playing");
        }

        if(gameRoundObj.getPile1Size()<gameRoundObj.getMinStonesToPickUp() &&
                gameRoundObj.getPile2Size()<gameRoundObj.getMinStonesToPickUp() &&
                gameRoundObj.getPile3Size()<gameRoundObj.getMinStonesToPickUp() ) {
            gameRoundObj.setGameStatus("Completed");

            if(gameRoundObj.getPlayer1Score() == gameRoundObj.getPlayer2Score() ){
                gameRoundObj.setWinnerName("Draw: Both players have equal score");
            }
            else{
                String winner = gameRoundObj.getPlayer1Score() < gameRoundObj.getPlayer2Score() ? gameRoundObj.getPlayer1Name() : gameRoundObj.getPlayer2Name();
                gameRoundObj.setWinnerName("winner is " +winner);
            }
            messagingTemplate.convertAndSend("/topic/messages", gameRoundObj);
            session.setAttribute("gameRoundObj", gameRoundObj);
            model.addAttribute("gameRound", gameRoundObj);
            System.out.println("completed" );

            return null;
        }


        messagingTemplate.convertAndSend("/topic/messages", gameRoundObj);
        session.setAttribute("gameRoundObj", gameRoundObj);
        model.addAttribute("gameRound", gameRoundObj);

        return null;
    }

    @PostMapping("/winner/{player}")
    public ResponseEntity<?> announceWinner(@ModelAttribute GameRound gameRound, Model model,
                                          HttpServletRequest request,
                                          @PathVariable String player) {
        HttpSession session = request.getSession();
        GameRound gameRoundObj = (GameRound) session.getAttribute("gameRoundObj");
        String playerName = gameRound.getWinnerName().equals("player1") ? gameRoundObj.getPlayer1Name() : gameRoundObj.getPlayer2Name();

        gameRoundObj.setWinnerName("winner is " + playerName);

        messagingTemplate.convertAndSend("/topic/messages", gameRoundObj);
        session.setAttribute("gameRoundObj", gameRoundObj);
        model.addAttribute("gameRound", gameRoundObj);

        return null;

    }

    @GetMapping("/timeout/{player}")
    public ResponseEntity<?> timeout(@ModelAttribute GameRound gameRound, Model model,
                                          HttpServletRequest request,
                                          @PathVariable String player) {

        HttpSession session = request.getSession();
        GameRound gameRoundObj = (GameRound) session.getAttribute("gameRoundObj");
        gameRoundObj.setGameStatus("Completed");
        System.out.println("completed" );
        System.out.println(player);
        gameRoundObj.setWinnerName(player + " has Lost the Game!");
        messagingTemplate.convertAndSend("/topic/messages", gameRoundObj);
        session.setAttribute("gameRoundObj", gameRoundObj);
        model.addAttribute("gameRound", gameRoundObj);
        return null;
    }


}
