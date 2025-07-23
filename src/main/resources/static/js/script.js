document.addEventListener("DOMContentLoaded", () => {
  const form = document.getElementById("loginForm");
  const message = document.getElementById("message");

  form.addEventListener("submit", async (e) => {
    e.preventDefault();

    const username = document.getElementById("username").value.trim();
    const password = document.getElementById("password").value.trim();

    try {
      const response = await fetch("http://localhost:8080/auth/login", {
        method: "POST",
        headers: {
          "Content-Type": "application/json"
        },
        body: JSON.stringify({ username, password })
      });

      if (!response.ok) {
        const errorText = await response.text();
        throw new Error(errorText || "Invalid credentials");
      }

      const data = await response.json();

      // store the token
      localStorage.setItem("token", data.token);

      message.textContent = "🌿 Login successful! Redirecting...";
      message.style.color = "#3b7a57";

      // go to dashboard
      setTimeout(() => {
        window.location.href = "dashboard.html";
      }, 1000);
    } catch (error) {
      message.textContent = "🍂 " + error.message;
      message.style.color = "#b85c5c";
    }
  });

  // sign up modal
  const openSignupBtn       = document.getElementById("openSignupBtn");
  const signupModal         = document.getElementById("signupModal");
  const closeSignupModalBtn = document.getElementById("closeSignupModal");

  openSignupBtn.addEventListener("click", () => {
    signupModal.style.display = "flex";
  });
  closeSignupModalBtn.addEventListener("click", () => {
    signupModal.style.display = "none";
  });
  window.addEventListener("click", e => {
    if (e.target === signupModal) signupModal.style.display = "none";
  });

  // send sign up data
  document.getElementById("signupForm").addEventListener("submit", async e => {
    e.preventDefault();
    const name     = document.getElementById("signupName").value.trim();
    const email    = document.getElementById("signupEmail").value.trim();
    const password = document.getElementById("signupPassword").value.trim();
    const msgEl    = document.getElementById("signupMessage");

    try {
      const res = await fetch("http://localhost:8080/users", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ name, email, password })
      });
      if (!res.ok) throw new Error(await res.text() || "Registration failed.");
      msgEl.style.color = "#3b7a57";
      msgEl.textContent = "✅ Account created! Please log in.";
      setTimeout(() => {
        signupModal.style.display = "none";
        msgEl.textContent = "";
      }, 1500);
    } catch (err) {
      msgEl.style.color = "#b85c5c";
      msgEl.textContent = "❌ " + err.message;
    }
  });


});