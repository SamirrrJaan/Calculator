import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

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
            top.setLayout(new GridLayout(1,2,0,10));
            JTextField equationField = new JTextField(10);
            JTextField equationField1 = new JTextField(10);
            top.add(equationField);
            top.add(equationField1);
            equationField.setFont(new Font("Arial", 0 , 70));

        JPanel half = new JPanel();
            half.setLayout(new GridLayout(1,2,0,0));
            JButton[] halfButtons = new JButton[2];
            halfButtons[0] = new JButton("<-");
            halfButtons[0].setActionCommand("goToLeft");
            halfButtons[1] = new JButton("->");
            halfButtons[1].setActionCommand("goToRight");
            half.add(halfButtons[0]);
            half.add(halfButtons[1]);

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
        ActionListener buttonListener = new ButtonsListener();
        halfButtons[0].addActionListener(buttonListener);
        halfButtons[1].addActionListener(buttonListener);
        main.add(top);
        main.add(medium);
        main.add(bottom);
        frame.getContentPane().add(main);
        frame.setVisible(true);
    }



}
