import java.util.Random;

public class RandomPlayer implements Player {
    Mancala mancala;
    String name;
    Random random;


    public RandomPlayer(){
        this.name = "Random Player";
        this.random = new Random();
    }

    public RandomPlayer(String name){
        this.name = name;
        this.random = new Random();
    }

    @Override
    public Player copyPlayer() {
        return new RandomPlayer(this.name);
    }

    @Override
    public void setMancala(Mancala mancala) {
        this.mancala = mancala;
    }

    @Override
    public int getScore() {
        int[][] board = mancala.getBoard();
        if(this.equals(mancala.getPlayer1())){
            return board[0][6];
        }
        return board[1][6];
    }

    @Override
    public int getMove() {
        int move = random.nextInt(6);
        while(!mancala.isValidMove(move)){
            move = random.nextInt(6);
        }
        return move;
    }

    @Override
    public String getName() {
        return name;
    }

    public static void main(String[] args) {
        RandomPlayer p1 = new RandomPlayer("Will");
        RandomPlayer p2 = new RandomPlayer("Joe");
        Mancala.runGame(p1, p2);
    }

    
}
