// ======================================================
// main.js - COMPLETE FIXED
// Mobile menu + navbar scroll + footer auto-inject
// ======================================================

document.addEventListener('DOMContentLoaded', function () {

  // ── Mobile menu ───────────────────────────────────
  const toggle   = document.getElementById('menuToggle');
  const navLinks = document.getElementById('navLinks');
  if (toggle && navLinks) {
    toggle.addEventListener('click', () => navLinks.classList.toggle('open'));
    navLinks.querySelectorAll('a').forEach(a =>
      a.addEventListener('click', () => navLinks.classList.remove('open'))
    );
  }

  // ── Navbar scroll glass ───────────────────────────
  const navbar = document.querySelector('.navbar');
  if (navbar) {
    window.addEventListener('scroll', () =>
      navbar.classList.toggle('scrolled', window.scrollY > 40)
    );
  }

  // ── Auto-mark active nav link ─────────────────────
  const page = window.location.pathname.split('/').pop() || 'index.html';
  document.querySelectorAll('.nav-links a').forEach(a => {
    if (a.getAttribute('href') === page) a.classList.add('active');
  });

  // ── Footer: always replace so it's consistent ─────
  const old = document.querySelector('footer');
  if (old) old.remove();

  const f = document.createElement('footer');
  f.innerHTML = `
    <div class="container">
      <div class="footer-grid">
        <div>
          <a href="index.html" class="logo" style="color:var(--cream)">
            <span class="logo-mark">🌿</span> SmartFit
          </a>
          <p style="opacity:.7;margin-top:.8rem;max-width:260px;font-size:.85rem;color:var(--cream)">
            A complete health and lifestyle planner built for real Indian budgets and kitchens.
          </p>
        </div>
        <div>
          <h4 style="color:var(--cream);margin-bottom:.7rem;font-size:.95rem">Plan</h4>
          <p><a href="diet.html">Diet Plan</a></p>
          <p><a href="workout.html">Workout</a></p>
          <p><a href="grocery.html">Grocery</a></p>
        </div>
        <div>
          <h4 style="color:var(--cream);margin-bottom:.7rem;font-size:.95rem">Care</h4>
          <p><a href="skincare.html">Skin Care</a></p>
          <p><a href="skincare.html">Hair Care</a></p>
          <p><a href="dashboard.html">Progress</a></p>
        </div>
        <div>
          <h4 style="color:var(--cream);margin-bottom:.7rem;font-size:.95rem">Account</h4>
          <p><a href="profile.html">Get Started</a></p>
          <p><a href="dashboard.html">Dashboard</a></p>
        </div>
      </div>
      <div class="footer-bottom">
        <span>© 2026 SmartFit. Built as a student project.</span>
        <span>Made with care in Maharashtra, India 🇮🇳</span>
      </div>
    </div>
  `;
  document.body.appendChild(f);
});