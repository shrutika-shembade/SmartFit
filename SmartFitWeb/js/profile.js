// ======================================================
// profile.js - FIXED VERSION
// Uses localStorage (not sessionStorage)
// Live BMI + full analysis preview
// ======================================================

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

  // Pre-fill if profile already exists
  const existing = SmartFit.loadProfile();
  if (existing) {
        const fields = ['age','gender','height','weight','occupation','lifeStage',
                    'workingHours','workoutTime','sleepTime','wakeTime',
                    'region','budget','allergy','skinType']; // FIX: added skinType
    fields.forEach(f => {
      const el = document.getElementById(f) || document.querySelector(`[name="${f}"]`);
      if (el && existing[f]) el.value = existing[f];
    });
    if (existing.lifestyle) {
      const r = document.querySelector(`input[name="lifestyle"][value="${existing.lifestyle}"]`);
      if (r) r.checked = true;
    }
    if (existing.foodPreference) {
      const r = document.querySelector(`input[name="foodPreference"][value="${existing.foodPreference}"]`);
      if (r) r.checked = true;
    }
    if (existing.healthIssue && existing.healthIssue !== 'none') {
      existing.healthIssue.split(',').forEach(v => {
        const cb = document.querySelector(`input[name="healthIssues"][value="${v.trim()}"]`);
        if (cb) cb.checked = true;
      });
    }
    updatePreview(); // FIX: run preview once on load for returning users
  }

   // Live preview
  function updatePreview() {
    const h  = parseFloat(document.getElementById('height')?.value);
    const w  = parseFloat(document.getElementById('weight')?.value);
    const a  = parseInt(document.getElementById('age')?.value);
    const g  = document.getElementById('gender')?.value;
    const ls = document.querySelector('input[name="lifestyle"]:checked')?.value || 'Moderate';
    const lifeStage = document.getElementById('lifeStage')?.value || '';

    if (!h || !w) return;

    const bmi      = SmartFit.calculateBMI(w, h);
    const category = SmartFit.getBMICategory(bmi);
    const goal     = SmartFit.getGoal(bmi, lifeStage); // SAFETY: life-stage aware
    const isPregnancySafety = goal === SmartFit.PREGNANCY_SAFETY_GOAL;

    setText('prevBMI',   bmi.toFixed(2));
    setText('prevCat',   category);
    setText('prevGoal',  goal);

    if (a && g) {
      const bmr    = SmartFit.calculateBMR(w, h, a, g);
      const tdee   = SmartFit.calculateTDEE(bmr, ls);
      const cal    = SmartFit.calculateCalories(tdee, goal);
      const pro    = isPregnancySafety ? null : SmartFit.calculateProtein(w, goal);
      const ideal  = SmartFit.calculateIdealWeight(h, g);
      const bf     = SmartFit.calculateBodyFat(bmi, a, g);
      const water  = SmartFit.calculateWater(w);

      setText('prevCal',   cal === null ? 'See safety note' : cal + ' kcal');
      setText('prevPro',   pro === null ? '—' : pro + 'g');
      setText('prevIdeal', ideal + ' kg');
      setText('prevBF',    bf + '%');
      setText('prevWater', Math.round(water / 1000 * 10) / 10 + 'L');
    }
  }

  function setText(id, val) {
    const el = document.getElementById(id);
    if (el) el.textContent = val;
  }

  ['height','weight','age','gender'].forEach(id => {
    document.getElementById(id)?.addEventListener('input', updatePreview);
  });
  document.querySelectorAll('input[name="lifestyle"]').forEach(r => {
    r.addEventListener('change', updatePreview);
  });

  // Form submit → save to localStorage → go to dashboard
  document.getElementById('profileForm')?.addEventListener('submit', function(e) {
    e.preventDefault();

    const issues = [...document.querySelectorAll('input[name="healthIssues"]:checked')]
      .map(cb => cb.value).join(',') || 'none';

       const profile = {
      age:           parseInt(document.getElementById('age').value),
      gender:        document.getElementById('gender').value,
      height:        parseFloat(document.getElementById('height').value),
      weight:        parseFloat(document.getElementById('weight').value),
      occupation:    document.getElementById('occupation').value || 'Student',
      lifeStage:     document.getElementById('lifeStage')?.value || 'Adult', // SAFETY: explicit, never inferred
      lifestyle:     document.querySelector('input[name="lifestyle"]:checked')?.value || 'Moderate',
      workingHours:  document.getElementById('workingHours')?.value || '8',
      workoutTime:   document.getElementById('workoutTime').value || 'morning',
      sleepTime:     document.getElementById('sleepTime')?.value || '23',
      wakeTime:      document.getElementById('wakeTime')?.value || '7',
      foodPreference:document.querySelector('input[name="foodPreference"]:checked')?.value || 'NonVeg',
      region:        document.getElementById('region').value,
      budget:        document.getElementById('budget').value,
      healthIssue:   issues,
      allergy:       document.getElementById('allergy')?.value || '',
      skinType:      document.getElementById('skinType')?.value || 'oily', // FIX: was missing entirely
    };

    SmartFit.saveProfile(profile);

    // Clear old plan so new one generates for this profile
    localStorage.removeItem('sf_diet_plan');
    localStorage.removeItem('sf_diet_date');

    window.location.href = 'dashboard.html';
  });
});