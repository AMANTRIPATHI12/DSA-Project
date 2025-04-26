import java.io.*;
import java.util.*;

class Task {
    String name;
    int deadline;
    int priority;

    Task(String name, int deadline, int priority) {
        this.name = name;
        this.deadline = deadline;
        this.priority = priority;
    }

    @Override
    public String toString() {
        return name + " (Deadline: " + deadline + " days, Priority: " + priority + ")";
    }
}

public class SmartSchedulerWithNotification {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);

        PriorityQueue<Task> taskQueue = new PriorityQueue<>((a, b) -> {
            if (a.deadline != b.deadline)
                return Integer.compare(a.deadline, b.deadline);
            return Integer.compare(b.priority, a.priority);
        });

        System.out.println("How many tasks do you want to add?");
        int n = sc.nextInt();
        sc.nextLine();

        for (int i = 1; i <= n; i++) {
            System.out.println("\nTask #" + i);
            System.out.print("Name: ");
            String name = sc.nextLine();

            System.out.print("Deadline (in days): ");
            int deadline = sc.nextInt();

            System.out.print("Priority (1-10): ");
            int priority = sc.nextInt();
            sc.nextLine();

            taskQueue.offer(new Task(name, deadline, priority));
        }

        // Save to file
        FileWriter writer = new FileWriter("TopTasks.txt");
        writer.write("🧠 Top Priority Tasks:\n");

        List<String> topThree = new ArrayList<>();
        int count = 1;

        while (!taskQueue.isEmpty()) {
            Task t = taskQueue.poll();
            String line = count + ". " + t.toString();
            writer.write(line + "\n");
            if (count <= 3) topThree.add(line);
            count++;
        }

        writer.close();

        // Open file in Notepad
        new ProcessBuilder("notepad", "TopTasks.txt").start();

        // Prepare toast notification using PowerShell
        String toastText = String.join("\n", topThree).replace("\"", "`\""); // escape quotes
        String psCommand =
            "$toastText = '🧠 Top Tasks:`n" + toastText + "';" +
            "[Windows.UI.Notifications.ToastNotificationManager, Windows.UI.Notifications, ContentType = WindowsRuntime] > $null;" +
            "$template = [Windows.UI.Notifications.ToastNotificationManager]::GetTemplateContent([Windows.UI.Notifications.ToastTemplateType]::ToastText02);" +
            "$textNodes = $template.GetElementsByTagName('text');" +
            "$textNodes.Item(0).AppendChild($template.CreateTextNode('Smart Scheduler')) > $null;" +
            "$textNodes.Item(1).AppendChild($template.CreateTextNode($toastText)) > $null;" +
            "$toast = [Windows.UI.Notifications.ToastNotification]::new($template);" +
            "[Windows.UI.Notifications.ToastNotificationManager]::CreateToastNotifier('SmartScheduler').Show($toast);";

        List<String> command = Arrays.asList("powershell.exe", "-command", psCommand);
        new ProcessBuilder(command).start();

        sc.close();
    }
}
