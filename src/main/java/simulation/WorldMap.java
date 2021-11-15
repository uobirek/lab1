package simulation;

import java.util.*;

public class WorldMap extends AbstractWorldMap{
    private static final int ANIMALS_NO = 20, PLANTS_NO=100;
    private ArrayList<Animal> animals = new ArrayList<>();
    private Map <Vector2D, List<Animal>> animalsPositions = new HashMap<>();
    //private LinkedList <Plant> plants = new LinkedList<>();
    private Map<Vector2D, Plant> plants = new HashMap<>();

    private Random random;

    public WorldMap(int width, int height){
        super(width, height);
        random = new Random();

        for (int i=0; i<ANIMALS_NO; i++){
            Animal animal = new Animal(getRandomPosition());
            animals.add(animal);
            placeAnimalOnMap(animal);
        }
        for (int i=0; i<PLANTS_NO; i++){
            placePlantOnMap();
        }
    }
    private void placeAnimalOnMap(Animal animal){
        List<Animal> animalsAtPosition = animalsPositions.get(animal.getPosition());
        if(animalsAtPosition == null){
            animalsAtPosition = new LinkedList<>();
            animalsPositions.put(animal.getPosition(), animalsAtPosition);
        }
        animalsAtPosition.add(animal);
    }

    private void placePlantOnMap(){
        Vector2D position = getRandomPosition();
        while (isOccupiedByPlant(position)) position = getRandomPosition();
        plants.put(position, new Plant(position));
    }
    private boolean isOccupiedByPlant(Vector2D position){
       return getPlantAtPosition(position) != null;
    }
    private Plant getPlantAtPosition(Vector2D position){
        return plants.get(position);
    }
    public void eat(){
        for (Animal animal: animals){
            if(isOccupiedByPlant(animal.getPosition())) {
                plants.remove(animal.getPosition());
                placePlantOnMap();
                System.out.println("Animal ate plant at position " + animal.getPosition());
            }
        }
    }
    private Vector2D getRandomPosition (){
        return new Vector2D(random.nextInt(getWidth()), random.nextInt(getHeight()));
    }


    public void run(){
        animalsPositions.clear();
        for (Animal animal : animals){
            animal.move(MapDirection.values()[random.nextInt(MapDirection.values().length)], height, width);
            placeAnimalOnMap(animal);
        }

    }
}
