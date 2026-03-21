package bai03;

// Class User triển khai interface
class User implements Authenticatable {

    private String password;

    public User(String password) {
        this.password = password;
    }

    @Override
    public String getPassword() {
        return password;
    }
}
