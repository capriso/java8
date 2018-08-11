package great.shine.section02;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
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
        simpleReduction();
        optaionalValueComposition();
        reductionMethod();
        collectResults();
        collectResultToMap();
        grouping();
        partitioning();

        primitiveTypeStreams();
        parallelStreams();
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

    private static void simpleReduction() {
        System.out.println("7. use Optional<T> for reduce operation");

        String contents = "Java 8 is a revolutionary release of the world’s #1 development platform.";

        // Stream from array
        Stream<String> words = Stream.of(contents.split("[\\P{L}]+"));

        // String result = null;
        // for(String s : list) {
        //   if(s.startsWith("J")) {
        //       result = s;
        //       break;
        //   }
        // }
        //
        // if(s != null) {
        //   System.out.println(result);
        // }
        Optional<String> optionalValue = words.filter(s -> s.startsWith("J")).findAny();
        optionalValue.ifPresent(System.out::println);

        System.out.println();
    }

    private static void optaionalValueComposition() {
        System.out.println("8. Optaional<T> Composition");

        // Stream from array
        Optional<Double> result = null;

        result = Optional.of(-4.0)
                .flatMap(StreamApi::inverse)
                .flatMap(StreamApi::squareRoot);
        System.out.println(result);

        result = Optional.of(0.0)
                .flatMap(StreamApi::inverse)
                .flatMap(StreamApi::squareRoot);
        System.out.println(result);

        result = Optional.of(4.0)
                .flatMap(StreamApi::inverse)
                .flatMap(StreamApi::squareRoot);
        System.out.println(result);

        System.out.println();
    }

    public static Optional<Double> inverse(Double x) {
        return x == 0 ? Optional.empty() : Optional.of(1 / x);
    }

    public static Optional<Double> squareRoot(Double x) {
        return x < 0 ? Optional.empty() : Optional.of(Math.sqrt(x));
    }

    private static void reductionMethod() {
        System.out.println("9. Reduction Method");

        Stream<Integer> values = Stream.of(1, 2, 3, 4);
        Integer sum = values.reduce(0, (x, y) -> x + y);
        System.out.println(sum);

        System.out.println();
    }

    private static void collectResults() {
        System.out.println("10. Collect Results");

        String contents = "Java 8 is a revolutionary release of the world’s #1 development platform.";

        // Stream from array
        Stream<String> words = Stream.of(contents.split("[\\P{L}]+"));

        String result = words.collect(Collectors.joining(", "));
        System.out.println(result);

        System.out.println();
    }

    private static void collectResultToMap() {
        System.out.println("11. Collect Results to Map");

        Person[] person = new Person[3];
        person[0] = new Person(1, "Michael");
        person[1] = new Person(2, "Jason");
        person[2] = new Person(3, "Shine");

        Stream<Person> people1 = Stream.of(person);
        Map<Integer, String> idToName = people1.collect(Collectors.toMap(Person::getId, Person::getName));
        System.out.println(idToName);

        // Function.identity()를 사용하면, 입력으로 들어온 값 그 자체를 넘겨준다.
        Stream<Person> people2 = Stream.of(person);
        Map<Integer, Person> idToPerson = people2.collect(Collectors.toMap(Person::getId, Function.identity()));
        System.out.println(idToPerson);

        System.out.println();
    }

    private static void grouping() {
        System.out.println("12. Grouping");

        Stream<Locale> locales1 = Stream.of(Locale.getAvailableLocales());
        Map<String, List<Locale>> countryToLocales =
                locales1.collect(
                        Collectors.groupingBy(Locale::getCountry)
                );
        System.out.println(countryToLocales);

        Stream<Locale> locales2 = Stream.of(Locale.getAvailableLocales());
        Map<String, Long> countryToLocaleCounts =
                locales2.collect(
                        Collectors.groupingBy(Locale::getCountry, Collectors.counting())
                );
        System.out.println(countryToLocaleCounts);

        System.out.println();
    }

    private static void partitioning() {
        System.out.println("13. Partitioning");

        Stream<Locale> locales = Stream.of(Locale.getAvailableLocales());
        Map<Boolean, List<Locale>> englishAndOtherLocales =
                locales.collect(
                        Collectors.partitioningBy(l -> l.getLanguage().equals("en"))
                );
        System.out.println(englishAndOtherLocales);

        System.out.println();
    }

    private static void primitiveTypeStreams() {
        System.out.println("14. Primitive Type Streams");

        // Wrapper Class를 사용할 이유가 없다면,
        // 기본 타입 스트림을 활용하여 형변환에서 발생하는 비효율을 제거할 수 있다.

        IntStream stream = IntStream.of(1, 2, 3, 4, 5);
        IntStream zeroToNinetyNine = IntStream.range(0, 100);
        IntStream zeroToHundred = IntStream.rangeClosed(0, 100);

        System.out.println(String.format("%d %d %d", stream.sum(), zeroToNinetyNine.sum(), zeroToHundred.sum()));

        String contents = "Java 8 is a revolutionary release of the world’s #1 development platform.";
        Stream<String> words = Stream.of(contents.split("[\\P{L}]+"));
        IntStream lengths = words.mapToInt(String::length);
        System.out.println(lengths.max());

        System.out.println();
    }

    private static void parallelStreams() {
        System.out.println("15. Parallel Streams");

        // Collection.parallelStream()을 제외하면 모든 스트림 연산은 기본으로 순차 스트림을 생성한다.
        // parallel() method를 사용하면 순차 스트림을 병렬 스트림으로 변환하여,
        // 모든 지연 처리 중간 스트림 연산을 병렬처리한다.
        // 따라서 임의의 순서로 실행될 수 있으며,
        // 멀티스레딩에 의해 예상치 못한 동작이 일어나지 않도록 코드를 잘 작성해야 한다는 점을 주의해야 한다.
        String contents = "Java 8 is a revolutionary release of the world’s #1 development platform.";
        Stream<String> words = Stream.of(contents.split("[\\P{L}]+")).parallel();
        System.out.println(words.count());

        System.out.println();
    }
}
