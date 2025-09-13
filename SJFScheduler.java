import java.util.*;

public class SJFScheduler implements Scheduler {
    @Override
    public List<Result> schedule(List<Process> processes) {
        processes.sort(Comparator.comparingInt(Process::getArrivalTime));
        int currentTime = 0, index = 0;
        PriorityQueue<Process> pq = new PriorityQueue<>(Comparator.comparingInt(Process::getBurstTime));
        List<Result> results = new ArrayList<>();

        System.out.println("=== SJF Scheduling ===");

        while (index < processes.size() || !pq.isEmpty()) {
            while (index < processes.size() && processes.get(index).getArrivalTime() <= currentTime) {
                pq.add(processes.get(index));
                index++;
            }

            if (pq.isEmpty()) {
                currentTime++;
                continue;
            }

            Process p = pq.poll();
            int startTime = currentTime;
            int endTime = startTime + p.getBurstTime();
            currentTime = endTime;

            int turnaroundTime = endTime - p.getArrivalTime();
            int waitingTime = turnaroundTime - p.getBurstTime();
            int responseTime = startTime - p.getArrivalTime();

            results.add(new Result(p.getPid(), waitingTime, turnaroundTime, responseTime, endTime));
            System.out.println("P" + p.getPid() + " executes from " + startTime + " to " + endTime);
        }
        return results;
    }
}
