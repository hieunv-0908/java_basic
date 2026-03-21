public class Warrior extends GameCharacter implements ISkill {
    private int armor;

    public Warrior(String name, double hp, int attackPower, int armor) {
        super(name, hp, attackPower);
        this.armor = armor;
        count_characters++;

    }

    @Override
    public void attack(GameCharacter target) {
        System.out.println(getName() + " tấn công với sức mạnh " + getAttackPower());
        target.takeDamage(getAttackPower());
    }

    @Override
    public void takeDamage(int amount) {
        int damageAfterArmor = amount - armor;
        if (damageAfterArmor < 0) {
            damageAfterArmor = 0;
        }
        super.takeDamage(damageAfterArmor);
    }

    @Override
    public void useUltimateSkill(GameCharacter target) {
        System.out.println(getName() + " Sử dụng kỹ năng tối thượng: Cú Đấm Sấm Sét!");
        int ultimateDamage = getAttackPower() * 2;
        this.setHp(getHp() * 0.1);
        target.takeDamage(ultimateDamage);
    }
}
