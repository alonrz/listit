# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Build & Development Commands

```bash
# Build the project
./gradlew build

# Run unit tests
./gradlew test

# Run a single unit test class
./gradlew test --tests "com.example.listit.ExampleUnitTest"

# Run all use case tests
./gradlew test --tests "com.example.listit.domain.usecase.*"

# Run a specific use case test
./gradlew test --tests "com.example.listit.domain.usecase.AddItemUseCaseTest"

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

- **`model/ListItem`** — domain model (id, title, isDone, listId). `listId` defaults to `DEFAULT_LIST_ID`
- **`model/ItemList`** — domain model for lists (id, name). Constants `DEFAULT_LIST_ID` and `DEFAULT_LIST_NAME`
- **`repository/ListItemRepository`** — interface defining item data operations (dependency rule: domain owns this)
- **`repository/ItemListRepository`** — interface defining list CRUD operations
- **`usecase/`** — one class per operation, each with `operator fun invoke()`:
  - Items: `ObserveItemsUseCase`, `ObserveItemsByListUseCase`, `AddItemUseCase`, `DeleteItemUseCase`, `UpdateItemTitleUseCase`, `UpdateItemStatusUseCase`, `MoveItemUseCase`
  - Lists: `ObserveListsUseCase`, `AddListUseCase`, `DeleteListUseCase`

### Data Layer (`data/`)

- **`local/`** — Room database (v3): `AppDatabase`, `MainListDao`, `ItemListDao`, `ListItemEntity` (@Entity for `main_list` table with FK to `item_lists`), `ItemListEntity` (@Entity for `item_lists` table). Singleton managed by Hilt's `DatabaseModule`. Default list seeded via `onCreate` callback.
- **`repository/ListItemRepositoryImpl`** — implements `ListItemRepository`, delegates to DAO with entity↔domain mapping
- **`repository/ItemListRepositoryImpl`** — implements `ItemListRepository`
- **`mapper/ListItemMapper`** — extension functions `ListItemEntity.toDomain()` and `ListItem.toEntity()`
- **`mapper/ItemListMapper`** — extension functions `ItemListEntity.toDomain()` and `ItemList.toEntity()`
- **`fake/FakeListItemRepository`** — in-memory implementation for previews/testing
- **`fake/FakeItemListRepository`** — in-memory list implementation for previews/testing

### Presentation Layer (`presentation/`)

- **`root/RootScreen`** — main scaffold with TopAppBar, FAB, BottomAppBar
- **`home/`** — HomeScreen (LazyColumn list) + HomeViewModel
- **`edit/`** — EditScreen (item editing) + EditViewModel
- **`settings/SettingsScreen`** — placeholder
- **`navigation/`** — `ScreenNavigation` sealed class routes, `NavGraph` wiring
- **`components/ListItemView`** — reusable list item composable

### Dependency Injection (`di/`)

Uses **Hilt/Dagger** (2.56.2) with KSP compiler. `ListItApplication` is annotated `@HiltAndroidApp`, `MainActivity` with `@AndroidEntryPoint`.

- **`DatabaseModule`** — `@Module` providing `AppDatabase` (`@Singleton`), `MainListDao`, and `ItemListDao`. Seeds default list on DB creation.
- **`RepositoryModule`** — `@Module` binding `ListItemRepositoryImpl` → `ListItemRepository` and `ItemListRepositoryImpl` → `ItemListRepository` (`@Singleton`)
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
