import java.util.Random;

public class ShadowKnight extends GameCharacter implements ISkill {

    private double damageReduction; // %
    private Random random = new Random();

    public ShadowKnight(String name, double hp, int attackPower, double damageReduction) {
        super(name, hp, attackPower);
        this.damageReduction = damageReduction;
    }

    @Override
    public void takeDamage(int amount) {
        int reduced = (int)(amount * (1 - damageReduction));
        super.takeDamage(reduced);

        // 30% phản đòn
        if (random.nextDouble() < 0.3) {
            System.out.println(getName() + " phản đòn!");
        }
    }

    @Override
    public void attack(GameCharacter target) {
        target.takeDamage(getAttackPower());
    }

    @Override
    public void useUltimateSkill(GameCharacter target) {
        System.out.println(getName() + " tăng phòng thủ!");
        damageReduction += 0.2;
    }
}