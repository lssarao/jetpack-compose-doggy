package com.example.doggy

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.doggy.ui.theme.myColour2

@Composable
fun TopBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Dog Breeds",
            style = MaterialTheme.typography.h6,
            fontWeight = FontWeight.ExtraBold,
            modifier = Modifier.padding(start = 16.dp)
        )
        IconButton(
            onClick = { },
            modifier = Modifier.padding(end = 16.dp)
        ) {
            Icon(
                imageVector = Icons.Rounded.Search,
                contentDescription = "Search"
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TopBarPreview() {
    TopBar()
}

@Composable
fun SearchBar() {
    OutlinedTextField(
        value = "",
        onValueChange = {},
        modifier = Modifier.fillMaxWidth(),
        leadingIcon = {
            Icon(
                imageVector = Icons.Rounded.Search,
                contentDescription = "",
                tint = Color.White.copy(
                    alpha = ContentAlpha.medium
                )
            )
        },
        trailingIcon = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Rounded.Close,
                    contentDescription = "",
                    tint = myColour2
                )
            }
        },
        singleLine = true,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            unfocusedBorderColor = Color.White.copy(
                alpha = ContentAlpha.medium
            ),
            focusedBorderColor = Color.White,
            cursorColor = Color.White,
        ),
        placeholder = {
            Text(
                text = "Search here...",
            )
        }
    )
}

@Preview(showBackground = true)
@Composable
fun AppPreview() {
    SearchBar()
}