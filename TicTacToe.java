import java.util.*;
import java.io.*;
public class TicTacToe
{
    public static void main(String[] args) throws IOException
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String again;
        do
        {
            System.out.print('\u000C');
            System.out.print("Welcome to n x n Tic-Tac-Toe\n");
            System.out.print("Options:\n1.Easy\n2.Medium\n3.Hard\nEnter choice: ");
            int mench;
            while(true)
            {
                try{mench= Integer.parseInt(br.readLine());}
                catch(Exception e){System.out.print("Please choose something from the list! Reenter: ");continue;}
                if(mench > 0 && mench < 4) break;
                else System.out.print("Please choose something from the list! Reenter: ");
            }
            int n;
            System.out.print("Please enter size of board: ");
            while(true)
            {
                n = Integer.parseInt(br.readLine());
                if(n > 2 && n < 11) break;
                else System.out.print("n between 3 and 10 please! Please reenter: ");
            }
            Random rgen = new Random();
            System.out.print("A coin toss will determine who goes first.\n");
            int uch = -1;
            while(true)
            {
                System.out.print("Please enter your choice, heads or tails(H/T): ");
                String ch = br.readLine();
                if(ch.equals("H") || ch.equals("h"))
                    uch = 0;
                else if(ch.equals("T") || ch.equals("t"))
                    uch = 1;
                else
                    System.out.println("Invalid choice!");
                if(uch == 1 || uch == 0)
                    break;
            }
            int play = rgen.nextInt(2);
            Player fp;
            System.out.println("Result of toss was " + (play == 0?'H':'T'));
            if(play == uch)
            {
                System.out.println("You won the toss! You play first!");
                fp = Player.USER;
            }
            else
            {
                System.out.println("You lost the toss! You play second!");
                fp = Player.CPU;
            }
            for(int i = 10;i > 0; i--)
            {
                System.out.print("////");
                try{
                    Thread.sleep(100);
                }
                catch(Exception e){e.printStackTrace();}
            }
            TTTBoard b = null;
            switch(mench)
            {
                case 1:
                    b = new TTTBoardEasy(n,fp,br,rgen);
                    break;
                case 2:
                    b = new TTTBoardMedium(n,fp,br,rgen);
                    break;
                case 3:
                    b = new TTTBoardHard(n,fp,br,rgen);
            }
            b.playGame();
            System.out.print("Another game(Y/N)?: ");
            again = br.readLine();
        }while(again.equals("Y")||again.equals("y"));
        System.out.println("Thanks for playing!");
    }
}