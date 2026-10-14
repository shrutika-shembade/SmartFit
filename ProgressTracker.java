// ======================================================
// ProgressTracker.java
// Weight history file mein save karna
// Progress trend dikhana - lost/gained calculate karna
// File I/O Concept: Read + Write text file
// DSA: ArrayList - history store karna
// ======================================================

import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class ProgressTracker {

    private static final String FILE_NAME
        = "SmartFit_WeightHistory.txt";

    // ── MAIN METHOD ────────────────────────────────────
    public static void trackProgress(User user) {

        System.out.println("\n");
        System.out.println("==========================================");
        System.out.println("       SMARTFIT PROGRESS TRACKER");
        System.out.println("==========================================");

        // Step 1: Read existing history
        ArrayList<String[]> history = readHistory();

        // Step 2: Show history if exists
        if (!history.isEmpty()) {
            printHistory(history);
            printTrend(history, user);
        } else {
            System.out.println("\n  No previous records found.");
            System.out.println("  This will be your first entry!");
        }

        // Step 3: Save today's entry
        saveTodayEntry(user, history);

        // Step 4: BMI history
        printBMIHistory(history, user);
    }

    // ── Read history from file ─────────────────────────
    // Each line format: date,weight,bmi
    private static ArrayList<String[]> readHistory() {

        ArrayList<String[]> history = new ArrayList<>();

        File file = new File(FILE_NAME);
        if (!file.exists()) return history;

        try {
            BufferedReader br = new BufferedReader(
                new FileReader(file));

            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    history.add(parts);
                }
            }
            br.close();

        } catch (IOException e) {
            System.out.println("  Could not read history file.");
        }

        return history;
    }

    // ── Print history table ────────────────────────────
    private static void printHistory(
            ArrayList<String[]> history) {

        System.out.println("\n  === YOUR WEIGHT HISTORY ===");
        System.out.printf("  %-12s %-10s %-10s%n",
            "DATE", "WEIGHT", "BMI");
        System.out.println("  ------------------------------------------");

        // Show last 10 entries only
        int start = Math.max(0, history.size() - 10);

        for (int i = start; i < history.size(); i++) {
            String[] entry = history.get(i);
            System.out.printf("  %-12s %-10s %-10s%n",
                entry[0], entry[1] + " kg", entry[2]);
        }
    }

    // ── Calculate and print trend ──────────────────────
    private static void printTrend(
            ArrayList<String[]> history, User user) {

        if (history.isEmpty()) return;

        // Get first and last recorded weight
        double firstWeight = Double.parseDouble(
            history.get(0)[1]);
        double lastWeight = Double.parseDouble(
            history.get(history.size() - 1)[1]);
        double currentWeight = user.getWeight();

        double totalChange = currentWeight - firstWeight;
        double recentChange = currentWeight - lastWeight;

        System.out.println("\n  === PROGRESS ANALYSIS ===");
        System.out.printf("  Starting Weight : %.1f kg%n", firstWeight);
        System.out.printf("  Last Recorded   : %.1f kg%n", lastWeight);
        System.out.printf("  Current Weight  : %.1f kg%n", currentWeight);
        System.out.println("  ------------------------------------------");

        // Goal based feedback
        String goal = user.getGoalValue();

        if (goal.equalsIgnoreCase("Weight Loss")) {
            if (totalChange < 0) {
                System.out.printf(
                    "  GREAT! You lost %.1f kg overall!%n",
                    Math.abs(totalChange));
            } else if (totalChange > 0) {
                System.out.printf(
                    "  Weight increased by %.1f kg. "
                    + "Review your diet plan.%n",
                    totalChange);
            } else {
                System.out.println("  Weight stable. Keep pushing!");
            }
        } else if (goal.equalsIgnoreCase("Weight Gain")) {
            if (totalChange > 0) {
                System.out.printf(
                    "  GREAT! You gained %.1f kg overall!%n",
                    totalChange);
            } else if (totalChange < 0) {
                System.out.printf(
                    "  Weight decreased by %.1f kg. "
                    + "Increase calorie intake.%n",
                    Math.abs(totalChange));
            } else {
                System.out.println("  Weight stable. Eat more protein!");
            }
        } else {
            if (Math.abs(totalChange) <= 1) {
                System.out.println("  PERFECT! Weight well maintained!");
            } else {
                System.out.printf(
                    "  Weight changed by %.1f kg. "
                    + "Adjust calories to maintain.%n",
                    totalChange);
            }
        }
    }

    // ── Save today's entry ─────────────────────────────
    private static void saveTodayEntry(
            User user, ArrayList<String[]> history) {

        LocalDate today = LocalDate.now();
        String dateStr = today.toString();

        double weight = user.getWeight();
        double bmi = user.calculateBMI();

        // Check if today already recorded
        for (String[] entry : history) {
            if (entry[0].equals(dateStr)) {
                System.out.println(
                    "\n  Today's entry already recorded.");
                return;
            }
        }

        try {
            FileWriter fw = new FileWriter(
                FILE_NAME, true); // true = append mode
            BufferedWriter bw = new BufferedWriter(fw);

            bw.write(dateStr + "," + weight + ","
                + String.format("%.2f", bmi) + "\n");

            bw.close();
            fw.close();

            System.out.println(
                "\n  Today's weight (" + weight
                + " kg) saved to history!");

        } catch (IOException e) {
            System.out.println(
                "  Could not save entry: " + e.getMessage());
        }
    }

    // ── BMI history trend ───────────────────────────────
    private static void printBMIHistory(
            ArrayList<String[]> history, User user) {

        System.out.println("\n  === BMI TREND ===");

        if (history.isEmpty()) {
            System.out.printf(
                "  Current BMI: %.2f (%s)%n",
                user.calculateBMI(), user.getCategory());
            return;
        }

        double firstBMI = Double.parseDouble(
            history.get(0)[2]);
        double currentBMI = user.calculateBMI();

        System.out.printf("  First BMI   : %.2f%n", firstBMI);
        System.out.printf("  Current BMI : %.2f%n", currentBMI);

        double bmiChange = currentBMI - firstBMI;

        if (Math.abs(bmiChange) < 0.1) {
            System.out.println("  Status      : BMI stable");
        } else if (bmiChange < 0) {
            System.out.printf(
                "  Status      : BMI decreased by %.2f%n",
                Math.abs(bmiChange));
        } else {
            System.out.printf(
                "  Status      : BMI increased by %.2f%n",
                bmiChange);
        }

        // Simple visual bar
        System.out.println("\n  Visual Progress:");
        printProgressBar(history, user);
    }

    // ── Simple ASCII progress bar ──────────────────────
    private static void printProgressBar(
            ArrayList<String[]> history, User user) {

        int count = Math.min(history.size() + 1, 10);
        System.out.print("  [");

        for (int i = 0; i < count; i++) {
            System.out.print("#");
        }
        for (int i = count; i < 10; i++) {
            System.out.print("-");
        }

        System.out.println("] " + count + " entries logged");
    }
}