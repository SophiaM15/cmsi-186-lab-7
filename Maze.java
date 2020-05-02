import java.util.stream.Stream;
import static java.util.stream.Collectors.joining;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Maze {

    private final Cell[][] cells;

    private Location initialRatLocation;
    private Location initialCheeseLocation;

    private Maze(String[] lines) {
        var height = lines.length;
        if (height == 0) {
            throw new IllegalArgumentException("Maze has no rows");
        }
        var width = lines[0].length();
        cells = new Cell[height][width];
        for (var row = 0; row < height; row++) {
            var line = lines[row];
            if (line.length() != width) {
                throw new IllegalArgumentException("Non-rectangular maze");
            }
            for (int column = 0; column < width; column++) {
                switch (line.charAt(column)) {
                case 'r':
                    if (initialRatLocation != null) {
                        throw new IllegalArgumentException("Maze can only have one rat");
                    }
                    initialRatLocation = new Location(row, column);
                    cells[row][column] = Cell.RAT;
                    break;
                case 'c':
                    if (initialCheeseLocation != null) {
                        throw new IllegalArgumentException("Maze can only have one cheese");
                    }
                    initialCheeseLocation = new Location(row, column);
                    cells[row][column] = Cell.CHEESE;
                    break;
                case 'w':
                    cells[row][column] = Cell.WALL;
                    break;
                case 'o':
                    cells[row][column] = Cell.OPEN;
                    break;
                default:
                    System.out.println(line.charAt(column));
                    throw new IllegalArgumentException("Illegal characters in maze description");
                }
            }
        }
        if (initialRatLocation == null) {
            throw new IllegalArgumentException("Maze has no rat");
        }
        if (initialCheeseLocation == null) {
            throw new IllegalArgumentException("Maze has no cheese");
        }
    }

    public static Maze fromString(final String description) {
        return new Maze(description.trim().split("\\s+"));
    }

    public static Maze fromFile(final String filename) {
        try {
            return Maze.fromScanner(new Scanner(new File(filename)));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static Maze fromScanner(final Scanner scanner) {
        final var lines = new ArrayList<String>();
        while (scanner.hasNextLine()) {
            lines.add(scanner.nextLine());
        }
        return new Maze(lines.toArray(new String[0]));
    }

    public class Location {
        private final int row;
        private final int column;

        Location(final int row, final int column) {
            this.row = row;
            this.column = column;
        }

        boolean isInMaze() {
            return row >= 0 && row < getWidth() && column >= 0 && column < getHeight();
            // TODO: Fill this in. Return whether the row and column is a legal
            // position in this maze.
        }

        boolean canBeMovedTo() {
            return isInMaze() && cells[row][column] == Cell.OPEN;
            // TODO: Fill this in. You can move to a space only if it is inside the
            // maze and the cell is open or contains the cheese.
        }

        boolean hasCheese() {
            return isInMaze() && cells[row][column] == Cell.CHEESE;
            // TODO: Fill this in. Returns whether the cell has the cheese. You can
            // use the contents() method to help you here.
        }

        Location above() {
            return new Location(row, column - 1);
            // TODO: Fill this in. It should return a new location whose coordinates
            // are (1) the row above this location's row, and (2) the same column.
        }

        Location below() {
            return new Location(row, column + 1);
            // TODO: Fill this in. Return the location directly below this one.
        }

        Location toTheLeft() {
            return new Location(row - 1, column);
            // TODO: Fill this in. Return the location directly to the left of this one.
        }

        Location toTheRight() {
            return new Location(row + 1, column);
            // TODO: Fill this in. Return the location directly to the right of this one.
        }

        void place(Cell cell) {
            cells[row][column] = cell;
        }

        Cell contents() {
            return cells[row][column];
        }

        boolean isAt(final Location other) {
            return row == other.row && column == other.column;
        }
    }

    public static enum Cell {
        OPEN(' '), WALL('\u2588'), TRIED('x'), PATH('.'), RAT('r'), CHEESE('c');

        private char display;

        private Cell(char display) {
            this.display = display;
        }

        public String toString() {
            return Character.toString(display);
        }

        // This needs a constructor and a toString method. You might need to do some
        // research on Java enums.
    }

    public interface MazeListener {
        void mazeChanged(Maze maze);
    }

    public int getWidth() {
        return getWidth();
        // TODO: Fill this in. The information comes from the cells array.
    }

    public int getHeight() {
        return getHeight();
        // TODO: Fill this in
    }

    public Location getInitialRatPosition() {
        return getInitialRatPosition();
        // TODO: Fill this in. It is a typical getter, since you already have a field
        // for the initial rat position.
    }

    public Location getInitialCheesePosition() {
        return getInitialCheesePosition();
        // TODO: Fill this in
    }

    public String toString() {
        return Stream.of(cells)
            .map(row -> Stream.of(row).map(Cell::toString).collect(joining()))
            .collect(joining("\n"));
    }
}
