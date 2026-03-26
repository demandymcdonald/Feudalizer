package com.display.windows.menu;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;

import java.util.function.Consumer;

public class AppMenuBar {
    private final MenuBar menuBar = new MenuBar();
    private Consumer<String> onCreateTitle;
    private Runnable onCreateCharacter;
    private Runnable onCreateHouse;

    public AppMenuBar() {
        Menu createMenu = new Menu("Create");

        MenuItem createCounty = new MenuItem("County");
        MenuItem createProvince = new MenuItem("Province");
        MenuItem createTown = new MenuItem("Town");
        MenuItem createManor = new MenuItem("Manor");
        MenuItem createSEZ = new MenuItem("Special Economic Zone");
        MenuItem createCharacter = new MenuItem("Character");
        MenuItem createHouse = new MenuItem("House");

        createCounty.setOnAction(e -> fireCreateTitle("County"));
        createProvince.setOnAction(e -> fireCreateTitle("Province"));
        createTown.setOnAction(e -> fireCreateTitle("Town"));
        createManor.setOnAction(e -> fireCreateTitle("Manor"));
        createSEZ.setOnAction(e -> fireCreateTitle("SEZ"));
        createCharacter.setOnAction(e -> fireCreateCharacter());
        createHouse.setOnAction(e -> fireCreateHouse());

        createMenu.getItems().addAll(
                createCounty, createProvince, createTown, createManor, createSEZ,
                new SeparatorMenuItem(),
                createCharacter, createHouse
        );

        menuBar.getMenus().add(createMenu);
    }

    public void setOnCreateTitle(Consumer<String> callback) {
        this.onCreateTitle = callback;
    }

    public void setOnCreateCharacter(Runnable callback) {
        this.onCreateCharacter = callback;
    }

    public void setOnCreateHouse(Runnable callback) {
        this.onCreateHouse = callback;
    }

    private void fireCreateTitle(String titleType) {
        if (onCreateTitle != null) onCreateTitle.accept(titleType);
    }

    private void fireCreateCharacter() {
        if (onCreateCharacter != null) onCreateCharacter.run();
    }

    private void fireCreateHouse() {
        if (onCreateHouse != null) onCreateHouse.run();
    }

    public MenuBar getNode() {
        return menuBar;
    }
}