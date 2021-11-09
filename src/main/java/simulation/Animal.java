package simulation;

public class Animal {
    private Vector2D position;

    public Animal(Vector2D position) {
        this.position = position;
    }

    public Vector2D getPosition() {
        return position;
    }

    public void move (MapDirection direction, int height, int width){

        position = position.add(direction.getUnitVector());
        position = PBC(position);
        System.out.println("Animal moves " + direction + "; new position " + position.toString());
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
}
