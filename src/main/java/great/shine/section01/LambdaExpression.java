package great.shine.section01;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class LambdaExpression {
    public static void main(String[] args) {
        useLambdaExpression();

        useMethodReference();

        useForEachMethod();

        useStaticMethod();
    }

    private static void useLambdaExpression() {
        System.out.println("1. using lambda expression to interface that has single abstract method");

        String[] strings = {"a", "bbb", "CC"};

        // Sorting array
        Arrays.sort(
                strings,
                (first, second) -> Integer.compare(first.length(), second.length())
        );

        System.out.println(String.join(", ", strings));
        System.out.println();
    }

    private static void useMethodReference() {
        System.out.println("2. using method reference");

        String[] strings = {"a", "bbb", "CC"};

        // Sorting array
        Arrays.sort(strings, String::compareToIgnoreCase);

        System.out.println(String.join(", ", strings));
        System.out.println();
    }

    private static void useForEachMethod() {
        System.out.println("3. using forEach method");

        String[] strings = {"a", "bbb", "CC"};
        List<String> list = Arrays.asList(strings);

        // forEach method
        list.forEach(System.out::println);

        System.out.println();
    }

    private static void useStaticMethod() {
        System.out.println("4. using static method of interface");

        String[] strings = {"a", "bbb", "CC"};

        // static method of interface
        Arrays.sort(
                strings,
                Comparator.comparingInt(String::length)
        );

        System.out.println(String.join(", ", strings));
        System.out.println();
    }
}