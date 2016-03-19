package com.map.testing;

import com.badlogic.gdx.Game;
import com.map.testing.screens.SettingsScreen;

public class MapTester extends Game {

    @Override
    public void create() {
        this.setScreen(new SettingsScreen());
    }

    @Override
    public void render() {
        super.render();
    }
}
