package com.example.PileOfStones;

import lombok.Data;

@Data
public class Game {
    private String player1Name;
    private String player2Name;
    private int pile1Size;
    private int pile2Size;
    private int pile3Size;
    private int minStonesPerTurn;
    private int maxStonesPerTurn;
    private String currentPlayer;
    private int player1Score;
    private int player2Score;
}

