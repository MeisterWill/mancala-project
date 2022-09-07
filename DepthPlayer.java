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
        return move;
    }

    public int getMoveAtDepth(int depth){ 
        //Recursive Algorithm that evaluates all possible moves and selects the combination of moves that
        //results in the best score difference for the DepthPlayer while both players are evaluating their best moves
        //at decreasing depths.

        //Base Case, Depth is 1. Choose move that results in best score difference for the player getting the move
        int bestScoreDiff = -100;
        int bestMove = 0;
        if(depth == 1){
            for(int i = 5; i >= 0; i--){ //For each possible move, 
                if(mancala.isValidMove(i)){
                    Mancala config = createConfiguration(); 
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
        else{ //depth > 1
            for(int i = 5; i >= 0; i--){ //For each possible move, 
                if(mancala.isValidMove(i)){
                    Mancala config = createConfiguration(); 
                    Player configPlayer;
                    if(config.getPlayerToMove() == config.getPlayer1()){
                        configPlayer = config.getPlayer1(); 
                    }   else{
                        configPlayer = config.getPlayer2(); 
                    }
                    config.makeMove(i);
                    config.makeMove(((DepthPlayer)(config.getPlayerToMove())).getMoveAtDepth(depth - 1));
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

    private Mancala createConfiguration(){
        Mancala config = new Mancala(this.mancala); //Copy the mancala
        boolean player1ToMove;
        if(config.getPlayer1().equals(config.getPlayerToMove())){
            player1ToMove = true;
        }   else{
            player1ToMove = false;
        }
        config.setPlayer1(new DepthPlayer(depth)); //Create new players
        config.setPlayer2(new DepthPlayer(depth));
        config.getPlayer1().setMancala(config);
        config.getPlayer2().setMancala(config);
        if(player1ToMove){
            config.setPlayerToMove(config.getPlayer1());
        }   else{
            config.setPlayerToMove(config.getPlayer2());
        }
        return config;
    }

    public static void main(String[] args) {
        HumanPlayer player1 = new HumanPlayer("Player 1");
        DepthPlayer player2 = new DepthPlayer(2);
        Mancala.runGame(player1, player2);
    }

    
    
}
