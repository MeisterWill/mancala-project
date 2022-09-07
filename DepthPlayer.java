public class DepthPlayer implements Player {

    private String name;
    private Mancala mancala;
    private int depth;

    DepthPlayer(int depth){
        this.depth = depth;
        this.name = "Depth " + depth + " Player";
    }

    DepthPlayer(String name, int depth){
        this.name = name;
        this.depth = depth;
    }

    @Override
    public Player copyPlayer() {
        return new DepthPlayer(this.name, this.depth);
    }

    private int getScoreDiff(){
        if(this.equals(mancala.getPlayer1())){
            return mancala.getPlayer1().getScore() - mancala.getPlayer2().getScore();
        }
        return mancala.getPlayer2().getScore() - mancala.getPlayer1().getScore();
    }

    @Override
    public int getMove() {
        int move = getMoveAtDepth(depth);
        //System.out.println("Best move is " + move);
        return getMoveAtDepth(depth);
    }

    private int getMoveAtDepth(int depth){ 
        //Recursive Algorithm that evaluates all possible moves and selects the combination of moves that
        //results in the best score difference for the DepthPlayer while both players are evaluating their best moves
        //at decreasing depths.

        //Base Case, Depth is 1. Choose move that results in best score difference for the player getting the move
        int bestScoreDiff = -100;
        int bestMove = 0;
        if(depth == 1){
            for(int i = 5; i >= 0; i--){ //For each possible move, 
                if(mancala.isValidMove(i)){
                    Mancala config = new Mancala(this.mancala); 
                    Player configPlayer;
                    if(config.getPlayerToMove() == config.getPlayer1()){
                        configPlayer = config.getPlayer1(); 
                    }   else{
                        configPlayer = config.getPlayer2(); 
                    }
                    config.makeMove(i);
                    if(((DepthPlayer)configPlayer).getScoreDiff() > bestScoreDiff){
                        bestMove = i;
                        bestScoreDiff = ((DepthPlayer)configPlayer).getScoreDiff();
                    }
                }
                
            }
        }
        return bestMove;
    }

    @Override
    public String getName() {
        return name;
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

    public static void main(String[] args) {
        HumanPlayer player1 = new HumanPlayer("Player 1");
        DepthPlayer player2 = new DepthPlayer(1);
        Mancala.runGame(player1, player2);
    }

    
    
}
