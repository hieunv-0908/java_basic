public class Patient {
    private String id;
    private String name;
    private int age;

    // Constructor
    public Patient(String id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    // Getter
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    // Setter
    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "ID: " + id + " | Tên: " + name + " | Tuổi: " + age;
    }
}