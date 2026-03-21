import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        GameCharacter goblin = new GameCharacter("Goblin", 100, 10) {

            @Override
            void attack(GameCharacter target) {
                System.out.println(getName() + " tấn công với sức mạnh " + getAttackPower());
                target.takeDamage(10);
            }

            @Override
            void takeDamage(int amount) {
                setHp(getHp() - amount);
                if (getHp() < 0) {
                    setHp(0);
                    status = false;
                    System.out.println(getName() + " bị hạ gục!");
                }
            }
        };

        GameCharacter[] characters = new GameCharacter[4];
        characters[0] = new Warrior("Yasuo", 200, 40, 30);
        characters[1] = new Mage("Veigar", 150, 50, 100);
        characters[2] = new Archer("Ashe", 220, 35, 0.3, 0.25);
        characters[3] = goblin;

        System.out.println("=== ARENA OF HEROES ===");
        System.out.println("Đã khởi tạo " + GameCharacter.getCount_characters() + " nhân vật tham gia đấu trường.\n");

        GameCharacter yasuo = characters[0];
        GameCharacter veigar = characters[1];
        GameCharacter gob = characters[2];

        Random random = new Random();

        System.out.println("=== ARENA AUTO BATTLE ===");

        int round = 1;

        while (true) {

            // Kiểm tra còn bao nhiêu người sống
            int aliveCount = 0;
            for (GameCharacter c : characters) {
                if (c.status) aliveCount++;
            }

            if (aliveCount <= 1) break;

            System.out.println("\n=== ROUND " + round + " ===");

            // Chọn người tấn công
            GameCharacter attacker;
            while (true) {
                attacker = characters[random.nextInt(characters.length)];
                if (attacker.status) break;
            }

            // Chọn mục tiêu
            GameCharacter target;
            while (true) {
                target = characters[random.nextInt(characters.length)];
                if (target.status && target != attacker) break;
            }

            System.out.println(attacker.getName() + " tấn công " + target.getName());
            attacker.attack(target);

            System.out.println("-> " + target.getName() + " còn " + target.getHp() + " HP");

            round++;
        }
        System.out.println("\n=== KẾT QUẢ ===");
        for (GameCharacter c : characters) {
            if (c.status) {
                System.out.println("🏆 Người chiến thắng: " + c.getName());
            }
        }
    }
}