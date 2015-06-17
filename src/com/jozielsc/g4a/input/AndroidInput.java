package com.jozielsc.g4a.input;

import java.util.List;

import com.jozielsc.g4a.interfaces.Input;

import android.content.Context;

import android.view.View;

public class AndroidInput implements Input {    
    
    TouchHandler touchHandler; 

    public AndroidInput(Context context, View view, float scaleX, float scaleY) {
      
            touchHandler = new SingleTouchHandler(view, scaleX, scaleY);
              
    }

    @Override
    public boolean isTouchDown(int pointer) {
        return touchHandler.isTouchDown(pointer);
    }

    @Override
    public int getTouchX(int pointer) {
        return touchHandler.getTouchX(pointer);
    }

    @Override
    public int getTouchY(int pointer) {
        return touchHandler.getTouchY(pointer);
    }
    

    @Override
    public List<TouchEvent> getTouchEvents() {
        return touchHandler.getTouchEvents();
    }
    
}
