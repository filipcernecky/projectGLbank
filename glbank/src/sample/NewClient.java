package sample;

import Client.Client;
import Database.Database;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class NewClient {

    public TextField Fname;
    public TextField Lname;
    public TextField email;


    public void sendingNC(ActionEvent actionEvent) throws IOException {
        String clientsName = Fname.getText();
        String clientsSurname = Lname.getText();
        String clientsEmail = email.getText();

        System.out.println(clientsName + clientsSurname + clientsEmail);

        Client newClient = new Client(clientsName,clientsSurname,clientsEmail);
        Database database = Database.getInstanceOfDatabase();

        Node node = (Node) actionEvent.getSource();
        Stage dialogStage = (Stage) node.getScene().getWindow();
        dialogStage.close();

        int idClient = database.addNewClient(newClient);
        if (idClient <0){
            System.out.println("id not found");
        }else{
            database.addNewLoginClient(idClient);
        }

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("../FXML/log.fxml"));
        Parent accountView = fxmlLoader.load();

        Stage stage = new Stage();
        stage.setScene(new Scene(accountView));

        stage.show();
    }
}