import java.util.Random;

public class BladeMaster extends GameCharacter implements ISkill {

    private double lifeSteal; // % hút máu

    public BladeMaster(String name, double hp, int attackPower, double lifeSteal) {
        super(name, hp, attackPower);
        this.lifeSteal = lifeSteal;
    }

    @Override
    public void attack(GameCharacter target) {
        int damage = getAttackPower();
        target.takeDamage(damage);

        double heal = damage * lifeSteal;
        setHp(getHp() + heal);

        System.out.println(getName() + " gây " + damage + " và hồi " + heal + " HP");
    }

    @Override
    public void useUltimateSkill(GameCharacter target) {
        System.out.println(getName() + " kích hoạt Huyết Kiếm!");
        lifeSteal += 0.2; // tăng hút máu tạm thời
    }
}