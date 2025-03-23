package org.gamestore.services.impls;

import jakarta.validation.ConstraintViolation;
import org.gamestore.entities.Game;
import org.gamestore.entities.User;
import org.gamestore.repositories.GameRepository;
import org.gamestore.repositories.UserRepository;
import org.gamestore.services.UserService;
import org.gamestore.services.dtos.CreateUserDTO;
import org.gamestore.services.dtos.LoginUserDTO;
import org.gamestore.utils.ValidatorUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper mapper;
    private final ValidatorUtil validatorUtil;
    private User user;
    private final GameRepository gameRepository;
    private final Set<Game> shoppingCart;

    public UserServiceImpl(UserRepository userRepository, ModelMapper mapper, ValidatorUtil validatorUtil, GameRepository gameRepository) {
        this.userRepository = userRepository;
        this.mapper = mapper;
        this.validatorUtil = validatorUtil;
        this.gameRepository = gameRepository;
        this.shoppingCart = new HashSet<>();
    }

    @Override
    public String register(CreateUserDTO createUserDTO) {
        if (!createUserDTO.getPassword().equals(createUserDTO.getConfirmedPassword())) {
            return "Passwords do not match!";
        }

        if (!validatorUtil.isValid(createUserDTO)) {
            return validatorUtil.validate(createUserDTO).stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.joining(System.lineSeparator()));
        }

        if (userRepository.findByEmail(createUserDTO.getEmail()).isPresent()) {
            return "Email address already in use!";
        }

        User user = mapper.map(createUserDTO, User.class);
        if (userRepository.count() == 0) {
            user.setAdmin(true);
        }

        userRepository.save(user);
        return "%s was registered".formatted(user.getFullName());
    }

    @Override
    public String login(LoginUserDTO loginUserDTO) {
        Optional<User> user =
                userRepository.findByEmailAndPassword(loginUserDTO.getEmail(), loginUserDTO.getPassword());
        if (user.isEmpty()) {
            return "Invalid email or password!";
        }
        this.user = user.get();
        return String.format("Successfully logged in %s", this.user.getFullName());
    }

    @Override
    public boolean isLoggedIn() {
        return this.user != null;
    }

    @Override
    public boolean isAdmin() {
        return isLoggedIn() && user.isAdmin();
    }

    @Override
    public String logout() {
        String result = isLoggedIn()
                ? "User %s successfully logged out".formatted(user.getFullName())
                : "Cannot log out. No user was logged in.";
        this.user = null;
        return result;
    }

    @Override
    public String getOwnedGames() {
        if (!isLoggedIn()) {
            return "No logged in user!";
        }
        return user.getGames().stream()
                .map(Game::getTitle)
                .collect(Collectors.joining(System.lineSeparator()));
    }

    @Override
    public String addItem(String title) {
        if (!isLoggedIn()) {
            return "No logged in user!";
        }

        if (shoppingCart.stream().anyMatch(g -> g.getTitle().equalsIgnoreCase(title))) {
            return title + " has been already added to cart!";
        }

        Optional<Game> optionalGame = gameRepository.findByTitle(title);
        if (optionalGame.isEmpty()) {
            return "No such game found!";
        }

        if (user.getGames().stream().anyMatch(g -> g.getTitle().equalsIgnoreCase(title))) {
            return "You already own %s!".formatted(title);
        }

        shoppingCart.add(optionalGame.get());
        return title + " added to cart.";
    }

    @Override
    public String removeItem(String title) {
        if (!isLoggedIn()) {
            return "No logged in user!";
        }

        Optional<Game> optionalGame = shoppingCart.stream().filter(g -> g.getTitle().equals(title)).findFirst();
        if (optionalGame.isEmpty()) {
            return "No such game in shopping cart!";
        }

        shoppingCart.remove(optionalGame.get());
        return title + " removed from cart.";
    }

    @Override
    public String buyItems() {
        if (!isLoggedIn()) {
            return "No logged in user!";
        }

        if (shoppingCart.isEmpty()) {
            return "Shopping cart is empty!";
        }

        user.getGames().addAll(shoppingCart);
        userRepository.save(user);
        String result = "Successfully bought games: \n" +
                shoppingCart.stream().map(g -> String.format(" -%s", g.getTitle()))
                                .collect(Collectors.joining(System.lineSeparator()));
        shoppingCart.clear();
        return result;
    }
}
