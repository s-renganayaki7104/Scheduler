import java.util.*;

public class UnixScheduler implements Scheduler {
    private int quantum;

    public UnixScheduler(int quantum) {
        this.quantum = quantum;
    }

    @Override
    public List<Result> schedule(List<Process> processes) {
        List<Result> results = new ArrayList<>();
        Map<Integer, Integer> firstResponse = new HashMap<>();
        processes.sort(Comparator.comparingInt(Process::getArrivalTime));
        int currentTime = 0, index = 0;

        // Keep track of original processes by PID
        Map<Integer, Process> originalProcesses = new HashMap<>();
        for (Process p : processes) originalProcesses.put(p.getPid(), p);

        Map<Integer, Queue<Process>> priorityQueues = new TreeMap<>();

        System.out.println("=== UNIX Scheduling (Priority + RR) ===");

        while (index < processes.size() || !priorityQueues.isEmpty()) {
            while (index < processes.size() && processes.get(index).getArrivalTime() <= currentTime) {
                Process p = processes.get(index);
                priorityQueues.putIfAbsent(p.getPriority(), new LinkedList<>());
                priorityQueues.get(p.getPriority()).add(p);
                index++;
            }

            if (priorityQueues.isEmpty()) {
                currentTime++;
                continue;
            }

            int highestPriority = Collections.min(priorityQueues.keySet());
            Queue<Process> queue = priorityQueues.get(highestPriority);
            Process p = queue.poll();

            int executionTime = Math.min(quantum, p.getRemainingTime());
            int startTime = currentTime;
            int endTime = startTime + executionTime;

            if (!firstResponse.containsKey(p.getPid())) {
                firstResponse.put(p.getPid(), startTime);
            }

            currentTime = endTime;
            p.setRemainingTime(p.getRemainingTime() - executionTime);

            System.out.println("P" + p.getPid() + " (PR=" + p.getPriority() + ") executes from " + startTime + " to " + endTime);

            if (p.getRemainingTime() > 0) {
                // Instead of creating a new Process, just update priority
                p = new Process(p.getPid(), originalProcesses.get(p.getPid()).getArrivalTime(), p.getRemainingTime(), p.getPriority() + 1);
                priorityQueues.putIfAbsent(p.getPriority(), new LinkedList<>());
                priorityQueues.get(p.getPriority()).add(p);
            } else {
                int completionTime = currentTime;
                int turnaroundTime = completionTime - originalProcesses.get(p.getPid()).getArrivalTime();
                int waitingTime = turnaroundTime - originalProcesses.get(p.getPid()).getBurstTime();
                int responseTime = firstResponse.get(p.getPid()) - originalProcesses.get(p.getPid()).getArrivalTime();
                results.add(new Result(p.getPid(), waitingTime, turnaroundTime, responseTime, completionTime));
            }

            if (queue.isEmpty()) {
                priorityQueues.remove(highestPriority);
            }
        }

        return results;
    }
}
