// ======================================================
// SleepTracker.java - FULLY FIXED VERSION
// Bug fix: Properly handles HH:MM format and validates
// 24-hour input correctly so duration calculates right
// ======================================================

import java.util.Scanner;

public class SleepTracker {

    public static void trackSleep(User user) {

        System.out.println("\n");
        System.out.println("==========================================");
        System.out.println("       SMARTFIT SLEEP TRACKER");
        System.out.println("==========================================");

        Scanner sc = new Scanner(System.in);

        System.out.println("\nEnter time in 24-HOUR format only.");
        System.out.println("You can type just the hour, or hour:minutes");
        System.out.println("Examples:");
        System.out.println("  11:40 PM  ->  type: 23:40   or just  23");
        System.out.println("  7:00 AM   ->  type: 7:00     or just  7");

        System.out.print("\nEnter sleep time (24-hr, e.g 23:40): ");
        double sleepTime = readTimeSafely(sc);

        System.out.print("Enter wake time (24-hr, e.g 7:00): ");
        double wakeTime = readTimeSafely(sc);

        // ── Calculate sleep duration (supports decimals) ─
        double sleepDuration;
        if (wakeTime > sleepTime) {
            sleepDuration = wakeTime - sleepTime;
        } else {
            // Crosses midnight
            sleepDuration = (24 - sleepTime) + wakeTime;
        }

        System.out.println("\n  ------------------------------------------");
        System.out.println("  Sleep Time : " + formatTime(sleepTime));
        System.out.println("  Wake Time  : " + formatTime(wakeTime));
        System.out.printf ("  Duration   : %.1f hours%n", sleepDuration);
        System.out.println("  ------------------------------------------");

        printQualityAnalysis(sleepDuration, user.getAge());
        printSleepSuggestions(sleepDuration, sleepTime);
        printGoalConnection(user.getGoalValue(), sleepDuration);
    }

    // ── Safely read time as decimal hour ────────────────
    // Accepts: "23", "23:40", "23.40" - all handled
    // DSA/Java Concept: String parsing + exception handling
    private static double readTimeSafely(Scanner sc) {

        while (true) {

            String input = sc.nextLine().trim();

            try {
                double hour;
                int minutes = 0;

                if (input.contains(":")) {
                    String[] parts = input.split(":");
                    hour = Double.parseDouble(parts[0].trim());
                    minutes = Integer.parseInt(parts[1].trim());
                } else {
                    hour = Double.parseDouble(input);
                }

                if (hour < 0 || hour > 23) {
                    System.out.print(
                        "Hour must be 0-23. Try again: ");
                    continue;
                }

                if (minutes < 0 || minutes > 59) {
                    System.out.print(
                        "Minutes must be 0-59. Try again: ");
                    continue;
                }

                // Convert to decimal hour: 23:40 -> 23.67
                double decimalTime = hour + (minutes / 60.0);
                return decimalTime;

            } catch (NumberFormatException e) {
                System.out.print(
                    "Invalid format. Use HH or HH:MM "
                    + "(e.g 23 or 23:40): ");
            }
        }
    }

    // ── Format decimal hour to readable time ────────────
    private static String formatTime(double decimalHour) {

        int hour = (int) decimalHour;
        int minutes = (int) Math.round((decimalHour - hour) * 60);

        String period = hour < 12 ? "AM" : "PM";
        int displayHour = hour % 12;
        if (displayHour == 0) displayHour = 12;

        return String.format("%d:%02d %s",
            displayHour, minutes, period);
    }

    private static void printQualityAnalysis(
            double duration, int age) {

        System.out.println("\n  === SLEEP QUALITY ANALYSIS ===");

        int idealMin, idealMax;

        if (age < 18) {
            idealMin = 8; idealMax = 10;
        } else if (age <= 64) {
            idealMin = 7; idealMax = 9;
        } else {
            idealMin = 7; idealMax = 8;
        }

        System.out.println("  Ideal for your age : "
            + idealMin + "-" + idealMax + " hours");

        if (duration < idealMin) {
            double deficit = idealMin - duration;
            System.out.println("  Status : INSUFFICIENT SLEEP");
            System.out.printf("  You need %.1f more hour(s) of sleep%n",
                deficit);
        } else if (duration > idealMax) {
            System.out.println("  Status : OVERSLEEPING");
            System.out.println("  Too much sleep can also affect energy levels");
        } else {
            System.out.println("  Status : PERFECT SLEEP DURATION");
        }
    }

    private static void printSleepSuggestions(
            double duration, double sleepTime) {

        System.out.println("\n  === SLEEP IMPROVEMENT TIPS ===");

        if (duration < 6) {
            System.out.println("  > Sone ka time fix karo - daily same time");
            System.out.println("  > Phone screen sone se 1 hour pehle band karo");
            System.out.println("  > Caffeine evening ke baad avoid karo");
            System.out.println("  > Room dark aur cool rakho");
        } else if (duration >= 6 && duration < 7) {
            System.out.println("  > Thoda aur jaldi sone ki koshish karo");
            System.out.println("  > Weekend pe bhi same schedule follow karo");
        } else {
            System.out.println("  > Great sleep schedule! Isse maintain karo");
        }

        // Late night check - sleepTime stored as 24hr decimal
        if (sleepTime >= 0 && sleepTime <= 4) {
            System.out.println("  > Bahut late so rahe ho - health risk hai");
            System.out.println("  > Gradually sleep time pehle karo - 15 min daily");
        } else if (sleepTime >= 23) {
            System.out.println("  > Thoda late hai - 10-11 PM ideal hota hai");
        }

        System.out.println("\n  GENERAL TIPS:");
        System.out.println("  > Sone se pehle light stretching karo");
        System.out.println("  > Warm milk pi sakte ho - acha sleep aata hai");
        System.out.println("  > Heavy meal sone se 2-3 ghante pehle khao");
        System.out.println("  > Meditation ya deep breathing try karo");
    }

    private static void printGoalConnection(
            String goal, double duration) {

        System.out.println("\n  === SLEEP AND YOUR GOAL ===");

        if (goal.equalsIgnoreCase("Weight Loss")) {
            System.out.println("  Kam neend se cortisol (stress hormone)");
            System.out.println("  badhta hai - weight loss mushkil hoti hai");
            if (duration < 7) {
                System.out.println("  Tip: 7+ hours sleep weight loss ke liye zaroori hai");
            }
        } else if (goal.equalsIgnoreCase("Weight Gain")) {
            System.out.println("  Muscle recovery neend mein hoti hai");
            System.out.println("  Growth hormone neend ke dauraan release hota hai");
            if (duration < 7) {
                System.out.println("  Tip: Muscle building ke liye 7-9 hours zaroori");
            }
        } else {
            System.out.println("  Achi neend overall health maintain karti hai");
            System.out.println("  Mood, energy aur focus sab improve hota hai");
        }
    }
}