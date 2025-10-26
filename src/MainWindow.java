import javax.swing.*;
import java.awt.*;

public class MainWindow {
    private int windowHeight;
    private int windowWidth;

    public MainWindow() {
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        windowWidth = gd.getDisplayMode().getWidth() / 2;
        windowHeight = gd.getDisplayMode().getHeight() / 2;
        if(windowWidth < 640) {
            windowWidth = 640;
        }
        if(windowHeight < 640) {
            windowHeight = 640;
        }
    }

    public void createInterface() {
        JFrame frame = new JFrame("GridLayOut");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(windowWidth, windowHeight);
            frame.setMinimumSize(new Dimension(640, 480));
            frame.setLocationRelativeTo(null);

        JPanel main = new JPanel();
            BoxLayout mainLayout = new BoxLayout(main, BoxLayout.Y_AXIS);
            main.setLayout(mainLayout);

        JPanel top = new JPanel();
            top.setLayout(new GridLayout(1,2,0,0));
            JButton[] topButtons = new JButton[2];
            for(int i = 0; i < 2; i++) {
                topButtons[i] = new JButton("TOP" + i);
                top.add(topButtons[i]);
            }

        JPanel half = new JPanel();
            half.setLayout(new GridLayout(1,2,0,0));
            JButton[] halfButtons = new JButton[2];
            for(int i = 0; i < 2; i++) {
                halfButtons[i] = new JButton("HALF" + i);
                half.add(halfButtons[i]);
            }

        JPanel medium = new JPanel();
            medium.setLayout(new GridLayout(2,8,0,0));
            JButton[] mediumButtons = new JButton[16];
            for(int i = 0; i < 16; i++) {
                if(i == 2) {
                    medium.add(half);
                }
                else {
                    mediumButtons[i] = new JButton("MEDIUM" + i);
                    medium.add(mediumButtons[i]);
                }
            }

        JPanel bottom = new JPanel();
            bottom.setLayout(new GridLayout(2,8,0,0));
            JButton[] bottomButtons = new JButton[16];
            for(int i = 0; i < 16; i++) {
                bottomButtons[i] = new JButton("BOTTOM" + i);
                bottom.add(bottomButtons[i]);
            }

        main.add(top);
        main.add(medium);
        main.add(bottom);
        frame.getContentPane().add(main);
        frame.setVisible(true);
    }
}
