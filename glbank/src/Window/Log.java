package Window;

import Database.Database;
import Employee.Employee;
import Client.Account;
import Client.Client;
import Client.Card;

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
        fnameClient.setText(subject.getFname());
        lnameCleint.setText(subject.getLname());
        emailClient.setText(subject.getEmail());
    }

    public void fillClientsInfo() throws SQLException {
        int id = clientsNames.getSelectionModel().getSelectedIndex();
        System.out.println("id of user"+ id);
        client = Client.get(id);
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


        if (selAccount == null){
            System.out.println("select index");
            selAccount =1;
            clientsAccounts.getSelectionModel().select(0);
        }

        System.out.println("index: "+selAccount);
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

    public void loadCards() {
        if(testsample ==null || testsample.getCountOfCards() == 0){
            return;
        }
        updateCards();
        testcard =testsample.getCard(0);
        comBoxClientCards.getSelectionModel().select(0);
        showCardInfo();
    }

    private void updateCards(){
        ObservableList<String> list3 = FXCollections.observableArrayList();
        for (Card swap : testsample.getCards()){
            list3.add(String.valueOf(swap.getId()));
        }
        comBoxClientCards.setItems(list3);
    }

    private void showCardInfo(){
        lblPin.setText(this.testcard.getPin());
        lblActive.setText(String.valueOf(this.testcard.isActive()));
        lblExpireDate.setText(this.testcard.getExpireM()+"/"+this.testcard.getExpireY());
        lblAccountNumber.setText(String.valueOf(this.testcard.getIda()));
    }

    public void selectCard(){
        lblPin.setText("");
        lblActive.setText("");
        lblAccountNumber.setText("");
        lblExpireDate.setText("");

        if(testsample == null || this.testsample.getCountOfCards() ==0){
            return;
        }
        int selected = comBoxClientCards.getSelectionModel().getSelectedIndex();
        if (selected<0){
            lblAccNumber.setText("");
            lblMoney.setText("");
            return;
        }
        testcard = testsample.getCard(selected);
        showCardInfo();


    }

    public void createNewCard() {
        if(!this.testsample.addCard()){
            lblMessNewCArd.setText("Card not created");
        }
        lblMessNewCArd.setText("Card created");
        testsample.loadCards();
        updateCards();
        loadCards();
    }

    public static String generatingLogin(){

        Random random = new Random();
        String userName ="";
        for (int i=0;i<7;i++){
            userName = userName + random.nextInt(10);
        }

        System.out.println("login "+userName);
        Database database = Database.getInstanceOfDatabase();
        if (database.loginClientExist(userName)){
            return "";
        }
        else{
            return userName;
        }
    }

    public static String generatingPass(){
        Random random = new Random();
        String userPassowrd ="";
        for (int i=0;i<7;i++){
            userPassowrd = userPassowrd + random.nextInt(26)+65;
        }

        System.out.println("passowrd "+userPassowrd);
        return userPassowrd;
    }

    private void showIBinfo(){
        int id = testsample.getId();
        String userName = database.userNameLoginClient(id);
        lblLogin.setText(userName);
    }


    public void btnResetPass(ActionEvent actionEvent) {
        int id = testsample.getId();
        database.resetIBPass(id);
    }

    public void blockByEmp(ActionEvent actionEvent) {
        int id = testsample.getId();
        if(!checkBoxBlock.isSelected()){
            database.unblockByEmp(id);
        }
        else{
            database.blockByEmp(id);
        }
    }

    public void selectAccountTran(ActionEvent actionEvent) {
        System.out.println("account list");
        if (testsample == null || testsample.countOfAccounts() == 0){
            return;
        }
        int selected  = comBoxTransAccounts.getSelectionModel().getSelectedIndex();
        if (selected<0){
            return;
        }
        testsample = testchmbr.getAccount(selected);
        showAccountsInfo();
        testchmbr.loadCards();
        loadCards();
    }
}