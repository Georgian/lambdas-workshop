package dev.georgian.solutions;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * This set of exercises covers simple stream pipelines,
 * including intermediate operations and basic collectors.
 * <p>
 * Some of these exercises use a BufferedReader variable
 * named "reader" that the test has set up for you.
 */
public class D_SimpleStreams {
    /**
     * Given a list of words, create an output list that contains
     * only the odd-length words, converted to upper case.
     */
    @Test
    public void d1_upcaseOddLengthWords() {
        List<String> input = List.of(
            "alfa", "bravo", "charlie", "delta", "echo", "foxtrot");

        List<String> result =
            input.stream()
                .filter(w -> (w.length() & 1) == 1)
                .map(String::toUpperCase)
                .collect(Collectors.toList());
        // Alternatives:
        // Instead of w -> (w.length() & 1) == 1, use w -> (w.length() % 2) != 0
        // Instead of String::toUpperCase, use w -> w.toUpperCase()

        assertEquals(List.of("BRAVO", "CHARLIE", "DELTA", "FOXTROT"), result);
    }
    // Hint 1:
    // <editor-fold defaultstate="collapsed">
    // Use filter() and map().
    // </editor-fold>
    // Hint 2:
    // <editor-fold defaultstate="collapsed">
    // To create the result list, use collect() with one of the
    // predefined collectors on the Collectors class.
    // </editor-fold>


    /**
     * Take the third through fifth words of the list, extract the
     * second letter from each, and join them, separated by commas,
     * into a single string. Watch for off-by-one errors.
     */
    @Test
    public void d2_joinStreamRange() {
        List<String> input = List.of(
            "alfa", "bravo", "charlie", "delta", "echo", "foxtrot");

        String result =
            input.stream()
                .skip(2)
                .limit(3)
                .map(word -> word.substring(1, 2))
                .collect(Collectors.joining(","));

        assertEquals("h,e,c", result);
    }
    // Hint 1:
    // <editor-fold defaultstate="collapsed">
    // Use Stream.skip() and Stream.limit().
    // </editor-fold>
    // Hint 2:
    // <editor-fold defaultstate="collapsed">
    // Use Collectors.joining().
    // </editor-fold>


    /**
     * Count the number of lines in the text file. (Remember to
     * use the BufferedReader named "reader" that has already been
     * opened for you.)
     *
     * @throws IOException
     */
    @Test
    public void d3_countLinesInFile() throws IOException {
        long count = reader.lines()
            .count();

        assertEquals(14, count);
    }
    // Hint 1:
    // <editor-fold defaultstate="collapsed">
    // Use BufferedReader.lines() to get a stream of lines.
    // </editor-fold>
    // Hint 2:
    // <editor-fold defaultstate="collapsed">
    // Use Stream.count().
    // </editor-fold>


    /**
     * Find the length of the longest line in the text file.
     *
     * @throws IOException
     */
    @Test
    public void d4_findLengthOfLongestLine() throws IOException {
        int longestLength =
            reader.lines()
                .mapToInt(String::length)
                .max()
                .orElse(0);

        assertEquals(53, longestLength);
    }
    // Hint 1:
    // <editor-fold defaultstate="collapsed">
    // Use Stream.mapToInt() to convert a stream of objects to an IntStream.
    // </editor-fold>
    // Hint 2:
    // <editor-fold defaultstate="collapsed">
    // Look at java.util.OptionalInt to get the result.
    // </editor-fold>
    // Hint 3:
    // <editor-fold defaultstate="collapsed">
    // Think about the case where the OptionalInt might be empty
    // (that is, where it has no value).
    // </editor-fold>


    /**
     * Find the longest line in the text file.
     *
     * @throws IOException
     */
    @Test
    public void d5_findLongestLine() throws IOException {
        String longest =
            reader.lines()
                .max(Comparator.comparingInt(String::length))
                .orElse("");
        // Alternative:
        // Instead of Comparator.comparingInt(String::length), one could
        // use something like:
        //     (s1, s2) -> Integer.compare(s1.length(), s2.length())

        assertEquals("Feed'st thy light's flame with self-substantial fuel,", longest);
    }
    // Hint 1:
    // <editor-fold defaultstate="collapsed">
    // Use Stream.max() with a Comparator.
    // </editor-fold>
    // Hint 2:
    // <editor-fold defaultstate="collapsed">
    // Use static methods on Comparator to help create a Comparator instance.
    // </editor-fold>


    /**
     * Select the longest words from the input list. That is, select the words
     * whose lengths are equal to the maximum word length.
     */
    @Test
    public void d6_selectLongestWords() {
        List<String> input = List.of(
            "alfa", "bravo", "charlie", "delta", "echo", "foxtrot", "golf", "hotel");

        int max = input.stream()
            .mapToInt(String::length)
            .max()
            .orElse(-1);

        List<String> result = input.stream()
            .filter(s -> s.length() == max)
            .collect(Collectors.toList());

        assertEquals(List.of("charlie", "foxtrot"), result);
    }
    // Hint:
    // <editor-fold defaultstate="collapsed">
    // Consider making two passes over the input stream.
    // </editor-fold>

    /**
     * Select the list of words from the input list whose length is greater than
     * the word's position in the list (starting from zero) .
     */
    @Test
    public void d7_selectByLengthAndPosition() {
        List<String> input = List.of(
            "alfa", "bravo", "charlie", "delta", "echo", "foxtrot", "golf", "hotel");

        List<String> result =
            IntStream.range(0, input.size())
                .filter(pos -> input.get(pos).length() > pos)
                .mapToObj(pos -> input.get(pos))
                .collect(Collectors.toList());

        assertEquals(List.of("alfa", "bravo", "charlie", "delta", "foxtrot"), result);
    }
    // Hint:
    // <editor-fold defaultstate="collapsed">
    // Instead of a stream of words (Strings), run an IntStream of indexes of
    // the input list, using index values to get elements from the input list.
    // </editor-fold>


// ========================================================
// END OF EXERCISES
// TEST INFRASTRUCTURE IS BELOW
// ========================================================


    private BufferedReader reader;

    @BeforeEach
    public void z_setUpBufferedReader() throws IOException {
        String filePath = Objects.requireNonNull(getClass().getClassLoader().getResource("SonnetI.txt")).getPath();
        reader = Files.newBufferedReader(Paths.get(filePath), StandardCharsets.UTF_8);
    }

    @AfterEach
    public void z_closeBufferedReader() throws IOException {
        reader.close();
    }

}