import java.util.ArrayList;

public class FoodDatabase {

    public static ArrayList<Meal> getFoods() {

        ArrayList<Meal> foods = new ArrayList<>();

        // ================= DETOX DRINKS (30) =================

        foods.add(new Meal("Lemon Water","DetoxDrink","1 Glass",20,0,5,0,"Maharashtra","Low",
            "Detoxifies body | Boosts immunity | Zero fat"));
        foods.add(new Meal("Jeera Water","DetoxDrink","1 Glass",15,0,4,0,"Maharashtra","Low",
            "Aids digestion | Reduces bloating | Budget friendly"));
        foods.add(new Meal("Ajwain Water","DetoxDrink","1 Glass",18,0,4,0,"NorthIndia","Low",
            "Relieves acidity | Improves metabolism | Zero fat"));
        foods.add(new Meal("Mint Water","DetoxDrink","1 Glass",10,0,2,0,"All","Low",
            "Cools body | Freshens breath | Zero calories"));
        foods.add(new Meal("Ginger Water","DetoxDrink","1 Glass",12,0,3,0,"All","Low",
            "Anti inflammatory | Boosts metabolism | Budget friendly"));
        foods.add(new Meal("Tulsi Water","DetoxDrink","1 Glass",10,0,2,0,"All","Low",
            "Immunity booster | Fights infection | Zero fat"));
        foods.add(new Meal("Green Tea","DetoxDrink","1 Cup",5,0,1,0,"All","Low",
            "Burns fat | Rich in antioxidants | Boosts metabolism"));
        foods.add(new Meal("Buttermilk","DetoxDrink","1 Glass",40,3,5,1,"All","Low",
            "Aids digestion | Probiotic | Keeps body cool"));
        foods.add(new Meal("Herbal Tea","DetoxDrink","1 Cup",5,0,1,0,"All","Low",
            "Calms mind | Zero fat | Natural detox"));
        foods.add(new Meal("Cinnamon Water","DetoxDrink","1 Glass",10,0,2,0,"All","Low",
            "Controls blood sugar | Anti inflammatory | Budget friendly"));
        foods.add(new Meal("Coconut Water","DetoxDrink","1 Glass",45,1,10,0,"SouthIndia","Medium",
            "Rich in electrolytes | Hydrates body | Natural energy"));
        foods.add(new Meal("Amla Juice","DetoxDrink","1 Glass",35,1,8,0,"All","Medium",
            "Vitamin C rich | Boosts immunity | Good for skin"));
        foods.add(new Meal("Beetroot Juice","DetoxDrink","1 Glass",50,2,12,0,"All","Medium",
            "Iron rich | Boosts stamina | Good for blood"));
        foods.add(new Meal("Spinach Juice","DetoxDrink","1 Glass",30,2,6,0,"All","Medium",
            "High iron | Vitamin C | Best for iron deficiency"));
        foods.add(new Meal("Orange Juice","DetoxDrink","1 Glass",60,1,14,0,"All","Medium",
            "Vitamin C rich | Natural energy | Boosts immunity"));
        foods.add(new Meal("Apple Juice","DetoxDrink","1 Glass",70,0,17,0,"All","Medium",
            "Natural detox | Good for digestion | Rich in antioxidants"));
        foods.add(new Meal("Carrot Juice","DetoxDrink","1 Glass",55,1,13,0,"All","Medium",
            "Good for eyes | Vitamin A rich | Boosts skin health"));
        foods.add(new Meal("Aloe Vera Juice","DetoxDrink","1 Glass",30,0,7,0,"All","Medium",
            "Detoxifies liver | Good for skin | Aids digestion"));
        foods.add(new Meal("Pomegranate Juice","DetoxDrink","1 Glass",80,1,18,0,"All","Medium",
            "Iron rich | Heart healthy | Rich in antioxidants"));
        foods.add(new Meal("Watermelon Juice","DetoxDrink","1 Glass",50,1,12,0,"All","Medium",
            "Hydrates body | Low calorie | Good for summer"));
        foods.add(new Meal("Wheatgrass Juice","DetoxDrink","1 Shot",25,2,4,0,"All","High",
            "Detoxifies blood | High nutrients | Boosts energy"));
        foods.add(new Meal("Celery Juice","DetoxDrink","1 Glass",35,1,7,0,"All","High",
            "Reduces inflammation | Good for gut | Rich in vitamins"));
        foods.add(new Meal("Turmeric Latte","DetoxDrink","1 Cup",80,3,10,3,"All","Medium",
            "Anti inflammatory | Boosts immunity | Good for joints"));
        foods.add(new Meal("Ashwagandha Milk","DetoxDrink","1 Glass",130,5,15,5,"All","High",
            "Reduces stress | Boosts energy | Good for hormones"));
        foods.add(new Meal("Moringa Water","DetoxDrink","1 Glass",20,1,4,0,"All","Medium",
            "Rich in iron | High nutrients | Boosts energy"));
        foods.add(new Meal("Peppermint Tea","DetoxDrink","1 Cup",5,0,1,0,"All","Low",
            "Relieves stress | Aids digestion | Zero calories"));
        foods.add(new Meal("Lemongrass Tea","DetoxDrink","1 Cup",8,0,2,0,"All","Low",
            "Reduces anxiety | Anti bacterial | Zero fat"));
        foods.add(new Meal("Black Coffee","DetoxDrink","1 Cup",5,0,1,0,"All","Low",
            "Boosts metabolism | Improves focus | Zero calories"));
        foods.add(new Meal("Ginger Lemon Tea","DetoxDrink","1 Cup",15,0,3,0,"All","Low",
            "Immunity booster | Anti inflammatory | Budget friendly"));
        foods.add(new Meal("Tamarind Water","DetoxDrink","1 Glass",25,0,6,0,"Maharashtra","Low",
            "Aids digestion | Rich in iron | Budget friendly"));

        // ================= BREAKFAST (30) =================

        foods.add(new Meal("Poha","Breakfast","1 Bowl",250,6,45,4,"Maharashtra","Low",
            "Easy to digest | Budget friendly | Good carbs"));
        foods.add(new Meal("Sprouts","Breakfast","1 Bowl",180,12,25,2,"Maharashtra","Low",
            "High protein | Iron rich | Low fat | Budget friendly"));
        foods.add(new Meal("Fruit Bowl","Breakfast","1 Bowl",160,3,35,1,"All","Low",
            "Natural vitamins | Boosts energy | Light on stomach"));
        foods.add(new Meal("Cornflakes","Breakfast","1 Bowl",200,5,42,2,"All","Low",
            "Quick to prepare | Good carbs | Budget friendly"));
        foods.add(new Meal("Banana","Breakfast","1 Piece",110,1,27,0,"All","Low",
            "Instant energy | Budget friendly | Good pre workout"));
        foods.add(new Meal("Peanut Chikki","Breakfast","2 Pieces",180,5,18,8,"All","Low",
            "High energy | Protein rich | Budget friendly"));
        foods.add(new Meal("Sattu Drink","Breakfast","1 Glass",150,8,25,2,"NorthIndia","Low",
            "High protein | Keeps full longer | Budget friendly"));
        foods.add(new Meal("Boiled Egg","Breakfast","2 Eggs",140,12,1,10,"All","Low",
            "High protein | Low carb | Budget friendly"));
        foods.add(new Meal("Roti Sabji","Breakfast","2 Roti",300,9,48,7,"All","Low",
            "Balanced meal | Good carbs | Budget friendly"));
        foods.add(new Meal("Upma","Breakfast","1 Bowl",220,5,40,5,"SouthIndia","Low",
            "Easy to digest | Budget friendly | Good energy"));
        foods.add(new Meal("Idli","Breakfast","3 Pieces",180,6,35,1,"SouthIndia","Low",
            "Low fat | Easy to digest | Probiotic"));
        foods.add(new Meal("Moong Chilla","Breakfast","2 Pieces",220,12,25,5,"NorthIndia","Low",
            "High protein | Low fat | Budget friendly"));
        foods.add(new Meal("Besan Chilla","Breakfast","2 Pieces",240,10,28,6,"NorthIndia","Low",
            "High protein | Low cost | Good for weight maintain"));
        foods.add(new Meal("Ragi Porridge","Breakfast","1 Bowl",220,6,40,3,"SouthIndia","Low",
            "Calcium rich | Good for bones | Budget friendly"));
        foods.add(new Meal("Oats","Breakfast","1 Bowl",220,8,35,4,"All","Medium",
            "High fiber | Keeps full | Good for weight loss"));
        foods.add(new Meal("Muesli","Breakfast","1 Bowl",250,8,40,5,"All","Medium",
            "High fiber | Energy rich | Good for digestion"));
        foods.add(new Meal("Apple","Breakfast","1 Piece",95,0,25,0,"All","Medium",
            "Rich in fiber | Natural sugar | Good for digestion"));
        foods.add(new Meal("Dosa","Breakfast","2 Pieces",300,8,50,8,"SouthIndia","Medium",
            "Probiotic | Good energy | Light on stomach"));
        foods.add(new Meal("Vegetable Sandwich","Breakfast","2 Slices",280,9,42,6,"All","Medium",
            "Balanced nutrients | Good fiber | Easy to make"));
        foods.add(new Meal("Paneer Paratha","Breakfast","2 Pieces",380,16,45,14,"NorthIndia","Medium",
            "High protein | Good energy | Keeps full longer"));
        foods.add(new Meal("Greek Yogurt Bowl","Breakfast","1 Bowl",200,14,20,4,"All","Medium",
            "Very high protein | Probiotic | Good for gut"));
        foods.add(new Meal("Banana Pancake","Breakfast","3 Pieces",280,8,45,6,"All","Medium",
            "Good energy | Natural sugar | Easy to digest"));
        foods.add(new Meal("Avocado Toast","Breakfast","2 Slices",320,8,35,16,"All","High",
            "Healthy fats | Good for heart | Rich in vitamins"));
        foods.add(new Meal("Protein Oats","Breakfast","1 Bowl",300,20,35,6,"All","High",
            "Very high protein | Best for muscle gain | Keeps full"));
        foods.add(new Meal("Egg White Omelette","Breakfast","3 Eggs",180,20,2,8,"All","Medium",
            "Very high protein | Low fat | Best for weight loss"));
        foods.add(new Meal("Quinoa Bowl","Breakfast","1 Bowl",280,10,42,5,"All","High",
            "Complete protein | Gluten free | Rich in nutrients"));
        foods.add(new Meal("Smoothie Bowl","Breakfast","1 Bowl",260,8,45,4,"All","High",
            "Rich in vitamins | Good energy | Antioxidant rich"));
        foods.add(new Meal("Whole Wheat Toast","Breakfast","2 Slices",200,7,35,3,"All","Medium",
            "Good fiber | Slow energy release | Heart healthy"));
        foods.add(new Meal("Peanut Butter Toast","Breakfast","2 Slices",280,10,32,12,"All","Medium",
            "High protein | Good fats | Keeps full longer"));
        foods.add(new Meal("Chia Pudding","Breakfast","1 Bowl",220,8,28,8,"All","High",
            "Rich in omega 3 | High fiber | Good for digestion"));

        // ================= LUNCH (30) =================

        foods.add(new Meal("Dal Rice","Lunch","1 Plate",400,14,60,8,"All","Low",
            "Complete protein | Budget friendly | Good energy"));
        foods.add(new Meal("Khichdi","Lunch","1 Plate",340,11,50,6,"All","Low",
            "Easy to digest | Balanced meal | Budget friendly"));
        foods.add(new Meal("Dal Roti","Lunch","2 Roti",380,14,55,8,"All","Low",
            "High protein | Good fiber | Budget friendly"));
        foods.add(new Meal("Jowar Bhakri","Lunch","2 Bhakri",350,10,65,3,"Maharashtra","Low",
            "Gluten free | High fiber | Traditional Maharashtra food"));
        foods.add(new Meal("Pithla Bhakri","Lunch","1 Plate",420,14,55,12,"Maharashtra","Low",
            "High protein | Traditional food | Budget friendly"));
        foods.add(new Meal("Mixed Dal","Lunch","1 Bowl",300,15,40,5,"All","Low",
            "Very high protein | Iron rich | Budget friendly"));
        foods.add(new Meal("Rajma Rice","Lunch","1 Plate",450,16,65,8,"NorthIndia","Low",
            "High protein | Iron rich | Keeps full longer"));
        foods.add(new Meal("Chole Rice","Lunch","1 Plate",470,15,68,9,"NorthIndia","Low",
            "High protein | Good carbs | Budget friendly"));
        foods.add(new Meal("Sambar Rice","Lunch","1 Plate",380,12,58,6,"SouthIndia","Low",
            "Rich in vegetables | Good protein | Budget friendly"));
        foods.add(new Meal("Curd Rice","Lunch","1 Plate",350,10,52,7,"SouthIndia","Low",
            "Probiotic | Cools body | Easy to digest"));
        foods.add(new Meal("Moong Dal Khichdi","Lunch","1 Plate",320,13,48,4,"All","Low",
            "Easy to digest | High protein | Good for recovery"));
        foods.add(new Meal("Aloo Sabji Roti","Lunch","2 Roti",370,10,58,8,"All","Low",
            "Good energy | Budget friendly | Filling meal"));
        foods.add(new Meal("Paneer Rice","Lunch","1 Plate",500,20,55,18,"All","Medium",
            "High protein | Good for muscle gain | Tasty"));
        foods.add(new Meal("Palak Paneer","Lunch","1 Plate",420,18,18,20,"NorthIndia","Medium",
            "Iron rich | High protein | Good for iron deficiency"));
        foods.add(new Meal("Veg Biryani","Lunch","1 Plate",450,12,70,10,"All","Medium",
            "Good energy | Rich in vegetables | Balanced meal"));
        foods.add(new Meal("Vegetable Thali","Lunch","1 Thali",550,20,75,15,"All","Medium",
            "Complete balanced meal | Rich in nutrients | Good energy"));
        foods.add(new Meal("Paneer Butter Masala","Lunch","1 Bowl",480,20,25,28,"NorthIndia","Medium",
            "High protein | Good for weight gain | Tasty"));
        foods.add(new Meal("Rajma Chawal","Lunch","1 Plate",460,18,65,8,"NorthIndia","Medium",
            "High protein | Iron rich | Complete meal"));
        foods.add(new Meal("Egg Curry Rice","Lunch","1 Plate",480,22,58,14,"All","Medium",
            "High protein | Good energy | Balanced meal"));
        foods.add(new Meal("Fish Curry Rice","Lunch","1 Plate",520,30,55,14,"SouthIndia","Medium",
            "Very high protein | Omega 3 rich | Good for brain"));
        foods.add(new Meal("Protein Thali","Lunch","1 Thali",650,35,70,20,"All","High",
            "Very high protein | Best for muscle gain | Complete meal"));
        foods.add(new Meal("Chicken Rice","Lunch","1 Plate",550,35,58,14,"All","High",
            "Very high protein | Good for muscle gain | Balanced"));
        foods.add(new Meal("Grilled Chicken Thali","Lunch","1 Thali",600,40,55,16,"All","High",
            "Highest protein | Low fat | Best for weight loss"));
        foods.add(new Meal("Quinoa Salad","Lunch","1 Bowl",380,14,48,10,"All","High",
            "Complete protein | Gluten free | Rich in nutrients"));
        foods.add(new Meal("Buddha Bowl","Lunch","1 Bowl",420,18,50,12,"All","High",
            "Balanced nutrients | Rich in fiber | Good for weight loss"));
        foods.add(new Meal("Tofu Stir Fry","Lunch","1 Plate",380,18,35,14,"All","High",
            "High protein | Low carb | Good for vegetarians"));
        foods.add(new Meal("Lentil Soup Roti","Lunch","2 Roti",400,16,55,8,"All","Medium",
            "High protein | Good fiber | Heart healthy"));
        foods.add(new Meal("Sprouts Khichdi","Lunch","1 Plate",350,16,48,5,"All","Low",
            "High protein | Iron rich | Budget friendly"));
        foods.add(new Meal("Missal Pav","Lunch","1 Plate",430,16,62,10,"Maharashtra","Medium",
            "High protein | Traditional food | Good energy"));
        foods.add(new Meal("Vada Pav Meal","Lunch","1 Plate",400,10,65,12,"Maharashtra","Low",
            "Budget friendly | Good energy | Traditional Maharashtra"));

        // ================= EVENING SNACK (30) =================

        foods.add(new Meal("Roasted Chana","EveningSnack","50g",180,10,25,3,"All","Low",
            "High protein | Budget friendly | Good for weight loss"));
        foods.add(new Meal("Makhana","EveningSnack","30g",120,4,18,2,"All","Low",
            "Low calorie | Good for digestion | Light snack"));
        foods.add(new Meal("Peanuts","EveningSnack","30g",170,8,5,14,"All","Low",
            "High protein | Good fats | Budget friendly"));
        foods.add(new Meal("Fruit Bowl","EveningSnack","1 Bowl",160,3,35,1,"All","Low",
            "Natural vitamins | Boosts energy | Light on stomach"));
        foods.add(new Meal("Sprouts Chaat","EveningSnack","1 Bowl",180,12,22,2,"All","Low",
            "High protein | Iron rich | Budget friendly"));
        foods.add(new Meal("Corn Chaat","EveningSnack","1 Bowl",190,6,35,2,"All","Low",
            "Good fiber | Natural energy | Budget friendly"));
        foods.add(new Meal("Sweet Potato","EveningSnack","100g",120,2,28,0,"All","Low",
            "Natural energy | Good carbs | Budget friendly"));
        foods.add(new Meal("Buttermilk","EveningSnack","1 Glass",40,3,5,1,"All","Low",
            "Probiotic | Cools body | Very low calorie"));
        foods.add(new Meal("Vegetable Soup","EveningSnack","1 Bowl",90,3,12,2,"All","Low",
            "Very low calorie | Good for weight loss | Filling"));
        foods.add(new Meal("Banana","EveningSnack","1 Piece",110,1,27,0,"All","Low",
            "Instant energy | Budget friendly | Natural sugar"));
        foods.add(new Meal("Boiled Egg","EveningSnack","1 Egg",70,6,0,5,"All","Low",
            "High protein | Zero carb | Budget friendly"));
        foods.add(new Meal("Popcorn","EveningSnack","1 Bowl",100,3,20,1,"All","Low",
            "Low calorie | Good fiber | Light snack"));
        foods.add(new Meal("Dates","EveningSnack","4 Pieces",120,1,30,0,"All","Medium",
            "Iron rich | Natural energy | Good for blood"));
        foods.add(new Meal("Almonds","EveningSnack","20g",140,5,4,12,"All","Medium",
            "Healthy fats | Good for brain | Rich in vitamin E"));
        foods.add(new Meal("Walnuts","EveningSnack","20g",150,4,4,14,"All","Medium",
            "Omega 3 rich | Good for brain | Heart healthy"));
        foods.add(new Meal("Greek Yogurt","EveningSnack","1 Cup",120,10,8,3,"All","Medium",
            "Very high protein | Probiotic | Good for gut"));
        foods.add(new Meal("Dry Fruit Laddu","EveningSnack","1 Piece",180,4,20,8,"All","Medium",
            "Energy dense | Natural sugar | Good for weight gain"));
        foods.add(new Meal("Hummus Veggies","EveningSnack","1 Bowl",150,6,18,6,"All","Medium",
            "High fiber | Good protein | Heart healthy"));
        foods.add(new Meal("Mixed Nuts","EveningSnack","30g",180,5,8,15,"All","Medium",
            "Healthy fats | Good for brain | Energy dense"));
        foods.add(new Meal("Peanut Butter","EveningSnack","2 tbsp",190,8,6,16,"All","Medium",
            "High protein | Good fats | Keeps full longer"));
        foods.add(new Meal("Fruit Yogurt","EveningSnack","1 Cup",160,6,25,3,"All","Medium",
            "Probiotic | Natural vitamins | Good for digestion"));
        foods.add(new Meal("Cheese Crackers","EveningSnack","6 Pieces",200,6,22,10,"All","Medium",
            "Good energy | Calcium rich | Easy snack"));
        foods.add(new Meal("Protein Bar","EveningSnack","1 Bar",220,15,20,8,"All","High",
            "Very high protein | Quick energy | Best post workout"));
        foods.add(new Meal("Whey Protein Shake","EveningSnack","1 Glass",160,25,5,2,"All","High",
            "Highest protein | Muscle recovery | Best for gym"));
        foods.add(new Meal("Energy Balls","EveningSnack","3 Pieces",200,8,22,9,"All","High",
            "Good energy | Protein rich | Natural ingredients"));
        foods.add(new Meal("Dark Chocolate","EveningSnack","2 Squares",120,2,14,7,"All","High",
            "Rich in antioxidants | Mood booster | Heart healthy"));
        foods.add(new Meal("Granola Bar","EveningSnack","1 Bar",190,4,28,7,"All","High",
            "Good energy | High fiber | Easy to carry"));
        foods.add(new Meal("Cottage Cheese","EveningSnack","100g",110,14,3,5,"All","Medium",
            "Very high protein | Low carb | Good for muscle"));
        foods.add(new Meal("Rice Cakes","EveningSnack","2 Pieces",70,1,15,0,"All","Medium",
            "Very low calorie | Light snack | Good for weight loss"));
        foods.add(new Meal("Edamame","EveningSnack","100g",120,11,9,5,"All","High",
            "High protein | Complete amino acids | Good for muscle"));

        // ================= DINNER (30) =================

        foods.add(new Meal("Dal Khichdi","Dinner","1 Plate",350,12,55,6,"All","Low",
            "Easy to digest | Balanced meal | Budget friendly"));
        foods.add(new Meal("Moong Khichdi","Dinner","1 Plate",320,13,50,4,"All","Low",
            "Light on stomach | High protein | Good for recovery"));
        foods.add(new Meal("Vegetable Soup","Dinner","1 Bowl",100,4,15,2,"All","Low",
            "Very low calorie | Good for weight loss | Light dinner"));
        foods.add(new Meal("Dal Roti","Dinner","2 Roti",350,14,50,7,"All","Low",
            "High protein | Good fiber | Budget friendly"));
        foods.add(new Meal("Jowar Roti","Dinner","2 Roti",280,8,50,2,"Maharashtra","Low",
            "Gluten free | High fiber | Traditional Maharashtra"));
        foods.add(new Meal("Moong Dal","Dinner","1 Bowl",220,14,28,2,"All","Low",
            "High protein | Iron rich | Easy to digest"));
        foods.add(new Meal("Fruit Salad","Dinner","1 Bowl",180,3,40,1,"All","Low",
            "Natural vitamins | Light dinner | Good for digestion"));
        foods.add(new Meal("Roti Sabji","Dinner","2 Roti",320,10,50,7,"All","Low",
            "Balanced meal | Good fiber | Budget friendly"));
        foods.add(new Meal("Tomato Soup","Dinner","1 Bowl",80,3,12,2,"All","Low",
            "Very low calorie | Rich in vitamin C | Light dinner"));
        foods.add(new Meal("Sprouts Salad","Dinner","1 Bowl",150,10,20,2,"All","Low",
            "High protein | Iron rich | Very low calorie"));
        foods.add(new Meal("Bajra Roti","Dinner","2 Roti",300,9,52,2,"NorthIndia","Low",
            "High fiber | Good for heart | Budget friendly"));
        foods.add(new Meal("Mixed Veg Sabji","Dinner","1 Bowl",180,5,25,6,"All","Low",
            "Rich in vitamins | Good fiber | Budget friendly"));
        foods.add(new Meal("Paneer Salad","Dinner","1 Bowl",250,18,8,12,"All","Medium",
            "High protein | Low carb | Good for weight loss"));
        foods.add(new Meal("Egg Salad","Dinner","1 Bowl",220,16,5,12,"All","Medium",
            "High protein | Low carb | Good for weight loss"));
        foods.add(new Meal("Palak Paneer","Dinner","1 Plate",400,18,15,18,"NorthIndia","Medium",
            "Iron rich | High protein | Good for iron deficiency"));
        foods.add(new Meal("Paneer Bhurji","Dinner","1 Bowl",300,18,8,18,"All","Medium",
            "High protein | Low carb | Good for muscle gain"));
        foods.add(new Meal("Boiled Eggs","Dinner","3 Eggs",210,18,2,15,"All","Medium",
            "Very high protein | Low carb | Best for weight loss"));
        foods.add(new Meal("Light Thali","Dinner","1 Plate",400,15,55,10,"All","Medium",
            "Balanced meal | Complete nutrition | Good energy"));
        foods.add(new Meal("Egg Curry","Dinner","1 Bowl",280,18,12,15,"All","Medium",
            "High protein | Good energy | Balanced meal"));
        foods.add(new Meal("Mushroom Curry","Dinner","1 Bowl",220,8,18,12,"All","Medium",
            "Low calorie | Good for immunity | Rich in vitamins"));
        foods.add(new Meal("Grilled Paneer","Dinner","150g",320,22,6,20,"All","High",
            "Very high protein | Low carb | Best for muscle gain"));
        foods.add(new Meal("Chicken Salad","Dinner","1 Bowl",300,30,10,12,"All","High",
            "Very high protein | Low carb | Best for weight loss"));
        foods.add(new Meal("Grilled Chicken","Dinner","150g",250,35,0,10,"All","High",
            "Highest protein | Zero carb | Best for weight loss"));
        foods.add(new Meal("Salmon Bowl","Dinner","1 Bowl",380,35,20,14,"All","High",
            "Omega 3 rich | Very high protein | Good for brain"));
        foods.add(new Meal("Tofu Salad","Dinner","1 Bowl",240,16,15,10,"All","High",
            "High protein | Good for vegetarians | Low calorie"));
        foods.add(new Meal("Protein Bowl","Dinner","1 Bowl",380,30,25,14,"All","High",
            "Very high protein | Best for muscle gain | Balanced"));
        foods.add(new Meal("Quinoa Salad","Dinner","1 Bowl",300,12,40,8,"All","High",
            "Complete protein | Gluten free | Rich in nutrients"));
        foods.add(new Meal("Lentil Soup","Dinner","1 Bowl",200,12,28,3,"All","Low",
            "High protein | Iron rich | Good for blood"));
        foods.add(new Meal("Zucchini Stir Fry","Dinner","1 Bowl",160,6,18,7,"All","High",
            "Very low calorie | Good for weight loss | Rich in vitamins"));
        foods.add(new Meal("Cauliflower Rice","Dinner","1 Bowl",150,5,20,5,"All","Medium",
            "Low carb | Good for weight loss | Light dinner"));

        // ================= PRE WORKOUT (30) =================

        foods.add(new Meal("Banana","PreWorkout","1 Piece",105,1,27,0,"All","Low",
            "Instant energy | Natural sugar | Budget friendly"));
        foods.add(new Meal("Dates","PreWorkout","4 Pieces",120,1,30,0,"All","Low",
            "Iron rich | Quick energy | Natural sugar"));
        foods.add(new Meal("Black Coffee","PreWorkout","1 Cup",5,0,1,0,"All","Low",
            "Boosts performance | Zero calories | Improves focus"));
        foods.add(new Meal("Sweet Potato","PreWorkout","100g",120,2,28,0,"All","Low",
            "Slow energy release | Good carbs | Budget friendly"));
        foods.add(new Meal("Peanut Chikki","PreWorkout","2 Pieces",180,5,18,8,"All","Low",
            "Quick energy | Protein rich | Budget friendly"));
        foods.add(new Meal("Orange","PreWorkout","1 Piece",80,1,18,0,"All","Low",
            "Quick energy | Vitamin C | Light on stomach"));
        foods.add(new Meal("Roasted Chana","PreWorkout","50g",180,10,25,3,"All","Low",
            "High protein | Good carbs | Budget friendly"));
        foods.add(new Meal("Fruit Bowl","PreWorkout","1 Bowl",160,3,35,1,"All","Low",
            "Natural energy | Rich in vitamins | Light on stomach"));
        foods.add(new Meal("Curd","PreWorkout","1 Bowl",100,5,8,3,"All","Low",
            "Probiotic | Light on stomach | Budget friendly"));
        foods.add(new Meal("Sprouts","PreWorkout","1 Bowl",180,12,25,2,"All","Low",
            "High protein | Iron rich | Budget friendly"));
        foods.add(new Meal("Cornflakes","PreWorkout","1 Bowl",200,5,42,2,"All","Low",
            "Quick energy | Good carbs | Budget friendly"));
        foods.add(new Meal("Poha","PreWorkout","1 Bowl",250,6,45,4,"Maharashtra","Low",
            "Good carbs | Easy to digest | Budget friendly"));
        foods.add(new Meal("Apple","PreWorkout","1 Piece",95,0,25,0,"All","Medium",
            "Natural energy | Good fiber | Light on stomach"));
        foods.add(new Meal("Oats","PreWorkout","1 Bowl",220,8,35,4,"All","Medium",
            "Slow energy release | High fiber | Keeps full longer"));
        foods.add(new Meal("Muesli","PreWorkout","1 Bowl",250,8,40,5,"All","Medium",
            "Good energy | High fiber | Rich in nutrients"));
        foods.add(new Meal("Banana Shake","PreWorkout","1 Glass",220,7,40,4,"All","Medium",
            "Good energy | Protein rich | Easy to digest"));
        foods.add(new Meal("Whole Wheat Toast","PreWorkout","2 Slices",200,7,35,3,"All","Medium",
            "Slow energy release | Good fiber | Heart healthy"));
        foods.add(new Meal("Boiled Egg","PreWorkout","2 Eggs",140,12,1,10,"All","Low",
            "High protein | Low carb | Budget friendly"));
        foods.add(new Meal("Rice Cakes","PreWorkout","2 Pieces",70,1,15,0,"All","Medium",
            "Very light | Quick carbs | Good for energy"));
        foods.add(new Meal("Greek Yogurt","PreWorkout","1 Cup",120,10,8,3,"All","Medium",
            "High protein | Probiotic | Light on stomach"));
        foods.add(new Meal("Peanut Butter Toast","PreWorkout","2 Slices",280,10,32,12,"All","Medium",
            "High protein | Good fats | Sustained energy"));
        foods.add(new Meal("Energy Bar","PreWorkout","1 Bar",200,6,30,6,"All","High",
            "Quick energy | Balanced nutrients | Easy to carry"));
        foods.add(new Meal("Pre Workout Drink","PreWorkout","1 Scoop",80,0,20,0,"All","High",
            "Boosts performance | Increases stamina | Fast energy"));
        foods.add(new Meal("Protein Bar","PreWorkout","1 Bar",220,15,20,8,"All","High",
            "High protein | Good energy | Best for gym"));
        foods.add(new Meal("Almond Butter Toast","PreWorkout","2 Slices",300,10,34,14,"All","High",
            "Healthy fats | Sustained energy | Rich in vitamin E"));
        foods.add(new Meal("Granola","PreWorkout","1 Bowl",250,6,38,8,"All","High",
            "Good energy | High fiber | Rich in nutrients"));
        foods.add(new Meal("Smoothie","PreWorkout","1 Glass",200,6,38,3,"All","High",
            "Rich in vitamins | Natural energy | Easy to digest"));
        foods.add(new Meal("Watermelon","PreWorkout","2 Slices",80,1,18,0,"All","Low",
            "Hydrates body | Natural sugar | Very light"));
        foods.add(new Meal("Mango","PreWorkout","1 Cup",100,1,25,0,"All","Medium",
            "Natural energy | Rich in vitamins | Good taste"));
        foods.add(new Meal("Coconut Water","PreWorkout","1 Glass",45,1,10,0,"SouthIndia","Medium",
            "Natural electrolytes | Hydrates body | Light on stomach"));

        // ================= POST WORKOUT (30) =================

        foods.add(new Meal("Milk","PostWorkout","1 Glass",150,8,12,8,"All","Low",
            "Good protein | Calcium rich | Budget friendly"));
        foods.add(new Meal("Fruit Bowl","PostWorkout","1 Bowl",160,3,35,1,"All","Low",
            "Natural vitamins | Replenishes energy | Budget friendly"));
        foods.add(new Meal("Sprouts","PostWorkout","1 Bowl",180,12,25,2,"All","Low",
            "High protein | Iron rich | Fast recovery"));
        foods.add(new Meal("Sweet Potato","PostWorkout","100g",120,2,28,0,"All","Low",
            "Replenishes glycogen | Good carbs | Budget friendly"));
        foods.add(new Meal("Boiled Eggs","PostWorkout","3 Eggs",210,18,2,15,"All","Medium",
            "Very high protein | Muscle recovery | Low carb"));
        foods.add(new Meal("Paneer","PostWorkout","100g",265,18,6,20,"All","Medium",
            "High protein | Good for muscle gain | Calcium rich"));
        foods.add(new Meal("Greek Yogurt","PostWorkout","1 Cup",120,10,8,3,"All","Medium",
            "High protein | Probiotic | Fast recovery"));
        foods.add(new Meal("Banana Shake","PostWorkout","1 Glass",220,7,40,4,"All","Medium",
            "Replenishes energy | Good protein | Easy to digest"));
        foods.add(new Meal("Chocolate Milk","PostWorkout","1 Glass",220,10,30,6,"All","Medium",
            "Good protein | Replenishes glycogen | Tasty recovery"));
        foods.add(new Meal("Peanut Butter Toast","PostWorkout","2 Slices",250,10,20,12,"All","Medium",
            "High protein | Good fats | Sustained recovery"));
        foods.add(new Meal("Muesli","PostWorkout","1 Bowl",250,8,40,5,"All","Medium",
            "Good energy | High fiber | Rich in nutrients"));
        foods.add(new Meal("Oats Shake","PostWorkout","1 Glass",240,10,35,5,"All","Medium",
            "Good protein | Replenishes energy | Easy to digest"));
        foods.add(new Meal("Egg Salad","PostWorkout","1 Bowl",220,16,5,12,"All","Medium",
            "High protein | Low carb | Good for weight loss"));
        foods.add(new Meal("Curd Rice","PostWorkout","1 Bowl",300,10,45,6,"All","Medium",
            "Probiotic | Good carbs | Easy to digest"));
        foods.add(new Meal("Dal Rice","PostWorkout","1 Plate",400,14,60,8,"All","Low",
            "Complete protein | Good carbs | Budget friendly"));
        foods.add(new Meal("Sprouts Chaat","PostWorkout","1 Bowl",180,12,22,2,"All","Low",
            "High protein | Iron rich | Budget friendly"));
        foods.add(new Meal("Protein Shake","PostWorkout","1 Scoop",180,25,5,2,"All","High",
            "Fastest protein | Best muscle recovery | Gym essential"));
        foods.add(new Meal("Recovery Drink","PostWorkout","1 Bottle",180,15,20,2,"All","High",
            "Fast recovery | Replenishes electrolytes | Reduces soreness"));
        foods.add(new Meal("Whey Protein","PostWorkout","1 Scoop",120,24,3,1,"All","High",
            "Highest protein | Fastest absorption | Best for muscle"));
        foods.add(new Meal("Chicken Breast","PostWorkout","150g",250,35,0,10,"All","High",
            "Highest protein | Zero carb | Best for weight loss"));
        foods.add(new Meal("Salmon","PostWorkout","150g",280,30,0,16,"All","High",
            "Omega 3 rich | Very high protein | Reduces inflammation"));
        foods.add(new Meal("Protein Bar","PostWorkout","1 Bar",220,15,20,8,"All","High",
            "High protein | Quick recovery | Easy to carry"));
        foods.add(new Meal("Cottage Cheese","PostWorkout","100g",110,14,3,5,"All","Medium",
            "High protein | Slow digesting | Good for night recovery"));
        foods.add(new Meal("Tuna Salad","PostWorkout","1 Bowl",200,28,5,6,"All","High",
            "Very high protein | Low fat | Best for weight loss"));
        foods.add(new Meal("Quinoa Bowl","PostWorkout","1 Bowl",280,10,42,5,"All","High",
            "Complete protein | Gluten free | Rich in nutrients"));
        foods.add(new Meal("Tofu Scramble","PostWorkout","1 Bowl",200,16,10,10,"All","High",
            "High protein | Good for vegetarians | Low calorie"));
        foods.add(new Meal("Coconut Water","PostWorkout","1 Glass",45,1,10,0,"SouthIndia","Medium",
            "Natural electrolytes | Hydrates body | Reduces cramps"));
        foods.add(new Meal("Almond Milk","PostWorkout","1 Glass",60,2,8,3,"All","Medium",
            "Low calorie | Calcium rich | Good for bones"));
        foods.add(new Meal("Lentil Soup","PostWorkout","1 Bowl",200,12,28,3,"All","Low",
            "High protein | Iron rich | Budget friendly"));
        foods.add(new Meal("Mixed Nuts","PostWorkout","30g",180,5,8,15,"All","Medium",
            "Healthy fats | Good for recovery | Rich in minerals"));

        return foods;
    }
}