import java.util.*;

public class RRScheduler implements Scheduler {
    private int quantum;

    public RRScheduler(int quantum) {
        this.quantum = quantum;
    }

    @Override
    public List<Result> schedule(List<Process> processes) {
        List<Result> results = new ArrayList<>();
        Queue<Process> queue = new LinkedList<>();
        Map<Integer, Integer> firstResponse = new HashMap<>();
        int currentTime = 0, index = 0;

        // Sort processes by arrival time
        processes.sort(Comparator.comparingInt(Process::getArrivalTime));

        System.out.println("=== Round Robin Scheduling (Quantum = " + quantum + ") ===");

        while (index < processes.size() || !queue.isEmpty()) {

            // Add newly arrived processes to the queue
            while (index < processes.size() && processes.get(index).getArrivalTime() <= currentTime) {
                queue.add(processes.get(index));
                index++;
            }

            // If no process is ready, jump to next arrival
            if (queue.isEmpty()) {
                if (index < processes.size()) {
                    currentTime = processes.get(index).getArrivalTime();
                } else {
                    currentTime++; // safety fallback
                }
                continue;
            }

            // Get next process from queue
            Process p = queue.poll();

            // Determine execution slice
            int execTime = Math.min(quantum, p.getRemainingTime());
            int startTime = currentTime;
            int endTime = startTime + execTime;

            // Record first response
            firstResponse.putIfAbsent(p.getPid(), startTime);

            // Execute process
            currentTime = endTime;
            p.setRemainingTime(p.getRemainingTime() - execTime);

            System.out.println("P" + p.getPid() + " executes from " + startTime + " to " + endTime);

            // Requeue if not finished, else calculate metrics
            if (p.getRemainingTime() > 0) {
                queue.add(p);
            } else {
                int completionTime = currentTime;
                int turnaroundTime = completionTime - p.getArrivalTime();
                int waitingTime = turnaroundTime - p.getBurstTime();
                int responseTime = firstResponse.get(p.getPid()) - p.getArrivalTime();
                results.add(new Result(p.getPid(), waitingTime, turnaroundTime, responseTime, completionTime));
            }
        }

        return results;
    }
}
