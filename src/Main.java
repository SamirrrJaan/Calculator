//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    private static MainWindow mainWindow = new MainWindow();

    public static void main(String[] args) {
        mainWindow.createInterface();
    }

    public static MainWindow getMainWindow() {
        return mainWindow;
    }

}


//V Разобраться почему во второй раз равно не работает
//Добавить кнопку "удалить всё"
//V Сделать обнуление переменных в calcMachine
//V Сделать перезапись переменных
//Разобраться со шрифтами
//Решить ситуацию 45(12+1)