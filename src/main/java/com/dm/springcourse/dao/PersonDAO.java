package com.dm.springcourse.dao;


import com.dm.springcourse.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class PersonDAO {

   private final JdbcTemplate jdbcTemplate;

   @Autowired
   public PersonDAO(JdbcTemplate jdbcTemplate){
       this.jdbcTemplate = jdbcTemplate;
   }
//    private static final String URL = "jdbc:mysql://localhost:3306/first_db";
//    private static final String USER = "bestuser";
//    private static final String PASSWORD = "bestuser";
//
//    private static Connection connection;
//
//    static {
//        try {
//            Class.forName("com.mysql.jdbc.Driver");
//        } catch (ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//
//        try {
//            connection = DriverManager.getConnection(URL, USER, PASSWORD);
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//
//    }

    public List<Person> index() {

       return jdbcTemplate.query("SELECT * FROM Person", new BeanPropertyRowMapper<>(Person.class));
//        List<Person> people = new ArrayList<>();
//
//        try {
//            Statement statement = connection.createStatement();
//            String SQL = "SELECT * FROM Person";
//            ResultSet resultSet = statement.executeQuery(SQL);
//
//            while (resultSet.next()) {
//                Person person = new Person();
//
//                person.setId(resultSet.getInt("id"));
//                person.setName(resultSet.getString("name"));
//                person.setAge(resultSet.getInt("age"));
//                person.setEmail(resultSet.getString("email"));
//
//                people.add(person);
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//        return people;
    }

    public Person show(int id) {
       return jdbcTemplate.query("SELECT * FROM Person WHERE id=?",
               new BeanPropertyRowMapper<>(Person.class), id).stream().findAny().orElse(null);


//        Person person = null;
//        try {
//            PreparedStatement preparedStatement =
//                    connection.prepareStatement("SELECT * FROM Person WHERE id=?");
//            preparedStatement.setInt(1, id);
//            ResultSet resultSet = preparedStatement.executeQuery();
//            resultSet.next();
//            person = new Person();
//            person.setId(resultSet.getInt("id"));
//            person.setName(resultSet.getString("name"));
//            person.setAge(resultSet.getInt("age"));
//            person.setEmail(resultSet.getString("email"));
//
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//        return person;
    }

    public void addPerson(Person person) {

       jdbcTemplate.update("INSERT INTO Person(name, age, email) VALUES (?,?,?)", person.getName(),
               person.getAge(), person.getEmail());

//        try {
//            PreparedStatement preparedStatement =
//                    connection.prepareStatement("INSERT INTO Person VALUES (1,?,?,?)");
//            preparedStatement.setString(1, person.getName());
//            preparedStatement.setInt(2, person.getAge());
//            preparedStatement.setString(3, person.getEmail());
//            preparedStatement.executeUpdate();
//
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }

    }

    public void update(int id, Person person) {
       jdbcTemplate.update("UPDATE Person SET name=?, age=?, email=? WHERE id=?",
               person.getName(), person.getAge(), person.getEmail(), id);
//        try {
//            PreparedStatement preparedStatement =
//                    connection.prepareStatement("UPDATE Person SET name=?, age=?, email=? WHERE id=?");
//            preparedStatement.setString(1, person.getName());
//            preparedStatement.setInt(2, person.getAge());
//            preparedStatement.setString(3, person.getEmail());
//            preparedStatement.setInt(4, id);
//            preparedStatement.executeUpdate();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
    }

    public void delete(int id) {
     jdbcTemplate.update("DELETE FROM Person WHERE id=?", id);

//        try {
//            PreparedStatement preparedStatement =
//                    connection.prepareStatement("DELETE FROM Person WHERE id=?");
//            preparedStatement.setInt(1,id);
//            preparedStatement.executeUpdate();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
    }

    public List<Person> create1000persons(){
       List<Person> people = new ArrayList<>();
       for(int i = 0; i < 1000; i++) {
           people.add(new Person(i, "Name" + i, 30, "mail"+i + "@test.com"));
       }
       return people;
    }

    public void multiUpdate(){
       List<Person> list = create1000persons();

       long before = System.currentTimeMillis();
        for (int i = 0; i < list.size(); i++){
            jdbcTemplate.update("INSERT INTO Person VALUES (?,?,?,?)",list.get(i).getId(), list.get(i).getName(),
                    list.get(i).getAge(), list.get(i).getEmail());
        }
        long after = System.currentTimeMillis();
        System.out.println("Time: " + (after-before));
    }

    public void batchUpdate(){
        List<Person> list = create1000persons();

        long before = System.currentTimeMillis();
        jdbcTemplate.batchUpdate("INSERT INTO Person VALUES (?,?,?,?)", new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setInt(1, list.get(i).getId());
                ps.setString(2, list.get(i).getName());
                ps.setInt(3, list.get(i).getAge());
                ps.setString(4, list.get(i).getEmail());
            }

            @Override
            public int getBatchSize() {
                return list.size();
            }

        });
        long after = System.currentTimeMillis();
        System.out.println("Time: " + (after-before));
    }
}
