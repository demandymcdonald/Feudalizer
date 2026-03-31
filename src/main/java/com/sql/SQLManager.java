package com.sql;

import com.Global;
import com.base.DateMutableEntity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import com.base.AbstractMutableManager;
import com.base.DMRegistry;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.sql.*;
import java.util.UUID;

public class SQLManager {
    private static final String DB_PATH = "data/feudalizer.db";
    private static Connection connection;

    // Initialize DB connection and create table if needed
    public static void init() {
        if (!Global.SQL_ENABLED) return;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + DB_PATH);
            createTableIfNotExists();
            System.out.println("Database initialized");
        } catch (SQLException e) {
            System.err.println("Failed to initialize database");
            e.printStackTrace();
        }
    }

    private static void createTableIfNotExists() throws SQLException {
        String sql = """
        CREATE TABLE IF NOT EXISTS data_store (
            uuid TEXT PRIMARY KEY,
            class_type TEXT NOT NULL,
            payload TEXT NOT NULL
        );
    """;
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        }
    }

    // Load all entities from DB at startup
    public static void loadAll() {
        if (!Global.SQL_ENABLED) return;
        String sql = "SELECT uuid, class_type, payload FROM data_store";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            int count = 0;
            while (rs.next()) {
                UUID id = UUID.fromString(rs.getString("uuid"));
                String classType = rs.getString("class_type");
                JsonObject payload = JsonParser.parseString(rs.getString("payload")).getAsJsonObject();

                // Get the appropriate manager and deserialize
                AbstractMutableManager<?, ?> manager = DMRegistry.getManager(classType);
                if (manager != null) {
                    manager.deserializeEntity(id, payload);
                    count++;
                } else {
                    System.err.println("No manager found for class: " + classType);
                }
            }

            System.out.println("Loaded " + count + " entities from database");

        } catch (SQLException e) {
            System.err.println("Failed to load from database");
            e.printStackTrace();
        }
    }

    // Save a single entity (upsert)
    public static void save(DateMutableEntity<?> entity) {
        if (!Global.SQL_ENABLED) return;
        String sql = "INSERT OR REPLACE INTO data_store (uuid, class_type, payload) VALUES (?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, entity.getId().toString());
            pstmt.setString(2, entity.getClass().getSimpleName());
            pstmt.setString(3, entity.serialize().toString());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Failed to save entity: " + entity.getId());
            e.printStackTrace();
        }
    }

    // Save all entities from all managers
    public static void saveAll() {
        if (!Global.SQL_ENABLED) return;
        int count = 0;

        try {
            connection.setAutoCommit(false); // Batch transaction for speed

            // Get all managers from registry and save their entities
            for (String managerKey : new String[]{"BookCharacter", "Family", "House", "Title"}) {
                AbstractMutableManager<?, ?> manager = DMRegistry.getManager(managerKey);
                if (manager != null) {
                    for (Object entity : manager.getItemMap().values()) {
                        if (entity instanceof DateMutableEntity<?> dme) {
                            save(dme);
                            count++;
                        }
                    }
                }
            }

            connection.commit();
            connection.setAutoCommit(true);
            System.out.println("Saved " + count + " entities to database");

        } catch (SQLException e) {
            System.err.println("Failed to save all entities");
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    // Clean shutdown
    public static void close() {
        if (!Global.SQL_ENABLED) return;
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

