package Employee;

public class Employee {
    private int id;
    private String fname;
    private String lname;
    private int position;
    private int positionId;
    private String nameposit;

    public Employee(int id, String fname, String lname, int position,  int positionId, String nameposit) {
        this.id = id;
        this.fname = fname;
        this.lname = lname;
        this.position = position;
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

    public int getPosition() {
        return position;
    }
    
    public int getPositionId() {
        return positionId;
    }
    
    public String getNameposit(){
        return nameposit;
    }
    
}