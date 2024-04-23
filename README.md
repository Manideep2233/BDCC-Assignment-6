
# Pile of Stones




- **Judge (J):**
  - Set up the game by entering players' names.
  - Specify the initial number of stones in each pile.
  - Define the range of stones that players can pick from each pile.
  - Determine the rules for selecting the winner (e.g., picking fewer stones).
  - Monitor the game and intervene if any player is suspected of cheating.
  - Declare the winner based on the game rules or adjust the winner if necessary.

- **Players (P1 and P2):**
  - Await instructions from the Judge.
  - Select the number of stones to pick from each pile within the range specified by the Judge.
  - Try to pick fewer stones than the opponent to win the game.
  - Await the Judge's decision on the winner.



## Functionalities Implemented: 

- Judge able to give parameters for game and start it.
- Players able to pick stones from piles, until all the piles contain stones less than minimum pickup.
- The algorithm calculates scores and declares winner in the end(checks tie also).
- If judge feels someone has cheated or cheating, he can change/declare the winner.
- All the data on all the screens is updated with-out refreshing/reading the web page.


## Technologies Used: 
![Logo](https://miro.medium.com/v2/resize:fit:1400/format:webp/1*74xH4GzvbdGpO0leY8_KZg.png)
![Logo](https://blogger.googleusercontent.com/img/b/R29vZ2xl/AVvXsEgjRNmOXzM9OW69iXtuPO0fKQrEYxPGOyU5fxh0bg1j8ViZJW1e9TmgD_icpJ-qIvD-3Bxiva4FOLv2wi8z56yRdQSf4LZfHj8mtN1BPtZCD9RlluYPsMT23CcBlgNKGZiGpzvPmCU3LsA/s1600/thymeleaf.png)
![Logo](https://miro.medium.com/v2/resize:fit:1400/format:webp/1*yIEa02vx20jdlcWWJM-L4Q.jpeg)


## Execution Process

make sure java 17, maven is installed and set up.
Enter into the project directory and run the spring application.

```bash
  mvn spring-boot:run
```
Open three seperate browser windows and in each one go to the below urls for judge and players.

http://localhost:8081/admin
http://localhost:8081/play/player1
http://localhost:8081/play/player2

Enter the player and piles information on the admin screen, after that player can start playing games.
