package Window;

import Client.Client;
import Database.Database;
import Employee.Employee;
import Client.Account;

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
import java.util.Random;


public class Log<client> {
    public Label name;
    public Label surname;

    public Button logOut;
    public Stage dialogStage;

    public ComboBox clientsNames;
    public Button addNewClient;
    public Label FnameClient;
    public Label LnameClient;
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
        Client subject = database.getClientInfo(getSelectedClientID());
        menoClient.setText(subject.getFname());
        priezviskoCleint.setText(subject.getLname());
        emailClient.setText(subject.getEmail());
    }

    public void fillClientsInfo() throws SQLException {
        int id = clientsNames.getSelectionModel().getSelectedIndex();
        System.out.println("id of user"+ id);
        client = client.get(id);
        System.out.println("info of user");
        FnameClient.setText(client.getFname());
        LnameClient.setText(client.getLname());
        emailClient.setText(client.getEmail());

        this.account = database.getAllAccounts(client.getId());
        System.out.println("id of client  "+client.getId());

        accounts();
        System.out.println("account list");
        clientsAccounts.getSelectionModel().select(0);

    }

    public int getSelectedClientID() {
        System.out.println("select");
        System.out.println(clientsNames.getSelectionModel().getSelectedIndex());
        int clientsIndex=clientsNames.getSelectionModel().getSelectedIndex();
        System.out.println(clientsIndex);
        return client.get(clientsIndex).getId();
    }

    public void showClientsInfo(int id) throws SQLException {
        Client subject = database.getClientInfo(getSelectedClientID());
        FnameClient.setText(subject.getFname());
        LnameClient.setText(subject.getLname());
        emailClient.setText(subject.getEmail());
    }

    public void accounts() throws SQLException{
        System.out.println("list size: "+ account.size());
        lists = FXCollections.observableArrayList();
        for (int i=0; i<this.account.size();i++){
            lists.add(this.account.get(i).getAccountNumber());
        }
        clientsAccounts.setItems(lists);
        System.out.println("list is full!");


        if (selectedInxAcc == null){
            System.out.println("select index");
            selectedInxAcc =1;
            clientsAccounts.getSelectionModel().select(0);
        }

        System.out.println("index: "+selectedInxAcc);
        showAccountsInfo();
        System.out.println("account info: ");
    }


    public void showAccountInfo() throws SQLException {
        System.out.println("show");
        String toAllNames = clientsAccounts.getValue().toString();
        System.out.println("showing: "+toAllNames);


        Database database = Database.getInstanceOfDatabase();
        int id = getSelectedClientID();
        System.out.println(id);
        Account test = database.getAccountInfo(getSelectedClientID());
        accNumber.setText(test.getAccountNumber());
        money.setText(String.valueOf(test.getAmount()));
    }

    public void showAccountInfo(int id) throws SQLException {
        Account test = database.getAccountInfo(getSelectedClientID());
        accNumber.setText(test.getAccountNumber());
        money.setText(String.valueOf(test.getAmount()));
    }

    public String generetatingAccoutnNumber(){
        Random random = new Random();
        String number ="";
        for (int i=0;i<10;i++){
            number = number + random.nextInt(10);
        }

        for (int i=0;i < this.account.size(); i++){
            if (account.get(i).getAccountNumber().equals(number)){
                System.out.println("accout is already in databesa");
            }
            else {
                System.out.println("creating new account");
                return number;
            }
        }
        return number;
    }

    public void createNewAccount() throws SQLException {
        String numAcc =  generetatingAccoutnNumber();
        database.addNewAccount(client.getId(), numAcc);
        this.account.add(new Account(numAcc));
        accounts();
    }




}