# BikeRakshak

**Bike Ruki? Rakshak Aaya.** — 24x7 two-wheeler mechanic service app for East Champaran, Bihar.

Native Kotlin Android app (package `com.bikerakshak.customer`), Material 3. Launch towns: Motihari, Dhaka, Raxaul, Chakia, Pakridayal, Kesaria, Areraj, Mehsi, Sugauli, Harsidhi, Turkaulia, Patahi.

Brand: navy `#0F2340`, rescue orange `#FF6A00`. Real logo: shield + orange wrench app icon (`res/drawable/ic_launcher_foreground.png`, 512px) — used as the launcher/adaptive icon, the splash logo, and the logo tile on the Sign Up / Sign In screens. Logo package source files: `~/workspace/your_files/mechanic-app/` (primary light/dark, minimal, app icon final).

## Status — customer app screens complete

- Screen 1 — Splash screen (navy + orange brand, 1.5s → home)
- Screen 2 — Home with bottom navigation (Dashboard / Orders / Profile)
- Screen 3 — Service catalogue (18 services) with search + cart
- Screen 4 — Booking flow: details, CC tier pricing, town picker with East Champaran coverage gate, date + 12 time slots, coupons (WELCOME10, RAKSHAK50), order summary, ₹49 service charge under ₹499, pay after service
- Screen 5 — Login / Sign Up / password reset (`bikerakshak://reset` deep link) — demo auth, Supabase GoTrue replaces it later; auth screens styled to the reference (logo tile, icon fields, show/hide password, Google button, Terms/Privacy)
- Screen 6 — Saved addresses, edit profile, manage account (logout / delete)
- **No login wall: every page works without login.** Booking, orders, profile, addresses and vehicles all work as a guest (data stays on the phone). Login / Sign Up is optional, for faster booking later.
- **Catalogue = FixWheel's public rate card, re-priced for East Champaran:** all 15 services from fixwheel.app (General Service, Service with Engine Oil, Diagnosis, Jump Start, Puncture, Running Repair, Engine Half/Full Overhaul, Carburettor, OBD, Battery, Brake Pads, Chain & Sprocket, Pick & Drop, EV Service) with CC-tier pricing, plus 3 BikeRakshak extras (sealed-can petrol delivery, scooty drive-belt, night diagnosis). Our own coupons (WELCOME10, RAKSHAK50) and our ₹49-under-₹499 service charge stay as set in the plan.

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
