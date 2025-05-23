package it.terenzi.battleship.logic;

import java.util.HashMap;
import java.util.Map;
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
     * public BattleshipGame() {
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

    public Map<String, Integer> aiTurn() {
        Map<String, Integer> response = new HashMap<>();
        Random r = new Random(System.currentTimeMillis());
        while (true) {
            try {
                int x, y;
                x = r.nextInt(10);
                y = r.nextInt(10);
                int result = playerBoard.tryHit(new Node(x, y, null));
                if (playerBoard.hasLost()) {
                    playerBoard.endGame();
                }
                response.put("result", result);
                response.put("posX", x);
                response.put("posY", y);

                return response;

            } catch (Exception e) {
            }
        }
    }
}
