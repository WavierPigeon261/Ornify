# 💍 Jewellery Shop Orders Android App

A tailor-made, easy-to-use Android application designed for jewellery shop owners to record and track customer orders, specifications, delivery dates, and photo references.

---

## ✨ Features

1. **Dashboard & Pending Orders**:
   - Displays all active pending orders in clean, organized cards.
   - Shows **"No pending orders"** if no orders are currently placed.
   - Displays order type, subcategory badge, approximate price formatted in ₹ (Indian Rupee), and delivery date.
   - **Rounded `+` button** at the bottom-right corner to create new orders.

2. **New Order Creation Panel**:
   - **Heading in bold text**: `"New Order"`.
   - **Select the type of order** dropdown:
     - *Polish*
     - *Repairs*
     - *Gold ornament making*
     - *Silver ring making*
   - **Conditional Subcategory Dropdown** (appears only when *Gold ornament making* is selected):
     - *Karimani Kanti*
     - *Ring*
     - *Chain*
   - **Approximate Price panel**:
     - Label: `"Enter approximate amount (In ₹):"`
     - Numeric input box with ₹ currency icon.
   - **Deliver by panel**:
     - Interactive calendar overlay dialog to pick the exact date of delivery.
   - **Additional Images (If any) panel**:
     - Camera button that opens the phone camera to capture photos of ornaments, designs, or sample pieces.
     - Saves photos directly into safe internal app storage and displays thumbnail previews with remove options.
   - **Save Order button**:
     - Persists all order details and photos locally into a Room SQLite database on the phone.

3. **Order Details View**:
   - Tap any card in the dashboard to open its complete details.
   - **Italic timestamp at the very top**: *"Placed on [date]"*.
   - View high-resolution captured jewellery photos.
   - Mark order as **Completed** or **Delete**.

---

## 🚀 How to Build the APK using GitHub Actions

Because your laptop is low on resources, you don't need to install Android Studio or the Android SDK on your computer. GitHub Actions will build the `.apk` file for you in the cloud for free!

### Step 1: Initialize Git and Push to GitHub

In your terminal (inside this folder `/workspace/shop`):

```bash
git init
git add .
git commit -m "Initial commit of Jewellery Shop Orders App"
git branch -M main
git remote add origin https://github.com/<YOUR-GITHUB-USERNAME>/<YOUR-REPO-NAME>.git
git push -u origin main
```

*(Replace `<YOUR-GITHUB-USERNAME>` and `<YOUR-REPO-NAME>` with your actual GitHub username and repo name).*

### Step 2: Download the APK from GitHub

1. Open your repository in your web browser on [GitHub](https://github.com).
2. Click on the **Actions** tab at the top.
3. You will see a workflow named **"Build Android APK"** running automatically.
4. When it turns green (completed, takes ~2 minutes), click on it.
5. Scroll down to the **Artifacts** section at the bottom.
6. Click **`JewelleryShopOrders-Debug-APK`** to download the ZIP file.
7. Unzip it to get `app-debug.apk`.
8. Send `app-debug.apk` to your phone (via WhatsApp, Google Drive, or USB cable) and install it!

---

## 🛠 Tech Stack

- **Language**: Kotlin 1.9.22
- **UI Framework**: Jetpack Compose with Material 3 (Gold & Warm Shop Theme)
- **Architecture**: MVVM (Model-View-ViewModel) + Repository Pattern + Kotlin Coroutines & Flow
- **Database**: Android Jetpack Room 2.6.1 (SQLite local persistence)
- **Camera & Storage**: Android `FileProvider` + `ActivityResultContracts.TakePicture` + internal storage
- **Image Loading**: Coil Compose 2.5.0
- **CI/CD**: GitHub Actions workflow (`.github/workflows/build.yml`)
