public class Process {
    private int pid;
    private int arrivalTime;
    private int burstTime;
    private int remainingTime;
    private int priority;
private int ioTime;       
private int ioDuration;   




    public Process(int pid, int arrivalTime, int burstTime, int priority) {
        this.pid = pid;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.remainingTime = burstTime;
        this.priority = priority;
    }

    public int getPid() { return pid; }
    public int getArrivalTime() { return arrivalTime; }
    public int getBurstTime() { return burstTime; }
    public int getRemainingTime() { return remainingTime; }
    public int getPriority() { return priority; }
public void incrementPriority() { priority--; }
public int getIoTime() { return ioTime; }
public int getIoDuration() { return ioDuration; }

    public void setRemainingTime(int remainingTime) { this.remainingTime = remainingTime; }

    @Override
    public String toString() {
        return "P" + pid + " (AT=" + arrivalTime + ", BT=" + burstTime + ", PR=" + priority + ")";
    }
}
