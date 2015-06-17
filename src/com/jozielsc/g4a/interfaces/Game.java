package com.jozielsc.g4a.interfaces;

public interface Game {
    public Input getInput();

    public FileIO getFileIO();

    public IGLGraphics getGLGraphics();

    public Audio getAudio();

    public void setScreen(Screen screen);

    public Screen getCurrentScreen();

    public Screen getStartScreen();
}