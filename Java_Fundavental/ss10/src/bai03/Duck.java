package bai03;

public class Duck extends Animal implements Swimmable, Flyable {
    public Duck(String name) {
        this.name = name;
    }

    @Override
    public void swim() {
        System.out.println(name + " is swimming.");
    }

    @Override
    public void fly() {
        System.out.println(name + " is flying.");
    }
}
