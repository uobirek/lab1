package simulation;

public class Simulation {
    private static int width = 30, height=50;
    private static final WorldMap worldMap = new WorldMap(width, height);

    public static WorldMap getWorldMap() {
        return worldMap;
    }
     public static void simulateDay(){

        worldMap.run();
        worldMap.eat();
        worldMap.reproduce();
        worldMap.atTheEndOfDay();
    }

}
