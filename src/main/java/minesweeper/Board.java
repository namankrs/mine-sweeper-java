package minesweeper;

import java.util.*;

class Board {

    private static final String MINE = "\u2620 ";
    private static final String FLAG = "\u2691 ";
    private static final int MINES_COUNT = 20;
    private Integer side;
    private Map<Integer, String> grids;
    private Set<Integer> mines;
    private Set<Integer> revealedGrids;


    Board(Integer side) {
        this.side = side;
        initialiseBoard();
        this.mines = new Mines(MINES_COUNT).generateRandomMines(side);
        this.revealedGrids = new HashSet<>();
    }

    private void initialiseBoard() {
        this.grids = new HashMap<>(this.side * this.side);
        for (Integer grid = side * side; grid > 0; grid--) {
            this.grids.put(grid, "\u25A2 ");
        }
    }


    void printBoard() {
        System.out.println();
        for (int grid = side * side; grid > 0; grid--) {
            System.out.print(this.grids.get(grid));
            if ((grid - 1) % side == 0) {
                System.out.println();
            }
        }
        System.out.println();
    }

    boolean revealGrid(Integer gridNumber) {
        if (this.mines.contains(gridNumber)) {
            this.mines.forEach(mineGrid -> this.grids.put(mineGrid, MINE));
            printBoard();
            System.out.println("GAME OVER ...you landed on a mine");
            return false;
        }
        revealedGrids.add(gridNumber);

        Set<Integer> neighbours = this.getNeighbours(gridNumber);
        Set<Integer> neighbouringMines = new HashSet<>(neighbours);
        neighbouringMines.retainAll(this.mines);
        Integer neighbouringMinesCount = neighbouringMines.size();


        if (neighbouringMinesCount == 0) {
            this.grids.put(gridNumber, FLAG);
            return true;
        }
        this.grids.put(gridNumber, neighbouringMinesCount.toString() + " ");
        return true;
    }

    boolean hasWon() {
        Set<Integer> toBeCheckedGrids = new HashSet<>(this.revealedGrids);
        toBeCheckedGrids.addAll(mines);
        return toBeCheckedGrids.size() == this.side * this.side;
    }

    private Set<Integer> getNeighbours(Integer gridNumber) {
        Set<Integer> neighbours = new HashSet<>(Arrays.asList(gridNumber + side,
                gridNumber - side));
        if (gridNumber % this.side == 0) {
            return getLeftNeighbours(gridNumber, neighbours);
        }
        if (gridNumber % side == 1) {
            return getRightNeighbours(gridNumber, neighbours);
        }
        return getAllNeighbours(gridNumber, neighbours);
    }

    private Set<Integer> getAllNeighbours(Integer gridNumber, Set<Integer> neighbours) {
        neighbours.addAll(Arrays.asList(gridNumber - 1, gridNumber + side - 1, gridNumber - side - 1,
                gridNumber + 1, gridNumber - side + 1, gridNumber + side + 1));
        return neighbours;
    }

    private Set<Integer> getRightNeighbours(Integer gridNumber, Set<Integer> neighbours) {
        neighbours.addAll(Arrays.asList(gridNumber + 1, gridNumber - side + 1, gridNumber + side + 1));
        return neighbours;
    }

    private Set<Integer> getLeftNeighbours(Integer gridNumber, Set<Integer> neighbours) {
        neighbours.addAll(Arrays.asList(gridNumber - 1, gridNumber + side - 1, gridNumber - side - 1));
        return neighbours;
    }
}
