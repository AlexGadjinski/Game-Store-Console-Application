package org.gamestore.services;

import org.gamestore.entities.User;
import org.gamestore.services.dtos.CreateUserDTO;
import org.gamestore.services.dtos.LoginUserDTO;

public interface UserService {
    String register(CreateUserDTO createUserDTO);

    String login(LoginUserDTO loginUserDTO);

    boolean isLoggedIn();

    boolean isAdmin();

    String logout();

    String getOwnedGames();

    String addItem(String title);

    String removeItem(String title);

    String buyItems();
}
