# School App

This project showcases a modern Android application with clean architecture and Compose UI.  


---

## ✨ Features

- **Home Screen**: Displays promotional banners and product categories.
- **Modern UI**: Built using **Jetpack Compose** and **Material3**.
- **Shared ViewModel** across tabs using **StateFlow** for consistent state management.
- **Clean, modular and scalable codebase** ready for production use.

---


## 📸 Screenshots

### 🏠 Home Screen

<p float="left">
   <img src="https://github.com/user-attachments/assets/e10b9478-32be-4124-8e4e-871d27a64de6" width="30%" />

</p>

### 🏠 Detailed School Info Screen
<p float="left">
    <img src="https://github.com/user-attachments/assets/8efbad9c-7a24-43de-adbc-0299ab6ddb6d" width="30%" />

</p>
---

## 🛠️ Approach & Architecture

This project follows **MVVM Clean Architecture** with a clear separation of concerns:

### 🧱 Layers
- **UI Layer**: Jetpack Compose + Material3 for modern, declarative UI.
- **Domain Layer**: Contains business logic in the form of use-cases.
- **Data Layer**: API interface, retroft

### 🔧 Tech Stack
- **Jetpack Compose**
- **Material3**
- **Kotlin Coroutines & Flow**
- **Hilt** for Dependency Injection
- **StateFlow** for UI state handling
- **Retrofit** for network communication
- **Sealed classes** for managing UI states

---

## ✅ Highlights

- Shared `ViewModel` for consistent state across navigation tabs.
- Proper **loading, success, and error UI states** using sealed classes.
- Architecture supports easy integration of real APIs.
- Follows **OOP** and modern **Android development best practices**.
- UI consistency is maintained across scrolls and tab switches.

---

## 📦 Project Structure
├── ui/ # Jetpack Compose screens, viewmodel & components

├── domain/ # UseCases and models

├── data/ # Repository and Mock API source

├── di/ # Hilt modules for DI




---

## 🚀 Getting Started

1. Clone the repo: git clone https://github.com/Boradeg/School.git
2. Open in Android Studio.
3. Run the app on an emulator or device.

