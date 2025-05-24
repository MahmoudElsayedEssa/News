# 📰 News App

A modern Android news application built with **Jetpack Compose** and **Clean Architecture**, featuring real-time news from NewsAPI.

## ✨ Features

- 🏠 **Latest Headlines** - Browse top news from various sources
- 🔍 **Smart Search** - Search articles with intelligent suggestions  
- 🎯 **Advanced Filters** - Filter by category, country, and sort options
- 📱 **Modern UI** - Material 3 design with smooth animations
- 🌙 **Dark/Light Theme** - Automatic theme switching
- 📖 **Reading Experience** - Adjustable font sizes and reading time
- 🔄 **Pull to Refresh** - Stay updated with latest news
- 📱 **Offline Support** - Cached articles for offline reading

## 🏗️ Architecture

Built with **Clean Architecture** principles:

```
┌─────────────────┐
│   Presentation  │  ← Jetpack Compose + MVVM
│   (UI Layer)    │
├─────────────────┤
│     Domain      │  ← Business Logic + Use Cases  
│  (Business)     │
├─────────────────┤
│      Data       │  ← Repository + DataSources
│   (Storage)     │
└─────────────────┘
```

## 🛠️ Tech Stack

- **UI**: Jetpack Compose + Material 3
- **Architecture**: Clean Architecture + MVVM
- **DI**: Hilt
- **Networking**: Retrofit + OkHttp
- **Local DB**: Room + DataStore
- **Image Loading**: Coil
- **Async**: Kotlin Coroutines
- **Pagination**: Paging 3

## 🚀 Setup

### Prerequisites
- Android Studio Hedgehog+
- Minimum SDK 26
- NewsAPI Key (free from [newsapi.org](https://newsapi.org/))

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/yourusername/news-app.git
   cd news-app
   ```

2. **Add your API key**
   
   Create `keys.properties` in project root:
   ```properties
   NEWS_API_KEY=your_newsapi_key_here
   ```

3. **Build and run**
   ```bash
   ./gradlew assembleDebug
   ```

## 📱 Screenshots




https://github.com/user-attachments/assets/0bafba37-3bcc-49bf-86ce-f68e9f920138



## 📚 Dependencies

Key libraries used:

```kotlin
// UI & Architecture
androidx.compose.ui
androidx.compose.material3
androidx.lifecycle.viewmodel-compose
androidx.navigation.navigation-compose
androidx.paging.paging-compose

// Networking & Data
com.squareup.retrofit2
androidx.room
androidx.datastore

// DI & Utils
com.google.dagger.hilt-android
io.coil-kt.coil-compose
```

---

<div align="center">
  <p>Made with ❤️ using Jetpack Compose</p>
</div>
