import java.util.Random;

public class Archer extends GameCharacter implements ISkill {

    private double critChance;     // tỉ lệ chí mạng (0.0 -> 1.0)
    private double dodgeChance;    // tỉ lệ né tránh
    private Random random = new Random();

    public Archer(String name, double hp, int attackPower,
                  double critChance, double dodgeChance) {
        super(name, hp, attackPower);
        this.critChance = critChance;
        this.dodgeChance = dodgeChance;
        count_characters++;
    }

    @Override
    public void attack(GameCharacter target) {
        int damage = getAttackPower();

        // Kiểm tra chí mạng
        if (random.nextDouble() < critChance) {
            damage *= 2;
            System.out.println(getName() + " bắn chí mạng! Gây " + damage + " sát thương!");
        } else {
            System.out.println(getName() + " bắn tên gây " + damage + " sát thương.");
        }

        target.takeDamage(damage);
    }

    @Override
    public void takeDamage(int amount) {

        // Kiểm tra né tránh
        if (random.nextDouble() < dodgeChance) {
            System.out.println(getName() + " đã né được đòn tấn công!");
            return;
        }

        super.takeDamage(amount);
    }

    @Override
    public void useUltimateSkill(GameCharacter target) {
        System.out.println(getName() + " sử dụng kỹ năng tối thượng: Mưa Tên!");
        int ultimateDamage = getAttackPower() * 3;
        target.takeDamage(ultimateDamage);
    }
}