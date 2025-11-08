import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class MainWindow {
    private int windowHeight;
    private int windowWidth;
    private JTextField equationField = new JTextField(10);
    private JTextField answerField = new JTextField(10);
    private JButton equalsButton = new JButton();
    private Font actionsFont = new Font("Arial", 0 , 20);
    private Font fieldsFont = new Font("Arial", 0 , 20);

    private JPanel[] buttonRows = new JPanel[5];
    private JButton[][] buttons = new JButton[5][8];
    private String[][] buttonNames = {
            {"deg", "str", "?", "set", "AC",   "<-", "->", "<X"},
            {"sin",  "+",  "-",  "*",   "/",    "1",  "2",   "3"},
            {"cos",  "^", "sqrt","root","10^",  "4",  "5",   "6"},
            {"tg",  "log", "lg", "ln",  "|",    "7",  "8",   "9"},
            {"ctg",  "Pi",  "e", "x!",  ".",    "(",  "0",   ")"}
    };
    private GridLayout buttonsLayout = new GridLayout(1,8,0,0);
    private JFrame infoWindow = new JFrame();

    public MainWindow() {
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        windowWidth = gd.getDisplayMode().getWidth() / 2;
        windowHeight = gd.getDisplayMode().getHeight() / 2;
        windowWidth = 1280;
        windowHeight = gd.getDisplayMode().getHeight() / 2;
        if(windowWidth < 640) {
            windowWidth = 640;
        }
        if(windowHeight < 640) {
            windowHeight = 640;
        }
        createInfoWindow();
    }

    public void createInterface() {
        JFrame frame = new JFrame("CalculatorSJ");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(windowWidth, windowHeight);
            frame.setMinimumSize(new Dimension(640, 480));
            frame.setLocationRelativeTo(null);
            frame.addComponentListener(
                new ComponentAdapter() {
                    public void componentResized(ComponentEvent e) {
                        windowHeight = frame.getHeight();
                        windowWidth = frame.getWidth();
                        int size = Math.toIntExact(Math.round(0.084*windowWidth-37));
                        System.out.println(size);
                        if(size > 73) {
                            size = 73;
                        }
                        if(size < 17) {
                            size = 17;
                        }
                        actionsFont = new Font("Arial", Font.PLAIN, size);
                        fieldsFont = new Font("Arial", Font.PLAIN, size-30);
                        for(int i = 0; i < 5; i++) {
                            for(int j = 0; j < 8; j++){
                                buttons[i][j].setFont(actionsFont);
                            }
                        }
                        equationField.setFont(fieldsFont);
                        answerField.setFont(fieldsFont);
                        equalsButton.setFont(fieldsFont);

                    }

                }
            );

        JPanel main = new JPanel();
            GridLayout mainLayout = new GridLayout(6,1,0,0);
            main.setLayout(mainLayout);

        JPanel row1 = new JPanel();
            GridLayout row1Layout = new GridLayout(1,2,0,0);
            row1.setLayout(row1Layout);
            row1.add(equationField);
            JPanel subRow1 = new JPanel();
            subRow1.setLayout(row1Layout);
            subRow1.add(answerField);
            JButton equalsButton = new JButton("=");
            equalsButton.setActionCommand("=");
            //equalsButton.setBorder(Border );???????????????????????????
            subRow1.add(equalsButton);
            row1.add(subRow1);

        for(int i = 0; i < 5; i++) {
            buttonRows[i] = new JPanel();
            buttonRows[i].setLayout(buttonsLayout);
            for(int j = 0; j < 8; j++) {
                buttons[i][j] = new JButton(buttonNames[i][j]);
                buttons[i][j].setActionCommand(buttonNames[i][j]);
                buttonRows[i].add(buttons[i][j]);
            }
        }

        ActionListener buttonListener = new ButtonsListener();
        equalsButton.addActionListener(buttonListener);
        for(int i = 0; i < 5; i++) {
            for(int j = 0; j < 8; j++) {
                buttons[i][j].addActionListener(buttonListener);
            }
        }
        main.add(row1);
        for(int i = 0; i < 5; i++) {
            main.add(buttonRows[i]);
        }
        frame.getContentPane().add(main);
        frame.setVisible(true);
    }

    public void createInfoWindow()  {
        infoWindow.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
        infoWindow.setSize(640, 480);
        JTextArea info = new JTextArea("Информация/Information");
        info.setFont(actionsFont);
        String everything = null;
        try {
            everything = readFile("src/Texts/infoText.txt");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        info.setText(everything);
        JPanel infoPanel = new JPanel();
        infoPanel.add(info);
        JScrollPane infoPane = new JScrollPane(infoPanel);
        infoPane.getVerticalScrollBar().setUnitIncrement(16);
        infoWindow.add(infoPane);
    }

    public String readFile(String filename) throws IOException
    {
        String content = null;
        File file = new File(filename); // For example, foo.txt
        FileReader reader = null;
        try {
            reader = new FileReader(file);
            char[] chars = new char[(int) file.length()];
            reader.read(chars);
            content = new String(chars);
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(reader != null){
                reader.close();
            }
        }
        return content;
    }

    public void setAnswer(String answer) {
        answerField.setText(answer);
        answerField.requestFocusInWindow();
    }

    public void setEquation(String equation) {
        int caretPos = equationField.getCaretPosition();
        equationField.setText(equation);
        equationField.requestFocusInWindow();
        equationField.setCaretPosition(caretPos + 1);
    }

    public void setEquationAfterDelete(String equation) {
        int caretPos = equationField.getCaretPosition();
        equationField.setText(equation);
        equationField.requestFocusInWindow();
        equationField.setCaretPosition(caretPos);
    }

    public void moveCaret(String where, int howFar) {
        equationField.requestFocusInWindow();
        if(where.equals("Right")) {
            equationField.setCaretPosition(equationField.getCaretPosition() + howFar);
        }
        else {
            equationField.setCaretPosition(equationField.getCaretPosition() - howFar);
        }
    }


    public void openInfoWindow() {
        infoWindow.setVisible(true);
    }

    public int getCaretPosition() {
        return equationField.getCaretPosition();
    }

    public JButton getButton(int i, int j) {
         return buttons[i][j];
    }
}
