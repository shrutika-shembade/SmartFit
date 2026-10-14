// ======================================================
// rings.js
// Animated SVG progress rings for dashboard stat cards
// Pure JS - no library needed
// Usage: <div class="ring-wrap" data-pct="75" data-color="#1B4332">
// ======================================================

document.addEventListener('DOMContentLoaded', function () {

  const rings = document.querySelectorAll('.ring-wrap');
  if (!rings.length) return;

  rings.forEach(function (wrap) {
    const pct   = parseFloat(wrap.getAttribute('data-pct'))   || 0;
    const color = wrap.getAttribute('data-color') || '#1B4332';
    const size  = 80;
    const r     = 32;
    const circ  = 2 * Math.PI * r;

    // Build SVG
    const svg = document.createElementNS('http://www.w3.org/2000/svg', 'svg');
    svg.setAttribute('viewBox', '0 0 ' + size + ' ' + size);
    svg.setAttribute('width',  size);
    svg.setAttribute('height', size);
    svg.style.display = 'block';

    // Track circle
    const track = document.createElementNS('http://www.w3.org/2000/svg', 'circle');
    track.setAttribute('cx', size / 2);
    track.setAttribute('cy', size / 2);
    track.setAttribute('r',  r);
    track.setAttribute('fill', 'none');
    track.setAttribute('stroke', 'rgba(27,67,50,0.1)');
    track.setAttribute('stroke-width', '6');
    svg.appendChild(track);

    // Fill circle
    const fill = document.createElementNS('http://www.w3.org/2000/svg', 'circle');
    fill.setAttribute('cx', size / 2);
    fill.setAttribute('cy', size / 2);
    fill.setAttribute('r',  r);
    fill.setAttribute('fill', 'none');
    fill.setAttribute('stroke', color);
    fill.setAttribute('stroke-width', '6');
    fill.setAttribute('stroke-linecap', 'round');
    fill.setAttribute('stroke-dasharray', circ);
    fill.setAttribute('stroke-dashoffset', circ); // starts empty
    fill.setAttribute('transform',
      'rotate(-90 ' + (size/2) + ' ' + (size/2) + ')');
    fill.style.transition = 'stroke-dashoffset 1.2s cubic-bezier(0.4,0,0.2,1)';
    svg.appendChild(fill);

    wrap.insertBefore(svg, wrap.firstChild);

    // Animate when visible
    const obs = new IntersectionObserver(function (entries) {
      entries.forEach(function (entry) {
        if (entry.isIntersecting) {
          const offset = circ * (1 - pct / 100);
          fill.setAttribute('stroke-dashoffset', offset);
          obs.unobserve(wrap);
        }
      });
    }, { threshold: 0.5 });
    obs.observe(wrap);
  });

});