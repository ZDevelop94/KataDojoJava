package org.example;

import org.javatuples.Pair;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class GameOfLifeGenerator {

    public String[][] nextGeneration(File file) {
        var arrayBoard = fileToBoard(file);
        Arrays.stream(arrayBoard)
                .toList()
                .forEach(x -> {
                            System.out.println();
                            Arrays.stream(x).toList().forEach(System.out::print);
                        }
                );
        return calculateNextGeneration(arrayBoard);
    }

    private String[][] fileToBoard(File file) {

        String[][] arrayOfLines = new String[4][8];

        try {
            Scanner fileScanner = new Scanner(file);
            int linesScannedCount = 0;
            while (fileScanner.hasNextLine()) {
                String data = fileScanner.nextLine();
                char[] chars = data.toCharArray();

                if (chars.length <= 8) {
                    String[] singleCharOfStrings = new String[chars.length];
                    for (int i = 0; i < chars.length; i++) {
                        singleCharOfStrings[i] = String.valueOf(chars[i]);
                    }//turned into String array just in case I have to map as you cannot map char Arrays (via stream)
                    arrayOfLines[linesScannedCount] = singleCharOfStrings;
                } else {
                    arrayOfLines[linesScannedCount] = new String[]{".", ".", ".", ".", ".", ".", ".", "."};
                }
                linesScannedCount++;
            }
            fileScanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        return arrayOfLines;
    }

    interface GameRules {
        Boolean rule(long amountAlive);
    }

    private String[][] calculateNextGeneration(String[][] arrayBoard) {
        String[][] calculatedBoard = new String[arrayBoard.length][arrayBoard[0].length];

        for (int rowPosition = 0; rowPosition < arrayBoard.length; rowPosition++) {
            for (int columnPosition = 0; columnPosition < arrayBoard[0].length; columnPosition++) {
                CellType nextGenOfCell = nextGenOfCell(arrayBoard, rowPosition, columnPosition);
                calculatedBoard[rowPosition][columnPosition] = nextGenOfCell.value();
            }
        }

        return calculatedBoard;
    }

    private CellType nextGenOfCell(String[][] arrayBoard, int rowPosition, int columnPosition) {
        final GameRules underpopulation = (amount) -> amount < 2;
        final GameRules overcrowding = (amount) -> amount > 3;
        //final GameRules ontoNext = (amount) -> x == 2 || amount == 3; redundant in switch
        final GameRules doNotChangeDeadToAlive = (amount) -> amount != 3;

        System.out.println("\nGGGg");
        System.out.println(arrayBoard.length + " 0length" + arrayBoard[0].length);
        System.out.println(rowPosition);
        System.out.println(columnPosition);

        long aliveNeighbours =
                amountOfAlive(
                        arrayBoard, new Cell(arrayBoard[rowPosition][columnPosition], new Position(rowPosition, columnPosition))
                );

        final int finalColumnPosition = columnPosition;
        return switch (Long.valueOf(aliveNeighbours)) {
            case Long amount when underpopulation.rule(amount) -> new DeadCell();
            case Long amount when overcrowding.rule(amount) -> new DeadCell();
            case Long amount when
                    !isAlive(arrayBoard[rowPosition][finalColumnPosition]) && (doNotChangeDeadToAlive.rule(amount)) ->
                    new DeadCell();
            default -> new AliveCell();
        };
    }

    private long amountOfAlive(String[][] arrayBoard, Cell cell) {

        var upNeighbourAlive =
                isNeighbourAlive(arrayBoard, cell.position.row() - 1, cell.position.column());
        var downNeighbourAlive =
                isNeighbourAlive(arrayBoard, cell.position.row() + 1, cell.position.column());
        var rightNeighbourAlive =
                isNeighbourAlive(arrayBoard, cell.position.row(), cell.position.column() + 1);
        var leftNeighbourAlive =
                isNeighbourAlive(arrayBoard, cell.position.row(), cell.position.column() - 1);
        var upRightNeighbourAlive =
                isNeighbourAlive(arrayBoard, cell.position.row() - 1, cell.position.column() + 1);
        var upLeftNeighbourAlive =
                isNeighbourAlive(arrayBoard, cell.position.row() - 1, cell.position.column() - 1);
        var downRightNeighbourAlive =
                isNeighbourAlive(arrayBoard, cell.position.row() + 1, cell.position.column() + 1);
        var downLeftNeighbour =
                isNeighbourAlive(arrayBoard, cell.position.row() + 1, cell.position.column() - 1);

        List<Pair<Boolean, Boolean>> neighbours = new ArrayList<>();

        neighbours.add(upNeighbourAlive);
        neighbours.add(downNeighbourAlive);
        neighbours.add(rightNeighbourAlive);
        neighbours.add(leftNeighbourAlive);
        neighbours.add(upRightNeighbourAlive);
        neighbours.add(upLeftNeighbourAlive);
        neighbours.add(downRightNeighbourAlive);
        neighbours.add(downLeftNeighbour);

        var neighboursToCount = neighbours.stream().map(neighbour ->
            switch (neighbour) {
                case Pair<Boolean, Boolean> p when p.getValue1() -> false;
                case Pair<Boolean, Boolean> p when !p.getValue0() -> false;
                default -> true;
            }
        );

        return neighboursToCount.filter(f -> f).count();
    }

    /**
     *
     * @param arrayBoard - the game board
     * @param row - the row to choose from the array
     * @param column - the column to choose from the array
     * @return Pair left side indicates if alive and left indicates if program should ignore (no neighbour)
     */
    private Pair<Boolean, Boolean> isNeighbourAlive(String[][] arrayBoard, int row, int column) {
        Optional<String> neighbour;
        try {
            neighbour = Optional.of(arrayBoard[row][column]);
        } catch (Exception e) {
            neighbour = Optional.empty();
        }

        return new Pair<>(isAlive(neighbour.orElse(new DeadCell().value())), neighbour.isEmpty());
    }

    private Boolean isAlive(String cell) {
        return cell.equals(new AliveCell().value());
    }

    abstract public class CellType {
        public abstract String value();
    }

    final public class AliveCell extends CellType {
        public String value() { return "*"; }
    }

    final public class DeadCell extends CellType{
        public String value() { return "."; }
    }
    record Cell(String value, Position position){}
    record Position(int row, int column){}
}
