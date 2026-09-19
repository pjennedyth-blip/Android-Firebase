# Task Checklist - Task Manager App Refactoring

- [x] Add Firebase Auth dependency to `app/build.gradle.kts`
- [x] Create TaskManagerApplication and update AndroidManifest.xml for Hilt setup
- [x] Create Domain Models: `Task.kt`, `TaskDraft.kt`
- [x] Create Domain Repository Interfaces: `AuthRepository.kt`, `TaskRepository.kt`, `DraftRepository.kt`
- [x] Create Domain UseCases (Auth, Task, Draft)
- [x] Create Data Layer Local Room components (`TaskDraftEntity`, `TaskDraftDao`, `AppDatabase`)
- [x] Create Data Layer Remote components (`TaskDocument`, DataSources)
- [x] Create Data Layer Mappers (`TaskMapper`)
- [x] Create Data Layer Repository Implementations (`AuthRepositoryImpl`, `TaskRepositoryImpl`, `DraftRepositoryImpl`)
- [x] Create Dependency Injection modules (`FirebaseModule`, `DatabaseModule`, `RepositoryModule`)
- [x] Create UI States (`AuthUiState`, `TaskListUiState`, `TaskFormUiState`, `DraftUiState`, `OperationState`)
- [x] Create/Refactor ViewModels with Hilt injection
- [x] Create UI Navigation and Screens (Login, Register, TaskList, TaskForm, Drafts)
- [x] Refactor MainActivity to hook everything up
- [x] Verify build and functionality
