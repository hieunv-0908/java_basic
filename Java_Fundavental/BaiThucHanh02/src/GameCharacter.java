import java.util.*;

abstract public class GameCharacter {
    private String name;
    private double hp;
    private int attackPower;
    public boolean status = true; // true: alive, false: dead
    static int count_characters = 0;

    public GameCharacter(String name, double hp, int attackPower) {
        this.name = name;
        this.hp = hp;
        this.attackPower = attackPower;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getHp() {
        return hp;
    }

    public void setHp(double hp) {
        this.hp = hp;
    }

    public int getAttackPower() {
        return attackPower;
    }

    public void setAttackPower(int attackPower) {
        this.attackPower = attackPower;
    }

    public static int getCount_characters() {
        return count_characters;
    }

    public static void setCount_characters(int count_characters) {
        GameCharacter.count_characters = count_characters;
    }

    abstract void attack(GameCharacter target);
    void takeDamage(int amount){
        hp -= amount;
        if (hp < 0){
            hp = 0;
            status = false;
            System.out.println(name + " bị hạ gục!");
        };
    }

    void display_info(){
        System.out.println("Name: " + name);
        System.out.println("HP: " + hp);
        System.out.println("Attack Power: " + attackPower);
    }
}
