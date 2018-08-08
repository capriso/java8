package great.shine.section02;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamApi {
    // Stream은 java 8에서 연산의 스케줄링은 구현체에 맡기고,
    // 다중 스레드를 사용해 연산을 병렬화한다.

    public static void main(String[] args) {
        useStreamInsteadOfIteration();

        useParallelStreamInsteadOfIteration();

        convertCollectionToStream();

        // stateless transformation
        useMapMethod();
        extractSubstream();

        // stateful transformation
        statefulTransformation();
    }

    private static void useStreamInsteadOfIteration() {
        System.out.println("1. use stream instead of iteration");
        // For loop을 이용한 Iteration은 코드를 병렬화하기 어렵다.
        // Stream은 요소를 보관하지 않으며, 원본을 변경하지 않고,
        // 결과가 필요하기 전까지 처리가 지연된다.

        String contents = "Java 8 is a revolutionary release of the world’s #1 development platform.";
        List<String> words = Arrays.asList(contents.split("[\\P{L}]+"));

        long count = words.stream().filter(w -> w.length() > 5).count();

        System.out.println(count);
        System.out.println();
    }

    private static void useParallelStreamInsteadOfIteration() {
        System.out.println("2. use parallel stream instead of iteration");
        // 이전 step으로 작성된 코드는 아래와 같이 병렬화하기가 쉽다.

        String contents = "Java 8 is a revolutionary release of the world’s #1 development platform.";
        List<String> words = Arrays.asList(contents.split("[\\P{L}]+"));

        long count = words.parallelStream().filter(w -> w.length() > 5).count();

        System.out.println(count);
        System.out.println();
    }

    private static void convertCollectionToStream() {
        System.out.println("3. convert collection to stream");

        String contents = "Java 8 is a revolutionary release of the world’s #1 development platform.";

        // Stream from array
        Stream<String> words = Stream.of(contents.split("[\\P{L}]+"));
        long count = words.filter(w -> w.length() > 5).count();
        System.out.println(count);

        // empty stream
        Stream<String> silence = Stream.empty();
        count = silence.filter(w -> w.length() > 5).count();
        System.out.println(count);

        System.out.println();
    }

    private static void useMapMethod() {
        System.out.println("4. use map method");

        String contents = "Java 8 is a revolutionary release of the world’s #1 development platform.";

        // Stream from array
        Stream<String> words = Stream.of(contents.split("[\\P{L}]+"));
        Stream<String> lowercaseWords = words.map(String::toLowerCase);
        lowercaseWords.forEach(System.out::print);
        System.out.println();

        System.out.println();
    }

    private static void extractSubstream() {
        System.out.println("5. extract substream");

        // 무한 Stream으로부터 생성되는 5개의 random value 추출
        Stream<Double> random5 = Stream.generate(Math::random).limit(5);
        System.out.println(
                String.join(", ", random5.map(String::valueOf).collect(Collectors.toList()))
        );

        // 무한 Stream으로부터 생성되는 초기 3개의 random value를 건너뛰고 3개의 random value 추출
        Stream<Double> random3 = Stream.generate(Math::random).skip(3).limit(3);
        System.out.println(
                String.join(", ", random3.map(String::valueOf).collect(Collectors.toList()))
        );

        // 두 Stream을 연결
        Stream<Double> random4 = Stream.concat(
                Stream.generate(Math::random).limit(1),
                Stream.generate(Math::random).limit(3)
        );
        System.out.println(
                String.join(", ", random4.map(String::valueOf).collect(Collectors.toList()))
        );

        System.out.println();
    }

    private static void statefulTransformation() {
        System.out.println("6. stateful transformation: distinct, reversed, ...");

        // distinct
        String[] strings = {"1", "2", "3", "4", "1"};
        Stream<String> unique = Stream.of(strings).distinct();
        System.out.println(
                String.join(", ", unique.collect(Collectors.toList()))
        );

        // reverse sorted result
        Stream<Integer> values = Stream.of(1, 3, 2, 4, 5);
        Stream<Integer> reversedValues = values.sorted(Comparator.reverseOrder());
        System.out.println(
                String.join(", ", reversedValues.map(String::valueOf).collect(Collectors.toList()))
        );

        System.out.println();
    }
}
