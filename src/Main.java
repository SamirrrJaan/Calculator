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


//Разобраться почему во второй раз равно не работает
//Добавить кнопку "удалить всё"
//Сделать обнуление переменных в calcMachine
//Сделать перезапись переменных
//Разобраться со шрифтами