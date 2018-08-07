package great.shine.section01;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class LambdaExpression {
    public static void main(String[] args) {
        String[] strings = {"a", "bbb", "CC"};
        List<String> list = Arrays.asList(strings);

        // Sorting array
        System.out.println("1. using lambda expression to interface that has single abstract method");
        Arrays.sort(
                strings,
                (first, second) -> Integer.compare(first.length(), second.length())
        );
        for (String s : strings) {
            System.out.print(s + ", ");
        }
        System.out.println();
        System.out.println();

        // Sorting array
        System.out.println("2. using method reference");
        Arrays.sort(strings, String::compareToIgnoreCase);
        for (String s : strings) {
            System.out.print(s + ", ");
        }
        System.out.println();
        System.out.println();

        // forEach method
        System.out.println("3. using forEach method");
        list.forEach(System.out::println);
        System.out.println();
        System.out.println();

        // static method of interface
        System.out.println("4. using static method of interface");
        Arrays.sort(
                strings,
                Comparator.comparingInt(String::length)
        );
        System.out.println();
        System.out.println();
    }
}