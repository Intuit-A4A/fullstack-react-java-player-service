package com.app.playerservicejava.controller;

import com.app.playerservicejava.model.Player;
import com.app.playerservicejava.model.Players;
import com.app.playerservicejava.service.PlayerService;
import jakarta.annotation.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping(value = "v1/players", produces = { MediaType.APPLICATION_JSON_VALUE })
public class PlayerController {
    @Resource
    private PlayerService playerService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Players> getPlayers() {
        Players players = playerService.getPlayers();
        return ok(players);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Player> getPlayerById(@PathVariable("id") String id) {
        Optional<Player> player = playerService.getPlayerById(id);

        if (player.isPresent()) {
            return new ResponseEntity<>(player.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/player")
    public ResponseEntity<Player> insertPlayer(@RequestBody Player playerData) {
        Player player = playerService.addPlayer(playerData);
        if(player != null) {
            return new ResponseEntity<>(player, HttpStatus.OK);
        }
        return new ResponseEntity<>(player, HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/player")
    public ResponseEntity<Optional<Player>> updatePlayer(@RequestBody Player playerData) {
        Optional<Player> player = playerService.updatePlayer(playerData);
        if(player != null) {
            return new ResponseEntity<>(player, HttpStatus.OK);
        }
        return new ResponseEntity<>(player, HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/players")
    public ResponseEntity<List<Player>> insertPlayers(@RequestBody List<Player> playerData) {
        List<Player> players = playerService.addPlayers(playerData);
        if(!players.isEmpty()) {
            return new ResponseEntity<>(players, HttpStatus.OK);
        }
        return new ResponseEntity<>(players, HttpStatus.BAD_REQUEST);
    }
}
