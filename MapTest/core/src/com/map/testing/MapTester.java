package com.map.testing;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.map.testing.screens.SettingsScreen;

public class MapTester extends Game {

    @Override
    public void create() {
//        Gdx.graphics.setContinuousRendering(false);
        this.setScreen(new SettingsScreen());
    }

    @Override
    public void render() {
        super.render();
    }
}
