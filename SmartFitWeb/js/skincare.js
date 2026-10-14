// ======================================================
// skincare.js — Grooming Personalization Engine
// Single source of truth: SmartFit profile (sf_profile).
// Reads gender/skinType/budget from the profile; the two
// on-page selects are session-only overrides, never saved
// back to the profile. Hair type/concern are page-local
// (no profile fields exist for them — see audit note).
// ======================================================

document.addEventListener('DOMContentLoaded', function () {

  // ── Guard: smartfit-data.js didn't load ───────────────
  if (typeof SmartFit === 'undefined') {
    document.body.innerHTML =
      '<div style="max-width:640px;margin:4rem auto;padding:2rem;font-family:sans-serif;' +
      'background:#fff3f3;border:2px solid #c0392b;border-radius:12px;color:#2b2b2b">' +
      '<h2 style="color:#c0392b;margin-bottom:.6rem">⚠️ Could not load SmartFit</h2>' +
      '<p>This page could not find <code>js/smartfit-data.js</code>.</p></div>';
    return;
  }

  // ── Guard: no profile yet — don't crash, ask for one ──
  const profile = SmartFit.loadProfile();
  if (!profile) {
    document.body.innerHTML =
      '<div style="max-width:640px;margin:4rem auto;padding:2rem;font-family:sans-serif;' +
      'background:#F4EFD9;border:2px solid #1B4332;border-radius:16px;color:#283618;text-align:center">' +
      '<h2 style="margin-bottom:.8rem">🌿 Complete your profile first</h2>' +
      '<p style="margin-bottom:1.2rem">Grooming recommendations are personalized from your SmartFit profile — skin type, budget and gender.</p>' +
      '<a href="profile.html" style="display:inline-block;background:#1B4332;color:#FEFAE0;padding:.8rem 1.6rem;border-radius:999px;text-decoration:none;font-weight:700">Complete Profile</a>' +
      '</div>';
    return;
  }

  // ======================================================
  // GROOMING DATA
  // ======================================================
  const GROOMING_DATA = {
    cleanser: [
      { budget:'low',    skinTypes:['oily','combination'],          name:'Besan + Haldi + Rose Water Paste', desc:'A simple homemade cleanser suitable for oily/combination skin.', use:'Make a light paste, massage gently, rinse with cool water.' },
      { budget:'low',    skinTypes:['dry','sensitive','normal'],    name:'Milk Cream (Malai) + Besan',       desc:'A gentle, budget cleanser suitable for dry or sensitive skin.', use:'Massage lightly for 1 minute, rinse off — avoid harsh scrubbing.' },
      { budget:'medium', skinTypes:['oily','combination'],          name:'Himalaya Neem Face Wash — ₹100',   desc:'Lightweight neem-based cleanser, suitable for oily/combination skin.', use:'Use morning and night, small pea-sized amount.' },
      { budget:'medium', skinTypes:['dry','sensitive','normal'],    name:'Cetaphil Gentle Skin Cleanser — ₹250', desc:'Fragrance-light, gentle cleanser suitable for dry or sensitive skin.', use:'Use once daily at night; a mild rinse in the morning may be enough.' },
      { budget:'high',   skinTypes:['oily','combination'],          name:'The Derma Co 1% Salicylic Face Wash', desc:'Lightweight cleanser often suitable for oily/combination skin.', use:'Use once daily initially, observe how your skin responds.' },
      { budget:'high',   skinTypes:['dry','sensitive','normal'],    name:'CeraVe Hydrating Cleanser',        desc:'Ceramide-based gentle cleanser suitable for dry/sensitive/normal skin.', use:'Use morning and night, no harsh rubbing.' },
    ],
    moisturizer: [
      { budget:'low',    skinTypes:['oily','combination'],          name:'Pure Aloe Vera Gel — ₹80',         desc:'Lightweight, non-heavy option suitable for oily/combination skin.', use:'Apply a thin layer after cleansing.' },
      { budget:'low',    skinTypes:['dry','sensitive','normal'],    name:'Pure Coconut Oil — ₹100/bottle',   desc:'A simple hydrating option suitable for dry skin.', use:'Apply a light layer while skin is slightly damp.' },
      { budget:'medium', skinTypes:['oily','combination'],          name:'Neutrogena Hydro Boost Gel — ₹400', desc:'Oil-free gel moisturizer suitable for oily/combination skin.', use:'Apply after cleansing, morning and night.' },
      { budget:'medium', skinTypes:['dry','sensitive','normal'],    name:'Cetaphil Moisturizing Cream — ₹350', desc:'Hydration-focused cream suitable for dry/sensitive skin.', use:'Apply generously after cleansing.' },
      { budget:'high',   skinTypes:['oily','combination'],          name:'CeraVe AM Facial Moisturizing Lotion', desc:'Lightweight, non-heavy, suitable for oily/combination skin.', use:'Apply in the morning before sunscreen.' },
      { budget:'high',   skinTypes:['dry','sensitive','normal'],    name:'La Roche-Posay Toleriane Double Repair', desc:'Hydration-focused, suitable for dry or sensitive skin.', use:'Apply morning and night.' },
    ],
    sunscreen: [
      { budget:'low',    skinTypes:['all'], name:'Lotus Herbals Safe Sun SPF 30', desc:'Budget-friendly daily sun protection, suitable for most skin types.', use:'Apply 15-20 min before sun exposure; reapply every few hours outdoors.' },
      { budget:'medium', skinTypes:['all'], name:'Minimalist SPF 50 Sunscreen — ₹250', desc:'Lightweight, widely suitable sunscreen.', use:'Apply as the last step of your morning routine.' },
      { budget:'high',   skinTypes:['all'], name:"La Roche-Posay Anthelios SPF 50", desc:'Higher-tier daily sunscreen.', use:'Apply generously, reapply if outdoors for long periods.' },
    ],
    treatment: [
      { budget:'low',    skinTypes:['oily','combination'],       name:'Multani Mitti Face Pack — ₹30',        desc:'A simple clay-based option some find helpful for oily/combination skin.', use:'Apply for 15 min, 2-3x/week, rinse with water.' },
      { budget:'low',    skinTypes:['dry','sensitive','normal'], name:'Honey + Milk Mask',                     desc:'A gentle, simple hydration-focused option.', use:'Apply for 15 min, rinse with lukewarm water.' },
      { budget:'medium', skinTypes:['oily','combination'],       name:'Minimalist 10% Niacinamide — ₹400',    desc:'A commonly used option for oil-control-focused routines.', use:'Use 2-3x/week at night, after cleansing.' },
      { budget:'medium', skinTypes:['dry','sensitive','normal'], name:'Minimalist 2% Hyaluronic Acid — ₹500', desc:'Hydration-focused serum suitable for dry/sensitive skin.', use:'Apply on slightly damp skin, 2-3x/week.' },
      { budget:'high',   skinTypes:['all'],                      name:'Vitamin C Serum',                       desc:'A commonly used general skin-health serum.', use:'Use in the morning before moisturizer and sunscreen.' },
    ]
  };

  const HAIR_DATA = {
    shampoo: [
      { budget:'low',    name:'Shikakai/Reetha Powder', desc:'A natural, budget-friendly cleansing option.', use:'Use as a paste, massage into scalp, rinse thoroughly.' },
      { budget:'medium', name:'Mamaearth Onion Shampoo — ₹350', desc:'A sulphate-free option often chosen for general scalp care.', use:'Use per the washing frequency for your hair type below.' },
      { budget:'high',   name:"WOW Apple Cider Vinegar Shampoo", desc:'A higher-tier cleansing option.', use:'Use per the washing frequency for your hair type below.' },
    ],
    conditioner: [
      { budget:'low',    name:'Aloe Vera Gel (leave-in)', desc:'A light, budget-friendly option for lengths.', use:'Apply to lengths only, not the scalp.' },
      { budget:'medium', name:'Mamaearth Onion Conditioner', desc:'A commonly paired conditioner for the shampoo above.', use:'Apply to lengths, leave 2-3 min, rinse.' },
      { budget:'high',   name:'L\'Oréal Professional Conditioner', desc:'A higher-tier conditioning option.', use:'Apply to lengths, leave as directed, rinse.' },
    ],
    oil: [
      { budget:'low',    name:'Coconut Oil — ₹150/bottle', desc:'A widely used, affordable oiling option.', use:'Warm slightly, massage into scalp, leave 1 hour before washing.' },
      { budget:'medium', name:'Dabur Almond Hair Oil', desc:'A lightweight oiling option.', use:'Massage into scalp 2-3x/week, leave 1 hour before washing.' },
      { budget:'high',   name:'Mamaearth Onion Hair Oil', desc:'A higher-tier oiling option.', use:'Massage into scalp 2-3x/week, leave 1 hour before washing.' },
    ]
  };

  const MEN_DATA = {
    beard: [
      { budget:'low',    name:'Plain Water + Mild Soap Wash',  desc:'Basic beard cleaning.', use:'Rinse daily, pat dry.' },
      { budget:'medium', name:'Beard Wash + Beard Oil',         desc:'Keeps beard hair soft and scalp-skin underneath hydrated.', use:'Wash 2-3x/week, oil daily after washing face.' },
      { budget:'high',   name:'Beard Wash + Beard Oil + Balm',  desc:'Fuller beard-care routine.', use:'Wash 2-3x/week, oil daily, balm for styling as needed.' },
    ],
    shaving: [
      { budget:'low',    name:'Warm Water + Mild Soap',          desc:'Basic pre-shave softening.', use:'Wash face with warm water before shaving to soften hair.' },
      { budget:'medium', name:'Shaving Gel + Aftershave Balm',   desc:'Reduces razor friction and post-shave irritation.', use:'Apply gel before shaving; balm after — avoid alcohol-heavy aftershaves which can dry skin.' },
      { budget:'high',   name:'Pre-Shave Oil + Gel + Balm',      desc:'A fuller shaving routine aimed at reducing irritation.', use:'Pre-shave oil, then gel, shave with the grain, then balm.' },
    ],
    hairGrooming: [
      { budget:'low',    name:'Basic Trim + Comb Routine', desc:'Simple upkeep.', use:'Comb daily, trim every 3-4 weeks.' },
      { budget:'medium', name:'Light Styling Cream',        desc:'Basic hold for daily styling.', use:'Small amount on damp hair, style as needed.' },
      { budget:'high',   name:'Styling Cream + Regular Trims', desc:'A fuller grooming routine.', use:'Style daily; trim every 3-4 weeks for a maintained look.' },
    ]
  };

  const HAIR_CONCERN_TEXT = {
    none:     'No specific concern — keep up your current routine!',
    dry:      'Dry hair: focus on oiling 2-3x/week and a hydrating conditioner on the lengths. Avoid daily heat styling.',
    oily:     'Oily scalp: wash more frequently (see frequency above), use a lightweight oil only if needed, and avoid over-conditioning the scalp itself.',
    dandruff: 'Dandruff: rinsing with neem water and adding tea tree oil to your shampoo are commonly used simple options. Keep the scalp clean with regular washing.',
    hairfall: 'Hair fall concern: a protein-rich diet (eggs, dal, paneer), gentle handling, and avoiding tight hairstyles may help. For ongoing concerns, consider consulting a dermatologist.',
    frizzy:   'Frizzy hair: a leave-in conditioner and avoiding over-washing may help retain moisture in the hair shaft.'
  };

  // ======================================================
  // SELECTION ENGINE
  // ======================================================
  function pickItem(list, budget, skinType) {
    let pool = list.filter(i => i.budget === budget && (i.skinTypes.includes(skinType) || i.skinTypes.includes('all')));
    if (!pool.length) pool = list.filter(i => i.budget === budget);
    if (!pool.length) pool = list.filter(i => i.skinTypes.includes(skinType) || i.skinTypes.includes('all'));
    if (!pool.length) pool = list;
    return pool[0];
  }

  function pickByBudget(list, budget) {
    return list.find(i => i.budget === budget) || list[0];
  }

  function getGroomingPlan(skinType, budget, hairType, hairConcern) {
    return {
      skincare: {
        cleanser:    pickItem(GROOMING_DATA.cleanser,    budget, skinType),
        moisturizer: pickItem(GROOMING_DATA.moisturizer, budget, skinType),
        sunscreen:   pickItem(GROOMING_DATA.sunscreen,   budget, skinType),
        treatment:   pickItem(GROOMING_DATA.treatment,   budget, skinType),
      },
      hair: {
        shampoo:    pickByBudget(HAIR_DATA.shampoo, budget),
        conditioner:pickByBudget(HAIR_DATA.conditioner, budget),
        oil:        pickByBudget(HAIR_DATA.oil, budget),
        washFreq:   hairType === 'curly' || hairType === 'coily' ? '2x/week' : (hairConcern === 'oily' ? 'Every alternate day' : '3x/week'),
      },
      men: {
        beard:        pickByBudget(MEN_DATA.beard, budget),
        shaving:      pickByBudget(MEN_DATA.shaving, budget),
        hairGrooming: pickByBudget(MEN_DATA.hairGrooming, budget),
      }
    };
  }

  // ======================================================
  // RENDER HELPERS
  // ======================================================
  function stepLi(n, title, item) {
    return `<li><span class="step-num">${n}</span><div><strong>${title}</strong>
      <p>${item.name} — ${item.desc}<br><em style="opacity:.8">${item.use}</em></p></div></li>`;
  }

  function renderSkin(plan) {
    document.getElementById('morningRoutineList').innerHTML =
      stepLi(1, 'Cleanser', plan.skincare.cleanser) +
      stepLi(2, 'Moisturizer', plan.skincare.moisturizer) +
      stepLi(3, 'Sunscreen (recommended daily)', plan.skincare.sunscreen);

    document.getElementById('nightRoutineList').innerHTML =
      stepLi(1, 'Cleanser', plan.skincare.cleanser) +
      stepLi(2, 'Treatment (2-3x/week)', plan.skincare.treatment) +
      stepLi(3, 'Moisturizer', plan.skincare.moisturizer);

    document.getElementById('homeRemedyCard').innerHTML = `
      <h4>🥗 Home Care &amp; Diet</h4>
      <div class="diet-tags">
        <span class="diet-tag good">Vitamin C — Amla, Orange</span>
        <span class="diet-tag good">Omega 3 — Walnuts</span>
        <span class="diet-tag good">Water — 2.5–3L daily</span>
        <span class="diet-tag bad">Go easy on: Sugar</span>
        <span class="diet-tag bad">Go easy on: Oily food</span>
      </div>`;
  }

  function renderHair(plan, hairConcern) {
    document.getElementById('hairOilList').innerHTML =
      stepLi(1, 'Hair Oil', plan.hair.oil) +
      `<li><span class="step-num">2</span><div><strong>Leave for 1 hour</strong><p>Then wash with the shampoo below.</p></div></li>`;

    document.getElementById('hairWashList').innerHTML =
      stepLi(1, 'Shampoo', plan.hair.shampoo) +
      stepLi(2, 'Conditioner', plan.hair.conditioner);

    document.getElementById('hairWashFreq').textContent = plan.hair.washFreq;
    document.getElementById('hairIssueText').textContent = HAIR_CONCERN_TEXT[hairConcern] || HAIR_CONCERN_TEXT.none;
  }

  function renderMen(plan, gender) {
    const wrap = document.getElementById('mensGroomingWrap');
    if (gender !== 'Male') { wrap.style.display = 'none'; return; }
    wrap.style.display = '';
    document.getElementById('beardRoutineList').innerHTML =
      stepLi(1, 'Beard Care', plan.men.beard) +
      stepLi(2, 'Shaving & After-Shave', plan.men.shaving);
    document.getElementById('mensHairRoutineList').innerHTML =
      stepLi(1, 'Hair Grooming', plan.men.hairGrooming);
  }

  function renderProfileSummary(skinType, budget, gender) {
    document.getElementById('profileSummaryCard').innerHTML = `
      <div class="form-row" style="margin-bottom:0">
        <div><p style="font-size:.72rem;opacity:.6;text-transform:uppercase;letter-spacing:.05em">Gender</p><p style="font-weight:700">${gender}</p></div>
        <div><p style="font-size:.72rem;opacity:.6;text-transform:uppercase;letter-spacing:.05em">Skin Type</p><p style="font-weight:700;text-transform:capitalize">${skinType}</p></div>
      </div>
      <div class="form-row" style="margin-top:.8rem;margin-bottom:0">
        <div><p style="font-size:.72rem;opacity:.6;text-transform:uppercase;letter-spacing:.05em">Budget</p><p style="font-weight:700;text-transform:capitalize">${budget}</p></div>
        <div><p style="font-size:.72rem;opacity:.6"><a href="profile.html" style="color:var(--forest-mid);font-weight:600">Edit in Profile →</a></p></div>
      </div>`;
  }

  // ======================================================
  // WIRE-UP
  // ======================================================
  const skinTypeSel = document.getElementById('skinType');
  const budgetSel    = document.getElementById('skinBudget');
  const hairTypeSel  = document.getElementById('hairType');
  const hairIssueSel = document.getElementById('hairIssue');

  // Default from profile (session-only overrides from here)
  skinTypeSel.value = (profile.skinType || 'normal').toLowerCase();
  budgetSel.value   = (profile.budget   || 'medium').toLowerCase();
  const gender = profile.gender || 'Female';

  function regenerate() {
    const skinType    = skinTypeSel.value;
    const budget      = budgetSel.value;
    const hairType    = hairTypeSel.value;
    const hairConcern = hairIssueSel.value;

    const plan = getGroomingPlan(skinType, budget, hairType, hairConcern);
    renderProfileSummary(skinType, budget, gender);
    renderSkin(plan);
    renderHair(plan, hairConcern);
    renderMen(plan, gender);
  }

  [skinTypeSel, budgetSel, hairTypeSel, hairIssueSel].forEach(el => {
    el.addEventListener('change', regenerate);
  });

  regenerate(); // initial render

  // ── Tab switcher (unchanged behavior) ─────────────────
  const tabBtns = document.querySelectorAll('.tab-btn');
  const tabContents = document.querySelectorAll('.care-tab-content');
  tabBtns.forEach(function (btn) {
    btn.addEventListener('click', function () {
      const targetTab = btn.getAttribute('data-tab');
      tabBtns.forEach(b => b.classList.remove('active'));
      btn.classList.add('active');
      tabContents.forEach(c => c.classList.toggle('hidden', c.id !== targetTab));
    });
  });

});