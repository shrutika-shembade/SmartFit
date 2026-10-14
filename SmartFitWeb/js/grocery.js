// ======================================================
// grocery.js - FIXED & DYNAMIC VERSION
// Generates grocery list from today's diet plan
// Fixes: Rs.0 bug, dynamic items, checkbox tracking
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

  const profile = SmartFit.loadProfile();
  const analysis = profile ? SmartFit.getAnalysis(profile) : null;
  const plan = (profile && analysis) ? SmartFit.getTodayPlan(profile, analysis) : null;

  // ── Regional price database ──────────────────────
  const PRICES = {
    'Lemon'         : { All:5,    Maharashtra:5,  NorthIndia:4,  SouthIndia:4  },
    'Jeera'         : { All:300,  Maharashtra:280, NorthIndia:260, SouthIndia:270 },
    'Oats'          : { All:180,  Maharashtra:180, NorthIndia:175, SouthIndia:178 },
    'Moong'         : { All:130,  Maharashtra:125, NorthIndia:120, SouthIndia:125 },
    'Spinach'       : { All:30,   Maharashtra:28,  NorthIndia:25,  SouthIndia:28 },
    'Beetroot'      : { All:35,   Maharashtra:32,  NorthIndia:30,  SouthIndia:30 },
    'Onion'         : { All:30,   Maharashtra:28,  NorthIndia:25,  SouthIndia:25 },
    'Tomato'        : { All:35,   Maharashtra:32,  NorthIndia:28,  SouthIndia:28 },
    'Oil'           : { All:130,  Maharashtra:125, NorthIndia:120, SouthIndia:122 },
    'Mustard Seeds' : { All:180,  Maharashtra:170, NorthIndia:160, SouthIndia:165 },
    'Turmeric'      : { All:200,  Maharashtra:190, NorthIndia:180, SouthIndia:185 },
    'Salt'          : { All:20,   Maharashtra:18,  NorthIndia:18,  SouthIndia:18 },
    'Poha'          : { All:55,   Maharashtra:50,  NorthIndia:52,  SouthIndia:53 },
    'Rice'          : { All:60,   Maharashtra:58,  NorthIndia:55,  SouthIndia:55 },
    'Dal'           : { All:120,  Maharashtra:115, NorthIndia:110, SouthIndia:112 },
    'Banana'        : { All:3,    Maharashtra:3,   NorthIndia:2,   SouthIndia:3  },
    'Paneer'        : { All:380,  Maharashtra:360, NorthIndia:350, SouthIndia:365 },
    'Curd'          : { All:60,   Maharashtra:55,  NorthIndia:52,  SouthIndia:54 },
    'Milk'          : { All:55,   Maharashtra:52,  NorthIndia:50,  SouthIndia:52 },
    'Dates'         : { All:400,  Maharashtra:380, NorthIndia:370, SouthIndia:380 },
    'Almonds'       : { All:800,  Maharashtra:780, NorthIndia:770, SouthIndia:775 },
    'Peanuts'       : { All:120,  Maharashtra:115, NorthIndia:110, SouthIndia:112 },
    'Honey'         : { All:500,  Maharashtra:480, NorthIndia:470, SouthIndia:475 },
    'Besan'         : { All:70,   Maharashtra:68,  NorthIndia:65,  SouthIndia:67 },
    'Jowar Flour'   : { All:50,   Maharashtra:48,  NorthIndia:46,  SouthIndia:47 },
    'Idli Batter'   : { All:60,   Maharashtra:55,  NorthIndia:55,  SouthIndia:50 },
    'Makhana'       : { All:700,  Maharashtra:680, NorthIndia:670, SouthIndia:675 },
    'Sprouts'       : { All:40,   Maharashtra:38,  NorthIndia:35,  SouthIndia:37 },
    'Default'       : { All:50,   Maharashtra:48,  NorthIndia:46,  SouthIndia:47 },
  };

  function getPrice(ingredient, region) {
    const p = PRICES[ingredient] || PRICES['Default'];
    return p[region] || p['All'] || 50;
  }

  // ── Build grocery items from diet plan ──────────
  const region = profile?.region || 'All';

  // Ingredient database - meal → ingredients
  const INGREDIENTS = {
    'Poha'          : [['Poha',80,'g','Grains'],['Onion',50,'g','Vegetables'],['Mustard Seeds',1,'tsp','Spices'],['Oil',10,'ml','Oils'],['Turmeric',0.5,'tsp','Spices'],['Lemon',0.5,'piece','Fruits']],
    'Oats'          : [['Oats',80,'g','Grains'],['Milk',150,'ml','Dairy'],['Banana',0.5,'piece','Fruits'],['Honey',1,'tsp','Spices']],
    'Sprouts'       : [['Moong',80,'g','Protein'],['Onion',30,'g','Vegetables'],['Lemon',0.5,'piece','Fruits'],['Salt',1,'pinch','Spices']],
    'Moong Chilla'  : [['Moong',80,'g','Protein'],['Onion',30,'g','Vegetables'],['Tomato',30,'g','Vegetables'],['Oil',8,'ml','Oils'],['Salt',1,'pinch','Spices']],
    'Besan Chilla'  : [['Besan',80,'g','Grains'],['Onion',30,'g','Vegetables'],['Tomato',30,'g','Vegetables'],['Oil',8,'ml','Oils']],
    'Dal Rice'      : [['Rice',100,'g','Grains'],['Dal',60,'g','Protein'],['Tomato',40,'g','Vegetables'],['Onion',40,'g','Vegetables'],['Oil',10,'ml','Oils'],['Turmeric',0.5,'tsp','Spices'],['Salt',1,'pinch','Spices']],
    'Khichdi'       : [['Rice',80,'g','Grains'],['Dal',60,'g','Protein'],['Turmeric',0.5,'tsp','Spices'],['Salt',1,'pinch','Spices']],
    'Moong Dal'     : [['Moong',80,'g','Protein'],['Onion',40,'g','Vegetables'],['Tomato',40,'g','Vegetables'],['Oil',8,'ml','Oils'],['Turmeric',0.5,'tsp','Spices']],
    'Jowar Bhakri'  : [['Jowar Flour',120,'g','Grains'],['Salt',1,'pinch','Spices']],
    'Palak Paneer'  : [['Spinach',100,'g','Vegetables'],['Paneer',80,'g','Dairy'],['Onion',40,'g','Vegetables'],['Tomato',40,'g','Vegetables'],['Oil',10,'ml','Oils']],
    'Vegetable Soup': [['Onion',50,'g','Vegetables'],['Tomato',100,'g','Vegetables'],['Oil',5,'ml','Oils'],['Salt',1,'pinch','Spices']],
    'Dal Khichdi'   : [['Rice',80,'g','Grains'],['Dal',60,'g','Protein'],['Turmeric',0.5,'tsp','Spices'],['Salt',1,'pinch','Spices']],
    'Moong Khichdi' : [['Rice',80,'g','Grains'],['Moong',60,'g','Protein'],['Salt',1,'pinch','Spices']],
    'Jowar Roti'    : [['Jowar Flour',120,'g','Grains'],['Salt',1,'pinch','Spices']],
    'Lemon Water'   : [['Lemon',1,'piece','Fruits'],['Honey',1,'tsp','Spices']],
    'Green Tea'     : [['Honey',0.5,'tsp','Spices']],
    'Buttermilk'    : [['Curd',100,'g','Dairy'],['Salt',1,'pinch','Spices']],
    'Beetroot Juice': [['Beetroot',100,'g','Vegetables'],['Lemon',0.5,'piece','Fruits']],
    'Spinach Juice' : [['Spinach',100,'g','Vegetables'],['Lemon',0.5,'piece','Fruits']],
    'Roasted Chana' : [['Peanuts',50,'g','Protein']],
    'Makhana'       : [['Makhana',30,'g','Other']],
    'Dates'         : [['Dates',4,'piece','Fruits']],
    'Fruit Bowl'    : [['Banana',1,'piece','Fruits']],
    'Banana'        : [['Banana',2,'piece','Fruits']],
  };

  // Collect all ingredients from plan
  const merged = {}; // key → {name, qty, unit, category, pricePerUnit}

  if (plan) {
    const meals = [plan.detox, plan.breakfast, plan.lunch, plan.snack, plan.dinner, plan.preWorkout, plan.postWorkout];
    meals.forEach(meal => {
      if (!meal) return;
      const ings = INGREDIENTS[meal.name] || [];
      ings.forEach(([name, qty, unit, cat]) => {
        const key = name + '_' + unit;
        if (merged[key]) {
          merged[key].qty += qty;
        } else {
          merged[key] = { name, qty, unit, category: cat };
        }
      });
    });
  }

  // If no plan data, use sample data
  const hasMerged = Object.keys(merged).length > 0;
  const items = hasMerged ? Object.values(merged) : [
    { name:'Jowar Flour',  qty:120, unit:'g',     category:'Grains'     },
    { name:'Makhana',      qty:30,  unit:'g',     category:'Grains'     },
    { name:'Moong',        qty:140, unit:'g',     category:'Protein'    },
    { name:'Besan',        qty:60,  unit:'g',     category:'Grains'     },
    { name:'Onion',        qty:80,  unit:'g',     category:'Vegetables' },
    { name:'Tomato',       qty:40,  unit:'g',     category:'Vegetables' },
    { name:'Curd',         qty:100, unit:'g',     category:'Dairy'      },
    { name:'Banana',       qty:2,   unit:'pieces', category:'Fruits'    },
    { name:'Lemon',        qty:1,   unit:'piece',  category:'Fruits'    },
    { name:'Mustard Seeds',qty:1,   unit:'tsp',   category:'Spices'     },
    { name:'Turmeric',     qty:1,   unit:'tsp',   category:'Spices'     },
    { name:'Oil',          qty:10,  unit:'ml',    category:'Oils'       },
    { name:'Salt',         qty:2,   unit:'pinch', category:'Spices'     },
    { name:'Dates',        qty:4,   unit:'pieces', category:'Fruits'    },
  ];

  // Calculate cost per item
  function calcCost(item) {
    const pricePerKg = getPrice(item.name, region);
    let cost = 0;
    switch (item.unit) {
      case 'g':      cost = (item.qty / 1000) * pricePerKg; break;
      case 'kg':     cost = item.qty * pricePerKg; break;
      case 'ml':     cost = (item.qty / 1000) * pricePerKg; break;
      case 'L':      cost = item.qty * pricePerKg; break;
      case 'piece':
      case 'pieces': cost = item.qty * pricePerKg; break;
      case 'tsp':    cost = (item.qty * 5 / 1000) * pricePerKg; break;
      case 'tbsp':   cost = (item.qty * 15 / 1000) * pricePerKg; break;
      case 'pinch':  cost = 1; break;
      default:       cost = item.qty * 5; break;
    }
    return Math.max(Math.round(cost), 1);
  }

  // Group by category
  const byCategory = {};
  items.forEach(item => {
    const cat = item.category || 'Other';
    if (!byCategory[cat]) byCategory[cat] = [];
    byCategory[cat].push(item);
  });

  const catIcons = {
    'Grains':'🌾', 'Protein':'💪', 'Dairy':'🥛',
    'Vegetables':'🥬', 'Fruits':'🍎', 'Oils':'🫙',
    'Spices':'🌶️', 'Other':'📦'
  };

  // Render categories
  const gridEl    = document.getElementById('groceryGrid');
  const totalEl   = document.getElementById('totalCost');
  const countEl   = document.getElementById('totalCount');
  const purchEl   = document.getElementById('purchasedCount');
  const regionEl  = document.getElementById('groceryRegion');
  const budgetEl  = document.getElementById('groceryBudget');

  if (regionEl) regionEl.textContent = region + ' pricing';
  if (budgetEl && profile) budgetEl.textContent = profile.budget + ' budget';

  if (!gridEl) return;

  let grandTotal = 0;
  let totalItems = 0;
  gridEl.innerHTML = '';

  Object.keys(byCategory).sort().forEach(cat => {
    const catItems = byCategory[cat];
    const div = document.createElement('div');
    div.className = 'grocery-category';

    let html = `<h3>${catIcons[cat] || '📦'} ${cat}</h3><ul class="item-list">`;
    catItems.forEach((item, idx) => {
      const cost = calcCost(item);
      grandTotal += cost;
      totalItems++;
      const id = 'gi_' + totalItems;
      const qtyStr = item.qty + ' ' + item.unit;
      html += `
        <li class="grocery-item">
          <input type="checkbox" id="${id}" onchange="updatePurchased()">
          <label for="${id}">
            <span class="item-name">${item.name}</span>
            <span class="item-qty">${qtyStr}</span>
          </label>
          <span class="item-cost mono-stat">₹${cost}</span>
        </li>`;
    });
    html += '</ul>';
    div.innerHTML = html;
    gridEl.appendChild(div);
  });

  // Pantry card
  const pantry = document.createElement('div');
  pantry.className = 'pantry-card';
  pantry.innerHTML = `
    <h3>🏠 Check Your Pantry First</h3>
    <p>You might already have these — saves money before you shop:</p>
    <ul class="pantry-list">
      <li>Salt</li><li>Turmeric</li><li>Cumin</li>
      <li>Mustard Seeds</li><li>Oil</li><li>Ghee</li>
      <li>Onion</li><li>Garlic</li><li>Ginger</li>
    </ul>`;
  gridEl.appendChild(pantry);

  // Update totals
  if (totalEl)   totalEl.textContent  = '₹' + grandTotal;
  if (countEl)   countEl.textContent  = totalItems;
  if (purchEl)   purchEl.textContent  = '0';

  // Budget badge
  const budgEl = document.getElementById('budgetBadge');
  if (budgEl) {
    if (grandTotal <= 300)      { budgEl.textContent = 'Budget Friendly'; budgEl.className = 'badge badge-low'; }
    else if (grandTotal <= 700) { budgEl.textContent = 'Moderate';        budgEl.className = 'badge badge-medium'; }
    else                        { budgEl.textContent = 'High';            budgEl.className = 'badge badge-high'; }
  }
});

function updatePurchased() {
  const checked = document.querySelectorAll('.grocery-item input:checked').length;
  const el = document.getElementById('purchasedCount');
  if (el) el.textContent = checked;
}