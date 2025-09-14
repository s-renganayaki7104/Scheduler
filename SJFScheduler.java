import java.util.List;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Comparator;

public class SJFScheduler implements Scheduler {

    @Override
    public List<Result> schedule(List<Process> processes) {
        processes.sort(Comparator.comparingInt(Process::getArrivalTime));
        int currentTime = 0, index = 0;
        PriorityQueue<Process> pq = new PriorityQueue<>(Comparator.comparingInt(Process::getBurstTime));
        List<Result> results = new ArrayList<>();

        System.out.println("=== SJF Scheduling ===");

        while (index < processes.size() || !pq.isEmpty()) {

            // Add newly arrived processes
            while (index < processes.size() && processes.get(index).getArrivalTime() <= currentTime) {
                pq.add(processes.get(index));
                index++;
            }

            // If no process ready, advance time
            if (pq.isEmpty()) {
                currentTime++;
                continue;
            }

            // Poll shortest job
            Process p = pq.poll();

            // Execute process
            int startTime = currentTime;
            int endTime = startTime + p.getBurstTime();
            currentTime = endTime;

            // Metrics
            int turnaroundTime = endTime - p.getArrivalTime();
            int waitingTime = turnaroundTime - p.getBurstTime();
            int responseTime = startTime - p.getArrivalTime();

            results.add(new Result(p.getPid(), waitingTime, turnaroundTime, responseTime, endTime));

            System.out.println("P" + p.getPid() + " executes from " + startTime + " to " + endTime);
        }

        return results;
    }
}
