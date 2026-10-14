// ======================================================
// WorkoutPlan.java - 1 HOUR WORKOUT VERSION
// Changes:
// 1. Now builds a full 60-minute session per day
// 2. Includes warm up + main exercises + cool down
// 3. Exercises repeat with variation to fill time
// DSA: ArrayList, HashMap, Random, While loop with time tracking
// ======================================================

import java.time.*;
import java.util.*;

public class WorkoutPlan {

    private static final String[] DAYS = {
        "MONDAY", "TUESDAY", "WEDNESDAY",
        "THURSDAY", "FRIDAY", "SATURDAY", "SUNDAY"
    };

    private static final HashMap<String, String> WORKOUT_SPLIT
        = new HashMap<>();
    private static final HashMap<String, String> YOGA_SPLIT
        = new HashMap<>();

    // Target session time in seconds (1 hour = 3600 sec)
    // Warm up 5 min + Main 50 min + Cool down 5 min
    private static final int TARGET_MAIN_SECONDS = 50 * 60;

    static {
        WORKOUT_SPLIT.put("MONDAY",    "Chest");
        WORKOUT_SPLIT.put("TUESDAY",   "Legs");
        WORKOUT_SPLIT.put("WEDNESDAY", "Abs");
        WORKOUT_SPLIT.put("THURSDAY",  "Shoulder");
        WORKOUT_SPLIT.put("FRIDAY",    "Arms");
        WORKOUT_SPLIT.put("SATURDAY",  "Full Body");
        WORKOUT_SPLIT.put("SUNDAY",    "REST");

        YOGA_SPLIT.put("MONDAY",    "Full Body");
        YOGA_SPLIT.put("TUESDAY",   "Back");
        YOGA_SPLIT.put("WEDNESDAY", "Abs");
        YOGA_SPLIT.put("THURSDAY",  "Legs");
        YOGA_SPLIT.put("FRIDAY",    "Thighs");
        YOGA_SPLIT.put("SATURDAY",  "Full Body");
        YOGA_SPLIT.put("SUNDAY",    "REST");
    }

    public static void generateWorkoutPlan(User user) {

        String goal        = user.getGoalValue();
        String workoutTime = user.getWorkoutTime();
        String gender      = user.getGender();

        System.out.println("\n");
        System.out.println("==========================================");
        System.out.println("         SMARTFIT WORKOUT PLAN");
        System.out.println("            (1 Hour Session)");
        System.out.println("==========================================");

        Scanner sc = new Scanner(System.in);

        System.out.println("\nSelect Fitness Level:");
        System.out.println("  1. Beginner     (Naya shuru kar rahe ho)");
        System.out.println("  2. Intermediate (6 months se kar rahe ho)");
        System.out.println("  3. Advanced     (1+ year experience)");
        System.out.print("\nEnter level (Beginner/Intermediate/Advanced): ");
        String level = sc.nextLine().trim();

        System.out.println("\nSelect Workout Type:");
        System.out.println("  1. Workout  (Exercises, Strength)");
        System.out.println("  2. Yoga     (Asanas, Pranayama)");
        System.out.print("\nEnter type (Workout/Yoga): ");
        String type = sc.nextLine().trim();

        ArrayList<Exercise> allExercises =
            WorkoutDatabase.getExercises();

        ArrayList<Exercise> filtered =
            WorkoutFilter.filterExercises(
                allExercises, level, type, goal);

        System.out.println("\n------------------------------------------");
        System.out.println("  YOUR WORKOUT PROFILE");
        System.out.println("------------------------------------------");
        System.out.println("  Goal         : " + goal);
        System.out.println("  Level        : " + level);
        System.out.println("  Type         : " + type);
        System.out.println("  Workout Time : " + workoutTime.toUpperCase());
        System.out.println("  Gender       : " + gender);
        System.out.println("  Session Time : 60 minutes (1 hour)");

        printGoalTip(goal, type);

        LocalDate today = LocalDate.now();
        String todayName = today.getDayOfWeek().toString();

        System.out.println("\n==========================================");
        System.out.println("  TODAY IS: " + todayName
            + " (" + today + ")");
        System.out.println("==========================================");

        String todayBodyPart = type.equalsIgnoreCase("Yoga")
            ? YOGA_SPLIT.getOrDefault(todayName, "Full Body")
            : WORKOUT_SPLIT.getOrDefault(todayName, "Full Body");

        System.out.println("\n[TODAY'S 1-HOUR WORKOUT SESSION]");
        printFullHourSession(todayName, todayBodyPart,
            filtered, type, workoutTime, level);

        // Ask if they want to see full week too
        System.out.print("\nSee full weekly plan too? (yes/no): ");
        String seeWeek = sc.nextLine().trim();

        if (seeWeek.equalsIgnoreCase("yes")) {
            System.out.println("\n==========================================");
            System.out.println("         FULL WEEKLY PLAN (Summary)");
            System.out.println("==========================================");

            for (String day : DAYS) {
                String bodyPart = type.equalsIgnoreCase("Yoga")
                    ? YOGA_SPLIT.get(day)
                    : WORKOUT_SPLIT.get(day);

                String marker = day.equals(todayName)
                    ? " << TODAY" : "";

                System.out.println("\n  " + day + marker
                    + "  ->  Focus: " + bodyPart
                    + "  (1 hour session)");
            }
            System.out.println("\n  Each day follows same 1-hour structure");
            System.out.println("  shown above for today, with that day's");
            System.out.println("  body part focus.");
        }

        printWorkoutTips(type, level, goal);
        printWarmUpCoolDown(type);
    }

    // ── Build a FULL 1-hour session ────────────────────
    // DSA: While loop that tracks elapsed time, ArrayList
    // for picking exercises, Random for variety
    private static void printFullHourSession(
            String day, String bodyPart,
            ArrayList<Exercise> filtered,
            String type, String workoutTime, String level) {

        if (bodyPart.equals("REST")) {
            printRestDay(type, workoutTime);
            return;
        }

        System.out.println("  Focus       : " + bodyPart);
        System.out.println("  Time        : " + workoutTime.toUpperCase());
        System.out.println("  Total Time  : 60 minutes");
        System.out.println("------------------------------------------");

        // ── PART 1: WARM UP (5 minutes) ────────────────
        System.out.println("\n  ===== WARM UP (5 minutes) =====");
        printWarmUpSet(type);

        // ── PART 2: MAIN WORKOUT (50 minutes) ──────────
        System.out.println("\n  ===== MAIN WORKOUT (50 minutes) =====");

        ArrayList<Exercise> dayExercises =
            WorkoutFilter.filterByBodyPart(filtered, bodyPart);

        if (dayExercises.isEmpty()) {
            if (bodyPart.equals("Legs")) {
                dayExercises = WorkoutFilter
                    .filterByBodyPart(filtered, "Thighs");
            } else if (bodyPart.equals("Thighs")) {
                dayExercises = WorkoutFilter
                    .filterByBodyPart(filtered, "Legs");
            }
            if (dayExercises.isEmpty()) {
                dayExercises = WorkoutFilter
                    .filterByBodyPart(filtered, "Full Body");
            }
        }

        // Also pull from Full Body to add variety + fill time
        ArrayList<Exercise> fullBodyExtra =
            WorkoutFilter.filterByBodyPart(filtered, "Full Body");

        // Combine pool: main body part + some full body
        ArrayList<Exercise> pool = new ArrayList<>(dayExercises);
        for (Exercise ex : fullBodyExtra) {
            if (!pool.contains(ex)) pool.add(ex);
        }

        buildHourLongRoutine(pool, level);

        // ── PART 3: COOL DOWN (5 minutes) ──────────────
        System.out.println("\n  ===== COOL DOWN (5 minutes) =====");
        printCoolDownSet(type);

        // Calories burned for full hour
        printCaloriesBurnedHour(bodyPart, level);
    }

    // ── Build exercises to fill ~50 minutes ────────────
    // Increases sets for each exercise based on level
    // so total time reaches close to 50 minutes
    private static void buildHourLongRoutine(
            ArrayList<Exercise> pool, String level) {

        if (pool.isEmpty()) {
            System.out.println("  No exercises found for this session.");
            return;
        }

        Random random = new Random();

        // Level based set multiplier - higher level = more sets
        int setMultiplier;
        if (level.equalsIgnoreCase("Advanced")) {
            setMultiplier = 2;
        } else if (level.equalsIgnoreCase("Intermediate")) {
            setMultiplier = 2;
        } else {
            setMultiplier = 1;
        }

        // Pick distinct exercises - up to 8 for variety in 1 hour
        int numExercises = Math.min(8, pool.size());

        ArrayList<Integer> usedIdx = new ArrayList<>();
        int totalSeconds = 0;
        int exerciseNum = 0;

        // Keep adding exercises until ~50 min reached
        // or we run out of unique exercises (then repeat with variation)
        while (totalSeconds < TARGET_MAIN_SECONDS) {

            int idx = random.nextInt(pool.size());
            Exercise ex = pool.get(idx);

            exerciseNum++;

            // Boost sets for longer duration
            int boostedSets = ex.getSets() * setMultiplier;

            int timePerSet = ex.getType().equalsIgnoreCase("Yoga")
                ? ex.getReps()
                : ex.getReps() * 3;

            int exerciseTime = (boostedSets * timePerSet)
                + (boostedSets * ex.getRestSeconds());

            System.out.println("\n  +-- Exercise " + exerciseNum
                + " ----------------------------+");
            System.out.println("  | Name      : " + ex.getName());
            System.out.println("  | Body Part : " + ex.getBodyPart());
            System.out.println("  | Sets      : " + boostedSets
                + " (increased for 1-hr session)");

            if (ex.getType().equalsIgnoreCase("Yoga")) {
                System.out.println("  | Hold      : " + ex.getReps() + " seconds");
            } else {
                System.out.println("  | Reps      : " + ex.getReps());
            }

            System.out.println("  | Rest      : " + ex.getRestSeconds() + " seconds");
            System.out.println("  +----------------------------------------+");

            System.out.println("\n  HOW TO DO:");
            String[] steps = ex.getHowToDo().split("\n");
            for (String step : steps) {
                System.out.println("    " + step);
            }
            System.out.println("\n  TIP: " + ex.getTip());

            System.out.println("\n  Time for this exercise: ~"
                + (exerciseTime / 60) + " min " + (exerciseTime % 60) + " sec");

            totalSeconds += exerciseTime;

            System.out.println("  Session time so far: ~"
                + (totalSeconds / 60) + " min / 50 min target");

            // Safety: stop after reasonable max exercises
            if (exerciseNum >= 14) break;
        }

        System.out.println("\n  ------------------------------------------");
        System.out.println("  TOTAL MAIN WORKOUT TIME: ~"
            + (totalSeconds / 60) + " minutes");
        System.out.println("  Total Exercises Done: " + exerciseNum);
        System.out.println("  ------------------------------------------");
    }

    // ── Warm up exercises set ──────────────────────────
    private static void printWarmUpSet(String type) {

        if (type.equalsIgnoreCase("Yoga")) {
            System.out.println("  1. Anulom Vilom    - 2 min");
            System.out.println("  2. Neck rotations  - 1 min");
            System.out.println("  3. Shoulder rolls  - 1 min");
            System.out.println("  4. Knee rotations  - 1 min");
        } else {
            System.out.println("  1. Jumping Jacks   - 1 min");
            System.out.println("  2. Arm Circles     - 1 min");
            System.out.println("  3. Leg Swings      - 1 min");
            System.out.println("  4. High Knees      - 1 min");
            System.out.println("  5. Light jog spot  - 1 min");
        }
    }

    // ── Cool down exercises set ────────────────────────
    private static void printCoolDownSet(String type) {

        if (type.equalsIgnoreCase("Yoga")) {
            System.out.println("  1. Shavasana       - 3 min (must)");
            System.out.println("  2. Deep breathing  - 2 min");
        } else {
            System.out.println("  1. Hamstring Stretch  - 1 min");
            System.out.println("  2. Quad Stretch       - 1 min");
            System.out.println("  3. Chest Opener       - 1 min");
            System.out.println("  4. Child Pose         - 2 min");
        }
    }

    // ── Calories for full hour session ─────────────────
    private static void printCaloriesBurnedHour(
            String bodyPart, String level) {

        int calPerHour;
        switch (bodyPart) {
            case "Full Body": calPerHour = 400; break;
            case "Legs":      calPerHour = 360; break;
            case "Abs":       calPerHour = 300; break;
            case "Chest":     calPerHour = 320; break;
            case "Shoulder":  calPerHour = 280; break;
            case "Arms":      calPerHour = 260; break;
            case "Thighs":    calPerHour = 340; break;
            case "Back":      calPerHour = 300; break;
            default:          calPerHour = 300;
        }

        if (level.equalsIgnoreCase("Intermediate")) calPerHour += 60;
        if (level.equalsIgnoreCase("Advanced"))      calPerHour += 120;

        System.out.println("\n  EST. CALORIES BURNED : ~"
            + calPerHour + " kcal (full 1-hour session)");
        System.out.println("------------------------------------------");
    }

    private static void printRestDay(
            String type, String workoutTime) {

        System.out.println("\n  *** REST DAY ***");
        System.out.println("  ---------------------------------");

        if (type.equalsIgnoreCase("Yoga")) {
            System.out.println("  Today's Light Practice (30 min total):");
            System.out.println("  > Anulom Vilom  - 10 minutes");
            System.out.println("  > Bhramari      - 5 minutes");
            System.out.println("  > Meditation    - 15 minutes");
        } else {
            System.out.println("  Recovery Activities (45-60 min):");
            System.out.println("  > Light walk    - 30 minutes");
            System.out.println("  > Full body stretch - 15 minutes");
            System.out.println("  > Extra water today");
            System.out.println("  > 8 hours sleep tonight");
        }

        System.out.println("\n  Rest day is as important as workout day!");
    }

    private static void printGoalTip(String goal, String type) {

        System.out.println("\n------------------------------------------");
        System.out.println("  GOAL STRATEGY");
        System.out.println("------------------------------------------");

        if (goal.equalsIgnoreCase("Weight Loss")) {
            System.out.println("  [WEIGHT LOSS STRATEGY]");
            System.out.println("  > Cardio focus - high reps low rest");
            System.out.println("  > Morning workout - fat burn zyada");
            System.out.println("  > Full 1 hour daily for best results");
        } else if (goal.equalsIgnoreCase("Weight Gain")) {
            System.out.println("  [WEIGHT GAIN STRATEGY]");
            System.out.println("  > Strength focus - compound moves");
            System.out.println("  > Eat within 30 min after workout");
            System.out.println("  > Full hour with heavier sets");
        } else {
            System.out.println("  [MAINTAIN STRATEGY]");
            System.out.println("  > Mix of strength + cardio");
            System.out.println("  > 1 hour daily maintains fitness well");
        }
    }

    private static void printWarmUpCoolDown(String type) {
        System.out.println("\n  (Warm up and cool down already included");
        System.out.println("   in your 1-hour session above)");
    }

    private static void printWorkoutTips(
            String type, String level, String goal) {

        System.out.println("\n==========================================");
        System.out.println("         IMPORTANT TIPS");
        System.out.println("==========================================");

        System.out.println("\n  General Tips:");
        System.out.println("  > Pani peete raho workout ke dauraan");
        System.out.println("  > Form sahi rakho - injury se bacho");
        System.out.println("  > Full 1 hour complete karne ki koshish karo");
        System.out.println("  > Rest days skip mat karo");

        System.out.println("\n  " + level + " Tips:");
        if (level.equalsIgnoreCase("Beginner")) {
            System.out.println("  > Pehle hafte mein 40 min se shuru karo");
            System.out.println("  > Gradually 1 hour tak badhao");
            System.out.println("  > Dard aaye to ruko");
        } else if (level.equalsIgnoreCase("Intermediate")) {
            System.out.println("  > Full 1 hour complete karo");
            System.out.println("  > Progressive overload karo");
        } else {
            System.out.println("  > Full 1 hour with heavy sets");
            System.out.println("  > Deload week lo har 8 weeks");
        }

        System.out.println("\n==========================================");
        System.out.println("  SmartFit ke saath strong bano!");
        System.out.println("  Aaj ki mehnat, kal ka result!");
        System.out.println("==========================================\n");
    }
}