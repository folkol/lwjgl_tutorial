package com.folkol.tutorial.lwjgl;

import java.util.Arrays;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

public class Timing {

    static final int SMOOOOTHNESS = 100;
    static long since = System.nanoTime();
    static int[] moving_avg_fps = new int[SMOOOOTHNESS];
    private static float rot;
    static
    {
        Arrays.fill(moving_avg_fps, 60);
    }


    public static void main(String[] args) throws LWJGLException {
        Display.create();
        Display.setVSyncEnabled(true);
        while(!Display.isCloseRequested()) {
            long elapsed  = elapsed();
            Display.setTitle(Integer.toString(fps(elapsed)));
            render(elapsed);
            Display.update();
        }
        Display.destroy();
    }

    private static void render(long elapsed) {
        float x = 0.4f;
        float y = 0.4f;

        float delta = elapsed/1000000L;
        rot += delta/20;

        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        GL11.glColor3f(1, 0, 0);
        GL11.glPushMatrix();

        GL11.glTranslatef(x, y, 0);
        GL11.glRotatef(rot, 0, 0, 1);
        GL11.glTranslatef(-x, -y, 0);

        GL11.glBegin(GL11.GL_TRIANGLES);
        GL11.glVertex2f(0, 0);
        GL11.glVertex2f(0.5f, 0);
        GL11.glVertex2f(0.25f, 0.5f);
        GL11.glEnd();

        GL11.glPopMatrix();
    }

    private static long elapsed() {
        long elapsed = System.nanoTime() - since;
        since = System.nanoTime();
        return elapsed;
    }

    private static int fps(long elapsed) {
        int fps = (int) (1000000000L / elapsed);

        shiftRight(moving_avg_fps);
        moving_avg_fps[0] = fps;

        return average(moving_avg_fps);
    }

    private static int average(int[] arr) {
        int average = 0;
        for(int i : arr) {
            average += i;
        }
        return average / arr.length;
    }

    private static void shiftRight(int[] arr) {
        System.arraycopy(arr, 0, arr, 1, arr.length - 1);
    }

}
