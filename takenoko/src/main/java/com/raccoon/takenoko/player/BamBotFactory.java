package com.raccoon.takenoko.player;

import org.springframework.beans.factory.FactoryBean;

public class BamBotFactory implements FactoryBean<Player> {

    @Override
    public Player getObject() throws Exception {
        return new BamBot();
    }

    @Override
    public Class<?> getObjectType() {
        return Player.class;
    }


}