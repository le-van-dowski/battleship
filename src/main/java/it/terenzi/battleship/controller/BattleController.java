package it.terenzi.battleship.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.terenzi.battleship.logic.BattleshipGame;
import it.terenzi.battleship.logic.Board;

@RestController
@RequestMapping("/battle")
public class BattleController {

    private static BattleshipGame game;

    @PostMapping("/newgame")
    public Map<String, Board> riempiGriglie() {
        game = new BattleshipGame();
        Map<String, Board> griglie = new HashMap<>();
        griglie.put("player", game.getPlayerBoard());
        griglie.put("computer", game.getAiBoard());
        return griglie;
    }
    // da mettere l'attacco e la risposta del computer

    /*
     * @PutMapping("/attack/{index}")
     * public Map<String, Boolean> attack(@PathVariable int index) {
     * boolean hit = computerShips.contains(index);
     * Map<String, Boolean> result = new HashMap<>();
     * result.put("hit", hit);
     * 
     * // se colpito, rimuoviamo la nave (opzionale)
     * if (hit) {
     * computerShips.remove(index);
     * }
     * 
     * return result;
     * }
     */
}
