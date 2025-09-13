import java.util.*;

public class FIFOScheduler implements Scheduler {
    @Override
    public List<Result> schedule(List<Process> processes) {
        Queue<Process> queue = new LinkedList<>(processes);
        int currentTime = 0;
        List<Result> results = new ArrayList<>();

        System.out.println("=== FIFO Scheduling ===");

        while (!queue.isEmpty()) {
            Process p = queue.poll();

            if (p.getArrivalTime() > currentTime) {
                currentTime = p.getArrivalTime();
            }

            int startTime = currentTime;
            int completionTime = startTime + p.getBurstTime();
            currentTime = completionTime;

            int turnaroundTime = completionTime - p.getArrivalTime();
            int waitingTime = turnaroundTime - p.getBurstTime();
            int responseTime = startTime - p.getArrivalTime();

            results.add(new Result(p.getPid(), waitingTime, turnaroundTime, responseTime, completionTime));

            System.out.println("P" + p.getPid() + " executes from " + startTime + " to " + completionTime);
        }
        return results;
    }
}
