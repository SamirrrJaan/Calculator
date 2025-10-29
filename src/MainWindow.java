import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MainWindow {
    private int windowHeight;
    private int windowWidth;
    private JTextField equationField = new JTextField(10);
    private JTextField answerField = new JTextField(10);
    private Font myFont = new Font("Arial", 0 , 70);
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
            top.add(equationField);
            top.add(answerField);
            JButton equalsButton = new JButton("=");
            equalsButton.setActionCommand("=");
            top.add(equalsButton);
            equationField.setFont(myFont);
            answerField.setFont(myFont);

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
            mediumButtons[0] = new JButton("degrees");
                mediumButtons[0].setActionCommand("degrees");
            mediumButtons[1] = new JButton("straight");
                mediumButtons[1].setActionCommand("straight");
            mediumButtons[2] = new JButton("empty");
                mediumButtons[2].setActionCommand("empty");
            mediumButtons[3] = new JButton("<[x]");
                mediumButtons[3].setActionCommand("<[x]");
            mediumButtons[4] = new JButton("log");
                mediumButtons[4].setActionCommand("log");
            mediumButtons[5] = new JButton("1");
                mediumButtons[5].setActionCommand("1");
            mediumButtons[6] = new JButton("2");
                mediumButtons[6].setActionCommand("2");
            mediumButtons[7] = new JButton("3");
                mediumButtons[7].setActionCommand("3");
            mediumButtons[8] = new JButton("+");
                mediumButtons[8].setActionCommand("+");
            mediumButtons[9] = new JButton("-");
                mediumButtons[9].setActionCommand("-");
            mediumButtons[10] = new JButton("*");
                mediumButtons[10].setActionCommand("*");
            mediumButtons[11] = new JButton("/");
                mediumButtons[11].setActionCommand("/");
            mediumButtons[12] = new JButton("ln");
                mediumButtons[12].setActionCommand("ln");
            mediumButtons[13] = new JButton("4");
                mediumButtons[13].setActionCommand("4");
            mediumButtons[14] = new JButton("5");
                mediumButtons[14].setActionCommand("5");
            mediumButtons[15] = new JButton("6");
                mediumButtons[15].setActionCommand("6");
            for(int i = 0; i < 16; i++) {
                if(i == 2) {
                    medium.add(half);
                }
                else {
                    medium.add(mediumButtons[i]);
                }
            }




        JPanel bottom = new JPanel();
            bottom.setLayout(new GridLayout(2,8,0,0));
            JButton[] bottomButtons = new JButton[16];
            bottomButtons[0] = new JButton("-");
                bottomButtons[0].setActionCommand("-");
            bottomButtons[1] = new JButton("sqrt");
                bottomButtons[1].setActionCommand("sqrt");
            bottomButtons[2] = new JButton("^");
                bottomButtons[2].setActionCommand("^");
            bottomButtons[3] = new JButton("-");
                bottomButtons[3].setActionCommand("-");
            bottomButtons[4] = new JButton("Pi");
                bottomButtons[4].setActionCommand("Pi");
            bottomButtons[5] = new JButton("7");
                bottomButtons[5].setActionCommand("7");
            bottomButtons[6] = new JButton("8");
                bottomButtons[6].setActionCommand("8");
            bottomButtons[7] = new JButton("9");
                bottomButtons[7].setActionCommand("9");
            bottomButtons[8] = new JButton("tg");
                bottomButtons[8].setActionCommand("tg");
            bottomButtons[9] = new JButton("ctg");
                bottomButtons[9].setActionCommand("ctg");
            bottomButtons[10] = new JButton("sin");
                bottomButtons[10].setActionCommand("sin");
            bottomButtons[11] = new JButton("cos");
                bottomButtons[11].setActionCommand("cos");
            bottomButtons[12] = new JButton("e");
                bottomButtons[12].setActionCommand("e");
            bottomButtons[13] = new JButton("(");
                bottomButtons[13].setActionCommand("(");
            bottomButtons[14] = new JButton("0");
                bottomButtons[14].setActionCommand("0");
            bottomButtons[15] = new JButton(")");
                bottomButtons[15].setActionCommand(")");
            for(int i = 0; i < 16; i++) {
                bottom.add(bottomButtons[i]);
            }


        ActionListener buttonListener = new ButtonsListener();
        for(int i = 0; i < 16; i++) {
            bottomButtons[i].setFont(myFont);
            bottomButtons[i].addActionListener(buttonListener);
        }
        for(int i = 0; i < 16; i++) {
            mediumButtons[i].setFont(myFont);
            mediumButtons[i].addActionListener(buttonListener);
        }
        for(int i = 0; i < 2; i++) {
            halfButtons[i].setFont(myFont);
            halfButtons[i].addActionListener(buttonListener);
        }
        equalsButton.addActionListener(buttonListener);
        main.add(top);
        main.add(medium);
        main.add(bottom);
        frame.getContentPane().add(main);
        frame.setVisible(true);
    }

    public void setAnswer(String answer) {
        answerField.requestFocusInWindow();
        answerField.setText(answer);
    }

    public void setEquation(String equation, String action) {
        // action "d" - delete, a - others
        int caretPosition = equationField.getCaretPosition() + 1;
        if(action.equals("a")) {
            equationField.setText(equation);
            equationField.requestFocusInWindow();
            equationField.setCaretPosition(caretPosition);
        }
        else {
            equationField.setText(equation);
            equationField.requestFocusInWindow();
            equationField.setCaretPosition(caretPosition - 2);
        }
    }

    public void moveCaret(String where) {
        equationField.requestFocusInWindow();
        if(where.equals("Right")) {
            equationField.setCaretPosition(equationField.getCaretPosition() + 1);
        }
        else {
            equationField.setCaretPosition(equationField.getCaretPosition() - 1);
        }
    }

    public int getCaretPosition() {
        return equationField.getCaretPosition();
    }
}
