package com.jozielsc.g4a.interfaces;

public abstract class Screen {
    protected final Game game;

    public Screen(Game game) {
        this.game = game;
    }

    public abstract void update(float deltaTime);

    public abstract void renderer(float deltaTime);

    public abstract void pause();

    public abstract void resume();

    public abstract void dispose();
    
 
}
