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
        // задаём минимальный размер окна
        frame.setMinimumSize(new Dimension(640, 480));
        frame.setLocationRelativeTo(null);
        JPanel panel = new JPanel();
        JPanel[] emptyPanels = new JPanel[11];
        for (int i = 0; i < 11; i++) {
            emptyPanels[i] = new JPanel();
        }
        JButton[] buttons = new JButton[24]; // под 6 кнопок
        // Три ряда, по две кнопки в каждом горизонтальный промежуток - 50px, вертикальный - 30px
        GridLayout gridLayout = new GridLayout(5, 7, 0, 0);
        panel.setLayout(gridLayout);
        for (int i = 0; i < 11; i++) { // сделаем кнопочную панель из 6-и кнопок
            panel.add(emptyPanels[i]);
        }
        for (int i = 0; i < 24; i++) { // сделаем кнопочную панель из 6-и кнопок
            buttons[i] = new JButton("" + (i + 1));
            panel.add(buttons[i]);
        }

        frame.add(panel);
        frame.setVisible(true);
    }
}
