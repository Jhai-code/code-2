import java.util.Scanner;
public class TicTacToe {
    private static final char PLAYER_X = 'X';
    private static final char PLAYER_O = 'O';
    private static final char EMPTY = ' ';
    private char[][] board;
    private Scanner scanner;
    private int xWins = 0, oWins = 0, draws = 0;

    public TicTacToe() {
        board = new char[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = EMPTY;
            }
        }
        scanner = new Scanner(System.in);
    }
    public void printBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(board[i][j]);
                if (j < 2) System.out.print("|");
            }
            System.out.println();
            if (i < 2) System.out.println("-----");
        }
    }
    public boolean check() {
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == board[i][1] && board[i][1] == board[i][2] && board[i][0] != EMPTY) {
                return true;
            }
            if (board[0][i] == board[1][i] && board[1][i] == board[2][i] && board[0][i] != EMPTY) {
                return true;
            }
        }
        if (board[0][0] == board[1][1] && board[1][1] == board[2][2] && board[0][0] != EMPTY) {
            return true;
        }
        if (board[0][2] == board[1][1] && board[1][1] == board[2][0] && board[0][2] != EMPTY) {
            return true;
        }
        return false;
    }
    public boolean isFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }
    public int minimax(int depth, boolean isMaximizing) {
        if (check()) {
            return isMaximizing ? -10 : 10; 
        }
        if (isFull()) {
            return 0; 
        }

        if (isMaximizing) {
            int bestScore = Integer.MIN_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == EMPTY) {
                        board[i][j] = PLAYER_O;
                        int score = minimax(depth + 1, false);
                        board[i][j] = EMPTY;
                        bestScore = Math.max(bestScore, score);
                    }
                }
            }
            return bestScore;
        } else {
            int bestScore = Integer.MAX_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == EMPTY) {
                        board[i][j] = PLAYER_X;
                        int score = minimax(depth + 1, true);
                        board[i][j] = EMPTY;
                        bestScore = Math.min(bestScore, score);
                    }
                }
            }
            return bestScore;
        }
    }
    public int[] findBestMove() {
        int bestScore = Integer.MIN_VALUE;
        int[] bestMove = new int[]{-1, -1};
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == EMPTY) {
                    board[i][j] = PLAYER_O;
                    int score = minimax(0, false);
                    board[i][j] = EMPTY;
                    if (score > bestScore) {
                        bestScore = score;
                        bestMove[0] = i;
                        bestMove[1] = j;
                    }
                }
            }
        }
        return bestMove;
    }
    public void resetBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = EMPTY;
            }
        }
    }
    public void playGame() {
        boolean playing = true;

        while (playing) {
            System.out.println("Choose game mode:\n1. Player vs AI  \n2. Player vs Player  \n3. Exit");
            int mode = scanner.nextInt();

            if (mode == 3) {
                playing = false;
                break;
            }

            resetBoard();
            char currentPlayer = PLAYER_X; 
            while (true) {
                printBoard();
                if (currentPlayer == PLAYER_X || mode == 2) {
                    System.out.print("Enter row (0, 1, 2): ");
                    int row = scanner.nextInt();
                    System.out.print("Enter column (0, 1, 2): ");
                    int col = scanner.nextInt();
                    if (row < 0 || row > 2 || col < 0 || col > 2 || board[row][col] != EMPTY) {
                        System.out.println("Invalid move! Try again.");
                        continue;
                    }
                    board[row][col] = currentPlayer;
                } else {
                    System.out.println("VickyAI is making a move...");
                    int[] move = findBestMove();
                    board[move[0]][move[1]] = PLAYER_O;
                }

                if (check()) {
                    printBoard();
                    System.out.println("Player " + currentPlayer + " wins!");
                    if (currentPlayer == PLAYER_X) xWins++;
                    else oWins++;
                    break;
                }
                if (isFull()) {
                    printBoard();
                    System.out.println("DRAW!");
                    draws++;
                    break;
                }

                currentPlayer = (currentPlayer == PLAYER_X) ? PLAYER_O : PLAYER_X;
            }

            System.out.println("Score: Player X - " + xWins + ", Player O - " + oWins + ", Draws - " + draws);
        }
    }

    public static void main(String[] args) {
        System.out.println("VickyAI X or O Game");
        TicTacToe game = new TicTacToe();
        game.playGame();
    }
}

