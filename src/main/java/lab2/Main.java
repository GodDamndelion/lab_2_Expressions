package lab2;

import lab2.ExpressionTree.ExpressionTree;

import java.util.Scanner;

/**
 * Класс Main
 */
public class Main {
    /**
     * Введите в командную строку выражение и значения переменных. Функция выведет результат.
     * @param args аргументы main
     */
    public static void main(String[] args) {
        System.out.println("Enter your expression: ");
        Scanner in = new Scanner(System.in);

        String exp = in.nextLine();
        ExpressionTree example = new ExpressionTree();
        example.calculate(exp);

        in.close();
        System.exit(0);
    }
}