package com.display.utils;

import com.Feudalizer;

import javafx.scene.text.Font;

public class FontManager {
    public static final Font CINZEL_REGULAR;
    public static final Font CINZEL_BOLD;
    public static final Font CRIMSON_REGULAR;
    public static final Font CRIMSON_BOLD;
    public static final Font COMIC_SANS;

    static {
        CINZEL_REGULAR = load("Cinzel-Regular.ttf", 14);
        CINZEL_BOLD = load("Cinzel-Bold.ttf", 14);
        CRIMSON_REGULAR = load("CrimsonPro-Regular.ttf", 14);
        CRIMSON_BOLD = load("CrimsonPro-Bold.ttf", 14);
        COMIC_SANS = load("Ldfcomicsanslight-6ddZo.ttf",14);
    }

    private static Font load(String path, double size) {
        Font font = Font.loadFont(FontManager.class.getResourceAsStream("/fonts/" + path), size);
        if (font == null) {
            Feudalizer.LOGGER.error("Failed to load font: {}", path);
        }
        return font;
    }

    private FontManager() {}
}
