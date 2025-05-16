package it.terenzi.battleship.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.terenzi.battleship.logic.BattleshipGame;
import it.terenzi.battleship.logic.Board;

@RestController
@RequestMapping("/battle")
public class BattleController {

    private static BattleshipGame game;

    @PostMapping("/newgame")
    public Map<String, Board> popolaGriglie() {
        game = new BattleshipGame();
        Map<String, Board> griglie = new HashMap<>();
        griglie.put("player", game.getPlayerBoard());
        griglie.put("computer", game.getAiBoard());
        return griglie;
    }
    // da mettere l'attacco e la risposta del computer

}
