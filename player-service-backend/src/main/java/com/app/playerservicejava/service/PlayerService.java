package com.app.playerservicejava.service;

import com.app.playerservicejava.model.Player;
import com.app.playerservicejava.model.Players;
import com.app.playerservicejava.repository.PlayerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlayerService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PlayerService.class);

    @Autowired
    private PlayerRepository playerRepository;

    public Players getPlayers() {
        Players players = new Players();
        playerRepository.findAll()
                .forEach(players.getPlayers()::add);
        return players;
    }

    @Cacheable(value = "player", key = "#playerId")
    public Optional<Player> getPlayerById(String playerId) {
        Optional<Player> player = null;

        /* simulated network delay */
        try {
            player = playerRepository.findById(playerId);
            Thread.sleep((long)(Math.random() * 2000));
        } catch (Exception e) {
            LOGGER.error("message=Exception in getPlayerById; exception={}", e.toString());
            return Optional.empty();
        }
        return player;
    }

    @CachePut(value = "player", key = "#playerData.playerId")
    public Player addPlayer(Player playerData) {
        Player player = null;
        try {
            player = playerRepository.save(playerData);
            return player;
        } catch (Exception e) {
            LOGGER.error("message=Exception in getPlayerById; exception={}", e.toString());
            return player;
        }
    }

    public List<Player> addPlayers(List<Player> playerData) {
        List<Player> players = null;
        try {
            players = playerRepository.saveAll(playerData);
            return players;
        } catch (Exception e) {
            LOGGER.error("message=Exception in getPlayerById; exception={}", e.toString());
            return players;
        }
    }

    @CachePut(value = "player", key = "#playerData.playerId")
    public Optional<Player> updatePlayer(Player playerData) {
        Optional<Player> player = null;
        try {
            int updated = playerRepository.updatePlayerById(playerData.getPlayerId(), playerData.getFirstName(), playerData.getLastName());
            if(updated > 0) {
                player = playerRepository.findById(playerData.getPlayerId());
            }
            return player;
        } catch (Exception e) {
            LOGGER.error("message=Exception in updatePlayerById; exception={}", e.toString());
            return player;
        }
    }

}
