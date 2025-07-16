let plants = [];
let isEditing = false;
let editingPlantId = null;

const plantList = document.getElementById("plantList");
const searchInput = document.getElementById("searchInput");

function renderPlants(filter = "") {
  plantList.innerHTML = "";

  const filtered = plants.filter((p) =>
    p.name.toLowerCase().includes(filter.toLowerCase())
  );

  filtered.forEach((plant) => {
    const li = document.createElement("li");
    li.className = "plant-item";

    li.innerHTML = `
      <div class="plant-details">
        <span class="plant-name">${plant.name}</span>
        <span class="plant-species">${plant.species}</span>
        <span class="plant-description">${plant.description || ""}</span>
      </div>

      <div class="water-status">
        ${
          plant.isWatered
            ? '<span style="color: #00bcd4;">Watered</span>'
            : '<span style="color: #4caf50;">Not Watered</span>'
        }
      </div>

      <div class="actions">
<button class="water-btn" data-id="${plant.id}" title="Toggle Watered">
 <i class="fas fa-tint" style="font-size: 1.2em; color: ${
   plant.isWatered ? "#00bcd4" : "#2e7d32"
 };"></i>
</button>
          <i class="fas fa-edit edit-icon" title="Edit" style="font-size: 1.2em; color: #2e7d32;" data-id="${
            plant.id
          }"></i>
          <i class="fas fa-trash delete-icon" title="Delete" style="font-size: 1.2em; color: #cc4444;" data-id="${
            plant.id
          }"></i>
      </div>
    `;

    plantList.appendChild(li);
  });

  // delete button
  document.querySelectorAll(".delete-icon").forEach((icon) => {
    icon.addEventListener("click", async (e) => {
      const id = e.target.getAttribute("data-id");
      await deletePlant(id);
    });
  });

  const editIcons = document.querySelectorAll(".edit-icon");
  editIcons.forEach((icon) => {
    icon.addEventListener("click", (e) => {
      const id = e.target.getAttribute("data-id");
      editPlant(id);
    });
  });

  // watered button
  document.querySelectorAll(".water-btn").forEach((btn) => {
    btn.addEventListener("click", async (e) => {
      const btn = e.currentTarget;
      const id = btn.getAttribute("data-id");
      const plant = plants.find((p) => p.id === parseInt(id));
      if (!plant) return;

      const token = localStorage.getItem("token");

      try {
        const response = await fetch(`http://localhost:8080/plants/${id}`, {
          method: "PUT",
          headers: {
            "Content-Type": "application/json",
            Authorization: "Bearer " + token,
          },
          body: JSON.stringify({
            ...plant,
            isWatered: !plant.isWatered,
          }),
        });

        if (!response.ok) throw new Error("Failed to update plant.");

        // refresh locally
        plant.isWatered = !plant.isWatered;
        renderPlants(searchInput.value);
      } catch (err) {
        alert("❌ Failed to update: " + err.message);
      }
    });
  });
}

async function toggleWatered(id, newStatus) {
  const token = localStorage.getItem("token");

  try {
    const response = await fetch(`http://localhost:8080/plants/${id}`, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
        Authorization: "Bearer " + token,
      },
      body: JSON.stringify({ isWatered: newStatus }),
    });

    if (!response.ok) throw new Error("Could not update plant.");

    fetchPlants();
  } catch (err) {
    alert("❌ Failed to update: " + err.message);
  }
}

const toggleButtons = document.querySelectorAll(".toggle-water-btn");
toggleButtons.forEach((btn) => {
  btn.addEventListener("click", async (e) => {
    const id = e.target.getAttribute("data-id");
    const plant = plants.find((p) => p.id === parseInt(id));
    const token = localStorage.getItem("token");

    try {
      const response = await fetch(`http://localhost:8080/plants/${id}`, {
        method: "PUT",
        headers: {
          "Content-Type": "application/json",
          Authorization: "Bearer " + token,
        },
        body: JSON.stringify({
          ...plant,
          isWatered: !plant.isWatered,
        }),
      });

      if (!response.ok) throw new Error("Could not update plant status.");

      fetchPlants(); // Reload updated list
    } catch (err) {
      alert("❌ Failed to update: " + err.message);
    }
  });
});

async function deletePlant(id) {
  const token = localStorage.getItem("token");

  try {
    const response = await fetch(`http://localhost:8080/plants/${id}`, {
      method: "DELETE",
      headers: {
        Authorization: "Bearer " + token,
      },
    });

    if (!response.ok) {
      throw new Error("Could not delete plant.");
    }

    plants = plants.filter((p) => p.id !== parseInt(id));
    renderPlants(searchInput.value);
  } catch (err) {
    alert("❌ Failed to delete: " + err.message);
  }
}

searchInput.addEventListener("input", (e) => {
  renderPlants(e.target.value);
});

document.getElementById("logoutBtn").addEventListener("click", () => {
  localStorage.removeItem("token");
  console.log("👋 Logging out...");
  setTimeout(() => {
    window.location.href = "index.html";
  }, 500); // meio segundo antes do redirecionamento
});

async function fetchPlants() {
  const token = localStorage.getItem("token");
  try {
    const response = await fetch("http://localhost:8080/users/plants", {
      headers: {
        Authorization: "Bearer " + token,
      },
    });
    plants = await response.json();
    renderPlants();
  } catch (err) {
    alert("Failed to load plants");
  }
}

fetchPlants();

// Open Add Plant modal
document.getElementById("addBtn").addEventListener("click", () => {
  document.getElementById("addModal").style.display = "block";
});

document
  .getElementById("addPlantForm")
  .addEventListener("submit", async (e) => {
    e.preventDefault();

    const name = document.getElementById("plantName").value.trim();
    const species = document.getElementById("plantSpecies").value.trim();
    const description = document
      .getElementById("plantDescription")
      .value.trim();
    const isWatered = document.getElementById("plantWatered").checked;
    const token = localStorage.getItem("token");

    const payload = { name, species, description, isWatered };

    try {
      let url = "http://localhost:8080/plants";
      let method = "POST";

      if (isEditing && editingPlantId) {
        url += `/${editingPlantId}`;
        method = "PUT";
      }

      const response = await fetch(url, {
        method,
        headers: {
          "Content-Type": "application/json",
          Authorization: "Bearer " + token,
        },
        body: JSON.stringify(payload),
      });

      if (!response.ok) throw new Error("Could not save plant.");

      // Reset modal and state
      document.getElementById("addModal").style.display = "none";
      document.getElementById("plantName").value = "";
      document.getElementById("plantSpecies").value = "";
      document.getElementById("plantDescription").value = "";
      document.getElementById("plantWatered").checked = false;
      isEditing = false;
      editingPlantId = null;
      document.querySelector("#addModal h3").textContent = "Add New Plant 🌿";
      document.querySelector("#addModal button[type='submit']").textContent =
        "Add";

      fetchPlants();
    } catch (err) {
      alert("❌ Failed to save: " + err.message);
    }
  });

fetchPlants();

function editPlant(id) {
  const plant = plants.find((p) => p.id === parseInt(id));
  if (!plant) return;

  isEditing = true;
  editingPlantId = id;

  document.getElementById("plantName").value = plant.name;
  document.getElementById("plantSpecies").value = plant.species;
  document.getElementById("plantDescription").value = plant.description || "";
  document.getElementById("plantWatered").checked = plant.isWatered;

  document.querySelector("#addModal h3").textContent = "Edit Plant 🌿";
  document.querySelector("#addModal button[type='submit']").textContent =
    "Save";
  document.getElementById("addModal").style.display = "block";
}
