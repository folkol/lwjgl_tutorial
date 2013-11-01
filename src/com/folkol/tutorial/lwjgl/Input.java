package com.folkol.tutorial.lwjgl;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

public class Input {

    public static void main(String[] args) throws LWJGLException {
        // Create window and init context
        Display.create();

        quit:
        while(!Display.isCloseRequested()) {
            // Inspect all key events
            while(Keyboard.next()) {
                if(Keyboard.getEventKey() == Keyboard.KEY_ESCAPE) {
                    if(Keyboard.getEventKeyState()) {
                        Display.setTitle("Quitting!");
                    } else {
                        break quit;
                    }
                }
            }

            // Check current state
            if(Keyboard.isKeyDown(Keyboard.KEY_A)) {
                Display.setTitle("lol");
            }

            // Swap buffers
            Display.update();
        }

        // Destroy context
        Display.destroy();
    }

}
