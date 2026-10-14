// ======================================================
// NutritionCalculator.java - UPDATED
// Uses TDEE (BMR x Activity) for accurate calorie targets
// Added: full body analysis display method
// ======================================================

public class NutritionCalculator {

    // Daily calories using TDEE + goal adjustment
    public static double calculateCalories(User user) {
        double tdee = user.calculateTDEE();
        String goal = user.getGoal();
        if (goal.equals("Weight Loss"))  return tdee - 300;
        if (goal.equals("Weight Gain"))  return tdee + 300;
        return tdee;
    }

    // Protein: 1.6-2.0g per kg based on goal
    public static double calculateProtein(User user) {
        String goal = user.getGoal();
        if (goal.equals("Weight Gain"))  return user.getWeight() * 2.0;
        if (goal.equals("Weight Loss"))  return user.getWeight() * 1.8;
        return user.getWeight() * 1.6;
    }

    // Fat: 0.8g per kg
    public static double calculateFat(User user) {
        return user.getWeight() * 0.8;
    }

    // Carbs from remaining calories
    public static double calculateCarbs(User user) {
        double totalCal   = calculateCalories(user);
        double proteinCal = calculateProtein(user) * 4;
        double fatCal     = calculateFat(user) * 9;
        double carbCal    = totalCal - proteinCal - fatCal;
        return Math.max(carbCal / 4, 50);
    }

    // Full body analysis display - Module 2
    public static void displayFullAnalysis(User user) {

        System.out.println("\n===== COMPLETE BODY ANALYSIS =====");

        double bmi     = user.calculateBMI();
        double bmr     = user.calculateBMR();
        double tdee    = user.calculateTDEE();
        double bodyFat = user.calculateBodyFat();
        double ideal   = user.calculateIdealWeight();
        double lbm     = user.calculateLeanBodyMass();
        double diff    = user.getWeight() - ideal;

        System.out.printf("BMI            : %.2f (%s)%n", bmi, user.getCategory());
        System.out.printf("BMR            : %.0f kcal/day%n", bmr);
        System.out.printf("TDEE           : %.0f kcal/day%n", tdee);
        System.out.printf("Body Fat       : %.1f%%%n", bodyFat);
        System.out.printf("Ideal Weight   : %.1f kg%n", ideal);
        System.out.printf("Lean Body Mass : %.1f kg%n", lbm);

        if (Math.abs(diff) < 1) {
            System.out.println("Weight Status  : At ideal weight!");
        } else if (diff > 0) {
            System.out.printf("Weight Status  : %.1f kg above ideal%n", diff);
        } else {
            System.out.printf("Weight Status  : %.1f kg below ideal%n", Math.abs(diff));
        }

        System.out.println("\n===== DAILY NUTRITION TARGETS =====");
        System.out.printf("Calories : %.0f kcal%n", calculateCalories(user));
        System.out.printf("Protein  : %.0f g%n",    calculateProtein(user));
        System.out.printf("Fat      : %.0f g%n",    calculateFat(user));
        System.out.printf("Carbs    : %.0f g%n",    calculateCarbs(user));
        System.out.printf("Water    : %.0f ml%n",   user.getWeight() * 35 + 500);
    }
}