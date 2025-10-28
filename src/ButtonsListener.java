import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonsListener implements ActionListener {
    private CalculationMachine calc = new CalculationMachine();
    private StringBuilder equation = new StringBuilder();
    private MainWindow mainWindow = Main.getMainWindow();

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "goToLeft":
                mainWindow.moveCaret("Left");
                break;
            case "goToRight":
                mainWindow.moveCaret("Right");
                break;
            case "=":
                calc.setEquation(equation.toString());
                mainWindow.setAnswer(calc.getAnswer());
                break;
            case "+":
                equation.insert(mainWindow.getCaretPosition(), "+");
                mainWindow.setEquation(equation.toString(), "a");
                break;
            case "-":
                equation.insert(mainWindow.getCaretPosition(), "-");
                mainWindow.setEquation(equation.toString(), "a");
                break;
            case "*":
                equation.insert(mainWindow.getCaretPosition(), "*");
                mainWindow.setEquation(equation.toString(), "a");
                break;
            case "/":
                equation.insert(mainWindow.getCaretPosition(), "/");
                mainWindow.setEquation(equation.toString(), "a");
                break;
            case "^":
                equation.insert(mainWindow.getCaretPosition(), "^");
                mainWindow.setEquation(equation.toString(), "a");
                break;
            case "(":
                equation.insert(mainWindow.getCaretPosition(), "(");
                mainWindow.setEquation(equation.toString(), "a");
                break;
            case ")":
                equation.insert(mainWindow.getCaretPosition(), ")");
                mainWindow.setEquation(equation.toString(), "a");
                break;
            case "0":
                equation.insert(mainWindow.getCaretPosition(), "0");
                mainWindow.setEquation(equation.toString(), "a");
                break;
            case "1":
                equation.insert(mainWindow.getCaretPosition(), "1");
                mainWindow.setEquation(equation.toString(), "a");
                break;
            case "2":
                equation.insert(mainWindow.getCaretPosition(), "2");
                mainWindow.setEquation(equation.toString(), "a");
                break;
            case "3":
                equation.insert(mainWindow.getCaretPosition(), "3");
                mainWindow.setEquation(equation.toString(), "a");
                break;
            case "4":
                equation.insert(mainWindow.getCaretPosition(), "4");
                mainWindow.setEquation(equation.toString(), "a");
                break;
            case "5":
                equation.insert(mainWindow.getCaretPosition(), "5");
                mainWindow.setEquation(equation.toString(), "a");
                break;
            case "6":
                equation.insert(mainWindow.getCaretPosition(), "6");
                mainWindow.setEquation(equation.toString(), "a");
                break;
            case "7":
                equation.insert(mainWindow.getCaretPosition(), "7");
                mainWindow.setEquation(equation.toString(), "a");
                break;
            case "8":
                equation.insert(mainWindow.getCaretPosition(), "8");
                mainWindow.setEquation(equation.toString(), "a");
                break;
            case "9":
                equation.insert(mainWindow.getCaretPosition(), "9");
                mainWindow.setEquation(equation.toString(), "a");
                break;
            case "<[x]":
                equation.deleteCharAt(mainWindow.getCaretPosition() - 1);
                mainWindow.setEquation(equation.toString(), "d");
                break;
        }
    }

}
