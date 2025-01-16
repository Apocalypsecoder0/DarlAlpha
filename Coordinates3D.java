
public class Coordinates3D {
    public double x, y, z;

    public Coordinates3D(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double distanceTo(Coordinates3D other) {
        return Math.sqrt(Math.pow(this.x - other.x, 2) + 
                        Math.pow(this.y - other.y, 2) + 
                        Math.pow(this.z - other.z, 2));
    }
}
