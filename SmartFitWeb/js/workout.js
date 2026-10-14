// ======================================================
// workout.js - PHASE 1 + PHASE 2 + PHASE 3 (Favorites fix + Video links)
// Phase 1: personalized daily generation (unchanged from before)
// Phase 2: Explore Exercises - category browsing + search,
//          kept fully separate from the timer/completion
//          system, which only ever applies to TODAY'S plan.
// Phase 3: Favorites (now backed by SmartFit.getFavorites /
//          toggleFavorite) + YouTube tutorial links on every
//          exercise card and in the Explore detail modal.
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
      '<li>The file inside it is named exactly <code>smartfit-data.js</code></li>' +
      '</ul></div>';
    return;
  }

  const profile  = SmartFit.loadProfile();
  const analysis = profile ? SmartFit.getAnalysis(profile) : null;

  if (profile) {
    const day = SmartFit.getDayName();
    const el  = document.getElementById('workoutDayLabel');
    if (el) el.textContent = day + ' · ' + (profile.workoutTime || 'morning').toUpperCase();
  }

  // ── Inject the CSS both Phase 1 and Phase 2 need ──────
  const style = document.createElement('style');
  style.textContent = `
    .exercise-card.completed { border: 2px solid var(--forest-dark); background: #eef6f0; }
    .exercise-card.completed .ex-icon { background: var(--forest-dark); color: var(--cream); }
    .ex-done-badge {
      display: inline-block; font-size: .74rem; font-weight: 700;
      color: var(--forest-mid); margin-top: .4rem; cursor: pointer; user-select: none;
    }
    .exercise-card.completed .ex-done-badge { color: var(--forest-dark); }
    .workout-complete-banner {
      display: none; background: var(--forest-dark); color: var(--cream);
      text-align: center; font-weight: 700; padding: .8rem;
      border-radius: var(--radius-md); margin-top: var(--space-sm);
    }
    .workout-complete-banner.show { display: block; }
    .rest-day-card {
      background: var(--cream-deep); border-radius: var(--radius-md);
      padding: var(--space-lg); text-align: center;
    }
    .rest-day-card h3 { margin-bottom: .5rem; }

    /* Phase 2 (premium redesign): visual Explore Exercises */
    @keyframes eeFadeUp {
      from { opacity: 0; transform: translateY(14px); }
      to   { opacity: 1; transform: translateY(0); }
    }
    #exploreSection { position: relative; }
    .explore-intro { margin-bottom: var(--space-md); }
    .explore-intro .eyebrow { display: block; margin-bottom: .3rem; }
    .explore-intro h2 { font-size: clamp(1.3rem, 2.4vw, 1.7rem); }

    .explore-search-wrap { position: relative; margin-bottom: var(--space-md); }
    .explore-search-wrap::before {
      content: '🔍'; position: absolute; left: 1.2rem; top: 50%; transform: translateY(-50%);
      font-size: .95rem; opacity: .5; pointer-events: none;
    }
    .explore-search {
      width: 100%; padding: 1rem 1.3rem 1rem 2.8rem;
      border: 2px solid transparent; border-radius: var(--radius-full);
      font-family: var(--font-body); font-size: .96rem;
      background: var(--white); box-shadow: 0 2px 14px rgba(27,67,50,.07);
      transition: box-shadow .25s ease, border-color .25s ease;
    }
    .explore-search::placeholder { color: rgba(40,54,24,.4); }
    .explore-search:focus {
      outline: none; border-color: var(--forest-mid);
      box-shadow: 0 4px 22px rgba(45,106,79,.18);
    }

    .explore-toolbar {
      display: flex; align-items: center; justify-content: space-between;
      gap: var(--space-sm); margin-bottom: var(--space-md); flex-wrap: wrap;
      min-height: 40px;
    }
    .explore-heading { font-size: .92rem; opacity: .65; font-weight: 500; }
    .explore-back-btn {
      display: none; align-items: center; gap: .4rem;
      background: var(--white); border: 1px solid rgba(27,67,50,.14); border-radius: 99px;
      padding: .55rem 1.1rem .55rem .9rem; font-family: var(--font-body); font-weight: 700;
      font-size: .82rem; color: var(--forest-dark); cursor: pointer;
      box-shadow: 0 2px 10px rgba(27,67,50,.06);
      transition: transform .2s ease, box-shadow .2s ease, background .2s ease;
    }
    .explore-back-btn::before { content: '←'; transition: transform .2s ease; }
    .explore-back-btn:hover { background: var(--forest-dark); color: var(--cream); transform: translateX(-2px); box-shadow: 0 4px 14px rgba(27,67,50,.18); }
    .explore-back-btn:hover::before { transform: translateX(-3px); }

    .explore-viewall-btn {
      display: inline-flex; align-items: center; gap: .4rem;
      background: linear-gradient(135deg, var(--forest-dark), var(--forest-mid));
      border: none; color: var(--cream);
      border-radius: 99px; padding: .8rem 1.6rem; font-family: var(--font-body);
      font-weight: 700; font-size: .86rem; cursor: pointer;
      box-shadow: 0 6px 18px rgba(27,67,50,.22);
      transition: transform .2s ease, box-shadow .2s ease; margin-top: var(--space-sm);
    }
    .explore-viewall-btn::after { content: '→'; transition: transform .2s ease; }
    .explore-viewall-btn:hover { transform: translateY(-2px); box-shadow: 0 10px 26px rgba(27,67,50,.3); }
    .explore-viewall-btn:hover::after { transform: translateX(3px); }

    .explore-empty {
      opacity: .6; text-align: center; padding: 3rem 1rem; font-size: .95rem;
      animation: eeFadeUp .35s ease both;
    }

    .browse-tags { display: flex; gap: .35rem; margin: .5rem 0 .4rem; flex-wrap: wrap; }
    .browse-tag {
      font-family: var(--font-mono); font-size: .64rem; font-weight: 700;
      padding: .22rem .6rem; border-radius: 99px; letter-spacing: .02em;
    }
    .browse-tag.lvl.beg { background: #d8f3dc; color: #1b4332; }
    .browse-tag.lvl.int { background: var(--turmeric-light); color: var(--ink); }
    .browse-tag.lvl.adv { background: #ffd9d1; color: #b8361f; }
    .browse-tag.ty  { background: #e3f2fd; color: #0d47a1; }

    /* Category grid */
    .ee-cat-grid {
      display: grid; grid-template-columns: repeat(3, 1fr); gap: var(--space-md);
    }
    @media (max-width: 900px) { .ee-cat-grid { grid-template-columns: repeat(2, 1fr); } }
    @media (max-width: 560px) { .ee-cat-grid { grid-template-columns: 1fr 1fr; gap: .7rem; } }

    .ee-cat-card {
      position: relative; display: flex; flex-direction: column; text-align: left;
      cursor: pointer; background: var(--white); border: none;
      border-radius: 22px; overflow: hidden; padding: 0; font-family: var(--font-body);
      box-shadow: 0 4px 16px rgba(27,67,50,.08);
      transition: transform .35s cubic-bezier(.2,.8,.2,1), box-shadow .35s ease;
      animation: eeFadeUp .45s cubic-bezier(.2,.8,.2,1) both;
    }
    .ee-cat-card:hover, .ee-cat-card:focus-visible {
      transform: translateY(-8px) scale(1.02);
      box-shadow: 0 20px 40px -12px rgba(27,67,50,.35);
      outline: none;
    }
    .ee-cat-card:active { transform: translateY(-4px) scale(1.005); }
    .ee-cat-card:hover .ee-cat-visual span { transform: scale(1.2) rotate(-4deg); }
    .ee-cat-card:hover .ee-cat-arrow { transform: translateX(4px); opacity: 1; }

    .ee-cat-visual {
      position: relative; aspect-ratio: 1.4 / 1; display: flex;
      align-items: center; justify-content: center; overflow: hidden;
    }
    .ee-cat-visual::before {
      content: ''; position: absolute; inset: 0;
      background-image: radial-gradient(circle at 22px 22px, rgba(255,255,255,.35) 2px, transparent 2.5px);
      background-size: 26px 26px; opacity: .5;
    }
    .ee-cat-visual::after {
      content: ''; position: absolute; width: 130%; height: 130%; border-radius: 50%;
      background: radial-gradient(circle, rgba(255,255,255,.25) 0%, transparent 65%);
      top: -35%; left: -15%;
    }
    .ee-cat-visual span {
      position: relative; z-index: 1; font-size: 2.7rem; display: inline-block;
      filter: drop-shadow(0 4px 10px rgba(0,0,0,.18));
      transition: transform .35s cubic-bezier(.2,.8,.2,1);
    }
    .ee-cat-count-badge {
      position: absolute; top: .6rem; right: .6rem; z-index: 2;
      font-family: var(--font-mono); font-size: .64rem; font-weight: 700; color: var(--cream);
      background: rgba(27,67,50,.35); backdrop-filter: blur(6px);
      padding: .25rem .55rem; border-radius: 99px; border: 1px solid rgba(255,255,255,.25);
    }
    .ee-cat-body { padding: var(--space-sm) var(--space-md) 1.1rem; position: relative; }
    .ee-cat-body h4 {
      font-family: var(--font-display); font-weight: 700; font-size: 1.02rem;
      color: var(--forest-dark); margin-bottom: .25rem; letter-spacing: 0;
    }
    .ee-cat-body p { font-size: .8rem; opacity: .65; line-height: 1.4; padding-right: 1.2rem; }
    .ee-cat-arrow {
      position: absolute; right: var(--space-md); bottom: 1.1rem;
      font-size: .95rem; color: var(--forest-mid); opacity: .45;
      transition: transform .25s ease, opacity .25s ease;
    }

    /* Tint palette — richer multi-stop gradients using existing brand colors */
    .ee-t1 { background: linear-gradient(150deg, var(--forest-light) 0%, var(--forest-mid) 55%, var(--forest-dark) 100%); }
    .ee-t2 { background: linear-gradient(150deg, var(--turmeric-light) 0%, var(--turmeric) 60%, #b9793f 100%); }
    .ee-t3 { background: linear-gradient(150deg, #f3a68a 0%, var(--coral) 60%, #c94a2c 100%); }
    .ee-t4 { background: linear-gradient(150deg, var(--forest-mid) 0%, var(--forest-dark) 65%, #0e2a1d 100%); }
    .ee-t5 { background: linear-gradient(150deg, var(--turmeric-light) 0%, var(--forest-light) 60%, var(--forest-mid) 100%); }

    /* Exercise result grid */
    .ee-result-grid {
      display: grid; grid-template-columns: repeat(3, 1fr); gap: var(--space-md);
    }
    @media (max-width: 900px) { .ee-result-grid { grid-template-columns: repeat(2, 1fr); } }
    @media (max-width: 560px) { .ee-result-grid { grid-template-columns: 1fr; } }

    .ee-exercise-card {
      background: var(--white); border-radius: 18px; overflow: hidden;
      box-shadow: 0 3px 14px rgba(27,67,50,.08);
      transition: transform .25s cubic-bezier(.2,.8,.2,1), box-shadow .25s ease;
      animation: eeFadeUp .4s cubic-bezier(.2,.8,.2,1) both;
    }
    .ee-exercise-card:hover { transform: translateY(-5px); box-shadow: 0 16px 32px -10px rgba(27,67,50,.25); }
    .ee-exercise-card:hover .ee-exercise-visual span { transform: scale(1.12); }

    .ee-exercise-visual {
      position: relative; height: 88px; display: flex;
      align-items: center; justify-content: center; overflow: hidden;
    }
    .ee-exercise-visual::before {
      content: ''; position: absolute; inset: 0;
      background: linear-gradient(180deg, rgba(255,255,255,0) 0%, rgba(0,0,0,.12) 100%);
    }
    .ee-exercise-visual span {
      position: relative; z-index: 1; font-size: 1.9rem;
      filter: drop-shadow(0 3px 8px rgba(0,0,0,.2));
      transition: transform .3s ease;
    }
    .ee-exercise-body { padding: .9rem var(--space-md) var(--space-md); }
    .ee-exercise-body h4 { margin-bottom: .15rem; font-size: 1rem; }

    .ee-stat-row { display: flex; gap: .4rem; margin: .55rem 0 .3rem; }
    .ee-stat-chip {
      flex: 1; text-align: center; background: var(--cream-deep);
      border-radius: 10px; padding: .4rem .3rem;
    }
    .ee-stat-chip strong { display: block; font-family: var(--font-mono); font-size: .82rem; color: var(--forest-dark); }
    .ee-stat-chip span { font-size: .62rem; opacity: .6; text-transform: uppercase; letter-spacing: .03em; }

    /* Phase 3: YouTube tutorial button — compact, premium, theme-matched */
    .ex-video-btn {
      display: inline-flex; align-items: center; gap: .35rem;
      margin: .4rem 0 .3rem;
      padding: .4rem .8rem;
      background: linear-gradient(135deg, var(--forest-mid), var(--forest-dark));
      color: var(--cream);
      border-radius: 99px;
      font-family: var(--font-body);
      font-size: .76rem; font-weight: 700;
      text-decoration: none;
      box-shadow: 0 3px 10px rgba(27,67,50,.18);
      transition: transform .2s ease, box-shadow .2s ease, background .2s ease;
    }
    .ex-video-btn:hover {
      transform: translateY(-2px);
      box-shadow: 0 6px 16px rgba(27,67,50,.28);
      background: linear-gradient(135deg, var(--forest-dark), #0e2a1d);
    }
    .ee-modal-video-btn { margin: .6rem 0; }
  `;
  document.head.appendChild(style);

  // ── Level / Type selectors: restore saved prefs, wire changes ──
  const prefs = SmartFit.getWorkoutPrefs();
  const lvlInput = document.getElementById(
    prefs.level === 'Advanced' ? 'lvlA' : prefs.level === 'Intermediate' ? 'lvlI' : 'lvlB'
  );
  if (lvlInput) lvlInput.checked = true;
  const typeInput = document.getElementById(prefs.type === 'Yoga' ? 'typeY' : 'typeW');
  if (typeInput) typeInput.checked = true;

  document.querySelectorAll('input[name="level"], input[name="type"]').forEach(input => {
    input.addEventListener('change', function () {
      const level = document.querySelector('input[name="level"]:checked')?.value || 'Beginner';
      const type  = document.querySelector('input[name="type"]:checked')?.value === 'yoga' ? 'Yoga' : 'Workout';
      SmartFit.saveWorkoutPrefs({ level, type });
      renderWorkout();
    });
  });

  // ── State shared with the timer section below ─────────
  let workoutState = SmartFit.getWorkoutState();
  let currentKey   = null;

  const timerClock    = document.getElementById('timerClock');
  const timerStart    = document.getElementById('timerStart');
  const timerReset    = document.getElementById('timerReset');
  const sessionBar    = document.getElementById('sessionProgress');
  const progressText  = document.getElementById('progressText');
  const sessionProgressWrap = document.querySelector('.session-progress');

  const banner = document.createElement('div');
  banner.className = 'workout-complete-banner';
  banner.textContent = '🎉 Today\'s workout completed!';
  if (sessionProgressWrap) sessionProgressWrap.appendChild(banner);

  let totalSeconds = 30, remainingSeconds = 30, timerInterval = null, isRunning = false;

  function formatTime(s) {
    const m = Math.floor(s / 60), sec = s % 60;
    return String(m).padStart(2,'0') + ':' + String(sec).padStart(2,'0');
  }
  function updateClock() { if (timerClock) timerClock.textContent = formatTime(remainingSeconds); }

  function updateProgress() {
    const total = document.querySelectorAll('#mainWorkoutList .exercise-card').length;
    const done  = document.querySelectorAll('#mainWorkoutList .exercise-card.completed').length;
    const pct = total ? Math.round(done / total * 100) : 0;
    if (sessionBar) sessionBar.style.width = pct + '%';
    if (progressText) progressText.textContent = done + ' of ' + total;
    if (banner) banner.classList.toggle('show', total > 0 && done >= total);
  }

  function tick() {
    if (remainingSeconds > 0) {
      remainingSeconds--; updateClock();
    } else {
      clearInterval(timerInterval); isRunning = false;
      if (timerStart) timerStart.textContent = '▶ Start';
      if (timerClock) {
        timerClock.style.color = '#E76F51';
        setTimeout(() => { if (timerClock) timerClock.style.color = ''; }, 800);
      }
      markExerciseDone(currentKey);
    }
  }

  function markExerciseDone(key) {
    if (!key || workoutState[key]) return;
    workoutState = SmartFit.saveWorkoutDone(key, true);
    const card  = document.querySelector('#mainWorkoutList .exercise-card[data-ex-id="' + key + '"]');
    const badge = card ? card.querySelector('.ex-done-badge') : null;
    if (card)  card.classList.add('completed');
    if (badge) badge.textContent = '✓ Exercise Completed';
    updateProgress();
  }

  function toggleExerciseDone(key, card, badge) {
    const nowDone = !workoutState[key];
    workoutState = SmartFit.saveWorkoutDone(key, nowDone);
    card.classList.toggle('completed', nowDone);
    badge.textContent = nowDone ? '✓ Exercise Completed' : '○ Tap to mark complete';
    updateProgress();
  }

  if (timerStart) {
    timerStart.addEventListener('click', function () {
      if (!isRunning) {
        isRunning = true; timerStart.textContent = '⏸ Pause';
        timerInterval = setInterval(tick, 1000);
      } else {
        isRunning = false; timerStart.textContent = '▶ Start';
        clearInterval(timerInterval);
      }
    });
  }
  if (timerReset) {
    timerReset.addEventListener('click', function () {
      clearInterval(timerInterval); isRunning = false;
      remainingSeconds = totalSeconds;
      if (timerStart) timerStart.textContent = '▶ Start';
      updateClock();
    });
  }

  function selectCard(card, secs, key, label) {
    document.querySelectorAll('#mainWorkoutList .exercise-card').forEach(c => c.classList.remove('active'));
    if (card) card.classList.add('active');
    clearInterval(timerInterval); isRunning = false;
    remainingSeconds = secs; totalSeconds = secs; currentKey = key;
    if (timerStart) timerStart.textContent = '▶ Start';
    const labelEl = document.getElementById('currentExLabel');
    if (labelEl) labelEl.textContent = label;
    updateClock();
  }

  // Drive the existing timer from an Explore exercise (used by the
  // detail modal's "Start Exercise" button). Reuses selectCard, so
  // there is only ever one timer. If this exercise also happens to
  // be one of today's cards, that card gets highlighted too.
  function startExerciseInTimer(ex) {
    const id = SmartFit.getExerciseId(ex);
    const timePerSet = ex.ty === 'Yoga' ? ex.r : ex.r * 3;
    const seconds = ex.s * timePerSet + ex.s * ex.rest;
    const matchingCard = document.querySelector('#mainWorkoutList .exercise-card[data-ex-id="' + id + '"]');
    selectCard(matchingCard, seconds, id, ex.n);
    const timerSection = document.querySelector('.timer-section');
    if (timerSection) timerSection.scrollIntoView({ behavior: 'smooth', block: 'center' });
  }

  const ICONS = {
    Chest:'🏋️', Shoulder:'🤸', Abs:'🔥', Legs:'🦵', Thighs:'🦵',
    Arms:'💪', Back:'🧎', 'Full Body':'⚡'
  };

  // ── PHASE 1: Build today's personalized list ──────────
  function renderWorkout() {
    const currentPrefs = SmartFit.getWorkoutPrefs();
    const plan = SmartFit.getTodayWorkout(profile || {}, analysis || { goal: 'All' }, currentPrefs);
    workoutState = SmartFit.getWorkoutState();

    const list = document.getElementById('mainWorkoutList');
    const titleEl = document.getElementById('mainWorkoutSubtitle');
    if (!list) return;
    list.innerHTML = '';

        if (plan.isPregnancySafety) {
      if (titleEl) titleEl.textContent = 'Pregnancy — Professional Review';
      list.innerHTML = `
        <div class="rest-day-card">
          <h3>🤰 Pregnancy profile detected</h3>
          <p>${plan.safetyMessage || 'Exercise during pregnancy should be planned with a qualified healthcare professional. A generic workout is not shown here.'}</p>
        </div>`;
      updateProgress();
      return;
    }

    if (plan.isRestDay) {
      if (titleEl) titleEl.textContent = 'Rest day';
      list.innerHTML = `
        <div class="rest-day-card">
          <h3>🌿 Today's a rest day</h3>
          <p>Recovery matters as much as training. Try a short walk, light stretching, or extra sleep tonight.</p>
        </div>`;
      updateProgress();
      return;
    }

    if (titleEl) titleEl.textContent = plan.bodyPart + ' focus · Click any exercise to set timer';

    plan.exercises.forEach(ex => {
      const isDone = !!workoutState[ex.id];
      const card = document.createElement('div');
      card.className = 'exercise-card' + (isDone ? ' completed' : '');
      card.dataset.exId = ex.id;
      card.dataset.seconds = ex.seconds;

      const metaLabel = ex.type === 'Yoga'
        ? `${ex.sets} sets · ${ex.reps} sec hold · ${ex.rest} sec rest`
        : `${ex.sets} sets · ${ex.reps} reps · ${ex.rest} sec rest`;

      const video = SmartFit.getVideoInfo(ex.name, ex.type);

      card.innerHTML = `
        <div class="ex-icon">${ICONS[ex.bodyPart] || '🏋️'}</div>
        <div class="ex-info">
          <h4>${ex.name}</h4>
          <p class="ex-meta">${metaLabel}</p>
          <a class="ex-video-btn" href="${video.videoUrl}" target="_blank" rel="noopener noreferrer" aria-label="Watch ${ex.name} tutorial video">▶ Watch ${ex.name} Tutorial</a>
          <details class="ex-howto">
            <summary>How to do it</summary>
            <ol>${ex.steps.map(s => `<li>${s}</li>`).join('')}</ol>
            <p class="ex-tip">💡 ${ex.tip}</p>
          </details>
        </div>
        <span class="ex-time">${formatTime(ex.seconds)}</span>
      `;

      const videoBtn = card.querySelector('.ex-video-btn');
      if (videoBtn) videoBtn.addEventListener('click', e => e.stopPropagation());

      const badge = document.createElement('div');
      badge.className = 'ex-done-badge';
      badge.textContent = isDone ? '✓ Exercise Completed' : '○ Tap to mark complete';
      badge.addEventListener('click', e => { e.stopPropagation(); toggleExerciseDone(ex.id, card, badge); });
      card.querySelector('.ex-info').appendChild(badge);

      card.addEventListener('click', () => selectCard(card, ex.seconds, ex.id, ex.name));

      list.appendChild(card);
    });

    updateProgress();
  }

  // Render Today's Personalized Workout NOW — before any Phase 2 code
  // runs — so nothing in the Explore section can ever block it.
  renderWorkout();
  updateClock();

  // ── PHASE 2 (redesigned): visual category grid → exercise grid ──
  // Fully isolated from Phase 1: never touches #mainWorkoutList,
  // workoutState, the timer, or any smartfit_workout_progress_* key.
  const CATEGORIES = [
    { key:'Chest',     icon:'🏋️', desc:'Chest & upper-body strength',      tint:'t1' },
    { key:'Shoulder',  icon:'🤸', desc:'Shoulder mobility & strength',     tint:'t2' },
    { key:'Abs',       icon:'🔥', desc:'Core & abdominal strength',        tint:'t3' },
    { key:'Legs',      icon:'🦵', desc:'Lower-body strength',              tint:'t4' },
    { key:'Thighs',    icon:'🍑', desc:'Glute & thigh toning',             tint:'t1' },
    { key:'Arms',      icon:'💪', desc:'Arm strength & toning',            tint:'t2' },
    { key:'Back',      icon:'🧎', desc:'Back strength & posture',          tint:'t3' },
    { key:'Full Body', icon:'⚡', desc:'Full-body & cardio conditioning',  tint:'t4' },
    { key:'Yoga',      icon:'🧘', desc:'Mobility, balance & flexibility',  tint:'t5' },
  ];

  let exploreView = 'grid';   // 'grid' | 'category' | 'search' | 'all'
  let exploreCategory = null;
  let levelFilter = 'All';
  let goalFilter = 'All';
  let favoritesOnly = false;
  let favorites = SmartFit.getFavorites();

  function exercisesFor(catKey) {
    if (catKey === 'Yoga') return SmartFit.WORKOUT_DATA.filter(ex => ex.ty === 'Yoga');
    return SmartFit.WORKOUT_DATA.filter(ex => ex.bp === catKey && ex.ty === 'Workout');
  }

  function filtersActive() {
    return levelFilter !== 'All' || goalFilter !== 'All' || favoritesOnly;
  }

  function applyFilters(list) {
    return list.filter(ex => {
      if (levelFilter !== 'All' && ex.lvl !== levelFilter) return false;
      if (goalFilter !== 'All' && ex.g !== goalFilter && ex.g !== 'All') return false;
      if (favoritesOnly && !favorites[SmartFit.getExerciseId(ex)]) return false;
      return true;
    });
  }

  const LEVEL_CLASS = { Beginner: 'beg', Intermediate: 'int', Advanced: 'adv' };
  const LEVEL_ICON  = { Beginner: '🟢', Intermediate: '🟡', Advanced: '🔴' };

  function tintFor(bp) { return (CATEGORIES.find(c => c.key === bp) || {tint:'t1'}).tint; }

  function exerciseMeta(ex) {
    const holdOrReps = ex.ty === 'Yoga' ? 'Hold' : 'Reps';
    return { holdOrReps, sets: ex.s, mid: ex.r + (ex.ty === 'Yoga' ? 's' : ''), rest: ex.rest + 's' };
  }

  function renderExploreCard(ex, index) {
    const id = SmartFit.getExerciseId(ex);
    const m = exerciseMeta(ex);
    const isFav = !!favorites[id];

    const wrap = document.createElement('div');
    wrap.className = 'ee-exercise-card';
    wrap.style.animationDelay = (Math.min(index, 8) * 0.04) + 's';
    wrap.setAttribute('role', 'button');
    wrap.setAttribute('tabindex', '0');
    wrap.setAttribute('aria-label', 'View details for ' + ex.n);

    wrap.innerHTML = `
      <div class="ee-exercise-visual ee-${tintFor(ex.bp)}">
        <span aria-hidden="true">${ICONS[ex.bp] || '🏋️'}</span>
        <button type="button" class="ee-fav-btn${isFav ? ' active' : ''}" aria-label="${isFav ? 'Remove from favorites' : 'Add to favorites'}" aria-pressed="${isFav}">${isFav ? '♥' : '♡'}</button>
      </div>
      <div class="ee-exercise-body">
        <h4>${ex.n}</h4>
        <div class="browse-tags">
          <span class="browse-tag lvl ${LEVEL_CLASS[ex.lvl] || 'beg'}">${LEVEL_ICON[ex.lvl] || ''} ${ex.lvl}</span>
          <span class="browse-tag ty">${ex.bp}</span>
          ${ex.g !== 'All' ? `<span class="browse-tag goal">${ex.g}</span>` : ''}
        </div>
        <div class="ee-stat-row">
          <div class="ee-stat-chip"><strong>${m.sets}</strong><span>Sets</span></div>
          <div class="ee-stat-chip"><strong>${m.mid}</strong><span>${m.holdOrReps}</span></div>
          <div class="ee-stat-chip"><strong>${m.rest}</strong><span>Rest</span></div>
        </div>
        <span class="ee-detail-hint">View details →</span>
      </div>`;

    const favBtn = wrap.querySelector('.ee-fav-btn');
    favBtn.addEventListener('click', e => {
      e.stopPropagation();
      favorites = SmartFit.toggleFavorite(id);
      const nowFav = !!favorites[id];
      favBtn.classList.toggle('active', nowFav);
      favBtn.textContent = nowFav ? '♥' : '♡';
      favBtn.setAttribute('aria-label', nowFav ? 'Remove from favorites' : 'Add to favorites');
      favBtn.setAttribute('aria-pressed', String(nowFav));
      if (favoritesOnly) renderExploreGrid();
    });

    const open = () => openExerciseModal(ex);
    wrap.addEventListener('click', open);
    wrap.addEventListener('keydown', e => {
      if (e.key === 'Enter' || e.key === ' ') { e.preventDefault(); open(); }
    });

    return wrap;
  }

  function renderExploreGrid() {
    const gridWrap = document.getElementById('exploreCategoryGrid');
    const resultsWrap = document.getElementById('exploreResults');
    const backBtn = document.getElementById('exploreBackBtn');
    const heading = document.getElementById('exploreHeading');
    const countEl = document.getElementById('exploreCount');
    if (!gridWrap || !resultsWrap) return;

    const searchBox = document.getElementById('exploreSearch');
    const query = (searchBox?.value || '').trim().toLowerCase();
    if (query) exploreView = 'search';

    const showResults = exploreView !== 'grid' || filtersActive();

    if (!showResults) {
      gridWrap.style.display = '';
      resultsWrap.style.display = 'none';
      if (backBtn) backBtn.style.display = 'none';
      if (heading) heading.textContent = 'Choose a workout category and discover exercises.';
      if (countEl) countEl.textContent = '';
      return;
    }

    gridWrap.style.display = 'none';
    resultsWrap.style.display = '';
    if (backBtn) backBtn.style.display = '';

    let pool, title;
    if (exploreView === 'search') {
      pool = (exploreCategory ? exercisesFor(exploreCategory) : SmartFit.WORKOUT_DATA)
        .filter(ex => ex.n.toLowerCase().includes(query));
      title = exploreCategory ? `Search results in ${exploreCategory}` : 'Search results';
    } else if (exploreView === 'all') {
      pool = SmartFit.WORKOUT_DATA;
      title = 'All Exercises';
    } else if (exploreView === 'category') {
      pool = exercisesFor(exploreCategory);
      title = exploreCategory + ' Exercises';
    } else {
      // still 'grid', but a filter (level/goal/favorites) forced results open
      pool = SmartFit.WORKOUT_DATA;
      title = favoritesOnly ? 'Favorite Exercises' : 'Filtered Exercises';
    }

    const results = applyFilters(pool);
    if (heading) heading.textContent = title;
    if (countEl) countEl.textContent = results.length + ' exercise' + (results.length === 1 ? '' : 's') + ' found';
    resultsWrap.innerHTML = '';

    if (!results.length) {
      resultsWrap.innerHTML = `
        <div class="explore-empty">
          <p style="font-weight:700;color:var(--forest-dark);margin-bottom:.3rem">No exercises found</p>
          <p style="margin-bottom:1rem">Try changing your filters or search.</p>
          <button type="button" class="explore-back-btn" id="exploreClearFiltersBtn" style="display:inline-flex">Clear Filters</button>
        </div>`;
      const clearBtn = document.getElementById('exploreClearFiltersBtn');
      if (clearBtn) clearBtn.addEventListener('click', clearFilters);
      return;
    }

    results.forEach((ex, i) => resultsWrap.appendChild(renderExploreCard(ex, i)));
  }

  function clearFilters() {
    levelFilter = 'All';
    goalFilter = 'All';
    favoritesOnly = false;
    const lvlSel = document.getElementById('filterLevel');
    const goalSel = document.getElementById('filterGoal');
    const favBtn = document.getElementById('filterFavorites');
    if (lvlSel) lvlSel.value = 'All';
    if (goalSel) goalSel.value = 'All';
    if (favBtn) favBtn.classList.remove('active');
    const searchBox = document.getElementById('exploreSearch');
    if (searchBox) searchBox.value = '';
    renderExploreGrid();
  }

  function buildFilterBar() {
    const wrap = document.getElementById('exploreFilterBar');
    if (!wrap) return;
    wrap.innerHTML = `
      <select id="filterLevel" class="explore-filter-select" aria-label="Filter by level">
        <option value="All">All Levels</option>
        <option value="Beginner">Beginner</option>
        <option value="Intermediate">Intermediate</option>
        <option value="Advanced">Advanced</option>
      </select>
      <select id="filterGoal" class="explore-filter-select" aria-label="Filter by goal">
        <option value="All">All Goals</option>
        <option value="Weight Loss">Weight Loss</option>
        <option value="Weight Gain">Weight Gain</option>
      </select>
      <button type="button" id="filterFavorites" class="explore-fav-chip" aria-pressed="false">♥ Favorites</button>
    `;
    document.getElementById('filterLevel').addEventListener('change', e => { levelFilter = e.target.value; renderExploreGrid(); });
    document.getElementById('filterGoal').addEventListener('change', e => { goalFilter = e.target.value; renderExploreGrid(); });
    document.getElementById('filterFavorites').addEventListener('click', function () {
      favoritesOnly = !favoritesOnly;
      this.classList.toggle('active', favoritesOnly);
      this.setAttribute('aria-pressed', String(favoritesOnly));
      renderExploreGrid();
    });
  }

  function buildCategoryGrid() {
    const gridWrap = document.getElementById('exploreCategoryGrid');
    if (!gridWrap) return;
    gridWrap.innerHTML = '';
    CATEGORIES.forEach((cat, i) => {
      const count = exercisesFor(cat.key).length;
      const card = document.createElement('button');
      card.type = 'button';
      card.className = 'ee-cat-card';
      card.style.animationDelay = (i * 0.05) + 's';
      card.setAttribute('aria-label', `Explore ${cat.key} exercises, ${count} available`);
      card.innerHTML = `
        <div class="ee-cat-visual ee-${cat.tint}">
          <span class="ee-cat-count-badge">${count} move${count === 1 ? '' : 's'}</span>
          <span aria-hidden="true">${cat.icon}</span>
        </div>
        <div class="ee-cat-body">
          <h4>${cat.key.toUpperCase()}</h4>
          <p>${cat.desc}</p>
          <span class="ee-cat-arrow" aria-hidden="true">→</span>
        </div>`;
      card.addEventListener('click', () => {
        exploreCategory = cat.key;
        exploreView = 'category';
        const searchBox = document.getElementById('exploreSearch');
        if (searchBox) searchBox.value = '';
        renderExploreGrid();
      });
      gridWrap.appendChild(card);
    });
  }

  // ── Exercise detail modal (Explore only — never touches the timer
  // unless the user explicitly clicks "Start Exercise" inside it) ──
  let modalEl = null;

  function ensureModal() {
    if (modalEl) return modalEl;
    modalEl = document.createElement('div');
    modalEl.className = 'ee-modal-overlay';
    modalEl.id = 'eeModalOverlay';
    modalEl.innerHTML = `
      <div class="ee-modal" role="dialog" aria-modal="true" aria-labelledby="eeModalTitle">
        <button type="button" class="ee-modal-close" id="eeModalClose" aria-label="Close">✕</button>
        <div class="ee-modal-visual" id="eeModalVisual"><span id="eeModalIcon"></span></div>
        <div class="ee-modal-body">
          <h3 id="eeModalTitle"></h3>
          <div class="browse-tags" id="eeModalTags"></div>
          <div class="ee-stat-row" id="eeModalStats"></div>
          <h4 class="ee-modal-subhead">How to perform</h4>
          <ol id="eeModalSteps"></ol>
          <p class="ex-tip" id="eeModalTip"></p>
          <a id="eeModalVideoBtn" class="ex-video-btn ee-modal-video-btn" href="#" target="_blank" rel="noopener noreferrer" aria-label="Watch tutorial video">▶ Watch Tutorial</a>
          <p class="ee-safety-note">⚠️ General safety: stop if you feel pain, breathe steadily throughout, and consult a healthcare professional if you have an injury, medical condition, or are pregnant.</p>
          <button type="button" class="explore-viewall-btn ee-start-btn" id="eeStartExerciseBtn">▶ Start Exercise</button>
        </div>
      </div>`;
    document.body.appendChild(modalEl);

    modalEl.addEventListener('click', e => { if (e.target === modalEl) closeExerciseModal(); });
    document.getElementById('eeModalClose').addEventListener('click', closeExerciseModal);
    document.addEventListener('keydown', e => {
      if (e.key === 'Escape' && modalEl.classList.contains('show')) closeExerciseModal();
    });
    return modalEl;
  }

  let modalExercise = null;
  let modalTriggerEl = null;

  function openExerciseModal(ex) {
    ensureModal();
    modalExercise = ex;
    modalTriggerEl = document.activeElement;

    const m = exerciseMeta(ex);
    document.getElementById('eeModalVisual').className = 'ee-modal-visual ee-' + tintFor(ex.bp);
    document.getElementById('eeModalIcon').textContent = ICONS[ex.bp] || '🏋️';
    document.getElementById('eeModalTitle').textContent = ex.n;
    document.getElementById('eeModalTags').innerHTML = `
      <span class="browse-tag lvl ${LEVEL_CLASS[ex.lvl] || 'beg'}">${LEVEL_ICON[ex.lvl] || ''} ${ex.lvl}</span>
      <span class="browse-tag ty">${ex.ty} · ${ex.bp}</span>
      ${ex.g !== 'All' ? `<span class="browse-tag goal">${ex.g}</span>` : ''}`;
    document.getElementById('eeModalStats').innerHTML = `
      <div class="ee-stat-chip"><strong>${m.sets}</strong><span>Sets</span></div>
      <div class="ee-stat-chip"><strong>${m.mid}</strong><span>${m.holdOrReps}</span></div>
      <div class="ee-stat-chip"><strong>${m.rest}</strong><span>Rest</span></div>`;
    document.getElementById('eeModalSteps').innerHTML = ex.steps.map(s => `<li>${s}</li>`).join('');
    document.getElementById('eeModalTip').textContent = '💡 ' + ex.tip;

    const video = SmartFit.getVideoInfo(ex.n, ex.ty);
    const videoBtn = document.getElementById('eeModalVideoBtn');
    if (videoBtn) {
      videoBtn.href = video.videoUrl;
      videoBtn.textContent = '▶ Watch ' + ex.n + ' Tutorial';
      videoBtn.setAttribute('aria-label', 'Watch ' + ex.n + ' tutorial video');
    }

    modalEl.classList.add('show');
    document.body.style.overflow = 'hidden';
    document.getElementById('eeModalClose').focus();
  }

  function closeExerciseModal() {
    if (!modalEl) return;
    modalEl.classList.remove('show');
    document.body.style.overflow = '';
    if (modalTriggerEl && typeof modalTriggerEl.focus === 'function') modalTriggerEl.focus();
  }

  document.addEventListener('DOMContentLoaded', () => {}); // no-op guard for lazy modal init order

  // Start Exercise button is created lazily inside ensureModal(); wire once available
  function wireStartButton() {
    const btn = document.getElementById('eeStartExerciseBtn');
    if (!btn || btn.dataset.wired) return;
    btn.dataset.wired = 'true';
    btn.addEventListener('click', () => {
      if (!modalExercise) return;
      closeExerciseModal();
      startExerciseInTimer(modalExercise);
    });
  }

  const exploreSearch = document.getElementById('exploreSearch');
  if (exploreSearch) {
    exploreSearch.addEventListener('input', () => {
      if (!exploreSearch.value.trim()) {
        exploreView = exploreCategory ? 'category' : 'grid';
      } else {
        exploreView = 'search';
      }
      renderExploreGrid();
    });
  }

  const viewAllBtn = document.getElementById('exploreViewAll');
  if (viewAllBtn) {
    viewAllBtn.addEventListener('click', () => {
      exploreCategory = null;
      exploreView = 'all';
      const searchBox = document.getElementById('exploreSearch');
      if (searchBox) searchBox.value = '';
      renderExploreGrid();
    });
  }

  const backBtn = document.getElementById('exploreBackBtn');
  if (backBtn) {
    backBtn.addEventListener('click', () => {
      exploreCategory = null;
      exploreView = 'grid';
      levelFilter = 'All'; goalFilter = 'All'; favoritesOnly = false;
      const lvlSel = document.getElementById('filterLevel');
      const goalSel = document.getElementById('filterGoal');
      const favBtn = document.getElementById('filterFavorites');
      if (lvlSel) lvlSel.value = 'All';
      if (goalSel) goalSel.value = 'All';
      if (favBtn) favBtn.classList.remove('active');
      const searchBox = document.getElementById('exploreSearch');
      if (searchBox) searchBox.value = '';
      renderExploreGrid();
    });
  }

  buildFilterBar();
  buildCategoryGrid();
  ensureModal();
  wireStartButton();
  renderExploreGrid();
  renderWorkout();
  updateClock();
});