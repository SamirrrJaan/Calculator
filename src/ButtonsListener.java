import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonsListener implements ActionListener {
    private CalculationMachine calc = new CalculationMachine();
    private StringBuilder equation = new StringBuilder();
    private MainWindow mainWindow = Main.getMainWindow();
    private boolean trigonometryStraight = true;
    private int howFar = 0;
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        int caretPos = mainWindow.getCaretPosition();
        switch (command) {
            case "<-":
                mainWindow.moveCaret("Left", 1);
                break;
            case "->":
                mainWindow.moveCaret("Right", 1);
                break;
            case "<X":
                equation.deleteCharAt(caretPos - 1);
                mainWindow.setEquation(equation.toString());
                mainWindow.moveCaret("Left", 2);
                break;
            case "AC":
                equation.delete(0, equation.length());
                mainWindow.setEquation(equation.toString());
                break;
            case "=":
                calc.setEquation(equation.toString());
                mainWindow.setAnswer(calc.getAnswer());
                break;
            case "+":
                equation.insert(caretPos, "+");
                mainWindow.setEquation(equation.toString());
                break;
            case "-":
                equation.insert(caretPos, "-");
                mainWindow.setEquation(equation.toString());
                break;
            case "*":
                equation.insert(caretPos, "*");
                mainWindow.setEquation(equation.toString());
                break;
            case "/":
                equation.insert(caretPos, "/");
                mainWindow.setEquation(equation.toString());
                break;
            case "^":
                equation.insert(caretPos, "^");
                mainWindow.setEquation(equation.toString());
                break;
            case "(":
                equation.insert(caretPos, "(");
                mainWindow.setEquation(equation.toString());
                break;
            case ")":
                equation.insert(caretPos, ")");
                mainWindow.setEquation(equation.toString());
                break;
            case "0":
                equation.insert(caretPos, "0");
                mainWindow.setEquation(equation.toString());
                break;
            case "1":
                equation.insert(caretPos, "1");
                mainWindow.setEquation(equation.toString());
                break;
            case "2":
                equation.insert(caretPos, "2");
                mainWindow.setEquation(equation.toString());
                break;
            case "3":
                equation.insert(caretPos, "3");
                mainWindow.setEquation(equation.toString());
                break;
            case "4":
                equation.insert(caretPos, "4");
                mainWindow.setEquation(equation.toString());
                break;
            case "5":
                equation.insert(caretPos, "5");
                mainWindow.setEquation(equation.toString());
                break;
            case "6":
                equation.insert(caretPos, "6");
                mainWindow.setEquation(equation.toString());
                break;
            case "7":
                equation.insert(caretPos, "7");
                mainWindow.setEquation(equation.toString());
                break;
            case "8":
                equation.insert(caretPos, "8");
                mainWindow.setEquation(equation.toString());
                break;
            case "9":
                equation.insert(caretPos, "9");
                mainWindow.setEquation(equation.toString());
                break;
            case "deg":
                if(mainWindow.getButton(0, 0).getText().equals("deg")) {
                    calc.setAngleSize('r');
                    mainWindow.getButton(0, 0).setText("rad");
                }
                else {
                    calc.setAngleSize('d');
                    mainWindow.getButton(0, 0).setText("deg");
                }
                break;
            case "str":
                if(mainWindow.getButton(0, 1).getText().equals("str")) {
                    trigonometryStraight = false;
                    mainWindow.getButton(0, 1).setText("arc");
                }
                else {
                    trigonometryStraight = true;
                    mainWindow.getButton(0, 1).setText("str");
                }
                break;
            case "sin":
                if(trigonometryStraight) {
                    equation.insert(caretPos, "sin()");
                    howFar = 3;
                }
                else {
                    equation.insert(caretPos, "arcsin()");
                    howFar = 6;
                }
                mainWindow.setEquation(equation.toString());
                mainWindow.moveCaret("Right", howFar);
                break;
            case "cos":
                if(trigonometryStraight) {
                    equation.insert(caretPos, "cos()");
                    howFar = 3;
                }
                else {
                    equation.insert(caretPos, "arccos()");
                    howFar = 6;
                }
                mainWindow.setEquation(equation.toString());
                mainWindow.moveCaret("Right", howFar);
                break;
            case "tg":
                if(trigonometryStraight) {
                    equation.insert(caretPos, "tg()");
                    howFar = 2;
                }
                else {
                    equation.insert(caretPos, "arctg()");
                    howFar = 5;
                }
                mainWindow.setEquation(equation.toString());
                mainWindow.moveCaret("Right", howFar);
                break;
            case "ctg":
                if(trigonometryStraight) {
                    equation.insert(caretPos, "ctg()");
                    howFar = 3;
                }
                else {
                    equation.insert(caretPos, "arctg()");
                    howFar = 6;
                }
                mainWindow.setEquation(equation.toString());
                mainWindow.moveCaret("Right", howFar);
                break;
            case "log":
                equation.insert(caretPos, "log[]()");
                mainWindow.setEquation(equation.toString());
                mainWindow.moveCaret("Right", 3);
                break;
            case "ln":
                equation.insert(caretPos, "ln()");
                mainWindow.setEquation(equation.toString());
                mainWindow.moveCaret("Right", 2);
                break;
            case "lg":
                equation.insert(caretPos, "lg()");
                mainWindow.setEquation(equation.toString());
                mainWindow.moveCaret("Right", 2);
                break;
            case "sqrt":
                equation.insert(caretPos, "sqrt()");
                mainWindow.setEquation(equation.toString());
                mainWindow.moveCaret("Right", 4);
                break;
            case "root":
                equation.insert(caretPos, "root[]()");
                mainWindow.setEquation(equation.toString());
                mainWindow.moveCaret("Right", 4);
                break;
            case "10^":
                equation.insert(caretPos, "10^()");
                mainWindow.setEquation(equation.toString());
                mainWindow.moveCaret("Right", 3);
                break;
            case ".":
                equation.insert(caretPos, ".");
                mainWindow.setEquation(equation.toString());
                break;
            case "Pi":
                equation.insert(caretPos, "π");
                mainWindow.setEquation(equation.toString());
                break;
            case "e":
                equation.insert(caretPos, "e");
                mainWindow.setEquation(equation.toString());
                break;
            case "|":
                equation.insert(caretPos, "|");
                mainWindow.setEquation(equation.toString());
                break;
            case "?":
                mainWindow.openInfoWindow();
                break;
        }
    }

}
