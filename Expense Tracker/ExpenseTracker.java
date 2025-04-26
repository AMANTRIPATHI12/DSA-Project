import java.io.*;
import java.util.*;

public class ExpenseTracker {
    static final String FILE_NAME = "expenses.txt";
    static Map<String, List<Double>> expenseMap = new HashMap<>();

    public static void main(String[] args) {
        loadExpensesFromFile();

        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\n🧾 Expense Tracker Menu:");
            System.out.println("1. Add Expense");
            System.out.println("2. View All Expenses");
            System.out.println("3. View Category Summary");
            System.out.println("4. View Total Expenses");
            System.out.println("5. Exit");

            System.out.print("Enter choice: ");
            int choice = sc.nextInt();
            sc.nextLine(); // clear buffer

            switch (choice) {
                case 1 -> addExpense(sc);
                case 2 -> showAllExpenses();
                case 3 -> showCategorySummary();
                case 4 -> showTotal();
                case 5 -> {
                    saveExpensesToFile();
                    System.out.println("✅ Data saved. Exiting...");
                    return;
                }
                default -> System.out.println("❌ Invalid choice. Try again.");
            }
        }
    }

    static void addExpense(Scanner sc) {
        System.out.print("Enter category (e.g., Food, Travel, Rent): ");
        String category = sc.nextLine().trim();

        System.out.print("Enter amount: ₹");
        double amount = sc.nextDouble();
        sc.nextLine(); // clear buffer

        expenseMap.putIfAbsent(category, new ArrayList<>());
        expenseMap.get(category).add(amount);
        System.out.println("✅ Expense added.");
    }

    static void showAllExpenses() {
        if (expenseMap.isEmpty()) {
            System.out.println("📭 No expenses recorded.");
            return;
        }

        System.out.println("\n📊 All Expenses:");
        for (var entry : expenseMap.entrySet()) {
            System.out.println("- " + entry.getKey() + ": " + entry.getValue());
        }
    }

    static void showCategorySummary() {
        if (expenseMap.isEmpty()) {
            System.out.println("📭 No expenses recorded.");
            return;
        }

        System.out.println("\n📁 Category Summary:");
        for (var entry : expenseMap.entrySet()) {
            double sum = entry.getValue().stream().mapToDouble(Double::doubleValue).sum();
            System.out.printf("- %s: ₹%.2f%n", entry.getKey(), sum);
        }
    }

    static void showTotal() {
        double total = 0;
        for (List<Double> list : expenseMap.values()) {
            total += list.stream().mapToDouble(Double::doubleValue).sum();
        }
        System.out.printf("💰 Total Expenses: ₹%.2f%n", total);
    }

    static void saveExpensesToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (var entry : expenseMap.entrySet()) {
                writer.print(entry.getKey() + ":");
                for (double value : entry.getValue()) {
                    writer.print(value + ",");
                }
                writer.println();
            }
        } catch (IOException e) {
            System.out.println("❌ Error saving expenses: " + e.getMessage());
        }
    }

    static void loadExpensesFromFile() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return;

        try (Scanner fileScanner = new Scanner(file)) {
            while (fileScanner.hasNextLine()) {
                String[] parts = fileScanner.nextLine().split(":");
                if (parts.length != 2) continue;

                String category = parts[0];
                String[] values = parts[1].split(",");
                List<Double> amounts = new ArrayList<>();
                for (String val : values) {
                    if (!val.isBlank()) amounts.add(Double.parseDouble(val.trim()));
                }
                expenseMap.put(category, amounts);
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("⚠️ Error loading saved expenses.");
        }
    }
}
