package mich.gwan.katharaka.model;

/**
 * create a user and their attributes.
 */
public class User {

    //User attributes
    private int id;
    private String name;
    private String email;
    private String password;
    //private String station;

    //User getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    //public String getStation(){ return station; }

    //public void setStation(String station) { this.station = station; }
}
