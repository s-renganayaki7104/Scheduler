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

        processes.sort(Comparator.comparingInt(Process::getArrivalTime));

        System.out.println("=== Round Robin Scheduling (Quantum = " + quantum + ") ===");

        while (index < processes.size() || !queue.isEmpty()) {
            while (index < processes.size() && processes.get(index).getArrivalTime() <= currentTime) {
                queue.add(processes.get(index));
                index++;
            }

            if (queue.isEmpty()) {
                currentTime++;
                continue;
            }

            Process p = queue.poll();
            int executionTime = Math.min(quantum, p.getRemainingTime());
            int startTime = currentTime;
            int endTime = startTime + executionTime;

            if (!firstResponse.containsKey(p.getPid())) {
                firstResponse.put(p.getPid(), startTime);
            }

            currentTime = endTime;
            p.setRemainingTime(p.getRemainingTime() - executionTime);

            System.out.println("P" + p.getPid() + " executes from " + startTime + " to " + endTime);

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
