
public class Moves implements Cloneable {

    public int x;
    public int y;
    public int x2;
    public int y2;

    public Moves(int x, int y, int x2, int y2) {
        this.x = x; this.y = y;
        this.x2 = x2; this.y2 = y2;
    }

    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) { return null; }
    }

    public String toString() {
        // we do + 1 since array starts at 0 but in this example the coordinates start at 1
        return "(move " + (x+1) + " " + (y+1) + " " + (x2+1) + " " + (y2+1) + ")";
    }

}