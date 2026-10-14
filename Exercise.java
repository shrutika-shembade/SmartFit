// ======================================================
// Exercise.java
// Yeh ek single exercise ka blueprint hai (OOP - Class)
// Jaise Meal.java diet ke liye tha, Exercise.java workout ke liye hai
// ======================================================

public class Exercise {

    // ── Fields (exercise ki details) ──────────────────
    private String name;         // Exercise ka naam
    private String bodyPart;     // Kaun sa body part train hoga
    private String level;        // Beginner / Intermediate / Advanced
    private String type;         // Workout ya Yoga
    private String goal;         // Weight Loss / Weight Gain / Maintain
    private int sets;            // Kitne sets
    private int reps;            // Ek set mein kitne reps
    private int restSeconds;     // Sets ke beech rest (seconds)
    private String howToDo;      // Exercise kaise karein - instructions
    private String tip;          // Extra tip for better results

    // ── Constructor (OOP concept - object banana) ─────
    public Exercise(
            String name,
            String bodyPart,
            String level,
            String type,
            String goal,
            int sets,
            int reps,
            int restSeconds,
            String howToDo,
            String tip) {

        this.name        = name;
        this.bodyPart    = bodyPart;
        this.level       = level;
        this.type        = type;
        this.goal        = goal;
        this.sets        = sets;
        this.reps        = reps;
        this.restSeconds = restSeconds;
        this.howToDo     = howToDo;
        this.tip         = tip;
    }

    // ── Getters (Encapsulation - private data access) ─
    public String getName()        { return name; }
    public String getBodyPart()    { return bodyPart; }
    public String getLevel()       { return level; }
    public String getType()        { return type; }
    public String getGoal()        { return goal; }
    public int getSets()           { return sets; }
    public int getReps()           { return reps; }
    public int getRestSeconds()    { return restSeconds; }
    public String getHowToDo()     { return howToDo; }
    public String getTip()         { return tip; }
}