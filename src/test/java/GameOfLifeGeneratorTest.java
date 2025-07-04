import org.example.GameOfLifeGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class GameOfLifeGeneratorTest {
    private final GameOfLifeGenerator gameGenerator = new GameOfLifeGenerator();;

    @Test
    @DisplayName("Generate the next generation from a 4 by 8 board")
    void GameOfLifeTest() {
        var file = new File("./src/test/resources/generation4by8.txt");

        String[][] result = gameGenerator.nextGeneration(file);

        String[][] expected =
                new String[][]{
                        {".",".",".",".",".",".",".","."},
                        {".",".",".","*","*",".",".","."},
                        {".",".",".","*","*",".",".","."},
                        {".",".",".",".",".",".",".","."}
        };

        assertArrayEquals(expected, result);

    }

}
