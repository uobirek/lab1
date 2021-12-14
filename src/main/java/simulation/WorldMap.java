package simulation;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class WorldMap extends AbstractWorldMap {
    private final static String statsFile = "stats.json";

    private int animalEnergy;
    private int plantEnergy;
    private int noOfPlants;
    private int dayNumber = 1;

    private List<Animal> animals = new ArrayList<>();
    private final Map<Vector2D, List<Animal>> animalsPositions = new HashMap<>();
    private final Map<Vector2D, Plant> plants = new HashMap<>();
    private final Random random = new Random();
    private SimulationStatistics statistics;

    public WorldMap() {
        super(SimulationParams.getField("width"),SimulationParams.getField("height"));
    }
    public void setSimulation(){
        this.width = SimulationParams.getField("width");
        this.height = SimulationParams.getField("height");

        this.animalEnergy = SimulationParams.getField("animalEnergy");
        this.plantEnergy = SimulationParams.getField("plantEnergy");
        this.noOfPlants = SimulationParams.getField("noOfPlants");
        animals.clear();
        plants.clear();
        for (int i = 0; i < SimulationParams.getField("noOfAnimals"); i++) {
            addAnimal(new Animal(getRandomVector(), animalEnergy));
        }
        for (int i = 0; i < noOfPlants; i++) {
            addNewPlant();
        }
    }

    private Plant getPlantAtPosition(Vector2D position) {
        return plants.get(position);
    }


    @Override
    public void run() {
        System.out.println("Today is day number " + dayNumber);
        animalsPositions.clear();
        animals.forEach(animal -> {
            animal.moveBasedOnGenome();
            placeAnimalOnMap(animal);
        });
    }

    private void placeAnimalOnMap(Animal animal) {
        animalsPositions
                .computeIfAbsent(animal.getPosition(), k -> new LinkedList<>())
                .add(animal);
    }

    private void addAnimal(Animal animal) {
        animals.add(animal);
        placeAnimalOnMap(animal);
    }

    public void eat() {
        animalsPositions.forEach(this::eatPlantAtPosition);
        IntStream.range(1, new Random().nextInt(noOfPlants / 20)).forEach(i -> {
            if (plants.size() < getHeight()*getWidth())  addNewPlant();

        });
    }

    private void eatPlantAtPosition(Vector2D position, List<Animal> animals) {
        if (isOccupiedByPlant(position)) {
            animals
                    .stream()
                    .max(Animal::compareTo)
                    .ifPresent((animal -> {
                        animal.setEnergy(animal.getEnergy() + plantEnergy);
                        plants.remove(animal.getPosition());
                        System.out.println("Animal " + animal.getAnimalID() + " ate plant at position " + animal.getPosition());
                    }));
        }
    }

    private Vector2D getRandomVector() {
        return new Vector2D(random.nextInt(width), random.nextInt(height));
    }

    private void addNewPlant() {
        Vector2D pos = getRandomVector();
        while (isOccupiedByPlant(pos)) pos = getRandomVector();
        plants.put(pos, new Plant(pos));
    }

    private boolean isOccupiedByPlant(Vector2D position) {
        return getPlantAtPosition(position) != null;
    }

    @Override
    public void atTheEndOfDay() {
        animals = animals.stream()
                .map(Animal::aging)
                .map(animal -> animal.setEnergy(animal.getEnergy() - 1))
                .filter(animal -> animal.getEnergy() > 0)
                .collect(Collectors.toList());
        this.dayNumber++;
        createStatistics();
    }

    @Override
    public void reproduce() {
        List<Animal> children = new LinkedList<>();
        animalsPositions.forEach((pos, animals) -> {
            List<Animal> parents = animals.stream()
                    .filter(a -> a.getEnergy() > animalEnergy / 2)
                    .sorted(Collections.reverseOrder())
                    .limit(2)
                    .collect(Collectors.toList());
            if (parents.size() == 2) {
                Animal child = new Animal(parents.get(0), parents.get(1));
                children.add(child);
                System.out.println("Animals at position " + pos + " reproduced. " +
                        "Animal " + child.getAnimalID() + " was born");
            }
        });
        children.forEach(this::addAnimal);
    }

    @Override
    public Map<Vector2D, List<Animal>> getAnimalLocation() {
        return animalsPositions;
    }

    @Override
    public Map<Vector2D, Plant> getPlantLocation() {
        return plants;
    }

    private void createStatistics() {
        statistics = new SimulationStatistics(
                animals.size(),
                plants.size(),
                animals.stream().mapToInt(Animal::getAge).average().orElse(0),
                animals.stream().mapToInt(Animal::getNumberOfChildren).average().orElse(0),
                animals.stream().mapToInt(Animal::getEnergy).average().orElse(0),
                dayNumber
        );
        //System.out.println(statistics);

    }

    public SimulationStatistics getStatistics() {
        return statistics;
    }
}