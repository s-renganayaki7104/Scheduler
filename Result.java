public class Result {
    int pid;
    int waitingTime;
    int turnaroundTime;
    int responseTime;
    int completionTime;

    public Result(int pid, int waitingTime, int turnaroundTime, int responseTime, int completionTime) {
        this.pid = pid;
        this.waitingTime = waitingTime;
        this.turnaroundTime = turnaroundTime;
        this.responseTime = responseTime;
        this.completionTime = completionTime;
    }

    @Override
    public String toString() {
        return String.format("P%-3d | WT=%-3d | TAT=%-3d | RT=%-3d | CT=%-3d",
                pid, waitingTime, turnaroundTime, responseTime, completionTime);
    }
}
