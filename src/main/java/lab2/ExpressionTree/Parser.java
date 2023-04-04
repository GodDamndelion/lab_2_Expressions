package lab2.ExpressionTree;

public class Parser {
    private String msymbol; // строка символов
    private int i; // позиция итератора в данной строке
    private int size; // размер строки
    boolean isReady; // Флаг для завершения обработки подстроки

    /**
     * Конструктор класса
     *
     * @param value входящая строка
     */
    Parser(String value) {
        msymbol = value;
        i = 0;
        size = msymbol.length();
        isReady = false;
    }

    /**
     *
     * @return размер
     */
    public int get_size() {
        return size;
    }

    /**
     * Установка итератора на определенный индекс
     * @param ind новый индекс
     */
    public void set_i(int ind){
        i = ind;
    }

    /**
     * Функция проверки корректности написанной формулы
     *
     * @return верное или не верное выражение
     */
    public boolean correct() {
        int cLeft = 0, cRight = 0; // открывающие скобки
        boolean result = false;
        char symbol = '&';

        while (i < size && cLeft >= cRight) {
            symbol = msymbol.charAt(i);
            if (symbol == '(')
                cLeft++;
            if (symbol == ')')
                cRight++;
            i++;
        }
        i = 0;

        if (cLeft == cRight)
            result = true;

        /* Функция проверки лишней пары открывающей и закрывающей скобки */
        if (msymbol.charAt(0) == '(' && msymbol.charAt(msymbol.length() - 1) == ')') {
            String test = msymbol.substring(1, msymbol.length() - 1);
            Parser ex = new Parser(test);

            if (ex.correct()) {
                msymbol = test;
                size = size - 2;
            }
        }

        return result;
    }

    /**
     * Функция проверки строки на выражение
     *
     * @see Parser#isOperator(char)
     * @return true - если выражение, иначе false
     */
    public boolean isItExpression() {
        boolean flag = false;
        i = 0;
        char symbol = '&';

        while (i < size && !flag) {
            symbol = msymbol.charAt(i);

            if (isOperator(symbol) && i!=0)
                flag = true;
            else
                i++;
        }

        i = 0;
        return flag;
    }

    /**
     * Проверка символна на оператор
     *
     * @param c - входной символ
     * @return true - если оператор
     */
    public boolean isOperator(char c) {
        return ((c == '+') || (c == '-') || (c == '/') || (c == '*') || (c == '^'));
    }

    /**
     * Проверка: я вляется ли строка числом
     *
     * @param value -строка
     * @return true - если число, false - если нет
     */
    public boolean isDigit(String value) {
        boolean result = false;
        char first = value.charAt(0), second = '?';

        if (value.length() > 1)
            second = value.charAt(1);

        if (first == '-' && Character.isDigit(second) || Character.isDigit(first))
            result = true;

        return result;
    }

    /**
     * Главная функция для поэтапного разбиения строки на состовляющие:
     * числа,
     * буквенные выражения,
     * знаки операций,
     * скобки
     *
     * @see Parser#isOperator(char)
     * @return
     */
    public String parse() {
        char symbol = '#', prev = '?', next = '&'; // символ сейчас, предыдущий и следущий
        String result = ""; // итоговое выражение
        boolean isNegative = false; // указатель, что число отрицательное, а не просто знак

        while (i < size && !isReady) {
            symbol = msymbol.charAt(i);

            if (i < size - 1)
                next = msymbol.charAt(i + 1);
            if (i > 0)
                prev = msymbol.charAt(i - 1);

            if (symbol != ' ' && (!(symbol == '(' && next == '-')) && (!(symbol == '-' && i == 0))) { // пропускаем пробелы и лишние минусы перед
                // отрицательным числом
                result += symbol;

                // У цифр - знак, десятичность, и размер
                // 15-5 - знак минуса тут операнд
                // 25/(-7) - знак минуса тут это число
                // -12 - это тоже число

                if (Character.isDigit(symbol)) {
                    if (prev == '-' && i == 1)
                        result = '-' + result;

                    if (i < size - 1 && (Character.isDigit(next) || (next == '.')))
                        while (i < size - 1 && (Character.isDigit(next) || (next == '.'))) { // "сборка числа"
                            i++;
                            prev = symbol;
                            symbol = next;
                            if (i <= size - 2)
                                next = msymbol.charAt(i + 1);
                            else
                                next = '&';
                            result += symbol;
                        }
                    isReady = true;
                } else if (Character.isLetter(symbol)) {
                    if (i < size - 1 && Character.isLetter(next))
                        while (i < size - 1 && Character.isLetter(next)) { // "сборка букв"
                            i++;
                            prev = symbol;
                            symbol = next;
                            if (i <= size - 2)
                                next = msymbol.charAt(i + 1);
                            else
                                next = '#';
                            result += symbol;
                        }
                    isReady = true;
                } else if (symbol == '(') {
                    result = "(";
                    isReady = true;
                } else if (symbol == ')') {
                    if (isNegative)
                        isNegative = false;
                    else {
                        result = ")";
                        isReady = true;
                    }
                } else if (isOperator(symbol) && !isNegative)
                    isReady = true;

                i++;
                prev = symbol;

            } else {
                isNegative = true;
                prev = symbol;
                symbol = next;
                if (i < size - 1)
                    next = msymbol.charAt(i + 1);
                i++;
            }

        }

        isReady = false;
        return result;
    }
}
