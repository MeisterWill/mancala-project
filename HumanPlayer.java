import java.util.Scanner;

public class HumanPlayer implements Player {
    Mancala mancala;
    Scanner scanner;
    String name;


    public HumanPlayer(){
        this.scanner = new Scanner(System.in);
        this.name = "Human Player";
    }

    public HumanPlayer(String name){
        this.scanner = new Scanner(System.in);
        this.name = name;
    }

    @Override
    public void setMancala(Mancala mancala) {
        this.mancala = mancala;
    }

    @Override
    public int getMove() {
        System.out.println("Input a number between 1 and 6 to select your slot: ");
        int userIn = scanner.nextInt();
        if(!mancala.isValidMove(userIn - 1)){
            return getMove();
        }
        return userIn - 1;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getScore() {
        int[][] board = mancala.getBoard();
        if(this.equals(mancala.getPlayer1())){
            return board[0][6];
        }
        return board[1][6];
    }
}
