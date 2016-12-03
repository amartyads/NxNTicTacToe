public class BoardDrawer
{
    static void draw(int[][] b,int p1)
    {
        int len = b[0].length;
        System.out.print('\u000C');
        System.out.println();
        for(int i = 0; i < len; ++i)
        {
            char temp;
            for(int j = 0; j < len;++j)
            {
                if(b[i][j] == p1)
                    temp = 'X';
                else if(b[i][j] == 0)
                    temp = ' ';
                else
                    temp = 'O';
                System.out.print(" " + temp + " ");
                if(j < len-1)
                    System.out.print("|");
            }
            System.out.println();
            if(i < len-1)
            {
                for(int k = 0; k < len; ++k)
                {
                    System.out.print(" - ");
                    if(k < len-1)
                        System.out.print("|");
                }
            }
            System.out.println();
        }
    }
}