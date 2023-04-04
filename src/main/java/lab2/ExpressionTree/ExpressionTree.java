package lab2.ExpressionTree;

import java.util.Scanner;

/**
 * Класс Дерево выражений для хранения выражения и его значения
 *
 * @author Pavel Bolshakov
 * @version 1.0
 */
public class ExpressionTree {
    /**
     * Ссылка на корень дерева
     */
    private Node root;
    /**
     *  Результат вычисления выражения всего дерева
     */
    private double result;

    /**
     * Пустой конструктор
     */
    public ExpressionTree() {
        root = null;
        result = 0;
    }

    /** @return root - возвращает корень дерева */
    public final Node get_root() {
        return root;
    }

    /** @return result - возвращает резултат вычисления дерева */
    public final double get_result() {
        return result;
    }

    /**
     * Основная функция обработки строки.
     * Начинает рекурсивный разбор строки.
     *
     * @see ExpressionTree#recursion(String, Scanner)
     *      После заполнения вызывает функцию печати и выводит результат
     * @see Node#print(Node)
     */
    public final void calculate(String expression) {
        Scanner in = new Scanner(System.in);
        recursion(expression, in);

        System.out.println("Expression: ");

        if (!root.isFilling())
            System.out.println(result);
        else
            root.print(root);

        System.out.println();
        System.out.println("Result: ");
        System.out.println(result);
        in.close();
    }

    /**
     * Рекурсивная обработка строки
     */
    public final void recursion(String value, Scanner in) {
        if (filling(value, in)) {
            if (result == 0)
                result = estimation();
        } else
            System.out.println("Problem with filling!");

    }

    /**
     * Заполнение поддерева
     */
    public final boolean filling(String value, Scanner in) {
        boolean res = false;
        Parser parser = new Parser(value);

        if (parser.isCorrect()) {
            res = true;

            root = new Node();

            if (parser.isItExpression()) {
                root.fillingBranch(root, parser, in);
            } else
                result = Double.parseDouble(parser.parse());
            // root.print(root);
        } else
            System.out.println("Error!");

        return res;
    }

    /**
     * Возвращает результат вычисления выражения
     *
     * @see Node#calculate(Node, double)
     */
    public final double estimation() {
        return root.calculate(root, 0);
    }
}
