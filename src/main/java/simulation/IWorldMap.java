package simulation;

public interface IWorldMap {
    int getWidth ();
    int getHeight();
    void run();
    void eat();
    void atTheEndOfDay();
    void reproduce();
}
