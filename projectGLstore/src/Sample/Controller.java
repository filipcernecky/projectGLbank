package Sample;

import Database.Database;
import Employee.Employee;
import javafx.event.ActionEvent;

import javafx.scene.control.TextField;

public class Controller {

    public TextField loginInput;
    public TextField passwordInput;

    public void logIn(ActionEvent actionEvent) {
        String login = loginInput.getText();
        String pass = passwordInput.getText();

        System.out.println(login + pass); 

        Database database = Database.getInstanceOfDatabase();
        Employee emp = database.getEmployee(login,pass);
        if(emp != null){
            System.out.println(emp.getId() + " " + emp.getFname() + " " + emp.getLname() + " " + emp.getPosition());
        }




    }

}