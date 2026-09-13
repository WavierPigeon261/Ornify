# Ornify - Jewellery Shop Orders Android App

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

