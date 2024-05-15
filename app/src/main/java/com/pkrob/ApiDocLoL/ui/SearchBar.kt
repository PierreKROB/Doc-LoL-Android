package com.pkrob.ApiDocLoL.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SearchBar(query: String, onQueryChanged: (String) -> Unit, label: String) {
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChanged,
        label = { Text(text = label) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    )
}
