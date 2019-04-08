package Database;

import Employee.Employee;
import Sample.Globals;

import java.sql.*;

public class Database {
    static final String SQL1 = "select * from employee inner join loginemp on employee.id=loginemp.ide where login = ? and password = ?";

    private static Database database = new Database();

    private Database(){
    }

    public static Database getInstanceOfDatabase(){
        return database;
    }

    private Connection getConnection(){ 
        Connection connection;
        try {
            Class.forName(Globals.databClassforName);
            System.out.println("Driver loaded!");
            connection = DriverManager.getConnection(Globals.url, Globals.username, Globals.password);
            return connection;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public Employee getEmployee(String login, String pass){
        Connection con = getConnection();

        ResultSet rs;

        try {
            PreparedStatement stmnt = con.prepareStatement(SQL1);
            stmnt.setString(1,login);
            stmnt.setString(2,pass);
            rs = stmnt.executeQuery();
            while (rs.next()){
                int id = rs.getInt("id");
                String fname = rs.getString("fname");
                String lname = rs.getString("lname");
                int position = rs.getInt("position");

                Employee employee = new Employee(id,fname,lname,position);
                return employee;
            }
            con.close();



        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;


    }

}