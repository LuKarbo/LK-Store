package ar.edu.davinci.carbone_lucas.lk_store.models;

public class UserDTO {
    private String userId;
    private String name;
    private String email;
    private String phoneNumber;
    private String address;
    private boolean isAdmin;

    public UserDTO(String userId, String name, String email, String phoneNumber, String address, boolean isAdmin) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.isAdmin = isAdmin;
    }

    public String getUserId() { return userId; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getAddress() { return address; }
    public boolean isAdmin() { return isAdmin; }
}
