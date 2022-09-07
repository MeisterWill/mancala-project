public class Mancala {
    Player player1;
    Player player2;
    Player playerToMove;
    int moveCount;
    int[][] board; //2 by 7 array to represent the board. Each players slots are numbered 0 to 6, 6 being that player's mancala

    public Mancala(){
        //Protocol for starting game,
        //Create mancala, create players with mancala, give them to mancala

        //Initialize the Board
        this.moveCount = 0;
        this.board = new int[2][7];
        for(int i = 0; i < 6; i++){
            board[0][i] = 4;
            board[1][i] = 4;
        }

        //Initialize Players to null
        this.player1 = null;
        this.player2 = null;
        
    }

    public Mancala(Mancala mancala){ //copy constructor, players are kept the same
        this.moveCount = mancala.moveCount;
        this.player1 = mancala.player1.copyPlayer();
        this.player2 = mancala.player2.copyPlayer();
        this.player1.setMancala(this);
        this.player2.setMancala(this);
        if(mancala.getPlayerToMove() == mancala.getPlayer1()){
            this.playerToMove = this.player1;
        }   else{
            this.playerToMove = this.player2;
        }
        this.board = new int[2][7];
        for(int i = 0; i < 6; i++){
            this.board[0][i] = mancala.getBoard()[0][i];
            this.board[1][i] = mancala.getBoard()[1][i];
        }
    }

    public int[][] getBoard(){
        return board;
    }

    public void setPlayerToMove(Player player){
        this.playerToMove = player;
    }

    public boolean isValidMove(int move){
        //Check to see if the given move is valid for a given player
        if(move > 6 || move < 0){
            return false;
        }
        if(playerToMove.equals(player1)){
            return board[0][move] != 0;
        }
        return board[1][move] != 0;
    }

    public boolean hasValidMove(){ //returns true if there is a valid move for the current player to move
        int side;
        if(playerToMove.equals(player1)){
            side = 0;
        }   else{
            side = 1;
        }
        for(int i = 0; i < 6; i++){
            if(board[side][i] != 0){
                return true;
            }
        }
        return false;
    }

    public void setPlayer1(Player player) {
        this.player1 = player;
    }

    public void setPlayer2(Player player) {
        this.player2 = player;
    }

    public Player getPlayerToMove() {
        return playerToMove;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public boolean inProgress(){
        for(int i = 0; i < 6; i++){
            if(board[0][i] != 0 || board[1][i] != 0){
                return true;
            }
        }
        return false;
    }

    public void printBoard(){
        //Prints the board to fit the player to move's perspective 
        if(playerToMove.equals(player1)){
            String result;
            result = String.format("Move: %d\n%s to move:", moveCount, playerToMove.getName());
            result += "\n" + String.format("|  |%2d|%2d|%2d|%2d|%2d|%2d|  |",
                board[1][5],board[1][4],board[1][3],board[1][2],board[1][1],board[1][0]);
            result += "\n" + String.format("|%2d|                 |%2d|",
                board[1][6],board[0][6]);
            result += "\n" + String.format("|  |%2d|%2d|%2d|%2d|%2d|%2d|  |\n",
                board[0][0],board[0][1],board[0][2],board[0][3],board[0][4],board[0][5]);
            System.out.println(result);
        }
        else{
            String result;
            result = String.format("Move: %d\n%s to move:", moveCount, playerToMove.getName());
            result += "\n" + String.format("|  |%2d|%2d|%2d|%2d|%2d|%2d|  |",
                board[0][5],board[0][4],board[0][3],board[0][2],board[0][1],board[0][0]);
            result += "\n" + String.format("|%2d|                 |%2d|",
                board[0][6],board[1][6]);
            result += "\n" + String.format("|  |%2d|%2d|%2d|%2d|%2d|%2d|  |\n",
                board[1][0],board[1][1],board[1][2],board[1][3],board[1][4],board[1][5]);
            System.out.println(result);
        }
    }
    

    public void makeMove(int move){
        moveCount++;
        //Makes a move for the current player to move given an integer slot between 0 and 5
        int side; //keeps track of which side stones are being placed
        boolean onFriendlySide;
        if(player1.equals(playerToMove)){
            side = 0;
        }   else{
            side = 1;
        }
        int slot = move;
        
        //Pick up stones
        int stones = board[side][move];
        board[side][move] = 0;
        //Distribute 1 at a time until empty
        while(stones > 0){
            //target the correct slot
            slot++;
            onFriendlySide= (playerToMove.equals(player1) && side == 0) || (playerToMove.equals(player2) && side == 1);

            if(slot == 7 && onFriendlySide){ //stones cross friendly mancala
                side = (side + 1) % 2;
                slot = 0;
            }   else if(slot == 6 && !onFriendlySide){ //skip enemy mancala for player 1
                side = (side + 1) % 2;
                slot = 0;
            }
            //place stone
            stones--;
            board[side][slot]++;
            
            
        }
        onFriendlySide= (playerToMove.equals(player1) && side == 0) || (playerToMove.equals(player2) && side == 1);

        //if last slot was friendly mancala, player to move doesn't switch
        if(onFriendlySide && slot == 6){
            if(!hasValidMove()){ //Switch back if player doesn't have valid move
            if(playerToMove.equals(player1)){
                playerToMove = player2;
            }
            else if(playerToMove.equals(player2)){
                playerToMove = player1;
            }
        }
            return;
        }

        //if last slot was empty on friendly side for player 1, capture pass turn
        else if(onFriendlySide && board[side][slot] == 1 && board[(side + 1) % 2][5 - slot] != 0){
            int captured = board[side][slot];
            board[side][slot] = 0;
            captured += board[(side + 1) % 2][5 - slot];
            board[(side + 1) % 2][5 - slot] = 0;
            board[side][6] += captured;

            if(playerToMove.equals(player1)){ //pass turn
                playerToMove = player2;
            }
            else if(playerToMove.equals(player2)){
                playerToMove = player1;
            }
        }

        else{
            //pass turn
            if(playerToMove.equals(player1)){
                playerToMove = player2;
            }
            else if(playerToMove.equals(player2)){
                playerToMove = player1;
            }
        }

        if(!hasValidMove()){ //Switch back if player doesn't have valid move
            if(playerToMove.equals(player1)){
                playerToMove = player2;
            }
            else if(playerToMove.equals(player2)){
                playerToMove = player1;
            }
        }
    }

    public static void runGame(Player player1, Player player2){
        Mancala mancala = new Mancala();
        mancala.setPlayer1(player1);
        mancala.setPlayer2(player2);
        player1.setMancala(mancala);
        player2.setMancala(mancala);

        mancala.setPlayerToMove(player1);
        while(mancala.inProgress()){
            mancala.printBoard();
            mancala.makeMove(mancala.playerToMove.getMove());
        }
        System.out.println("GAME COMPLETED");
        mancala.printBoard();
    }

    public static void compareAlgorithms(int iterations, Player player1, Player player2){
        double total = 0;
        for(int i = 0; i < iterations; i++){
            Mancala mancala = new Mancala();
            mancala.setPlayer1(player1);
            mancala.setPlayer2(player2);
            player1.setMancala(mancala);
            player2.setMancala(mancala);

            mancala.setPlayerToMove(player1);
            while(mancala.inProgress()){
                mancala.makeMove(mancala.playerToMove.getMove());
            }
            total += mancala.getBoard()[0][6] - mancala.getBoard()[1][6];
        }   
        System.out.println(player1.getName() + " had an average score advantage of " + (total / iterations) + " over " + player2.getName());
    }

    public static void main(String[] args) {
        RandomPlayer player1 = new RandomPlayer();
        DepthPlayer player2 = new DepthPlayer(1);
        runGame(player1, player2);
    }
}
