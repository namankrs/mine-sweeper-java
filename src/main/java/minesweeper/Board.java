package minesweeper;

import java.util.*;

public class Board {

    public static final String MINE = "\u2620 ";
    public static final String FLAG = "\u2691 ";
    public static final int MINES_COUNT = 20;
    private Integer side;
    private Map<Integer, String> grids;
    private Set<Integer> mines;
    private Set<Integer> revealedGrids;


    public Board(Integer side) {
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


    public void printBoard() {
        System.out.println();
        for (int grid = side * side; grid > 0; grid--) {
            System.out.print(this.grids.get(grid));
            if ((grid - 1) % side == 0) {
                System.out.println();
            }
        }
        System.out.println();
    }

    public boolean revealGrid(Integer gridNumber) {
        if (this.mines.contains(gridNumber)) {
            this.mines.forEach(mineGrid -> this.grids.put(mineGrid, MINE));
            printBoard();
            System.out.println("GAME OVER ...you landed on a mine");
            return false;
        }
        revealedGrids.add(gridNumber);

        Integer neighbouringMinesCount = this.getNeighbouringMinesCount(gridNumber);
        if (neighbouringMinesCount == 0) {
            this.grids.put(gridNumber, FLAG);
            return true;
        }
        this.grids.put(gridNumber, neighbouringMinesCount.toString() + " ");
        return true;
    }

    public boolean hasWon() {
        Set<Integer> toBeCheckedGrids = new HashSet<>(this.revealedGrids);
        toBeCheckedGrids.addAll(mines);
        return toBeCheckedGrids.size() == this.side * this.side;
    }

    private Integer getNeighbouringMinesCount(Integer gridNumber) {
        Set<Integer> neighbours = new HashSet<>(Arrays.asList(gridNumber + side,
                gridNumber - side));
        if (gridNumber % this.side == 0) {
            return getLeftNeighboursCount(gridNumber, neighbours);
        }
        if (gridNumber % side == 1) {
            return getRightNeighboursCount(gridNumber, neighbours);
        }
        return getAllNeighboursCount(gridNumber, neighbours);
    }

    private Integer getAllNeighboursCount(Integer gridNumber, Set<Integer> neighbours) {
        neighbours.addAll(Arrays.asList(gridNumber - 1, gridNumber + side - 1, gridNumber - side - 1,
                gridNumber + 1, gridNumber - side + 1, gridNumber + side + 1));
        neighbours.retainAll(this.mines);
        return neighbours.size();
    }

    private Integer getRightNeighboursCount(Integer gridNumber, Set<Integer> neighbours) {
        neighbours.addAll(Arrays.asList(gridNumber + 1, gridNumber - side + 1, gridNumber + side + 1));
        neighbours.retainAll(this.mines);
        return neighbours.size();
    }

    private Integer getLeftNeighboursCount(Integer gridNumber, Set<Integer> neighbours) {
        neighbours.addAll(Arrays.asList(gridNumber - 1, gridNumber + side - 1, gridNumber - side - 1));
        neighbours.retainAll(this.mines);
        return neighbours.size();
    }
}
