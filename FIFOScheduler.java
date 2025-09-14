import java.util.*;

class BlockedProcess {
    Process process;
    int endTime;

    public BlockedProcess(Process process, int endTime) {
        this.process = process;
        this.endTime = endTime;
    }
}

public class FIFOScheduler implements Scheduler {

    @Override
    public List<Result> schedule(List<Process> processes) {
        Queue<Process> readyQueue = new LinkedList<>(processes);
        List<BlockedProcess> blockedQueue = new ArrayList<>();
        List<Result> results = new ArrayList<>();
        List<TimelineEvent> timeline = new ArrayList<>();
        int currentTime = 0;

        System.out.println("=== FIFO Scheduling with Priority & I/O ===");

        while (!readyQueue.isEmpty() || !blockedQueue.isEmpty()) {

            // 1. Unblock finished I/O processes
            Iterator<BlockedProcess> iter = blockedQueue.iterator();
            while (iter.hasNext()) {
                BlockedProcess b = iter.next();
                if (currentTime >= b.endTime) {
                    readyQueue.add(b.process);
                    timeline.add(new TimelineEvent(b.process.getPid(), b.endTime - b.process.getIoDuration(), b.endTime, true));
                    System.out.println("P" + b.process.getPid() + " finished I/O at t=" + b.endTime);
                    iter.remove();
                }
            }

            // 2. Apply aging for all waiting processes
            for (Process p : readyQueue) p.incrementPriority();

            // 3. Idle CPU handling
            if (readyQueue.isEmpty()) {
                int nextArrival = Integer.MAX_VALUE;
                for (Process p : processes) {
                    if (p.getArrivalTime() > currentTime) {
                        nextArrival = Math.min(nextArrival, p.getArrivalTime());
                    }
                }
                int nextIoComplete = Integer.MAX_VALUE;
                for (BlockedProcess b : blockedQueue) {
                    nextIoComplete = Math.min(nextIoComplete, b.endTime);
                }

                int nextEvent = Math.min(nextArrival, nextIoComplete);
                currentTime = (nextEvent == Integer.MAX_VALUE) ? currentTime + 1 : nextEvent;
                continue;
            }

            // 4. Pick first process ready to run (not blocked for I/O)
            Process pToRun = null;
            for (Process p : readyQueue) {
                if (p.getIoTime() < 0 || currentTime != p.getIoTime()) {
                    pToRun = p;
                    break;
                }
            }

            if (pToRun == null) {
                currentTime++; // all processes temporarily blocked
                continue;
            }

            // Remove from readyQueue
            readyQueue.remove(pToRun);

            // 5. Check if process needs I/O now
            if (pToRun.getIoTime() >= 0 && currentTime == pToRun.getIoTime()) {
                int ioStart = currentTime;
                int ioEnd = ioStart + pToRun.getIoDuration();
                blockedQueue.add(new BlockedProcess(pToRun, ioEnd));
                System.out.println("P" + pToRun.getPid() + " starts I/O from t=" + ioStart + " to t=" + ioEnd);
                continue; // skip CPU execution this cycle
            }

            // 6. Execute process
            int startTime = currentTime;
            int completionTime = startTime + pToRun.getBurstTime();
            currentTime = completionTime;

            int turnaroundTime = completionTime - pToRun.getArrivalTime();
            int waitingTime = turnaroundTime - pToRun.getBurstTime();
            int responseTime = startTime - pToRun.getArrivalTime();

            results.add(new Result(pToRun.getPid(), waitingTime, turnaroundTime, responseTime, completionTime));

            // Log CPU execution in timeline
            timeline.add(new TimelineEvent(pToRun.getPid(), startTime, completionTime, false));

            System.out.println("P" + pToRun.getPid() + " executes from " + startTime + " to " + completionTime);
        }

        // 7. Show Gantt chart
        GanttChart.showChart(timeline, "FIFO Scheduling Gantt Chart");

        return results;
    }
}
