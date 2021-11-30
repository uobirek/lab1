package simulation;

import java.util.Objects;

public record Vector2D (int x, int y){




    @Override
    public String toString() {
        return String.format("(%d, %d)", x, y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector2D vector2D = (Vector2D) o;
        return x == vector2D.x && y == vector2D.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    public Vector2D add (Vector2D vector2D){
        return new Vector2D(x+ vector2D.x, y+ vector2D.y);
    }
    public Vector2D opposite(){
        return new Vector2D(-x, -y );
    }
    public Vector2D subtract(Vector2D vector2D){
        return add(vector2D.opposite());
    }


}
