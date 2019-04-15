package Database;

import Employee.Employee;
import Sample.Globals;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

import Client.Account;
import Client.Client;
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


    static final String queryEmp1 = "select * from employee inner join loginemp on employee.id=loginemp.ide where login = ? and password = ?";

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

    static final String queryClient = "select * from client";

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
            return clients;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    static final String queryNewClient = "insert into client(fname,lname,email) values(?,?,?)";

    public void addNewClient(Client client){
        Connection conn = getConnection();
        System.out.println("adding");
        try{
            PreparedStatement statement = conn.prepareStatement(queryNewClient);
            statement.setString(1,client.getFname());
            statement.setString(2,client.getLname());
            statement.setString(3,client.getEmail());
            if(statement.execute()){
                System.out.println("success");
            }else{
                System.out.println("fail");
            }
            conn.close();

        }  catch (SQLException e) {
            e.printStackTrace();
        }

    }

    static final String queryID = "select client.id, client.fname, client.lname, client.email from client";

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

    static final String queryAccount = "select * from account";

    public Account getAccountInfo(int id) throws SQLException {
        System.out.println("info"+ id);
        Account swap = new Account(-1,-1, "default", -1);
        Connection con = getConnection();
        ResultSet res;
        try {
            PreparedStatement stmnt = con.prepareStatement(queryAccount);
            res = stmnt.executeQuery();
            while (res.next()) {
                System.out.println("kk"+ res.getInt("id"));
                if (res.getInt("idc") == id) {
                    String accountNum = res.getString("accnum");
                    double amount = res.getDouble("amount");
                    return new Account(accountNum,amount);
                }
            }
            return swap;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return swap;

    }

}