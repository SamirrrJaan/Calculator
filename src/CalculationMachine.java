import java.util.Arrays;

public class CalculationMachine {
    private String equation;
    private StringBuilder equationSimplified = new StringBuilder();
    private String[] numbersArray = new String[100];
    private final char[] digitsArray = {'0','1','2','3','4','5','6','7','8','9','.'};
    private final char[] actionsArray = {'^','*','/','+','-'};
    private char[] variables = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
    private int varId = 0;

    public String getAnswer() {
        String result = "Errors: not correct equation";
        double resultD = 0;
        int bracketOpenCount = 0;
        int bracketCloseCount = 0;
        int bracketCount = 0;
        boolean noErrors = true;
        // Проверка выражения на правильный синтаксис
        for(int i = 0; i < equation.length(); i++) {
            if(equation.charAt(i) == '(') {
                bracketOpenCount++;
                bracketCount++;
            }
            if(equation.charAt(i) == ')') {
                bracketCloseCount++;
                bracketCount++;
            }
        }
        // Если нечётное кол-во скобок
        if(bracketCount%2 != 0) {
            noErrors = false;
            error("oddBracket");
        }
        // Если количество "(" и ")" не равно
        else if(bracketOpenCount != bracketCloseCount) {
            noErrors = false;
            error("notSameBracketCount");
        }
        // Если подряд два знака действия
        else {
            for(int i = 0; i < equation.length() - 1; i++) {
                for(int j = 0; j < actionsArray.length; j++) {
                    if(equation.charAt(i) == actionsArray[j]) {
                        for(int k = 0; k < actionsArray.length; k++) {
                            if(equation.charAt(i+1) == actionsArray[k]) {
                                noErrors = false;
                                error("actionByAction");
                            }
                        }
                    }
                }
            }
        }
        //Тут неведомо сложный механизм замены чисел на переменные
        if(noErrors) {
            boolean firstDigit = true;
            boolean digitWasFound = true;
            boolean digitWasFound4 = true;
            int firstDigitId = 0;
            int lastDigitId = 0;
            equationSimplified.append(equation);
            System.out.println(equationSimplified);
            while(digitWasFound4) {
                for (int i = 0; i < equationSimplified.length(); i++) {
                    digitWasFound = false;
                    for (int j = 0; j < digitsArray.length; j++) {
                        if(equationSimplified.charAt(i) == digitsArray[j]) {
                            digitWasFound = true;
                            if(firstDigit) {
                                firstDigitId = i;
                                lastDigitId = i;
                                firstDigit = false;
                                break;
                            }
                            else {
                                lastDigitId++;
                                break;
                            }
                        }

                    }
                    if(!firstDigit && !digitWasFound) {
                        numbersArray[varId] = equationSimplified.substring(firstDigitId, lastDigitId + 1);
                        equationSimplified.delete(firstDigitId, lastDigitId + 1);
                        equationSimplified.insert(firstDigitId, variables[varId]);
                        varId++;
                        firstDigit = true;
                        firstDigitId = 0;
                        lastDigitId = 0;
                        break;
                    }
                }
                System.out.println(equationSimplified);
                System.out.println(Arrays.toString(numbersArray));
                digitWasFound4 = false;
                for (int i = 0; i < equationSimplified.length(); i++) {
                    for (int j = 0; j < digitsArray.length; j++) {
                        if(equationSimplified.charAt(i) == digitsArray[j]) {
                            digitWasFound4 = true;
                            break;
                        }
                    }
                }
            }
        }
        //Механизм такой:
        //1.Считаем количество скобок.
        //2.Делаем по порядку сначала самые вложенные скобки, а в скобках по порядку действия
        for(int z = 0; z < bracketCount/2 + 1; z++) {
            boolean bracketWasFound = false; //Была ли найдена скобка
            int bracketPower = 0; //Степень вложенности скобки
            int bracketPowerMax = 0; //МАХ значение вложенности
            int bracketPowerMaxId = 0;//Индекс МАХ скобки
            int bracketPowerMaxCloseId = 0;//Индекс МАХ закрытой скобки
            for(int i = 0; i < equationSimplified.length(); i++) { //Ищем первую максимальную открытую скобку
                if(equationSimplified.charAt(i) == '(') {
                    bracketPower++;
                    bracketWasFound = true;
                    if(bracketPower > bracketPowerMax) {
                        bracketPowerMax = bracketPower;
                        bracketPowerMaxId = i;
                    }
                }
                else if(equationSimplified.charAt(i) == ')') {
                    bracketPower--;
                }
            }
            for(int i = bracketPowerMaxId; i < equationSimplified.length(); i++) { //Ищем первую максимальную закрытую скобку
                if(equationSimplified.charAt(i) == ')') {
                    bracketPowerMaxCloseId = i;
                    break;
                }
            }
            if(!bracketWasFound) { //Случай если скобок нет, притворяемся, что они есть по бокам.
                bracketPowerMaxId = -1;
                bracketPowerMaxCloseId = equationSimplified.length();
            }
            //Вводим новую строку - подстроку, в которой всё то, что в самой вложенной скобке
            StringBuilder subEquation = new StringBuilder(equationSimplified.substring(bracketPowerMaxId + 1, bracketPowerMaxCloseId));
            System.out.println(subEquation);
            int operatorsCount = 0; //Дальше проводим счёт сколько в этой строке операторов
            // (в планах тригонометрические функции обозвать какими-нибудь символами)
            for(int i = 0; i < subEquation.length(); i++) {
                for(int j = 0; j < actionsArray.length; j++) {
                    if(subEquation.charAt(i) == actionsArray[j]) {
                        operatorsCount++;
                    }
                }
            }
            //private final char[] actionsArray = {'^','*','/','+','-'}; Дальше ищем оператор, который нужно выполнить первым,
            // порядок выполнения в ^^^^^^^^^^
            for(int x = 0; x < operatorsCount; x++) {
                int operationFirst = 0; //Индекс в subEqution = индексу первого по выполнению оператора
                boolean stopSearching = false; //Логика для того, чтобы не проверять уже точно не первые операторы
                for(int i = 0; i < actionsArray.length; i++) {
                    if(!stopSearching) {
                        for(int j = 0; j < subEquation.length(); j++) {
                            if(actionsArray[i] == subEquation.charAt(j)) {
                                operationFirst = j;
                                stopSearching = true;
                                break;
                            }
                        }
                    }
                    else {
                        break;
                    }
                }
                //отправляем в mathAction оператора и элементы сбоку от него(два числа)
                String str = mathAction(subEquation.charAt(operationFirst), subEquation.charAt(operationFirst - 1),
                        subEquation.charAt(operationFirst + 1));
                System.out.println(str);
                numbersArray[varId] = str; //Вводим новую переменную и соот.ей элемент в массиве numbersArray
                subEquation.delete((operationFirst - 1), (operationFirst + 2));//Заменяем в подстроке то, что мы посчитали на новую переменную
                subEquation.insert(operationFirst - 1, variables[varId]);
                System.out.println(numbersArray[varId]);
                varId++;
                System.out.println(subEquation);
            }
            //Случай, если скобок нет
            if(bracketWasFound) {
                equationSimplified.delete(bracketPowerMaxId, bracketPowerMaxCloseId + 1);
                equationSimplified.insert(bracketPowerMaxId, subEquation);
            }
            else {
                equationSimplified.delete(0, equationSimplified.length());
                equationSimplified.insert(0, subEquation);
            }
            System.out.println(equationSimplified);
        }
        for(int i = 0; i < variables.length; i++) {
            if(equationSimplified.charAt(0) == variables[i]) {
                result = numbersArray[i];
            }
        }
        return result;
    }

    private String mathAction(char action, char first, char second) {
        String answer = "";
        double firstNum = 0;
        double secondNum = 0;
        for(int i = 0; i < variables.length; i++) {
            if(variables[i] == first) {
                firstNum = Double.parseDouble(numbersArray[i]);
            }
        }
        for(int i = 0; i < variables.length; i++) {
            if(variables[i] == second) {
                secondNum = Double.parseDouble(numbersArray[i]);
            }
        }
        switch(action) {
            case '*':
                answer += firstNum * secondNum;
                break;
            case '+':
                answer += firstNum + secondNum;
                break;
            case '/':
                answer += firstNum / secondNum;
                break;
            case '-':
                answer += firstNum - secondNum;
                break;
            case '^':
                answer += Math.pow(firstNum,secondNum);
                break;
        }
        return answer;
    }

    public void setEquation(String equation) {
        this.equation = equation;
    }

    public void error(String type) {
        switch(type) {
            case "oddBracket":
                System.out.println(type);
                break;
            case "notSameBracketCount":
                System.out.println(type);
                break;
            case "actionByAction":
                System.out.println(type);
                break;
        }
    }
    /*Надо сделать:
    1. Разобраться с тем, что в конце введённого выражения должен быть пробел.
    2. Разобраться с отрицательными числами
    3. Сделать алгоритм, заменяющий тригонометрические функции на какой-нибудь оператор, типо:
    SIN -> 's'
    COS -> 'c'
    TG -> 't'
    CTG -> 'k'
    ARCTG -> 'a'
    ARCCTG -> 'A'
    или вроде того с помощью RegEx; Причём ещё ввести переменную '@' которая при попадании в mathAction
    как первое число будет вызывать тригонометрические функции. По типу:
    SIN(2) -> SIN(a) -> @sa
    4. Разбить всё(метод getAnswer) на отдельные методы
    5. В отдельном методе для проверки на ошибки выражения ещё ловить случай ")4+2(" который сейчас игнорится
    4. После первых пунктов приступать к интерфейсу 3333
        */
}
