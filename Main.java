import java.util.*;
import java.io.*;
class Main {
  public static void main(String[] args) throws InterruptedException {
    int length = 50;
    int width = 120;
    int maxRounds = 10000;
    Scanner pauser = new Scanner(System.in);
   Cell[][] board = new Cell[length][width];
   //Populate
    for(int i = 0; i < length; i++)
    {
      for(int j = 0; j<width; j++)
      {
        board[i][j] = new Cell();
        if(Math.random() < 0.15) board[i][j].startAlive();
      }
    }
   for(int rounds = 0; rounds < maxRounds; rounds++)
   {
     //Assign States
     for(int i = 0; i < length; i++)
     {
       for(int j = 0; j<width; j++)
       {
         int num = board[i][j].numAdjacent(board, i, j);
         if(num > 3 || num < 2) board[i][j].apoptosis();
         if(num == 3) board[i][j].binaryFission();
       }
     }
     //AT END OF ROUND, SWEEP AND SET DEAD CELLS TO NO ACT, ALIVE CELLS TO ACT
     //Render and cleanup
    
     for(int i = 0; i < length; i++)
     {
       for(int j = 0; j<width; j++)
       {
         System.out.print(board[i][j].render());
         board[i][j].act = board[i][j].alive;
       }
       System.out.println();
     }
     System.out.println("\n Round "+ rounds );
     pauser.nextLine();
     
   }
   
  }
}
 class Cell {
  boolean act;
  boolean alive;
  public  boolean canReproduce()
  {
    return act;
  }
  public Cell()
  {
    act = false;
    alive = false;
  }
  public void startAlive()
  {
   
    act = true;
    alive = true;
  
  }
  public  void binaryFission()
  {
    alive = true;
  }
  public  void apoptosis()
  {
    alive = false;
  }
  public  void ageOfConsent()
  {
    act = true;
  }
  public void phagocytosis()
  {
    act = false;
  }
  public String render()
  {
    if(alive)
    {
      return "X";
    }
    return " ";
  }
    public static int numAdjacent(Cell[][] board, int length, int width)
  {
    int count = 0;
    int maxLength = board.length;
    int maxWidth = board[0].length;
    //Left length
    if(length > 0 && width > 0 && board[length-1][width-1].canReproduce()) count++;
    if(length > 0 && board[length-1][width].canReproduce()) count++;
    if(length > 0 && width < maxWidth-1 && board[length-1][width+1].canReproduce()) count++;
    //Same length
    if(width > 0 && board[length][width-1].canReproduce()) count++;
    if(width < maxWidth-1 && board[length][width+1].canReproduce()) count++;
    //Right Length
    if(length < maxLength-1 && width > 0 && board[length+1][width-1].canReproduce()) count++;
    if(length < maxLength - 1 && board[length+1][width].canReproduce()) count++;
    if(length < maxLength - 1 && width < maxWidth-1 && board[length+1][width+1].canReproduce()) count++;

    return count;
  }
}
