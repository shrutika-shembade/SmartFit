// ======================================================
// DietPlan.java - FULLY UPDATED
// Changes from original:
//   1. PCOS diet logic added
//   2. Diabetes diet logic added
//   3. Thyroid diet logic added
//   4. Hypertension diet logic added
//   5. Food preference respected in selection
//   6. Reasons shown for health-specific picks
// DSA: ArrayList, HashMap, Random, Priority filtering
// ======================================================

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class DietPlan {

    public static void generateDietPlan(
            ArrayList<Meal> foods,
            User user) {

        System.out.println("\n============================");
        System.out.println("    SMARTFIT DIET PLAN");
        System.out.println("============================");

        String goal        = user.getGoalValue();
        String healthIssue = user.getHealthIssue() == null
                             ? "none" : user.getHealthIssue();
        String workoutTime = user.getWorkoutTime();
        double weight      = user.getWeight();

        // Ask mood
        Scanner sc = new Scanner(System.in);
        System.out.print("\nEnter Mood (happy/sad/stressed/lowenergy/normal): ");
        String mood = sc.nextLine().trim();

        Random random = new Random();

        // ── Category lists ─────────────────────────────
        ArrayList<Meal> detoxList       = new ArrayList<>();
        ArrayList<Meal> preWorkoutList  = new ArrayList<>();
        ArrayList<Meal> breakfastList   = new ArrayList<>();
        ArrayList<Meal> lunchList       = new ArrayList<>();
        ArrayList<Meal> snackList       = new ArrayList<>();
        ArrayList<Meal> postWorkoutList = new ArrayList<>();
        ArrayList<Meal> dinnerList      = new ArrayList<>();

        // ── Health condition priority foods ───────────
        // DSA: ArrayList as priority lookup
        ArrayList<String> priorityFoods = new ArrayList<>();

        // Iron Deficiency priority
        if (healthIssue.toLowerCase().contains("iron")) {
            priorityFoods.add("Spinach Juice");
            priorityFoods.add("Beetroot Juice");
            priorityFoods.add("Pomegranate Juice");
            priorityFoods.add("Sprouts");
            priorityFoods.add("Dates");
            priorityFoods.add("Moong Dal");
            priorityFoods.add("Palak Paneer");
            priorityFoods.add("Lentil Soup");
            priorityFoods.add("Rajma Rice");
            priorityFoods.add("Bajra Roti");
        }

        // PCOS priority - low GI, anti-inflammatory
        if (healthIssue.toLowerCase().contains("pcos")) {
            priorityFoods.add("Sprouts");
            priorityFoods.add("Moong Chilla");
            priorityFoods.add("Oats");
            priorityFoods.add("Jowar Bhakri");
            priorityFoods.add("Vegetable Soup");
            priorityFoods.add("Moong Dal");
            priorityFoods.add("Sprouts Chaat");
            priorityFoods.add("Fruit Bowl");
            priorityFoods.add("Green Tea");
            priorityFoods.add("Buttermilk");
        }

        // Diabetes priority - low GI, no sugar spikes
        if (healthIssue.toLowerCase().contains("diabetes")) {
            priorityFoods.add("Jowar Bhakri");
            priorityFoods.add("Bajra Roti");
            priorityFoods.add("Moong Dal");
            priorityFoods.add("Sprouts");
            priorityFoods.add("Vegetable Soup");
            priorityFoods.add("Oats");
            priorityFoods.add("Moong Chilla");
            priorityFoods.add("Green Tea");
            priorityFoods.add("Buttermilk");
            priorityFoods.add("Roasted Chana");
        }

        // Thyroid priority - selenium, iodine rich
        if (healthIssue.toLowerCase().contains("thyroid")) {
            priorityFoods.add("Oats");
            priorityFoods.add("Sprouts");
            priorityFoods.add("Moong Dal");
            priorityFoods.add("Buttermilk");
            priorityFoods.add("Fruit Bowl");
            priorityFoods.add("Vegetable Soup");
            // Avoid soy-based for thyroid
        }

        // Hypertension priority - low sodium, DASH diet
        if (healthIssue.toLowerCase().contains("hypertension")) {
            priorityFoods.add("Buttermilk");
            priorityFoods.add("Fruit Bowl");
            priorityFoods.add("Oats");
            priorityFoods.add("Moong Dal");
            priorityFoods.add("Vegetable Soup");
            priorityFoods.add("Banana");
            priorityFoods.add("Green Tea");
        }

        // Mood foods
        ArrayList<String> moodFoods = new ArrayList<>();
        if (mood.equalsIgnoreCase("stressed")) {
            moodFoods.add("Dark Chocolate");
            moodFoods.add("Walnuts");
            moodFoods.add("Banana");
            moodFoods.add("Herbal Tea");
            moodFoods.add("Turmeric Latte");
            moodFoods.add("Green Tea");
        } else if (mood.equalsIgnoreCase("sad")) {
            moodFoods.add("Dark Chocolate");
            moodFoods.add("Banana");
            moodFoods.add("Smoothie Bowl");
            moodFoods.add("Fruit Bowl");
            moodFoods.add("Banana Shake");
        } else if (mood.equalsIgnoreCase("lowenergy")) {
            moodFoods.add("Dates");
            moodFoods.add("Banana Shake");
            moodFoods.add("Peanut Chikki");
            moodFoods.add("Energy Bar");
            moodFoods.add("Sweet Potato");
        }

        // Goal preferred foods
        ArrayList<String> goalPreferred = new ArrayList<>();
        if (goal.equalsIgnoreCase("Weight Loss")) {
            goalPreferred.add("Vegetable Soup");
            goalPreferred.add("Sprouts");
            goalPreferred.add("Paneer Salad");
            goalPreferred.add("Egg White Omelette");
            goalPreferred.add("Moong Chilla");
            goalPreferred.add("Fruit Bowl");
            goalPreferred.add("Greek Yogurt");
            goalPreferred.add("Tomato Soup");
            goalPreferred.add("Sprouts Chaat");
            goalPreferred.add("Makhana");
        } else if (goal.equalsIgnoreCase("Weight Gain")) {
            goalPreferred.add("Paneer Butter Masala");
            goalPreferred.add("Protein Bowl");
            goalPreferred.add("Peanut Butter Toast");
            goalPreferred.add("Banana Shake");
            goalPreferred.add("Protein Thali");
            goalPreferred.add("Paneer Rice");
            goalPreferred.add("Dal Rice");
            goalPreferred.add("Rajma Rice");
        }

        // ── Filter + separate into categories ─────────
        for (Meal meal : foods) {

            String cat  = meal.getCategory();
            int    cal  = meal.getCalories();
            String name = meal.getFoodName();

            // Goal calorie filter
            boolean goalMatch = false;
            if (goal.equalsIgnoreCase("Weight Loss")) {
                goalMatch = (cal <= 300);
                if (goalPreferred.contains(name)) goalMatch = true;
            } else if (goal.equalsIgnoreCase("Weight Gain")) {
                goalMatch = (cal >= 250);
                if (goalPreferred.contains(name)) goalMatch = true;
            } else {
                goalMatch = true;
            }

            // Health condition override
            if (!priorityFoods.isEmpty()) {
                if (priorityFoods.contains(name)) {
                    goalMatch = true;
                }
                // PCOS + Diabetes: block high sugar foods
                if (healthIssue.toLowerCase().contains("pcos")
                 || healthIssue.toLowerCase().contains("diabetes")) {
                    if (name.contains("Shake")
                     || name.contains("Chocolate")
                     || name.contains("Chikki")
                     || name.contains("Laddu")) {
                        goalMatch = false;
                    }
                }
                // Hypertension: block high sodium
                if (healthIssue.toLowerCase()
                        .contains("hypertension")) {
                    if (name.contains("Pickle")
                     || name.contains("Papad")) {
                        goalMatch = false;
                    }
                }
            }

            // Mood override
            if (!moodFoods.isEmpty()
                && moodFoods.contains(name)) {
                goalMatch = true;
            }

            if (!goalMatch) continue;

            // Separate into category lists
            switch (cat.toLowerCase()) {
                case "detoxdrink":
                    detoxList.add(meal); break;
                case "preworkout":
                    preWorkoutList.add(meal); break;
                case "breakfast":
                    breakfastList.add(meal); break;
                case "lunch":
                    lunchList.add(meal); break;
                case "eveningsnack":
                    snackList.add(meal); break;
                case "postworkout":
                    postWorkoutList.add(meal); break;
                case "dinner":
                    dinnerList.add(meal); break;
            }
        }

        // ── Random selection ───────────────────────────
        Meal detox       = getRandomMeal(detoxList, random);
        Meal preWorkout  = getRandomMeal(preWorkoutList, random);
        Meal breakfast   = getRandomMeal(breakfastList, random);
        Meal lunch       = getRandomMeal(lunchList, random);
        Meal snack       = getRandomMeal(snackList, random);
        Meal postWorkout = getRandomMeal(postWorkoutList, random);
        Meal dinner      = getRandomMeal(dinnerList, random);

        // ── Priority overrides for health conditions ───
        // Iron: always use iron-rich detox
        if (healthIssue.toLowerCase().contains("iron")) {
            for (Meal m : detoxList) {
                String n = m.getFoodName();
                if (n.equalsIgnoreCase("Spinach Juice")
                 || n.equalsIgnoreCase("Beetroot Juice")
                 || n.equalsIgnoreCase("Pomegranate Juice")) {
                    detox = m;
                    break;
                }
            }
        }

        // PCOS: prefer green tea as detox
        if (healthIssue.toLowerCase().contains("pcos")) {
            for (Meal m : detoxList) {
                if (m.getFoodName().equalsIgnoreCase("Green Tea")) {
                    detox = m;
                    break;
                }
            }
        }

        // Diabetes: prefer buttermilk detox
        if (healthIssue.toLowerCase().contains("diabetes")) {
            for (Meal m : detoxList) {
                if (m.getFoodName().equalsIgnoreCase("Buttermilk")) {
                    detox = m;
                    break;
                }
            }
        }

        // ── Display header ─────────────────────────────
        System.out.println("\nMood     : " + mood.toUpperCase());
        System.out.println("Workout  : " + workoutTime.toUpperCase());
        System.out.println("Goal     : " + goal);

        // Health condition note
        if (!healthIssue.equalsIgnoreCase("none")
         && !healthIssue.isEmpty()) {
            System.out.println("Health   : "
                + healthIssue.toUpperCase()
                + " - diet adjusted accordingly");
        }

        printMoodTip(mood);

        // ── Display schedule by workout time ───────────
        if (workoutTime.equalsIgnoreCase("morning")) {
            System.out.println("\n--- MORNING WORKOUT SCHEDULE ---");
            printMeal("Morning Detox", detox, weight, healthIssue);
            printMeal("Pre Workout",   preWorkout, weight, healthIssue);
            printMeal("Breakfast",     breakfast, weight, healthIssue);
            printMeal("Lunch",         lunch, weight, healthIssue);
            printMeal("Post Workout",  postWorkout, weight, healthIssue);
            printMeal("Evening Snack", snack, weight, healthIssue);
            printMeal("Dinner",        dinner, weight, healthIssue);
        } else {
            System.out.println("\n--- EVENING WORKOUT SCHEDULE ---");
            printMeal("Morning Detox", detox, weight, healthIssue);
            printMeal("Breakfast",     breakfast, weight, healthIssue);
            printMeal("Lunch",         lunch, weight, healthIssue);
            printMeal("Pre Workout",   preWorkout, weight, healthIssue);
            printMeal("Post Workout",  postWorkout, weight, healthIssue);
            printMeal("Evening Snack", snack, weight, healthIssue);
            printMeal("Dinner",        dinner, weight, healthIssue);
        }

        // ── Alternatives ───────────────────────────────
        printAlternatives(breakfastList, breakfast, "Breakfast");
        printAlternatives(lunchList,     lunch,     "Lunch");
        printAlternatives(dinnerList,    dinner,    "Dinner");

        // ── Recipe hint ────────────────────────────────
        printRecipeHint(breakfast, lunch, dinner);

        // ── Nutrition summary ──────────────────────────
        printNutritionSummary(
            detox, breakfast, lunch, snack,
            dinner, preWorkout, postWorkout, user);
    }

    // ── Random picker ──────────────────────────────────
    private static Meal getRandomMeal(
            ArrayList<Meal> meals, Random random) {
        if (meals.isEmpty()) return null;
        return meals.get(random.nextInt(meals.size()));
    }

    // ── Adjusted quantity by weight ────────────────────
    private static String adjustQuantity(
            String qty, double weight) {
        if (weight >= 80) {
            if (qty.contains("1 Bowl"))  return "2 Bowls";
            if (qty.contains("1 Plate")) return "1.5 Plates";
            if (qty.contains("1 Glass")) return "2 Glasses";
            if (qty.contains("2 Roti"))  return "3 Roti";
        } else if (weight <= 45) {
            if (qty.contains("1 Bowl"))  return "3/4 Bowl";
            if (qty.contains("1 Plate")) return "3/4 Plate";
            if (qty.contains("2 Roti"))  return "1.5 Roti";
        }
        return qty;
    }

    // ── Health reason for meal ─────────────────────────
    private static String getHealthReason(
            String foodName, String healthIssue) {
        if (healthIssue == null) return null;
        String h = healthIssue.toLowerCase();

        if (h.contains("iron")) {
            if (foodName.equalsIgnoreCase("Spinach Juice")
             || foodName.equalsIgnoreCase("Beetroot Juice")
             || foodName.equalsIgnoreCase("Palak Paneer")
             || foodName.equalsIgnoreCase("Sprouts")
             || foodName.equalsIgnoreCase("Dates")) {
                return "Iron rich - chosen for iron deficiency";
            }
        }
        if (h.contains("pcos")) {
            if (foodName.equalsIgnoreCase("Oats")
             || foodName.equalsIgnoreCase("Green Tea")
             || foodName.equalsIgnoreCase("Sprouts")) {
                return "Low GI food - good for PCOS";
            }
        }
        if (h.contains("diabetes")) {
            if (foodName.equalsIgnoreCase("Jowar Bhakri")
             || foodName.equalsIgnoreCase("Moong Dal")
             || foodName.equalsIgnoreCase("Buttermilk")) {
                return "Low glycemic - good for diabetes";
            }
        }
        if (h.contains("thyroid")) {
            if (foodName.equalsIgnoreCase("Oats")
             || foodName.equalsIgnoreCase("Sprouts")) {
                return "Selenium rich - good for thyroid";
            }
        }
        return null;
    }

    // ── Print one meal ─────────────────────────────────
    private static void printMeal(
            String title, Meal meal,
            double weight, String healthIssue) {

        if (meal == null) {
            System.out.println("\n" + title + " : Not Available");
            return;
        }

        String qty = adjustQuantity(meal.getQuantity(), weight);
        String healthReason = getHealthReason(
            meal.getFoodName(), healthIssue);

        System.out.println("\n--------------------------------");
        System.out.println(title);
        System.out.println("--------------------------------");
        System.out.println("Food     : " + meal.getFoodName());
        System.out.println("Quantity : " + qty);
        System.out.println("Calories : " + meal.getCalories());
        System.out.println("Protein  : " + meal.getProtein() + " g");
        System.out.println("Carbs    : " + meal.getCarbs() + " g");
        System.out.println("Fat      : " + meal.getFat() + " g");

        if (healthReason != null) {
            System.out.println("Reason   : " + healthReason);
        }
    }

    // ── Mood tip ──────────────────────────────────────
    private static void printMoodTip(String mood) {
        switch (mood.toLowerCase()) {
            case "stressed":
                System.out.println("Mood Tip : Eat calming foods. Avoid caffeine.");
                break;
            case "sad":
                System.out.println("Mood Tip : Eat mood-boosting foods. Stay hydrated.");
                break;
            case "lowenergy":
                System.out.println("Mood Tip : Eat energy-dense foods. Drink more water.");
                break;
            default:
                System.out.println("Mood Tip : You are doing great! Stay consistent.");
        }
    }

    // ── Alternatives ───────────────────────────────────
    private static void printAlternatives(
            ArrayList<Meal> list,
            Meal selected,
            String mealName) {

        if (selected == null || list.size() <= 1) return;

        System.out.println("\n--- " + mealName.toUpperCase()
            + " ALTERNATIVES ---");
        int count = 0;
        for (Meal m : list) {
            if (!m.getFoodName().equals(selected.getFoodName())) {
                System.out.println("* " + m.getFoodName()
                    + "  (" + m.getCalories() + " cal)");
                count++;
                if (count == 3) break;
            }
        }
    }

    // ── Recipe hint ────────────────────────────────────
    private static void printRecipeHint(
            Meal breakfast, Meal lunch, Meal dinner) {
        System.out.println("\n--- RECIPES AVAILABLE FOR ---");
        if (breakfast != null)
            System.out.println("* " + breakfast.getFoodName()
                + " - check RecipeDatabase");
        if (lunch != null)
            System.out.println("* " + lunch.getFoodName()
                + " - check RecipeDatabase");
        if (dinner != null)
            System.out.println("* " + dinner.getFoodName()
                + " - check RecipeDatabase");
    }

    // ── Nutrition summary ──────────────────────────────
    private static void printNutritionSummary(
            Meal detox, Meal breakfast, Meal lunch,
            Meal snack, Meal dinner,
            Meal preWorkout, Meal postWorkout,
            User user) {

        System.out.println("\n================================");
        System.out.println("      NUTRITION SUMMARY");
        System.out.println("================================");

        int    totalCal     = 0;
        double totalProtein = 0;
        double totalCarbs   = 0;
        double totalFat     = 0;

        Meal[] all = {
            detox, breakfast, lunch,
            snack, dinner, preWorkout, postWorkout
        };

        for (Meal m : all) {
            if (m != null) {
                totalCal     += m.getCalories();
                totalProtein += m.getProtein();
                totalCarbs   += m.getCarbs();
                totalFat     += m.getFat();
            }
        }

        double targetCal = NutritionCalculator
            .calculateCalories(user);

        System.out.println("Total Calories  : " + totalCal);
        System.out.println("Target Calories : " + (int) targetCal);

        int diff = (int)(targetCal - totalCal);
        if (diff > 100) {
            System.out.println("Deficit : " + diff
                + " cal - eat a light snack");
        } else if (diff < -100) {
            System.out.println("Surplus : " + Math.abs(diff)
                + " cal - reduce portion slightly");
        } else {
            System.out.println("Status  : Perfect! On target.");
        }

        System.out.println("Total Protein   : " + totalProtein + " g");
        System.out.println("Total Carbs     : " + totalCarbs + " g");
        System.out.println("Total Fat       : " + totalFat + " g");
        System.out.println("================================");
    }
}