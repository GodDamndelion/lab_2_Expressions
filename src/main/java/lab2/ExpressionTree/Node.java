package lab2.ExpressionTree;

import java.util.Scanner;

/**
 * Класс Узел Дерева выражений
 *
 * @see ExpressionTree
 * @author Pavel Bolshakov
 * @version 1.0
 */
class Node {
    /**
     * Возможное поддерево
     */
    private ExpressionTree subTree;
    /**
     * Левая ветвь
     */
    private Node left;
    /**
     * Правая ветвь
     */
    private Node right;
    /**
     * Численное значение
     */
    private double value;
    /**
     * Операнд
     */
    private String term;
    /**
     * Заполнен ли уровень
     */
    private boolean isFilling;

    /** Конструктор без параметров */
    Node() {
        left = null;
        right = null;
        subTree = null;
        value = -1;
        term = null;
        isFilling = false;
    }

    /**
     * Возвращает значение Узла
     * @return текущее значение
     */
    public double getValue() {
        return value;
    }

    /**
     * Установка в качестве значения результата вычисления поддерева
     * @see ExpressionTree#get_result()
     */
    public void setValueBySubTree() {
        value = subTree.get_result();
    }

    /**
     * Возвращает, заполнен ли уровень
     */
    public boolean isFilling() {
        return isFilling;
    }

    /**
     * Создание левой ветви
     * @param root корень дерева
     * @see Node#Node()
     */
    public void createLeftBranch(Node root) {
        if (root.left == null)
            root.left = new Node();
    }

    /**
     * Создание правой ветви
     * @param root корень дерева
     * @see Node#Node()
     */
    public void createRightBranch(Node root) {
        if (root.right == null)
            root.right = new Node();
    }

    /**
     * Заполнение ветви дерева
     * @param root корень дерева
     * @param parser парсер
     * @param in Scanner
     */
    public final void fillingBranch(Node root, Parser parser, Scanner in) {
        String part;

        while (!root.isFilling) {
            part = parser.parse();

            if (part.charAt(0) != ')') {
                if (part.charAt(0) == '(') { // встречаем открывающую скобку - спускаемся вниз
                    if (root.left == null) { // проверка на какую ветку спускаться
                        root.createLeftBranch(root);
                        fillingBranch(root.left, parser, in);
                    } else {
                        root.createRightBranch(root);
                        fillingBranch(root.right, parser, in);
                        root.isFilling = true;
                    }
                } else if (parser.isDigit(part)) { // Заполнение цифрой
                    if (root.left == null) {
                        root.createLeftBranch(root);
                        root.left.value = Double.parseDouble(part);
                        root.left.isFilling = true;
                    } else if (root.right == null) {
                        root.createRightBranch(root);
                        root.right.value = Double.parseDouble(part);
                        root.right.isFilling = true;
                        root.isFilling = true;
                    }
                } else if (parser.isOperator(part.charAt(0))) // заполнение значение оператора
                    root.term = part;
                else {
                    // обыкновенная переменная
                    System.out.print("Enter meaning of " + part + ":");
                    String exp = in.nextLine();

                    // записано выражение в переменной помещаем в отдельное дерево
                    if (root.left == null) {
                        root.createLeftBranch(root);
                        root.left.subTree = new ExpressionTree();
                        root.left.subTree.recursion(exp, in);
                        root.left.setValueBySubTree();
                        root.left.isFilling = true;
                    } else if (root.right == null) {
                        root.createRightBranch(root);
                        root.right.subTree = new ExpressionTree();
                        root.right.subTree.recursion(exp, in);
                        root.right.setValueBySubTree();
                        root.right.isFilling = true;
                        root.isFilling = true;
                    }
                }
            }
        }

    }

    /**
     * Функция рекурсивного обхода дерева и подсчета выражения
     * @param root корень дерева
     * @param result итоговое значение выражения
     * @return result
     */
    public final double calculate(Node root, double result) {
        if (root.left == null && root.right == null)
            result = root.value;
        else {
            result = calculate(root.left, result);
            switch (root.term) {
                case "+" -> {
                    result = result + calculate(root.right, result);
                }
                case "-" -> {
                    result = result - calculate(root.right, result);
                }
                case "*" -> {
                    result = result * calculate(root.right, result);
                }
                case "/" -> {
                    result = result / calculate(root.right, result);
                }
                case "^" -> {
                    result = Math.pow(result, calculate(root.right, result));
                }
            }
        }

        return result;
    }

    /**
     * Функция рекурсивной печати
     * @param root корень дерева
     */
    void print(Node root) {
        if (root.left == null && root.right == null)
            System.out.print(Double.toString(root.value));
        else {
            System.out.print("(");
            print(root.left);
            System.out.print(root.term);
            print(root.right);
            System.out.print(")");
        }
    }
}
