
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.example.FizzBuzzService;

import java.util.List;
import java.util.stream.IntStream;

public class FizzBuzzServiceTest {

    private final FizzBuzzService service = new FizzBuzzService();

    @Test
    @DisplayName("Fizz Buzz Test upto 10")
    void FizzBuzzTest1() {

        var result = IntStream.rangeClosed(1, 10).mapToObj(service::isFizOrBuzz).toList();
        var expectedResult = List.of("1", "2", "Fizz", "4", "Buzz", "Fizz", "7", "8", "Fizz", "Buzz");

        assertEquals(result, expectedResult);
    }

    @Test
    @DisplayName("Fizz Buzz Test from 10 to 20")
    void FizzBuzzTest2() {

        var result = IntStream.rangeClosed(10, 20).mapToObj(service::isFizOrBuzz).toList();
        var expectedResult = List.of("Buzz", "11", "Fizz", "13", "14", "Fizz", "16", "17", "Fizz", "19", "Buzz");

        assertEquals(result, expectedResult);
    }
}
