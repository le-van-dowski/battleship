package it.terenzi.battleship.logic;

import java.util.Random;

import it.terenzi.battleship.logic.exceptions.AlredyHitException;
import it.terenzi.battleship.logic.exceptions.InvalidPositionException;
import lombok.Data;

@Data
public class BattleshipGame {
    private Board playerBoard;
    private Board aiBoard;

    public BattleshipGame() {
        this.playerBoard = new Board();
        this.aiBoard = new Board();
        this.aiBoard.randomizeShips();
        this.playerBoard.randomizeShips();
    }
    /*
     * public BattleshipGame(Board playerBoard, Board aiBoard) {
     * this.playerBoard = new Board();
     * this.aiBoard = new Board();
     * aiBoard.randomizeShips();
     * }
     */

    public void placePlayerShip(int posx, int posy, int lenght, boolean vertical) throws InvalidPositionException {
        playerBoard.placeShip(posx, posy, lenght, vertical);
    }

    public void randomfunction() {
        playerBoard.randomizeShips();
    }

    public int playerTurn(Node node) throws AlredyHitException {
        return aiBoard.tryHit(node);
    }

    public void emptyBoard() {
        playerBoard = new Board();
    }

    public int aiTurn() {
        Random r = new Random(System.currentTimeMillis());
        while (true) {
            try {
                int result = playerBoard.tryHit(new Node(r.nextInt(10), r.nextInt(10), null));
                if (playerBoard.hasLost()) {
                    playerBoard.endGame();
                }
                return result;
            } catch (Exception e) {
            }
        }
    }
}
