\# Gestor Personal de Tareas



\## Descripción



Gestor Personal de Tareas es una aplicación Android desarrollada para permitir a los usuarios registrar, consultar, actualizar y eliminar sus tareas. También permite gestionar borradores localmente y publicarlos posteriormente en Cloud Firestore.



La aplicación fue desarrollada como parte del taller de formación del SENA.



\## Integrante



\- Jennedy Katerine Perez Barrios



Programa de formación: Análisis y Desarrollo de Software  

SENA - 2026



\## Tecnologías utilizadas



\- Kotlin

\- Jetpack Compose

\- Firebase Authentication

\- Cloud Firestore

\- Room

\- Hilt

\- StateFlow

\- Coroutines

\- Navigation Compose

\- Git y GitHub



\## Arquitectura



El proyecto utiliza arquitectura MVVM con separación por capas:



UI

↓

ViewModel

↓

UseCase

↓

Repository

↓

Firebase / Room



\## Estructura de paquetes



text

com.example.taskmanagerapp

├── data

├── di

├── domain

├── model

└── ui

