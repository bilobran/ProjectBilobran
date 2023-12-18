package org.example;


import com.sun.net.httpserver.HttpServer;

import java.io.*;
import java.net.InetSocketAddress;
import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 100);

        server.createContext("/", exchange -> {
            StringBuilder response = new StringBuilder();
            response.append("<html><body>");
            response.append("<center><h1>PLEASE SELECT A TAG</h1></center>");
            response.append("<font size= 5><p>/employees</p>");
            response.append("<font size= 5><p>/departments</p>");
            response.append("<font size= 5><p>/employees/names</p>");
            response.append("<font size= 5><p>/all</p>");
            response.append("</body></html>");
            exchange.sendResponseHeaders(200, response.length());
            OutputStream responseBody = exchange.getResponseBody();
            responseBody.write(response.toString().getBytes());

        });
        server.createContext("/employees", exchange -> {
            try {
                List<Employee> employees = Employee.emplGetFromDatabase();
                StringBuilder response = new StringBuilder();
                response.append("<html><body>");
                response.append("<center><h1>EMPLOYEES</h1></center>");
                for (Employee employee : employees) {
                    response.append("<center>").append("<font size= 3 color= blue>")
                            .append("<p>")
                            .append("<b>")
                            .append(employee)
                            .append("</b>").append("</p>").append("</center>");
                }
                response.append("</body></html>");
                exchange.sendResponseHeaders(200, response.length());
                OutputStream responseBody = exchange.getResponseBody();
                responseBody.write(response.toString().getBytes());



            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });


        server.createContext("/departments", exchange -> {
            List<Department> departments = Department.depGetFromDatabase();
            StringBuilder response = new StringBuilder();
            response.append("<html><body>");
            response.append("<center><h1>DEPARTMENTS</h1></center>");
            for (Department department : departments) {
                response.append("<center>").append("<font size= 3 color= red>")
                        .append("<p>")
                        .append("<b>")
                        .append(department)
                        .append("</b>").append("</p>").append("</center>");

            }
            response.append("</body></html>");
            exchange.sendResponseHeaders(200, response.length());
            OutputStream responseBody = exchange.getResponseBody();
            responseBody.write(response.toString().getBytes());

        });

        server.createContext("/employees/names", exchange -> {
            try {
                List<Employee> employees = Employee.emplGetFromDatabase();
                StringBuilder response = new StringBuilder();
                response.append("<html><body>");
                response.append("<center><h1>NAMES</h1></center>");
                for (Employee employee : employees) {
                    response.append("<center>").append("<font size= 3 color= grey>")
                            .append("<p>")
                            .append("<b>")
                            .append("ID:")
                            .append(employee.getId()).append(" - ")
                            .append(employee.getFirstName()).append(" ")
                            .append(employee.getLastName())
                            .append("</b>").append("</p>").append("</center>");
                }
                response.append("</body></html>");
                exchange.sendResponseHeaders(200, response.length());
                OutputStream responseBody = exchange.getResponseBody();
                responseBody.write(response.toString().getBytes());

            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });


        server.createContext("/all", exchange -> {
            Map<Department, Employee> all = getAll();
            StringBuilder response = new StringBuilder();
            response.append("<html><body>");
            response.append("<center><h1>EMPLOYEES & DEPARTMENTS</h1></center>");

            for (Map.Entry<Department, Employee> entry : all.entrySet()) {
                response.append("<center>")
                        .append("<p>")
                        .append("<b>")
                        .append(" DEPARTMENT ")
                        .append(entry.getKey().getName().toUpperCase())
                        .append(":")
                        .append(entry.getValue())
                        .append("</b>").append("</p>").append("</center>");
            }
            response.append("</body></html>");
            exchange.sendResponseHeaders(200, response.length());
            OutputStream responseBody = exchange.getResponseBody();
            responseBody.write(response.toString().getBytes());


        });


        server.start();

    }

    private static Map<Department, Employee> getAll() {
        Map<Department, Employee> all = new HashMap<>();

        String url = "jdbc:mysql://localhost:3306/COMPANY";
        String username = "root";
        String password = "17101995";

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String query = "SELECT *  FROM EMPLOYEE RIGHT JOIN DEPARTMENT ON EMPLOYEE.ID_DEPARTMENT = DEPARTMENT.ID";
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(query)) {
                while (resultSet.next()) {
                    Department department = new Department();
                    Employee employee = new Employee();
                    department.setId(resultSet.getInt("ID"));
                    department.setName(resultSet.getString("NAME"));
                    department.setCity(resultSet.getString("CITY"));
                    employee.setId(resultSet.getInt("ID"));
                    employee.setFirstName(resultSet.getString("FIRSTNAME"));
                    employee.setLastName(resultSet.getString("LASTNAME"));
                    employee.setPosition(resultSet.getString("POSITION"));
                    employee.setDateEmployment(resultSet.getString("DATE_EMPLOYMENT"));
                    employee.setIdDepartment(resultSet.getInt("ID_DEPARTMENT"));
                    employee.setIdBoss(resultSet.getInt("ID_BOSS"));
                    employee.setRate(resultSet.getInt("RATE"));
                    employee.setBonus(resultSet.getInt("BONUS"));


                    all.put(department, employee);


                }

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return all;
    }
}