package com.example.doggy.screens.breeddetail

/**
 * Input: Dog Data
 * Output: Detail page containing image, name etc.
 */
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.doggy.network.DogInfo
import com.example.doggy.ui.theme.myColour2

@Composable
fun DogDetailScreen(dogInfo: DogInfo) {
    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(dogInfo.imageUrl)
            .fallback(coil.base.R.drawable.notification_template_icon_low_bg)
            .crossfade(true)
            .build()
    )
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Card(
            shape = RoundedCornerShape(20.dp),
            elevation = 20.dp,
            border = BorderStroke(1.dp, myColour2),
            modifier = Modifier.padding(10.dp)
        ) {
            Image(
                painter = painter,
                contentDescription = "Dog Image",
                modifier = Modifier.size(height = 300.dp, width = 300.dp),
                contentScale = ContentScale.Crop
            )
        }
        Card(
            backgroundColor = myColour2,
            shape = RoundedCornerShape(20.dp),
            elevation = 20.dp,
            modifier = Modifier.padding(40.dp),
            contentColor = Color.White,
            border = BorderStroke(1.dp, myColour2)
        )
        {
            SelectionContainer {
                Column(
                    modifier = Modifier.padding(
                        top = 20.dp,
                        bottom = 20.dp,
                        start = 10.dp,
                        end = 10.dp
                    )
                ) {
                    Row {
                        Text(
                            text = "ID: ",
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = dogInfo.id.toString(),
                        )
                    }
                    Row {
                        Text(
                            text = "Name: ",
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = dogInfo.name ?: "Unknown",
                        )
                    }
                    Row {
                        val annotatedString = buildAnnotatedString {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                append("Bred For: ")
                            }
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Normal)) {
                                append(dogInfo.bredFor ?: "Unknown")
                            }
                        }
                        Text(
                            text = annotatedString,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Row {
                        Text(
                            text = "Bred Group: ",
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = dogInfo.breedGroup ?: "Unknown",
                        )
                    }
                    Row {
                        Text(
                            text = "Life Span: ",
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = dogInfo.lifeSpan ?: "Unknown",
                        )
                    }
                    Row {
                        Text(
                            text = "Origin: ",
                            fontWeight = FontWeight.Bold
                        )
                        val safeOrigin = if (dogInfo.origin.isNullOrBlank()) {
                            "Unknown"
                        } else {
                            dogInfo.origin
                        }
                        Text(
                            text = safeOrigin,
                        )
                    }
                    Row {
                        val annotatedTemperament = buildAnnotatedString {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                append("Temperament: ")
                            }
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Normal)) {
                                append(
                                    dogInfo.temperament
                                        ?: "Sorry, this information is not available."
                                )
                            }
                        }
                        Text(
                            text = annotatedTemperament,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Row {
                        Text(
                            text = "Height(In Metric): ",
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = dogInfo.height?.metric ?: "Unknown",
                        )
                    }
                    Row {
                        Text(
                            text = "Weight(In Metric): ",
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = dogInfo.weight?.metric ?: "Unknown",
                        )
                    }
                } // Column contains rows end
            } // Selection Container end
        } // Card end
    } // Parent column end
}