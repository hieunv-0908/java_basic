public class User {
    private int age;

    public User(int age) {
        this.age = age;
    }

    public void setAge(int age) {
        try{
            if(age < 0){
                throw new IllegalArgumentException("Tuổi không thể âm");
            }else{
                this.age = age;
            }
        }catch(IllegalArgumentException iae){
            System.out.println(iae.getMessage());
        }
    }

    public int getAge() {
        return age;
    }
}
