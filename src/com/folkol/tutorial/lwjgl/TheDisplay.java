package com.folkol.tutorial.lwjgl;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

public class TheDisplay {
    public static void main(String[] argv) throws LWJGLException {
        DisplayMode[] availableDisplayModes = Display.getAvailableDisplayModes();
        Display.setFullscreen(false);
        for (DisplayMode dm : availableDisplayModes) {
            System.out.println(dm);
            Display.setDisplayMode(dm);
            Display.create();
            while (!Display.isCloseRequested()) {
                // Render something
                Display.update();
            }
            Display.destroy();
        }
    }
}
