import java.util.Stack;

public class BacktrackingMazeSolver {

    /**
     * Moves a rat from (x1,y1) to (x2,y2), filling in the cells as it goes, and
     * notifying a listener at each step.
     */
    public boolean solve(Maze maze, int x, int y, Maze.MazeListener listener) {
        var start = maze.new Location(x, y);
        var end = maze.new Location(x, y);
        if (!start.canBeMovedTo()) {
            throw new IllegalArgumentException("Bad start location");
        }
        if (!end.canBeMovedTo()) {
            throw new IllegalArgumentException("Bad end location");
        }
        if (listener == null) {
            throw new IllegalArgumentException("Listener cannot be null");
        }

        end.place(Maze.Cell.CHEESE);
        var path = new Stack<Maze.Location>();
        var current = start;

        // TODO: initialize the current location to the initial rat location

        // Solution loop. At each step, place the rat and notify listener.
        while (true) {
            current.place(Maze.Cell.RAT);
            listener.mazeChanged(maze);

            if (current.isAt(end)) {
                return true;
            }
            // TODO: Place the rat in the current cell

            // TODO: Notify the listener

            // TODO: Did we reach the desired end cell? If so, return true

            // Move to an adjacent open cell, leaving a breadcrumb. If we
            // can't move at all, backtrack. If there's nowhere to backtrack
            // to, we're totally stuck.

            if (current.above().canBeMovedTo()) {
                path.push(current);
                current.place(Maze.Cell.PATH);
                current = current.above();
            } else if (current.toTheRight().canBeMovedTo()) {
                path.push(current);
                current.place(Maze.Cell.PATH);
                current = current.toTheRight();
                // TODO Fill this in
            } else if (current.below().canBeMovedTo()) {
                path.push(current);
                current.place(Maze.Cell.PATH);
                current = current.below();
                // TODO Fill this in
            } else if (current.toTheLeft().canBeMovedTo()) {
                path.push(current);
                current.place(Maze.Cell.PATH);
                current = current.toTheLeft();
                // TODO Fill this in
            } else {
                Location[x][y] = 0;
                return false;
                // TODO Fill this in ... mark this cell TRIED. If the path is
                // empty, return false. Otherwise, back up (by popping from the
                // path that is being built up)

            }
        }
        return false;
    }
}
