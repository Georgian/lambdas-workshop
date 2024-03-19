package dev.georgian;

import dev.georgian.model.Person;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MainTest {

    @Test
    void mainTest() {
        var numbers = List.of(1, 2, 3, 4, 5, 6);

        int sum = 0;
//        for (Integer number : numbers) {
//            if (number % 2 == 0) {
//                sum += number;
//            }
//        }


        Predicate<Integer> integerPredicate = number -> number % 2 == 0;

        Function<Integer, Boolean> integerPredicate2 = integer -> integer % 2 == 0;

        Supplier<Integer> supp = () -> {
            System.out.print("");
            return 2;
        };

        var i = supp.get();


        sum = numbers.stream()
            .filter(integerPredicate)
            .reduce(0, Integer::sum);


         numbers = addSomeNumbersToList(numbers);

        assertEquals(12, sum);
    }


    private List<Integer> addSomeNumbersToList(List<Integer> numbers) {
        var numbersCopy = numbers == null
            ? new ArrayList<Integer>()
            : new ArrayList<>(numbers);

        List<Person> a = new ArrayList<>();



        numbersCopy.add(5);
        numbersCopy.add(6);
        numbersCopy.add(7);
        numbersCopy.add(5);
        return numbersCopy;
    }


}
