import java.io.InputStreamReader;
import java.util.List;
import model.SportTeams;
import com.opencsv.CSVReader;
import model.Position;
import java.io.IOException;
import java.util.ArrayList;


public class Utilities {
    public static List<SportTeams> getSportTeamsFromCsv(String path){
        List<SportTeams> result = new ArrayList<>();
        try(CSVReader reader = new CSVReader( new InputStreamReader(Main.class.getResourceAsStream("/SportTeamsIndicators.csv")) );){
            String[] record;
            reader.readNext();
            while((record = reader.readNext()) != null){
                String Name = record[0];
                String Team = record[1].trim()
                        .replace("\"", "")
                        .replace("'", "");
                String positionStr = record[2]
                        .trim()
                        .replace("\"", "")
                        .replace("'", "")
                        .replace(" ", "_");
                Position position = Position.valueOf(positionStr);
                int Height = Integer.parseInt(record[3]);
                int Weight = Integer.parseInt(record[4]);
                double Age = Double.parseDouble(record[5]);
                SportTeams sportTeam = new SportTeams(Name, Team, position, Height,
                        Weight, Age);
                result.add(sportTeam);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
}