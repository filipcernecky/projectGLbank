package Sample;

import Employee.Employee;
import javafx.scene.control.Label;


public class Account {

    public Label fnameShow;
    public Label lnameShow;
    public Label positShow;

    private Employee employee;

    public void showDataMethod(Employee employee) {
        this.employee=employee;

        fnameShow.setText(employee.getFname());
        lnameShow.setText(employee.getLname());
        positShow.setText(employee.getNameposit());
    }
}