package model;

public class Contact {

    private String name;
    private String email;
    private String phone;


    public Contact(String n, String e, String p) {
        this.name = n;
        this.email = e;
        this.phone = p;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }
}
