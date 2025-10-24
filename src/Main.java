//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        CalculationMachine calc = new CalculationMachine();
        calc.setEquation("25+12^(2-1) ");
        System.out.println(calc.getAnswer());

    }
}