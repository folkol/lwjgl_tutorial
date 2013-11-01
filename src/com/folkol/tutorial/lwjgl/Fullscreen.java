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

public class Fullscreen {

    static DisplayMode dm = new DisplayMode(400, 300);
    static boolean fullscreen;

    public static void main(String[] args) throws LWJGLException {
        JFrame window = createDialog();

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

            Display.update();
        }
        Display.destroy();

        window.dispatchEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSING));
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
