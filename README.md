# 🏟️ Sports Betting Android App

A modern sports betting application built with Kotlin, Jetpack Compose, Clean Architecture, and Firebase Analytics.  

---

## 🔧 Architecture

This project follows a **multi-module Clean Architecture** setup with the following layer separation:

- **`app`** – Entry point, navigation, DI graph
- **`core`** – Core components such as UiComponents, Extensions
- **`domain`** – Business logic, use cases, repository contracts
- **`data`** – Repository implementations, Room DB, network (Retrofit)
- **`feature-*`** – UI modules (e.g., `betting`, `cart`)
- **`analytics`** – Analytics abstraction (e.g., Firebase)
- **`network`** – Retrofit service + DTOs

---

## 🛠️ Tech Stack

- **Language**: Kotlin (v2.0.21)
- **UI**: Jetpack Compose + Material3
- **Navigation**: Jetpack Navigation Compose
- **DI**: Hilt
- **Async**: Coroutines + Flow + StateFlow
- **Local DB**: Room
- **Network**: Retrofit + Moshi
- **Analytics**: Firebase Analytics (custom AnalyticsManager interface)
- **Testing**: JUnit, MockK, Coroutine Test, Google Truth

---

## 📲 Features

### 📰 Bet Bulletin
- Fetches sports and events
- Allows full-text search by team name

### 🔍 Bet Detail
- Shows odds and bookmaker info for selected event
- Allows adding a bet to cart
- Logs `match_detail_viewed` and `add_to_cart` Firebase events

### 🧺 Bet Cart
- Persists bets in Room DB
- Displays total odds and selected bets
- Allows removing individual bets
- Logs `remove_from_cart` Firebase events

---

## 🧪 Testing Strategy

All layers are covered with isolated unit tests:

- **UseCase tests**: validate business logic using `mockk` and `runTest`
- **ViewModel tests**:
  - Dispatcher control via `Dispatchers.setMain()`
  - StateFlow assertions with `Google Truth`
  - Coroutine verification with `advanceUntilIdle()`
- **Room + Repository tests**: ensure correct DB writes with `withContext(Dispatchers.IO)`

---
