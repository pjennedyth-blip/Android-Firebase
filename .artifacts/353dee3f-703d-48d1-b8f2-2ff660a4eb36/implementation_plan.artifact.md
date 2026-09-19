# Plan de Implementación: Fase 1 - Análisis y Diseño

Este plan formaliza la **Fase 1** de la actividad académica, basándose en la arquitectura ya implementada en el código pero asegurando que la documentación técnica (evidencia) y la estructura inicial del proyecto sean coherentes y limpias.

## User Review Required

> [!IMPORTANT]
> - Procederé a eliminar archivos "heredados" que ya no se utilizan en la nueva arquitectura para que el proyecto coincida con la "Estructura inicial" deseada.
> - Los archivos a eliminar son: `app/src/main/java/com/example/taskmanagerapp/data/TaskRepository.kt` (viejo), `ui/TaskScreen.kt`, `ui/TaskViewModel.kt` y `model/Task.kt` (viejo).
> - Crearé un nuevo artefacto de documentación con los diagramas, modelos y bocetos solicitados.

## Proposed Changes

### 1. Documentación (Evidencia de Fase 1)
#### [NEW] [analisis_diseno_fase_1.artifact.md](file:///C:/Users/perej/AndroidStudioProjects/Android-Firebase/.artifacts/353dee3f-703d-48d1-b8f2-2ff660a4eb36/analisis_diseno_fase_1.artifact.md)
- **Actores y Funcionalidades**: Definición de usuarios y casos de uso.
- **Diagrama de Arquitectura**: Representación visual del flujo UI -> VM -> UseCase -> Repo -> Data.
- **Modelos de Datos**: Definición técnica de `Task` y `TaskDraft`.
- **Distribución de Datos**: Matriz de responsabilidades entre Firestore (remoto) y Room (local/borradores).
- **Mapa de Navegación**: Grafo de rutas de la aplicación.
- **Bocetos**: Descripción estructurada de cada pantalla.

### 2. Limpieza de Estructura Inicial (Cleanup)
Para que el proyecto refleje fielmente el diseño de la Fase 1 sin ruido de código anterior.

#### [DELETE] [TaskRepository.kt](file:///C:/Users/perej/AndroidStudioProjects/Android-Firebase/app/src/main/java/com/example/taskmanagerapp/data/TaskRepository.kt)
#### [DELETE] [TaskScreen.kt](file:///C:/Users/perej/AndroidStudioProjects/Android-Firebase/app/src/main/java/com/example/taskmanagerapp/ui/TaskScreen.kt)
#### [DELETE] [TaskViewModel.kt](file:///C:/Users/perej/AndroidStudioProjects/Android-Firebase/app/src/main/java/com/example/taskmanagerapp/ui/TaskViewModel.kt)
#### [DELETE] [Task.kt](file:///C:/Users/perej/AndroidStudioProjects/Android-Firebase/app/src/main/java/com/example/taskmanagerapp/model/Task.kt)

## Verification Plan

### Automated Tests
- Ejecutar `./gradlew :app:assembleDebug` para asegurar que la eliminación de archivos heredados no afecte la compilación (confirmar que no queden referencias residuales).

### Manual Verification
- Revisar que el artefacto de documentación contenga todos los puntos solicitados en la "Evidencia esperada".
