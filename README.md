# ListIt

A simple to-do list app for Android built with Jetpack Compose and Clean Architecture.

## Features

- Create, edit, and delete tasks
- Mark tasks as done/not done
- Multiple lists — organize items into separate lists
- Move items between lists
- Dynamic color theming on Android 12+

## Tech Stack

- **Kotlin** + **Jetpack Compose** (Material3)
- **Room** for local persistence
- **Hilt** for dependency injection
- **Navigation Compose** for screen routing
- **Clean Architecture** (domain / data / presentation layers)

## Architecture

The app follows **Clean Architecture** organized into three layers with a strict dependency rule: outer layers depend on inner layers, never the reverse.

```
┌─────────────────────────────────────────────────────┐
│                  Presentation Layer                 │
│         (Compose UI ← ViewModel ← UseCase)          │
├─────────────────────────────────────────────────────┤
│                    Domain Layer                     │
│      (UseCases, Models, Repository interfaces)      │
├─────────────────────────────────────────────────────┤
│                     Data Layer                      │
│     (Repository implementations, Room DB, DAOs)     │
└─────────────────────────────────────────────────────┘
```

### Data Flow

```
Room DB ──Flow──▶ DAO ──Flow──▶ RepositoryImpl ──map──▶ Domain Model
    ──Flow──▶ UseCase ──Flow──▶ ViewModel (stateIn) ──▶ Compose UI (collectAsStateWithLifecycle)
```

1. **Room** emits `Flow<List<Entity>>` from the DAO whenever data changes.
2. **RepositoryImpl** maps entities to domain models and exposes them as `Flow`.
3. **UseCases** (one per operation) pass the flow through or perform write operations via the repository interface.
4. **ViewModel** collects flows with `stateIn()` to produce `StateFlow` for the UI.
5. **Compose screens** observe state via `collectAsStateWithLifecycle()` and re-compose on changes.

Write operations flow in the opposite direction: UI → ViewModel → UseCase → Repository → DAO → Room DB.

### Dependency Rule

The **domain layer** is pure Kotlin with no Android dependencies. It defines repository interfaces that the data layer implements. The presentation layer depends on domain (use cases and models) but never on data directly.

## Build

```bash
./gradlew build
```

Requires JDK 17. Min SDK 21, Target SDK 35.
