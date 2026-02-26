# Remaining Work and Known Issues

This document outlines the remaining phases required to complete the Attendance App and known issues/errors in the current codebase.

## Phase 1: Background Services Implementation
-   **SyncAttendanceWorker**: The `doWork()` method is currently a stub (`// TODO: Implement sync logic`). It needs to be implemented to synchronize local database changes with the remote server.
-   **FCMService**: The `FCMService` contains TODOs for sending tokens to the backend and handling notifications (`// TODO: Send token to backend`, `// TODO: Handle notification`). This is critical for push notifications.

## Phase 2: Authentication Logic
-   **SplashViewModel**: The authentication check is simulated. It needs to integrate `AuthRepository` and `UserPreferencesRepository` to actually check if the user is logged in or if onboarding is complete (`// TODO: Check actual auth state`).
-   **Dependency Injection**: The `SplashViewModel` constructor has commented-out dependencies that need to be re-enabled once the repositories are fully implemented.

## Phase 3: Configuration
-   **Google Maps API Key**: The `AndroidManifest.xml` (via `build.gradle.kts`) uses a placeholder `YOUR_API_KEY_HERE`. This must be replaced with a valid API key enabled for the Maps SDK for Android.
-   **Google Sheets API**: The `SHEETS_BASE_URL` in `build.gradle.kts` is a placeholder. Valid OAuth 2.0 credentials and a real spreadsheet ID need to be configured.
-   **Firebase Configuration**: The `google-services.json` file is present but contains dummy data. It must be replaced with a valid file downloaded from the Firebase Console for the specific project package name (`com.company.attendanceapp`).

## Phase 4: Testing
-   **Unit Tests**: The `app/src/test` directory is missing. Unit tests need to be written for ViewModels, Repositories, and UseCases.
-   **Instrumentation Tests**: The `app/src/androidTest` directory is missing. UI tests (using Espresso or Compose Test Rule) need to be written to verify screen interactions.

## Phase 5: Release
-   **Signing**: The current CI workflow builds a debug APK. For production distribution, a release keystore must be generated, and the build process must be configured to sign the APK with this keystore (typically via secrets in GitHub Actions).
