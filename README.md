# BikeRakshak

**Bike Ruki? Rakshak Aaya.** — 24x7 two-wheeler mechanic service app for East Champaran, Bihar.

Native Kotlin Android app (package `com.bikerakshak.customer`), Material 3. Launch towns: Motihari, Dhaka, Raxaul, Chakia, Pakridayal, Kesaria, Areraj, Mehsi, Sugauli, Harsidhi, Turkaulia, Patahi.

## Status

- Screen 1 — Splash screen (navy + orange brand, 1.5s → home)
- Screen 2 — Home with bottom navigation (Dashboard / Orders / Profile)
- Next: Screen 3 — service catalogue with cart, then booking flow with East Champaran coverage gate

## Open in Android Studio

1. Clone this repo.
2. Open the project folder in Android Studio (it will sync Gradle; min SDK 24, target SDK 36).
3. Run on a real device (Galaxy A21s over USB recommended).

## Stack (planned)

- Native Kotlin + Material 3
- Supabase backend (auth + Postgrest) — URL/anon key in `local.properties`, never committed
- No map SDK, no payment SDK for MVP (pay after service: UPI/cash)

All code in this repo is original work for BikeRakshak.
