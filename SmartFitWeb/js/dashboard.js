// ======================================================
// dashboard.js - COMPLETE FIXED VERSION
// Reads all data from localStorage via SmartFit
// No hardcoded values - everything is dynamic
// ======================================================

let SF_TARGET_CAL = 0; // set on load, used by toggleMeal()

document.addEventListener('DOMContentLoaded', function () {

  // ── Guard: smartfit-data.js didn't load ───────────────
  if (typeof SmartFit === 'undefined') {
    document.body.innerHTML =
      '<div style="max-width:640px;margin:4rem auto;padding:2rem;font-family:sans-serif;' +
      'background:#fff3f3;border:2px solid #c0392b;border-radius:12px;color:#2b2b2b">' +
      '<h2 style="color:#c0392b;margin-bottom:.6rem">⚠️ Could not load SmartFit</h2>' +
      '<p style="margin-bottom:.8rem">This page could not find <code>js/smartfit-data.js</code>.</p>' +
      '<p style="margin-bottom:.4rem">Check that:</p>' +
      '<ul style="margin-left:1.2rem;line-height:1.7">' +
      '<li>The <code>js</code> folder sits directly next to this HTML file</li>' +
      '<li>The file inside it is named exactly <code>smartfit-data.js</code> (lowercase, with the hyphen)</li>' +
      '<li>You are viewing this through a local server (e.g. VS Code "Live Server"), not by double-clicking the HTML file</li>' +
      '</ul></div>';
    return;
  }

  // ── Require profile ──────────────────────────────────
  const profile = SmartFit.requireProfile();
  if (!profile) return;

  const analysis = SmartFit.getAnalysis(profile);
  const plan     = SmartFit.getTodayPlan(profile, analysis);
  SF_TARGET_CAL  = analysis.calories;

  // ── Greeting + Date ───────────────────────────────────
  const hour  = new Date().getHours();
  const greet = hour < 12 ? 'Good morning' : hour < 17 ? 'Good afternoon' : 'Good evening';

  set('dashDate',  SmartFit.getTodayDisplay());
  set('dashGreet', greet + ', ' + (profile.name || 'SmartFitter') + '! 👋');

  // ── BMI Chip ──────────────────────────────────────────
  set('dashBMI', analysis.bmi);
  set('dashCat', analysis.category);

  // Color code BMI chip
  const catEl = document.getElementById('dashCat');
  if (catEl) {
    catEl.className = 'bmi-chip-cat';
    if (analysis.category === 'Normal')      catEl.style.background = '#2D6A4F';
    else if (analysis.category === 'Underweight') catEl.style.background = '#E9C46A';
    else catEl.style.background = '#E76F51';
  }

  // ── Stat Cards ────────────────────────────────────────
  set('calVal',   analysis.calories + ' kcal');
  set('waterVal', analysis.glasses  + ' glasses');

  // Steps from history
  const todayStr   = new Date().toISOString().split('T')[0];
  const stepHist   = SmartFit.getStepHistory();
  const todayStep  = stepHist.find(s => s.date === todayStr);
  const stepsCount = todayStep ? todayStep.steps : 0;
  set('stepsVal', stepsCount.toLocaleString());

  // Sleep from profile
  const sleepT = parseInt(profile.sleepTime)  || 23;
  const wakeT  = parseInt(profile.wakeTime)   || 7;
  const sleepH = ((wakeT - sleepT + 24) % 24);
  set('sleepVal', sleepH + ' hrs');

  // Animate bars after 300ms
  setTimeout(() => {
    const waterPct = Math.min(Math.round(SmartFit.getTodayWater() / analysis.glasses * 100), 100);
    const stepPct  = Math.min(Math.round(stepsCount / 8000 * 100), 100);
    const sleepPct = Math.min(Math.round(sleepH / 9 * 100), 100);

    setBarWidth('waterBar', waterPct || 0);
    setBarWidth('stepsBar', stepPct);
    setBarWidth('sleepBar', sleepPct);
    // calBar is driven by meals actually checked off — see updateEatenSummary()
  }, 400);

  // ── Body Analysis Row ─────────────────────────────────
  set('abmr',     analysis.bmr + ' kcal');
  set('abodyFat', analysis.bodyFat + '%');
  set('aideal',   analysis.idealW + ' kg');
  set('albm',     analysis.lbm    + ' kg');
  set('agoal',    analysis.goal);
  set('aprotein', analysis.protein + 'g');
  set('acarbs',   analysis.carbs   + 'g');
  set('afat',     analysis.fat     + 'g');

  // ── Meals List ────────────────────────────────────────
  const mealList = document.getElementById('mealList');
  if (mealList && plan) {
    const wt = profile.workoutTime || 'morning';
    const meals = wt === 'morning'
      ? [
          { key:'detox',       label:'🌿 Morning Detox', meal: plan.detox,       defaultDone: true  },
          { key:'preWorkout',  label:'⚡ Pre Workout',   meal: plan.preWorkout,  defaultDone: true  },
          { key:'breakfast',   label:'🍳 Breakfast',     meal: plan.breakfast,   defaultDone: false },
          { key:'lunch',       label:'🍛 Lunch',         meal: plan.lunch,       defaultDone: false },
          { key:'snack',       label:'🥗 Evening Snack', meal: plan.snack,       defaultDone: false },
          { key:'dinner',      label:'🌙 Dinner',        meal: plan.dinner,      defaultDone: false },
        ]
      : [
          { key:'detox',       label:'🌿 Morning Detox', meal: plan.detox,       defaultDone: true  },
          { key:'breakfast',   label:'🍳 Breakfast',     meal: plan.breakfast,   defaultDone: true  },
          { key:'lunch',       label:'🍛 Lunch',         meal: plan.lunch,       defaultDone: false },
          { key:'preWorkout',  label:'⚡ Pre Workout',   meal: plan.preWorkout,  defaultDone: false },
          { key:'postWorkout', label:'💪 Post Workout',  meal: plan.postWorkout, defaultDone: false },
          { key:'dinner',      label:'🌙 Dinner',        meal: plan.dinner,      defaultDone: false },
        ];

    const savedMealState = SmartFit.getMealsState();

    mealList.innerHTML = '';
    meals.forEach(m => {
      if (!m.meal) return;
      const isDone = savedMealState.hasOwnProperty(m.key) ? savedMealState[m.key] : m.defaultDone;
      const li = document.createElement('li');
      li.className = 'meal-item' + (isDone ? ' done' : '');
      li.dataset.mealKey = m.key;
      li.dataset.cal     = m.meal.cal;
      li.dataset.protein = m.meal.p;
      li.innerHTML = `
        <div class="meal-check" onclick="toggleMeal(this)">${isDone ? '✓' : '○'}</div>
        <div class="meal-info">
          <div class="meal-time">${m.label}</div>
          <div class="meal-name">${m.meal.name}</div>
        </div>
        <div class="meal-cal">${m.meal.cal} kcal</div>`;
      mealList.appendChild(li);
    });
  }

  // ── Water Circles ────────────────────────────────────
  buildWaterCircles(analysis.glasses);

  // ── Weight Chart ─────────────────────────────────────
  buildWeightChart();

  // ── Reminders ─────────────────────────────────────────
  buildReminders(profile);

  // ── Achievements ──────────────────────────────────────
  buildAchievements();

  // ── Nutrition Summary (now driven by meals actually checked off) ──
  updateEatenSummary();
});

// ── Nutrition summary (eaten calories/protein vs target) ──────────
function updateEatenSummary() {
  const doneItems = document.querySelectorAll('#mealList .meal-item.done');
  let eatenCal = 0, eatenProtein = 0;
  doneItems.forEach(li => {
    eatenCal     += parseInt(li.dataset.cal, 10)     || 0;
    eatenProtein += parseInt(li.dataset.protein, 10) || 0;
  });

  set('sumCal', eatenCal + ' kcal eaten');
  set('sumPro', eatenProtein + 'g protein');
  set('sumCalTarget', 'Target: ' + SF_TARGET_CAL + ' kcal');

  const calPct = SF_TARGET_CAL ? Math.min(Math.round(eatenCal / SF_TARGET_CAL * 100), 100) : 0;
  setBarWidth('calBar', calPct);

  const diff = SF_TARGET_CAL - eatenCal;
  const noteEl = document.getElementById('calNote');
  if (noteEl) {
    if (diff > 200)       noteEl.textContent = '⚠️ ' + diff + ' kcal left in your budget. Add a snack like roasted chana or makhana.';
    else if (diff < -200) noteEl.textContent = '⚠️ ' + Math.abs(diff) + ' kcal over target. Go easy on the next meal.';
    else                  noteEl.textContent = '✅ On target! Great nutrition balance today.';
  }
}

// ── Water circles builder ─────────────────────────────
function buildWaterCircles(totalGlasses) {
  const waterDiv = document.getElementById('waterCircles');
  if (!waterDiv) return;
  const drank = SmartFit.getTodayWater();
  set('waterCount', drank + ' / ' + totalGlasses + ' glasses');
  waterDiv.innerHTML = '';

  for (let i = 0; i < Math.min(totalGlasses, 24); i++) {
    const g = document.createElement('div');
    g.className = 'water-glass' + (i < drank ? ' filled' : '');
    g.textContent = i < drank ? '💧' : '○';
    g.addEventListener('click', function() {
      const nowFilled = waterDiv.querySelectorAll('.filled').length;
      const newCount  = g.classList.contains('filled') ? nowFilled - 1 : nowFilled + 1;
      const clamped   = Math.max(0, Math.min(newCount, totalGlasses));
      SmartFit.saveWaterEntry(clamped);
      set('waterCount', clamped + ' / ' + totalGlasses + ' glasses');
      setBarWidth('waterBar', Math.min(Math.round(clamped / totalGlasses * 100), 100));
      waterDiv.querySelectorAll('.water-glass').forEach((el, idx) => {
        el.classList.toggle('filled', idx < clamped);
        el.textContent = idx < clamped ? '💧' : '○';
      });
    });
    waterDiv.appendChild(g);
  }
}

// ── Weight chart builder ──────────────────────────────
function buildWeightChart() {
  const chart  = document.getElementById('weightChart');
  const meta   = document.getElementById('weightMeta');
  const status = document.getElementById('weightStatus');
  if (!chart) return;

  const wHist = SmartFit.getWeightHistory();
  chart.innerHTML = '';

  if (!wHist.length) {
    chart.innerHTML = '<p style="opacity:.5;font-size:.85rem;padding:1rem 0;text-align:center">No weight data yet.<br>Click "Log Weight" below to start.</p>';
    if (meta)   meta.textContent   = 'Start tracking your weight';
    if (status) status.textContent = '--';
    return;
  }

  const recent = wHist.slice(-7);
  const min    = Math.min(...recent.map(e => e.weight));
  const max    = Math.max(...recent.map(e => e.weight));
  const range  = (max - min) || 1;

  recent.forEach((e, i) => {
    const barWrap = document.createElement('div');
    barWrap.style.cssText = 'display:flex;flex-direction:column;align-items:center;flex:1;gap:4px';

    const bar = document.createElement('div');
    bar.className = 'w-bar' + (i === recent.length - 1 ? ' today' : '');
    bar.style.height = (15 + ((e.weight - min) / range) * 85) + '%';
    bar.title = e.date + ': ' + e.weight + ' kg';

    const lbl = document.createElement('span');
    lbl.style.cssText = 'font-size:.6rem;opacity:.6;font-family:monospace';
    lbl.textContent = e.weight;

    barWrap.appendChild(bar);
    barWrap.appendChild(lbl);
    chart.appendChild(barWrap);
  });

  const latest = wHist[wHist.length - 1];
  const first  = wHist[0];
  const diff   = +(latest.weight - first.weight).toFixed(1);
  if (meta)   meta.textContent   = latest.weight + ' kg — current weight';
  if (status) {
    status.textContent = diff > 0 ? '▲ +' + diff + ' kg' : diff < 0 ? '▼ ' + diff + ' kg' : 'Stable';
    status.className   = 'badge ' + (diff > 0 ? 'badge-high' : diff < 0 ? 'badge-low' : 'badge-medium');
  }
}

// ── Reminders builder ─────────────────────────────────
function buildReminders(profile) {
  const rl = document.getElementById('reminderList');
  if (!rl) return;
  const wt = profile.workoutTime || 'morning';
  const r  = wt === 'morning'
    ? ['06:30 — Detox drink','07:00 — Pre workout','08:00 — Breakfast','11:00 — Water break','13:00 — Lunch','16:30 — Snack','20:00 — Dinner','22:00 — Sleep time']
    : ['06:30 — Detox drink','08:00 — Breakfast','11:00 — Water break','13:00 — Lunch','17:00 — Pre workout','19:30 — Post workout meal','21:00 — Dinner'];
  rl.innerHTML = '';
  r.forEach(text => {
    const li = document.createElement('li');
    li.className = 'reminder-item';
    li.textContent = text;
    rl.appendChild(li);
  });
}

// ── Achievements builder ──────────────────────────────
function buildAchievements() {
  const strip = document.getElementById('achieveStrip');
  if (!strip) return;
  const { earned, locked } = SmartFit.getAchievements();

  set('earnedCount', earned.length + ' earned');

  strip.innerHTML = '';
  earned.forEach(a => {
    const el = document.createElement('div');
    el.className = 'badge-item earned';
    el.innerHTML = `<span>${a.icon}</span> ${a.title}`;
    el.title = a.desc;
    strip.appendChild(el);
  });
  locked.slice(0, 2).forEach(a => {
    const el = document.createElement('div');
    el.className = 'badge-item locked';
    el.innerHTML = `<span style="filter:grayscale(1)">${a.icon}</span> ${a.title}`;
    el.title = a.desc;
    strip.appendChild(el);
  });
  if (!earned.length && !locked.length) {
    strip.innerHTML = '<p style="opacity:.5;font-size:.85rem">Complete daily tasks to earn badges!</p>';
  }
}

// ── Helpers ──────────────────────────────────────────
function set(id, val) {
  const el = document.getElementById(id);
  if (el) el.textContent = val;
}

function setBarWidth(id, pct) {
  const el = document.getElementById(id);
  if (el) el.style.width = Math.max(0, Math.min(pct, 100)) + '%';
}

function toggleMeal(el) {
  const li = el.parentElement;
  li.classList.toggle('done');
  el.textContent = li.classList.contains('done') ? '✓' : '○';

  SmartFit.saveMealDone(li.dataset.mealKey, li.classList.contains('done'));
  updateEatenSummary();
}

// ── Global functions called by buttons ───────────────
function logWeight() {
  const w = prompt('Enter your current weight (kg):\nExample: 55.5');
  if (!w || isNaN(parseFloat(w))) {
    alert('Please enter a valid number. Example: 55.5');
    return;
  }
  const weight = parseFloat(w);
  if (weight < 20 || weight > 300) {
    alert('Please enter a realistic weight between 20-300 kg');
    return;
  }
  SmartFit.saveWeightEntry(weight);
  alert('✅ Weight ' + weight + ' kg saved!');
  location.reload();
}

function logSteps() {
  const s = prompt('Enter today\'s step count:\nExample: 8500');
  if (!s || isNaN(parseInt(s))) {
    alert('Please enter a valid number. Example: 8500');
    return;
  }
  const steps = parseInt(s);
  if (steps < 0 || steps > 100000) {
    alert('Please enter a realistic step count');
    return;
  }
  SmartFit.saveStepEntry(steps);
  alert('✅ ' + steps.toLocaleString() + ' steps saved!');
  location.reload();
}