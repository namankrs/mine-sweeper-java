package minesweeper;

import java.util.HashSet;
import java.util.Set;

public class Mines {
    private Integer minesCount;

    public Mines(Integer minesCount) {
        this.minesCount = minesCount;
    }

    protected Set<Integer> generateRandomMines(Integer side) {
        Set<Integer> mines = new HashSet<>();
        for (int mineCount = 0; mineCount < minesCount; mineCount++) {
            Integer mineIndex = (int) (Math.random() * side * side);
            mines.add(mineIndex);
        }
        return mines;
    }
}

