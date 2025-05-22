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


    @PutMapping("/attack/{index}")
    public Map<String, Object> attack(@PathVariable int index) {
     
    Map<String, Object> response = new HashMap<>();
    Map<String, Integer> aiResponse;

    //  result.put("hit", hit);
    //cordinate in indice
        int x = index % 10;
        int y = index / 10;

        try {
            //turno del player
            int turnRes = game.playerTurn(new Node(x,y));
            response.put("hit", (turnRes==1 || turnRes==2));
            response.put("sunk", turnRes==2);
            
            //turno computer
            aiResponse = game.aiTurn();
            int aiResult = aiResponse.get("result");
            int atkIndex = aiResponse.get("posY")*10 + aiResponse.get("posX");
            //aggiungi alla response la stringa e il valore
            response.put("aiHit",(aiResult==1 || aiResult==2));
            response.put("aiSunk", aiResult);
            response.put("indexAttacco", atkIndex);

           
        } catch (AlredyHitException e) {
            response.put("error", "You suck");}

        return response;
    }
    


}
