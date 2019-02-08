package com.raccoon.takenoko;

import com.raccoon.takenoko.game.Game;
import com.raccoon.takenoko.player.BamBotFactory;
import com.raccoon.takenoko.player.BotFactory;
import com.raccoon.takenoko.player.Player;

import com.raccoon.takenoko.tool.Constants;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.*;
import java.util.stream.Collectors;

@SpringBootApplication
public class Takeyesntko {

    private static final Logger LOGGER = Logger.getLogger(Takeyesntko.class);

    private static boolean verbose = true;

    @Autowired
    private ObjectFactory<Game> gameObjectFactory;



    public static void main(String[] args) {

        SpringApplication.run(Takeyesntko.class, args);

    }

    @Bean
    public CommandLineRunner commandLineRunner(@Autowired @Qualifier("everyOther") FactoryBean<Player> everyOtherFactory, @Autowired @Qualifier("giveMeBambots") FactoryBean<Player> gimmeBambots) {
        return args -> {



            print(" ________      _       __   __   ______    __    __   ________   __   __   ________  ");
            print("|__    __|   /   \\    |  | /  / |   ___|  |  \\  |  | |   __   | |  | /  / |   __   | ");
            print("   |  |     / /_\\ \\   |  |/  /  |  |__    |  |\\ |  | |  |  |  | |  |/  /  |  |  |  | ");
            print("   |  |    / _____ \\  |     <   |   __|_  |  | \\|  | |  |__|  | |     <   |  |__|  | ");
            print("   |__|   /_/     \\_\\ |__|\\__\\  |_______| |__|  \\__| |________| |__|\\__\\  |________| ");
            print("                                                         Presented by angry raccoons\n");

            if (args.length > 0) {
                launch1gameNoJutsu(4, everyOtherFactory);
            }
            else {
                launchManyGamesNoJutsu(4, everyOtherFactory);
                launchManyGamesNoJutsu(2, gimmeBambots);
            }

        };
    }

    @Bean(name = "everyOther")
    public BotFactory botFactory() {
        return new BotFactory();
    }

    @Bean(name = "giveMeBambots")
    public BamBotFactory bamBotFactory() {
        return new BamBotFactory();
    }


    /**
     * Allows a conditionnal print
     *
     * @param str The String to be printed.
     */
    public static void print(String str) {
        if (verbose) {
            LOGGER.info(str);
        }
    }

    /**
     * Launches the game, verbose mode
     */
    private void launch1gameNoJutsu(int playerNumber, FactoryBean<Player> playerFactory) {
        Game game = gameObjectFactory.getObject();
        try {
            game.addPlayers(playerNumber, playerFactory);
        } catch (Exception e) {
            e.printStackTrace();
        }
        game.start();
    }

    /**
     * Launches 1000 games and prints out the output
    */
    private void launchManyGamesNoJutsu(int playerNumber, FactoryBean<Player> playerFactory) {

        Takeyesntko.setVerbose(false);

        Map<Integer, Integer> playerWins = new HashMap<>();
        int[] scores = new int [playerNumber];

        for (int i = 1 ; i <= playerNumber ; i++) {
            playerWins.put(i, 0);
        }
        int voidedGames = 0;
        String[] playersTypes = new String[playerNumber];

        for (int i = 0; i < Constants.NUMBER_OF_GAMES_FOR_STATS; i++) {
            Game game = gameObjectFactory.getObject();
            try {
                game.addPlayers(playerNumber, playerFactory);
            } catch (Exception e) {
                print("Something went wrong while adding the players");
            }
            game.start();

            // First check that it isn't a void game (all players at 0)
            int numberOfNullResults = 0;
            for (Player pl : game.getPlayers()) {
                if (pl.getScore() != 0) {
                    break;
                } else {
                    numberOfNullResults++;
                }
            }
            if (numberOfNullResults == playerNumber) {
                voidedGames++;
                continue;
            }

            // increments the wins of the winner
            playerWins.put(game.getWinner().getId(), playerWins.get(game.getWinner().getId()) + 1);

            // counts the points
            for (Player pl : game.getPlayers()) {
                playersTypes[pl.getId() - 1] = pl.getClass().getSimpleName();
                scores[pl.getId() - 1] += pl.getScore();
            }
        }

        // printing out results
        Takeyesntko.setVerbose(true);
        List<Map.Entry<Integer, Integer>> sortedWinners = playerWins.entrySet().stream().sorted(Map.Entry.comparingByValue()).collect(Collectors.toList()); // Sorting the players according to their score
        print(String.format(" -- Launched %6.0f games!", Constants.NUMBER_OF_GAMES_FOR_STATS));
        print(String.format("| %-8s| %-14s| %-12s| %-9s|", "Player ", "Type","Victories", "Avg. Score\t"));
        for (int i = sortedWinners.size() - 1; i >= 0; i--) {
            int currentPlayer = sortedWinners.get(i).getKey();
            print(String.format("| #%-7d|  %-13s|     %5.1f %% |        %5.2f\t|", currentPlayer, playersTypes[currentPlayer - 1], (float)sortedWinners.get(i).getValue()*100 / (Constants.NUMBER_OF_GAMES_FOR_STATS), (float)scores[currentPlayer - 1]/Constants.NUMBER_OF_GAMES_FOR_STATS));
        }
        print(String.format(" -- There has been %d void games where all players' scores were 0 (roughly %3.1f percents)", voidedGames, (voidedGames * 100 / Constants.NUMBER_OF_GAMES_FOR_STATS)));

        // Checksum : if the checksum is not nbGames, points were badly distributed
        int totalGames = 0;
        for (int w : playerWins.values()) {
            totalGames += w;
        }
        print(String.format(" -- Checksum : won + voided games add up to %d (should be %3.0f)%n", totalGames + voidedGames, Constants.NUMBER_OF_GAMES_FOR_STATS));
    }

    /**
     * Allows to inject (i.e. at runtime) a verbose value (which is 'true' by default).
     *
     * @param verbose typically a new verbose value
     */
    public static void setVerbose(boolean verbose) {

        Takeyesntko.verbose = verbose;
    }

}