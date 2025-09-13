import java.io.*;
import java.util.*;

public class ProcessLoader {
    public static List<Process> loadFromCSV(String filename) throws IOException {
        List<Process> processes = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String line;
        while ((line = br.readLine()) != null) {
            if (line.startsWith("PID")) continue; // skip header
            String[] parts = line.split(",");
            int pid = Integer.parseInt(parts[0].trim());
            int arrival = Integer.parseInt(parts[1].trim());
            int burst = Integer.parseInt(parts[2].trim());
            int priority = Integer.parseInt(parts[3].trim());
            processes.add(new Process(pid, arrival, burst, priority));
        }
        br.close();
        return processes;
    }
}
