// ======================================================
// WorkoutFilter.java
// Yeh FoodFilter.java jaisa hai - exercises filter karta hai
// DSA Concept: ArrayList filtering - for loop + conditions
// ======================================================

import java.util.ArrayList;

public class WorkoutFilter {

    // ── Method: exercises filter karo ─────────────────
    // Parameters: saari exercises, user ka level, type, goal
    public static ArrayList<Exercise> filterExercises(
            ArrayList<Exercise> allExercises,
            String level,
            String type,
            String goal) {

        // Filtered exercises store karne ke liye
        ArrayList<Exercise> filtered = new ArrayList<>();

        // ── Har exercise check karo (for-each loop) ───
        for (Exercise ex : allExercises) {

            // Level match check
            // equalsIgnoreCase - capital small ka fark nahi
            boolean levelMatch =
                ex.getLevel().equalsIgnoreCase(level);

            // Type match - Workout ya Yoga
            boolean typeMatch =
                ex.getType().equalsIgnoreCase(type);

            // Goal match - "All" waale sabke liye hain
            boolean goalMatch =
                ex.getGoal().equalsIgnoreCase(goal)
                || ex.getGoal().equalsIgnoreCase("All");

            // Teeno match hon tabhi add karo
            if (levelMatch && typeMatch && goalMatch) {
                filtered.add(ex);
            }
        }

        return filtered;
    }

    // ── Method: specific body part ki exercises ────────
    public static ArrayList<Exercise> filterByBodyPart(
            ArrayList<Exercise> exercises,
            String bodyPart) {

        ArrayList<Exercise> result = new ArrayList<>();

        for (Exercise ex : exercises) {
            if (ex.getBodyPart().equalsIgnoreCase(bodyPart)) {
                result.add(ex);
            }
        }

        return result;
    }
}
