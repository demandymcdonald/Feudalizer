package com.display.windows.dialogs;

import com.GlobalVars;
import com.base.DMRegistry;
import com.display.geography.GeographyManager;
import com.display.geography.GeometryType;
import com.simulation.title.land.Province;
import com.simulation.title.land.Titles;
import com.simulation.character.BookCharacter;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.util.Pair;
import org.geotools.api.feature.simple.SimpleFeature;
import org.locationtech.jts.geom.Geometry;

import java.util.ArrayList;
import java.util.List;

import static com.display.geography.GeographyManager.registerCustomGeometry;

public class ProvinceCreationDialog extends TitleSelectionDialog<Province> {

    private final TextField nameField = new TextField();
    private final ComboBox<BookCharacter> holderCombo = new ComboBox<>();

    public ProvinceCreationDialog() {
        super("Create Province", "Merge counties into a province");
        init();
    }

    @Override
    protected VBox buildDialogContent() {
        VBox content = new VBox(10);
        content.setPadding(new Insets(20));

        // Province details
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        grid.add(new Label("Province Name:"), 0, 0);
        grid.add(nameField, 1, 0);

        holderCombo.getItems().addAll(DMRegistry.getCharacterManager().getAll());
        holderCombo.setPromptText("Select holder (optional)");
        grid.add(new Label("Holder:"), 0, 1);
        grid.add(holderCombo, 1, 1);

        content.getChildren().add(grid);
        content.getChildren().add(buildSelectionSection());

        return content;
    }

    @Override
    protected void setupValidation(Button createButton) {
        nameField.textProperty().addListener((obs, old, newVal) -> validateForm(createButton));
        selectedCountiesView.getItems().addListener(
                (javafx.collections.ListChangeListener.Change<? extends String> c) -> validateForm(createButton)
        );
    }

    private void validateForm(Button createBtn) {
        boolean valid = !nameField.getText().trim().isEmpty()
                && !selectedCounties.isEmpty();
        createBtn.setDisable(!valid);
    }

    @Override
    protected Province createTitle() {
        String name = nameField.getText().trim();
        BookCharacter holder = holderCombo.getValue();

        // Merge geometries
        List<Geometry> geometries = new ArrayList<>();
        for (Pair<com.display.geography.GeometryType, String> county : selectedCounties) {
            Geometry geom = (Geometry) GeographyManager.getFeature(county.getKey(), county.getValue())
                    .getDefaultGeometry();
            if (geom != null) {
                geometries.add(geom);
            }
        }

        if (geometries.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Geometry");
            alert.setHeaderText("Cannot create province");
            alert.setContentText("No valid geometries found for selected counties");
            alert.showAndWait();
            return null;
        }

        Geometry merged = geometries.get(0);
        for (int i = 1; i < geometries.size(); i++) {
            merged = merged.union(geometries.get(i));
        }
        // Handles placing geometry in proper featurecontainer/map and saving it to disk
        SimpleFeature feature = registerCustomGeometry(name, GeometryType.CUST_P, merged);
        // Create province
        Province province = Titles.createProvince(name, GlobalVars.CURRENT_DATE(), null, GeometryType.CUST_P,feature.getID());
        province.setHolder(holder);
        // Exclude counties
        for (Pair<com.display.geography.GeometryType, String> county : selectedCounties) {
            GeographyManager.excludeGeometry(county.getKey(), county.getValue());
        }

        return province;
    }
}