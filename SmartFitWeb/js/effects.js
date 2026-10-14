// ======================================================
// effects.js
// Shared 3D effects for all pages:
//   - Card tilt on mouse hover (CSS 3D transforms)
//   - Scroll reveal animations
//   - Animated counter numbers
//   - Progress bar fill animation
//   - Floating label animations
//   - Cursor glow
// No external library needed - pure JS + CSS transforms
//
// FIXED: selector lists referenced classes (.dash-panel)
// that don't exist in the actual dashboard markup, and were
// missing classes that do (.panel, .analysis-card, .stat-card)
// — so most of these effects were silently doing nothing.
// ======================================================

document.addEventListener('DOMContentLoaded', function () {

  // ── 1. 3D CARD TILT ──────────────────────────────────
  const tiltTargets = document.querySelectorAll(
    '.tilt-card, .card, .feature-row, .meal-card, ' +
    '.exercise-card, .grocery-category, .routine-card, ' +
    '.stat-card, .panel, .analysis-card'
  );

  tiltTargets.forEach(function (el) {
    el.addEventListener('mousemove', function (e) {
      const rect = el.getBoundingClientRect();
      const cx = rect.left + rect.width  / 2;
      const cy = rect.top  + rect.height / 2;

      const dx = (e.clientX - cx) / (rect.width  / 2);
      const dy = (e.clientY - cy) / (rect.height / 2);

      const maxTilt = 6;
      const rotX = -dy * maxTilt;
      const rotY =  dx * maxTilt;

      el.style.transform =
        'perspective(800px) ' +
        'rotateX(' + rotX + 'deg) ' +
        'rotateY(' + rotY + 'deg) ' +
        'translateZ(4px)';

      el.style.transition = 'transform 0.1s ease';
      el.style.willChange = 'transform';
    });

    el.addEventListener('mouseleave', function () {
      el.style.transform =
        'perspective(800px) rotateX(0) rotateY(0) translateZ(0)';
      el.style.transition = 'transform 0.4s ease';
    });
  });

  // ── 2. SCROLL REVEAL ────────────────────────────────
  const style = document.createElement('style');
  style.textContent = `
    .reveal {
      opacity: 0;
      transform: translateY(30px);
      transition: opacity 0.65s ease, transform 0.65s ease;
    }
    .reveal.visible {
      opacity: 1;
      transform: translateY(0);
    }
    .reveal-delay-1 { transition-delay: 0.1s; }
    .reveal-delay-2 { transition-delay: 0.2s; }
    .reveal-delay-3 { transition-delay: 0.3s; }
    .reveal-delay-4 { transition-delay: 0.4s; }
  `;
  document.head.appendChild(style);

  const revealTargets = document.querySelectorAll(
    '.card, .feature-row, .meal-card, .stat-card, .panel, .analysis-card, ' +
    '.exercise-card, .routine-card, .grocery-category, ' +
    '.section-head, .hero-trust, .savings-card'
  );

  revealTargets.forEach(function (el, i) {
    el.classList.add('reveal');
    if (i % 4 === 1) el.classList.add('reveal-delay-1');
    if (i % 4 === 2) el.classList.add('reveal-delay-2');
    if (i % 4 === 3) el.classList.add('reveal-delay-3');
  });

  const observer = new IntersectionObserver(function (entries) {
    entries.forEach(function (entry) {
      if (entry.isIntersecting) {
        entry.target.classList.add('visible');
        observer.unobserve(entry.target);
      }
    });
  }, { threshold: 0.12 });

  document.querySelectorAll('.reveal').forEach(function (el) {
    observer.observe(el);
  });

  // ── 3. ANIMATED STAT COUNTERS ────────────────────────
  function animateCounter(el) {
    const target   = parseFloat(el.getAttribute('data-count'));
    const suffix   = el.getAttribute('data-suffix') || '';
    const decimal  = el.getAttribute('data-decimal') === 'true';
    const duration = 1400;
    const start    = performance.now();

    function step(now) {
      const elapsed  = now - start;
      const progress = Math.min(elapsed / duration, 1);
      const eased    = 1 - Math.pow(1 - progress, 3);
      const current  = target * eased;

      el.textContent = decimal
        ? current.toFixed(1) + suffix
        : Math.round(current) + suffix;

      if (progress < 1) requestAnimationFrame(step);
    }
    requestAnimationFrame(step);
  }

  const counters = document.querySelectorAll('[data-count]');
  if (counters.length) {
    const counterObs = new IntersectionObserver(function (entries) {
      entries.forEach(function (entry) {
        if (entry.isIntersecting) {
          animateCounter(entry.target);
          counterObs.unobserve(entry.target);
        }
      });
    }, { threshold: 0.5 });
    counters.forEach(function (el) { counterObs.observe(el); });
  }

  // ── 4. PROGRESS BAR FILL ANIMATION ──────────────────
  const bars = document.querySelectorAll('.stat-bar-fill[data-width]');
  const barObs = new IntersectionObserver(function (entries) {
    entries.forEach(function (entry) {
      if (entry.isIntersecting) {
        const el = entry.target;
        const w  = el.getAttribute('data-width');
        setTimeout(function () {
          el.style.width = w + '%';
        }, 200);
        barObs.unobserve(el);
      }
    });
  }, { threshold: 0.5 });
  bars.forEach(function (el) { barObs.observe(el); });

  // ── 5. FLOATING BADGE ANIMATION ─────────────────────
  const floatStyle = document.createElement('style');
  floatStyle.textContent = `
    @keyframes floatY {
      0%, 100% { transform: translateY(0px); }
      50%       { transform: translateY(-6px); }
    }
    .trust-item { animation: floatY 3.5s ease-in-out infinite; }
    .trust-item:nth-child(2) { animation-delay: 0.6s; }
    .trust-item:nth-child(3) { animation-delay: 1.2s; }
    .thali-tag { animation: floatY 4s ease-in-out infinite; }
    .thali-tag.tag-2 { animation-delay: 0.8s; }
    .thali-tag.tag-3 { animation-delay: 1.6s; }
    .thali-tag.tag-4 { animation-delay: 2.4s; }
    .logo-mark { animation: floatY 2.5s ease-in-out infinite; }
  `;
  document.head.appendChild(floatStyle);

  // ── 6. SMOOTH MAGNETIC BUTTONS ───────────────────────
  document.querySelectorAll('.btn-primary').forEach(function (btn) {
    btn.addEventListener('mousemove', function (e) {
      const rect = btn.getBoundingClientRect();
      const cx = rect.left + rect.width  / 2;
      const cy = rect.top  + rect.height / 2;
      const dx = (e.clientX - cx) * 0.25;
      const dy = (e.clientY - cy) * 0.25;
      btn.style.transform =
        'translateX(' + dx + 'px) translateY(' + dy + 'px) translateZ(4px)';
    });
    btn.addEventListener('mouseleave', function () {
      btn.style.transform = '';
    });
  });

  // ── 7. CURSOR GLOW (desktop only) ────────────────────
  if (!('ontouchstart' in window)) {
    const glow = document.createElement('div');
    glow.id = 'cursorGlow';
    const glowStyle = document.createElement('style');
    glowStyle.textContent = `
      #cursorGlow {
        pointer-events: none;
        position: fixed;
        width: 300px;
        height: 300px;
        border-radius: 50%;
        background: radial-gradient(circle, rgba(45,106,79,0.06) 0%, transparent 70%);
        transform: translate(-50%, -50%);
        transition: left 0.12s ease, top 0.12s ease;
        z-index: 9999;
        mix-blend-mode: multiply;
      }
    `;
    document.head.appendChild(glowStyle);
    document.body.appendChild(glow);

    document.addEventListener('mousemove', function (e) {
      glow.style.left = e.clientX + 'px';
      glow.style.top  = e.clientY + 'px';
    });
  }

});