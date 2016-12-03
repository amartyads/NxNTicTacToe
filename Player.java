public enum Player
{
    USER(-1),
    CPU(1),
    NONE(0);
    int id;
    private Player(int i){ id = i;}
    public boolean equals(int i) { return id == i; }
    public int getVal(){ return id; }
}
