package ro.ase.acs.classes;


import java.io.Serial;
import java.io.Serializable;

public class Team implements Comparable<Team>, Serializable {
    @Serial
    private static final long serialVersionUID = -1966657669200736715L;
    @Serial
    private static int position = 1;
    private String name;
    private int points;
    private int GF;
    private int GA;
    private int GD;

    public Team(String name, int points, int GF, int GA, int GD) {
        this.name = name;
        this.points = points;
        this.GF = GF;
        this.GA = GA;
        this.GD = GD;
    }

    public static int getPosition() {
        return position;
    }

    public static void setPosition() {
        position++;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getGF() {
        return GF;
    }

    public void setGF(int GF) {
        this.GF = GF;
    }

    public int getGA() {
        return GA;
    }

    public void setGA(int GA) {
        this.GA = GA;
    }

    public int getGD() {
        return GD;
    }

    public void setGD(int GD) {
        this.GD = GD;
    }

    @Override
    public int compareTo(Team other) {
        if (this.points != other.points) {
            return Integer.compare(other.points, this.points);
        } else if (this.GD != other.GD) {
            return Integer.compare(other.GD, this.GD);
        } else if (this.GF != other.GF) {
            return Integer.compare(other.GF, this.GF);
        } else {
            return this.name.compareTo(other.name);
        }
    }

    @Override
    public String toString() {
        position++;
        return position + ", " + name + ", " + points + ", " + GF + ", " + GA + ", " + GD + System.lineSeparator();
    }
}
