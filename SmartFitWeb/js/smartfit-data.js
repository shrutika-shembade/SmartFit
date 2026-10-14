// ======================================================
// smartfit-data.js - COMPLETE VERSION (FIXED)
// Central engine: saves profile, all calculations,
// diet plan generation, tracking, achievements
// ======================================================

const SmartFit = {

  // ── Storage ──────────────────────────────────────────
  saveProfile(data) {
    localStorage.setItem('sf_profile', JSON.stringify(data));
    localStorage.setItem('sf_saved', 'true');
  },

  loadProfile() {
    try {
      const raw = localStorage.getItem('sf_profile');
      return raw ? JSON.parse(raw) : null;
    } catch(e) { return null; }
  },

  hasProfile() {
    return localStorage.getItem('sf_saved') === 'true'
      && !!localStorage.getItem('sf_profile');
  },

  requireProfile() {
    if (!this.hasProfile()) {
      window.location.href = 'profile.html';
      return null;
    }
    return this.loadProfile();
  },

  // ── Calculations ─────────────────────────────────────
  calculateBMI(weight, heightCm) {
    const h = heightCm / 100;
    return +(weight / (h * h)).toFixed(2);
  },

  getBMICategory(bmi) {
    if (bmi < 18.5) return 'Underweight';
    if (bmi < 25)   return 'Normal';
    if (bmi < 30)   return 'Overweight';
    return 'Obese';
  },

    // SAFETY: explicit, never inferred from gender/age/occupation.
  PREGNANCY_SAFETY_GOAL: 'Pregnancy — Professional Review',

  getGoal(bmi, lifeStage) {
    if (lifeStage === 'Pregnancy') return this.PREGNANCY_SAFETY_GOAL;
    if (bmi < 18.5) return 'Weight Gain';
    if (bmi < 25)   return 'Maintain';
    return 'Weight Loss';
  },

  calculateBMR(weight, heightCm, age, gender) {
    if (gender === 'Female')
      return (10 * weight) + (6.25 * heightCm) - (5 * age) - 161;
    return (10 * weight) + (6.25 * heightCm) - (5 * age) + 5;
  },

  getActivityMultiplier(lifestyle) {
    const map = {
      'Sedentary': 1.2, 'Moderate': 1.375,
      'Active': 1.55,   'VeryActive': 1.725
    };
    return map[lifestyle] || 1.375;
  },

  calculateTDEE(bmr, lifestyle) {
    return bmr * this.getActivityMultiplier(lifestyle);
  },

  calculateBodyFat(bmi, age, gender) {
    if (gender === 'Female')
      return +((1.20 * bmi) + (0.23 * age) - 5.4).toFixed(1);
    return +((1.20 * bmi) + (0.23 * age) - 16.2).toFixed(1);
  },

  calculateIdealWeight(heightCm, gender) {
    if (gender === 'Female')
      return +(45.5 + 2.3 * ((heightCm - 152.4) / 2.54)).toFixed(1);
    return +(50.0 + 2.3 * ((heightCm - 152.4) / 2.54)).toFixed(1);
  },

  calculateLBM(weight, bodyFatPct) {
    return +(weight * (1 - bodyFatPct / 100)).toFixed(1);
  },

    calculateCalories(tdee, goal) {
    if (goal === this.PREGNANCY_SAFETY_GOAL) return null; // SAFETY: no auto deficit/surplus
    if (goal === 'Weight Loss') return Math.round(tdee - 300);
    if (goal === 'Weight Gain') return Math.round(tdee + 300);
    return Math.round(tdee);
  },

  calculateProtein(weight, goal) {
    if (goal === 'Weight Gain') return Math.round(weight * 2.0);
    if (goal === 'Weight Loss') return Math.round(weight * 1.8);
    return Math.round(weight * 1.6);
  },

  calculateFat(weight)  { return Math.round(weight * 0.8); },

  calculateCarbs(calories, protein, fat) {
    return Math.max(Math.round((calories - protein*4 - fat*9) / 4), 50);
  },

  calculateWater(weight) { return Math.round(weight * 35 + 500); },

  // ── Full analysis ─────────────────────────────────────
  getAnalysis(profile) {
    const w   = +profile.weight;
    const h   = +profile.height;
    const age = +profile.age;
    const g   = profile.gender || 'Female';
    const ls  = profile.lifestyle || 'Moderate';

    const bmi      = this.calculateBMI(w, h);
    const category = this.getBMICategory(bmi);
    const goal     = this.getGoal(bmi, profile.lifeStage); // SAFETY: explicit field only
    const isPregnancySafety = goal === this.PREGNANCY_SAFETY_GOAL;
    const bmr      = Math.round(this.calculateBMR(w, h, age, g));
    const tdee     = Math.round(this.calculateTDEE(bmr, ls));
    const bodyFat  = this.calculateBodyFat(bmi, age, g);   // reference metric, still computed
    const idealW   = this.calculateIdealWeight(h, g);       // reference metric, still computed
    const lbm      = this.calculateLBM(w, bodyFat);
    const calories = this.calculateCalories(tdee, goal);
    const protein  = isPregnancySafety ? null : this.calculateProtein(w, goal);
    const fat      = isPregnancySafety ? null : this.calculateFat(w);
    const carbs    = isPregnancySafety ? null : this.calculateCarbs(calories, protein, fat);
    const water    = this.calculateWater(w);
    const glasses  = Math.ceil(water / 250);

    return { bmi, category, goal, bmr, tdee, bodyFat,
             idealW, lbm, calories, protein, fat,
             carbs, water, glasses, isPregnancySafety };
             },

  // ── Diet Data ─────────────────────────────────────────
  DIET_DATA: {
    detox: [
      { name:'Lemon Water',      cal:20,  p:0,  c:5,  f:0, reason:'Detoxifies · Boosts immunity · Zero fat' },
      { name:'Jeera Water',      cal:15,  p:0,  c:4,  f:0, reason:'Aids digestion · Reduces bloating' },
      { name:'Green Tea',        cal:5,   p:0,  c:1,  f:0, reason:'Burns fat · Antioxidants · Good for PCOS', pcos:true, diabetes:true },
      { name:'Buttermilk',       cal:40,  p:3,  c:5,  f:1, reason:'Probiotics · Good for digestion', diabetes:true, hypertension:true },
      { name:'Beetroot Juice',   cal:50,  p:2,  c:12, f:0, reason:'Iron rich · Boosts stamina', iron:true },
      { name:'Spinach Juice',    cal:30,  p:2,  c:6,  f:0, reason:'High iron · Vitamin C · Best for anemia', iron:true },
      { name:'Pomegranate Juice',cal:80,  p:1,  c:18, f:0, reason:'Antioxidants · Iron rich · Heart healthy', iron:true },
      { name:'Amla Juice',       cal:35,  p:1,  c:8,  f:0, reason:'Vitamin C · Immunity booster' },
      { name:'Ginger Water',     cal:12,  p:0,  c:3,  f:0, reason:'Anti-inflammatory · Boosts metabolism' },
      { name:'Cinnamon Water',   cal:10,  p:0,  c:2,  f:0, reason:'Controls blood sugar', diabetes:true },
    ],
    breakfast: [
      { name:'Poha',             cal:250, p:6,  c:45, f:4,  reason:'Easy to digest · Budget friendly · Good carbs' },
      { name:'Sprouts',          cal:180, p:12, c:25, f:2,  reason:'High protein · Iron rich · Low fat', iron:true, pcos:true },
      { name:'Oats',             cal:220, p:8,  c:35, f:4,  reason:'High fiber · Controls cholesterol', diabetes:true, pcos:true },
      { name:'Idli',             cal:180, p:6,  c:35, f:1,  reason:'Fermented · Good for gut · Low fat' },
      { name:'Moong Chilla',     cal:220, p:12, c:25, f:5,  reason:'High protein · Low calorie', diabetes:true },
      { name:'Besan Chilla',     cal:240, p:10, c:28, f:6,  reason:'High protein · Low cost' },
      { name:'Fruit Bowl',       cal:160, p:3,  c:35, f:1,  reason:'Natural vitamins · Light on stomach', hypertension:true },
      { name:'Upma',             cal:220, p:5,  c:40, f:5,  reason:'Light breakfast · Good carbs' },
      { name:'Egg White Omelette',cal:180,p:20, c:2,  f:8,  reason:'Very high protein · Low fat', nonveg:true },
      { name:'Greek Yogurt Bowl',cal:200, p:14, c:20, f:4,  reason:'High protein · Probiotics' },
      { name:'Banana',           cal:110, p:1,  c:27, f:0,  reason:'Quick energy · Natural sugar · Budget friendly', hypertension:true },
      { name:'Jowar Bhakri',     cal:280, p:8,  c:55, f:2,  reason:'Gluten free · High fiber · Low GI', diabetes:true, pcos:true },
      { name:'Ragi Porridge',    cal:220, p:6,  c:40, f:3,  reason:'Calcium rich · Iron rich', iron:true },
    ],
    lunch: [
      { name:'Dal Rice',         cal:400, p:14, c:60, f:8,  reason:'Complete protein · Budget friendly · Balanced' },
      { name:'Khichdi',          cal:340, p:11, c:50, f:6,  reason:'Easy to digest · Balanced meal' },
      { name:'Jowar Bhakri',     cal:350, p:10, c:65, f:3,  reason:'Gluten free · High fiber', diabetes:true, pcos:true },
      { name:'Pithla Bhakri',    cal:420, p:14, c:55, f:12, reason:'Local ingredient · High protein' },
      { name:'Palak Paneer',     cal:420, p:18, c:18, f:20, reason:'Iron rich · High protein', iron:true },
      { name:'Rajma Rice',       cal:450, p:16, c:65, f:8,  reason:'High protein · Iron rich · Filling', iron:true },
      { name:'Vegetable Soup',   cal:100, p:4,  c:15, f:2,  reason:'Very low calorie · Best for weight loss', hypertension:true, diabetes:true },
      { name:'Moong Dal',        cal:220, p:14, c:28, f:2,  reason:'High protein · Iron rich', diabetes:true, iron:true },
      { name:'Sprouts Khichdi',  cal:350, p:16, c:48, f:5,  reason:'High protein · Iron rich', iron:true, pcos:true },
      { name:'Veg Biryani',      cal:450, p:12, c:70, f:10, reason:'Flavorful · Good carbs' },
      { name:'Chicken Rice',     cal:550, p:35, c:58, f:14, reason:'High protein · Energy dense', nonveg:true },
      { name:'Mixed Dal',        cal:300, p:15, c:40, f:5,  reason:'High protein · Budget friendly', iron:true },
      { name:'Chole Rice',       cal:470, p:15, c:68, f:9,  reason:'High fiber · Good protein' },
      { name:'Sambar Rice',      cal:380, p:12, c:58, f:6,  reason:'Vitamin rich · Good protein' },
    ],
    snack: [
      { name:'Roasted Chana',    cal:180, p:10, c:25, f:3,  reason:'High protein · Budget friendly' },
      { name:'Makhana',          cal:120, p:4,  c:18, f:2,  reason:'Low calorie · Good for weight loss' },
      { name:'Dates',            cal:120, p:1,  c:30, f:0,  reason:'Iron rich · Natural energy', iron:true },
      { name:'Almonds',          cal:140, p:5,  c:4,  f:12, reason:'Healthy fat · Vitamin E' },
      { name:'Fruit Bowl',       cal:160, p:3,  c:35, f:1,  reason:'Natural vitamins · Light snack' },
      { name:'Sprouts Chaat',    cal:180, p:12, c:22, f:2,  reason:'High protein · Iron rich', iron:true },
      { name:'Greek Yogurt',     cal:120, p:10, c:8,  f:3,  reason:'High protein · Probiotics' },
      { name:'Buttermilk',       cal:40,  p:3,  c:5,  f:1,  reason:'Probiotics · Very low calorie', hypertension:true },
      { name:'Banana',           cal:110, p:1,  c:27, f:0,  reason:'Quick energy · Natural sugar' },
      { name:'Peanuts',          cal:170, p:8,  c:5,  f:14, reason:'Healthy fat · Good protein' },
    ],
    dinner: [
      { name:'Dal Khichdi',      cal:350, p:12, c:55, f:6,  reason:'Easy to digest · Light dinner' },
      { name:'Moong Khichdi',    cal:320, p:13, c:50, f:4,  reason:'High protein · Light on stomach', diabetes:true },
      { name:'Vegetable Soup',   cal:100, p:4,  c:15, f:2,  reason:'Very low calorie · Weight loss', hypertension:true },
      { name:'Moong Dal',        cal:220, p:14, c:28, f:2,  reason:'High protein · Iron rich', iron:true, diabetes:true },
      { name:'Jowar Roti',       cal:280, p:8,  c:50, f:2,  reason:'Gluten free · High fiber', diabetes:true },
      { name:'Palak Paneer',     cal:400, p:18, c:15, f:18, reason:'Iron rich · High protein', iron:true },
      { name:'Grilled Chicken',  cal:250, p:35, c:0,  f:10, reason:'Highest protein · Zero carb', nonveg:true },
      { name:'Paneer Salad',     cal:250, p:18, c:8,  f:12, reason:'High protein · Low carb' },
      { name:'Lentil Soup',      cal:200, p:12, c:28, f:3,  reason:'Iron rich · High protein', iron:true },
      { name:'Tomato Soup',      cal:80,  p:3,  c:12, f:2,  reason:'Very low calorie · Antioxidants' },
      { name:'Dal Rice',         cal:350, p:13, c:55, f:6,  reason:'Balanced · Easy to make' },
    ]
  },

  _filter(arr, profile, analysis) {
    const h      = (profile.healthIssue || '').toLowerCase();
    const isVeg  = profile.foodPreference === 'Veg';
    const isVegan= profile.foodPreference === 'Vegan';
    const goal   = analysis.goal;

    const filtered = arr.filter(item => {
      if (isVeg   && item.nonveg)  return false;
      if (isVegan && (item.nonveg || item.dairy)) return false;
      if (h.includes('iron')         && item.iron)         return true;
      if (h.includes('pcos')         && item.pcos)         return true;
      if (h.includes('diabetes')     && item.diabetes)     return true;
      if (h.includes('hypertension') && item.hypertension) return true;
      if (goal === 'Weight Loss' && item.cal > 500) return false;
      if (goal === 'Weight Gain' && item.cal < 150) return false;
      return true;
    });

    return filtered.length ? filtered : arr;
  },

  _pick(arr) {
    if (!arr || !arr.length) return null;
    return arr[Math.floor(Math.random() * arr.length)];
  },

    // Shared meal-selection logic — used by the daily plan AND the
  // weekly plan generator below, so there is one source of truth.
  _buildDayMeals(profile, analysis) {
    const h = (profile.healthIssue || '').toLowerCase();

    let detoxPool = this._filter(this.DIET_DATA.detox, profile, analysis);
    let detox = this._pick(detoxPool);
    if (h.includes('iron')) {
      const d = detoxPool.filter(x => x.iron);
      if (d.length) detox = this._pick(d);
    } else if (h.includes('pcos') || h.includes('diabetes')) {
      const d = detoxPool.find(x => x.name === 'Green Tea' || x.name === 'Cinnamon Water' || x.name === 'Buttermilk');
      if (d) detox = d;
    }

    const plan = {
      detox,
      breakfast:   this._pick(this._filter(this.DIET_DATA.breakfast, profile, analysis)),
      lunch:       this._pick(this._filter(this.DIET_DATA.lunch,     profile, analysis)),
      snack:       this._pick(this._filter(this.DIET_DATA.snack,     profile, analysis)),
      dinner:      this._pick(this._filter(this.DIET_DATA.dinner,    profile, analysis)),
      preWorkout:  this._pick(this._filter(this.DIET_DATA.snack,     profile, analysis)),
      postWorkout: this._pick(this._filter(this.DIET_DATA.snack,     profile, analysis)),
    };

    let totalCal = 0, totalP = 0, totalC = 0, totalF = 0;
    Object.values(plan).forEach(m => {
      if (m) {
        totalCal += m.cal || 0;
        totalP   += m.p   || 0;
        totalC   += m.c   || 0;
        totalF   += m.f   || 0;
      }
    });
    plan.totals = { cal: totalCal, protein: totalP, carbs: totalC, fat: totalF };
    return plan;
  },

  generateDietPlan(profile, analysis) {
    if (analysis && analysis.isPregnancySafety) {
      const plan = {
        detox: null, breakfast: null, lunch: null, snack: null,
        dinner: null, preWorkout: null, postWorkout: null,
        totals: { cal: 0, protein: 0, carbs: 0, fat: 0 },
        isPregnancySafety: true,
        safetyMessage: 'Pregnancy profile detected. Personalized pregnancy nutrition should be reviewed with a qualified healthcare professional.'
      };
      localStorage.setItem('sf_diet_plan', JSON.stringify(plan));
      localStorage.setItem('sf_diet_date', new Date().toDateString());
      return plan;
    }

    const plan = this._buildDayMeals(profile, analysis);
    localStorage.setItem('sf_diet_plan',  JSON.stringify(plan));
    localStorage.setItem('sf_diet_date',  new Date().toDateString());
    return plan;
  },
    getTodayPlan(profile, analysis) {
    const saved = localStorage.getItem('sf_diet_plan');
    const savedDate = localStorage.getItem('sf_diet_date');
    if (saved && savedDate === new Date().toDateString()) {
      try { return JSON.parse(saved); } catch(e) {}
    }
    return this.generateDietPlan(profile, analysis);
  },

  // ══════════════════════════════════════════════════════
  // WEEKLY DIET PLAN (powers the Grocery module)
  // ══════════════════════════════════════════════════════
  DAY_NAMES: ['Monday','Tuesday','Wednesday','Thursday','Friday','Saturday','Sunday'],

  // Monday of the current week (YYYY-MM-DD) — stable cache key so
  // the weekly plan/grocery list stays the same all week and only
  // regenerates when a new week starts.
  _getWeekKey() {
    const d = new Date();
    const day = d.getDay(); // 0=Sun..6=Sat
    const diffToMonday = day === 0 ? -6 : 1 - day;
    const monday = new Date(d);
    monday.setDate(d.getDate() + diffToMonday);
    return monday.toISOString().split('T')[0];
  },

  generateWeeklyDietPlan(profile, analysis) {
    const week = {};
    if (analysis && analysis.isPregnancySafety) {
      this.DAY_NAMES.forEach(day => {
        week[day] = {
          detox: null, breakfast: null, lunch: null, snack: null,
          dinner: null, preWorkout: null, postWorkout: null,
          totals: { cal: 0, protein: 0, carbs: 0, fat: 0 },
          isPregnancySafety: true
        };
      });
    } else {
      // Each day calls _buildDayMeals independently, so each day's
      // meals are picked separately (not today's meal x7).
      this.DAY_NAMES.forEach(day => {
        week[day] = this._buildDayMeals(profile, analysis);
      });
    }
    const weekKey = this._getWeekKey();
    localStorage.setItem('sf_weekly_diet_plan', JSON.stringify(week));
    localStorage.setItem('sf_weekly_diet_week', weekKey);
    return week;
  },

  getWeeklyDietPlan(profile, analysis) {
    const saved = localStorage.getItem('sf_weekly_diet_plan');
    const savedWeek = localStorage.getItem('sf_weekly_diet_week');
    if (saved && savedWeek === this._getWeekKey()) {
      try { return JSON.parse(saved); } catch(e) {}
    }
    return this.generateWeeklyDietPlan(profile, analysis);
  },

  // ══════════════════════════════════════════════════════
  // GROCERY ENGINE
  // Prices are rough, manually-estimated Indian grocery prices —
  // always labeled "Estimated" in the UI, never live/current prices.
  // ══════════════════════════════════════════════════════

  GROCERY_PRICES: {
    'Poha':          { Maharashtra:55,  NorthIndia:52,  SouthIndia:53,  All:53  },
    'Oats':          { Maharashtra:180, NorthIndia:175, SouthIndia:178, All:177 },
    'Moong':         { Maharashtra:130, NorthIndia:120, SouthIndia:125, All:125 },
    'Sprouts':       { Maharashtra:40,  NorthIndia:35,  SouthIndia:38,  All:38  },
    'Rice':          { Maharashtra:60,  NorthIndia:55,  SouthIndia:55,  All:57  },
    'Dal':           { Maharashtra:120, NorthIndia:110, SouthIndia:112, All:115 },
    'Onion':         { Maharashtra:28,  NorthIndia:25,  SouthIndia:25,  All:26  },
    'Tomato':        { Maharashtra:32,  NorthIndia:28,  SouthIndia:28,  All:29  },
    'Spinach':       { Maharashtra:28,  NorthIndia:25,  SouthIndia:28,  All:27  },
    'Beetroot':      { Maharashtra:32,  NorthIndia:30,  SouthIndia:30,  All:31  },
    'Lemon':         { Maharashtra:5,   NorthIndia:4,   SouthIndia:4,   All:4   },
    'Banana':        { Maharashtra:3,   NorthIndia:2,   SouthIndia:3,   All:3   },
    'Dates':         { Maharashtra:400, NorthIndia:380, SouthIndia:390, All:390 },
    'Almonds':       { Maharashtra:800, NorthIndia:780, SouthIndia:790, All:790 },
    'Peanuts':       { Maharashtra:115, NorthIndia:110, SouthIndia:112, All:112 },
    'Makhana':       { Maharashtra:680, NorthIndia:670, SouthIndia:675, All:675 },
    'Oil':           { Maharashtra:125, NorthIndia:120, SouthIndia:122, All:122 },
    'Mustard Seeds': { Maharashtra:170, NorthIndia:160, SouthIndia:165, All:165 },
    'Turmeric':      { Maharashtra:190, NorthIndia:180, SouthIndia:185, All:185 },
    'Salt':          { Maharashtra:18,  NorthIndia:18,  SouthIndia:18,  All:18  },
    'Paneer':        { Maharashtra:360, NorthIndia:350, SouthIndia:365, All:358 },
    'Curd':          { Maharashtra:55,  NorthIndia:52,  SouthIndia:54,  All:54  },
    'Milk':          { Maharashtra:52,  NorthIndia:50,  SouthIndia:52,  All:51  },
    'Besan':         { Maharashtra:68,  NorthIndia:65,  SouthIndia:67,  All:67  },
    'Jowar Flour':   { Maharashtra:48,  NorthIndia:46,  SouthIndia:47,  All:47  },
    'Honey':         { Maharashtra:480, NorthIndia:470, SouthIndia:475, All:475 },
    'Default':       { Maharashtra:50,  NorthIndia:48,  SouthIndia:49,  All:49  },
  },

  // category added for grocery grouping
  MEAL_INGREDIENTS: {
    'Poha':           [['Poha',80,'g','Grains & Staples'],['Onion',50,'g','Vegetables'],['Oil',10,'ml','Other'],['Mustard Seeds',1,'tsp','Spices'],['Turmeric',0.5,'tsp','Spices'],['Lemon',0.5,'piece','Fruits']],
    'Oats':           [['Oats',80,'g','Grains & Staples'],['Milk',150,'ml','Dairy / Alternatives'],['Banana',0.5,'piece','Fruits'],['Honey',1,'tsp','Other']],
    'Sprouts':        [['Moong',80,'g','Pulses & Dal'],['Onion',30,'g','Vegetables'],['Lemon',0.5,'piece','Fruits'],['Salt',1,'pinch','Spices']],
    'Moong Chilla':   [['Moong',80,'g','Pulses & Dal'],['Onion',30,'g','Vegetables'],['Tomato',30,'g','Vegetables'],['Oil',8,'ml','Other']],
    'Besan Chilla':   [['Besan',80,'g','Grains & Staples'],['Onion',30,'g','Vegetables'],['Tomato',30,'g','Vegetables'],['Oil',8,'ml','Other']],
    'Dal Rice':       [['Rice',100,'g','Grains & Staples'],['Dal',60,'g','Pulses & Dal'],['Onion',40,'g','Vegetables'],['Tomato',40,'g','Vegetables'],['Oil',10,'ml','Other'],['Turmeric',0.5,'tsp','Spices'],['Salt',1,'pinch','Spices']],
    'Khichdi':        [['Rice',80,'g','Grains & Staples'],['Dal',60,'g','Pulses & Dal'],['Turmeric',0.5,'tsp','Spices'],['Salt',1,'pinch','Spices']],
    'Moong Dal':      [['Moong',80,'g','Pulses & Dal'],['Onion',40,'g','Vegetables'],['Tomato',40,'g','Vegetables'],['Oil',8,'ml','Other'],['Turmeric',0.5,'tsp','Spices']],
    'Jowar Bhakri':   [['Jowar Flour',120,'g','Grains & Staples'],['Salt',1,'pinch','Spices']],
    'Pithla Bhakri':  [['Besan',80,'g','Grains & Staples'],['Jowar Flour',120,'g','Grains & Staples'],['Onion',40,'g','Vegetables'],['Oil',10,'ml','Other']],
    'Palak Paneer':   [['Spinach',100,'g','Vegetables'],['Paneer',80,'g','Dairy / Alternatives'],['Onion',40,'g','Vegetables'],['Tomato',40,'g','Vegetables'],['Oil',10,'ml','Other']],
    'Vegetable Soup': [['Onion',50,'g','Vegetables'],['Tomato',100,'g','Vegetables'],['Oil',5,'ml','Other'],['Salt',1,'pinch','Spices']],
    'Dal Khichdi':    [['Rice',80,'g','Grains & Staples'],['Dal',60,'g','Pulses & Dal'],['Turmeric',0.5,'tsp','Spices'],['Salt',1,'pinch','Spices']],
    'Moong Khichdi':  [['Rice',80,'g','Grains & Staples'],['Moong',60,'g','Pulses & Dal'],['Salt',1,'pinch','Spices']],
    'Jowar Roti':     [['Jowar Flour',120,'g','Grains & Staples'],['Salt',1,'pinch','Spices']],
    'Lemon Water':    [['Lemon',1,'piece','Fruits'],['Honey',1,'tsp','Other']],
    'Green Tea':      [['Honey',0.5,'tsp','Other']],
    'Buttermilk':     [['Curd',100,'g','Dairy / Alternatives'],['Salt',1,'pinch','Spices']],
    'Beetroot Juice': [['Beetroot',100,'g','Vegetables'],['Lemon',0.5,'piece','Fruits']],
    'Spinach Juice':  [['Spinach',100,'g','Vegetables'],['Lemon',0.5,'piece','Fruits']],
    'Roasted Chana':  [['Peanuts',50,'g','Nuts & Seeds']],
    'Makhana':        [['Makhana',30,'g','Nuts & Seeds']],
    'Dates':          [['Dates',4,'piece','Fruits']],
    'Fruit Bowl':     [['Banana',1,'piece','Fruits'],['Honey',0.5,'tsp','Other']],
    'Banana':         [['Banana',2,'piece','Fruits']],
    'Greek Yogurt':   [['Curd',120,'g','Dairy / Alternatives']],
    'Greek Yogurt Bowl': [['Curd',150,'g','Dairy / Alternatives'],['Honey',1,'tsp','Other'],['Banana',0.5,'piece','Fruits']],
    'Idli':           [['Rice',100,'g','Grains & Staples'],['Dal',40,'g','Pulses & Dal'],['Salt',1,'pinch','Spices']],
    'Upma':           [['Oats',80,'g','Grains & Staples'],['Onion',30,'g','Vegetables'],['Oil',8,'ml','Other'],['Mustard Seeds',0.5,'tsp','Spices']],
    'Rajma Rice':     [['Rice',100,'g','Grains & Staples'],['Dal',60,'g','Pulses & Dal'],['Onion',40,'g','Vegetables'],['Tomato',40,'g','Vegetables'],['Oil',10,'ml','Other']],
    'Veg Biryani':    [['Rice',120,'g','Grains & Staples'],['Onion',50,'g','Vegetables'],['Tomato',50,'g','Vegetables'],['Oil',12,'ml','Other']],
    'Sprouts Khichdi':[['Rice',80,'g','Grains & Staples'],['Moong',60,'g','Pulses & Dal'],['Turmeric',0.5,'tsp','Spices']],
    'Tomato Soup':    [['Tomato',150,'g','Vegetables'],['Onion',30,'g','Vegetables'],['Oil',5,'ml','Other']],
    'Paneer Salad':   [['Paneer',80,'g','Dairy / Alternatives'],['Onion',30,'g','Vegetables'],['Tomato',30,'g','Vegetables'],['Lemon',0.5,'piece','Fruits']],
    'Mixed Dal':      [['Dal',80,'g','Pulses & Dal'],['Onion',30,'g','Vegetables'],['Tomato',30,'g','Vegetables'],['Oil',8,'ml','Other']],
    'Chole Rice':     [['Rice',100,'g','Grains & Staples'],['Dal',80,'g','Pulses & Dal'],['Onion',50,'g','Vegetables'],['Tomato',50,'g','Vegetables'],['Oil',10,'ml','Other']],
    'Sambar Rice':    [['Rice',100,'g','Grains & Staples'],['Dal',60,'g','Pulses & Dal'],['Onion',30,'g','Vegetables'],['Tomato',30,'g','Vegetables']],
    'Sprouts Chaat':  [['Moong',60,'g','Pulses & Dal'],['Onion',20,'g','Vegetables'],['Tomato',20,'g','Vegetables'],['Lemon',0.5,'piece','Fruits']],
    'Almonds':        [['Almonds',20,'g','Nuts & Seeds']],
    'Peanuts':        [['Peanuts',30,'g','Nuts & Seeds']],
    'Lentil Soup':    [['Dal',80,'g','Pulses & Dal'],['Onion',30,'g','Vegetables'],['Tomato',30,'g','Vegetables'],['Oil',8,'ml','Other']],
    'Chicken Rice':   [['Rice',100,'g','Grains & Staples']],
    'Ragi Porridge':  [['Oats',80,'g','Grains & Staples'],['Milk',150,'ml','Dairy / Alternatives']],
  },

  formatGroceryQty(qty, unit) {
    if (unit === 'g' && qty >= 1000) return (Math.round(qty / 100) / 10) + ' kg';
    if (unit === 'ml' && qty >= 1000) return (Math.round(qty / 100) / 10) + ' L';
    const rounded = Number.isInteger(qty) ? qty : Math.round(qty * 10) / 10;
    return rounded + ' ' + unit;
  },

  calcGroceryItemCost(name, qty, unit, region) {
    const priceTable = this.GROCERY_PRICES[name] || this.GROCERY_PRICES['Default'];
    const pricePerKg = priceTable[region] || priceTable['All'] || 50;
    switch (unit) {
      case 'g':      return Math.max(Math.round(qty / 1000 * pricePerKg), 1);
      case 'kg':     return Math.round(qty * pricePerKg);
      case 'ml':     return Math.max(Math.round(qty / 1000 * pricePerKg), 1);
      case 'L':      return Math.round(qty * pricePerKg);
      case 'piece':
      case 'pieces': return Math.round(qty * pricePerKg);
      case 'tsp':    return Math.max(Math.round(qty * 5 / 1000 * pricePerKg), 1);
      case 'pinch':  return 1;
      default:       return Math.max(Math.round(qty * 3), 1);
    }
  },

  // Walks every day/meal slot of a weekly plan and combines
  // duplicate ingredients (keyed by name+unit) into one merged
  // weekly quantity per ingredient — no fixed quantities, purely
  // derived from the actual generated diet.
  extractWeeklyIngredients(weeklyPlan) {
    const merged = {};
    this.DAY_NAMES.forEach(day => {
      const dayPlan = weeklyPlan[day];
      if (!dayPlan) return;
      const meals = [dayPlan.detox, dayPlan.breakfast, dayPlan.lunch, dayPlan.snack, dayPlan.dinner, dayPlan.preWorkout, dayPlan.postWorkout];
      meals.forEach(meal => {
        if (!meal) return;
        const ings = this.MEAL_INGREDIENTS[meal.name] || [];
        ings.forEach(([name, qty, unit, category]) => {
          const key = name + '_' + unit;
          if (merged[key]) merged[key].qty += qty;
          else merged[key] = { name, qty, unit, category };
        });
      });
    });
    return Object.values(merged);
  },

  // Approximate weekly budget reference per tier — used only to
  // compare against the estimated cost. The profile stores budget
  // as a category (low/medium/high), not a number, so these figures
  // are illustrative reference points, not a stored user budget.
  GROCERY_BUDGET_REFERENCE: { low: 600, medium: 1200, high: 2000 },

  getGroceryBudgetStatus(estimatedCost, budgetTier) {
    const tier = (budgetTier || 'medium').toLowerCase();
    const weeklyBudget = this.GROCERY_BUDGET_REFERENCE[tier] || this.GROCERY_BUDGET_REFERENCE.medium;
    const remaining = weeklyBudget - estimatedCost;
    return {
      weeklyBudget,
      estimatedCost,
      remaining: Math.abs(remaining),
      status: remaining >= 0 ? 'within' : 'over'
    };
  },

  getWeeklyGroceryList(profile, analysis) {
    const weeklyPlan = this.getWeeklyDietPlan(profile, analysis);
    const region = profile.region || 'All';
    const items = this.extractWeeklyIngredients(weeklyPlan);

    const byCategory = {};
    let grandTotal = 0;
    items.forEach(item => {
      const cost = this.calcGroceryItemCost(item.name, item.qty, item.unit, region);
      grandTotal += cost;
      const id = item.name + '_' + item.unit;
      const entry = {
        id, name: item.name,
        category: item.category || 'Other',
        qtyDisplay: this.formatGroceryQty(item.qty, item.unit),
        cost
      };
      if (!byCategory[entry.category]) byCategory[entry.category] = [];
      byCategory[entry.category].push(entry);
    });

    const budgetStatus = this.getGroceryBudgetStatus(grandTotal, profile.budget);
    return { byCategory, grandTotal, totalItems: items.length, budgetStatus, region };
  },

  // ── Purchased-state persistence, keyed per week ────────
  _groceryPurchasedKey() {
    return 'sf_grocery_purchased_' + this._getWeekKey();
  },

  getGroceryPurchased() {
    try { return JSON.parse(localStorage.getItem(this._groceryPurchasedKey())) || {}; }
    catch(e) { return {}; }
  },

  setGroceryPurchased(itemId, isPurchased) {
    const state = this.getGroceryPurchased();
    state[itemId] = isPurchased;
    localStorage.setItem(this._groceryPurchasedKey(), JSON.stringify(state));
    return state;
  },

  regenerateWeeklyGrocery(profile, analysis) {
    localStorage.removeItem('sf_weekly_diet_plan');
    localStorage.removeItem('sf_weekly_diet_week');
    localStorage.removeItem(this._groceryPurchasedKey());
    return this.getWeeklyGroceryList(profile, analysis);
  },


  // ── Tracking ──────────────────────────────────────────
  saveWeightEntry(weight) {
    const history = this.getWeightHistory();
    const today   = new Date().toISOString().split('T')[0];
    const existing = history.findIndex(e => e.date === today);
    if (existing >= 0) {
      history[existing].weight = +weight;
    } else {
      history.push({ date: today, weight: +weight });
    }
    localStorage.setItem('sf_weight_history', JSON.stringify(history));
    return history;
  },

  getWeightHistory() {
    try { return JSON.parse(localStorage.getItem('sf_weight_history')) || []; }
    catch(e) { return []; }
  },

  saveStepEntry(steps) {
    const history = this.getStepHistory();
    const today   = new Date().toISOString().split('T')[0];
    const existing = history.findIndex(e => e.date === today);
    const entry = {
      date: today, steps: +steps,
      calories: Math.round(steps * 0.04),
      distance: +(steps * 0.762 / 1000).toFixed(2)
    };
    if (existing >= 0) history[existing] = entry;
    else history.push(entry);
    localStorage.setItem('sf_step_history', JSON.stringify(history));
    return history;
  },

  getStepHistory() {
    try { return JSON.parse(localStorage.getItem('sf_step_history')) || []; }
    catch(e) { return []; }
  },

  saveWaterEntry(glasses) {
    const today = new Date().toISOString().split('T')[0];
    localStorage.setItem('sf_water_' + today, glasses);
  },

  getTodayWater() {
    const today = new Date().toISOString().split('T')[0];
    return parseInt(localStorage.getItem('sf_water_' + today)) || 0;
  },

  // ── Meal completion tracking (NEW - fixes "eaten" totals) ─────────
  saveMealDone(mealKey, isDone) {
    const today = new Date().toISOString().split('T')[0];
    const key   = 'sf_meals_' + today;
    let state = {};
    try { state = JSON.parse(localStorage.getItem(key)) || {}; } catch(e) {}
    state[mealKey] = isDone;
    localStorage.setItem(key, JSON.stringify(state));
    return state;
  },

  getMealsState() {
    const today = new Date().toISOString().split('T')[0];
    try { return JSON.parse(localStorage.getItem('sf_meals_' + today)) || {}; }
    catch(e) { return {}; }
  },

  // ── Workout completion tracking (NEW) ──────────────────
  saveWorkoutDone(exerciseKey, isDone) {
    const today = new Date().toISOString().split('T')[0];
    const key   = 'smartfit_workout_progress_' + today;
    let state = {};
    try { state = JSON.parse(localStorage.getItem(key)) || {}; } catch(e) {}
    state[exerciseKey] = isDone;
    localStorage.setItem(key, JSON.stringify(state));
    return state;
  },

  getWorkoutState() {
    const today = new Date().toISOString().split('T')[0];
    try { return JSON.parse(localStorage.getItem('smartfit_workout_progress_' + today)) || {}; }
    catch(e) { return {}; }
  },

  // ── Workout preferences (level/type) ───────────────────
  saveWorkoutPrefs(prefs) {
    localStorage.setItem('sf_workout_prefs', JSON.stringify(prefs));
  },

  getWorkoutPrefs() {
    try {
      const raw = localStorage.getItem('sf_workout_prefs');
      return raw ? JSON.parse(raw) : { level: 'Beginner', type: 'Workout' };
    } catch(e) { return { level: 'Beginner', type: 'Workout' }; }
  },

  // ── Exercise ID (stable, collision-safe across duplicate names) ──
  _slug(text) {
    return (text || '').toLowerCase().replace(/[^a-z0-9]+/g, '-').replace(/(^-|-$)/g, '');
  },

  getExerciseId(ex) {
    return this._slug(ex.bp) + '-' + this._slug(ex.n);
  },

  // ── Favorites (Explore Exercises) ──────────────────────
  // Persistent across days (unlike workout-progress keys above),
  // keyed by the same stable id getExerciseId() already produces.
  getFavorites() {
    try { return JSON.parse(localStorage.getItem('sf_favorites')) || {}; }
    catch(e) { return {}; }
  },

  toggleFavorite(exerciseId) {
    const favs = this.getFavorites();
    if (favs[exerciseId]) delete favs[exerciseId];
    else favs[exerciseId] = true;
    localStorage.setItem('sf_favorites', JSON.stringify(favs));
    return favs;
  },

  // ── Exercise video guidance (YouTube) ──────────────────
  // Never fabricates a video ID. Always builds a precise YouTube
  // search link for the exact exercise/pose name, so every single
  // exercise gets a correct, working tutorial link. Shared by both
  // Today's Workout cards and the Explore detail modal — one source
  // of truth, no duplicated video data anywhere.
  getVideoInfo(name, type) {
    const suffix = type === 'Yoga'
      ? 'yoga pose tutorial correct form'
      : 'exercise correct form tutorial';
    const query = name + ' ' + suffix;
    return {
      videoTitle: 'How to do ' + name,
      videoUrl: 'https://www.youtube.com/results?search_query=' + encodeURIComponent(query),
      videoType: 'youtube-search'
    };
  },

  // ── Weekly body-part split (mirrors WorkoutDatabase.java's WORKOUT_SPLIT/YOGA_SPLIT) ──
  WORKOUT_SPLIT: {
    Monday:'Chest', Tuesday:'Legs', Wednesday:'Abs', Thursday:'Shoulder',
    Friday:'Arms', Saturday:'Full Body', Sunday:'REST'
  },
  YOGA_SPLIT: {
    Monday:'Full Body', Tuesday:'Back', Wednesday:'Abs', Thursday:'Legs',
    Friday:'Thighs', Saturday:'Full Body', Sunday:'REST'
  },

  // ── Full exercise database, ported from WorkoutDatabase.java ──
  // n=name, bp=bodyPart, lvl=level, ty=type(Workout/Yoga), g=goal,
  // s=sets, r=reps (or hold-seconds for Yoga), rest=restSeconds
  WORKOUT_DATA: [
    {n:'Push Ups',bp:'Chest',lvl:'Beginner',ty:'Workout',g:'All',s:3,r:10,rest:30,steps:['Zameen par ulta let jao','Haath shoulder ke barabar rakh','Body seedhi rakho','Aahista neeche jao, phir upar aao','Saansi andar lo jate waqt, bahar nikalo aate waqt'],tip:'Agar mushkil lage to ghutno par push up karo pehle'},
    {n:'Wide Push Ups',bp:'Chest',lvl:'Beginner',ty:'Workout',g:'All',s:3,r:8,rest:30,steps:['Push up position mein aao','Haath thoda zyada wide rakh','Chest ko floor ke paas le jao','Slowly upar aao'],tip:'Wide grip se chest ke outer part train hota hai'},
    {n:'Wall Push Ups',bp:'Chest',lvl:'Beginner',ty:'Workout',g:'Weight Loss',s:3,r:12,rest:20,steps:['Deewar ke samne khade ho','Haath deewar par rakh','Body aage jhukao aur wapis aao','Saansi properly lo'],tip:'Beginners ke liye sabse aasaan chest exercise'},
    {n:'Diamond Push Ups',bp:'Chest',lvl:'Intermediate',ty:'Workout',g:'All',s:4,r:12,rest:45,steps:['Push up position mein aao','Haath ko paas rakh - diamond shape banao','Chest neeche lao','Upar push karo'],tip:'Triceps aur chest dono train hote hain'},
    {n:'Decline Push Ups',bp:'Chest',lvl:'Intermediate',ty:'Workout',g:'Weight Gain',s:4,r:10,rest:45,steps:['Paon kisi height par rakh (kursi ya bed)','Haath zameen par rakh','Upper chest ke liye neeche jao','Slowly upar aao'],tip:'Upper chest ke liye best exercise hai'},
    {n:'Archer Push Ups',bp:'Chest',lvl:'Intermediate',ty:'Workout',g:'All',s:3,r:8,rest:60,steps:['Wide push up position mein aao','Ek taraf jhuko aur ek haath seedha rakho','Wapis aao aur doosri taraf karo','Alternate karo'],tip:'Ek taraf ka chest zyada train hota hai'},
    {n:'Clap Push Ups',bp:'Chest',lvl:'Advanced',ty:'Workout',g:'Weight Gain',s:4,r:8,rest:60,steps:['Normal push up karo','Upar aate waqt itni force lagao ki haath zameen se uthe','Hawa mein taali bajao','Safely land karo'],tip:'Explosive power ke liye best - safety ka dhyan rakho'},
    {n:'Pike Push Ups',bp:'Chest',lvl:'Advanced',ty:'Workout',g:'All',s:4,r:10,rest:60,steps:['Inverted V position mein aao','Sar ko zameen ki taraf le jao','Slowly upar push karo','Kamar seedhi rakho'],tip:'Shoulder aur chest dono ke liye excellent'},

    {n:'Arm Circles',bp:'Shoulder',lvl:'Beginner',ty:'Workout',g:'All',s:3,r:20,rest:20,steps:['Seedhe khade ho','Dono haath side mein phailao','Chote circles mein ghhumao - forward','Phir backward circles karo'],tip:'Shoulder warm up ke liye best exercise'},
    {n:'Shoulder Taps',bp:'Shoulder',lvl:'Beginner',ty:'Workout',g:'Weight Loss',s:3,r:16,rest:30,steps:['Push up position mein aao','Ek haath se doosre shoulder ko tap karo','Balance maintain karo','Dono taraf alternate karo'],tip:'Core aur shoulder dono train hote hain'},
    {n:'Pike Push Ups',bp:'Shoulder',lvl:'Intermediate',ty:'Workout',g:'All',s:4,r:10,rest:45,steps:['Inverted V shape banao','Sar neeche le jao','Shoulder se push karo upar','Position maintain karo'],tip:'Shoulder press ka home version hai yeh'},
    {n:'Handstand Wall Hold',bp:'Shoulder',lvl:'Advanced',ty:'Workout',g:'Weight Gain',s:3,r:30,rest:60,steps:['Deewar ke paas jao','Haath zameen par rakh','Paon deewar par le jao','30 second hold karo'],tip:'Pure shoulder strength ke liye - slowly practice karo'},
    {n:'Wall Handstand Push Ups',bp:'Shoulder',lvl:'Advanced',ty:'Workout',g:'Weight Gain',s:3,r:5,rest:90,steps:['Handstand position mein jao','Aahista sar zameen par le jao','Shoulder se push karke upar aao','Control maintain karo'],tip:'Advanced shoulder exercise - pehle hold practice karo'},

    {n:'Crunches',bp:'Abs',lvl:'Beginner',ty:'Workout',g:'Weight Loss',s:3,r:15,rest:30,steps:['Zameen par peethe ke bal leto','Ghutne mod lo','Haath sar ke peeche rakh','Shoulders ko upar uthao','Slowly neeche aao'],tip:'Neck par zyada pressure mat do - abs se uthao'},
    {n:'Plank',bp:'Abs',lvl:'Beginner',ty:'Workout',g:'All',s:3,r:30,rest:30,steps:['Push up position mein aao','Forearm par rest karo','Body ek seedhi line mein rakho','30 seconds hold karo','Breathe normally karte raho'],tip:'Kamar neeche mat girane do - body seedhi rakho'},
    {n:'Leg Raises',bp:'Abs',lvl:'Beginner',ty:'Workout',g:'Weight Loss',s:3,r:12,rest:30,steps:['Seedha let jao','Haath sides mein rakho','Dono paon seedhe rakho','90 degree tak upar uthao','Slowly neeche lao'],tip:'Lower abs ke liye best exercise hai yeh'},
    {n:'Bicycle Crunches',bp:'Abs',lvl:'Intermediate',ty:'Workout',g:'Weight Loss',s:3,r:20,rest:30,steps:['Zameen par let jao','Haath sar ke peeche','Ek ghutna chest ki taraf lao','Doosri taraf ki elbow se touch karo','Cycle chalane jaisa motion karo'],tip:'Slow karo - fast karne se benefit kam hota hai'},
    {n:'Mountain Climbers',bp:'Abs',lvl:'Intermediate',ty:'Workout',g:'Weight Loss',s:4,r:30,rest:30,steps:['Push up position mein aao','Ek ghutna chest ki taraf lao quickly','Wapis lo aur doosra ghutna lao','Running motion jaisi speed karo'],tip:'Cardio + abs dono ek saath - best fat burner'},
    {n:'Russian Twist',bp:'Abs',lvl:'Intermediate',ty:'Workout',g:'Weight Loss',s:3,r:20,rest:30,steps:['Zameen par baithke ghutne mod lo','Thoda peeche jhuko','Haath jod lo','Left right twist karo','Paon zameen se utha sakte ho (advanced)'],tip:'Obliques (side abs) ke liye perfect exercise'},
    {n:'V-Ups',bp:'Abs',lvl:'Advanced',ty:'Workout',g:'All',s:4,r:12,rest:45,steps:['Seedha let jao - haath upar','Ek saath haath aur paon upar uthao','V shape banao','Slowly wapis zameen par aao'],tip:'Full core exercise - ek saath upper lower abs'},
    {n:'Dragon Flag',bp:'Abs',lvl:'Advanced',ty:'Workout',g:'Weight Gain',s:3,r:8,rest:60,steps:['Bench ya zameen par peethe ke bal','Haath koi solid cheez pakdo','Poora body seedha rakho','Slowly neeche lao - zameen se upar'],tip:'Bruce Lee wali exercise - bahut advanced hai'},

    {n:'Squats',bp:'Legs',lvl:'Beginner',ty:'Workout',g:'All',s:3,r:15,rest:30,steps:['Seedhe khade ho - paon shoulder width','Haath aage ya seene par rakho','Ghutne modte hue neeche baitho','Jaisa kursi par baithna ho','Heel zameen par rakho - wapis khade ho'],tip:'Ghutne paon ki ungliyon se aage mat nikalne do'},
    {n:'Lunges',bp:'Legs',lvl:'Beginner',ty:'Workout',g:'All',s:3,r:12,rest:30,steps:['Seedhe khade ho','Ek paon aage rakho','Dono ghutne 90 degree mod lo','Wapis khade ho','Doosra paon aage rakho'],tip:'Balance ke liye ek jagah dekh ke karo'},
    {n:'Calf Raises',bp:'Legs',lvl:'Beginner',ty:'Workout',g:'All',s:3,r:20,rest:20,steps:['Seedhe khade ho','Ek haath deewar par balance ke liye','Paon ki ungliyon par uthao','Slowly neeche aao'],tip:'Paon ki muscles ke liye bahut acha'},
    {n:'Jump Squats',bp:'Legs',lvl:'Intermediate',ty:'Workout',g:'Weight Loss',s:4,r:15,rest:45,steps:['Normal squat position','Neeche jao','Upar aate waqt jump karo','Softly land karo - ghutne thode mod ke','Directly next squat mein jao'],tip:'Cardio + leg strength - knees ka dhyan rakho'},
    {n:'Sumo Squats',bp:'Legs',lvl:'Intermediate',ty:'Workout',g:'All',s:3,r:15,rest:30,steps:['Paon wide rakh - wider than shoulder','Paon bahar ki taraf point karo','Deep squat mein jao','Wapis khade ho'],tip:'Inner thighs ke liye best exercise'},
    {n:'Bulgarian Split Squats',bp:'Legs',lvl:'Advanced',ty:'Workout',g:'Weight Gain',s:4,r:10,rest:60,steps:['Peeche kursi ya bed rakho','Ek paon peeche kursi par','Aage wale paon par squat karo','Slowly neeche jao','Wapis upar aao'],tip:'Single leg strength ke liye best - balance practice karo'},
    {n:'Pistol Squats',bp:'Legs',lvl:'Advanced',ty:'Workout',g:'Weight Gain',s:3,r:6,rest:90,steps:['Ek paon par khade ho','Doosra paon aage seedha rakho','Ek paon par neeche baithho','Wapis khade ho','Haath balance ke liye aage rakho'],tip:'Bahut advanced - pehle wall ka support lo'},

    {n:'Glute Bridge',bp:'Thighs',lvl:'Beginner',ty:'Workout',g:'All',s:3,r:15,rest:30,steps:['Peethe ke bal let jao','Ghutne modte hue paon zameen par','Kamar ko upar uthao','Glutes squeeze karo upar','Slowly neeche aao'],tip:'Glutes aur inner thighs ke liye perfect'},
    {n:'Side Leg Raises',bp:'Thighs',lvl:'Beginner',ty:'Workout',g:'Weight Loss',s:3,r:15,rest:20,steps:['Karayt par let jao ek taraf','Ek haath sir ke neeche','Upar wala paon uthao','Slowly neeche lao','Dono taraf karo'],tip:'Outer thighs aur hips toning ke liye best'},
    {n:'Donkey Kicks',bp:'Thighs',lvl:'Beginner',ty:'Workout',g:'Weight Loss',s:3,r:15,rest:20,steps:['Charo haath paon par aa jao','Ek paon peeche upar uthao - kick karo','Glutes squeeze karo','Wapis aao','Dono taraf repeat'],tip:'Glutes toning ke liye number one exercise'},
    {n:'Inner Thigh Squeeze',bp:'Thighs',lvl:'Beginner',ty:'Workout',g:'All',s:3,r:20,rest:20,steps:['Peethe ke bal let jao','Ghutne modte hue paon zameen par','Dono ghutno ke beech takiya rakh','Squeeze karo 5 second','Release karo'],tip:'Inner thighs ke liye simple par effective'},
    {n:'Fire Hydrants',bp:'Thighs',lvl:'Intermediate',ty:'Workout',g:'Weight Loss',s:3,r:15,rest:30,steps:['Charo haath paon par','Ek paon side mein uthao - 90 degree','Kutta hydrant pe jaisa motion','Slowly neeche lao','Dono taraf karo'],tip:'Outer glutes aur hips ke liye best'},
    {n:'Curtsy Lunges',bp:'Thighs',lvl:'Intermediate',ty:'Workout',g:'Weight Loss',s:3,r:12,rest:30,steps:['Seedhe khade ho','Ek paon doosre ke peeche cross karo','Lunge mein jao - namaskar jaisa','Wapis aao','Alternate karo'],tip:'Glutes aur thighs toning ke liye unique exercise'},

    {n:'Tricep Dips',bp:'Arms',lvl:'Beginner',ty:'Workout',g:'All',s:3,r:12,rest:30,steps:['Kursi ya bed ke kinare baitho','Haath kursi par rakho','Body aage le jao','Kohniya modte hue neeche jao','Wapis push karke upar aao'],tip:'Back of arms (triceps) ke liye best home exercise'},
    {n:'Arm Rotations',bp:'Arms',lvl:'Beginner',ty:'Workout',g:'Weight Loss',s:3,r:30,rest:20,steps:['Seedhe khade ho','Dono haath side mein phailao','Chote circles - forward 15','Backward circles - 15','Speed gradually badhao'],tip:'Arm toning aur shoulder warm up dono'},
    {n:'Diamond Push Ups',bp:'Arms',lvl:'Intermediate',ty:'Workout',g:'All',s:4,r:10,rest:45,steps:['Push up position','Haath ko andar rakho - diamond shape','Triceps feel karein neeche jaate waqt','Slowly upar aao'],tip:'Triceps ke liye push up ka best variation'},
    {n:'Plank to Push Up',bp:'Arms',lvl:'Intermediate',ty:'Workout',g:'All',s:3,r:10,rest:45,steps:['Forearm plank mein aao','Ek haath seedha karo','Doosra haath seedha karo - push up position','Wapis forearm par aao','Alternate arms se'],tip:'Arms + core dono ek saath train hote hain'},
    {n:'Tricep Kickbacks',bp:'Arms',lvl:'Intermediate',ty:'Workout',g:'Weight Loss',s:3,r:15,rest:30,steps:['Thoda aage jhuko','Ek haath se kisi cheez ka support lo','Doosra haath 90 degree','Arm ko peeche seedha karo','Slowly wapis lao'],tip:'Triceps isolation ke liye - form pe dhyan do'},

    {n:'Superman',bp:'Back',lvl:'Beginner',ty:'Workout',g:'All',s:3,r:12,rest:30,steps:['Pet ke bal let jao','Haath aage ki taraf','Haath aur paon dono ek saath upar uthao','2 second hold karo','Slowly neeche aao'],tip:'Lower back ke liye best - spine health improve hoti hai'},
    {n:'Cat Cow Stretch',bp:'Back',lvl:'Beginner',ty:'Workout',g:'All',s:3,r:15,rest:20,steps:['Charo haath paon par aao','Saansi lete waqt kamar neeche karo (Cow)','Saansi bahar karte waqt kamar upar karo (Cat)','Dheerey dheerey karo','Neck bhi move karo gently'],tip:'Back pain ke liye excellent - subah karo'},
    {n:'Reverse Snow Angels',bp:'Back',lvl:'Beginner',ty:'Workout',g:'All',s:3,r:12,rest:30,steps:['Pet ke bal let jao','Haath sides mein rakho','Haath upar le jao - jaise snow angel','Slowly wapis aao','Body zameen se uthao thoda'],tip:'Upper back aur shoulder dono ke liye achha'},
    {n:'Pull Up Negative',bp:'Back',lvl:'Intermediate',ty:'Workout',g:'Weight Gain',s:3,r:6,rest:60,steps:['Pull up bar pakdo - kisi bhi tarah','Jump karke top position mein jao','Bahut slowly neeche aao - 5 seconds','Yahi ek rep hai','Repeat karo'],tip:'Pull up seekhne ka best tarika - negative reps'},

    {n:'Jumping Jacks',bp:'Full Body',lvl:'Beginner',ty:'Workout',g:'Weight Loss',s:3,r:30,rest:30,steps:['Seedhe khade ho','Jump karo aur paon wide karo','Saath mein haath upar uthao','Wapis jump karo - paon paas','Haath neeche aao'],tip:'Warm up ke liye perfect - cardio bhi hoti hai'},
    {n:'Burpees',bp:'Full Body',lvl:'Intermediate',ty:'Workout',g:'Weight Loss',s:3,r:10,rest:60,steps:['Seedhe khade ho','Neeche baitho - haath zameen par','Paon peeche le jao - push up position','Push up karo (optional)','Paon wapis lao - khade ho - jump karo'],tip:'Sabse effective fat burning exercise - tough but results best'},
    {n:'High Knees',bp:'Full Body',lvl:'Beginner',ty:'Workout',g:'Weight Loss',s:3,r:30,rest:30,steps:['Seedhe khade ho','Ek ghutna chest tak uthao','Jaldi se doosra ghutna uthao','Running in place jaisa','Arms bhi move karo'],tip:'Heart rate badhane ke liye best cardio'},
    {n:'Box Steps',bp:'Full Body',lvl:'Beginner',ty:'Workout',g:'All',s:3,r:20,rest:30,steps:['Kursi ya step ke samne khade ho','Ek paon upar rakho','Doosra paon upar rakho','Pehla paon neeche','Doosra paon neeche - repeat'],tip:'Beginners ke liye cardio - joints pe kam pressure'},
    {n:'Skaters',bp:'Full Body',lvl:'Intermediate',ty:'Workout',g:'Weight Loss',s:3,r:20,rest:30,steps:['Ek paon par khade ho','Ice skater jaisa side mein jump karo','Doosre paon par land karo','Back paon ko peeche swing karo','Jaldi jaldi alternate karo'],tip:'Legs + cardio + balance - bahut effective'},

    {n:'Tadasana (Mountain Pose)',bp:'Full Body',lvl:'Beginner',ty:'Yoga',g:'All',s:1,r:60,rest:10,steps:['Seedhe khade ho - paon paas paas','Haath sides mein rakho','Poora body seedha karo','Aankhein bandh karo','Saansi par dhyan do - 60 second hold'],tip:'Posture improve hota hai - subah ka best asana'},
    {n:'Balasana (Child Pose)',bp:'Back',lvl:'Beginner',ty:'Yoga',g:'All',s:1,r:60,rest:10,steps:['Ghutno par baitho','Aage jhuko - haath seedhe aage','Maatha zameen par rakho','Aram se saansi lo','60 second ya zyada raho'],tip:'Stress relief ke liye best yoga pose - back relaxation'},
    {n:'Sukhasana (Easy Pose)',bp:'Full Body',lvl:'Beginner',ty:'Yoga',g:'All',s:1,r:120,rest:0,steps:['Zameen par sukh se baitho','Paon cross karo','Kamar seedhi rakho','Haath ghutno par','Aankhein bandh - deep breathing karo'],tip:'Meditation ke liye perfect - mind calm hota hai'},
    {n:'Vrikshasana (Tree Pose)',bp:'Legs',lvl:'Beginner',ty:'Yoga',g:'All',s:2,r:30,rest:10,steps:['Seedhe khade ho','Ek paon doosre ke ghutne par rakho','Haath namaste mein chest par','Ya haath upar le jao','30 second balance karo - dono taraf'],tip:'Balance aur concentration improve hoti hai'},
    {n:'Marjaryasana (Cat Pose)',bp:'Back',lvl:'Beginner',ty:'Yoga',g:'All',s:3,r:10,rest:15,steps:['Charo haath paon par aao','Saansi bahar karte waqt kamar upar karo','Sar neeche - chin chest par','Saansi lete waqt kamar neeche karo','Sar upar - yeh cow pose hai'],tip:'Spine flexibility ke liye - back pain mein relief'},
    {n:'Adho Mukha Svanasana (Downward Dog)',bp:'Full Body',lvl:'Beginner',ty:'Yoga',g:'All',s:3,r:30,rest:15,steps:['Charo haath paon par aao','Kamar upar uthao - V shape banao','Haath seedhe - heels zameen par','Sar haathon ke beech','30 second hold - deep breathing'],tip:'Poora body stretch hota hai - energy badhti hai'},
    {n:'Virabhadrasana (Warrior 1)',bp:'Legs',lvl:'Beginner',ty:'Yoga',g:'All',s:2,r:30,rest:15,steps:['Ek paon aage rakho - bada qadam','Peeche wala paon seedha','Ghutna aage wala 90 degree','Haath upar le jao','30 second hold - dono taraf'],tip:'Leg strength + balance + focus - complete asana'},

    {n:'Naukasana (Boat Pose)',bp:'Abs',lvl:'Intermediate',ty:'Yoga',g:'Weight Loss',s:3,r:30,rest:20,steps:['Zameen par baitho','Paon upar uthao - seedhe rakho','Haath aage seedhe rakho','V shape banao body se','30 second hold - balance karo'],tip:'Core strength ke liye best yoga - abs feel karein'},
    {n:'Trikonasana (Triangle Pose)',bp:'Full Body',lvl:'Intermediate',ty:'Yoga',g:'All',s:2,r:30,rest:15,steps:['Paon wide rakh','Ek taraf haath neeche - paon tak','Doosra haath upar - ceiling ki taraf','Dono haath ek seedhi line mein','30 second - dono taraf'],tip:'Hamstrings + side stretch - posture aur flexibility'},
    {n:'Setu Bandhasana (Bridge Pose)',bp:'Thighs',lvl:'Intermediate',ty:'Yoga',g:'All',s:3,r:30,rest:20,steps:['Peethe ke bal let jao','Ghutne mod lo - paon zameen par','Kamar uthao - upar ki taraf','Haath zameen par ya interlace karo','30 second hold - glutes squeeze'],tip:'Glutes + back + thyroid ke liye - very beneficial'},
    {n:'Bhujangasana (Cobra Pose)',bp:'Back',lvl:'Intermediate',ty:'Yoga',g:'All',s:3,r:20,rest:15,steps:['Pet ke bal let jao','Haath shoulders ke neeche','Sar aur chest upar uthao','Kamar se upar ka hissa uthao','20 second hold - back feel karein'],tip:'Back strength + flexibility - spine health excellent'},
    {n:'Ardha Matsyendrasana (Seated Twist)',bp:'Back',lvl:'Intermediate',ty:'Yoga',g:'All',s:2,r:30,rest:10,steps:['Zameen par seedhe baitho','Ek paon doosre ke ghutne ke bahar','Kamar twist karo us taraf','Ek haath peeche zameen par','30 second - dono taraf'],tip:'Spine twist - digestion improve hoti hai'},

    {n:'Sirsasana (Headstand)',bp:'Full Body',lvl:'Advanced',ty:'Yoga',g:'Weight Gain',s:1,r:60,rest:0,steps:['Pehle wall ke paas practice karo','Haath triangle banao zameen par','Sar haathon ke beech','Ghutne chest par','Slowly paon upar - wall ka support'],tip:'Brain ke liye best - blood flow improves - beginner wall se seekhe'},
    {n:'Chakrasana (Wheel Pose)',bp:'Full Body',lvl:'Advanced',ty:'Yoga',g:'All',s:3,r:20,rest:30,steps:['Peethe ke bal let jao','Haath shoulders ke paas zameen par','Paon ghutne ke paas','Ek saath push karo - upar bridge banao','20 second hold'],tip:'Full spine bend - bahut flexibility chahiye - warm up zaroor karo'},
    {n:'Mayurasana (Peacock Pose)',bp:'Full Body',lvl:'Advanced',ty:'Yoga',g:'Weight Gain',s:3,r:10,rest:60,steps:['Ghutno par baitho','Haath zameen par - anguliyaan peeche','Kohniyaan pet par','Aage jhuko - balance karo','Paon upar uthao - hold'],tip:'Core + arm strength - concentration bahut chahiye'},

    {n:'Anulom Vilom (Alternate Nostril)',bp:'Full Body',lvl:'Beginner',ty:'Yoga',g:'All',s:1,r:120,rest:0,steps:['Sukhasana mein baitho','Daaya haath - angutha aur ring finger use karo','Ek nostril bandh - doosre se saansi lo','Doosra nostril bandh - pehle se bahar nikalo','2 minute karo - din mein 2 baar'],tip:'Stress, anxiety, blood pressure ke liye - subah khaali pet best'},
    {n:'Kapalbhati Pranayama',bp:'Abs',lvl:'Beginner',ty:'Yoga',g:'Weight Loss',s:1,r:60,rest:10,steps:['Seedhe baitho','Naak se tezi se saansi bahar phenko','Pet andar karo jab bahar phenko','Saansi andar automatically aayegi','1 minute - phir rest'],tip:'Belly fat kam karne mein help - acidity mein na karo'},
    {n:'Bhramari (Humming Bee Breath)',bp:'Full Body',lvl:'Beginner',ty:'Yoga',g:'All',s:5,r:1,rest:5,steps:['Aankhein bandh karo','Haath - anguthe se kaano ko band karo','Ungliyan aankhon par halki rakho','Saansi lo',"Bahar nikaalte waqt humming sound karo - 'mmm'"],tip:'Headache, anxiety, insomnia ke liye best - raat ko bhi kar sakte ho'},

    {n:'Hamstring Stretch',bp:'Legs',lvl:'Beginner',ty:'Workout',g:'All',s:2,r:30,rest:10,steps:['Zameen par seedhe baitho','Paon seedhe aage rakho','Haath se paon pakad ne ki koshish karo','Ghutne seedhe rakho','30 second hold - dono taraf'],tip:'Workout ke baad zaroor karo - injury prevent hoti hai'},
    {n:'Quad Stretch',bp:'Thighs',lvl:'Beginner',ty:'Workout',g:'All',s:2,r:30,rest:10,steps:['Ek paon par khade ho','Doosra paon peeche pakdo - haath se','Ghutna seedha neeche','Haath se balance karo','30 second - dono taraf'],tip:'Quad muscles relax hoti hai - leg day ke baad must'},
    {n:'Chest Opener Stretch',bp:'Chest',lvl:'Beginner',ty:'Workout',g:'All',s:2,r:30,rest:10,steps:['Seedhe khade ho','Haath peeche le jao - interlock karo','Chest ko aage push karo','Chin upar','30 second hold'],tip:'Chest workout ke baad must - muscles relax hoti hain'},
    {n:'Neck Stretches',bp:'Full Body',lvl:'Beginner',ty:'Workout',g:'All',s:2,r:20,rest:10,steps:['Seedhe baitho','Sar daaye jhukao - 20 second','Sar baaye jhukao - 20 second','Sar aage jhukao - 20 second','Circles slowly - dono direction'],tip:'Office workers ke liye must - neck pain relief'},
  ],

  // ── Generate today's workout (mirrors WorkoutPlan.java's day-split + pool logic) ──
    getTodayWorkout(profile, analysis, prefs) {
    const level = prefs.level || 'Beginner';
    const type  = prefs.type  || 'Workout';
    const goal  = (analysis && analysis.goal) || 'All';
    const dayName = new Date().toLocaleDateString('en-US', { weekday: 'long' });

    if (profile && profile.lifeStage === 'Pregnancy') { // SAFETY: explicit field only
      return {
        isRestDay: false,
        isPregnancySafety: true,
        bodyPart: null,
        dayName,
        exercises: [],
        safetyMessage: 'Pregnancy profile detected. A generic workout is not shown here — exercise during pregnancy should be planned with a qualified healthcare professional.'
      };
    }

    const today   = new Date().toISOString().split('T')[0];
    const split   = type === 'Yoga' ? this.YOGA_SPLIT : this.WORKOUT_SPLIT;
    const bodyPart = split[dayName] || 'Full Body';

    if (bodyPart === 'REST') {
      return { isRestDay: true, bodyPart: null, dayName, exercises: [] };
    }
    const matchesFilter = (ex, requireBodyPart, requireGoal) => {
      if (ex.lvl !== level || ex.ty !== type) return false;
      if (requireBodyPart && ex.bp !== bodyPart && ex.bp !== 'Full Body') return false;
      if (requireGoal && ex.g !== goal && ex.g !== 'All') return false;
      return true;
    };

    // Tiered fallback so a short list always still gets exercises
    let pool = this.WORKOUT_DATA.filter(ex => matchesFilter(ex, true, true));
    if (pool.length < 4) pool = pool.concat(this.WORKOUT_DATA.filter(ex => matchesFilter(ex, true, false) && !pool.includes(ex)));
    if (pool.length < 4) pool = pool.concat(this.WORKOUT_DATA.filter(ex => ex.lvl === level && ex.ty === type && !pool.includes(ex)));

    // Dedupe by generated id, shuffle, cap at 6
    const seen = new Set();
    const deduped = [];
    pool.forEach(ex => {
      const id = this.getExerciseId(ex);
      if (!seen.has(id)) { seen.add(id); deduped.push(ex); }
    });
    const shuffled = deduped.sort(() => Math.random() - 0.5);
    const chosen = shuffled.slice(0, 6);

    const exercises = chosen.map(ex => {
      const timePerSet = ex.ty === 'Yoga' ? ex.r : ex.r * 3;
      const seconds = ex.s * timePerSet + ex.s * ex.rest;
      return {
        id: this.getExerciseId(ex),
        name: ex.n, bodyPart: ex.bp, level: ex.lvl, type: ex.ty, goal: ex.g,
        sets: ex.s, reps: ex.r, rest: ex.rest, steps: ex.steps, tip: ex.tip,
        seconds
      };
    });

    const result = { isRestDay: false, bodyPart, dayName, exercises };

const cacheKey = `sf_workout_plan_${today}_${level}_${type}`;

localStorage.setItem(cacheKey, JSON.stringify(result));
return result;
  },

  // ── Achievements ──────────────────────────────────────
  getAchievements() {
    const wh = this.getWeightHistory();
    const sh = this.getStepHistory();
    const earned = [], locked = [];

    earned.push({ icon:'🌿', title:'Profile Created',  desc:'Welcome to SmartFit!' });

    if (wh.length >= 1)
      earned.push({ icon:'⚖️', title:'First Weigh-In', desc:'Tracking started!' });
    else
      locked.push({ icon:'⚖️', title:'First Weigh-In', desc:'Log your first weight' });

    if (wh.length >= 7)
      earned.push({ icon:'🏆', title:'Week Warrior', desc:'7 days of tracking!' });
    else
      locked.push({ icon:'🏆', title:'Week Warrior', desc:`Track for 7 days (${wh.length}/7)` });

    if (wh.length >= 30)
      earned.push({ icon:'👑', title:'Monthly Master', desc:'30 days consistent!' });
    else
      locked.push({ icon:'👑', title:'Monthly Master', desc:`Track 30 days (${wh.length}/30)` });

    if (sh.length >= 1)
      earned.push({ icon:'👣', title:'First Steps', desc:'Step tracking started!' });
    else
      locked.push({ icon:'👣', title:'First Steps', desc:'Log your first step count' });

    const goodDays = sh.filter(s => s.steps >= 8000).length;
    if (goodDays >= 7)
      earned.push({ icon:'🔥', title:'Step Streak', desc:'7 days of 8000+ steps!' });
    else
      locked.push({ icon:'🔥', title:'Step Streak', desc:`8000+ steps for 7 days (${goodDays}/7)` });

    const profile = this.loadProfile();
    if (profile) {
      const bmi = this.calculateBMI(+profile.weight, +profile.height);
      if (bmi >= 18.5 && bmi < 25)
        earned.push({ icon:'💪', title:'Healthy Range', desc:'BMI in normal range!' });
      else
        locked.push({ icon:'💪', title:'Healthy Range', desc:'Reach BMI 18.5-24.9' });
    }

    return { earned, locked };
  },

  // ── Date helpers ──────────────────────────────────────
  getTodayDisplay() {
    return new Date().toLocaleDateString('en-IN', {
      weekday:'long', day:'numeric', month:'long', year:'numeric'
    });
  },

  getDayName() {
    return new Date().toLocaleDateString('en-IN', { weekday:'long' }).toUpperCase();
  },

  youtubeSearch(query) {
    return 'https://www.youtube.com/results?search_query=' + encodeURIComponent(query + ' recipe');
  }
};