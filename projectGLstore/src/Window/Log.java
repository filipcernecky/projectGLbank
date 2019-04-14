package Window;

import Client.Client;
import Database.Database;
import Employee.Employee;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import Client.Account;


public class Log<client> {
    public Label name;
    public Label surname;

    public Button logOut;
    public Stage dialogStage;

    public ComboBox clientsNames;
    public Button addNewClient;
    public Label menoClient;
    public Label priezviskoCleint;
    public Label emailClient;

    public ComboBox clientsAccounts;
    public Label accNumber;
    public Label money;

    Database database = Database.getInstanceOfDatabase();
    private List<Client> client;


    public void initialize () throws SQLException {
        this.client = database.getAllClients();
        clients();
    }


    public void showData(Employee emp) {
        name.setText(emp.getFname());
        surname.setText(emp.getLname());
    }

    public void logOut(ActionEvent actionEvent) throws IOException {

        Node node = (Node) actionEvent.getSource();
        dialogStage = (Stage) node.getScene().getWindow();
        dialogStage.close();

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("../FXML/sample.fxml"));
        Parent accountView = fxmlLoader.load();

        Stage stage = new Stage();
        stage.setScene(new Scene(accountView));

        stage.show();
    }

    public void clients() throws SQLException{
        ObservableList<String> list = FXCollections.observableArrayList();

        for (int i=0; i<this.client.size();i++){
            list.add(this.client.get(i).getFname()+" "+this.client.get(i).getLname());
        }
        clientsNames.setItems(list);
        clientsNames.getSelectionModel().select(0);
        showClientsInfo(this.client.get(0).getId());
        showAccountInfo(this.client.get(0).getId());
    }

    public void createNewClient(ActionEvent actionEvent) throws IOException {
        Node node = (Node) actionEvent.getSource();
        dialogStage = (Stage) node.getScene().getWindow();
        dialogStage.close();

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("../FXML/newClient.fxml"));
        Parent accountView = fxmlLoader.load();

        Stage stage = new Stage();
        stage.setScene(new Scene(accountView));

        stage.show();
    }

    public void showClientsInfo() throws SQLException {
        System.out.println("show");
        String toAllNames = clientsNames.getValue().toString();
        System.out.println("showing: "+toAllNames);


        Database database = Database.getInstanceOfDatabase();
        int id = getSelectedClientID();
        System.out.println(id);
        Client clientik = database.getClientInfo(getSelectedClientID());
        menoClient.setText(clientik.getFname());
        priezviskoCleint.setText(clientik.getLname());
        emailClient.setText(clientik.getEmail());
    }

    public int getSelectedClientID() {
        System.out.println("select");
        System.out.println(clientsNames.getSelectionModel().getSelectedIndex());
        int clientsIndex=clientsNames.getSelectionModel().getSelectedIndex();
        System.out.println(clientsIndex);
        return client.get(clientsIndex).getId();
    }

    public void showClientsInfo(int id) throws SQLException {
        Client clientik = database.getClientInfo(getSelectedClientID());
        menoClient.setText(clientik.getFname());
        priezviskoCleint.setText(clientik.getLname());
        emailClient.setText(clientik.getEmail());
    }

    public void showAccountInfo() throws SQLException {
        System.out.println("show");
        String toAllNames = clientsAccounts.getValue().toString();
        System.out.println("showing: "+toAllNames);


        Database database = Database.getInstanceOfDatabase();
        int id = getSelectedClientID();
        System.out.println(id);
        Account ucet = database.getAccountInfo(getSelectedClientID());
        accNumber.setText(ucet.getAccountNumber());
        money.setText(String.valueOf(ucet.getAmount()));
    }

    public void showAccountInfo(int id) throws SQLException {
        Account ucet = database.getAccountInfo(getSelectedClientID());
        accNumber.setText(ucet.getAccountNumber());
        money.setText(String.valueOf(ucet.getAmount()));
    }


}