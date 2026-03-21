public class Student {
    int age;
    String name;
    String phone;
    boolean isActive;

    public Student() {
    }

    public Student(int age, String name, String phone, boolean isActive) {
        this.age = age;
        this.name = name;
        this.phone = phone;
        this.isActive = isActive;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

}
