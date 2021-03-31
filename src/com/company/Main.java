package com.company;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;


public class Main {

    public static void main(String[] args) {
        String path = "src/com/company/Read";
        String input = readFromFile(path);
        String[] strings = input.split("\\s+");
        StringBuilder input2 = new StringBuilder();
        try {
            for (String st : strings) {
                input2.append(st);
            }
            if(input2.length()%2==0){
                throw new InputMismatchException();
            }
        }catch(InputMismatchException e){
            throw new ValidationException("Number of tokens is incorrect", e);
        }
        List<Object> toCalculate = new ArrayList<>();
        addElements1(toCalculate, input2.toString());

        for (int j = 0; j < toCalculate.size(); j++) {
            for (int i = 1; i < toCalculate.size(); i += 2) {
                if (readAsCharacter(toCalculate.get(i)).equals('*')
                        || readAsCharacter(toCalculate.get(i)).equals('/')
                        || readAsCharacter(toCalculate.get(i)).equals('%')) {
                    double io = evaluate1(toCalculate.get(i - 1), toCalculate.get(i + 1), toCalculate.get(i));
                    toCalculate.remove(i + 1);
                    toCalculate.set(i, io);
                    toCalculate.remove(i - 1);
                }
            }
        }

        double result = readAsDouble(toCalculate.get(0));
        double operand;
        char operator;
        for (int i = 1; i < toCalculate.size(); i += 2) {
            operator = readAsCharacter(toCalculate.get(i));
            operand = readAsDouble(toCalculate.get(i + 1));
            result = evaluate2(result, operand, operator);
        }
        System.out.println("The result is: " +result);

    }

    private static void addElements1(List<Object> elements1, String input) {
        for (int i = 0; i < input.length(); i++) {
            if (i % 2 == 0) {
                elements1.add(parseCharacterToDouble(input.charAt(i)));
            } else {
                elements1.add(input.charAt(i));
            }
        }

    }

    private static String readFromFile(String path) {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            return br.readLine();
        } catch (IOException e) {
            throw new RuntimeException("Fifierul: " + path + " nu poate fi deschis", e);
        }
    }

    private static double parseCharacterToDouble(Character token) {
        try {
            return Double.parseDouble(String.valueOf(token));
        } catch (NumberFormatException e) {
            throw new ValidationException("Token-ul: [" + token + "] nu s-a putut converti la double", e);
        }
    }

    private static double evaluate1(Object op1, Object op2, Object operator) {
        return switch ((Character) operator) {
            case '*' -> readAsDouble(op1) * readAsDouble(op2);
            case '/' -> readAsDouble(op1) / readAsDouble(op2);
            case '%' -> readAsDouble(op1) % readAsDouble(op2);
            default -> throw new UnsupportedOperationException("Operatorul: [" + operator + "] nu este inca implementat");
        };
    }

    private static double evaluate2(Object op1, Object op2, Object operator) {
        return switch ((Character) operator) {
            case '+' -> readAsDouble(op1) + readAsDouble(op2);
            case '-' -> readAsDouble(op1) - readAsDouble(op2);
            default -> throw new UnsupportedOperationException("Operatorul: [" + operator + "] nu este inca implementat");
        };
    }

    private static Character readAsCharacter(Object ob) {
        return (Character) ob;
    }

    private static Double readAsDouble(Object ob) {
        return (Double) ob;
    }
}
