public class Main {
    public static void main(String[] args) {

        // Khai báo theo yêu cầu
        Animal animal = new Dog();

        // Gọi phương thức chung
        animal.sound();

        // Không gọi được phương thức riêng của Dog
        // animal.wagTail();  // lỗi biên dịch

        // Dùng instanceof và ép kiểu
        if (animal instanceof Dog) {
            Dog dog = (Dog) animal; // ép kiểu
            dog.wagTail();          // gọi được phương thức riêng
        }
    }
}
