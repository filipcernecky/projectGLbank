package Database;

import Employee.Employee;
import sample.Globals;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.sql.*;

import Client.Account;
import Client.Client;
import Client.Card;
import Client.LoginHistory;
import Client.LoginClient;
import Window.Log;


public class Database {

    private static Database database = new Database();

    private Database(){
    }

    public static Database getInstanceOfDatabase(){
        return database;
    }


    public Connection getConnection(){
        Connection connection;
        try {
            Class.forName(Globals.driver);
            System.out.println("done");
            connection = DriverManager.getConnection(Globals.url, Globals.username, Globals.password);
            return connection;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }


    private static final String queryEmp1 = "select * from employee inner join loginemp on employee.id=loginemp.ide where login = ? and password = ?";

    public Employee getEmployee(String login, String pass){
        Connection con = getConnection();
        ResultSet res;
        try {
            PreparedStatement stmnt = con.prepareStatement(queryEmp1);
            stmnt.setString(1,login);
            stmnt.setString(2,pass);
            res = stmnt.executeQuery();
            while (res.next()){
                int id = res.getInt("id");
                String fname = res.getString("fname");
                String lname = res.getString("lname");
                int position = res.getInt("position");

                Employee employee = new Employee(id,fname,lname,position);
                return employee;
            }
            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;

    }

    private static final String queryClient = "select * from client";

    public List<Client> getAllClients(){
        Connection con = getConnection();
        System.out.println("done");
        ResultSet res;
        List<Client> clients = new ArrayList<>();
        try {
            PreparedStatement stmnt = con.prepareStatement(queryClient);
            res = stmnt.executeQuery();
            while (res.next()){
                int id =res.getInt("id");
                String name = res.getString("fname");
                String surname = res.getString("lname");
                String email = res.getString("email");
                Client client =  new Client(id, name, surname,email);
                clients.add(client);
            }
            con.close();
            return clients;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static final String queryNewClient = "insert into client(fname,lname,email) values(?,?,?)";

    public int addNewClient(Client client){
        Connection conn = getConnection();
        System.out.println("adding");
        int rezult =-1;
        try{
            PreparedStatement statement = conn.prepareStatement(queryNewClient);
            statement.setString(1,client.getFname());
            statement.setString(2,client.getLname());
            statement.setString(3,client.getEmail());
            if(statement.execute()){
                System.out.println("client not created");
                conn.close();
                return rezult;
            }else{
                System.out.println("client created");
                ResultSet result = statement.getGeneratedKeys();
                if (result .next()){
                    rezult = result .getInt(1);
                    System.out.println("new id: "+rezult);
                    return rezult;
                }
                conn.close();
                return rezult;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rezult;
    }


    private static final String queryID = "select client.id, client.fname, client.lname, client.email from client";

    public Client getClientInfo(int id) throws SQLException {
        System.out.println("InfoofClient"+ id);
        Client swap = new Client(-1,"default", "default", "default");
        Connection con = getConnection();
        ResultSet res;
        try {
            PreparedStatement stmnt = con.prepareStatement(queryID);
            res = stmnt.executeQuery();
            while (res.next()) {
                System.out.println("ffs"+ res.getInt("id"));
                if (res.getInt("id") == id) {
                    String name = res.getString("fname");
                    String surname = res.getString("lname");
                    String email = res.getString("email");
                    return new Client(name, surname, email);
                }
            }
            return swap;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return swap;

    }

    private static final String queryAccount = "select * from account where idc =?";

    public List<Account> getAllAccounts(int idClient){
        Connection con = getConnection();
        ResultSet result;
        List<Account> accounts = new ArrayList<>();
        try {
            PreparedStatement stmnt = con.prepareStatement(queryAccount);
            stmnt.setInt(1,idClient);
            result = stmnt.executeQuery();
            while (result.next()){
                int id = result.getInt("id");
                int idc = result.getInt("idc");
                String accountNum = result.getString("accountnum");
                double amount = result.getDouble("amount");
                Account account = new Account(id, idc, accountNum, amount);
                accounts.add(account);
            }
            con.close();
            return accounts;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static final String queryNewAccount = "insert into account(idc,accNum,amount) values(?,?,?)";

    public boolean addNewAccount(int idc, String number){
        Connection conn = getConnection();
        System.out.println("creating in process");
        try{
            PreparedStatement statement = conn.prepareStatement(queryNewAccount);
            statement.setInt(1,idc);
            statement.setString(2,number);
            statement.setDouble(3,0);
            if(statement.execute()){
                conn.close();
                System.out.println("account not there");
                return false;
            }else{
                conn.close();
                System.out.println("account there");
                return true;
            }

        }  catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static final String queryCard = "select * from card where ida =?";

    public List<Card> getAllCards(int idAcc){
        Connection con = getConnection();
        System.out.println("Connectin Done");
        ResultSet res;
        List<Card> cards = new ArrayList<>();
        try {
            PreparedStatement word = con.prepareStatement(queryCard);
            word.setInt(1,idAcc);
            res = word.executeQuery();
            while (res.next()){
                int id = res.getInt("id");
                int ida = res.getInt("ida");
                String pin = res.getString("pin");
                boolean active = res.getBoolean("active");
                int expireM = res.getInt("expireM");
                int expireY = res.getInt("expireY");
                Card card = new Card(id, ida,pin,active,expireM,expireY);
                cards.add(card);

            }
            con.close();
            return cards;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    private static final String queryNewCard = "insert into card(ida,pin,active,expireM,expireY) values(?,?,?,?,?)";

    public boolean addNewCard(int ida,String pin, int month, int year){
        Connection conn = getConnection();
        System.out.println("creating new card");
        try{
            PreparedStatement statement = conn.prepareStatement(queryNewCard);
            statement.setInt(1,ida);
            statement.setString(2,pin);
            statement.setBoolean(3,true);
            statement.setInt(4,month);
            statement.setInt(5,year);
            if(statement.execute()){
                conn.close();
                System.out.println("card does not exist");
                return false;
            }else{
                conn.close();
                System.out.println("card exists");
                return true;
            }

        }  catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    static final String queryAccountNumber = "select count(id) as countId from account where accnum = ?";

    public boolean accountNumberExist(String accNum){
        Connection conn = getConnection();
        ResultSet res;
        try {
            PreparedStatement stmnt = conn.prepareStatement(queryAccountNumber);
            stmnt.setString(1,accNum);
            res = stmnt.executeQuery();
            res.next();
            int count = res.getInt("countId");
            conn.close();
            if (count == 0){
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    static final String queryLoginClient = "select count(login) as countLogin from loginclient where login = ?";

    public boolean loginClientExist(String login){
        Connection conn = getConnection();
        ResultSet res;
        try {
            PreparedStatement stmnt = conn.prepareStatement(queryLoginClient);
            stmnt.setString(1,login);
            res = stmnt.executeQuery();
            res.next();
            int count = res.getInt("countLogin");
            conn.close();
            if (count == 0){
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    private static final String queryNewLoginClient = "insert into loginclient(idc,login,password) values(?,?,?)";

    public boolean addNewLoginClient(int clientID){
        Connection conn = getConnection();
        System.out.println("creating log");
        String login;
        String password;
        try{
            PreparedStatement statement = conn.prepareStatement(queryNewLoginClient);
            statement.setInt(1,clientID);
            login = Log.generatingLogin();
            password =Log.generatingPass();
            if (login.equals("")){
                System.out.println("log empty");
                return false;
            }
            statement.setString(2,login);
            statement.setString(3,password);
            if(statement.execute()){
                conn.close();
                System.out.println("log created");
                return true;
            }else{
                conn.close();
                System.out.println("log not created");
                return false;
            }
        }  catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static final String queryLoginClientLbl = "select login from loginclient where idc = ?";

    public String userNameLoginClient(int clientID){
        Connection conn = getConnection();
        ResultSet res;
        String login = null;
        try {
            PreparedStatement stmnt = conn.prepareStatement(queryLoginClientLbl);
            stmnt.setInt(1,clientID);
            res = stmnt.executeQuery();
            res.next();
            login = res.getString("login");
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return login;
    }

    static final String queryResetPass = "update loginclient set password = ? where idc = ?";

    public String resetIBPass(int clientID) {
        Connection con = getConnection();
        String newPass = Log.generatingPass();
        try {
            PreparedStatement statement = con.prepareStatement(queryResetPass);
            statement.setString(1,newPass);
            statement.setInt(2,clientID);
            statement.executeUpdate();
            return newPass;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return newPass;
    }

    private static final String queryClientLogin = "select * from loginclient where idc =?";

    public List<LoginClient> getAllLogins(int idClient){
        Connection conn = getConnection();
        ResultSet res;
        List<LoginClient> logins = new ArrayList<>();
        try {
            PreparedStatement stmnt = conn.prepareStatement(queryClientLogin);
            stmnt.setInt(1,idClient);
            res = stmnt.executeQuery();
            while (res.next()){
                int id = res.getInt("id");
                int idc = res.getInt("idc");
                String login = res.getString("login");
                String pass = res.getString("password");
                LoginClient loginClient = new LoginClient(id, idc, login,pass);
                logins.add(loginClient);
            }
            conn.close();
            return logins;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return logins;
    }

    private static final String queryBlockByEmp = "insert into loginhistory(idl) select id from loginclient where idc = ?";

    public void blockByEmp(int idClient){
        Connection con = getConnection();
        System.out.println("IB blocked");
        try {
            PreparedStatement statement = con.prepareStatement(queryBlockByEmp);
            statement.setInt(1, idClient);
            System.out.println(idClient);
            statement.execute();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static final String queryLastrecord = "select * from loginhistory where idl = (select id from loginclient where idc = ?)order by UNIX_TIMESTAMP(logDate) desc limit 1";

    public boolean isIBblock(int Client){
        Connection con = getConnection();
        System.out.println("last record");
        String isSuccess;
        try {
            PreparedStatement statement = con.prepareStatement(queryLastrecord);
            statement.setInt(1, Client);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()) {
                isSuccess = resultSet.getString("success");
                System.out.println(isSuccess);
                if (isSuccess == null) {
                    return false;
                } else {
                    System.out.println(isSuccess);
                    return true;
                }
            }
            System.out.println("no record found");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    private static final String queryUnblockByEmp = "insert into loginhistory(idl,success) values((select id from loginclient where idc = ?),1)";

    public void unblockByEmp(int clientID){
        Connection conn = getConnection();
        System.out.println("IB unblocked");
        try{
            PreparedStatement statement = conn.prepareStatement(queryUnblockByEmp);
            statement.setInt(1,clientID);
            statement.execute();
            conn.close();
        }  catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static final String queryBlockCard = "select * from card where ida = (select id from account where idc = ?)";

}