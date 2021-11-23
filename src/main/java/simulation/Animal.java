package simulation;

import java.util.Random;
import java.util.Vector;

public class Animal
    implements Comparable <Animal> {
    private Vector2D position;
    private int energy;
    private int age=1;
    private final int animalID;
    private static int counter=0;
    private final Genome genome;
    private int numberOfChildren=0;

    public Genome getGenome() {
        return genome;
    }


    public Animal(Vector2D position, int energy) {
        this.position = position;
        this.energy = energy;
        this.animalID=counter++;
        this.genome = new Genome();
    }

    public Animal(Animal mother, Animal father){
        Vector2D move = MapDirection.values()[new Random().nextInt(MapDirection.values().length)].getUnitVector();
        this.position = PBC(mother.getPosition().add(move));
        this.genome = new Genome(mother.getGenome(), father.getGenome());
        this.energy = (mother.getEnergy()+ father.getEnergy())/4;
        this.animalID=counter++;
        mother.setEnergy(mother.getEnergy()*3/4);
        father.setEnergy(father.getEnergy()*3/4);
        mother.increaseNumberOfChildren();
        father.increaseNumberOfChildren();
    }

    public void setPosition(Vector2D position) {
        this.position = position;
    }

    public Animal setEnergy(int energy) {
        this.energy = energy;
        return this;
    }


    public int getEnergy() {
        return energy;
    }

    public int getAnimalID() {
        return animalID;
    }

    public int getAge() {
        return age;
    }
    public Animal aging(){
        age++;
        return this;
    }
    public Vector2D getPosition() {
        return position;
    }

    public void move (MapDirection direction, int height, int width){

        position = position.add(direction.getUnitVector());
        position = PBC(position);
        position = PBC(position);
        System.out.println("Animal " + animalID + " moves " + direction + "; new position " + position.toString()
                + "; energy: "+ energy + "; age "  + age);
    }
    private Vector2D PBC (Vector2D position){
        int width = Simulation.getWorldMap().getWidth();
        int height = Simulation.getWorldMap().getHeight();
        if (position.getX()<0) return position.add (new Vector2D(width, 0));
        if (position.getX()>=0) return position.subtract(new Vector2D(width, 0));
        if (position.getY()<0) return position.add (new Vector2D(0, height));
        if (position.getY()>=0) return position.subtract (new Vector2D(0, height));
        return position;
    }

    @Override
    public int compareTo(Animal animal) {
       return this.getEnergy() == animal.getEnergy()
               ?  this.getAnimalID() - animal.getAnimalID()
               : this.getEnergy() - animal.getEnergy();
    }

    public void increaseNumberOfChildren(){
        numberOfChildren++;
    }
}
