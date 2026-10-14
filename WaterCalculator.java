// ======================================================
// WaterCalculator.java
// User ki weight se daily water intake calculate karna
// Simple logic - medical formula use ho rahi hai
// ======================================================

public class WaterCalculator {

    public static void calculateWater(User user) {

        System.out.println("\n");
        System.out.println("╔══════════════════════════════════════╗");
        System.out.println("║       SMARTFIT WATER INTAKE PLAN     ║");
        System.out.println("╚══════════════════════════════════════╝");

        double weight      = user.getWeight();
        String goal        = user.getGoalValue();
        String workoutTime = user.getWorkoutTime();

        // ── Formula: weight * 35 ml per kg ────────────
        // Medical standard formula
        double baseWater = weight * 35;

        // ── Extra water for workout ────────────────────
        double workoutExtra = 500; // 500ml extra on workout days

        // ── Goal based adjustment ──────────────────────
        double goalExtra = 0;
        if (goal.equalsIgnoreCase("Weight Loss")) {
            goalExtra = 250; // extra for metabolism
        }

        double totalWater = baseWater
                          + workoutExtra
                          + goalExtra;

        // ── Glasses calculation ────────────────────────
        // 1 glass = 250ml
        int glasses = (int) Math.ceil(totalWater / 250);

        System.out.println("\n  Your Weight    : " + weight + " kg");
        System.out.println("  Goal           : " + goal);
        System.out.println("  Workout Day    : Yes");
        System.out.println("\n  ─────────────────────────────────────");
        System.out.printf ("  Base Water     : %.0f ml%n", baseWater);
        System.out.printf ("  Workout Extra  : %.0f ml%n", workoutExtra);
        if (goalExtra > 0)
        System.out.printf ("  Goal Extra     : %.0f ml%n", goalExtra);
        System.out.println("  ─────────────────────────────────────");
        System.out.printf ("  TOTAL DAILY    : %.0f ml%n", totalWater);
        System.out.printf ("  IN GLASSES     : %d glasses (250ml each)%n",
                            glasses);
        System.out.println("  ─────────────────────────────────────");

        // ── Hourly schedule ────────────────────────────
        printWaterSchedule(workoutTime, glasses);

        // ── Tips ───────────────────────────────────────
        printWaterTips(goal);
    }

    // ── Hourly water schedule ──────────────────────────
    private static void printWaterSchedule(
            String workoutTime, int glasses) {

        System.out.println("\n  ── DAILY WATER SCHEDULE ──");

        if (workoutTime.equalsIgnoreCase("morning")) {
            System.out.println("  6:00 AM  - 1 glass  (Wake up - khali pet)");
            System.out.println("  7:00 AM  - 1 glass  (Pre workout)");
            System.out.println("  8:00 AM  - 1 glass  (During workout)");
            System.out.println("  9:00 AM  - 1 glass  (Post workout)");
            System.out.println("  11:00 AM - 1 glass  (Mid morning)");
            System.out.println("  1:00 PM  - 1 glass  (Before lunch)");
            System.out.println("  3:00 PM  - 1 glass  (Afternoon)");
            System.out.println("  5:00 PM  - 1 glass  (Evening)");
            System.out.println("  7:00 PM  - 1 glass  (Before dinner)");
            System.out.println("  9:00 PM  - 1 glass  (After dinner)");
        } else {
            System.out.println("  6:00 AM  - 1 glass  (Wake up - khali pet)");
            System.out.println("  9:00 AM  - 1 glass  (Mid morning)");
            System.out.println("  11:00 AM - 1 glass  (Before lunch)");
            System.out.println("  1:00 PM  - 1 glass  (After lunch)");
            System.out.println("  3:00 PM  - 1 glass  (Afternoon)");
            System.out.println("  5:00 PM  - 1 glass  (Pre workout)");
            System.out.println("  6:00 PM  - 1 glass  (During workout)");
            System.out.println("  7:00 PM  - 1 glass  (Post workout)");
            System.out.println("  8:00 PM  - 1 glass  (Before dinner)");
            System.out.println("  10:00 PM - 1 glass  (Before sleep)");
        }

        System.out.println("\n  Total    : " + glasses
            + " glasses per day");
    }

    // ── Water tips ─────────────────────────────────────
    private static void printWaterTips(String goal) {

        System.out.println("\n  ── WATER TIPS ──");
        System.out.println("  * Subah uthke pehla kaam - 1 glass pani");
        System.out.println("  * Khane se 30 min pehle pani piyo");
        System.out.println("  * Khane ke turant baad zyada pani mat piyo");
        System.out.println("  * Thanda pani avoid karo - room temperature best");
        System.out.println("  * Peela peshab = dehydrated - zyada piyo");

        if (goal.equalsIgnoreCase("Weight Loss")) {
            System.out.println("  * Weight loss tip: Bhookh lagey to pehle pani piyo");
            System.out.println("  * Cold water se zyada calories burn hoti hai");
        } else if (goal.equalsIgnoreCase("Weight Gain")) {
            System.out.println("  * Khane ke saath zyada pani mat piyo");
            System.out.println("  * Meals ke beech mein piyo - appetite maintain");
        }

        System.out.println("\n  * Phone reminder set karo har 2 ghante");
        System.out.println("  * Water bottle saath rakho hamesha");
    }
}