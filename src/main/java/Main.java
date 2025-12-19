import model.SportTeams;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        List<SportTeams> list = new ArrayList<>();
        list = Utilities.getSportTeamsFromCsv("SportTeamsIndicators.csv");
        //DataBase.connection(); подключение идет в Analytics
        //DataBase.createDb();
        //DataBase.writeDb(list);
        Analytics.main();
    }
}