import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonsListener implements ActionListener {
    private CalculationMachine calc = new CalculationMachine();
    private String equation = new String();
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "goToLeft":
                System.out.println("eeeeeeeeeeeeeee");
                break;
            case "=":
                calc.setEquation(equation);

        }
    }
}
