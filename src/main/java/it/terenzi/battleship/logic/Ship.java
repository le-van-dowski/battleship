package it.terenzi.battleship.logic;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Ship {

    private List<Node> nodes = new ArrayList<>();
    private boolean sunk;

    public Ship(int posx, int posy, int lenght, boolean vertical) {
        if (vertical) {
            for (int i = 0; i < lenght; i++) {
                nodes.add(new Node(posx, posy + 1, State.SHIP));
            }
        } else {
            for (int i = 0; i < lenght; i++) {
                nodes.add(new Node(posx + 1, posy, State.SHIP));
            }
        }
    }

    public int checkStatus() {
        for (Node n : nodes) {
            if (!(n.getStatus() == State.HITSHIP))
                return 1;
        }
        sunk = true;
        return 2;
    }

    public boolean isSunk() {
        return sunk;
    }

}
