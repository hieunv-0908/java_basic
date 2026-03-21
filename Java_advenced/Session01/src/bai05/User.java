package bai05;

public class User {

    private int age;

    public void setAge(int age) throws InvalidAgeException {

        if(age < 0 || age > 120){
            throw new InvalidAgeException("Tuổi không hợp lệ cho đăng ký người dùng!");
        }

        this.age = age;
    }

    public int getAge(){
        return age;
    }
}