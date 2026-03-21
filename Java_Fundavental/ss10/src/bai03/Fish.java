package bai03;

public class Fish extends Animal implements Swimmable {
    public Fish(String name) {
        this.name = name;
    }

    @Override
    public void swim() {
        System.out.println(name + " is swimming.");
    }
}
