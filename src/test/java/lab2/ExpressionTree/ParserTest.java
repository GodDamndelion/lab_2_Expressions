package lab2.ExpressionTree;

import org.junit.jupiter.api.*;

class ParserTest {
    static String testExpression = "((x-28)-((y-57)*8))/100";
    Parser test = new Parser(testExpression);

    @AfterEach
    void setup() {
        test.set_i(0);
    }

    @Tag("dev")
    @Test
    void get_size() {
        Assertions.assertEquals(testExpression.length(), test.get_size());
    }

    @Tag("dev")
    @Test
    void isCorrect() {
        Assertions.assertTrue(test.isCorrect());
    }

    @Tag("dev")
    @Test
    void isItExpression() {
        Assertions.assertTrue(test.isItExpression());
    }

    @Tag("dev")
    @Test
    void isOperator() {
        Assertions.assertTrue(test.isOperator('^'));
        Assertions.assertTrue(test.isOperator('+'));
        Assertions.assertTrue(test.isOperator('-'));
        Assertions.assertTrue(test.isOperator('/'));
        Assertions.assertTrue(test.isOperator('*'));
        Assertions.assertFalse(test.isOperator('&'));
        Assertions.assertFalse(test.isOperator('?'));
        Assertions.assertFalse(test.isOperator('$'));
        Assertions.assertFalse(test.isOperator('3'));
        Assertions.assertFalse(test.isOperator('b'));
    }

    @Tag("dev")
    @Test
    void isDigit() {
        Assertions.assertTrue(test.isDigit("3"));
        Assertions.assertTrue(test.isDigit("-3"));
        Assertions.assertFalse(test.isDigit("b"));
    }
}