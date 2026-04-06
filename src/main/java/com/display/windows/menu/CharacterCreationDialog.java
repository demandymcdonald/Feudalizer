package com.display.windows.menu;

import com.Global;
import com.objects.character.HumanCharacter;
import com.objects.character.CharacterManager;
import com.objects.government.House;
import com.base.DMRegistry;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class CharacterCreationDialog extends Dialog<HumanCharacter> {

    private final TextField givenNameField = new TextField();
    private final TextField surnameField = new TextField();
    private final ComboBox<HumanCharacter.Gender> genderCombo = new ComboBox<>();
    private final RadioButton commonerRadio = new RadioButton("Commoner");
    private final RadioButton nobleRadio = new RadioButton("Noble");
    private final ComboBox<House> houseCombo = new ComboBox<>();
    private final TextField newHouseField = new TextField();
    private final TextField dobField = new TextField();
    private final TextField dodField = new TextField();

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("MMM dd, yyyy");

    public CharacterCreationDialog() {
        setTitle("Create Character");
        setHeaderText("Create a new character");

        DialogPane dialogPane = getDialogPane();
        dialogPane.getStyleClass().add("character-dialog");

        ButtonType createButton = new ButtonType("Create", ButtonBar.ButtonData.OK_DONE);
        dialogPane.getButtonTypes().addAll(createButton, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));

        int row = 0;

        grid.add(new Label("Given Name:"), 0, row);
        grid.add(givenNameField, 1, row++);

        grid.add(new Label("Surname:"), 0, row);
        grid.add(surnameField, 1, row++);

        genderCombo.getItems().addAll(HumanCharacter.Gender.values());
        genderCombo.setValue(HumanCharacter.Gender.Male);
        grid.add(new Label("Gender:"), 0, row);
        grid.add(genderCombo, 1, row++);

        dobField.setPromptText("MMM dd, yyyy (e.g., Jan 15, 1990)");
        grid.add(new Label("Date of Birth:"), 0, row);
        grid.add(dobField, 1, row++);

        dodField.setPromptText("Leave blank if alive");
        grid.add(new Label("Date of Death:"), 0, row);
        grid.add(dodField, 1, row++);

        ToggleGroup statusGroup = new ToggleGroup();
        commonerRadio.setToggleGroup(statusGroup);
        nobleRadio.setToggleGroup(statusGroup);
        commonerRadio.setSelected(true);
        grid.add(new Label("Status:"), 0, row);
        grid.add(commonerRadio, 1, row++);
        grid.add(nobleRadio, 1, row++);

        houseCombo.getItems().addAll(DMRegistry.getHouseManager().getAll());
        houseCombo.setPromptText("Select existing house (optional)");
        grid.add(new Label("Existing House:"), 0, row);
        grid.add(houseCombo, 1, row++);

        newHouseField.setPromptText("Or create new house");
        grid.add(new Label("New House:"), 0, row);
        grid.add(newHouseField, 1, row++);

        houseCombo.setDisable(true);
        newHouseField.setDisable(true);

        statusGroup.selectedToggleProperty().addListener((obs, old, newVal) -> {
            boolean isNoble = newVal == nobleRadio;
            houseCombo.setDisable(!isNoble);
            newHouseField.setDisable(!isNoble);
        });

        dialogPane.setContent(grid);

        Button createBtn = (Button) dialogPane.lookupButton(createButton);
        createBtn.setDisable(true);

        givenNameField.textProperty().addListener((obs, old, newVal) -> validateForm(createBtn));
        surnameField.textProperty().addListener((obs, old, newVal) -> validateForm(createBtn));

        setResultConverter(dialogButton -> {
            if (dialogButton == createButton) {
                return createCharacter();
            }
            return null;
        });
    }

    private void validateForm(Button createBtn) {
        boolean valid = !givenNameField.getText().trim().isEmpty()
                && !surnameField.getText().trim().isEmpty();
        createBtn.setDisable(!valid);
    }

    private LocalDate parseDate(String dateStr, LocalDate defaultDate) {
        if (dateStr == null || dateStr.trim().isEmpty()) {
            return defaultDate;
        }

        try {
            return LocalDate.parse(dateStr.trim(), DATE_FORMAT);
        } catch (DateTimeParseException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Date");
            alert.setHeaderText("Date format error");
            alert.setContentText("Please use format: MMM dd, yyyy (e.g., Jan 15, 1990)");
            alert.showAndWait();
            return null;
        }
    }

    private HumanCharacter createCharacter() {
        String givenName = givenNameField.getText().trim();
        String surname = surnameField.getText().trim();
        HumanCharacter.Gender gender = genderCombo.getValue();

        LocalDate dob = parseDate(dobField.getText(), Global.CURRENT_DATE());
        if (dob == null) return null;

        LocalDate dod = parseDate(dodField.getText(), null);

        if (commonerRadio.isSelected()) {
            return CharacterManager.buildCommoner(givenName, surname, dob, dod, gender);
        } else {
            House existingHouse = houseCombo.getValue();
            String newHouseName = newHouseField.getText().trim();

            if (existingHouse != null) {
                return CharacterManager.buildNoble(givenName, surname, existingHouse, dob, dod, gender);
            } else if (!newHouseName.isEmpty()) {
                return CharacterManager.buildNoble(givenName, surname, newHouseName, dob, dod, gender);
            } else {
                return CharacterManager.buildNoble(givenName, surname, dob, dod, gender);
            }
        }
    }
}