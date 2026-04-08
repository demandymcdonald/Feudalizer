package com.display.windows.cards.edit;

import com.base.DMRegistry;
import com.objects.character.human.HumanCharacter;
import com.objects.character.CharacterManager;
import com.objects.government.House;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class CharacterEditCard extends BaseEditCard<HumanCharacter> {

    private final Label titleLabel = new Label();
    private final TextField givenNameField = new TextField();
    private final TextField surnameField = new TextField();
    private final ComboBox<HumanCharacter.Gender> genderCombo = new ComboBox<>();
    private final TextField dobField = new TextField();
    private final TextField dodField = new TextField();
    private final RadioButton commonerRadio = new RadioButton("Commoner");
    private final RadioButton nobleRadio = new RadioButton("Noble");
    private final ComboBox<House> houseCombo = new ComboBox<>();
    private final TextField newHouseField = new TextField();

    private HumanCharacter existingCharacter = null;

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("MMM dd, yyyy");

    public CharacterEditCard(boolean editMode) {
        super(editMode);
        init();
    }

    @Override
    protected Node buildHeader() {
        VBox header = new VBox(8);
        header.getStyleClass().add("card-header");

        titleLabel.setText(isEditMode() ? "Edit Character" : "Create Character");
        titleLabel.getStyleClass().add("card-title");

        header.getChildren().add(titleLabel);
        return header;
    }

    @Override
    protected Node buildBody(HumanCharacter data) {
        // buildBody isn't used directly here - we build it once and update via populate()
        return buildForm();
    }

    private Node buildForm() {
        VBox body = new VBox(16);
        body.setPadding(new Insets(24));

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(12);

        int row = 0;

        grid.add(new Label("Given Name:"), 0, row);
        grid.add(givenNameField, 1, row++);

        grid.add(new Label("Surname:"), 0, row);
        grid.add(surnameField, 1, row++);

        genderCombo.getItems().addAll(HumanCharacter.Gender.values());
        genderCombo.setValue(HumanCharacter.Gender.Male);
        grid.add(new Label("Gender:"), 0, row);
        grid.add(genderCombo, 1, row++);

        dobField.setPromptText("MMM dd, yyyy");
        grid.add(new Label("Date of Birth:"), 0, row);
        grid.add(dobField, 1, row++);

        dodField.setPromptText("Leave blank if alive");
        grid.add(new Label("Date of Death:"), 0, row);
        grid.add(dodField, 1, row++);

        // Noble/Commoner toggle
        ToggleGroup statusGroup = new ToggleGroup();
        commonerRadio.setToggleGroup(statusGroup);
        nobleRadio.setToggleGroup(statusGroup);
        commonerRadio.setSelected(true);
        grid.add(new Label("Status:"), 0, row);
        grid.add(commonerRadio, 1, row++);
        grid.add(nobleRadio, 1, row++);

        // House fields - disabled for commoners
        houseCombo.getItems().addAll(DMRegistry.getHouseManager().getAll());
        houseCombo.setPromptText("Select existing house");
        houseCombo.setDisable(true);
        grid.add(new Label("House:"), 0, row);
        grid.add(houseCombo, 1, row++);

        newHouseField.setPromptText("Or create new house");
        newHouseField.setDisable(true);
        grid.add(new Label("New House:"), 0, row);
        grid.add(newHouseField, 1, row++);

        statusGroup.selectedToggleProperty().addListener((obs, old, newVal) -> {
            boolean isNoble = newVal == nobleRadio;
            houseCombo.setDisable(!isNoble);
            newHouseField.setDisable(!isNoble);
        });

        body.getChildren().add(grid);
        return body;
    }

    @Override
    public void populate(HumanCharacter character) {
        this.existingCharacter = character;

        givenNameField.setText(character.getGivenName());
        surnameField.setText(character.getSurname());
        genderCombo.setValue(character.getGender());

        if (character.getDOB() != null) {
            dobField.setText(character.getDOB().format(DATE_FORMAT));
        }
        if (character.getDOD() != null) {
            dodField.setText(character.getDOD().format(DATE_FORMAT));
        }

        // Set noble/commoner state
        if (character.getHouse().isPresent()) {
            nobleRadio.setSelected(true);
            houseCombo.setValue(character.getHouse().get());
        } else {
            commonerRadio.setSelected(true);
        }
    }

    @Override
    protected HumanCharacter buildEntity() {
        String givenName = givenNameField.getText().trim();
        String surname = surnameField.getText().trim();
        HumanCharacter.Gender gender = genderCombo.getValue();
        LocalDate dob = parseDate(dobField.getText());
        LocalDate dod = parseDate(dodField.getText());

        if (isEditMode() && existingCharacter != null) {
            // Mutate existing character
            existingCharacter.setGivenName(givenName);
            existingCharacter.setSurname(surname);
            existingCharacter.setGender(gender);
            existingCharacter.setDOB(dob);
            existingCharacter.setDOD(dod);

            if (nobleRadio.isSelected()) {
                House selectedHouse = houseCombo.getValue();
                String newHouseName = newHouseField.getText().trim();
                if (selectedHouse != null) {
                    existingCharacter.setHouse(selectedHouse);
                } else if (!newHouseName.isEmpty()) {
                    House house = new House(newHouseName, existingCharacter);
                    existingCharacter.setHouse(house);
                }
            } else {
                existingCharacter.setHouse(null);
            }

            return existingCharacter;
        } else {
            // Create new character
            if (commonerRadio.isSelected()) {
                return CharacterManager.buildCommoner(givenName, surname, dob, dod, gender);
            }

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

    private LocalDate parseDate(String dateStr) {
        if (dateStr == null || dateStr.trim().isEmpty()) return null;
        try {
            return LocalDate.parse(dateStr.trim(), DATE_FORMAT);
        } catch (DateTimeParseException e) {
            // TODO: surface this error to the user in the card rather than silently failing
            return null;
        }
    }

    public Node getNode() {
        return root;
    }
}
