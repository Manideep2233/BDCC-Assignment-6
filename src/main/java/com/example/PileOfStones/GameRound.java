package com.example.PileOfStones;


import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;


@Data
public class GameRound {

    private String player1Name;
    private String player2Name;
    private int pile1Size;
    private int pile2Size;
    private int pile3Size;
    private int minStonesToPickUp;
    private int maxStonesToPickUp;
    private String currentPlayer;
    private int player1Score;
    private int player2Score;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private long durationInSeconds;
    private String winner;
    private String pickupHistory;

    //for outputs only
    private String statusMessage;
    private String gameStatus;
    private String winnerName;
    private String currentPlayerScore;

}