// ======================================================
// Main.java - PHASE 1 FINAL COMPLETE
// All modules connected in correct flow order
// Fixes: lifestyle multiplier, recipe connection,
//        veg/nonveg filter, health conditions
// ======================================================

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("==========================================");
        System.out.println("     WELCOME TO SMARTFIT");
        System.out.println("  Complete Health & Lifestyle Planner");
        System.out.println("==========================================");

        // ══════════════════════════════════════════════
        // MODULE 1: USER REGISTRATION
        // ══════════════════════════════════════════════

        System.out.println("\n===== BASIC DETAILS =====");

        System.out.print("Enter Age: ");
        int age = sc.nextInt();

        System.out.print("Enter Height (cm): ");
        double height = sc.nextDouble();
        height = height / 100.0;

        System.out.print("Enter Weight (kg): ");
        double weight = sc.nextDouble();
        sc.nextLine();

        System.out.print("Enter Gender (Male/Female): ");
        String gender = sc.nextLine();

        // Occupation
        System.out.println("\n===== OCCUPATION =====");
        System.out.println("1. Student");
        System.out.println("2. OfficeWorker");
        System.out.println("3. GymPerson");
        System.out.println("4. Housewife");
        System.out.println("5. Pregnant");
        System.out.println("6. Senior");
        System.out.print("Enter occupation: ");
        String occupation = sc.nextLine();

        // Food preference
        System.out.println("\n===== FOOD PREFERENCE =====");
        System.out.println("1. Veg");
        System.out.println("2. NonVeg");
        System.out.println("3. Vegan");
        System.out.print("Enter food preference: ");
        String foodPreference = sc.nextLine();

        // Lifestyle
        System.out.println("\n===== LIFESTYLE =====");
        System.out.println("1. Sedentary  (desk job, no exercise)");
        System.out.println("2. Moderate   (light exercise 1-3 days/week)");
        System.out.println("3. Active     (exercise 3-5 days/week)");
        System.out.println("4. VeryActive (hard training 6-7 days/week)");
        System.out.print("Enter lifestyle: ");
        String lifestyle = sc.nextLine();

        System.out.print("Working hours per day: ");
        String workingHours = sc.nextLine();

        // Schedule
        System.out.println("\n===== YOUR SCHEDULE =====");
        System.out.print("Sleep time (24hr hour only, e.g 23): ");
        String sleepTime = sc.nextLine() + ":00";

        System.out.print("Wake time (24hr hour only, e.g 6): ");
        String wakeTime = sc.nextLine() + ":00";

        System.out.print("Workout time (morning/evening): ");
        String workoutTime = sc.nextLine();

        // Region and budget
        System.out.println("\n===== REGION & BUDGET =====");
        System.out.print("Region (Maharashtra/NorthIndia/SouthIndia/All): ");
        String region = sc.nextLine();

        System.out.print("Budget (low/medium/high): ");
        String budget = sc.nextLine();

        // Health issues
        System.out.println("\n===== HEALTH ISSUES =====");
        System.out.println("Enter health issue (or none):");
        System.out.println("iron / diabetes / pcos / thyroid /");
        System.out.println("hypertension / obesity / none");
        System.out.print("Health Issue: ");
        String healthIssue = sc.nextLine();

        // ── Create user ───────────────────────────────
        User user = new User(
            age, height, weight, gender,
            region, budget, workoutTime, healthIssue,
            occupation, foodPreference, lifestyle,
            sleepTime, wakeTime, workingHours
        );

        // ══════════════════════════════════════════════
        // MODULE 1 OUTPUT: USER PROFILE
        // ══════════════════════════════════════════════
        user.displayUser();

        // ══════════════════════════════════════════════
        // MODULE 2: BODY ANALYSIS
        // ══════════════════════════════════════════════
        NutritionCalculator.displayFullAnalysis(user);

        // ══════════════════════════════════════════════
        // MODULE 3: DIET PLAN
        // Fix 3: foodPreference now passed to filter
        // Fix 1: health conditions handled in DietPlan
        // ══════════════════════════════════════════════
        ArrayList<Meal> allFoods = FoodDatabase.getFoods();

        // Fix 3: Pass food preference to filter
        ArrayList<Meal> filteredFoods =
            FoodFilter.filterFoods(
                allFoods,
                user.getRegion(),
                user.getBudget(),
                user.getFoodPreference()
            );

        DietPlan.generateDietPlan(filteredFoods, user);

        // ══════════════════════════════════════════════
        // MODULE 5: RECIPE MODULE
        // Fix 4: Now connected to main flow
        // ══════════════════════════════════════════════
        System.out.println("\n\nPress ENTER to see Recipe Guide...");
        sc.nextLine();
        RecipeModule.showAllRecipesMenu(
            null, null, null, null, null, null, null);

        // ══════════════════════════════════════════════
        // MODULE 6: WORKOUT PLAN
        // ══════════════════════════════════════════════
        System.out.println("\n\nPress ENTER to see Workout Plan...");
        sc.nextLine();
        WorkoutPlan.generateWorkoutPlan(user);

        // ══════════════════════════════════════════════
        // MODULE 7: WATER TRACKER
        // ══════════════════════════════════════════════
        System.out.println("\n\nPress ENTER for Water Plan...");
        sc.nextLine();
        WaterCalculator.calculateWater(user);

        // ══════════════════════════════════════════════
        // MODULE 8: STEP TRACKER
        // ══════════════════════════════════════════════
        System.out.println("\n\nPress ENTER for Step Tracker...");
        sc.nextLine();
        StepTracker.trackSteps(user);

        // ══════════════════════════════════════════════
        // MODULE 9 + 10: WEIGHT + PROGRESS TRACKER
        // ══════════════════════════════════════════════
        System.out.println("\n\nPress ENTER for Progress Tracker...");
        sc.nextLine();
        ProgressTracker.trackProgress(user);

        // ══════════════════════════════════════════════
        // MODULE 11: SLEEP TRACKER
        // ══════════════════════════════════════════════
        System.out.println("\n\nPress ENTER for Sleep Tracker...");
        sc.nextLine();
        SleepTracker.trackSleep(user);

        // ══════════════════════════════════════════════
        // MODULE 4: GROCERY LIST
        // ══════════════════════════════════════════════
        System.out.println("\n\nPress ENTER for Grocery List...");
        sc.nextLine();
        GroceryGenerator.generateGroceryList(
            null, null, null, null, null, null, null, user);

        // ══════════════════════════════════════════════
        // MODULE 12: SKIN CARE
        // ══════════════════════════════════════════════
        System.out.println("\n\nPress ENTER for Skin Care...");
        sc.nextLine();
        SkinCare.generateSkinCare(user);

        // ══════════════════════════════════════════════
        // MODULE 13: HAIR CARE
        // ══════════════════════════════════════════════
        System.out.println("\n\nPress ENTER for Hair Care...");
        sc.nextLine();
        HairCare.generateHairCare(user);

        // ══════════════════════════════════════════════
        // MODULE 14: HYGIENE ASSISTANT
        // ══════════════════════════════════════════════
        System.out.println("\n\nPress ENTER for Hygiene Guide...");
        sc.nextLine();
        HygieneAssistant.generateHygieneRoutine(user);

        // ══════════════════════════════════════════════
        // MODULE 15: REMINDERS
        // ══════════════════════════════════════════════
        System.out.println("\n\nPress ENTER for Reminder Schedule...");
        sc.nextLine();
        ReminderEngine.showReminders(user);

        // ══════════════════════════════════════════════
        // ACHIEVEMENTS
        // ══════════════════════════════════════════════
        System.out.println("\n\nPress ENTER for Achievements...");
        sc.nextLine();
        AchievementEngine.checkAchievements(user);

        // ══════════════════════════════════════════════
        // MODULE 17: WEEKLY REPORT
        // ══════════════════════════════════════════════
        System.out.println("\n\nPress ENTER to Generate Weekly Report...");
        sc.nextLine();
        WeeklyReport.generateReport(user);

        // ══════════════════════════════════════════════
        // SESSION COMPLETE
        // ══════════════════════════════════════════════
        System.out.println("\n==========================================");
        System.out.println("     SMARTFIT SESSION COMPLETE!");
        System.out.println("==========================================");
        System.out.println("Files saved:");
        System.out.println("  SmartFit_WeightHistory.txt");
        System.out.println("  SmartFit_StepHistory.txt");
        System.out.println("  SmartFit_GroceryList.txt");
        System.out.println("  SmartFit_Report_[date].txt");
        System.out.println("\nRun again tomorrow to track progress!");
        System.out.println("==========================================");

        sc.close();
    }
}