package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Employee {

    private int id;
    private String firstName;
    private String lastName;
    private String position;
    private String dateEmployment;
    private int idDepartment;
    private int idBoss;
    private int rate;
    private int bonus;

    public Employee() {
    }

    public Employee(int id, String firstName, String lastName, String position, String dateEmployment, int idDepartment, int idBoss, int rate, int bonus) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.position = position;
        this.dateEmployment = dateEmployment;
        this.idDepartment = idDepartment;
        this.idBoss = idBoss;
        this.rate = rate;
        this.bonus = bonus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getDateEmployment() {
        return dateEmployment;
    }

    public void setDateEmployment(String dateEmployment) {
        this.dateEmployment = dateEmployment;
    }

    public int getIdDepartment() {
        return idDepartment;
    }

    public void setIdDepartment(int idDepartment) {
        this.idDepartment = idDepartment;
    }

    public int getIdBoss() {
        return idBoss;
    }

    public void setIdBoss(int idBoss) {
        this.idBoss = idBoss;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public int getBonus() {
        return bonus;
    }

    public void setBonus(int bonus) {
        this.bonus = bonus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return id == employee.id && idDepartment == employee.idDepartment && Objects.equals(firstName, employee.firstName) && Objects.equals(lastName, employee.lastName) && Objects.equals(position, employee.position) && Objects.equals(dateEmployment, employee.dateEmployment) && Objects.equals(idBoss, employee.idBoss) && Objects.equals(rate, employee.rate) && Objects.equals(bonus, employee.bonus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, position, dateEmployment, idDepartment, idBoss, rate, bonus);
    }

    @Override
    public String toString() {
        return "Employee - " +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", position='" + position + '\'' +
                ", dateEmployment ='" + dateEmployment + '\'' +
                ", idDepartment=" + idDepartment +
                ", idBoss=" + idBoss +
                ", rate=" + rate +
                ", bonus=" + bonus;
    }

    static List<Employee> emplGetFromDatabase() throws ClassNotFoundException {
        List<Employee> list = new ArrayList<>();
        String url = "jdbc:mysql://localhost:3306/COMPANY";
        String username = "root";
        String password = "17101995";

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String query = "SELECT * FROM employee";
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(query)) {
                while (resultSet.next()) {
                    Employee employee = new Employee();
                    employee.setId(resultSet.getInt("ID"));
                    employee.setFirstName(resultSet.getString("FIRSTNAME"));
                    employee.setLastName(resultSet.getString("LASTNAME"));
                    employee.setPosition(resultSet.getString("POSITION"));
                    employee.setDateEmployment(resultSet.getString("DATE_EMPLOYMENT"));
                    employee.setIdDepartment(resultSet.getInt("ID_DEPARTMENT"));
                    employee.setIdBoss(resultSet.getInt("ID_BOSS"));
                    employee.setRate(resultSet.getInt("RATE"));
                    employee.setBonus(resultSet.getInt("BONUS"));

                    list.add(employee);
                }

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

}
