package com.jozielsc.g4a.graphics;

import javax.microedition.khronos.opengles.GL10;

import com.jozielsc.g4a.interfaces.IGLGraphics;

import android.opengl.GLSurfaceView;

public class GLGraphics implements IGLGraphics {
    GLSurfaceView glView;
    GL10 gl;
    
    public GLGraphics(GLSurfaceView glView) {
        this.glView = glView;
    }
    
    public GL10 getGL() {
        return gl;
    }
    
    public void setGL(GL10 gl) {
        this.gl = gl;
    }
    
    public int getWidth() {
        return glView.getWidth();
    }
    
    public int getHeight() {
        return glView.getHeight();
    }
}
