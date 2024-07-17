package awesomecucumber.steps;

import awesomecucumber.pages.LoginPage;
import awesomecucumber.utils.DatabaseUtil;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseSteps {
    
    @Given("set the UserName and Password which get from DB")
    public void iFetchDataFromTheDatabase() {
        DatabaseUtil.connectToDatabase("jdbc:mysql://localhost:3306/mydatabase", "root", "admin");
//        ResultSet rs = DatabaseUtil.runQuery("SELECT * FROM my_table");
        ResultSet rs = DatabaseUtil.runQuery("SELECT username, password FROM mydatabase.my_table");
        String username = "";
        String password = "";
        try {
            while (rs.next()) {
                username = rs.getString("username"); // Assuming the column name is 'username'
                password = rs.getString("password"); // Assuming the column name is 'password'
                // Now you can use username and password as needed
                System.out.println("Username: " + username + ", Password: " + password);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        LoginPage.setUserNameDB(username);
        LoginPage.setUserPassDB(password);
        // Process ResultSet
    }

}
