package aclasses;

import wrappers.Coordinates;
import wrappers.Location;
import enums.Country;
import enums.EyeColor;
import enums.HairColor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public abstract class APerson {
    private long id;
    private String name;
    private Coordinates coordinates;
    private java.time.LocalDateTime creationDate;
    private Float height;
    private EyeColor eyeColor;
    private HairColor hairColor;
    private Country nationality;
    private Location location;

    public APerson(){
        this.name = "Test";
        this.id = hashCode();
        this.creationDate = java.time.LocalDateTime.now();
        this.coordinates = new Coordinates((long)1,1);
        this.height = 1f;
        this.eyeColor = EyeColor.BLUE;
        this.hairColor = HairColor.BLACK;
        this.nationality = Country.INDIA;
        this.location = new Location(1, 1f, 1f);
    }

    public APerson(String name, Coordinates coordinates, Float height, EyeColor eyeColor, HairColor hairColor, Country nationality, Location location) {
        this.name = name;
        this.id = hashCode();
        this.coordinates = coordinates;
        this.creationDate = java.time.LocalDateTime.now();
        this.height = height;
        this.eyeColor = eyeColor;
        this.hairColor = hairColor;
        this.nationality = nationality;
        this.location = location;
    }


    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public Float getHeight() {
        return height;
    }

    public EyeColor getEyeColor() {
        return eyeColor;
    }

    public HairColor getHairColor() {
        return hairColor;
    }

    public Country getNationality() {
        return nationality;
    }

    public Location getLocation() {
        return location;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public void setHeight(Float height) {
        this.height = height;
    }

    public void setEyeColor(EyeColor eyeColor) {
        this.eyeColor = eyeColor;
    }

    public void setHairColor(HairColor hairColor) {
        this.hairColor = hairColor;
    }

    public void setNationality(Country nationality) {
        this.nationality = nationality;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void printStats(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        System.out.println("Id: "+ this.id);
        System.out.println("Name: " + this.name);
        System.out.println("Coordinates: "+ this.coordinates.getX().toString()+ ", "+ this.coordinates.getY());
        System.out.println("CreationDate: "+ dtf.format(this.creationDate));
        System.out.println("Height: "+ this.height.toString());
        System.out.println("EyeColor: "+ this.eyeColor.toString());
        System.out.println("HairColor: "+ this.hairColor.toString());
        System.out.println("Nationality: "+ this.nationality.toString());
        System.out.println("Location: "+ this.location.getX().toString() + ", "+ this.location.getY().toString()+", "+ this.location.getZ().toString());
    }

    @Override
    public String toString() {
        return "APerson{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", coordinates=" + coordinates +
                ", creationDate=" + creationDate +
                ", height=" + height +
                ", eyeColor=" + eyeColor +
                ", hairColor=" + hairColor +
                ", nationality=" + nationality +
                ", location=" + location +
                '}';
    }
}
