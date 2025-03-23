package org.gamestore.services;

import org.gamestore.services.dtos.CreateGameDTO;
import org.gamestore.services.dtos.DetailGameDTO;
import org.gamestore.services.dtos.EditGameDTO;
import org.gamestore.services.dtos.ViewGameDTO;

import java.util.Set;

public interface GameService {
    String addGame(CreateGameDTO createGameDTO);

    String editGame(EditGameDTO editGameDTO);

    String deleteGame(long id);

    Set<ViewGameDTO> getAllGames();

    DetailGameDTO getGameDetails(String title);
}
