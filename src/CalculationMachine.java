import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.regex.*;


public class CalculationMachine {
    private String equation;
    private StringBuilder equationSimplified = new StringBuilder();
    private String[] numbersArray = new String[100];
    private final char[] digitsArray = {'0','1','2','3','4','5','6','7','8','9','.'};
    private final char[] actionsArray = {'r','R','m','o','l','L','s','c','t','k','S','C','T','K','^','*','/','+','-','f'};
    private char[] variables = {'a','b','d','e','g','h','i','j','n','p','q','u','v','w','x','y','z','A','B','C','D',
    'E','F','G','H','I','J','K','M','N','O','P','Q','S','T','U','V','W','X','Y','Z'};
    private int varId = 0;
    private int bracketCount = 0;
    private boolean noErrors = true;
    private char angleSize = 'd';

    public String getInfo() {
        String str = "";
        str = "equation: " + equation + "\n" +
                "bracketCount: " + bracketCount + "\n" +
                "angleSize: " + angleSize + "\n";
        return str;
    }

    public CalculationMachine() {
        for(int i = 0; i < numbersArray.length; i++) {
            numbersArray[i] = "nothing here";
        }
    }

    public String getAnswer() {
        String result = "Incorrect";
        checkForErrors();
        if(noErrors) {
            //Тут неведомо сложный механизм замены чисел на переменные
            simplifyTheEquation();
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
                    System.out.println(Arrays.toString(numbersArray));
                    String str = mathAction(subEquation.charAt(operationFirst), subEquation.charAt(operationFirst - 1),
                            subEquation.charAt(operationFirst + 1));
                    for (int j = 0; j < variables.length; j++) {
                        if(subEquation.charAt(operationFirst - 1) == variables[j]) {
                            numbersArray[j] = "nothing here";
                        }
                        if(subEquation.charAt(operationFirst + 1) == variables[j]) {
                            numbersArray[j] = "nothing here";
                        }
                    }
                    System.out.println(Arrays.toString(numbersArray));
                    System.out.println(str);
                    for (int j = 0; j < numbersArray.length; j++) {
                        if(numbersArray[j].equals("nothing here")) {
                            varId = j;
                            break;
                        }
                    }
                    numbersArray[varId] = str; //Вводим новую переменную и соот.ей элемент в массиве numbersArray
                    System.out.println(Arrays.toString(numbersArray));
                    subEquation.delete((operationFirst - 1), (operationFirst + 2));//Заменяем в подстроке то, что мы посчитали на новую переменную
                    subEquation.insert(operationFirst - 1, variables[varId]);
                    System.out.println(numbersArray[varId]);
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
                System.out.println(equationSimplified + " SASASASASASA");
                for(int i = 0; i < variables.length; i++) {
                    if(equationSimplified.charAt(0) == variables[i]) {
                        result = numbersArray[i];
                    }
                }
            }
        }
        System.out.println(result);
        //Тут надо из настроек доставать сколько знаков после запятой показывать
        BigDecimal bd = new BigDecimal(result);
        bd = bd.setScale(3, RoundingMode.HALF_UP);
        result = bd.toString();
        return result;
    }

    private void checkForErrors() {
        noErrors = true;
        char[] simpleActions = {'^','*','/','+','-'};
        int bracketOpenCount = 0;
        int bracketCloseCount = 0;
        bracketCount = 0;
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
            if(bracketCloseCount > bracketOpenCount) {
                error("bracketOpensAfterClose");
                noErrors = false;
                break;
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
                for(int j = 0; j < simpleActions.length; j++) {
                    if(equation.charAt(i) == simpleActions[j]) {
                        for(int k = 0; k < simpleActions.length; k++) {
                            if(equation.charAt(i+1) == simpleActions[k]) {
                                noErrors = false;
                                error("actionByAction");
                            }
                        }
                    }
                }
            }
        }
    }

    private void simplifyTheEquation() {
        equationSimplified.append(equation);
        System.out.println(equationSimplified);
        //С самого начала заменяем пи и экспоненту на числа им соответствующие.
        boolean searching = true;
        while (searching) {
            searching = false;
            Pattern p = Pattern.compile("π");
            Matcher m = p.matcher(equationSimplified);
            if (m.find()) {
                searching = true;
                equationSimplified.delete(m.start(), m.end());
                equationSimplified.insert(m.start(), "" + Math.PI);
                System.out.println(equationSimplified);
            }
            p = Pattern.compile("e");
            m = p.matcher(equationSimplified);
            if (m.find()) {
                searching = true;
                equationSimplified.delete(m.start(), m.end());
                equationSimplified.insert(m.start(), "" + Math.exp(1));
                System.out.println(equationSimplified);
            }
        }
        //Не отходя от кассы заменим 12! на 12f@.
        searching = true;
        while(searching) {
            searching = false;
            for (int i = 0; i < equationSimplified.length(); i++) {
                if (equationSimplified.charAt(i) == '!') {
                    equationSimplified.deleteCharAt(i);
                    equationSimplified.insert(i, "f@");
                    searching = true;
                    break;
                }
            }
        }
        // |a+b| заменяем на @m(a+b). После подсчёта будет использован модуль

        //Далее все числа заменяем на переменные, чтобы с ними было проще обращаться.
        boolean firstDigit = true;
        boolean digitWasFound = true;
        boolean digitWasFound4 = true;
        int firstDigitId = 0;
        int lastDigitId = 0;
        while (digitWasFound4) {
            for (int i = 0; i < equationSimplified.length(); i++) {
                digitWasFound = false;
                for (int j = 0; j < digitsArray.length; j++) {
                    if (equationSimplified.charAt(i) == digitsArray[j]) {
                        digitWasFound = true;
                        if (firstDigit) {
                            firstDigitId = i;
                            lastDigitId = i;
                            firstDigit = false;
                            break;
                        } else {
                            lastDigitId++;
                            break;
                        }
                    }

                }
                if (!firstDigit && !digitWasFound) {
                    for (int j = 0; j < numbersArray.length; j++) {
                        if (numbersArray[j].equals("nothing here")) {
                            varId = j;
                            break;
                        }
                    }
                    numbersArray[varId] = equationSimplified.substring(firstDigitId, lastDigitId + 1);
                    equationSimplified.delete(firstDigitId, lastDigitId + 1);
                    equationSimplified.insert(firstDigitId, variables[varId]);
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
                    if (equationSimplified.charAt(i) == digitsArray[j]) {
                        digitWasFound4 = true;
                        break;
                    }
                }
            }
        }
        //Далее избавляемся от случаев, когда (-a)*b таким образом, чтобы a = -a а выражение стало
        // (a)*b. От случая, когда минус в самом самом начале мы избавляемся в методе setEquation.
        searching = true;
        while (searching) {
            searching = false;
            for (int i = 0; i < equationSimplified.length() - 1; i++) {
                if (equationSimplified.charAt(i) == '(' && equationSimplified.charAt(i + 1) == '-') {
                    for (int j = 0; j < variables.length; j++) {
                        if (equationSimplified.charAt(i + 2) == variables[j]) {
                            numbersArray[j] = "-" + numbersArray[j];
                            equationSimplified.deleteCharAt(i + 1);
                            searching = true;
                            System.out.println(equationSimplified);
                            System.out.println(Arrays.toString(numbersArray));
                            break;
                        }
                    }
                    if (searching) {
                        break;
                    }
                }
            }
        }
        //ЛОГАРИФМЫ. Заменяем логарифмы на их упрощённые версии
        searching = true;
        while(searching) {
            searching = false;
            Pattern p = Pattern.compile("lg");
            Matcher m = p.matcher(equationSimplified);
            if (m.find()) {
                searching = true;
                equationSimplified.delete(m.start(), m.end());
                equationSimplified.insert(m.start(), "@l");
                System.out.println(equationSimplified);
            }
            p = Pattern.compile("ln");
            m = p.matcher(equationSimplified);
            if (m.find()) {
                searching = true;
                equationSimplified.delete(m.start(), m.end());
                equationSimplified.insert(m.start(), "@L");
                System.out.println(equationSimplified);
            }
            p = Pattern.compile("log"); //log[a](b)
            m = p.matcher(equationSimplified);
            if (m.find()) {
                searching = true;
                char logPower = equationSimplified.charAt(m.start() + 4);
                equationSimplified.delete(m.start(), m.end() + 3);
                equationSimplified.insert(m.start(), logPower + "o");
                System.out.println(equationSimplified);
            }
        }
        //Сначала заменяем все аркфункции на их упрощённые версии
        //Это нужно для того, чтобы при поиске "sin" он не вытягивал из
        //"arcsin" и не получалось arc@s(число)
        searching = true;
        while (searching) {
            searching = false;
            //ARC SINUS
            Pattern p = Pattern.compile("arcsin");
            Matcher m = p.matcher(equationSimplified);
            if (m.find()) {
                searching = true;
                equationSimplified.delete(m.start(), m.end());
                equationSimplified.insert(m.start(), "@S");
                System.out.println(equationSimplified);
            }
            //ARC COSINUS
            p = Pattern.compile("arccos");
            m = p.matcher(equationSimplified);
            if (m.find()) {
                searching = true;
                equationSimplified.delete(m.start(), m.end());
                equationSimplified.insert(m.start(), "@C");
                System.out.println(equationSimplified);
            }
            //ARC TANGENT
            p = Pattern.compile("arctg");
            m = p.matcher(equationSimplified);
            if (m.find()) {
                searching = true;
                equationSimplified.delete(m.start(), m.end());
                equationSimplified.insert(m.start(), "@T");
                System.out.println(equationSimplified);
            }
            //ARC COTANGENT
            p = Pattern.compile("arcctg");
            m = p.matcher(equationSimplified);
            if (m.find()) {
                searching = true;
                equationSimplified.delete(m.start(), m.end());
                equationSimplified.insert(m.start(), "@K");
                System.out.println(equationSimplified);
            }
        }
        //После аркфункций заменяем уже обычные тригонометрические
        //Важно! ctg содержит в себе tg. Поэтому беднягу тангенса мы выносим отдельно от вообще всего.
        //Другим решением было бы использовать англоязычное обозначение - tan вместо tg и cot вместо ctg.
        //Почему-то в мире они обозначаются по-разному.
        searching = true;
        while (searching) {
            searching = false;
            //SINUS
            Pattern p = Pattern.compile("sin");
            Matcher m = p.matcher(equationSimplified);
            if (m.find()) {
                searching = true;
                equationSimplified.delete(m.start(), m.end());
                equationSimplified.insert(m.start(), "@s");
                System.out.println(equationSimplified);
                //COSINUS
                p = Pattern.compile("cos");
                m = p.matcher(equationSimplified);
            }
            if (m.find()) {
                searching = true;
                equationSimplified.delete(m.start(), m.end());
                equationSimplified.insert(m.start(), "@c");
                System.out.println(equationSimplified);
            }
            //COTANGENT
            p = Pattern.compile("ctg");
            m = p.matcher(equationSimplified);
            if (m.find()) {
                searching = true;
                equationSimplified.delete(m.start(), m.end());
                equationSimplified.insert(m.start(), "@k");
                System.out.println(equationSimplified);
            }
        }
        searching = true;
        while (searching) {
            searching = false;
            //TANGENT
            Pattern p = Pattern.compile("tg");
            Matcher m = p.matcher(equationSimplified);
            if (m.find()) {
                searching = true;
                equationSimplified.delete(m.start(), m.end());
                equationSimplified.insert(m.start(), "@t");
                System.out.println(equationSimplified);
            }
        }
        //КОРНИ
        searching = true;
        while (searching) {
            searching = false;
            //SQRT
            Pattern p = Pattern.compile("sqrt");
            Matcher m = p.matcher(equationSimplified);
            if (m.find()) {
                searching = true;
                equationSimplified.delete(m.start(), m.end());
                equationSimplified.insert(m.start(), "@r");
                System.out.println(equationSimplified);
            }
            //ROOT
            p = Pattern.compile("root");
            m = p.matcher(equationSimplified);
            if (m.find()) {
                searching = true;
                char rootPower = equationSimplified.charAt(m.start() + 5);
                equationSimplified.delete(m.start(), m.end() + 3);
                equationSimplified.insert(m.start(), rootPower + "R");
                System.out.println(equationSimplified);
            }
        }
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
            //Факториал
            case 'f':
                int fact = 1;
                for(int i = 1; i <= firstNum; i++) {
                    fact *= i;
                    System.out.println(fact);
                }
                answer += fact;
                break;
            //Простые действия
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
            //Тригонометрия
            //СИНУС
            case 's':
                if(angleSize == 'd') {
                    secondNum = Math.toRadians(secondNum);
                }
                answer += Math.sin(secondNum);
                break;
            //КОСИНУС
            case 'c':
                if(angleSize == 'd') {
                    secondNum = Math.toRadians(secondNum);
                }
                answer += Math.cos(secondNum);
                break;
            //ТАНГЕНС
            case 't':
                if(angleSize == 'd') {
                    secondNum = Math.toRadians(secondNum);
                }
                answer += Math.tan(secondNum);
                break;
            //КОТАНГЕНС
            case 'k':
                if(angleSize == 'd') {
                    secondNum = Math.toRadians(secondNum);
                }
                answer += 1/Math.tan(secondNum);
                break;
            //АРКСИНУС
            case 'S':
                secondNum = Math.asin(secondNum);
                if(angleSize == 'd') {
                    secondNum = Math.toDegrees(secondNum);
                }
                answer += secondNum;
                break;
            //АРККОСИНУС
            case 'C':
                secondNum = Math.acos(secondNum);
                if(angleSize == 'd') {
                    secondNum = Math.toDegrees(secondNum);
                }
                answer += secondNum;
                break;
            //АРКТАНГЕНС
            case 'T':
                secondNum = Math.atan(secondNum);
                if(angleSize == 'd') {
                    secondNum = Math.toDegrees(secondNum);
                }
                answer += secondNum;
                break;
            //АРККОТАНГЕНС
            case 'K':
                secondNum = Math.atan(1/secondNum);
                if(angleSize == 'd') {
                    secondNum = Math.toDegrees(secondNum);
                }
                answer += secondNum;
                break;
            //Логарифмы
            //НАТУРАЛЬНЫЙ
            case 'L':
                answer += Math.log(secondNum);
                break;
            //10й
            case 'l':
                answer += Math.log10(secondNum);
                break;
            //ПРОИЗВОЛЬНЫЙ
            case 'o':
                answer += Math.log(secondNum) / Math.log(firstNum);
                break;
            //МОДУЛЬ
            case 'm':
                answer += Math.abs(secondNum);
                break;
            //КВАДРАТНЫЙ КОРЕНЬ
            case 'r':
                answer += Math.sqrt(secondNum);
                break;
            //КОРЕНЬ
            case 'R':
                answer += Math.pow(secondNum,(1/firstNum));
                break;
        }
        return answer;
    }

    public void setEquation(String equationNew) {
        equation = equationNew; //Выражение меняется
        System.out.println(equation + "EQUATION SETTED");
        equation += " ";//В конец пробел для удобства
        if(equation.charAt(0) == '-') {
            equation = "0" + equation; //Если в самом начале минус, то представим, что есть ещё 0 ("-1+2" -> "0-1+2 ")
        }
        equationSimplified.delete(0, equationSimplified.length());//Из вспомогательного equationSimplified удаляем всё, что там осталось
        //Очищаем массив чисел
        Arrays.fill(numbersArray, "nothing here");
        makeEquationGreatAgain();
    }
    //Этим методом заменяем все модули на его упрощённую версию.
    public void makeEquationGreatAgain() {
        StringBuilder sb = new StringBuilder(equation);
        boolean notGreatYet = true;
        boolean searching = true;
        while(searching) {
            searching = false;
            for(int i = 0; i < sb.length(); i++) {
                if(sb.charAt(i) == '|') {
                    sb.replace(i, i + 1, "@m(");
                    for(int j = i; j < sb.length(); j++) {
                        if(sb.charAt(j) == '|') {
                            searching = true;
                            sb.replace(j, j + 1, ")");
                            break;
                        }
                    }
                    break;
                }
            }
        }
        equation = sb.toString();
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

    public void setAngleSize(char angleSize) {
        this.angleSize = angleSize;
    }

    /*
    SIN -> 's'
    ASIN -> 'S'
    COS -> 'c'
    ACOS -> 'C'
    TG -> 't'
    ARCTG -> 'T'
    CTG -> 'k'
    ARCCTG -> 'K'
    lg -> 'l'
    ln -> 'L'
    log -> 'o' Lol
    |x| -> @m(x)
    sqrt(z) -> @rx
    root[a](b) -> aRb
        */
}
