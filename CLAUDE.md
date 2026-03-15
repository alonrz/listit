# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Build & Development Commands

```bash
# Build the project
./gradlew build

# Run unit tests
./gradlew test

# Run a single unit test class
./gradlew test --tests "com.example.mylist.ExampleUnitTest"

# Run instrumented (Android device/emulator) tests
./gradlew connectedAndroidTest

# Clean build
./gradlew clean build

# Lint check
./gradlew lint
```

## Architecture

**ListIt** is a single-module Android to-do list app using **Jetpack Compose**, **MVVM**, and the **Repository pattern**.

### Layers

- **UI (Compose):** `root/RootScreen` provides the main scaffold (TopAppBar, BottomAppBar, FAB). Screens live in feature packages (`home/`, `edit/`, `settings/`).
- **ViewModels:** Use `StateFlow` + coroutines for reactive state. Each ViewModel has a corresponding Factory class for dependency injection (no Hilt/Dagger — manual factory pattern).
- **Repository:** `ItemsRepo` interface with two implementations — `MainListRepoLocalData` (Room) and `MainListRepoFakeData` (mock). `RepoProvider` selects which to use.
- **Room Database:** Single table `main_list` via `AppDatabase` singleton. `MainListDao` returns `Flow<List<ListItemData>>` for reactive queries. UUID-based primary keys.

### Navigation

Type-safe navigation using a sealed class `ScreenNavigation` in `navigation/`. Routes: Root → Home, Edit (with item ID/title/isDone args), Settings. Wired in `NavGraph.kt`.

### Key Technical Details

- **Min SDK 21 / Target SDK 35**, compiled with JDK 17
- Compose BOM `2025.08.00`, Material3
- Room 2.7.1 with KSP compiler
- Reactive chain: Room `Flow` → ViewModel `stateIn()` → Compose `collectAsStateWithLifecycle()`
- Dynamic color theming on Android 12+ (`ListItTheme`)
- Database uses destructive fallback migration