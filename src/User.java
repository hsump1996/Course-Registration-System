import java.io.Serializable;
import java.util.ArrayList;

public abstract class User implements Serializable {

    //Class Instance Variables of User Class
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    static final long serialVersionUID = 4L;


    //Constructor of User class that accepts username and password as parameters
    User(String username, String password) {

        this.username = username;
        this.password = password;

    }

    //Another constructor of User class that accepts username, password, firsName and lastName as parameters
    User(String username, String password, String firstName, String lastName) {

        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;


    }


    //Getter method for first name
    public String getFirstName() {

        return firstName;
    }


    //Getter method for last name
    public String getLastName() {

        return lastName;
    }


    //Getter method for password
    public String getPassword() {
        return password;
    }


    //Getter method for username
    public String getUsername() {
        return username;
    }


    //Setter method for first name
    public void setFirstName(String firstName) {

        this.firstName = firstName;
    }

    //Setter method for last name
    public void setLastName(String lastName) {

        this.lastName = lastName;
    }

    //Setter method for password
    public void setPassword(String password) {
        this.password = password;
    }



    //Setter method for username
    public void setUsername(String username) {

        this.username = username;
    }
}
