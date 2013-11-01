package com.folkol.tutorial.lwjgl;

import java.awt.BorderLayout;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

public class Fullscreen {

    static DisplayMode dm = new DisplayMode(400, 300);
    static boolean fullscreen;
    private static float rot;

    public static void main(String[] args) throws LWJGLException {
        JFrame window = createDialog();

        Display.setVSyncEnabled(true);
        Display.create();
        while (!Display.isCloseRequested()) {
            if(!Display.getDisplayMode().equals(dm) || Display.isFullscreen() != fullscreen) {
                Display.destroy();
                Display.setDisplayMode(dm);
                Display.setFullscreen(fullscreen);
                Display.create();
            }

            if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
                if(!fullscreen)
                    break;
                fullscreen = false;
            }

            if(Keyboard.isKeyDown(Keyboard.KEY_F))
                fullscreen = true;

            render();

            Display.update();
        }
        Display.destroy();

        window.dispatchEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSING));
    }

    private static void render() {
        rot++;

        GL11.glClearColor(0.2f, 0.2f, 0.2f, 1);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

        GL11.glPushMatrix();
        GL11.glRotatef(rot, 0, 0, 1);
        GL11.glBegin(GL11.GL_TRIANGLES);
        GL11.glColor3f(1, 0, 0);
        GL11.glVertex2f(0, 0);
        GL11.glVertex2f(100, 0);
        GL11.glVertex2f(50, 100);
        GL11.glEnd();
        GL11.glPopMatrix();
    }

    private static JFrame createDialog() throws LWJGLException {
        final JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        DisplayMode[] availableDisplayModes = Display.getAvailableDisplayModes();
        final JList<DisplayMode> modeList = new JList<DisplayMode>(availableDisplayModes);
        modeList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Fullscreen.dm = modeList.getSelectedValuesList().get(0);
                    }
                }).start();
            }
        });

        JPanel topPanel = new JPanel(new BorderLayout());
        frame.setContentPane(topPanel);
        topPanel.add(modeList, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);

        return frame;
    }

}
