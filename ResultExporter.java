import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ResultExporter {
    public static void exportToCSV(List<Result> results, String filename) {
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write("PID,WaitingTime,TurnaroundTime,ResponseTime,CompletionTime\n");
            for (Result r : results) {
                writer.write(r.pid + "," + r.waitingTime + "," + r.turnaroundTime + "," + r.responseTime + "," + r.completionTime + "\n");
            }
            System.out.println("Results exported to " + filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
