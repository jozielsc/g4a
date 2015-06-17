package com.jozielsc.g4a.game;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import com.jozielsc.g4a.audio.AndroidAudio;
import com.jozielsc.g4a.file.AndroidFileIO;
import com.jozielsc.g4a.graphics.GLGraphics;
import com.jozielsc.g4a.input.AndroidInput;
import com.jozielsc.g4a.interfaces.Audio;
import com.jozielsc.g4a.interfaces.FileIO;
import com.jozielsc.g4a.interfaces.Game;
import com.jozielsc.g4a.interfaces.IGLGraphics;
import com.jozielsc.g4a.interfaces.Input;
import com.jozielsc.g4a.interfaces.Screen;

import android.app.Activity;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.opengl.GLSurfaceView.Renderer;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.view.Window;
import android.view.WindowManager;


public abstract class GLGame extends Activity implements Game, Renderer {
    public final String TAG = getClass().getSimpleName();
	enum GLGameState {
        Initialized,
        Running,
        Paused,
        Finished,
        Idle
    }
	
	GLSurfaceView glView;    
	IGLGraphics glGraphics;
    Audio audio;
    Input input;
    FileIO fileIO;
    Screen screen;
    GLGameState state = GLGameState.Initialized;
    Object stateChanged = new Object();
    long startTime = System.nanoTime();
    WakeLock wakeLock;
    
    @Override 
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                             WindowManager.LayoutParams.FLAG_FULLSCREEN);
        glView = new GLSurfaceView(this);
        glView.setRenderer(this);
        setContentView(glView);
        
        glGraphics = new GLGraphics(glView);
        fileIO = new AndroidFileIO(this);
        audio = new AndroidAudio(this);
        input = new AndroidInput(this, glView, 1, 1);
        PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK, "GLGame");        
    }
    
    public void onResume() {
        super.onResume();
        glView.onResume();
        wakeLock.acquire();
    }
    
    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {        
    	
    	glGraphics.setGL(gl);
        
        synchronized(stateChanged) {
            if(state == GLGameState.Initialized)
                screen = getStartScreen();
            state = GLGameState.Running;
            screen.resume();
            startTime = System.nanoTime();
        }        
    }
    
    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {  
    	
    }

    @Override
    public void onDrawFrame(GL10 gl) {
    	
        GLGameState state = null;
        
        synchronized(stateChanged) {
            state = this.state;
        }
        
        if(state == GLGameState.Running) {
            float deltaTime = (System.nanoTime()-startTime) / 1000000000.0f;
            startTime = System.nanoTime();
            
            screen.update(deltaTime);
            screen.renderer(deltaTime);
        }
        
        if(state == GLGameState.Paused) {
            screen.pause();            
            synchronized(stateChanged) {
                this.state = GLGameState.Idle;
                stateChanged.notifyAll();
            }
        }
        
        if(state == GLGameState.Finished) {
            screen.pause();
            screen.dispose();
            synchronized(stateChanged) {
                this.state = GLGameState.Idle;
                stateChanged.notifyAll();
            }            
        }
    }   
    
    
    @Override 
    public void onPause() {
    	
        synchronized(stateChanged) {
            if(isFinishing())            
                state = GLGameState.Finished;
            else
                state = GLGameState.Paused;
            while(true) {
                try {
                    stateChanged.wait();
                    break;
                } catch(InterruptedException e) {
                }
            }
        }
        wakeLock.release();
        glView.onPause();  
        super.onPause();
    }    
    @Override
    public IGLGraphics getGLGraphics() {
        return glGraphics;
    }  
    
    @Override
    public Input getInput() {
        return input;
    }

    @Override
    public FileIO getFileIO() {
        return fileIO;
    }

    @Override
    public Audio getAudio() {
        return audio;
    }

    @Override
    public void setScreen(Screen screen) {
    	
        if (screen == null)
            throw new IllegalArgumentException("Screen não pode ser null");

        this.screen.pause();
        this.screen.dispose();
        screen.resume();
        screen.update(0);
        this.screen = screen;
    }

    @Override
    public Screen getCurrentScreen() {
        return screen;
    }

	@Override
	public Screen getStartScreen() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
}
