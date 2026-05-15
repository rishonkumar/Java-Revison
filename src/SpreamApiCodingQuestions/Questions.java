package SpreamApiCodingQuestions;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Questions {

    // filter even number
    // Explanation: The filter method is used to apply a condition that keeps only
    // even numbers.
    // The collect method gathers the results into a new list.
    List<Integer> evenNumbers() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6);

        List<Integer> evenNumbers = numbers.stream().filter(n -> n % 2 == 0).collect(Collectors.toList());

        return evenNumbers;
    }

    // Find the maximum value in a list of integers.
    // The max method takes a comparator and returns the maximum element wrapped in
    // an Optional

    void maxNumber() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6);

        Optional<Integer> max = numbers.stream().max(Integer::compare);
        System.out.println(max);
    }

    void sum() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6);

        int sum = numbers.stream().mapToInt(Integer::intValue).sum();
        System.out.println(sum);
    }

    // Convert all strings in a list to uppercase.
    void convertStringtoUppercase() {
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie");
        List<String> res = names.stream().map(String::toUpperCase).collect(Collectors.toList());

    }

    // Sort a list of integers in ascending order.
    void sortIntegers() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6);

        List<Integer> sorted = numbers.stream().sorted().collect(Collectors.toList());
        System.out.println(sorted);
    }

    // Count the number of elements in a list that are greater than 5.
    void elementsGreaterThan5() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6);

        long count = numbers.stream().filter(m -> m > 5).count();
        System.out.println(count);
    }

    // Get a list of distinct elements from a list of integers.

    void getDistinct() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6);

        List<Integer> distinctNumbers = numbers.stream()
                .distinct()
                .collect(Collectors.toList());
        System.out.println(distinctNumbers);
    }

    // Return any element from a list of integers.
    void returnElement() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6);

        Optional<Integer> anyElement = numbers.stream()
                .findAny();
        System.out.println(anyElement);
    }

    // Extract first names from a list of full names.
    // The map function splits each name string and selects the first part.
    void extractFirstNames() {
        List<String> fullNames = Arrays.asList("Alice Johnson", "Bob Harris",
                "Charlie Lou");
        List<String> firstNames = fullNames.stream().map(name -> name.split(" ")[0]).collect(Collectors.toList());
        System.out.println(firstNames);
    }

    // Check if all numbers in a list are positive.
    void allPositive() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6);

        boolean allPositive = numbers.stream().allMatch(n -> n > 0);
        System.out.println(allPositive);
    }

    // Flatten a nested list structure.
    // flatMap converts each element into its own stream and then merges them into a
    // single stream
    void flattenList() {
        List<List<Integer>> nestedNumbers = Arrays.asList(Arrays.asList(1, 2),
                Arrays.asList(3, 4, 5));
        List<Integer> flaList = nestedNumbers.stream().flatMap(List::stream).collect(Collectors.toList());
        System.out.println(flaList);
    }
}
