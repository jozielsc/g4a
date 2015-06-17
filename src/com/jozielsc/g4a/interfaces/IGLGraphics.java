package com.jozielsc.g4a.interfaces;

import javax.microedition.khronos.opengles.GL10;

public interface IGLGraphics {
    public GL10 getGL();
    public void setGL(GL10 gl);
    public int getWidth();
    public int getHeight();
	
}
