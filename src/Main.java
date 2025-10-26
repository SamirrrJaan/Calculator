//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        CalculationMachine calc = new CalculationMachine();
        calc.setEquation("12+(-1)");
        System.out.println(calc.getAnswer());
        MainWindow mainWindow = new MainWindow();
        mainWindow.createInterface();

    }
}