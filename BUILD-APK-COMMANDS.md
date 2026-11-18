# BikeRakshak — Build the APK (all commands)

Everything needed to build the app is in this repo. No server, no login,
no extra setup inside the app — all data (services, prices, coupons, towns)
is bundled in the code.

## What you need first

- **JDK 17 or newer** (JDK 21 works; Android Studio bundles one)
- **Android SDK** with **platform android-36** + **build-tools 36.0.0**
- Set `ANDROID_HOME` to your SDK folder (Android Studio does this itself)
- Git

## Option 1 — Command line (Windows PowerShell)

```powershell
git clone https://github.com/shadow0null/bikerakshak.git
cd bikerakshak
.\gradlew.bat assembleDebug
```

APK: `app\build\outputs\apk\debug\app-debug.apk`

Install on your phone (USB debugging on, phone connected):

```powershell
adb install -r app\build\outputs\apk\debug\app-debug.apk
```

## Option 2 — Command line (Linux / macOS)

```bash
git clone https://github.com/shadow0null/bikerakshak.git
cd bikerakshak
./gradlew assembleDebug
```

APK: `app/build/outputs/apk/debug/app-debug.apk`

```bash
adb install -r app/build/outputs/apk/debug/app-debug.apk
```

## Option 3 — Android Studio (easiest on Windows)

1. Open Android Studio → **Get from VCS**
2. URL: `https://github.com/shadow0null/bikerakshak.git` → **Clone**
3. Let Gradle sync finish, connect your phone, press **Run**.

## Option 4 — Give this prompt to any AI (Cursor / ChatGPT / etc.)

> Clone https://github.com/shadow0null/bikerakshak (branch main).
> Build a debug APK with ./gradlew assembleDebug
> (on Windows: .\gradlew.bat assembleDebug).
> The app is a native Kotlin Android app: AGP 8.11.1, Kotlin 2.0.21,
> compileSdk/targetSdk 36, minSdk 24, package com.bikerakshak.customer.
> It needs JDK 17+ and Android SDK platform 36.
> Fix any build error you hit, then give me the APK file at
> app/build/outputs/apk/debug/app-debug.apk.
> Do not change the app's features — only fix the build if it breaks.

## Notes

- The APK is **debug-signed** — fine for installing on your own phone.
  A Play Store release build needs a release keystore (not created yet).
- The app works **fully without login**. Login / Sign Up is optional.
- First build downloads Gradle libraries once (a few minutes); later builds
  are fast.
- If a build fails, the exact error is at the end of the output —
  read the lines starting with `e:` first.
