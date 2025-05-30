package it.terenzi.battleship.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.terenzi.battleship.logic.BattleshipGame;
import it.terenzi.battleship.logic.Board;
import it.terenzi.battleship.logic.Node;
import it.terenzi.battleship.logic.exceptions.AlredyHitException;

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

    @GetMapping("/attack/{index}")
    public Map<String, Object> attack(@PathVariable int index) {

        Map<String, Object> response = new HashMap<>();
        Map<String, Integer> aiResponse;
        Boolean playerLoss, aiLoss;

        // result.put("hit", hit);
        // cordinate in indice
        int x = index % 10;
        int y = index / 10;

        try {
            // check player loss
            playerLoss = game.getPlayerBoard().hasLost();
            // se true ha perso
            response.put("playerLost", playerLoss);
            aiLoss = game.getAiBoard().hasLost();
            response.put("aiLost", aiLoss);

            // turno del player
            int turnRes = game.playerTurn(new Node(x, y, null));
            response.put("hit", turnRes > 0);
            response.put("sunk", turnRes == 2);

            // turno computer
            aiResponse = game.aiTurn();
            int aiResult = aiResponse.get("result");
            int atkIndex = aiResponse.get("posY") * 10 + aiResponse.get("posX");
            // aggiungi alla response la stringa e il valore
            response.put("aiHit", aiResult > 0);
            response.put("aiSunk", aiResult == 2);
            response.put("indexAttacco", atkIndex);

        } catch (AlredyHitException e) {
            response.put("error", e.getMessage());
        }

        return response;

    }

}
