/*
   Group members
   1. Charupat    Trakulchang         6481176
   2. Pibhu       Chitburanachart     6580195
   3. Thitirat    Kulpornpaisarn      6580871
   4. Chalantorn  Sawangwongchinsri   6580873
*/

package Project1_6580871;
import java.util.*;

class Board {
    private int size;
    private List<Integer> queenPosition;

    public Board(int size) {
        this.size = size;
        this.queenPosition = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            queenPosition.add(-1); 
        }
    }

    public void setQueen(int row, int column) {
        if (row >= 0 && row < size) {
            queenPosition.set(row, column);
        }
    }

    public void show() {
        for (int i = 0; i < size; i++) {
            System.out.print("row" + (i + 1) + " ");  
            for (int j = 0; j < size; j++) {
                if (queenPosition.get(i) == j) {
                    System.out.print(" Q ");  
                }
                else {
                    System.out.print(" . ");  
                }
            }
            System.out.println();
        }
        System.out.println(); 
    }

    public List<Integer> getQueenPositions() {
        return queenPosition;
    }

    public int getSize() {
        return size;
    }
}

class QueenPlacement {
    private Board board;
    private int solutionCount = 0;
    private boolean foundMatch = false;

    public QueenPlacement(int size) {
        this.board = new Board(size);
    }

    public Board getBoard() {
        return board;  
    }

    public boolean place(int k, int i) {
        List<Integer> positions = board.getQueenPositions();
        for (int j = 0; j < k; j++) {
            if (positions.get(j) == i || Math.abs(positions.get(j) - i) == Math.abs(j - k)) {
                return false;
            }
        }
        return true;
    }

    public void solveNQueens(int firstQueenRow, int firstQueenCol, boolean isManual) {
        int n = board.getSize();
        Stack<int[]> stack = new Stack<>();
        stack.push(new int[] {0, -1}); 

        while (!stack.isEmpty()) {
            int[] position = stack.pop();
            int row = position[0];
            int col = position[1] + 1;

            while (col < n && !place(row, col)) {
                col++;
            }

            if (col < n) {
                board.setQueen(row, col);
                stack.push(new int[] {row, col}); 

                if (row == n - 1) { 
                    if (!isManual || board.getQueenPositions().get(firstQueenRow) == firstQueenCol) {
                        foundMatch = true;
                        solutionCount++;
                        System.out.println("==============================");
                        System.out.println("Solution " + solutionCount + ":");
                        board.show();  
                    }
                }
                else stack.push(new int[] {row + 1, -1}); 

            }
            else board.setQueen(row, -1); 

        }
    }

    public boolean isMatch() {
        return foundMatch;
    }
}

class NQueensProgram {
    private Scanner scanner;
    private QueenPlacement queenPlacement;

    public NQueensProgram() {
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        while (true) {
            try {
                int n = getBoardSize();
                boolean isManual = askManualPlace();
                int firstQueenRow = -1, firstQueenCol = -1;

                if (isManual) {
                    firstQueenRow = getQueenPosition("row", n);
                    firstQueenCol = getQueenPosition("column", n);
                }

                // Initialize and display the board with the user's queen
                queenPlacement = new QueenPlacement(n);
                if (isManual) {
                    queenPlacement.getBoard().setQueen(firstQueenRow, firstQueenCol);
                    System.out.println("==============================");
                    System.out.println("Board with User's First Queen Placed:");
                    queenPlacement.getBoard().show();
                }

                queenPlacement.solveNQueens(firstQueenRow, firstQueenCol, isManual);

                if (isManual && !queenPlacement.isMatch()) {
                    System.out.println("No solution.");
                }

            }
            catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.next();  
            }

            if (!askContinue()) {
                System.out.println("Exiting the program. Goodbye!");
                break;
            }
        }
        scanner.close();  
    }

    private int getBoardSize() {
        int n = 0;
        while (n < 4) {
            System.out.print("Enter the size of the board (n >= 4)\n=> ");
            n = scanner.nextInt();
            if (n < 4) {
                System.out.println("Board size must be at least 4. Please try again.");
            }
        }
        return n;
    }

    private boolean askManualPlace() {
        String manualPlace;
        do {
            System.out.print("Do you want to manually place the first queen? (y for yes, n for no)\n=> ");
            manualPlace = scanner.next().trim().toLowerCase();
            if (!manualPlace.equals("y") && !manualPlace.equals("n")) {
                System.out.println("Invalid input, please select 'y' or 'n'.");
            }
        } while (!manualPlace.equals("y") && !manualPlace.equals("n"));
        return manualPlace.equals("y");
    }

    private int getQueenPosition(String type, int n) {
        int position;
        do {
            System.out.print("Enter the " + type + " number for the first queen (1 to " + n + ")\n=> ");
            position = scanner.nextInt();
            if (position < 1 || position > n) {
                System.out.println("Invalid " + type + " number. Please try again.");
            }
        } while (position < 1 || position > n);
        return position - 1; 
    }

    private boolean askContinue() {
        System.out.print("Do you want to try again? (y for yes, any other key to exit)\n=> ");
        String tryAgain = scanner.next().trim().toLowerCase();
        return tryAgain.equals("y");
    }
}

public class Project1 {
    public static void main(String[] args) {
        NQueensProgram solver = new NQueensProgram();
        solver.start();
    }
}
