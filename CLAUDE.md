# Feudalizer — AI Assistant Guide

## Project Overview

Feudalizer is a **JavaFX desktop application** for simulating feudal political systems. It models
nobles, titles, land hierarchies, succession rules, and geographic territory using real US county
shapefile data. The architecture is event-sourced: all entity state changes are recorded in
timestamped timelines, enabling historical simulation and "what-if" sandbox scenarios.

**Version**: 1.0-SNAPSHOT (alpha 0.3)
**Java**: 22
**Build System**: Maven

---

## Build & Run

```bash
# Compile
mvn clean compile

# Run via JavaFX plugin (preferred)
mvn javafx:run

# Package
mvn package
```

**Requirements**: JDK 22+, Maven 3.6+. JavaFX 21 is bundled via Maven dependencies.

---

## Repository Structure

```
Feudalizer/
├── pom.xml                          # Maven build config
├── data/shapefiles/fips/            # US Census county boundary shapefiles
└── src/main/
    ├── java/com/
    │   ├── Feudalizer.java          # Application entry point (extends javafx.Application)
    │   ├── GlobalVars.java          # Thread-local simulation state (date, screen dims, paths)
    │   ├── Testcase.java            # Demo data initialization
    │   ├── TypedSerialized.java     # Registry for succession rule factories
    │   ├── base/                    # Core state management framework
    │   │   ├── flags/               # Error/state flag enums
    │   │   ├── reference/           # Typed entity reference system
    │   │   └── timeline/            # Temporal state tracking
    │   │       ├── change/          # TimelineChange types and conditions
    │   │       └── propagation/     # Sandbox simulation (Sandbox, Objective, Scope)
    │   ├── display/                 # JavaFX UI layer
    │   │   ├── geography/           # Map rendering (MapDisplay, GeographyLoader)
    │   │   ├── utils/               # FontManager (Cinzel font)
    │   │   └── windows/             # UI components
    │   │       ├── cards/           # View/edit cards for entities
    │   │       ├── dialogs/         # Modal creation forms
    │   │       └── menu/            # MenuBar, SearchBar, InfoPanel
    │   ├── simulation/              # Domain model
    │   │   ├── culture/             # Cultural terminology localization
    │   │   ├── factions/            # Faction management
    │   │   ├── people/              # Characters, Houses, Families
    │   │   └── title/               # Land hierarchy and succession
    │   │       ├── land/            # County, Province, Town, Manor, SEZ
    │   │       ├── succession/      # Inheritance rules, SuccessionPlanner
    │   │       └── resources/       # Land resources
    │   ├── sql/                     # SQLite persistence (currently disabled)
    │   └── utilities/               # Threading, loading, serialization helpers
    └── resources/styles/            # CSS (shared.css, cards.css, hotbar.css)
```

---

## Architecture

### Entry Point

`Feudalizer.java` (`extends Application`):
1. Initializes `ThreadManager` with the JavaFX thread context
2. Loads geography shapefiles via `GeographyLoader`
3. Registers succession rule factories via `TypedSerialized`
4. Builds `MainWindow` (root `BorderPane`)
5. Registers shutdown hook to save/close `SQLManager`

`main()` calls `Testcase.init()` to populate demo characters, then `launch()`.

### Thread-Local Architecture

State is **per-thread**, not global singletons:

- `GlobalVars` — Thread-local container for: current simulation `LocalDate`, screen dimensions, shapefile paths, `LoadingManager` reference
- `DMRegistry` — Thread-local registry mapping `ObjectType` → `AbstractMutableManager`
- `ThreadManager` — Manages `ThreadSpecific` instances and supports copying context to child threads

Access pattern:
```java
GlobalVars vars = ThreadManager.getVars();   // current thread's vars
DMRegistry reg  = ThreadManager.getRegistry(); // current thread's registry
```

Always use `ThreadManager` accessors. Never store `GlobalVars` or `DMRegistry` in instance fields
and share across threads.

### Entity & State Management (`com.base`)

All simulation entities extend `DateMutableEntity`, which provides:
- A `Timeline<T>` — a `TreeMap<LocalDate, TimelineState<T>>` tracking state history
- Serialization to/from `JsonObject` via `JsonSerializable`
- Thread-safe state lookup using `GlobalVars.getDate()` as the current date context

`AbstractMutableManager<T extends DateMutableEntity>`:
- Holds a `HashBiMap<UUID, T>` for bidirectional lookup
- Handles batch reload when the simulation date changes
- Coordinates with `DMRegistry` for cross-manager linking

`DMRegistry`:
- Central coordinator; all managers register here
- `DMRegistry.isMain()` — checks if running on the JavaFX Application thread

### Simulation Domain (`com.simulation`)

**People**:
- `BookCharacter` — A person in the simulation (gender, birth/death dates, house/family refs)
- `House` — A noble lineage
- `Family` — Tracks spousal/child relationships
- `CharacterManager` — Factory for nobles and commoners

**Titles / Land Hierarchy** (Province → County → Town → Manor):
- `TitleManager` — Manages land entity lookups; caches results with a 10-minute TTL
- `TitleFactory` — Polymorphic deserialization of land types
- `SpecialEconomicZone` — Land with unique economic properties

**Succession**:
- `SuccessionPlanner` — Evaluates candidate heirs
- Rule types registered in `TypedSerialized` (CommonLaw, Custom, etc.)
- `InheritanceGenerator` / `ClaimGenerator` — Build inheritance chains

**Culture**:
- Terminology system for cultural localization of titles and roles

### UI Layer (`com.display`)

`MainWindow` layout (JavaFX `BorderPane`):
- **Top**: `MenuBar` + `SearchBar` inside a `VBox`
- **Center**: `MapDisplay` — GeoTools map rendering US county boundaries
- **Right**: `InfoPanel` — entity view/edit cards

**Cards** (`com.display.windows.cards`):
- Hierarchy: `BaseCard` → `BaseViewCard` / `BaseEditCard`
- Specialized subclasses for each entity type (County, Province, Town, Manor, Character, House)
- Cards are instantiated dynamically based on entity type

**Dialogs**: Modal forms for creating Characters, selecting Titles, and creating Provinces.

**Geography**:
- `GeographyLoader` — Parses FIPS shapefiles into `SimpleFeatureCollection`
- `GeographyManager` — Manages loaded feature collections
- `GeoStore` — Caches computed geometries
- `MapDisplay` — Renders counties; supports multi-select for province creation

### Persistence (`com.sql`)

`SQLManager` wraps SQLite with UPSERT-based entity storage.
**Currently disabled** via `SQL_ENABLED = false` in `Feudalizer.java`.
Do not remove the SQL code; it is intended for future use.

### Sandbox Simulation (`com.base.timeline.propagation`)

`Sandbox` runs isolated simulations of timeline changes:
- `Objective` — Describes what change to simulate
- `Scope` / `Scopes` — Execution context for a sandbox run

Timeline supports two insertion modes:
- **Safe** (propagating): triggers `Sandbox` change propagation
- **Unsafe** (direct): writes without side effects (use for loading/initialization)

---

## Key Conventions

### State Mutation
- Never directly mutate entity fields. Use timeline insertions with proper dates.
- Use **unsafe insertion** only during deserialization or `Testcase.init()`.
- Use **safe insertion** for all user-triggered changes so Sandbox propagation fires.

### Thread Safety
- Never share `GlobalVars` or `DMRegistry` across threads.
- Use `ThreadManager.copyToThread(thread)` to propagate context to child threads.
- Check `DMRegistry.isMain()` before touching JavaFX nodes.

### Serialization
- Entities implement `JsonSerializable` — implement both `toJson()` and `fromJson(JsonObject)`.
- Use `TitleFactory` pattern for polymorphic deserialization of subclassed entities.
- Register new succession rule types in `TypedSerialized`.

### UI Updates
- All JavaFX node mutations must run on the Application thread: `Platform.runLater(() -> ...)`.
- Cards are stateless display components; they read from entity timelines.
- CSS changes go in the appropriate stylesheet (`shared.css`, `cards.css`, or `hotbar.css`).

### Adding a New Entity Type
1. Extend `DateMutableEntity` with your state class.
2. Implement `JsonSerializable`.
3. Create an `AbstractMutableManager` subclass.
4. Register the manager in `DMRegistry` via `ObjectType` enum.
5. Add `BaseViewCard` / `BaseEditCard` subclasses.
6. (Optional) Register factory in `TypedSerialized` if polymorphic deserialization is needed.

### Adding a New Land Type
1. Extend the appropriate land class (e.g., `County`).
2. Register a factory in `TitleFactory`.
3. Add corresponding card classes.

---

## Dependencies

| Dependency | Version | Purpose |
|---|---|---|
| JavaFX | 21.0.1 | Desktop UI |
| Jackson | 2.16.1 | JSON serialization |
| GeoTools | 31.0 | Geospatial processing |
| Guava | (transitive) | BiMap, Multimap, Cache |
| GSON | 2.10.1 | Utility JSON serialization |
| SLF4J | 2.0.9 | Logging |
| SQLite JDBC | 3.45.0.0 | Optional persistence |

GeoTools artifacts are resolved from the **OSGeo Maven repository** configured in `pom.xml`.

---

## Known Gaps & TODOs

- `ProvinceCreationPanel` is referenced in `MapDisplay` but not fully wired up.
- House creation dialog is a stub.
- `LoadingManager` blocking is only implemented for sandbox operations; main thread loads are unblocked.
- SQL persistence is fully implemented but disabled; re-enable by setting `SQL_ENABLED = true` in `Feudalizer.java`.
- Date-change propagation triggers in `DMRegistry` need review for completeness.

---

## Demo / Test Data

`Testcase.init()` creates:
- House "Mojang" with character "Testificate"
- Character "Hot Testificate" (spouse)
- Children "Claude" and "Olivia Rodrigo"

This is the only startup data when SQL is disabled. Modify `Testcase.java` to add more demo entities.
