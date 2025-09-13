import java.util.*;
import java.io.*;

public class CPUSchedulerSimulator {
    public static void main(String[] args) throws IOException {
        // Load processes from CSV
        List<Process> processes = ProcessLoader.loadFromCSV("processes.csv");

        System.out.println("Choose Scheduling Algorithm:");
        System.out.println("1. FIFO");
        System.out.println("2. SJF");
        System.out.println("3. Round Robin");
        System.out.println("4. UNIX Scheduling");

        Scanner sc = new Scanner(System.in);
        int choice = sc.nextInt();

        Scheduler scheduler = null;
        switch (choice) {
            case 1 -> scheduler = new FIFOScheduler();
            case 2 -> scheduler = new SJFScheduler();
            case 3 -> {
                System.out.print("Enter Quantum: ");
                int quantum = sc.nextInt();
                scheduler = new RRScheduler(quantum);
            }
            case 4 -> {
                System.out.print("Enter Quantum: ");
                int quantum = sc.nextInt();
                scheduler = new UnixScheduler(quantum);
            }
            default -> {
                System.out.println("Invalid choice");
                return;
            }
        }

        List<Result> results = scheduler.schedule(processes);

        // Print table
        System.out.println("\n--- Results ---");
        int totalWT = 0, totalTAT = 0, totalRT = 0;
        for (Result r : results) {
            System.out.println(r);
            totalWT += r.waitingTime;
            totalTAT += r.turnaroundTime;
            totalRT += r.responseTime;
        }

        int n = results.size();
        System.out.println("\nAverage Waiting Time: " + (totalWT / (double)n));
        System.out.println("Average Turnaround Time: " + (totalTAT / (double)n));
        System.out.println("Average Response Time: " + (totalRT / (double)n));

        // Export results to CSV
        ResultExporter.exportToCSV(results, "scheduler_results.csv");
GanttChart.printGanttChart(results);
    }
}
