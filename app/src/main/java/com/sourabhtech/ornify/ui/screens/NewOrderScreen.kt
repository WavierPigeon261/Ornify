package com.sourabhtech.ornify.ui.screens

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.CurrencyRupee
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import coil.compose.AsyncImage
import com.sourabhtech.ornify.ui.OrderViewModel
import com.sourabhtech.ornify.util.DateUtils
import com.sourabhtech.ornify.util.ImageStorageUtils
import java.io.File
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewOrderScreen(
    viewModel: OrderViewModel,
    onNavigateBack: () -> Unit
) {
    val context = LocalContext.current
    val orderToEdit by viewModel.orderToEdit.collectAsState()
    val isEditMode = orderToEdit != null

    // Category options
    val primaryCategories = listOf(
        "Polish",
        "Repairs",
        "Gold ornament making",
        "Silver ring making"
    )
    val goldSubCategories = listOf(
        "Karimani Kanti",
        "Ring",
        "Chain"
    )

    var selectedCategory by remember(orderToEdit) {
        mutableStateOf(orderToEdit?.orderType ?: primaryCategories[0])
    }
    var isCategoryDropdownExpanded by remember { mutableStateOf(false) }

    var selectedSubCategory by remember(orderToEdit) {
        mutableStateOf(orderToEdit?.subCategory ?: goldSubCategories[0])
    }
    var isSubCategoryDropdownExpanded by remember { mutableStateOf(false) }

    var priceInput by remember(orderToEdit) {
        mutableStateOf(orderToEdit?.let { "%.2f".format(it.approximatePrice) } ?: "")
    }
    var priceError by remember { mutableStateOf(false) }

    // Date Picker state
    val defaultDeliveryCalendar = remember {
        Calendar.getInstance().apply { add(Calendar.DAY_OF_YEAR, 3) }
    }
    var selectedDeliveryDateMillis by remember(orderToEdit) {
        mutableStateOf(orderToEdit?.deliveryDateMillis ?: defaultDeliveryCalendar.timeInMillis)
    }
    var showDatePickerDialog by remember { mutableStateOf(false) }

    // Images state
    val capturedImagePaths = remember(orderToEdit) {
        mutableStateListOf<String>().apply {
            orderToEdit?.let { addAll(it.imagePaths) }
        }
    }
    var currentPhotoFile by remember { mutableStateOf<File?>(null) }
    var currentPhotoUri by remember { mutableStateOf<Uri?>(null) }

    // Camera launcher
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success && currentPhotoFile != null) {
            currentPhotoFile?.absolutePath?.let { path ->
                capturedImagePaths.add(path)
            }
        }
    }

    // Permission launcher
    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            try {
                val (uri, file) = ImageStorageUtils.createTempPictureUri(context)
                currentPhotoUri = uri
                currentPhotoFile = file
                cameraLauncher.launch(uri)
            } catch (e: Exception) {
                Toast.makeText(context, "Error starting camera: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(context, "Camera permission is required to take order photos", Toast.LENGTH_LONG).show()
        }
    }

    fun launchCamera() {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            try {
                val (uri, file) = ImageStorageUtils.createTempPictureUri(context)
                currentPhotoUri = uri
                currentPhotoFile = file
                cameraLauncher.launch(uri)
            } catch (e: Exception) {
                Toast.makeText(context, "Error starting camera: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        } else {
            cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (isEditMode) "Edit Order" else "New Order",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        viewModel.setOrderToEdit(null)
                        onNavigateBack()
                    }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Heading in bold text at the top
            Text(
                text = if (isEditMode) "Edit Order" else "New Order",
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            )

            // Panel 1: "Select the type of order"
            OrderSectionCard(title = "Select the type of order") {
                ExposedDropdownMenuBox(
                    expanded = isCategoryDropdownExpanded,
                    onExpandedChange = { isCategoryDropdownExpanded = !isCategoryDropdownExpanded }
                ) {
                    OutlinedTextField(
                        value = selectedCategory,
                        onValueChange = {},
                        readOnly = true,
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isCategoryDropdownExpanded) },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.outline
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor()
                    )

                    ExposedDropdownMenu(
                        expanded = isCategoryDropdownExpanded,
                        onDismissRequest = { isCategoryDropdownExpanded = false }
                    ) {
                        primaryCategories.forEach { category ->
                            DropdownMenuItem(
                                text = { Text(category, style = MaterialTheme.typography.bodyLarge) },
                                onClick = {
                                    selectedCategory = category
                                    isCategoryDropdownExpanded = false
                                }
                            )
                        }
                    }
                }

                // Conditional Subcategory Dropdown for "Gold ornament making"
                if (selectedCategory == "Gold ornament making") {
                    Spacer(modifier = Modifier.height(14.dp))
                    Text(
                        text = "Gold Ornament Type",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.primary
                        )
                    )
                    Spacer(modifier = Modifier.height(6.dp))

                    ExposedDropdownMenuBox(
                        expanded = isSubCategoryDropdownExpanded,
                        onExpandedChange = { isSubCategoryDropdownExpanded = !isSubCategoryDropdownExpanded }
                    ) {
                        OutlinedTextField(
                            value = selectedSubCategory,
                            onValueChange = {},
                            readOnly = true,
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isSubCategoryDropdownExpanded) },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = MaterialTheme.colorScheme.primary,
                                unfocusedBorderColor = MaterialTheme.colorScheme.outline
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .menuAnchor()
                        )

                        ExposedDropdownMenu(
                            expanded = isSubCategoryDropdownExpanded,
                            onDismissRequest = { isSubCategoryDropdownExpanded = false }
                        ) {
                            goldSubCategories.forEach { subCategory ->
                                DropdownMenuItem(
                                    text = { Text(subCategory, style = MaterialTheme.typography.bodyLarge) },
                                    onClick = {
                                        selectedSubCategory = subCategory
                                        isSubCategoryDropdownExpanded = false
                                    }
                                )
                            }
                        }
                    }
                }
            }

            // Panel 2: "Approximate Price"
            OrderSectionCard(title = "Approximate Price") {
                Text(
                    text = "Enter approximate amount (In ₹):",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = priceInput,
                    onValueChange = { input ->
                        if (input.all { it.isDigit() || it == '.' }) {
                            priceInput = input
                            priceError = false
                        }
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.CurrencyRupee,
                            contentDescription = "Rupees",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    },
                    placeholder = { Text("0.00") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    isError = priceError,
                    supportingText = {
                        if (priceError) {
                            Text("Please enter a valid amount", color = MaterialTheme.colorScheme.error)
                        }
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.outline
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
            }

            // Panel 3: "Deliver by:"
            OrderSectionCard(title = "Deliver by:") {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.45f))
                        .clickable { showDatePickerDialog = true }
                        .padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "Selected Delivery Date",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = DateUtils.formatDate(selectedDeliveryDateMillis),
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            )
                        }

                        Icon(
                            imageVector = Icons.Default.CalendarMonth,
                            contentDescription = "Pick date",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                }
            }

            // Panel 4: "Additional Images (If any)"
            OrderSectionCard(title = "Additional Images (If any)") {
                Button(
                    onClick = { launchCamera() },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    ),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        imageVector = Icons.Default.CameraAlt,
                        contentDescription = "Take picture",
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Take Picture",
                        style = MaterialTheme.typography.labelLarge
                    )
                }

                if (capturedImagePaths.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(14.dp))
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        itemsIndexed(capturedImagePaths) { index, path ->
                            Box(
                                modifier = Modifier
                                    .size(90.dp)
                                    .clip(RoundedCornerShape(12.dp))
                            ) {
                                AsyncImage(
                                    model = File(path),
                                    contentDescription = "Captured image $index",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.fillMaxSize()
                                )

                                Box(
                                    modifier = Modifier
                                        .align(Alignment.TopEnd)
                                        .padding(4.dp)
                                        .size(22.dp)
                                        .clip(CircleShape)
                                        .background(Color.Black.copy(alpha = 0.7f))
                                        .clickable {
                                            capturedImagePaths.removeAt(index)
                                        },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Close,
                                        contentDescription = "Delete image",
                                        tint = Color.White,
                                        modifier = Modifier.size(14.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Bottom button: "Save Order" / "Update Order"
            Button(
                onClick = {
                    val amount = priceInput.toDoubleOrNull()
                    if (amount == null || amount <= 0.0) {
                        priceError = true
                        Toast.makeText(context, "Please enter a valid approximate amount", Toast.LENGTH_SHORT).show()
                        return@Button
                    }

                    val subCategoryToSave = if (selectedCategory == "Gold ornament making") {
                        selectedSubCategory
                    } else {
                        null
                    }

                    viewModel.saveOrder(
                        existingOrderId = orderToEdit?.id,
                        orderType = selectedCategory,
                        subCategory = subCategoryToSave,
                        approximatePrice = amount,
                        deliveryDateMillis = selectedDeliveryDateMillis,
                        imagePaths = capturedImagePaths.toList(),
                        onSaved = {
                            val msg = if (isEditMode) "Order updated successfully!" else "Order saved successfully!"
                            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                            onNavigateBack()
                        }
                    )
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = if (isEditMode) "Update Order" else "Save Order",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                )
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }

    // Material 3 Date Picker Dialog Overlay
    if (showDatePickerDialog) {
        val datePickerState = rememberDatePickerState(
            initialSelectedDateMillis = selectedDeliveryDateMillis
        )
        DatePickerDialog(
            onDismissRequest = { showDatePickerDialog = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        datePickerState.selectedDateMillis?.let {
                            selectedDeliveryDateMillis = it
                        }
                        showDatePickerDialog = false
                    }
                ) {
                    Text("OK", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePickerDialog = false }) {
                    Text("Cancel")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}

@Composable
private fun OrderSectionCard(
    title: String,
    content: @Composable () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            )
            Spacer(modifier = Modifier.height(10.dp))
            content()
        }
    }
}
