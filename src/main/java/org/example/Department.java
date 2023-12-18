package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Department {
    private int id;
    private String name;
    private String city;

    public Department() {
    }

    public Department(int id, String name, String city) {
        this.id = id;
        this.name = name;
        this.city = city;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }


    @Override
    public String toString() {
        return "Department - " +
                "id=" + id +
                ", name='" + name + '\'' +
                ", city='" + city;
    }

    static List<Department> depGetFromDatabase() {
        List<Department> departments = new ArrayList<>();
        String url = "jdbc:mysql://localhost:3306/COMPANY";
        String username = "root";
        String password = "17101995";

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String query = "SELECT * FROM DEPARTMENT";
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(query)) {
                while (resultSet.next()) {
                    Department department = new Department();
                    department.setId(resultSet.getInt("ID"));
                    department.setName(resultSet.getString("NAME"));
                    department.setCity(resultSet.getString("CITY"));
                    departments.add(department);
                }

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return departments;
    }
}
