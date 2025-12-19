package model;

import java.util.Date;

public class SportTeams {
    private String nameOfMember;
    private String nameOfTeam;
    private Position position;
    private int Height;
    private int Weight;
    private double Age;

    public SportTeams(String nameOfMember, String nameOfTeam, Position position,
                      int height, int weight, double age) {
        this.nameOfTeam = nameOfTeam;
        this.nameOfMember = nameOfMember;
        this.position = position;
        this.Height = height;
        this.Weight = weight;
        this.Age = age;
    }

    public String getNameOfTeam() {
        return nameOfTeam;
    }

    public String getNameOfMember() {
        return nameOfMember;
    }

    public Position getPosition() {
        return position;
    }

    public int getHeight() {
        return Height;
    }

    public int getWeight() {
        return Weight;
    }

    public double getAge() {
        return Age;
    }

    @Override
    public String toString() {
        return "SportTeams{" +
                "nameOfMember='" + nameOfMember + '\'' +
                ", nameOfTeam='" + nameOfTeam + '\'' +
                ", position='" + position + '\'' +
                ", Height=" + Height +
                ", Weight=" + Weight +
                ", Age=" + Age +
                '}';
    }
}
