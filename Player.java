public interface Player {
    int getMove();
    String getName();
    void setMancala(Mancala mancala);
    int getScore();
    Player copyPlayer();
}
