package lab2.ExpressionTree;

import org.junit.jupiter.api.*;

class ExpressionTreeTest {
    static ExpressionTree testTree = new ExpressionTree();
    static String testExpression = "((76-28)-((39-57)*8))/100";
    static Double testResult = ((76.0-28.0)-((39.0-57.0)*8.0))/100.0;

    @Tag("dev")
    @Test
    void calculate() {
        testTree.calculate(testExpression);
        Assertions.assertEquals(testResult, testTree.get_result());
    }

    @Tag("dev")
    @Test
    void estimation() {
        testTree.calculate(testExpression);
        Assertions.assertEquals(testResult, testTree.estimation());
    }

    @Tag("dev")
    @Test
    void fillingBranch() {
        Node exp = new Node();
        Parser parser = new Parser(testExpression);
        exp.fillingBranch(exp, parser, null);

        Assertions.assertEquals(testResult, exp.calculate(exp, 0));
    }

    @Tag("dev")
    @Test
    void calculateNode() {
        Node exp = new Node();
        Parser parser = new Parser("(((3+2)-67)/56)*(35-53)");
        exp.fillingBranch(exp, parser, null);
        Assertions.assertEquals((((3.0+2.0)-67.0)/56.0)*(35.0-53.0), exp.calculate(exp, 0));
    }
}