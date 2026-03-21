package bai04;

public class Main {
    public static void main(String[] args) {
        interface RemoteControl {
            void powerOn();

            default void checkBattery() {
                System.out.println("Pin ổn định");
            }
        }
                RemoteControl smartLight = new RemoteControl() {
                    @Override
                    public void powerOn() {
                        System.out.println("Đèn đã bật");
                    }
                };

                smartLight.powerOn();
                smartLight.checkBattery();
    }
}
