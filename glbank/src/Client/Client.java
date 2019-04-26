package Client;

import java.util.ArrayList;
import java.util.List;
import Database.Database;

public class Client {
    private int id;
    private String fname;
    private String lname;
    private String email;
    private List<Account> accounts = new ArrayList<>();

    public Client(int id, String fname, String lname, String email) {
        this.id = id;
        this.fname = fname;
        this.lname = lname;
        this.email = email;
    }
    public Client (String fname, String lname){
        this.fname = fname;
        this.lname = lname;
    }

    public Client( String fname, String lname, String email) {
        this.fname = fname;
        this.lname = lname;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public String getFname() {
        return fname;
    }

    public String getLname() {
        return lname;
    }

    public String getEmail() { return email;
    }

    public Account getAccount(int id){
        return this.accounts.get(id);
    }

    public boolean addAccount(String accNum){
        Database database = Database.getInstanceOfDatabase();
        return database.addNewAccount(this.id,accNum);
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    public boolean loadAccounts(){
        Database database = Database.getInstanceOfDatabase();
        List<Account> swapAccounts = database.getAllAccounts(this.id);
        if (swapAccounts == null || swapAccounts.size() == 0){
            return false;
        }else {
            accounts = swapAccounts;
            return true;
        }
    }

    public int countOfAccounts(){
        return this.accounts.size();
    }

}