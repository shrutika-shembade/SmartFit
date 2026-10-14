// ======================================================
// three-bg.js
// Three.js floating particle background for homepage hero
// Gives a modern, premium fitness app feel
// Library: Three.js r128 from CDN
// ======================================================

(function () {

  // Only run if canvas container exists on this page
  const container = document.getElementById('threeBg');
  if (!container) return;

  // ── Scene setup ─────────────────────────────────────
  const scene    = new THREE.Scene();
  const camera   = new THREE.PerspectiveCamera(
    60,
    container.clientWidth / container.clientHeight,
    0.1,
    1000
  );
  camera.position.z = 30;

  const renderer = new THREE.WebGLRenderer({
    alpha: true,       // transparent background
    antialias: true
  });
  renderer.setSize(container.clientWidth, container.clientHeight);
  renderer.setPixelRatio(Math.min(window.devicePixelRatio, 2));
  container.appendChild(renderer.domElement);

  // ── Create floating particles ────────────────────────
  const particleCount = 120;
  const geometry = new THREE.BufferGeometry();
  const positions = new Float32Array(particleCount * 3);
  const sizes     = new Float32Array(particleCount);

  for (let i = 0; i < particleCount; i++) {
    positions[i * 3]     = (Math.random() - 0.5) * 80; // x
    positions[i * 3 + 1] = (Math.random() - 0.5) * 60; // y
    positions[i * 3 + 2] = (Math.random() - 0.5) * 40; // z
    sizes[i] = Math.random() * 1.5 + 0.3;
  }

  geometry.setAttribute('position',
    new THREE.BufferAttribute(positions, 3));
  geometry.setAttribute('size',
    new THREE.BufferAttribute(sizes, 1));

  // Circular sprite texture drawn on canvas
  const canvas = document.createElement('canvas');
  canvas.width = 32; canvas.height = 32;
  const ctx = canvas.getContext('2d');
  const grad = ctx.createRadialGradient(16, 16, 0, 16, 16, 16);
  grad.addColorStop(0,   'rgba(212, 163, 115, 1)');   // turmeric center
  grad.addColorStop(0.4, 'rgba(212, 163, 115, 0.6)');
  grad.addColorStop(1,   'rgba(212, 163, 115, 0)');
  ctx.fillStyle = grad;
  ctx.fillRect(0, 0, 32, 32);

  const texture = new THREE.CanvasTexture(canvas);

  const material = new THREE.PointsMaterial({
    size: 0.8,
    map: texture,
    transparent: true,
    depthWrite: false,
    blending: THREE.AdditiveBlending,
    opacity: 0.75
  });

  const particles = new THREE.Points(geometry, material);
  scene.add(particles);

  // ── Floating geometric shapes ────────────────────────
  // Small icosahedra scattered around for depth
  const shapes = [];
  const shapeGeo = new THREE.IcosahedronGeometry(0.4, 0);
  const shapeMat = new THREE.MeshBasicMaterial({
    color: 0x2D6A4F,
    wireframe: true,
    opacity: 0.25,
    transparent: true
  });

  for (let i = 0; i < 12; i++) {
    const mesh = new THREE.Mesh(shapeGeo, shapeMat);
    mesh.position.set(
      (Math.random() - 0.5) * 60,
      (Math.random() - 0.5) * 40,
      (Math.random() - 0.5) * 20
    );
    mesh.rotation.set(
      Math.random() * Math.PI,
      Math.random() * Math.PI,
      0
    );
    mesh.userData.rotSpeed = {
      x: (Math.random() - 0.5) * 0.008,
      y: (Math.random() - 0.5) * 0.008
    };
    mesh.userData.floatSpeed = Math.random() * 0.0008 + 0.0003;
    mesh.userData.floatOffset = Math.random() * Math.PI * 2;
    scene.add(mesh);
    shapes.push(mesh);
  }

  // ── Mouse parallax ───────────────────────────────────
  let mouseX = 0, mouseY = 0;
  document.addEventListener('mousemove', function (e) {
    mouseX = (e.clientX / window.innerWidth  - 0.5) * 0.3;
    mouseY = (e.clientY / window.innerHeight - 0.5) * 0.3;
  });

  // ── Animation loop ───────────────────────────────────
  let clock = 0;
  function animate() {
    requestAnimationFrame(animate);
    clock += 0.005;

    // Slowly rotate particle field
    particles.rotation.y += 0.0008;
    particles.rotation.x += 0.0003;

    // Float shapes
    shapes.forEach(function (mesh) {
      mesh.rotation.x += mesh.userData.rotSpeed.x;
      mesh.rotation.y += mesh.userData.rotSpeed.y;
      mesh.position.y +=
        Math.sin(clock + mesh.userData.floatOffset)
        * mesh.userData.floatSpeed;
    });

    // Smooth camera parallax on mouse
    camera.position.x += (mouseX * 8 - camera.position.x) * 0.05;
    camera.position.y += (-mouseY * 5 - camera.position.y) * 0.05;
    camera.lookAt(scene.position);

    renderer.render(scene, camera);
  }

  animate();

  // ── Resize handler ───────────────────────────────────
  window.addEventListener('resize', function () {
    camera.aspect = container.clientWidth / container.clientHeight;
    camera.updateProjectionMatrix();
    renderer.setSize(container.clientWidth, container.clientHeight);
  });

})();