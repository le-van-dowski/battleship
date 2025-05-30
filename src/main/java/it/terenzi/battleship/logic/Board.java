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

        Ship nuova = new Ship(posx, posy, lenght, vertical);
        for (Ship s : ships) {
            if (s.overlap(nuova))
                throw new InvalidPositionException("ship placement overlaps with another boat!!");
        }
        ships.add(nuova);
    }

    public void randomizeShips() {
        Random pO = new Random(System.currentTimeMillis());

        int l[] = { 4, 3, 3, 3, 2, 2, 2, 1, 1 };

        for (int i = 0; i < 9; i++) {
            boolean placed = false;
            while (!placed) {
                int posx = pO.nextInt(10);
                int posy = pO.nextInt(10);
                // boolean v = vertical == 0 ? true : false;
                Boolean v = pO.nextBoolean();
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
        if (hittenShipNodes.contains(node) || hittenWaterNodes.contains(node)) {
            throw new AlredyHitException("this node alredy got hitten1");
        }

        for (Ship s : ships) {
            for (Node n : s.getNodes()) {
                if ((n.getPosx() == node.getPosx()) && (n.getPosy() == node.getPosy())) {
                    n.setStatus(State.HITSHIP);
                    node.setStatus(State.HITSHIP);
                    hittenShipNodes.add(node);
                    return s.checkStatus();
                }

            }
        }
        node.setStatus(State.HITWATER);
        hittenWaterNodes.add(node);
        return 0;

    }

    public Boolean hasLost() { // se almeno una barca non è affondata ritorna false, altrimenti l'avversario ha
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