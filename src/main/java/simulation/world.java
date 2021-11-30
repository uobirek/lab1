package simulation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.DoubleToIntFunction;

public class world {
    private static Random random = new Random();
   // private static Animal animal = new Animal(new Vector2D(random.nextInt(100), random.nextInt(100)));
    private static final int DAYS_NUMBER = 6;

    public static void main(String[] args) {
        for (int i=0; i<DAYS_NUMBER; i++){

            Simulation.simulateDay();

        }

    }



}
