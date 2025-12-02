import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseService {
    private static final String DB_URL =
            "jdbc:mysql://localhost:3306/people_db" +
            "?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "odira2005";

    // Person data class nested within DatabaseService
    public static class Person {
        private final int id;
        private final String name;
        private final int age;
        private final String phone;
        private final String residence;

        public Person(int id, String name, int age, String phone, String residence) {
            this.id = id;
            this.name = name;
            this.age = age;
            this.phone = phone;
            this.residence = residence;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }

        public String getPhone() {
            return phone;
        }

        public String getResidence() {
            return residence;
        }
    }

    public void createDatabaseAndTable() {
        // Connect to MySQL server without specifying database
        String serverUrl = "jdbc:mysql://localhost:3306" +
                "?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
        
        String createDatabaseSql = "CREATE DATABASE IF NOT EXISTS people_db";
        String useDatabaseSql = "USE people_db";
        String createTableSql = "CREATE TABLE IF NOT EXISTS people (" +
                "id INT PRIMARY KEY, " +
                "name VARCHAR(255) NOT NULL, " +
                "age INT NOT NULL, " +
                "phone VARCHAR(50), " +
                "residence VARCHAR(255))";

        try (Connection conn = DriverManager.getConnection(serverUrl, DB_USER, DB_PASSWORD);
             Statement stmt = conn.createStatement()) {

            stmt.execute(createDatabaseSql);
            stmt.execute(useDatabaseSql);
            stmt.execute(createTableSql);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error creating database: " + e.getMessage());
        }
    }

    public void savePerson(int id, String name, int age, String phone, String residence) {
        String sql = "INSERT INTO people (id, name, age, phone, residence) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.setString(2, name);
            pstmt.setInt(3, age);
            pstmt.setString(4, phone);
            pstmt.setString(5, residence);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error saving record: " + e.getMessage());
        }
    }

    public void deletePerson(int id) {
        String sql = "DELETE FROM people WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error deleting record: " + e.getMessage());
        }
    }

    public List<Person> loadAllPeople() {
        List<Person> people = new ArrayList<>();
        String sql = "SELECT * FROM people";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                people.add(new Person(
                        rs.getInt("id"),
                        rs.getString("name"),
                    rs.getInt("age"),
                    rs.getString("phone"),
                    rs.getString("residence")
                ));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error loading records: " + e.getMessage());
        }

        return people;
    }
}
