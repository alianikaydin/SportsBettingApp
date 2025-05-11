# Sports Betting Android App

A modern sports betting application built with Kotlin, Jetpack Compose, Clean Architecture, and Firebase Analytics.  

---

## Architecture

This project follows a **multi-module Clean Architecture** setup with the following layer separation:

- **`app`** – Entry point, navigation, DI graph
- **`core`** – Core components such as UiComponents, Extensions
- **`domain`** – Business logic, use cases, repository contracts
- **`data`** – Repository implementations, Room DB, network (Retrofit)
- **`feature-*`** – UI modules (e.g., `betting`, `cart`)
- **`analytics`** – Analytics abstraction (e.g., Firebase)
- **`network`** – Retrofit service + DTOs

---

## Tech Stack

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

## Features

### Bet Bulletin
- Fetches sports and events
- Allows full-text search by team name

### Bet Detail
- Shows odds and bookmaker info for selected event
- Allows adding a bet to cart
- Logs `match_detail_viewed` and `add_to_cart` Firebase events

### Bet Cart
- Persists bets in Room DB
- Displays total odds and selected bets
- Allows removing individual bets
- Logs `remove_from_cart` Firebase events

---

## Testing Strategy

All layers are covered with isolated unit tests:

- **UseCase tests**: validate business logic using `mockk` and `runTest`
- **ViewModel tests**:
  - Dispatcher control via `Dispatchers.setMain()`
  - StateFlow assertions with `Google Truth`
  - Coroutine verification with `advanceUntilIdle()`
- **Room + Repository tests**: ensure correct DB writes with `withContext(Dispatchers.IO)`

---

## Screenshots

<p align="center">
  <img src="https://github.com/user-attachments/assets/498c10ea-5cad-42b7-aa45-e9c0da92fdf9" alt="Bet Bulletin" width="30%" style="margin: 0 10px;" />
  <img src="https://github.com/user-attachments/assets/57fdcc02-b5bc-4f9e-8977-22dbb492666a" alt="Bet Detail" width="30%" style="margin: 0 10px;" />
  <img src="https://github.com/user-attachments/assets/3da61b82-0e5b-4443-a660-71c3b4a2f9d1" alt="Bet Cart" width="30%" style="margin: 0 10px;" />
</p>


