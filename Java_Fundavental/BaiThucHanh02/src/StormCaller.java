import java.util.Random;

public class StormCaller extends GameCharacter implements ISkill {

    private Random random = new Random();

    public StormCaller(String name, double hp, int attackPower) {
        super(name, hp, attackPower);
    }

    @Override
    public void attack(GameCharacter target) {
        int damage = getAttackPower() + random.nextInt(30);
        target.takeDamage(damage);

        double regen = 10;
        setHp(getHp() + regen);

        System.out.println(getName() + " gây " + damage + " và hồi 10 HP");
    }

    @Override
    public void useUltimateSkill(GameCharacter target) {
        int burst = getAttackPower() * 3;
        System.out.println(getName() + " gọi bão gây " + burst);
        target.takeDamage(burst);

        // tự mất máu
        super.takeDamage(30);
    }
}