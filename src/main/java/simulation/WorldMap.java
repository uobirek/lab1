package simulation;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;
import java.util.Vector;

public class WorldMap extends AbstractWorldMap{
    private static final int ANIMALS_NO = 20, PLANTS_NO=100;
    private ArrayList<Animal> animals = new ArrayList<>();
    private LinkedList <Plant> plants = new LinkedList<>();
    private Random random;

    public WorldMap(int width, int height){
        super(width, height);
        random = new Random();

        for (int i=0; i<ANIMALS_NO; i++){
            animals.add(new Animal(getRandomPosition()));
        }
        for (int i=0; i<PLANTS_NO; i++){
            placePlantOnMap();
        }
    }

    private void placePlantOnMap(){
        Vector2D position = getRandomPosition();
        while (isOccupiedByPlant(position)) position = getRandomPosition();
        plants.add(new Plant(getRandomPosition()));
        System.out.println("PLANT");
    }
    private boolean isOccupiedByPlant(Vector2D position){
       return getPlantAtPosition(position) != null;
    }
    private Plant getPlantAtPosition(Vector2D position){
        for (Plant plant: plants){
            if(plant.getPosition().equals(position)) return plant;
        }
        return null;
    }
    public void eat(){
        for (Animal animal: animals){
            Plant plant = getPlantAtPosition(animal.getPosition());
            if(plant!=null) {
                plants.remove(plant);
                placePlantOnMap();
                System.out.println("Animal ate plant at position " + animal.getPosition());
            }
        }
    }
    private Vector2D getRandomPosition (){
        return new Vector2D(random.nextInt(getWidth()), random.nextInt(getHeight()));
    }


    public void run(){
        for (Animal animal : animals){
            animal.move(MapDirection.values()[random.nextInt(MapDirection.values().length)], height, width);
        }

    }
}
