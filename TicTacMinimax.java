import java.util.Scanner;

class TicTacMinimax 
{
    private static char [][] board = new char[3][3];
    //private static int count = 0;
    public static void printBoard()
    {
        System.out.println(
        ("\n" + " " + board[0][0] + " | " + board[0][1] + " | " + board[0][2]  + "\n" + 
        "-----------" + "\n" + " " +
        board[1][0] + " | " + board[1][1] + " | " + board[1][2]  + "\n" +
        "-----------" + "\n" + " " +
        board[2][0] + " | " + board[2][1] + " | " + board[2][2]  
        ));
    }

    static char ai = 'x', human = 'o';

    public static boolean isMovesLeft(char [][] position)
    {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (board [i][j] == ' ')
                    return true;
        return false;
    }

    public static int evaluate(char [][] b)
    {
        for (int row = 0; row < 3; row++)
        {    
            if (b[row][0] == b[row][1] && b[row][1] == b[row][2])
            {
                if (b[row][0] == ai)
                {
                    return 1;
                }
                else if (b[row][0] == human)
                {
                    return -1;
                }
            }
        }
        for (int col = 0; col < 3; col++)
        {    
            if (b[0][col] == b[1][col] && b[1][col] == b[2][col])
            {
                if (b[0][col] == ai)
                {
                    return 1;
                }
                else if (b[0][col] == human)
                {
                    return -1;
                }
            }
    
        }

        if (b[0][0] == b[1][1] && b[1][1] == b[2][2])
        {
            if (b[0][0] == ai)
                return 1;
            else if (b[0][0] == human)
                return -1;
        }
 
        if (b[0][2] == b[1][1] && b[1][1] == b[2][0])
        {
            if (b[0][2] == ai)
                return 1;
            else if (b[0][2] == human)
                return -1;
        }
 
        // Else if none of them have won then return 0
        return 0;
    }

    public static int minimax(char b[][], int depth, Boolean isMax)
    {
        //count++;
        //System.out.println(count);
        int score = evaluate(b);

        if (score == 1)
            return score;
        if (score == -1)
            return score;
        if (isMovesLeft(b) == false)
            return 0;
        
        if (isMax)
        {
            int best = -10;
            
            for (int i = 0; i < 3; i++)
            {
                for (int j = 0; j < 3; j++)
                {
                    // Check if cell is empty
                    if (b[i][j]==' ')
                    {
                        // Make the move
                        b[i][j] = ai;
 
                        // Call minimax recursively and choose
                        // the maximum value
                        best = Math.max(best, minimax(b, depth + 1, !isMax));
 
                        // Undo the move
                        b[i][j] = ' ';
                    }
                }
            }
            //count = 0;
            return best;
        }

        else 
        {
            int best = 10;
            for (int i = 0; i < 3; i++)
            {
                for (int j = 0; j < 3; j++)
                {
                    // Check if cell is empty
                    if (b[i][j]==' ')
                    {
                        // Make the move
                        b[i][j] = human;
 
                        // Call minimax recursively and choose
                        // the maximum value
                        best = Math.min(best, minimax(b, depth + 1, !isMax));
 
                        // Undo the move
                        b[i][j] = ' ';
                    }
                }
            }
            //count = 0;
            return best;
        }
        //return 0;
    }

    public static int[] findBestMove(char b[][])
    {
        int bestVal = -1000;
        int[] bestMove = {-1, -1};

        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                // Check if cell is empty
                if (b[i][j] == ' ')
                {
                    // Make the move
                    b[i][j] = ai;
 
                    // compute evaluation function for this
                    // move.
                    int moveVal = minimax(b, 0, false);
 
                    // Undo the move
                    b[i][j] = ' ';
 
                    // If the value of the current move is
                    // more than the best value, then update
                    // best/
                    if (moveVal > bestVal)
                    {
                        bestMove[0] = i;
                        bestMove[1] = j;
                        bestVal = moveVal;
                        //System.out.println('i');
                    }
                
                }
            }
        }
        return bestMove;
    }


    public static void main(String [] args) throws Exception 
    {
        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                board[i][j] = ' ';
            }
        }
        printBoard();
        
        boolean run = true; 
        boolean playerTurn = true;

        while (run)
        {
            if (playerTurn) 
            {
                Scanner input1 = new Scanner(System.in);
                System.out.print("Enter the row of the position you'd like to place the \"o\" (0 is the first row):");
                
                int row = Integer.parseInt(input1.nextLine());
                Scanner input2 = new Scanner(System.in);
                System.out.print("Enter the column of the position you'd like to place the \"o\" (0 is the first column):");
                int col = Integer.parseInt(input2.nextLine());
                if ((evaluate(board) != 0) || (isMovesLeft(board) == false))
                {
                    run = false;
                }    
                if (board[row][col] == ' ')
                {
                    board[row][col] = human;
                    printBoard();
                    playerTurn = false;
                    
                }
            }

            if ((evaluate(board) != 0) || (isMovesLeft(board) == false))
            {
                run = false;
            }

            if (playerTurn == false)
            {
                int [] bestMoveResult = findBestMove(board);
                //System.out.println(bestMoveResult[0] + "," +bestMoveResult[1]);
                board[bestMoveResult[0]][bestMoveResult[1]] = ai;
                printBoard();
                //System.out.println("worked");
                playerTurn = true; 
            }
            

            if ((evaluate(board) != 0) || (isMovesLeft(board) == false))
            {
                run = false;
            }
        }
  
    }
}
