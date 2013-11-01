package com.folkol.tutorial.lwjgl;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

public class FrameBufferObject {

    public static void main(String[] args) throws LWJGLException {
        Display.setVSyncEnabled(true);
        Display.create();
        while(!Display.isCloseRequested()) {
            System.out.println(GL11.glGetString(GL11.GL_VERSION));
            Display.update();
        }
        Display.destroy();
    }

}
