package it.terenzi.battleship.logic;

import lombok.Data;

@Data
public class Node {

    private int posx, posy;
    private State status;

    public Node(int posx, int posy, State status) {
        this.posx = posx;
        this.posy = posy;
        this.status = status;
    }

}