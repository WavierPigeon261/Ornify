# 💎 Ornify - Jewellery Shop Orders Android App

[![Build Status](https://github.com/WavierPigeon261/Ornify/actions/workflows/android.yml/badge.svg)](https://github.com/WavierPigeon261/Ornify/actions)
[![Language](https://img.shields.io/badge/language-Kotlin-blue.svg)](https://kotlinlang.org/)
[![Android](https://img.shields.io/badge/Android-12%2B-brightgreen.svg)](https://www.android.com/)
[![License](https://img.shields.io/badge/license-MIT-green.svg)](LICENSE)

A modern Android application designed for jewellery shop owners to efficiently record, track, and manage customer orders with specifications, delivery dates, and photo references.

**Package ID**: `com.sourabhtech.ornify`

---

## ✨ Key Features

### 1. **Dashboard & Pending Orders**
- 📋 View all active pending orders in elegant card layouts
- 💰 Display order type, subcategory, price in ₹ (Indian Rupee), and delivery date
- ✨ "No pending orders" message when inventory is clear
- ➕ Rounded floating action button to create new orders
- ⏰ History icon to access completed orders

### 2. **Material You Dynamic Theming**
- 🎨 Adapts to device wallpaper and system color palette
- 🌓 Supports Android 12, 13, 14, and 15+
- 🌙 Automatic light and dark theme switching
- ✨ Warm gold palette fallback for older Android versions

### 3. **Order Management**
Create and edit orders with comprehensive details:

**Order Types:**
- Polish
- Repairs
- Gold ornament making
- Silver ring making

**Conditional Subcategories** (Gold ornament making):
- Karimani Kanti
- Ring
- Chain

**Order Details:**
- 💵 Approximate price input (₹ currency)
- 📅 Delivery date calendar picker
- 📸 Photo capture and gallery
- 💾 Local database persistence

### 4. **Order Details View**
- 📖 Comprehensive order information display
- 📝 Creation and modification timestamps in italic
- 🖼️ High-resolution jewellery photo gallery
- ✏️ **Edit** order details
- 🗑️ **Delete** orders
- ✅ **Complete/Reopen** orders

### 5. **History Management**
- ⏱️ Complete history of finished orders
- 🧹 Clear all history with confirmation dialog
- 📊 Track completed work over time

---

## 🚀 Getting Started

### Prerequisites
- Android Studio (latest version)
- JDK 11 or higher
- Android SDK 31+ (Target: Android 12+)
- Kotlin 1.9+

### Clone the Repository
```bash
git clone https://github.com/WavierPigeon261/Ornify.git
cd Ornify
```

### Build Locally
1. Open the project in Android Studio
2. Sync Gradle files
3. Select your target device/emulator
4. Click **Run** or press `Shift + F10`

---

## 📦 Building APK with GitHub Actions

Every push automatically triggers our CI/CD workflow to build a debug APK.

### Download Built APK:
1. Navigate to [GitHub Actions](https://github.com/WavierPigeon261/Ornify/actions)
2. Click the latest **"Build Android APK"** workflow run
3. Download **`Ornify-Debug-APK`** artifact
4. Install `app-debug.apk` directly on your Android device:
   ```bash
   adb install app-debug.apk
   ```

---

## 🏗️ Project Structure

```
Ornify/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/sourabhtech/ornify/
│   │   │   │   ├── MainActivity
│   │   │   │   ├── fragments/
│   │   │   │   ├── database/
│   │   │   │   ├── adapters/
│   │   │   │   └── utils/
│   │   │   ├── res/
│   │   │   │   ├── layout/
│   │   │   │   ├── values/
│   │   │   │   └── drawable/
│   │   │   └── AndroidManifest.xml
│   │   ├── test/
│   │   └── androidTest/
│   └── build.gradle
├── build.gradle
├── settings.gradle
└── README.md
```

---

## 🛠️ Technology Stack

| Technology | Purpose |
|-----------|---------|
| **Kotlin** | Primary language |
| **Android Jetpack** | Modern Android development |
| **Material You** | Dynamic theming |
| **Room Database** | Local data persistence |
| **View Binding** | Type-safe view access |
| **Fragments** | UI modularity |
| **GitHub Actions** | CI/CD automation |

---

## 📋 Permissions Required

- `android.permission.CAMERA` - Capture photos of jewellery
- `android.permission.READ_EXTERNAL_STORAGE` - Access gallery
- `android.permission.WRITE_EXTERNAL_STORAGE` - Save photos (API < 30)

---

## 🎯 Use Cases

✅ Small to medium jewellery shop owners  
✅ Track custom order requests  
✅ Manage delivery commitments  
✅ Store design references and photos  
✅ Maintain order history and records  

---

## 📝 License

This project is open source and available under the MIT License. See the [LICENSE](LICENSE) file for details.

---

## 🤝 Contributing

Contributions are welcome! Please feel free to:
1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

---

## 📧 Contact & Support

For questions, bug reports, or feature requests, please open an [issue](https://github.com/WavierPigeon261/Ornify/issues) on GitHub.

---

## 🙌 Acknowledgments

- Android Material Design Team
- Kotlin Community
- Open-source contributors

---

**Made with ❤️ by [WavierPigeon261](https://github.com/WavierPigeon261)**

*Last Updated: September 2026*
