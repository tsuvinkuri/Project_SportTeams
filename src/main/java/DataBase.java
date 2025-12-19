import java.sql.*;
import java.util.List;

import model.SportTeams;

public class DataBase {
    private static Connection conn;
    private static Statement statmt;
    private static ResultSet resSet;
    public static void connection(){
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:SportTeams.db3");
            System.out.println("База Подключена!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void createDb(){
        try {
            statmt = conn.createStatement();
            statmt.execute("CREATE TABLE IF NOT EXISTS 'SportTeams' (" +
                    "'id' INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "'nameOfMember' text," +
                    "'nameOfTeam' text," +
                    "'position' text," +
                    "'height' INTEGER," +
                    "'weight' INTEGER," +
                    "'age' REAL)");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Таблица создана или уже существует");
    }

    public static void writeDb(List<SportTeams> sportTeamsList){
        try {
            PreparedStatement pstmt = conn.prepareStatement(
                    "INSERT INTO SportTeams (nameOfMember, nameOfTeam, position, height, weight, age) VALUES (?, ?, ?, ?, ?, ?)"
            );
            for (SportTeams sportTeam : sportTeamsList) {
                pstmt.setString(1, sportTeam.getNameOfMember());
                pstmt.setString(2, sportTeam.getNameOfTeam());
                pstmt.setString(3, sportTeam.getPosition().toString());
                pstmt.setInt(4, sportTeam.getHeight());
                pstmt.setInt(5, sportTeam.getWeight());
                pstmt.setDouble(6, sportTeam.getAge());
                pstmt.executeUpdate();
            }
            System.out.println("Таблица заполнена");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
