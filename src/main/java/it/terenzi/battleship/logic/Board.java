package it.terenzi.battleship.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import it.terenzi.battleship.logic.exceptions.AlredyHitException;
import it.terenzi.battleship.logic.exceptions.InvalidPositionException;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Board {

    private boolean hidden = false;
    private List<Ship> ships = new ArrayList<>();
    private List<Node> hittenWaterNodes = new ArrayList<>();
    private List<Node> hittenShipNodes = new ArrayList<>();

    public void Clear() {
        ships.clear();
    }

    public void placeShip(int posx, int posy, int lenght, boolean vertical) throws InvalidPositionException {
        if (vertical && (posy + lenght) > 9) {
            throw new InvalidPositionException("ship placement overlaps with another boat!!");
        } else if (!vertical && (posx + lenght) > 9) {
            throw new InvalidPositionException("ship placement overlaps with another boat!!");
        }
        boolean overlapped = false;
        for (Ship s : ships) {
            for (Node n : s.getNodes()) {
                for (int i = 0; i < lenght; i++) {
                    overlapped = (vertical) ? n.getPosx() == posx + 1 && n.getPosy() == posy
                            : n.getPosx() == posx && n.getPosy() == posy;

                    if (overlapped) {
                        throw new InvalidPositionException("ship placement overlaps with another boat!!");
                    }
                }
            }
        }
        ships.add(new Ship(posx, posy, lenght, vertical));
    }

    public void randomizeShips() {
        Random pO = new Random(System.currentTimeMillis());

        int l[] = { 4, 3, 3, 3, 2, 2, 2, 1, 1 };

        for (int i = 0; i < 9; i++) {
            boolean placed = false;
            while (!placed) {
                int posx = pO.nextInt(10);
                int posy = pO.nextInt(10);
                int vertical = pO.nextInt(2);
                boolean v = vertical == 0 ? true : false;
                try {
                    placeShip(posx, posy, l[i], v);
                    placed = true;
                } catch (InvalidPositionException e) {
                    placed = false;
                }
            }
        }
    }

    public int tryHit(Node node) throws AlredyHitException {// 0 hit water, 1 hit ship, 2 ship sunk
        // control hits
        if (!((node.getStatus() == State.HITSHIP) || (node.getStatus() == State.SHIP))) {
            for (Node n : hittenWaterNodes) {
                if (n.equals(node)) {
                    throw new AlredyHitException("this node alredy got hitten");
                }
            }
            hittenWaterNodes.add(node);
            for (Ship s : ships) {
                for (Node n : s.getNodes()) {
                    if ((n.getPosx() == node.getPosx()) && (n.getPosy() == node.getPosy())) {
                        n.setStatus(State.HITWATER);
                        return s.checkStatus();
                    }

                }
            }
        } else {
            for (Node n : hittenShipNodes) {
                if (n.equals(node)) {
                    throw new AlredyHitException("this node alredy got hitten");
                }
            }
            hittenShipNodes.add(node);
            for (Ship s : ships) {
                for (Node n : s.getNodes()) {
                    if ((n.getPosx() == node.getPosx()) && (n.getPosy() == node.getPosy())) {
                        n.setStatus(State.HITSHIP);
                        return s.checkStatus();
                    }

                }
            }

        }
        return 0;
    }

    public boolean hasLost() { // se almeno una barca non è affondata ritorna false, altrimenti l'avversario ha
                               // vinto
        for (Ship s : ships) {
            if (!s.isSunk())
                return false;
        }
        return true;
    }

    public void endGame() {
        hidden = true;
    }

}