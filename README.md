# Game-Store-Console-Application

## Description
**Game Store Console Application**  
A simple console-based platform for a game store where users can register, log in, manage their shopping cart, buy games, and manage their own game catalog. Administrators can add, edit, or delete games from the catalog. Users can view all games, see detailed information about each game, and purchase games they haven't bought yet. The system maintains user-specific game ownership and ensures users can only purchase games once.

---

## Features:
1. **User Registration, Login, and Logout**  
   - Users can register with an email, password, and full name.
   - Admins and users can log in and log out of the system.
   - System prevents invalid user registrations with appropriate validation.

2. **Game Management**  
   - Admins can add, edit, and delete games from the catalog.
   - Games include details like title, price, size, description, YouTube trailer ID, release date, and thumbnail URL.

3. **View Games**  
   - All users can view all games and their details.
   - Users can see a list of their owned games after logging in.

4. **Shopping Cart**  
   - Logged-in users can add or remove games from their shopping cart.
   - Users can buy games from their cart, and the system ensures they cannot buy the same game twice.

5. **Database Design**  
   - A relational database holds users, games, and orders.
   - Users can register, order, and manage games with different levels of access (admin vs. regular user).

---

## Example Commands:
- **RegisterUser**|`ivan@ivan.com`|`Ivan12`|`Ivan12`|`Ivan`  
  Registers a new user with email `ivan@ivan.com`, password `Ivan12`, and full name `Ivan`.
  
- **LoginUser**|`ivan@ivan.com`|`Ivan12`  
  Logs in the user with email `ivan@ivan.com` and password `Ivan12`.
  
- **Logout**  
  Logs out the currently logged-in user.

- **AddGame**|`Overwatch`|`100.00`|`15.5`|`FqnKB22pOC0`|`https://url.com/overwatch.png`|`Overwatch is a team-based multiplayer shooter.`|`24-05-2016`  
  Adds a new game with title `Overwatch`, price `100.00`, size `15.5`, YouTube trailer ID `FqnKB22pOC0`, image URL, description, and release date `24-05-2016`.

- **EditGame**|`1`|`price=80.00`|`size=12.0`  
  Edits the game with ID `1`, changing the price to `80.00` and size to `12.0`.

- **DeleteGame**|`1`  
  Deletes the game with ID `1`.

- **AllGames**  
  Displays a list of all games with their titles and prices.

- **DetailsGame**|`Overwatch`  
  Displays detailed information about the game `Overwatch`.

- **OwnedGames**  
  Displays the list of games owned by the currently logged-in user.

- **AddItem**|`Overwatch`  
  Adds the game `Overwatch` to the shopping cart.

- **RemoveItem**|`Overwatch`  
  Removes the game `Overwatch` from the shopping cart.

- **BuyItem**  
  Buys all games in the shopping cart and adds them to the user's owned games.

---
