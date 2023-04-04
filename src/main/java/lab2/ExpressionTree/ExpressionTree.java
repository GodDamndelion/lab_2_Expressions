package lab2.ExpressionTree;

import java.util.Scanner;

public class ExpressionTree {
    private Node root; // ссылка на корень дерева
    private double result; // результат вычисления выражения всего поддерева

    // Конструктор
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
     * Основная функция обработки строки
     * Начинает рекурсивную разработку строки
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
        Parser comp = new Parser(value);

        if (comp.correct()) {
            res = true;

            root = new Node();

            if (comp.isItExpression())
                root = root.fillingBranch(root, comp, in);
            else
                result = Double.parseDouble(comp.parse());
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
