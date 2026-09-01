# RouteWise SA - Android Kotlin Native Application

A production-grade Android native application developed with **Kotlin**, **Android Jetpack**, **Room Local Database**, **Retrofit 2**, **Coroutines & Flow**, **Material 3**, and **Google Maps & Mapbox SDKs**.

---

## 📁 Android Studio & GitHub Project Structure

When cloned or pushed to GitHub, this repository follows the canonical Android Studio Kotlin project hierarchy:

```text
RouteWiseSA/
├── .gitignore                          # Standard Android Studio gitignore
├── build.gradle.kts                    # Top-level build configuration (Kotlin DSL)
├── settings.gradle.kts                 # Project & repository resolution settings
├── gradlew                             # Gradle wrapper for Linux / macOS
├── gradlew.bat                         # Gradle wrapper for Windows
├── gradle/
│   ├── libs.versions.toml              # Gradle version catalog for dependencies
│   └── wrapper/
│       └── gradle-wrapper.properties   # Gradle 8.4 wrapper distribution
└── app/
    ├── build.gradle.kts                # Application module build script & plugins
    ├── proguard-rules.pro              # Proguard optimization rules
    └── src/
        └── main/
            ├── AndroidManifest.xml     # App permissions, activities, & metadata
            ├── java/
            │   └── com/
            │       └── routewise/
            │           └── sa/
            │               ├── RouteWiseApplication.kt
            │               ├── MainActivity.kt
            │               ├── data/
            │               │   ├── local/          # Room DB, DAOs, Converters
            │               │   ├── model/          # Entities & Data Models
            │               │   ├── remote/         # Retrofit APIs & Network
            │               │   └── repository/     # Repository Pattern implementations
            │               ├── ui/
            │               │   ├── map/            # Map Fragment & ViewModels
            │               │   ├── hazards/        # Incident reporting & lists
            │               │   ├── scout/          # AI Road Scout chat & tips
            │               │   ├── offline/        # Offline corridor manager
            │               │   └── profile/        # SOS & driver settings
            │               └── utils/              # TTS Voice, Geo Math, Helpers
            └── res/
                ├── drawable/           # Vector assets (ic_hazard, ic_fuel, etc.)
                ├── layout/             # Android XML Layouts & ViewBinding
                ├── menu/               # Bottom navigation menus
                ├── navigation/         # Jetpack Navigation graph (mobile_navigation.xml)
                └── values/             # strings.xml, colors.xml, themes.xml
```

---

## 🇿🇦 Key Features
- **Real-Time Traffic Hazards:** Track and report potholes, load-shedding signal outages, collisions, and speed traps across South Africa (N1, N2, N3, N4, N12).
- **Corridor Route Planning:** Turn-by-turn navigation with multi-stops (Engen 1-Stop, Shell Ultra City, TotalEnergies, Wimpy).
- **Offline First:** Local caching with Room DB and offline fallback routing.
- **AI Road Scout:** Gemini-powered assistant for South African highway road advice and route summaries.
- **Voice Guidance:** Native TTS engine for driving prompts.

---

## 🚀 How to Open in Android Studio
1. Clone this repository or extract the unzipped folder.
2. Launch **Android Studio** (Hedgehog, Iguana, Ladybug or newer).
3. Select **File -> Open...** and choose this project root folder.
4. Allow Gradle to sync dependencies automatically.
5. Build and run on an Android Emulator or connected physical device (API 24+).
