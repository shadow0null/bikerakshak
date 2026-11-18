# BikeRakshak

**Bike Ruki? Rakshak Aaya.** — 24x7 two-wheeler mechanic service app for East Champaran, Bihar.

Native Kotlin Android app (package `com.bikerakshak.customer`), Material 3. Launch towns: Motihari, Dhaka, Raxaul, Chakia, Pakridayal, Kesaria, Areraj, Mehsi, Sugauli, Harsidhi, Turkaulia, Patahi.

## Status — customer app screens complete

- Screen 1 — Splash screen (navy + orange brand, 1.5s → home)
- Screen 2 — Home with bottom navigation (Dashboard / Orders / Profile)
- Screen 3 — Service catalogue (18 services) with search + cart
- Screen 4 — Booking flow: details, CC tier pricing, town picker with East Champaran coverage gate, date + 12 time slots, coupons (WELCOME10, RAKSHAK50), order summary, ₹49 service charge under ₹499, pay after service
- Screen 5 — Login / Sign Up / password reset (`bikerakshak://reset` deep link) — demo auth, Supabase GoTrue replaces it later
- Screen 6 — Saved addresses, edit profile, manage account (logout / delete)

Bookings and addresses are stored on-device (SharedPreferences) in this build. Prices are Week-1 survey starting estimates, not locked rates.

## Open in Android Studio

1. Clone this repo.
2. Open the project folder in Android Studio (it will sync Gradle; min SDK 24, target SDK 36).
3. Run on a real device (Galaxy A21s over USB recommended).

## Stack (planned)

- Native Kotlin + Material 3
- Supabase backend (auth + Postgrest) — URL/anon key in `local.properties`, never committed
- No map SDK, no payment SDK for MVP (pay after service: UPI/cash)

## Still to build

- Supabase wiring (real auth + bookings sync + mechanic assignment)
- Mechanic app + admin panel
- Push notifications

All code in this repo is original work for BikeRakshak.
