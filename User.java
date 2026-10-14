// ======================================================
// User.java - COMPLETE FINAL VERSION
// Module 1: All fields added
// - Occupation, FoodPreference, Lifestyle
// - Health issues: iron, diabetes, pcos, thyroid, hypertension
// - Sleep time, Wake time, Working hours
// - BMR, TDEE, Body Fat%, Ideal Weight, LBM calculations
// ======================================================

public class User {

    private int age;
    private double height;  // in meters
    private double weight;  // in kg
    private String gender;
    private String region;
    private String budget;
    private String workoutTime;
    private String healthIssue;

    // Module 1 additions
    private String occupation;
    private String foodPreference;
    private String lifestyle;
    private String sleepTime;
    private String wakeTime;
    private String workingHours;

    public User(
            int age, double height, double weight,
            String gender, String region, String budget,
            String workoutTime, String healthIssue,
            String occupation, String foodPreference,
            String lifestyle, String sleepTime,
            String wakeTime, String workingHours) {

        this.age           = age;
        this.height        = height;
        this.weight        = weight;
        this.gender        = gender;
        this.region        = region;
        this.budget        = budget;
        this.workoutTime   = workoutTime;
        this.healthIssue   = healthIssue;
        this.occupation    = occupation;
        this.foodPreference = foodPreference;
        this.lifestyle     = lifestyle;
        this.sleepTime     = sleepTime;
        this.wakeTime      = wakeTime;
        this.workingHours  = workingHours;
    }

    // BMI
    public double calculateBMI() {
        return weight / (height * height);
    }

    // BMI Category
    public String getCategory() {
        double bmi = calculateBMI();
        if (bmi < 18.5) return "Underweight";
        else if (bmi < 25) return "Normal";
        else if (bmi < 30) return "Overweight";
        else return "Obese";
    }

    // Goal from BMI
    public String getGoal() {
        double bmi = calculateBMI();
        if (bmi < 18.5) return "Weight Gain";
        else if (bmi < 25) return "Maintain";
        else return "Weight Loss";
    }

    // BMR - Mifflin St Jeor Formula (most accurate)
    public double calculateBMR() {
        if (gender.equalsIgnoreCase("Female")) {
            return (10 * weight) + (6.25 * height * 100) - (5 * age) - 161;
        } else {
            return (10 * weight) + (6.25 * height * 100) - (5 * age) + 5;
        }
    }

    // Activity multiplier from lifestyle
    public double getActivityMultiplier() {
        if (lifestyle == null) return 1.375;
        switch (lifestyle.toLowerCase()) {
            case "sedentary":  return 1.2;
            case "moderate":   return 1.375;
            case "active":     return 1.55;
            case "veryactive": return 1.725;
            default:           return 1.375;
        }
    }

    // TDEE = BMR x Activity
    public double calculateTDEE() {
        return calculateBMR() * getActivityMultiplier();
    }

    // Body Fat % - Deurenberg Formula
    public double calculateBodyFat() {
        double bmi = calculateBMI();
        if (gender.equalsIgnoreCase("Female")) {
            return (1.20 * bmi) + (0.23 * age) - 5.4;
        } else {
            return (1.20 * bmi) + (0.23 * age) - 16.2;
        }
    }

    // Ideal Weight - Devine Formula
    public double calculateIdealWeight() {
        double heightCm = height * 100;
        if (gender.equalsIgnoreCase("Female")) {
            return 45.5 + 2.3 * ((heightCm - 152.4) / 2.54);
        } else {
            return 50.0 + 2.3 * ((heightCm - 152.4) / 2.54);
        }
    }

    // Lean Body Mass
    public double calculateLeanBodyMass() {
        double fatFraction = calculateBodyFat() / 100.0;
        return weight * (1 - fatFraction);
    }

    // Health helpers
    public boolean hasIronDeficiency() {
        return healthIssue != null && healthIssue.toLowerCase().contains("iron");
    }
    public boolean hasPCOS() {
        return healthIssue != null && healthIssue.toLowerCase().contains("pcos");
    }
    public boolean hasDiabetes() {
        return healthIssue != null && healthIssue.toLowerCase().contains("diabetes");
    }
    public boolean hasThyroid() {
        return healthIssue != null && healthIssue.toLowerCase().contains("thyroid");
    }
    public boolean hasHypertension() {
        return healthIssue != null && healthIssue.toLowerCase().contains("hypertension");
    }
    public boolean isVegetarian() {
        return foodPreference != null &&
               (foodPreference.equalsIgnoreCase("Veg") ||
                foodPreference.equalsIgnoreCase("Vegan"));
    }

    // Display user profile
    public void displayUser() {
        System.out.println("\n===== USER PROFILE =====");
        System.out.println("Age           : " + age);
        System.out.println("Height        : " + height + " m");
        System.out.println("Weight        : " + weight + " kg");
        System.out.println("Gender        : " + gender);
        System.out.println("Occupation    : " + occupation);
        System.out.println("Region        : " + region);
        System.out.println("Budget        : " + budget);
        System.out.println("Food Pref     : " + foodPreference);
        System.out.println("Lifestyle     : " + lifestyle);
        System.out.println("Workout Time  : " + workoutTime);
        System.out.println("Sleep Time    : " + sleepTime);
        System.out.println("Wake Time     : " + wakeTime);
        System.out.println("Working Hours : " + workingHours + " hrs/day");
        System.out.println("Health Issue  : " + healthIssue);
    }

    // Getters
    public int    getAge()            { return age; }
    public double getHeight()         { return height; }
    public double getWeight()         { return weight; }
    public String getGender()         { return gender; }
    public String getRegion()         { return region; }
    public String getBudget()         { return budget; }
    public String getWorkoutTime()    { return workoutTime; }
    public String getHealthIssue()    { return healthIssue; }
    public String getOccupation()     { return occupation; }
    public String getFoodPreference() { return foodPreference; }
    public String getLifestyle()      { return lifestyle; }
    public String getSleepTime()      { return sleepTime; }
    public String getWakeTime()       { return wakeTime; }
    public String getWorkingHours()   { return workingHours; }
    public String getGoalValue()      { return getGoal(); }
}