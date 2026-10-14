// ======================================================
// WorkoutDatabase.java
// Yeh saari exercises ka collection hai
// FoodDatabase.java jaisa hi - ArrayList mein data store karna
// DSA Concept: ArrayList use ho raha hai data store karne ke liye
// ======================================================

import java.util.ArrayList;

public class WorkoutDatabase {

    // ── Static method jo saari exercises return karta hai ──
    // Static matlab - object banaye bina call kar sakte hain
    public static ArrayList<Exercise> getExercises() {

        ArrayList<Exercise> exercises = new ArrayList<>();

        // ══════════════════════════════════════════════
        // CHEST EXERCISES
        // ══════════════════════════════════════════════

        // Beginner Chest - Workout
        exercises.add(new Exercise(
            "Push Ups",
            "Chest",
            "Beginner",
            "Workout",
            "All",
            3, 10, 30,
            "1. Zameen par ulta let jao\n" +
            "2. Haath shoulder ke barabar rakh\n" +
            "3. Body seedhi rakho\n" +
            "4. Aahista neeche jao, phir upar aao\n" +
            "5. Saansi andar lo jate waqt, bahar nikalo aate waqt",
            "Agar mushkil lage to ghutno par push up karo pehle"));

        exercises.add(new Exercise(
            "Wide Push Ups",
            "Chest",
            "Beginner",
            "Workout",
            "All",
            3, 8, 30,
            "1. Push up position mein aao\n" +
            "2. Haath thoda zyada wide rakh\n" +
            "3. Chest ko floor ke paas le jao\n" +
            "4. Slowly upar aao",
            "Wide grip se chest ke outer part train hota hai"));

        exercises.add(new Exercise(
            "Wall Push Ups",
            "Chest",
            "Beginner",
            "Workout",
            "Weight Loss",
            3, 12, 20,
            "1. Deewar ke samne khade ho\n" +
            "2. Haath deewar par rakh\n" +
            "3. Body aage jhukao aur wapis aao\n" +
            "4. Saansi properly lo",
            "Beginners ke liye sabse aasaan chest exercise"));

        // Intermediate Chest - Workout
        exercises.add(new Exercise(
            "Diamond Push Ups",
            "Chest",
            "Intermediate",
            "Workout",
            "All",
            4, 12, 45,
            "1. Push up position mein aao\n" +
            "2. Haath ko paas rakh - diamond shape banao\n" +
            "3. Chest neeche lao\n" +
            "4. Upar push karo",
            "Triceps aur chest dono train hote hain"));

        exercises.add(new Exercise(
            "Decline Push Ups",
            "Chest",
            "Intermediate",
            "Workout",
            "Weight Gain",
            4, 10, 45,
            "1. Paon kisi height par rakh (kursi ya bed)\n" +
            "2. Haath zameen par rakh\n" +
            "3. Upper chest ke liye neeche jao\n" +
            "4. Slowly upar aao",
            "Upper chest ke liye best exercise hai"));

        exercises.add(new Exercise(
            "Archer Push Ups",
            "Chest",
            "Intermediate",
            "Workout",
            "All",
            3, 8, 60,
            "1. Wide push up position mein aao\n" +
            "2. Ek taraf jhuko aur ek haath seedha rakho\n" +
            "3. Wapis aao aur doosri taraf karo\n" +
            "4. Alternate karo",
            "Ek taraf ka chest zyada train hota hai"));

        // Advanced Chest - Workout
        exercises.add(new Exercise(
            "Clap Push Ups",
            "Chest",
            "Advanced",
            "Workout",
            "Weight Gain",
            4, 8, 60,
            "1. Normal push up karo\n" +
            "2. Upar aate waqt itni force lagao ki\n" +
            "   haath zameen se uthe\n" +
            "3. Hawa mein taali bajao\n" +
            "4. Safely land karo",
            "Explosive power ke liye best - safety ka dhyan rakho"));

        exercises.add(new Exercise(
            "Pike Push Ups",
            "Chest",
            "Advanced",
            "Workout",
            "All",
            4, 10, 60,
            "1. Inverted V position mein aao\n" +
            "2. Sar ko zameen ki taraf le jao\n" +
            "3. Slowly upar push karo\n" +
            "4. Kamar seedhi rakho",
            "Shoulder aur chest dono ke liye excellent"));

        // ══════════════════════════════════════════════
        // SHOULDER EXERCISES
        // ══════════════════════════════════════════════

        exercises.add(new Exercise(
            "Arm Circles",
            "Shoulder",
            "Beginner",
            "Workout",
            "All",
            3, 20, 20,
            "1. Seedhe khade ho\n" +
            "2. Dono haath side mein phailao\n" +
            "3. Chote circles mein ghhumao - forward\n" +
            "4. Phir backward circles karo",
            "Shoulder warm up ke liye best exercise"));

        exercises.add(new Exercise(
            "Shoulder Taps",
            "Shoulder",
            "Beginner",
            "Workout",
            "Weight Loss",
            3, 16, 30,
            "1. Push up position mein aao\n" +
            "2. Ek haath se doosre shoulder ko tap karo\n" +
            "3. Balance maintain karo\n" +
            "4. Dono taraf alternate karo",
            "Core aur shoulder dono train hote hain"));

        exercises.add(new Exercise(
            "Pike Push Ups",
            "Shoulder",
            "Intermediate",
            "Workout",
            "All",
            4, 10, 45,
            "1. Inverted V shape banao\n" +
            "2. Sar neeche le jao\n" +
            "3. Shoulder se push karo upar\n" +
            "4. Position maintain karo",
            "Shoulder press ka home version hai yeh"));

        exercises.add(new Exercise(
            "Handstand Wall Hold",
            "Shoulder",
            "Advanced",
            "Workout",
            "Weight Gain",
            3, 30, 60,
            "1. Deewar ke paas jao\n" +
            "2. Haath zameen par rakh\n" +
            "3. Paon deewar par le jao\n" +
            "4. 30 second hold karo",
            "Pure shoulder strength ke liye - slowly practice karo"));

        exercises.add(new Exercise(
            "Wall Handstand Push Ups",
            "Shoulder",
            "Advanced",
            "Workout",
            "Weight Gain",
            3, 5, 90,
            "1. Handstand position mein jao\n" +
            "2. Aahista sar zameen par le jao\n" +
            "3. Shoulder se push karke upar aao\n" +
            "4. Control maintain karo",
            "Advanced shoulder exercise - pehle hold practice karo"));

        // ══════════════════════════════════════════════
        // ABS / CORE EXERCISES
        // ══════════════════════════════════════════════

        exercises.add(new Exercise(
            "Crunches",
            "Abs",
            "Beginner",
            "Workout",
            "Weight Loss",
            3, 15, 30,
            "1. Zameen par peethe ke bal leto\n" +
            "2. Ghutne mod lo\n" +
            "3. Haath sar ke peeche rakh\n" +
            "4. Shoulders ko upar uthao\n" +
            "5. Slowly neeche aao",
            "Neck par zyada pressure mat do - abs se uthao"));

        exercises.add(new Exercise(
            "Plank",
            "Abs",
            "Beginner",
            "Workout",
            "All",
            3, 30, 30,
            "1. Push up position mein aao\n" +
            "2. Forearm par rest karo\n" +
            "3. Body ek seedhi line mein rakho\n" +
            "4. 30 seconds hold karo\n" +
            "5. Breathe normally karte raho",
            "Kamar neeche mat girane do - body seedhi rakho"));

        exercises.add(new Exercise(
            "Leg Raises",
            "Abs",
            "Beginner",
            "Workout",
            "Weight Loss",
            3, 12, 30,
            "1. Seedha let jao\n" +
            "2. Haath sides mein rakho\n" +
            "3. Dono paon seedhe rakho\n" +
            "4. 90 degree tak upar uthao\n" +
            "5. Slowly neeche lao",
            "Lower abs ke liye best exercise hai yeh"));

        exercises.add(new Exercise(
            "Bicycle Crunches",
            "Abs",
            "Intermediate",
            "Workout",
            "Weight Loss",
            3, 20, 30,
            "1. Zameen par let jao\n" +
            "2. Haath sar ke peeche\n" +
            "3. Ek ghutna chest ki taraf lao\n" +
            "4. Doosri taraf ki elbow se touch karo\n" +
            "5. Cycle chalane jaisa motion karo",
            "Slow karo - fast karne se benefit kam hota hai"));

        exercises.add(new Exercise(
            "Mountain Climbers",
            "Abs",
            "Intermediate",
            "Workout",
            "Weight Loss",
            4, 30, 30,
            "1. Push up position mein aao\n" +
            "2. Ek ghutna chest ki taraf lao quickly\n" +
            "3. Wapis lo aur doosra ghutna lao\n" +
            "4. Running motion jaisi speed karo",
            "Cardio + abs dono ek saath - best fat burner"));

        exercises.add(new Exercise(
            "Russian Twist",
            "Abs",
            "Intermediate",
            "Workout",
            "Weight Loss",
            3, 20, 30,
            "1. Zameen par baithke ghutne mod lo\n" +
            "2. Thoda peeche jhuko\n" +
            "3. Haath jod lo\n" +
            "4. Left right twist karo\n" +
            "5. Paon zameen se utha sakte ho (advanced)",
            "Obliques (side abs) ke liye perfect exercise"));

        exercises.add(new Exercise(
            "V-Ups",
            "Abs",
            "Advanced",
            "Workout",
            "All",
            4, 12, 45,
            "1. Seedha let jao - haath upar\n" +
            "2. Ek saath haath aur paon upar uthao\n" +
            "3. V shape banao\n" +
            "4. Slowly wapis zameen par aao",
            "Full core exercise - ek saath upper lower abs"));

        exercises.add(new Exercise(
            "Dragon Flag",
            "Abs",
            "Advanced",
            "Workout",
            "Weight Gain",
            3, 8, 60,
            "1. Bench ya zameen par peethe ke bal\n" +
            "2. Haath koi solid cheez pakdo\n" +
            "3. Poora body seedha rakho\n" +
            "4. Slowly neeche lao - zameen se upar",
            "Bruce Lee wali exercise - bahut advanced hai"));

        // ══════════════════════════════════════════════
        // LEG EXERCISES
        // ══════════════════════════════════════════════

        exercises.add(new Exercise(
            "Squats",
            "Legs",
            "Beginner",
            "Workout",
            "All",
            3, 15, 30,
            "1. Seedhe khade ho - paon shoulder width\n" +
            "2. Haath aage ya seene par rakho\n" +
            "3. Ghutne modte hue neeche baitho\n" +
            "4. Jaisa kursi par baithna ho\n" +
            "5. Heel zameen par rakho - wapis khade ho",
            "Ghutne paon ki ungliyon se aage mat nikalne do"));

        exercises.add(new Exercise(
            "Lunges",
            "Legs",
            "Beginner",
            "Workout",
            "All",
            3, 12, 30,
            "1. Seedhe khade ho\n" +
            "2. Ek paon aage rakho\n" +
            "3. Dono ghutne 90 degree mod lo\n" +
            "4. Wapis khade ho\n" +
            "5. Doosra paon aage rakho",
            "Balance ke liye ek jagah dekh ke karo"));

        exercises.add(new Exercise(
            "Calf Raises",
            "Legs",
            "Beginner",
            "Workout",
            "All",
            3, 20, 20,
            "1. Seedhe khade ho\n" +
            "2. Ek haath deewar par balance ke liye\n" +
            "3. Paon ki ungliyon par uthao\n" +
            "4. Slowly neeche aao",
            "Paon ki muscles ke liye bahut acha"));

        exercises.add(new Exercise(
            "Jump Squats",
            "Legs",
            "Intermediate",
            "Workout",
            "Weight Loss",
            4, 15, 45,
            "1. Normal squat position\n" +
            "2. Neeche jao\n" +
            "3. Upar aate waqt jump karo\n" +
            "4. Softly land karo - ghutne thode mod ke\n" +
            "5. Directly next squat mein jao",
            "Cardio + leg strength - knees ka dhyan rakho"));

        exercises.add(new Exercise(
            "Sumo Squats",
            "Legs",
            "Intermediate",
            "Workout",
            "All",
            3, 15, 30,
            "1. Paon wide rakh - wider than shoulder\n" +
            "2. Paon bahar ki taraf point karo\n" +
            "3. Deep squat mein jao\n" +
            "4. Wapis khade ho",
            "Inner thighs ke liye best exercise"));

        exercises.add(new Exercise(
            "Bulgarian Split Squats",
            "Legs",
            "Advanced",
            "Workout",
            "Weight Gain",
            4, 10, 60,
            "1. Peeche kursi ya bed rakho\n" +
            "2. Ek paon peeche kursi par\n" +
            "3. Aage wale paon par squat karo\n" +
            "4. Slowly neeche jao\n" +
            "5. Wapis upar aao",
            "Single leg strength ke liye best - balance practice karo"));

        exercises.add(new Exercise(
            "Pistol Squats",
            "Legs",
            "Advanced",
            "Workout",
            "Weight Gain",
            3, 6, 90,
            "1. Ek paon par khade ho\n" +
            "2. Doosra paon aage seedha rakho\n" +
            "3. Ek paon par neeche baithho\n" +
            "4. Wapis khade ho\n" +
            "5. Haath balance ke liye aage rakho",
            "Bahut advanced - pehle wall ka support lo"));

        // ══════════════════════════════════════════════
        // THIGH / GLUTE TONING
        // ══════════════════════════════════════════════

        exercises.add(new Exercise(
            "Glute Bridge",
            "Thighs",
            "Beginner",
            "Workout",
            "All",
            3, 15, 30,
            "1. Peethe ke bal let jao\n" +
            "2. Ghutne modte hue paon zameen par\n" +
            "3. Kamar ko upar uthao\n" +
            "4. Glutes squeeze karo upar\n" +
            "5. Slowly neeche aao",
            "Glutes aur inner thighs ke liye perfect"));

        exercises.add(new Exercise(
            "Side Leg Raises",
            "Thighs",
            "Beginner",
            "Workout",
            "Weight Loss",
            3, 15, 20,
            "1. Karayt par let jao ek taraf\n" +
            "2. Ek haath sir ke neeche\n" +
            "3. Upar wala paon uthao\n" +
            "4. Slowly neeche lao\n" +
            "5. Dono taraf karo",
            "Outer thighs aur hips toning ke liye best"));

        exercises.add(new Exercise(
            "Donkey Kicks",
            "Thighs",
            "Beginner",
            "Workout",
            "Weight Loss",
            3, 15, 20,
            "1. Charo haath paon par aa jao\n" +
            "2. Ek paon peeche upar uthao - kick karo\n" +
            "3. Glutes squeeze karo\n" +
            "4. Wapis aao\n" +
            "5. Dono taraf repeat",
            "Glutes toning ke liye number one exercise"));

        exercises.add(new Exercise(
            "Inner Thigh Squeeze",
            "Thighs",
            "Beginner",
            "Workout",
            "All",
            3, 20, 20,
            "1. Peethe ke bal let jao\n" +
            "2. Ghutne modte hue paon zameen par\n" +
            "3. Dono ghutno ke beech takiya rakh\n" +
            "4. Squeeze karo 5 second\n" +
            "5. Release karo",
            "Inner thighs ke liye simple par effective"));

        exercises.add(new Exercise(
            "Fire Hydrants",
            "Thighs",
            "Intermediate",
            "Workout",
            "Weight Loss",
            3, 15, 30,
            "1. Charo haath paon par\n" +
            "2. Ek paon side mein uthao - 90 degree\n" +
            "3. Kutta hydrant pe jaisa motion\n" +
            "4. Slowly neeche lao\n" +
            "5. Dono taraf karo",
            "Outer glutes aur hips ke liye best"));

        exercises.add(new Exercise(
            "Curtsy Lunges",
            "Thighs",
            "Intermediate",
            "Workout",
            "Weight Loss",
            3, 12, 30,
            "1. Seedhe khade ho\n" +
            "2. Ek paon doosre ke peeche cross karo\n" +
            "3. Lunge mein jao - namaskar jaisa\n" +
            "4. Wapis aao\n" +
            "5. Alternate karo",
            "Glutes aur thighs toning ke liye unique exercise"));

        // ══════════════════════════════════════════════
        // ARM TONING EXERCISES
        // ══════════════════════════════════════════════

        exercises.add(new Exercise(
            "Tricep Dips",
            "Arms",
            "Beginner",
            "Workout",
            "All",
            3, 12, 30,
            "1. Kursi ya bed ke kinare baitho\n" +
            "2. Haath kursi par rakho\n" +
            "3. Body aage le jao\n" +
            "4. Kohniya modte hue neeche jao\n" +
            "5. Wapis push karke upar aao",
            "Back of arms (triceps) ke liye best home exercise"));

        exercises.add(new Exercise(
            "Arm Rotations",
            "Arms",
            "Beginner",
            "Workout",
            "Weight Loss",
            3, 30, 20,
            "1. Seedhe khade ho\n" +
            "2. Dono haath side mein phailao\n" +
            "3. Chote circles - forward 15\n" +
            "4. Backward circles - 15\n" +
            "5. Speed gradually badhao",
            "Arm toning aur shoulder warm up dono"));

        exercises.add(new Exercise(
            "Diamond Push Ups",
            "Arms",
            "Intermediate",
            "Workout",
            "All",
            4, 10, 45,
            "1. Push up position\n" +
            "2. Haath ko andar rakho - diamond shape\n" +
            "3. Triceps feel karein neeche jaate waqt\n" +
            "4. Slowly upar aao",
            "Triceps ke liye push up ka best variation"));

        exercises.add(new Exercise(
            "Plank to Push Up",
            "Arms",
            "Intermediate",
            "Workout",
            "All",
            3, 10, 45,
            "1. Forearm plank mein aao\n" +
            "2. Ek haath seedha karo\n" +
            "3. Doosra haath seedha karo - push up position\n" +
            "4. Wapis forearm par aao\n" +
            "5. Alternate arms se",
            "Arms + core dono ek saath train hote hain"));

        exercises.add(new Exercise(
            "Tricep Kickbacks",
            "Arms",
            "Intermediate",
            "Workout",
            "Weight Loss",
            3, 15, 30,
            "1. Thoda aage jhuko\n" +
            "2. Ek haath se kisi cheez ka support lo\n" +
            "3. Doosra haath 90 degree\n" +
            "4. Arm ko peeche seedha karo\n" +
            "5. Slowly wapis lao",
            "Triceps isolation ke liye - form pe dhyan do"));

        // ══════════════════════════════════════════════
        // BACK EXERCISES
        // ══════════════════════════════════════════════

        exercises.add(new Exercise(
            "Superman",
            "Back",
            "Beginner",
            "Workout",
            "All",
            3, 12, 30,
            "1. Pet ke bal let jao\n" +
            "2. Haath aage ki taraf\n" +
            "3. Haath aur paon dono ek saath upar uthao\n" +
            "4. 2 second hold karo\n" +
            "5. Slowly neeche aao",
            "Lower back ke liye best - spine health improve hoti hai"));

        exercises.add(new Exercise(
            "Cat Cow Stretch",
            "Back",
            "Beginner",
            "Workout",
            "All",
            3, 15, 20,
            "1. Charo haath paon par aao\n" +
            "2. Saansi lete waqt kamar neeche karo (Cow)\n" +
            "3. Saansi bahar karte waqt kamar upar karo (Cat)\n" +
            "4. Dheerey dheerey karo\n" +
            "5. Neck bhi move karo gently",
            "Back pain ke liye excellent - subah karo"));

        exercises.add(new Exercise(
            "Reverse Snow Angels",
            "Back",
            "Beginner",
            "Workout",
            "All",
            3, 12, 30,
            "1. Pet ke bal let jao\n" +
            "2. Haath sides mein rakho\n" +
            "3. Haath upar le jao - jaise snow angel\n" +
            "4. Slowly wapis aao\n" +
            "5. Body zameen se uthao thoda",
            "Upper back aur shoulder dono ke liye achha"));

        exercises.add(new Exercise(
            "Pull Up Negative",
            "Back",
            "Intermediate",
            "Workout",
            "Weight Gain",
            3, 6, 60,
            "1. Pull up bar pakdo - kisi bhi tarah\n" +
            "2. Jump karke top position mein jao\n" +
            "3. Bahut slowly neeche aao - 5 seconds\n" +
            "4. Yahi ek rep hai\n" +
            "5. Repeat karo",
            "Pull up seekhne ka best tarika - negative reps"));

        // ══════════════════════════════════════════════
        // FULL BODY / CARDIO EXERCISES
        // ══════════════════════════════════════════════

        exercises.add(new Exercise(
            "Jumping Jacks",
            "Full Body",
            "Beginner",
            "Workout",
            "Weight Loss",
            3, 30, 30,
            "1. Seedhe khade ho\n" +
            "2. Jump karo aur paon wide karo\n" +
            "3. Saath mein haath upar uthao\n" +
            "4. Wapis jump karo - paon paas\n" +
            "5. Haath neeche aao",
            "Warm up ke liye perfect - cardio bhi hoti hai"));

        exercises.add(new Exercise(
            "Burpees",
            "Full Body",
            "Intermediate",
            "Workout",
            "Weight Loss",
            3, 10, 60,
            "1. Seedhe khade ho\n" +
            "2. Neeche baitho - haath zameen par\n" +
            "3. Paon peeche le jao - push up position\n" +
            "4. Push up karo (optional)\n" +
            "5. Paon wapis lao - khade ho - jump karo",
            "Sabse effective fat burning exercise - tough but results best"));

        exercises.add(new Exercise(
            "High Knees",
            "Full Body",
            "Beginner",
            "Workout",
            "Weight Loss",
            3, 30, 30,
            "1. Seedhe khade ho\n" +
            "2. Ek ghutna chest tak uthao\n" +
            "3. Jaldi se doosra ghutna uthao\n" +
            "4. Running in place jaisa\n" +
            "5. Arms bhi move karo",
            "Heart rate badhane ke liye best cardio"));

        exercises.add(new Exercise(
            "Box Steps",
            "Full Body",
            "Beginner",
            "Workout",
            "All",
            3, 20, 30,
            "1. Kursi ya step ke samne khade ho\n" +
            "2. Ek paon upar rakho\n" +
            "3. Doosra paon upar rakho\n" +
            "4. Pehla paon neeche\n" +
            "5. Doosra paon neeche - repeat",
            "Beginners ke liye cardio - joints pe kam pressure"));

        exercises.add(new Exercise(
            "Skaters",
            "Full Body",
            "Intermediate",
            "Workout",
            "Weight Loss",
            3, 20, 30,
            "1. Ek paon par khade ho\n" +
            "2. Ice skater jaisa side mein jump karo\n" +
            "3. Doosre paon par land karo\n" +
            "4. Back paon ko peeche swing karo\n" +
            "5. Jaldi jaldi alternate karo",
            "Legs + cardio + balance - bahut effective"));

        // ══════════════════════════════════════════════
        // YOGA - BEGINNER
        // ══════════════════════════════════════════════

        exercises.add(new Exercise(
            "Tadasana (Mountain Pose)",
            "Full Body",
            "Beginner",
            "Yoga",
            "All",
            1, 60, 10,
            "1. Seedhe khade ho - paon paas paas\n" +
            "2. Haath sides mein rakho\n" +
            "3. Poora body seedha karo\n" +
            "4. Aankhein bandh karo\n" +
            "5. Saansi par dhyan do - 60 second hold",
            "Posture improve hota hai - subah ka best asana"));

        exercises.add(new Exercise(
            "Balasana (Child Pose)",
            "Back",
            "Beginner",
            "Yoga",
            "All",
            1, 60, 10,
            "1. Ghutno par baitho\n" +
            "2. Aage jhuko - haath seedhe aage\n" +
            "3. Maatha zameen par rakho\n" +
            "4. Aram se saansi lo\n" +
            "5. 60 second ya zyada raho",
            "Stress relief ke liye best yoga pose - back relaxation"));

        exercises.add(new Exercise(
            "Sukhasana (Easy Pose)",
            "Full Body",
            "Beginner",
            "Yoga",
            "All",
            1, 120, 0,
            "1. Zameen par sukh se baitho\n" +
            "2. Paon cross karo\n" +
            "3. Kamar seedhi rakho\n" +
            "4. Haath ghutno par\n" +
            "5. Aankhein bandh - deep breathing karo",
            "Meditation ke liye perfect - mind calm hota hai"));

        exercises.add(new Exercise(
            "Vrikshasana (Tree Pose)",
            "Legs",
            "Beginner",
            "Yoga",
            "All",
            2, 30, 10,
            "1. Seedhe khade ho\n" +
            "2. Ek paon doosre ke ghutne par rakho\n" +
            "3. Haath namaste mein chest par\n" +
            "4. Ya haath upar le jao\n" +
            "5. 30 second balance karo - dono taraf",
            "Balance aur concentration improve hoti hai"));

        exercises.add(new Exercise(
            "Marjaryasana (Cat Pose)",
            "Back",
            "Beginner",
            "Yoga",
            "All",
            3, 10, 15,
            "1. Charo haath paon par aao\n" +
            "2. Saansi bahar karte waqt kamar upar karo\n" +
            "3. Sar neeche - chin chest par\n" +
            "4. Saansi lete waqt kamar neeche karo\n" +
            "5. Sar upar - yeh cow pose hai",
            "Spine flexibility ke liye - back pain mein relief"));

        exercises.add(new Exercise(
            "Adho Mukha Svanasana (Downward Dog)",
            "Full Body",
            "Beginner",
            "Yoga",
            "All",
            3, 30, 15,
            "1. Charo haath paon par aao\n" +
            "2. Kamar upar uthao - V shape banao\n" +
            "3. Haath seedhe - heels zameen par\n" +
            "4. Sar haathon ke beech\n" +
            "5. 30 second hold - deep breathing",
            "Poora body stretch hota hai - energy badhti hai"));

        exercises.add(new Exercise(
            "Virabhadrasana (Warrior 1)",
            "Legs",
            "Beginner",
            "Yoga",
            "All",
            2, 30, 15,
            "1. Ek paon aage rakho - bada qadam\n" +
            "2. Peeche wala paon seedha\n" +
            "3. Ghutna aage wala 90 degree\n" +
            "4. Haath upar le jao\n" +
            "5. 30 second hold - dono taraf",
            "Leg strength + balance + focus - complete asana"));

        // ══════════════════════════════════════════════
        // YOGA - INTERMEDIATE
        // ══════════════════════════════════════════════

        exercises.add(new Exercise(
            "Naukasana (Boat Pose)",
            "Abs",
            "Intermediate",
            "Yoga",
            "Weight Loss",
            3, 30, 20,
            "1. Zameen par baitho\n" +
            "2. Paon upar uthao - seedhe rakho\n" +
            "3. Haath aage seedhe rakho\n" +
            "4. V shape banao body se\n" +
            "5. 30 second hold - balance karo",
            "Core strength ke liye best yoga - abs feel karein"));

        exercises.add(new Exercise(
            "Trikonasana (Triangle Pose)",
            "Full Body",
            "Intermediate",
            "Yoga",
            "All",
            2, 30, 15,
            "1. Paon wide rakh\n" +
            "2. Ek taraf haath neeche - paon tak\n" +
            "3. Doosra haath upar - ceiling ki taraf\n" +
            "4. Dono haath ek seedhi line mein\n" +
            "5. 30 second - dono taraf",
            "Hamstrings + side stretch - posture aur flexibility"));

        exercises.add(new Exercise(
            "Setu Bandhasana (Bridge Pose)",
            "Thighs",
            "Intermediate",
            "Yoga",
            "All",
            3, 30, 20,
            "1. Peethe ke bal let jao\n" +
            "2. Ghutne mod lo - paon zameen par\n" +
            "3. Kamar uthao - upar ki taraf\n" +
            "4. Haath zameen par ya interlace karo\n" +
            "5. 30 second hold - glutes squeeze",
            "Glutes + back + thyroid ke liye - very beneficial"));

        exercises.add(new Exercise(
            "Bhujangasana (Cobra Pose)",
            "Back",
            "Intermediate",
            "Yoga",
            "All",
            3, 20, 15,
            "1. Pet ke bal let jao\n" +
            "2. Haath shoulders ke neeche\n" +
            "3. Sar aur chest upar uthao\n" +
            "4. Kamar se upar ka hissa uthao\n" +
            "5. 20 second hold - back feel karein",
            "Back strength + flexibility - spine health excellent"));

        exercises.add(new Exercise(
            "Ardha Matsyendrasana (Seated Twist)",
            "Back",
            "Intermediate",
            "Yoga",
            "All",
            2, 30, 10,
            "1. Zameen par seedhe baitho\n" +
            "2. Ek paon doosre ke ghutne ke bahar\n" +
            "3. Kamar twist karo us taraf\n" +
            "4. Ek haath peeche zameen par\n" +
            "5. 30 second - dono taraf",
            "Spine twist - digestion improve hoti hai"));

        // ══════════════════════════════════════════════
        // YOGA - ADVANCED
        // ══════════════════════════════════════════════

        exercises.add(new Exercise(
            "Sirsasana (Headstand)",
            "Full Body",
            "Advanced",
            "Yoga",
            "Weight Gain",
            1, 60, 0,
            "1. Pehle wall ke paas practice karo\n" +
            "2. Haath triangle banao zameen par\n" +
            "3. Sar haathon ke beech\n" +
            "4. Ghutne chest par\n" +
            "5. Slowly paon upar - wall ka support",
            "Brain ke liye best - blood flow improves - beginner wall se seekhe"));

        exercises.add(new Exercise(
            "Chakrasana (Wheel Pose)",
            "Full Body",
            "Advanced",
            "Yoga",
            "All",
            3, 20, 30,
            "1. Peethe ke bal let jao\n" +
            "2. Haath shoulders ke paas zameen par\n" +
            "3. Paon ghutne ke paas\n" +
            "4. Ek saath push karo - upar bridge banao\n" +
            "5. 20 second hold",
            "Full spine bend - bahut flexibility chahiye - warm up zaroor karo"));

        exercises.add(new Exercise(
            "Mayurasana (Peacock Pose)",
            "Full Body",
            "Advanced",
            "Yoga",
            "Weight Gain",
            3, 10, 60,
            "1. Ghutno par baitho\n" +
            "2. Haath zameen par - anguliyaan peeche\n" +
            "3. Kohniyaan pet par\n" +
            "4. Aage jhuko - balance karo\n" +
            "5. Paon upar uthao - hold",
            "Core + arm strength - concentration bahut chahiye"));

        // ══════════════════════════════════════════════
        // BREATHING EXERCISES (PRANAYAMA)
        // ══════════════════════════════════════════════

        exercises.add(new Exercise(
            "Anulom Vilom (Alternate Nostril)",
            "Full Body",
            "Beginner",
            "Yoga",
            "All",
            1, 120, 0,
            "1. Sukhasana mein baitho\n" +
            "2. Daaya haath - angutha aur ring finger use karo\n" +
            "3. Ek nostril bandh - doosre se saansi lo\n" +
            "4. Doosra nostril bandh - pehle se bahar nikalo\n" +
            "5. 2 minute karo - din mein 2 baar",
            "Stress, anxiety, blood pressure ke liye - subah khaali pet best"));

        exercises.add(new Exercise(
            "Kapalbhati Pranayama",
            "Abs",
            "Beginner",
            "Yoga",
            "Weight Loss",
            1, 60, 10,
            "1. Seedhe baitho\n" +
            "2. Naak se tezi se saansi bahar phenko\n" +
            "3. Pet andar karo jab bahar phenko\n" +
            "4. Saansi andar automatically aayegi\n" +
            "5. 1 minute - phir rest",
            "Belly fat kam karne mein help - acidity mein na karo"));

        exercises.add(new Exercise(
            "Bhramari (Humming Bee Breath)",
            "Full Body",
            "Beginner",
            "Yoga",
            "All",
            5, 1, 5,
            "1. Aankhein bandh karo\n" +
            "2. Haath - anguthe se kaano ko band karo\n" +
            "3. Ungliyan aankhon par halki rakho\n" +
            "4. Saansi lo\n" +
            "5. Bahar nikaalte waqt humming sound karo - 'mmm'",
            "Headache, anxiety, insomnia ke liye best - raat ko bhi kar sakte ho"));

        // ══════════════════════════════════════════════
        // STRETCHING / COOLDOWN
        // ══════════════════════════════════════════════

        exercises.add(new Exercise(
            "Hamstring Stretch",
            "Legs",
            "Beginner",
            "Workout",
            "All",
            2, 30, 10,
            "1. Zameen par seedhe baitho\n" +
            "2. Paon seedhe aage rakho\n" +
            "3. Haath se paon pakad ne ki koshish karo\n" +
            "4. Ghutne seedhe rakho\n" +
            "5. 30 second hold - dono taraf",
            "Workout ke baad zaroor karo - injury prevent hoti hai"));

        exercises.add(new Exercise(
            "Quad Stretch",
            "Thighs",
            "Beginner",
            "Workout",
            "All",
            2, 30, 10,
            "1. Ek paon par khade ho\n" +
            "2. Doosra paon peeche pakdo - haath se\n" +
            "3. Ghutna seedha neeche\n" +
            "4. Haath se balance karo\n" +
            "5. 30 second - dono taraf",
            "Quad muscles relax hoti hai - leg day ke baad must"));

        exercises.add(new Exercise(
            "Chest Opener Stretch",
            "Chest",
            "Beginner",
            "Workout",
            "All",
            2, 30, 10,
            "1. Seedhe khade ho\n" +
            "2. Haath peeche le jao - interlock karo\n" +
            "3. Chest ko aage push karo\n" +
            "4. Chin upar\n" +
            "5. 30 second hold",
            "Chest workout ke baad must - muscles relax hoti hain"));

        exercises.add(new Exercise(
            "Neck Stretches",
            "Full Body",
            "Beginner",
            "Workout",
            "All",
            2, 20, 10,
            "1. Seedhe baitho\n" +
            "2. Sar daaye jhukao - 20 second\n" +
            "3. Sar baaye jhukao - 20 second\n" +
            "4. Sar aage jhukao - 20 second\n" +
            "5. Circles slowly - dono direction",
            "Office workers ke liye must - neck pain relief"));

        return exercises;
    }
}