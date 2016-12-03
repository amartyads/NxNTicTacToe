import java.io.*;
import java.util.*;
class TTTBoardMedium implements TTTBoard
{
    int n;
    int[][] board;
    Player curPlayer,starter;
    BufferedReader br;
    Random random;
    public TTTBoardMedium(int n, Player player, BufferedReader b, Random rgen)
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
        int CPUWin = wonInStep(Player.CPU);
        int UserWin = wonInStep(Player.USER);
        int row,col;
        //check for winning move
        if(CPUWin != -1)
        {
            row = CPUWin / n;
            col = CPUWin % n;
            board[row][col] = 1;
        }
        //check for blocking move
        else if(UserWin != -1)
        {
            row = UserWin / n;
            col = UserWin % n;
            board[row][col] = 1;
        }
        //check heuristic
        else
        {
            ArrayList<Integer> coords = new ArrayList<Integer>();
            int maxHeu = Integer.MIN_VALUE;
            for(int i = 0; i < n; i++)
            {
                for(int j = 0; j < n; j++)
                {
                    if(board[i][j] == 0)
                    {
                        board[i][j] = 1;
                        int curHeu = calcHeu();
                        if(maxHeu < curHeu)
                        {
                            maxHeu = curHeu;
                            coords.clear();
                            coords.add(i*n+j);
                        }
                        else if(maxHeu == curHeu)
                        {
                            coords.add(i*n+j);
                        }
                        board[i][j] = 0;
                    }
                }
            }
            int coord = coords.get(random.nextInt(coords.size()));
            row = coord / n;
            col = coord % n;
            board[row][col] = 1;
        }
    }
    private int wonInStep(Player p)
    {
        int sumR,sumC,sumDiag1,sumDiag2;
        int l = p.getVal();
        for(int i = 0; i < n; i++)
        {
            sumR = 0;
            sumC = 0;
            for(int j = 0; j < n; j++)
            {
                sumR+=board[i][j];
                sumC+=board[j][i];
            }
            if(sumR == l*(n-1))
            {
                for(int j = 0; j < n; j++)
                {
                    if(board[i][j] == 0)
                        return (i * n) + j;
                }
            }
            else if(sumC == l*(n-1))
            {
                for(int j = 0; j < n; j++)
                {
                    if(board[j][i] == 0)
                        return (j * n) + i;
                }
            }
        }
        sumDiag1 = 0;
        sumDiag2 = 0;
        for(int i = 0; i < n; i++)
        {
            sumDiag1+=board[i][i];
            sumDiag2+=board[i][n-i-1];
        }
        if(sumDiag1 == l*(n-1))
        {
            for(int i = 0; i < n; i++)
            {
                if(board[i][i] == 0)
                    return (i*n + i);
            }
        }
        else if(sumDiag2 == l*(n-1))
        {
            for(int i = 0; i < n; i++)
            {
                if(board[i][n-i-1] == 0)
                    return (i*n + (n-i-1));
            }
        }
        return -1;
    }
    private int calcHeu()
    {
        int tot = 0,pos = 0,neg = 0;
        //rows
        for(int i = 0; i < n; i++)
        {
            pos = 0;
            neg = 0;
            for(int j = 0; j < n; j++)
            {
                if(board[i][j] > 0)
                    pos++;
                else if(board[i][j] < 0)
                    neg++;
            }
            if(pos > 0 && neg == 0)
            {
                tot += 1 << pos;
            }
            else if(neg > 0 && pos == 0)
            {
                tot -= 1 << neg;
            }
        }
        //columns
        for(int i = 0; i < n; i++)
        {
            pos = 0;
            neg = 0;
            for(int j = 0; j < n; j++)
            {
                if(board[j][i] > 0)
                    pos++;
                else if(board[j][i] < 0)
                    neg++;
            }
            if(pos > 0 && neg == 0)
            {
                tot += 1 << pos;
            }
            else if(neg > 0 && pos == 0)
            {
                tot -= 1 << neg;
            }
        }
        //diag1
        pos = 0;
        neg = 0;
        for(int j = 0; j < n; j++)
        {
            if(board[j][j] > 0)
                pos++;
            else if(board[j][j] < 0)
                neg++;
        }
        if(pos > 0 && neg == 0)
        {
            tot += 1 << pos;
        }
        else if(neg > 0 && pos == 0)
        {
            tot -= 1 << neg;
        }
        pos = 0;
        neg = 0;
        for(int j = 0; j < n; j++)
        {
            if(board[j][n-j-1] > 0)
                pos++;
            else if(board[j][n-j-1] < 0)
                neg++;
        }
        if(pos > 0 && neg == 0)
        {
            tot += 1 << pos;
        }
        else if(neg > 0 && pos == 0)
        {
            tot -= 1 << neg;
        }
        return tot;
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