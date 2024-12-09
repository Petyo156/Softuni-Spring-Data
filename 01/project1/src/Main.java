import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws SQLException {
        DBConnection dbConnection = new DBConnection();

        //printVillainNameAndMinionsCount();
        //printMinionNamesAgeVillainID(3);
        //addMinion();
        //changeTownNamesUppercase("Bulgaria");
        printAllMinionNames();

        DBConnection.getCollection().close();
    }

    private static void printAllMinionNames() throws SQLException {
        Statement statement = DBConnection.getCollection().createStatement();
        ResultSet resultSet = statement.executeQuery(
                "SELECT name FROM minions;"
        );

        Deque<String> names = new ArrayDeque<>();
        while (resultSet.next()) {
            String minionName = resultSet.getString("name");
            names.add(minionName);
        }

        while (!names.isEmpty()) {
            System.out.println(names.removeFirst());
            try {
                System.out.println(names.removeLast());
            } catch (NoSuchElementException ignored) {

            }
        }

    }

    private static void changeTownNamesUppercase(String country) throws SQLException {
        PreparedStatement countryExists = DBConnection.getCollection().prepareStatement(
                "SELECT * FROM towns " +
                        "WHERE country = ?");
        countryExists.setString(1, country);
        ResultSet resultSet = countryExists.executeQuery();

        if (!resultSet.next()) {
            System.out.println("No country with given name.");
            return;
        }

        PreparedStatement updateTowns = DBConnection.getCollection().prepareStatement(
                "UPDATE towns SET name = UPPER(name) WHERE country = ?");
        updateTowns.setString(1, country);

        int rowsUpdated = updateTowns.executeUpdate();

        if (rowsUpdated > 0) {
            System.out.println(rowsUpdated + " town(s) were updated to uppercase.");
            ResultSet result = countryExists.executeQuery();
            List<String> names = new ArrayList<>();
            while (result.next()) {
                String name = result.getString("name");
                names.add(name);
            }
            System.out.println(names);
        } else {
            System.out.println("No towns were updated.");
        }
    }

    private static void addMinion() {
        //uhhhh
    }

    private static void printVillainNameAndMinionsCount() throws SQLException {
        Statement statement = DBConnection.getCollection().createStatement();
        ResultSet villainNameAndMinionsCount = statement.executeQuery(
                "SELECT v.name, COUNT(*) AS count FROM villains v " +
                        "JOIN minions_villains mv on v.id = mv.villain_id " +
                        "GROUP BY v.name " +
                        "HAVING count > 15 " +
                        "ORDER BY count DESC");
        while (villainNameAndMinionsCount.next()) {
            String name = villainNameAndMinionsCount.getString("name");
            String count = villainNameAndMinionsCount.getString("count");
            System.out.printf("%s %s%n", name, count);
        }
    }

    private static void printMinionNamesAgeVillainID(int id) throws SQLException {
        PreparedStatement statementVillain = DBConnection.getCollection().prepareStatement(
                "SELECT v.name FROM villains v " +
                        "WHERE v.id = ?");
        statementVillain.setInt(1, id);
        ResultSet resultSetVillain = statementVillain.executeQuery();

        if (!resultSetVillain.next()) {
            System.out.println("No villain with given id.");
            return;
        }
        String villainName = resultSetVillain.getString("v.name");


        PreparedStatement statement = DBConnection.getCollection().prepareStatement(
                "SELECT m.name, m.age FROM minions m " +
                        "JOIN minions_villains mv on m.id = mv.minion_id " +
                        "JOIN villains v on mv.villain_id = v.id " +
                        "WHERE v.id = ?");
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();

        Map<String, String> minions = new LinkedHashMap<>();
        while (resultSet.next()) {
            String name = resultSet.getString("m.name");
            String age = resultSet.getString("m.age");

            minions.put(name, age);
        }

        System.out.println("Villain: " + villainName);
        int count = 1;
        for (Map.Entry<String, String> entry : minions.entrySet()) {
            System.out.println(count++ + ". " + entry.getKey() + " " + entry.getValue());
        }
    }


}