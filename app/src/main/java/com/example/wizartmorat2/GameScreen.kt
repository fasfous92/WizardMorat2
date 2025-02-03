package com.example.wizardmorat2

import android.os.Bundle
import android.os.Parcel
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.dropUnlessResumed
import androidx.navigation.NavController
import androidx.navigation.navArgument
import com.example.wizardmorat.ui.theme.barrioFontFamily

import org.json.JSONArray
import org.json.JSONObject
import kotlin.math.abs


@Composable
fun FixedValueRangeSlider(valuesList: List<Int>,
                          selectedValue: Int,
                          onValueChange: (Int) -> Unit) {
    // Convert the list to a float list for the slider
    val floatList = valuesList.map { it.toFloat() }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Display the currently selected value
        Text(
            text = "${selectedValue.toInt()} Players",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Slider
        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.Center,
        ) {
            // Draw labels for each value in the list above the slider
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                floatList.forEach { value ->
                    Text(
                        text = value.toInt().toString(),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = FontFamily.SansSerif,
                        color = Color.Black,
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                }
            }

        }

        Spacer(modifier = Modifier.height(16.dp))




        // Slider implementation
        Slider(
            value = selectedValue.toFloat(),
            onValueChange = { newValue ->
                val nearestValue = floatList.minByOrNull { abs(it - newValue.toInt()) } ?: selectedValue
                onValueChange(nearestValue.toInt()) // Update the state through the callback
            },
            valueRange = floatList.first().toFloat()..floatList.last().toFloat(),
            steps = floatList.size - 2, // Number of steps between values
            modifier = Modifier.fillMaxWidth()
        )
    }
}


fun isListValid(inputList: List<String>): Boolean {
    // Check if all elements are not empty

    if (inputList.all { inputValid(it) } && inputList.distinct().size == inputList.size) {
        return true
    }

    return false

}
fun inputValid(text: String): Boolean{
    if(text.length>=3) return true
    return false
}

fun prepareData(title: String, playerName : List<String>): JSONObject {
val result = JSONObject().apply {
    put("title", title)
    put("players", JSONArray(playerName))
}

    return result


}



    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun GameScreen(navController: NavController
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "Wizard Morat", // Title of the TopAppBar
                            fontSize = 24.sp, // Set the font size to 24sp,
                            fontFamily = barrioFontFamily,
                            fontWeight = FontWeight.Bold, // Set the font weight to bold
                            color = Color.White
                        )
                    },
                    modifier = Modifier.fillMaxWidth(), // Makes the TopAppBar fill the full width of the screen
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color(0xFF4B0082), // Set the background color to indigo
                        titleContentColor = Color.White // Title text color
                    )
                )
            }

        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .background(Color(0xFFFDF5E6))
            ) {
                var errorMessage by remember { mutableStateOf("") }
                var selectedValue by remember { mutableStateOf(3) } // Initial value is 3
                var playersNames =
                    remember { mutableStateListOf<String>() }
                repeat(selectedValue){playersNames.add("")}// Initialize an empty mutable list
                var gameTitle by remember { mutableStateOf("") } // Correct state declaration

                val focusRequester = remember { FocusRequester() }
                val keyboardController = LocalSoftwareKeyboardController.current
                val titleFocusRequester = remember { FocusRequester() } // For the game title field



                Column(
                    modifier = Modifier
                        .border(BorderStroke(1.dp, Color.Gray), shape = RoundedCornerShape(8.dp))
                        .fillMaxWidth()
                        .padding(16.dp)
                        .weight(2f)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .border(
                                BorderStroke(1.dp, Color.Gray),
                                shape = RoundedCornerShape(8.dp)
                            ),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically

                    ) {

                        Text(
                            text = "Title",
                            fontSize = 20.sp,
                            color = Color.Black,
                            fontFamily = barrioFontFamily,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .weight(1f)
                                .padding(8.dp)

                        )

                        var titleValid=true
                        TextField(
                            value = gameTitle,
                            onValueChange = { gameTitle = it
                                            titleValid=gameTitle.length >=3
                            },
                            modifier = Modifier
                                .weight(3f)
                                .clip(RoundedCornerShape(8.dp))
                                .fillMaxWidth()
                                .focusRequester(titleFocusRequester)
                                .onFocusChanged { focusState ->
                                    if (focusState.isFocused) {
                                        keyboardController?.show()
                                    }
                                }
                                .padding(0.dp), // Remove inner padding
                            isError = !titleValid,
                            placeholder = {
                                if (!titleValid) Text(text = "Title must be atleast 3 characters", fontFamily = FontFamily.SansSerif, fontSize = 16.sp)
                            },
                            colors = TextFieldDefaults.textFieldColors(
                                containerColor = Color(0xFFFDF5E6),
                                cursorColor = Color.Gray,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent
                            ),
                            textStyle = TextStyle(
                                color = Color.Black,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = barrioFontFamily
                            ),


                            )

                        LaunchedEffect(Unit) {
                            titleFocusRequester.requestFocus()
                        }

                    }








                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                            .fillMaxHeight(0.3f)
                            .padding(8.dp)
                            .drawBehind {
                                val borderWidth = 3.dp.toPx()
                                drawLine(
                                    color = Color.Gray,
                                    start = Offset(0f, 0f), // Top border start
                                    end = Offset(size.width, 0f), // Top border end
                                    strokeWidth = borderWidth
                                )
                                drawLine(
                                    color = Color.Gray,
                                    start = Offset(0f, size.height), // Bottom border start
                                    end = Offset(size.width, size.height), // Bottom border end
                                    strokeWidth = borderWidth
                                )
                            },
                        contentAlignment = Alignment.CenterStart


                    )
                    {


                        Text(
                            text = "9adech Min Wa7ed",
                            fontSize = 20.sp,
                            fontFamily = barrioFontFamily,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,

                            )
                    }

                    Box(
                        modifier = Modifier
                            .border(
                                BorderStroke(1.dp, Color.Gray),
                                shape = RoundedCornerShape(8.dp)
                            )
                            .weight(2f)
                            .padding(8.dp)
                            .fillMaxSize()
                    ) {
                        // The slider in the center of the screen
                        FixedValueRangeSlider(
                            valuesList = listOf(3, 4, 5, 6),
                            selectedValue = selectedValue,
                            onValueChange = { newValue: Int ->
                                selectedValue = newValue
                                playersNames.clear()
                                repeat(selectedValue) { playersNames.add("") }
                                Log.d("Select","${playersNames.size}")
                            },
                        )


                    }


                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(3f)
                        .padding(16.dp) // Padding outside the Row
                        .clip(RoundedCornerShape(8.dp)) // Clip the shape of the Row to match RoundedCornerShape
                ) {
                    for (i in 0 until selectedValue) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .sizeIn(
                                    maxHeight = 60.dp // Set the maximum height
                                )
                                .background(
                                    Color(0xFFFDF5E6),
                                    shape = RoundedCornerShape(8.dp)
                                ) // Rounded background
                                .border(
                                    BorderStroke(1.dp, Color.Gray),
                                    shape = RoundedCornerShape(8.dp)
                                ),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(8.dp)
                                    .weight(1f),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "${i + 1}",
                                    fontSize = 24.sp,
                                    fontFamily = FontFamily.SansSerif,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Black,
                                )
                            }


    //                        var inputText by remember { mutableStateOf("") } // Correct state declaration
    //                        playersNames.add(inputText)

                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .weight(4f),
                                contentAlignment = Alignment.Center
                            ) {
                                var isValid = true

                                TextField(
                                    value = playersNames[i],
                                    onValueChange = {
                                        playersNames[i] = it
                                        isValid = playersNames[i].length >= 3
                                    }, // Update inputText directly
                                    modifier = Modifier
                                        .fillMaxWidth(0.9f)
                                        .fillMaxHeight(0.9f)
                                        .focusRequester(focusRequester)
                                        .onFocusChanged { focusState ->
                                            if (focusState.isFocused) {
                                                keyboardController?.show()
                                            }
                                        }
                                        .clip(shape = RoundedCornerShape(8.dp)),
                                    isError = !isValid,
                                    placeholder = {
                                        if (!isValid) Text(text = "Username must be at least 3 characters", fontFamily = FontFamily.SansSerif)
                                    },
                                    colors = TextFieldDefaults.textFieldColors(
                                        containerColor = Color(0xFFFDF5E6), // Background color of the TextField
                                        cursorColor = Color.Gray, // Color of the blinking cursor
                                        focusedIndicatorColor = Color.Transparent, // No underline when focused
                                        unfocusedIndicatorColor = Color.Transparent // No underline when not focused
                                    ),
                                    textStyle = TextStyle(
                                        color = Color.Black, // Text color
                                        fontSize = 18.sp, // Font size
                                        fontWeight = FontWeight.Bold, // Font weight
                                        fontFamily = FontFamily.SansSerif // Font family
                                    ),
                                )


                                LaunchedEffect(Unit) {
                                    focusRequester.requestFocus() // Request focus to trigger the keyboard
                                }
                            }
                        }

                    }
                }


                Column (
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.8f),
                    horizontalAlignment = Alignment.CenterHorizontally

                    ) {


                    // Show error message if any input is empty
                    if (errorMessage.isNotEmpty()) {
                        Text(
                            text = errorMessage,
                            color = Color.Red,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.SansSerif,
                            modifier = Modifier
                                .padding(8.dp)
                                .weight(0.5f)
                        )

                    }
                    Button(
                        onClick = {
                            // Check if Title is good or not
                            if(inputValid(gameTitle)){
                                var filteredList = playersNames.take(selectedValue)
                                var lowFilteredList=filteredList.map { it.lowercase() }
                                if (isListValid(lowFilteredList)) {
                                    //eveything is good
                                    errorMessage = ""
                                    val data= prepareData(gameTitle, filteredList)

                                    Log.d("GameScreen", "no error")

                                    navController.navigate("scoreScreen?gameJson=$data")


                                }else{
                                  // Log.d("Select","lease fill all players name uniquely")

                                   errorMessage = "please fill all players names uniquely"
                                }
                            }
                             else {
                                // Otherwise, gather the values (for demonstration, you could print them)
                                errorMessage = "Error in Title"  // Clear error message
                                //Log.d("Select","Error in Title")


                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth(0.5f)
                            .padding(vertical = 16.dp)
                            .weight(1f)
                    ) {
                        Text(text = "Barra nebdaw", fontFamily = barrioFontFamily, fontSize = 20.sp)
                    }





                }

            }

        }
    }







