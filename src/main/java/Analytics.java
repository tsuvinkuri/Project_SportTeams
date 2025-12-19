import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import javax.swing.*;
import java.sql.*;
import java.util.*;

public class Analytics {
    private static Connection conn;

    public static void main() {
        connection();
        Map<String, Double> avgAgeByTeam = getAverageAgeByTeam();
        createShedule(avgAgeByTeam);
        String tallestTeam = getTeamWithMaxAvgHeight();
        System.out.println("Команда с самым высоким средним ростом: " + tallestTeam);
        printTopFiveTallestPlayers(tallestTeam);
        String team = getTeamByHeightWeightAge();
        System.out.println("\nКоманда с самым высоким средним возрастом (рост 74-78 дюймов, вес 190-210 фунтов): " + team);
    }

    private static void connection() {
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:SportTeams.db3");
            System.out.println("База подключена!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static Map<String, Double> getAverageAgeByTeam() {
        Map<String, Double> result = new HashMap<>();
        String sql = "SELECT nameOfTeam, AVG(age) AS avgAge FROM SportTeams GROUP BY nameOfTeam";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                result.put(rs.getString("nameOfTeam"), rs.getDouble("avgAge"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    private static void createShedule(Map<String, Double> avgAgeByTeam) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (Map.Entry<String, Double> entry : avgAgeByTeam.entrySet()) {
            dataset.addValue(entry.getValue(), "Средний возраст", entry.getKey());
        }
        JFreeChart chart = ChartFactory.createBarChart(
                "Средний возраст по командам",
                "Команда",
                "Возраст",
                dataset
        );
        JFrame frame = new JFrame("График");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new ChartPanel(chart));
        frame.pack();
        frame.setVisible(true);
    }

    private static String getTeamWithMaxAvgHeight() {
        String sql = "SELECT nameOfTeam FROM SportTeams GROUP BY nameOfTeam ORDER BY AVG(height) DESC LIMIT 1";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getString("nameOfTeam");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    private static void printTopFiveTallestPlayers(String team) {
        String sql = "SELECT nameOfMember, height FROM SportTeams WHERE nameOfTeam = ? ORDER BY height DESC LIMIT 5";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, team);
            ResultSet rs = pstmt.executeQuery();
            System.out.println("5 самых высоких игроков команды " + team + ":");
            while (rs.next()) {
                System.out.println(rs.getString("nameOfMember") + " - " + rs.getInt("height") + " дюймов");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static String getTeamByHeightWeightAge() {
        String sql = "SELECT nameOfTeam FROM SportTeams " +
                "GROUP BY nameOfTeam " +
                "HAVING AVG(height) BETWEEN 74 AND 78 " +
                "AND AVG(weight) BETWEEN 190 AND 210 " +
                "ORDER BY AVG(age) DESC LIMIT 1";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getString("nameOfTeam");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}