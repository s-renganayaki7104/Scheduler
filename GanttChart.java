import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;

public class GanttChart extends JPanel {
    private List<TimelineEvent> timeline;

    public GanttChart(List<TimelineEvent> timeline) {
        this.timeline = timeline;
        setPreferredSize(new Dimension(800, timeline.size() * 50 + 50));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int y = 30;
        for (TimelineEvent t : timeline) {
            // CPU execution vs I/O coloring
            if (t.isIo) {
                g.setColor(Color.LIGHT_GRAY); // I/O block
            } else {
                g.setColor(getColor(t.pid)); // CPU execution
            }

            int x = t.start * 50; // scale time
            int width = (t.end - t.start) * 50;
            g.fillRect(x, y, width, 30);

            g.setColor(Color.BLACK);
            g.drawRect(x, y, width, 30);
            g.drawString("P" + t.pid, x + width / 2 - 10, y + 20);

            y += 40;
        }

        // Time axis
        g.setColor(Color.BLACK);
        for (int i = 0; i <= getMaxTime(); i++) {
            g.drawString(String.valueOf(i), i * 50, 20);
        }
    }

    private int getMaxTime() {
        int max = 0;
        for (TimelineEvent t : timeline) {
            if (t.end > max) max = t.end;
        }
        return max;
    }

    private Color getColor(int pid) {
        switch (pid % 6) {
            case 0: return Color.RED;
            case 1: return Color.BLUE;
            case 2: return Color.GREEN;
            case 3: return Color.ORANGE;
            case 4: return Color.MAGENTA;
            default: return Color.CYAN;
        }
    }

    public static void showChart(List<TimelineEvent> timeline, String title) {
        JFrame frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.add(new GanttChart(timeline));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // Convenience method to convert Results to TimelineEvents (CPU execution)
    public static void printGanttChart(List<Result> results) {
        List<TimelineEvent> timeline = new ArrayList<>();
        for (Result r : results) {
            timeline.add(new TimelineEvent(r.pid, r.responseTime, r.completionTime, false));
        }
        showChart(timeline, "CPU Scheduling Gantt Chart");
    }
}
