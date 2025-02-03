package com.example.wizardmorat2

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.wizardmorat.ui.theme.barrioFontFamily
import com.example.wizartmorat2.R
import kotlinx.coroutines.delay

@Composable
fun HomeScreen(navController: NavController) {
    Scaffold(

    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            // Image as the background
            Image(
                painter = painterResource(id = R.drawable.wizard),
                contentDescription = "Home Image",
                modifier = Modifier
                    .fillMaxSize()

                    .align(Alignment.Center), // Directly aligns the image to the center

                contentScale = ContentScale.Crop // Adjust the scale as needed
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.25f) // Set height as 1/4 of the screen height
                    .background(Color.Black.copy(alpha = 0.5f)) // Semi-transparent black background
                    .align(Alignment.BottomStart) // Position it at the top center

            ) {
                // Add content inside the top Box
                Column (
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                ){
                    Box(
                        modifier = Modifier
                            .weight(2f)
                            .fillMaxSize()
                            .padding(10.dp)
                    ) {
                        TypingAnimation(
                            textToType = "Mar7be Bikom Fi Wizard version Morat !"
                        )
                    }
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {
                        Button(
                            onClick = {
                                navController.navigate("gameScreen")
                            },
                            modifier = Modifier
                                .align(Alignment.Center)
                                .fillMaxWidth(0.7f), // Removes the background modifier here
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF4B0082), // Set the background color to indigo
                                contentColor = Color.White // Set the text color to white
                            )
                        ) {
                            Text(text = "Wizards") // Text color will now be white
                        }
                    }






                }
            }
        }
    }

}

@Composable
fun TypingAnimation(textToType: String) {
    // State to hold the text currently being displayed
    var displayedText by remember { mutableStateOf("") }
    // Start the animation on layout load
    LaunchedEffect(textToType) {
        for (i in textToType.indices) {
            displayedText = textToType.substring(0, i + 1) // Reveal one character at a time
            delay(100) // Delay for typing speed (100ms per character)
        }
    }

    // Display the animated text
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        contentAlignment = Alignment.Center // Align the text to the center
    ) {
        Text(
            text = displayedText,
            fontSize = 24.sp, // Set the font size to 24sp,
            fontFamily = barrioFontFamily,
            fontWeight = FontWeight.Bold, // Set the font weight to bold
            color = Color.White
        )
    }
}

