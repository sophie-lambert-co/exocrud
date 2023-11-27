document.addEventListener("DOMContentLoaded", function () {
    // Fonction pour mettre à jour la liste des couleurs
    function updateColorList() {
        fetch("http://localhost:8080/colors")
            .then(response => response.json())
            .then(data => {
                const colorList = document.getElementById("color-list");
                colorList.innerHTML = "";

                data.forEach(color => {
                    const listItem = document.createElement("li");
                    listItem.textContent = `ID: ${color.id}, Nom: ${color.name}, Hex: ${color.hex}`;
                    colorList.appendChild(listItem);
                });
            })
            .catch(error => console.error("Erreur lors de la récupération des couleurs:", error));
    }

    // Fonction pour ajouter une couleur
    function addColor() {
        const colorNameInput = document.getElementById("color-name");
        const colorHexInput = document.getElementById("color-hex");

        const newName = colorNameInput.value;
        const newHex = colorHexInput.value;

        fetch("http://localhost:8080/couleur", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({ name: newName, hex: newHex }),
        })
            .then(response => response.json())
            .then(data => {
                console.log("Nouvelle couleur ajoutée:", data);
                updateColorList();
            })
            .catch(error => console.error("Erreur lors de l'ajout de la couleur:", error));

        colorNameInput.value = "";
        colorHexInput.value = "";
    }

    // Fonction pour mettre à jour une couleur
    function updateColor() {
        const updateColorIdInput = document.getElementById("update-color-id");
        const updateColorNameInput = document.getElementById("update-color-name");

        const updateId = updateColorIdInput.value;
        const updateName = updateColorNameInput.value;

        fetch(`http://localhost:8080/couleur/${updateId}`, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({ name: updateName }),
        })
            .then(response => response.json())
            .then(data => {
                console.log("Couleur mise à jour:", data);
                updateColorList();
            })
            .catch(error => console.error("Erreur lors de la mise à jour de la couleur:", error));

        updateColorIdInput.value = "";
        updateColorNameInput.value = "";
    }

    // Fonction pour supprimer une couleur
    function deleteColor() {
        const deleteColorIdInput = document.getElementById("delete-color-id");
        const deleteId = deleteColorIdInput.value;

        fetch(`http://localhost:8080/couleur/${deleteId}`, {
            method: "DELETE",
        })
            .then(response => response.json())
            .then(data => {
                console.log("Couleur supprimée:", data);
                updateColorList();
            })
            .catch(error => console.error("Erreur lors de la suppression de la couleur:", error));

        deleteColorIdInput.value = "";
    }

    // Fonction pour mettre à jour la liste des aliments
    function updateFoodList() {
        fetch("http://localhost:8080/aliments")
            .then(response => response.json())
            .then(data => {
                const foodList = document.getElementById("food-list");
                foodList.innerHTML = "";

                data.forEach(food => {
                    const listItem = document.createElement("li");
                    listItem.textContent = `ID: ${food.id}, Nom: ${food.name}, Poids: ${food.weight}, Calories: ${food.calories}, Vitamines C: ${food.vitamins}, ID Type: ${food.typeId}, ID Couleur: ${food.colorId}`;
                    foodList.appendChild(listItem);
                });
            })
            .catch(error => console.error("Erreur lors de la récupération des aliments:", error));
    }

    // Fonction pour ajouter un aliment
    function addFood() {
        const foodNameInput = document.getElementById("food-name");
        const foodWeightInput = document.getElementById("food-weight");
        const foodCaloriesInput = document.getElementById("food-calories");
        const foodVitaminsInput = document.getElementById("food-vitamins");
        const foodTypeIdInput = document.getElementById("food-type-id");
        const foodColorIdInput = document.getElementById("food-color-id");

        const newName = foodNameInput.value;
        const newWeight = foodWeightInput.value;
        const newCalories = foodCaloriesInput.value;
        const newVitamins = foodVitaminsInput.value;
        const newTypeId = foodTypeIdInput.value;
        const newColorId = foodColorIdInput.value;

        fetch("http://localhost:8080/aliments", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({
                name: newName,
                weight: newWeight,
                calories: newCalories,
                vitamins: newVitamins,
                typeId: newTypeId,
                colorId: newColorId,
            }),
        })
            .then(response => response.json())
            .then(data => {
                console.log("Nouvel aliment ajouté:", data);
                updateFoodList();
            })
            .catch(error => console.error("Erreur lors de l'ajout de l'aliment:", error));

        // Efface les champs du formulaire après l'ajout
        foodNameInput.value = "";
        foodWeightInput.value = "";
        foodCaloriesInput.value = "";
        foodVitaminsInput.value = "";
        foodTypeIdInput.value = "";
        foodColorIdInput.value = "";
    }

    // Fonction pour mettre à jour un aliment
    function updateFood() {
        const updateFoodIdInput = document.getElementById("update-food-id");
        const updateFoodNameInput = document.getElementById("update-food-name");
        const updateFoodWeightInput = document.getElementById("update-food-weight");
        const updateFoodCaloriesInput = document.getElementById("update-food-calories");
        const updateFoodVitaminsInput = document.getElementById("update-food-vitamins");
        const updateFoodTypeIdInput = document.getElementById("update-food-type-id");
        const updateFoodColorIdInput = document.getElementById("update-food-color-id");

        const updateId = updateFoodIdInput.value;
        const updateName = updateFoodNameInput.value;
        const updateWeight = updateFoodWeightInput.value;
        const updateCalories = updateFoodCaloriesInput.value;
        const updateVitamins = updateFoodVitaminsInput.value;
        const updateTypeId = updateFoodTypeIdInput.value;
        const updateColorId = updateFoodColorIdInput.value;

        fetch(`http://localhost:8080/aliments/${updateId}`, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({
                name: updateName,
                weight: updateWeight,
                calories: updateCalories,
                vitamins: updateVitamins,
                typeId: updateTypeId,
                colorId: updateColorId,
            }),
        })
            .then(response => response.json())
            .then(data => {
                console.log("Aliment mis à jour:", data);
                updateFoodList();
            })
            .catch(error => console.error("Erreur lors de la mise à jour de l'aliment:", error));

        // Efface les champs du formulaire après la mise à jour
        updateFoodIdInput.value = "";
        updateFoodNameInput.value = "";
        updateFoodWeightInput.value = "";
        updateFoodCaloriesInput.value = "";
        updateFoodVitaminsInput.value = "";
        updateFoodTypeIdInput.value = "";
        updateFoodColorIdInput.value = "";
    }

    // Fonction pour supprimer un aliment
    function deleteFood() {
        const deleteFoodIdInput = document.getElementById("delete-food-id");
        const deleteId = deleteFoodIdInput.value;

        fetch(`http://localhost:8080/aliments/${deleteId}`, {
            method: "DELETE",
        })
            .then(response => response.json())
            .then(data => {
                console.log("Aliment supprimé:", data);
                updateFoodList();
            })
            .catch(error => console.error("Erreur lors de la suppression de l'aliment:", error));

        deleteFoodIdInput.value = "";
    }

    // Met à jour la liste des couleurs au chargement de la page
    updateColorList();

    // Met à jour la liste des aliments au chargement de la page
    updateFoodList();

    // Associe les fonctions aux boutons correspondants
    document.getElementById("add-color-button").addEventListener("click", addColor);
    document.getElementById("update-color-button").addEventListener("click", updateColor);
    document.getElementById("delete-color-button").addEventListener("click", deleteColor);
    document.getElementById("add-food-button").addEventListener("click", addFood);
    document.getElementById("update-food-button").addEventListener("click", updateFood);
    document.getElementById("delete-food-button").addEventListener("click", deleteFood);
});

