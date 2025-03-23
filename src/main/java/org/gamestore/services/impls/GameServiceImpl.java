package org.gamestore.services.impls;

import jakarta.validation.ConstraintViolation;
import org.gamestore.entities.Game;
import org.gamestore.repositories.GameRepository;
import org.gamestore.services.GameService;
import org.gamestore.services.UserService;
import org.gamestore.services.dtos.CreateGameDTO;
import org.gamestore.services.dtos.DetailGameDTO;
import org.gamestore.services.dtos.EditGameDTO;
import org.gamestore.services.dtos.ViewGameDTO;
import org.gamestore.utils.ValidatorUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GameServiceImpl implements GameService {
    private final GameRepository gameRepository;
    private final ModelMapper mapper;
    private final ValidatorUtil validatorUtil;
    private final UserService userService;

    public GameServiceImpl(GameRepository gameRepository, ModelMapper mapper, ValidatorUtil validatorUtil, UserService userService) {
        this.gameRepository = gameRepository;
        this.mapper = mapper;
        this.validatorUtil = validatorUtil;
        this.userService = userService;
    }

    @Override
    public String addGame(CreateGameDTO createGameDTO) {
        if (!userService.isAdmin()) {
            return "User is not admin!";
        }

        if (!validatorUtil.isValid(createGameDTO)) {
            return validatorUtil.validate(createGameDTO).stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.joining(System.lineSeparator()));
        }

        Game game = mapper.map(createGameDTO, Game.class);
        gameRepository.save(game);
        return "Added " + game.getTitle();
    }

    @Override
    public String editGame(EditGameDTO editGameDTO) {
        if (!userService.isAdmin()) {
            return "User is not admin!";
        }

        if (!validatorUtil.isValid(editGameDTO)) {
            return validatorUtil.validate(editGameDTO).stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.joining(System.lineSeparator()));
        }

        Optional<Game> optionalGame = gameRepository.findById(editGameDTO.getId());
        if (optionalGame.isEmpty()) {
            return "No such game found!";
        }

        Game game = optionalGame.get();
        updateGame(game, editGameDTO);
        gameRepository.save(game);
        return "Edited " + game.getTitle();
    }

    private void updateGame(Game game, EditGameDTO editGameDTO) {
        if (editGameDTO.getTitle() != null) {
            game.setTitle(editGameDTO.getTitle());
        }
        if (editGameDTO.getPrice() != null) {
            game.setPrice(editGameDTO.getPrice());
        }
        if (editGameDTO.getSize() != null) {
            game.setSize(editGameDTO.getSize());
        }
        if (editGameDTO.getUrl() != null) {
            game.setUrl(editGameDTO.getUrl());
        }
        if (editGameDTO.getImageThumbnail() != null) {
            game.setImageThumbnail(editGameDTO.getImageThumbnail());
        }
        if (editGameDTO.getDescription() != null) {
            game.setDescription(editGameDTO.getDescription());
        }
        if (editGameDTO.getReleaseDate() != null) {
            game.setReleaseDate(editGameDTO.getReleaseDate());
        }
    }


    @Override
    public String deleteGame(long id) {
        if (!userService.isAdmin()) {
            return "User is not admin!";
        }

        Optional<Game> optionalGame = gameRepository.findById(id);
        if (optionalGame.isEmpty()) {
            return "No such game found!";
        }

        gameRepository.delete(optionalGame.get());
        return "Deleted " + optionalGame.get().getTitle();
    }

    @Override
    public Set<ViewGameDTO> getAllGames() {
        return gameRepository.findAll().stream()
                .map(g -> mapper.map(g, ViewGameDTO.class))
                .collect(Collectors.toSet());
    }

    @Override
    public DetailGameDTO getGameDetails(String title) {
        Optional<Game> optionalGame = gameRepository.findByTitle(title);
        if (optionalGame.isEmpty()) {
            return new DetailGameDTO();
        }
        return mapper.map(optionalGame.get(), DetailGameDTO.class);
    }
}
