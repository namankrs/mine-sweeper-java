package minesweeper;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Board board = new Board(10);
        board.printBoard();
        System.out.println("Enter grid number to reveal");
        while (scanner.hasNextInt()){
            Integer chosenGridNumber = scanner.nextInt();
            if(! board.revealGrid(chosenGridNumber)) return;
            board.printBoard();
            if(board.hasWon()){
                System.out.println("YOU WON");
                return;
            }
            System.out.println("Enter grid number to reveal");
        }
    }
}
