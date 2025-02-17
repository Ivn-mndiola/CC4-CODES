import java.util.Scanner;
import java.util.Stack;

public class InfixToPostfix {

    // Method to determine the precedence of operators
    private static int precedence(char operator) {
        switch (operator) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
            case '%':
                return 2;
            case '^':
                return 3; // Exponentiation has the highest precedence
            default:
                return 0;
        }
    }

    // Method to determine if the operator is left associative
    private static boolean isLeftAssociative(char operator) {
        return operator != '^'; // Only exponentiation is right associative
    }

    // Method to convert infix expression to postfix expression
    public static String infixToPostfix(String infix) {
        StringBuilder postfix = new StringBuilder();
        Stack<Character> stack = new Stack<>();

        // Print table header
        System.out.printf("%-10s %-30s %-20s%n", "Token", "Stack", "Output (Postfix)");
        System.out.printf("%-10s %-30s %-20s%n", "-----", "------------------------------", "--------------------");

        for (char ch : infix.toCharArray()) {
            // Print the current token being processed
            String token = String.valueOf(ch);
            
            // If the character is an operand (digit), add it to the output
            if (Character.isDigit(ch)) {
                postfix.append(ch);
                System.out.printf("%-10s %-30s %-20s%n", token, stack.toString(), postfix.toString());
            } 
            // If the character is '(', push it to the stack
            else if (ch == '(') {
                stack.push(ch);
                System.out.printf("%-10s %-30s %-20s%n", token, stack.toString(), postfix.toString());
            } 
            // If the character is ')', pop and output from the stack
            // until an '(' is encountered
            else if (ch == ')') {
                while (!stack.isEmpty() && stack.peek() != '(') {
                    postfix.append(stack.pop());
                    System.out.printf("%-10s %-30s %-20s%n", "", stack.toString(), postfix.toString());
                }
                stack.pop(); // Remove '(' from the stack
                System.out.printf("%-10s %-30s %-20s%n", token, stack.toString(), postfix.toString());
            } 
            // If the character is an operator
            else if ("+-*/%^".indexOf(ch) != -1) {
                while (!stack.isEmpty() && 
                       (precedence(stack.peek()) > precedence(ch) || 
                       (precedence(stack.peek()) == precedence(ch) && isLeftAssociative(ch)))) {
                    postfix.append(stack.pop());
                    System.out.printf("%-10s %-30s %-20s%n", "", stack.toString(), postfix.toString());
                }
                stack.push(ch);
                System.out.printf("%-10s %-30s %-20s%n", token, stack.toString(), postfix.toString());
            }
        }

        // Pop all the operators from the stack
        while (!stack.isEmpty()) {
            postfix.append(stack.pop());
            System.out.printf("%-10s %-30s %-20s%n", "", stack.toString(), postfix.toString());
        }

        return postfix.toString();
    }

    // Method to evaluate the postfix expression
    public static int evaluatePostfix(String postfix) {
        Stack<Integer> stack = new Stack<>();

        System.out.printf("%-10s %-20s %-20s%n", "Token", "Stack", "Action");
        System.out.printf("%-10s %-20s %-20s%n", "-----", "------------------", "--------------------");

        for (char ch : postfix.toCharArray()) {
            // If the character is a digit, push it onto the stack
            if (Character.isDigit(ch)) {
                stack.push(Character.getNumericValue(ch));
                System.out.printf("%-10s %-20s %-20s%n", ch, stack.toString(), "Pushed to stack");
            } else {
                // Pop two operands from the stack
                int rightOperand = stack.pop();
                int leftOperand = stack.pop();
                int result = 0;

                // Perform the operation based on the operator
                switch (ch) {
                    case '+':
                        result = leftOperand + rightOperand;
                        break;
                    case '-':
                        result = leftOperand - rightOperand;
                        break;
                    case '*':
                        result = leftOperand * rightOperand; // Fixed typo here
                        break;
 case '/':
                        result = leftOperand / rightOperand;
                        break;
                    case '%':
                        result = leftOperand % rightOperand;
                        break;
                    case '^':
                        result = (int) Math.pow(leftOperand, rightOperand);
                        break;
                }
                // Push the result back onto the stack
                stack.push(result);
                System.out.printf("%-10s %-20s %-20s%n", ch, stack.toString(), "Computed: " + leftOperand + " " + ch + " " + rightOperand + " = " + result);
            }
        }

        // The final result will be the only element left in the stack
        return stack.pop();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean continueCalculating = true;

        while (continueCalculating) {
            System.out.print("Enter an infix expression: ");
            String infix = scanner.nextLine();
            
            String postfix = infixToPostfix(infix);
            System.out.println("Infix Expression: " + infix);
            System.out.println("Postfix Expression: " + postfix);
            
            // Compute the value of the postfix expression
            int value = evaluatePostfix(postfix);
            System.out.println("Value of the expression: " + value);
            
            System.out.print("Do you want to try again? (yes/no): ");
            String response = scanner.nextLine();
            continueCalculating = response.equalsIgnoreCase("yes");
        }

        scanner.close();
    }
}
