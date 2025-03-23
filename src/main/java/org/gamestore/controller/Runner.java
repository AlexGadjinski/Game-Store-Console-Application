package org.gamestore.controller;

import org.gamestore.services.GameService;
import org.gamestore.services.UserService;
import org.gamestore.services.dtos.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.stream.Collectors;

@Component
public class Runner implements CommandLineRunner {
    private final BufferedReader reader;
    private final UserService userService;
    private final GameService gameService;

    public Runner(BufferedReader reader, UserService userService, GameService gameService) {
        this.reader = reader;
        this.userService = userService;
        this.gameService = gameService;
    }

    @Override
    public void run(String... args) throws IOException {
        while (true) {
            String line = reader.readLine();
            if (line.equals("stop")) {
                return;
            }

            String[] tokens = line.split("\\|");

            String command = switch (tokens[0]) {
                case "RegisterUser" -> userService.register(new CreateUserDTO(tokens[1], tokens[2], tokens[3], tokens[4]));
                case "LoginUser" -> userService.login(new LoginUserDTO(tokens[1], tokens[2]));
                case "Logout" -> userService.logout();
                case "AddGame" -> gameService.addGame(new CreateGameDTO(
                        tokens[1], new BigDecimal(tokens[2]), Double.parseDouble(tokens[3]),
                        tokens[4], tokens[5], tokens[6], parseDate(tokens[7])));
                case "EditGame" -> {
                    EditGameDTO editGameDTO = new EditGameDTO();
                    populateEditGameDTO(editGameDTO, tokens);
                    yield gameService.editGame(editGameDTO);
                }
                case "DeleteGame" -> gameService.deleteGame(Long.parseLong(tokens[1]));
                case "AllGames" -> gameService.getAllGames().stream()
                        .map(ViewGameDTO::toString)
                        .collect(Collectors.joining(System.lineSeparator()));
                case "DetailGame" -> gameService.getGameDetails(tokens[1]).toString();
                case "OwnedGames" -> userService.getOwnedGames();
                case "AddItem" -> userService.addItem(tokens[1]);
                case "RemoveItem" -> userService.removeItem(tokens[1]);
                case "BuyItem" -> userService.buyItems();
                default -> "";
            };
            System.out.println(command);
        }
    }

    private void populateEditGameDTO(EditGameDTO editGameDTO, String[] tokens) {
        editGameDTO.setId(Long.parseLong(tokens[1]));
        Arrays.stream(tokens).skip(2)
                .map(t -> t.split("="))
                .forEach(s -> {
                    String field = s[0];
                    String value = s[1];
                    switch (field) {
                        case "title" -> editGameDTO.setTitle(value);
                        case "price" -> editGameDTO.setPrice(new BigDecimal(value));
                        case "size" -> editGameDTO.setSize(Double.parseDouble(value));
                        case "url" -> editGameDTO.setUrl(value);
                        case "imageThumbnail" -> editGameDTO.setImageThumbnail(value);
                        case "description" -> editGameDTO.setDescription(value);
                        case "releaseDate" -> editGameDTO.setReleaseDate(parseDate(value));
                    }
                });
    }

    private static LocalDate parseDate(String date) {
        return LocalDate.parse(date, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }
}
