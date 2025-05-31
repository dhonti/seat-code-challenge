# 📱 Android Technical Challenge – Jetpack Compose + Clean Architecture (MVI)

This project is a technical challenge built using **Kotlin** and **Jetpack Compose**, structured following **Clean Architecture** principles and the **MVI (Model–View–Intent)** presentation pattern.

---

## 🧠 Architecture

The codebase is divided into modular layers following Clean Architecture:

- **app** – Entry point and dependency graph.
- **presentation** – UI layer with Jetpack Compose and ViewModels.
- **domain** – Business logic and use cases.
- **rover-robot** – Feature module for robot logic.
- **commons:core** – Shared core utilities (e.g., constants, error models).
- **commons:ui** – Shared Compose UI components.
- **commons:robot** – Robot-specific shared logic or UI elements.

### 📁 Project structure

:app
:domain
:presentation
:rover-robot
:commons:core
:commons:ui
:commons:robot

## ⚙️ Presentation Pattern – MVI

The project uses **MVI (Model–View–Intent)** to manage UI state and user interactions in a unidirectional data flow:

1. **Intent** – User actions sent from the UI.
2. **ViewModel** – Receives intents and emits **ViewState** and **Effects**.
3. **View** – Renders the current state and responds to effects.

### ✅ Benefits

- Predictable state management
- Clear separation of concerns
- Scalable to complex UIs

---

## 🛠️ Tech Stack & Libraries

| Purpose              | Libraries Used                                      |
|----------------------|-----------------------------------------------------|
| UI                   | Jetpack Compose, Material3                          |
| Dependency Injection | Dagger Hilt                                         |
| State Management     | Kotlin Coroutines, StateFlow                        |
| JSON Parsing         | Moshi                                               |
| Testing              | JUnit4, Espresso, Compose UI Test, Shot             |
| Navigation           | Jetpack Navigation Compose                          |
| Build                | Gradle (KTS), Kotlin 2.x support                    |

---

## 🔄 GitHub Actions CI

The project integrates a set of GitHub Actions workflows to automate builds and testing:

| Workflow                     | Description                                                               |
|------------------------------|---------------------------------------------------------------------------|
| **Generate debug APK**       | Builds the application in `debug` mode and outputs an installable `.apk`. |
| **Run tests**                | Executes unit tests, UI tests and Screenshot tests using Gradle.          |
| **Version catalog update**   | Checks and updates dependencies defined in `libs.versions.toml`.          |
| **Update screenshots baseline** | Automatically commits new screenshot baselines after test execution.      |

> All workflows are configured to run on pull requests and can also be triggered manually.

---

## 🧪 Testing

The project includes:

- ✅ **Unit Tests** – For use cases and ViewModels.
- 📸 **UI Screenshot Tests** – Powered by [Shot](https://github.com/pedrovgs/Shot).
- 🧪 **Instrumentation Tests** – With Espresso and Compose Test APIs.

### Run screenshot tests

```bash
./gradlew executeScreenshotTests