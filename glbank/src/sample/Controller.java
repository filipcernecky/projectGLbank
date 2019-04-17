package sample;

import Database.Database;
import Employee.Employee;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller {
    public Button enterBtn;
    public TextField loginInput;
    public TextField passInput;
    public Label wrongName;
    public Label wrongPassword;
    Stage dialogStage = new Stage();
    Employee emp;



    public void logIn(ActionEvent actionEvent) {
        String userName = loginInput.getText();
        String password = passInput.getText();

        System.out.println(userName + password);

        Database database = Database.getInstanceOfDatabase();
        emp = database.getEmployee(userName,password);

        if(emp != null){
            System.out.println(emp.getId() + " " + emp.getFname() + " " + emp.getLname() + " " + emp.getPosition());
            try {

                Node node = (Node)actionEvent.getSource();
                dialogStage = (Stage) node.getScene().getWindow();
                dialogStage.close();

                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("../FXML/log.fxml"));
                Parent accountView = fxmlLoader.load();


                Stage stage = new Stage();
                stage.setScene(new Scene(accountView));

                stage.show();
            } catch (IOException e){
                e.printStackTrace();
            }

        } else {

            wrongName.setVisible(true);
            wrongPassword.setVisible(true);
        }

    }

}