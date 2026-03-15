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

**ListIt** is a single-module Android to-do list app following **Clean Architecture** with three layers: domain, data, and presentation.

### Domain Layer (`domain/`)

Pure Kotlin with no Android dependencies.

- **`model/ListItem`** — domain model (id, title, isDone)
- **`repository/ListItemRepository`** — interface defining data operations (dependency rule: domain owns this)
- **`usecase/`** — one class per operation (`ObserveItemsUseCase`, `AddItemUseCase`, `DeleteItemUseCase`, `UpdateItemTitleUseCase`, `UpdateItemStatusUseCase`), each with `operator fun invoke()`

### Data Layer (`data/`)

- **`local/`** — Room database: `AppDatabase`, `MainListDao`, `ListItemEntity` (@Entity for `main_list` table). Singleton managed by Hilt's `DatabaseModule`.
- **`repository/ListItemRepositoryImpl`** — implements `ListItemRepository`, delegates to DAO with entity↔domain mapping
- **`mapper/ListItemMapper`** — extension functions `ListItemEntity.toDomain()` and `ListItem.toEntity()`
- **`fake/FakeListItemRepository`** — in-memory implementation for previews/testing

### Presentation Layer (`presentation/`)

- **`root/RootScreen`** — main scaffold with TopAppBar, FAB, BottomAppBar
- **`home/`** — HomeScreen (LazyColumn list) + HomeViewModel
- **`edit/`** — EditScreen (item editing) + EditViewModel
- **`settings/SettingsScreen`** — placeholder
- **`navigation/`** — `ScreenNavigation` sealed class routes, `NavGraph` wiring
- **`components/ListItemView`** — reusable list item composable

### Dependency Injection (`di/`)

Uses **Hilt/Dagger** (2.56.2) with KSP compiler. `ListItApplication` is annotated `@HiltAndroidApp`, `MainActivity` with `@AndroidEntryPoint`.

- **`DatabaseModule`** — `@Module` providing `AppDatabase` (`@Singleton`) and `MainListDao`
- **`RepositoryModule`** — `@Module` binding `ListItemRepositoryImpl` → `ListItemRepository` (`@Singleton`)
- All use cases and `ListItemRepositoryImpl` use `@Inject constructor`
- ViewModels use `@HiltViewModel` + `@Inject constructor` and are obtained via `hiltViewModel()` in composables
- `EditViewModel` reads nav args from `SavedStateHandle` (keys: `"id"`, `"title"`, `"isDone"`)

### Key Technical Details

- **Min SDK 21 / Target SDK 35**, compiled with JDK 17
- Compose BOM `2025.08.00`, Material3
- Room 2.7.1 with KSP compiler, Hilt 2.56.2 with KSP compiler
- Reactive chain: Room `Flow` → DAO → RepositoryImpl (maps to domain) → UseCase → ViewModel `stateIn()` → Compose `collectAsStateWithLifecycle()`
- Dynamic color theming on Android 12+ (`ListItTheme`)
- Database uses destructive fallback migration
- Navigation passes item data (id/title/isDone) as URL route arguments
