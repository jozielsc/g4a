package com.jozielsc.g4a.game;

import com.jozielsc.g4a.interfaces.Game;
import com.jozielsc.g4a.interfaces.IGLGraphics;
import com.jozielsc.g4a.interfaces.Screen;


public abstract class GLScreen extends Screen {
    protected final IGLGraphics glGraphics;
    protected final GLGame glGame;
    
    public GLScreen(Game game) {
        super(game);
        glGame = (GLGame)game;
        glGraphics = ((GLGame)game).getGLGraphics();
    }

}
