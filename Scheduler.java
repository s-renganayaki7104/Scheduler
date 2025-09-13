import java.util.List;

public interface Scheduler {
    List<Result> schedule(List<Process> processes);
}
