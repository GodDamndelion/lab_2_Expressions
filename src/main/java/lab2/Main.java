package lab2;

import lab2.ExpressionTree.ExpressionTree;

import java.util.Scanner;

public class Main {
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