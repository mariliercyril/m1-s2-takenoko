package com.cco.takenoko.server;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.ObjectFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.context.annotation.Bean;

import com.cco.takenoko.server.facade.ServerFacade;

import com.cco.takenoko.server.game.Game;

import com.cco.takenoko.server.player.BamBotFactory;
import com.cco.takenoko.server.player.Player;

@SpringBootApplication
public class TakenokoServer {

    private static final Logger LOGGER = Logger.getLogger(TakenokoServer.class);

    private static boolean verbose = true;

    private static Game game;

    public static Game getGame() {

    	return game;
    }

    @Autowired
    private ObjectFactory<Game> gameObjectFactory;

    public static void main(String[] args) {

        SpringApplication.run(TakenokoServer.class, args);

    }

    @Bean
    public CommandLineRunner commandLineRunner(@Autowired @Qualifier("bamBots") FactoryBean<Player> bamBots) {
        return args -> {



            print(" ________      _       __   __   ______    __    __   ________   __   __   ________  ");
            print("|__    __|   /   \\    |  | /  / |   ___|  |  \\  |  | |   __   | |  | /  / |   __   | ");
            print("   |  |     / /_\\ \\   |  |/  /  |  |__    |  |\\ |  | |  |  |  | |  |/  /  |  |  |  | ");
            print("   |  |    / _____ \\  |     <   |   __|_  |  | \\|  | |  |__|  | |     <   |  |__|  | ");
            print("   |__|   /_/     \\_\\ |__|\\__\\  |_______| |__|  \\__| |________| |__|\\__\\  |________| ");
            print("                                                         Presented by 2co\n");

            if (args.length > 0) {
                launch1gameNoJutsu(4, bamBots);
            }
            else {
                new ServerFacade().launchManyGamesNoJutsu(2, bamBots, gameObjectFactory);
            }

        };
    }

	@Bean(name = "bamBots")
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
        game = gameObjectFactory.getObject();
        try {
            game.addPlayers(playerNumber, playerFactory);
        } catch (Exception e) {
            e.printStackTrace();
        }
        game.start();
    }

    /**
     * Allows to inject (i.e. at runtime) a verbose value (which is 'true' by default).
     *
     * @param verbose typically a new verbose value
     */
    public static void setVerbose(boolean verbose) {

        TakenokoServer.verbose = verbose;
    }

}