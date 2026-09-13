# 💎 Ornify - Jewellery Shop Orders Android App

A modern Android application designed for jewellery shop owners to record and track customer orders, specifications, delivery dates, and photo references.

- **App Name**: Ornify
- **Package ID**: `com.sourabhtech.ornify`
- **Design System**: Material You (Dynamic Color adapting to phone wallpaper & theme)

---

## ✨ Features

1. **Dashboard & Pending Orders**:
   - Displays all active pending orders in clean cards.
   - Shows **"No pending orders"** if no orders are currently placed.
   - Displays order type, subcategory badge, approximate price formatted in ₹ (Indian Rupee), and delivery date.
   - **Rounded `+` button** at the bottom-right corner to create new orders.
   - **History clock icon** on the top-right corner to access past completed orders.

2. **Material You Dynamic Theming**:
   - Adapts to your device's wallpaper and system color palette on Android 12+ (Android 12, 13, 14, 15).
   - Automatically supports system light and dark themes.
   - Elegant warm gold palette fallback for older Android versions.

3. **New Order & Editing Saved Orders**:
   - Create new orders or **edit existing saved orders**.
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
     - Calendar overlay dialog to select customer delivery date.
   - **Additional Images (If any) panel**:
     - Camera button to capture photos of ornaments, designs, or sample pieces.
     - Saves photos directly into internal app storage and displays thumbnail previews with remove options.
   - **Save / Update Order**:
     - Persists all order details and photos locally into a Room SQLite database on the phone.

4. **Order Details View**:
   - Tap any card in the dashboard or history to open its details.
   - **Creation date in italic style at the top**: *"Placed on [date]"*.
   - **Modification date in italic style**: *"Modified on [date]"* (shown whenever an order is edited).
   - View high-resolution captured jewellery photos.
   - Action buttons: **Edit**, **Delete**, and **Complete / Reopen**.

5. **History & Delete History**:
   - Tap the clock icon on the top-right corner of the dashboard to view the History panel.
   - Displays all completed orders.
   - **Delete History button** on the top-right corner of the history panel to clear past records with a confirmation dialog.

---

## 🚀 How to Build the APK using GitHub Actions

Every push to your GitHub repository automatically triggers the GitHub Actions CI workflow to compile the app and generate the debug APK.

### Download the APK:
1. Open your repository on [GitHub](https://github.com/WavierPigeon261/Ornify).
2. Go to the **Actions** tab.
3. Click on the latest run of **"Build Android APK"**.
4. Download the **`Ornify-Debug-APK`** artifact at the bottom.
5. Install `app-debug.apk` directly on your phone!
