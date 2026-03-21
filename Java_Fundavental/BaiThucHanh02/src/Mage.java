public class Mage extends GameCharacter implements ISkill {
    private int mana;

    public Mage(String name, double hp, int attackPower, int mana) {
        super(name, hp, attackPower);
        this.mana = mana;
        count_characters++;
    }

    @Override
    public void attack(GameCharacter target) {
        int damageFinal = getAttackPower() > 5 ? getAttackPower() : getAttackPower() / 2;
        if(mana < 5){
            System.out.println(getName() + " tấn công bằng gậy " + damageFinal + " không đủ mana để sử dụng phép");
        }else{
            System.out.println(getName() + " tấn công bằng hoả cầu thuật " + damageFinal);
        }
        target.takeDamage(damageFinal);
        this.mana -= 5;
        if(mana < 0){
            mana = 0;
        }
        if (mana == 0){
            System.out.println(getName() + " đã hết mana!");
        }
    }


    @Override
    public void useUltimateSkill(GameCharacter target) {
        if(mana < 50){
            System.out.println(getName() + " không đủ mana để sử dụng kỹ năng tối thượng!");
            return;
        }
        System.out.println(getName() + " Sử dụng kỹ năng tối thượng: Đại hoả câu thuật!");
        int ultimateDamage = getAttackPower() * 3;
        mana -= 50;
        target.takeDamage(ultimateDamage);
    }
}
