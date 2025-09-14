public class TimelineEvent {
    public int pid;
    public int start;
    public int end;
    public boolean isIo; // true if I/O

    public TimelineEvent(int pid, int start, int end, boolean isIo) {
        this.pid = pid;
        this.start = start;
        this.end = end;
        this.isIo = isIo;
    }
}
