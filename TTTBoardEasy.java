import java.io.*;
import java.util.*;
class TTTBoardEasy implements TTTBoard
{
    int n;
    int[][] board;
    Player curPlayer,starter;
    BufferedReader br;
    Random random;
    public TTTBoardEasy(int n, Player player, BufferedReader b, Random rgen)
    {
        this.n = n;
        board = new int[n][n];
        curPlayer = player;
        starter = curPlayer;
        br = b;
        random = rgen;
    }
    public void playGame()
    {
        while(won().equals(Player.NONE) && !drawn())
            doGameStep();
        Player p = won();
        BoardDrawer.draw(board,starter.getVal());
        if(!(p.equals(Player.NONE)))
        {
            if(p.equals(Player.USER))
                System.out.println("YOU WIN!");
            else
                System.out.println("YOU LOSE!");
        }
        else if(drawn())
        {
            System.out.println("GAME DRAWN!");
        }
    }
    private void doGameStep()
    {
        BoardDrawer.draw(board,starter.getVal());
        if(curPlayer.equals(Player.USER))
        {
            takeInp();
            curPlayer = Player.CPU;
        }
        else
        {
            doCPUMove();
            curPlayer = Player.USER;
        }
    }
    private void takeInp()
    {
        boolean valid = false;
        int row=-1,col=-1;
        System.out.println("Your move!");
        while(!valid)
        {
            try
            {
                System.out.print("Enter row number of move: ");
                row = Integer.parseInt(br.readLine());
                System.out.print("Enter column number of move: ");
                col = Integer.parseInt(br.readLine());
                if(board[row-1][col-1] != 0 || row <1 || col <1 || row >n || col >n)
                    System.out.println("Invalid move!");
                else
                    valid = true;
            }
            catch(Exception e)
            {
                System.out.println("Invalid input!");
            }
        }
        board[row-1][col-1] = -1;
    }
    private void doCPUMove()
    {
        System.out.print("CPU will make a move now. Press enter to proceed: ");
        try{char dummy = (char)br.read();}
        catch(Exception e){e.printStackTrace();}
        while(true)
        {
            int coord = random.nextInt(n*n);
            int row = coord / n;
            int col = coord % n;
            if(board[row][col] == 0)
            {
                board[row][col] = 1;
                break;
            }
        }
    }
    private Player won()
    {
        int sumR,sumC,sumDiag1,sumDiag2;
        for(int i = 0; i < n; i++)
        {
            sumR = 0;
            sumC = 0;
            for(int j = 0; j < n; j++)
            {
                sumR+=board[i][j];
                sumC+=board[j][i];
            }
            if(sumR == n || sumC == n)
                return Player.CPU;
            if(sumR == -n || sumC == -n)
                return Player.USER;
        }
        sumDiag1 = 0;
        sumDiag2 = 0;
        for(int i = 0; i < n; i++)
        {
            sumDiag1+=board[i][i];
            sumDiag2+=board[i][n-i-1];
        }
        if(sumDiag1 == n || sumDiag2 == n)
            return Player.CPU;
        if(sumDiag1 == -n || sumDiag2 == -n)
            return Player.USER;
        return Player.NONE;
    }
    private boolean drawn()
    {
        for(int i = 0; i < n; i++)
        {
            for(int j = 0; j < n; j++)
            {
                if(board[i][j] == 0)
                    return false;
            }
        }
        return true;
    }
}