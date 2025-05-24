# 📰 Android News App

**Souhoola Junior Android Developer Task**

A modern Android news application built with **Jetpack Compose** implementing paginated news articles from NewsAPI with sorting, error handling, and article sharing capabilities.

## 📋 Task Requirements Implemented

✅ **Paginated Article List** with LazyColumn  
✅ **Search functionality** with real-time filtering  
✅ **Sorting dropdown** (Latest, Popularity, Relevancy)  
✅ **NewsAPI integration** with authentication  
✅ **Connectivity & HTTP error handling**  
✅ **Article Details** with Read More & Share  
✅ **Pull-to-refresh** functionality  
✅ **Loading states** and error UI  

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


## 📱 Screenshots

https://github.com/user-attachments/assets/0bafba37-3bcc-49bf-86ce-f68e9f920138

## 🛠️ Tech Stack

- **UI**: Jetpack Compose + Material 3
- **Architecture**: Clean Architecture + MVVM
- **DI**: Hilt for dependency injection
- **Networking**: Retrofit + OkHttp + Gson
- **Pagination**: Paging 3 library
- **Image Loading**: Coil
- **State Management**: StateFlow + Compose State
- **Error Handling**: Custom domain exceptions

## 🚀 Setup Instructions

### 1. Prerequisites
- **Android Studio**: Hedgehog (2023.1.1) or newer
- **Minimum SDK**: API 26 (Android 8.0)
- **NewsAPI Key**: Free account from [newsapi.org](https://newsapi.org/)

### 2. Installation Steps

1. **Clone Repository**
   ```bash
   git clone <repository-url>
   cd android-news-app
   ```

2. **Configure API Key**
   
   Create `keys.properties` file in project root:
   ```properties
   NEWS_API_KEY=your_newsapi_key_here
   ```

3. **Build & Run**
   ```bash
   ./gradlew assembleDebug
   ./gradlew installDebug
   ```

## 📱 App Demo

### Screen 1: Article List
**Features Implemented:**
- ✅ Search bar with real-time filtering
- ✅ LazyColumn with article cards showing:
  - Title, source name, publish date
  - Thumbnail images (with fallback)
- ✅ Sorting dropdown (Latest/Popularity/Relevancy) 
- ✅ Footer loading indicator during pagination
- ✅ Empty state UI and comprehensive error handling
- ✅ Pull-to-refresh functionality
- ✅ Internet connectivity monitoring

### Screen 2: Article Details  
**Features Implemented:**
- ✅ Accessed by clicking article from list
- ✅ Full title and main image display
- ✅ Article content/summary with reading time
- ✅ Source name and formatted publish date
- ✅ **Read More** button → opens original URL in browser
- ✅ **Share** button → opens Android native share sheet

## 🎯 Key Features

### 📄 **Pagination Implementation**
- Uses **Paging 3** library for efficient data loading
- Loads next page automatically when reaching list end
- Handles loading states and retry mechanisms

### 🔍 **Search & Sorting**
- Real-time search with 500ms debounce
- Sorting options reload the entire list
- Search history for better UX

### 🌐 **Error Handling**
- **Network errors**: No internet connection
- **HTTP errors**: 401 Unauthorized, 429 Rate limit, 5xx Server errors  
- **Validation errors**: Invalid search queries
- Retry mechanisms with user feedback

### 📱 **Modern UI/UX**
- Material 3 design system
- Smooth animations and transitions
- Loading placeholders for better perceived performance
- Pull-to-refresh with custom indicators

## 🧪 Architecture Highlights

### **Clean Code Principles**
- **Separation of Concerns**: Clear layer boundaries
- **Dependency Inversion**: Repository pattern with interfaces
- **Single Responsibility**: Each class has one purpose
- **SOLID Principles**: Applied throughout the codebase

### **Error Handling Strategy**
```kotlin
// Domain-specific exceptions
sealed class NewsDomainException
class NetworkTimeoutException
class AuthenticationException
class RateLimitExceededException
// + comprehensive error mapping
```

### **State Management**
```kotlin
// Clean state management with StateFlow
data class ArticleListState(
    val articles: LazyPagingItems<Article>,
    val isLoading: Boolean,
    val error: ErrorState?,
    val searchQuery: String,
    val selectedSortBy: SortBy
)
```

## 📊 Performance Features

- **Efficient Pagination**: Only loads visible items + buffer
- **Image Caching**: Coil handles memory and disk caching
- **Debounced Search**: Prevents excessive API calls
- **State Preservation**: Survives configuration changes

## 🔧 Testing

```bash
# Run unit tests
./gradlew test

# Run instrumented tests
./gradlew connectedAndroidTest
```

**Test Coverage:**
- ✅ Repository layer with mock responses
- ✅ ViewModel state management
- ✅ Use case business logic
- ✅ Error handling scenarios

---

**Task completed for Souhoola Junior Android Developer position**  
*Demonstrating modern Android development with Clean Architecture*
