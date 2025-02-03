package com.example.wizardmorat2

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.example.wizardmorat.ui.theme.barrioFontFamily
import com.example.wizartmorat2.grid

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PopupScore(boolscore: MutableState<Boolean>, currentPlay: MutableState<Int>, Score: grid, predictions:grid, players: List<String>,
               scoreText: MutableState<Array<Array<String>>>
) {
    var showPopup by remember { mutableStateOf(boolscore.value) }
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp
    val screenWidth = configuration.screenWidthDp

    // Determine height based on screen size
    val boxHeight = when {
        screenWidth > 600 -> (screenHeight * 0.5).dp // Tablet: Use 50% of screen height
        else -> (screenHeight * 0.7).dp // Phone: Use 30% of screen height
    }

    var buttonClick by remember { mutableStateOf<(() -> Unit)?>(null) }
    var errorMessage by remember { mutableStateOf("") }


    buttonClick = {
        // Check if Title is good or not
        if(checkScoreSum(scoreText,currentPlay.value,players.size)){
            //value are good then let's update scores
            updateScore(predictions,Score,currentPlay.value,players.size)

            errorMessage=""
            scoreText.value=Score.toStringArray()


            showPopup = false
            boolscore.value=false
            currentPlay.value++

        }
        else {
            // Otherwise, gather the values (for demonstration, you could print them)

            errorMessage = "Sum Of wins not equals to# cards"  // Clear error message
            //Log.d("Select","Error in Title")



        }
    }



    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
//        Button(onClick = { showPopup = true },
//            modifier = Modifier.fillMaxSize(0.8f)) {
//            Text("Give Prediction")
//        }

        if (showPopup) {
            Popup(
                alignment = Alignment.Center,
                onDismissRequest = { showPopup = false },
                properties = PopupProperties(focusable = true),
            ) {
                Box(
                    modifier = Modifier
                        //.size(500.dp)
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.5f)) // Semi-transparent grey background
                        .padding(20.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .fillMaxWidth(0.9f)
                            .height(boxHeight)
                            .background(Color(0xFFE6E6FA))
                    ) { // Center the content
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(0.2f),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Score: Round ${currentPlay.value}", // Title of the TopAppBar
                                fontSize = 18.sp, // Set the font size to 24sp,
                                fontFamily = barrioFontFamily,
                                fontWeight = FontWeight.Normal, // Set the font weight to bold
                                color = Color(0xFF4B0082)
                            )
                        }

                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .weight(2f),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(modifier = Modifier.fillMaxSize()
                                .padding(20.dp)
                                .border(BorderStroke(3.dp, Color(0xFF4B0082)), shape = RoundedCornerShape(8.dp))
                                .verticalScroll(
                                    state = rememberScrollState(), // Corrected: Use ScrollState instead of SwipeToDismissBoxState
                                    enabled = true // Optional, as true is the default
                                )
                            ){
                                for (i in 0 until players.size) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .defaultMinSize(minHeight = 60.dp)
                                            .padding((60/players.size+1).dp),                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Box(modifier = Modifier.fillMaxSize()
                                            .weight(0.8f)
                                            .padding(8.dp),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Text(
                                                text = players[i],
                                                fontFamily = barrioFontFamily,
                                                color = Color.Black,
                                                fontSize = 20.sp,
                                                textAlign = TextAlign.Center,

                                                )
                                        }

                                        Box( modifier = Modifier
                                            .width(60.dp)
                                            .height(60.dp)
                                            .border(BorderStroke(1.dp,  Color(0xFF4B0082)), shape = RoundedCornerShape(8.dp)),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            var inputValue by remember { mutableStateOf("".toString()) }
                                            var isError by remember { mutableStateOf(false) }

                                            TextField(
                                                value = inputValue,
                                                onValueChange = {
                                                    inputValue = it
                                                    isError = checkInput(it, currentPlay.value)
                                                    // Check if the input is not numerical
                                                    if (!isError) {
                                                        if (it.isNotEmpty()) {
                                                            Score.set(currentPlay.value, i + 1, it.toInt())
                                                        }
                                                        inputValue = it
                                                        scoreText.value[currentPlay.value][i + 1] = it
                                                    }
                                                },
                                                keyboardOptions = KeyboardOptions(
                                                    imeAction = ImeAction.Done,  // Set keyboard action to 'Done'
                                                    keyboardType = KeyboardType.Number // Set keyboard type to 'Number'
                                                ),
                                                keyboardActions = KeyboardActions(
                                                    onDone = {
                                                        buttonClick?.invoke()
                                                    },
                                                ),
                                                colors = TextFieldDefaults.textFieldColors(
                                                    containerColor = Color(0xFFE6E6FA),
                                                    cursorColor = Color.Gray,
                                                    focusedIndicatorColor = Color.Transparent,
                                                    unfocusedIndicatorColor = Color.Transparent
                                                ),
                                                textStyle = androidx.compose.ui.text.TextStyle(
                                                    fontFamily = FontFamily.SansSerif,
                                                    fontSize = 20.sp,
                                                    color = Color.Black
                                                ),
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .onKeyEvent { event ->
                                                        if (event.key == Key.Enter && event.type == KeyEventType.KeyUp) {
                                                            buttonClick?.invoke() // Simulate button click
                                                            true
                                                        } else {
                                                            false
                                                        }
                                                    },
                                                isError = isError // Highlights the text field if there's an error
                                            )

                                            // Display an error message if the input is invalid
                                            if (isError) {
                                                Text(
                                                    text = "numbers only",
                                                    color = Color.Red,
                                                    modifier = Modifier.padding(start = 8.dp, top = 4.dp)
                                                )
                                            }

                                        }


                                    }

                                }
                            }
                        }



                        Box(
                            Modifier.fillMaxSize()
                            .weight(0.5f) ,
                            contentAlignment = Alignment.Center
                        ){
//                                    Button(onClick = {
//                                        if
//
//                                        showPopup = false }) {
//                                        Text("Close Popup")
//                                    }



                            Column (
                                modifier = Modifier
                                    .fillMaxSize(),
                                horizontalAlignment = Alignment.CenterHorizontally

                            ) {
                                // Show error message if any input is empty

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


                                Button(
                                    onClick = {
                                       buttonClick?.invoke()
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth(0.5f)
                                        .fillMaxHeight(0.7f)
                                        .padding(vertical = 16.dp)
                                        .weight(1f)
                                ) {
                                    Text(text = "Sart Game", fontFamily = barrioFontFamily, fontSize = 20.sp)
                                }





                            }

                        }
                    }
                }
            }
        }
    }
}



fun checkScoreSum(scoreText: MutableState<Array<Array<String>>>, currentPlay: Int, size: Int): Boolean {
    var sum=0
    Log.d("checkFinalSum", "size: $size")
    for (i in 0 until size){
        var value=scoreText.value[currentPlay][i+1]
        Log.d("checkFinalSum", "i: $i , value: $value")

        if (!checkInput(value.toString(),currentPlay)) {
            sum += value.toInt()
        }else{
            Log.d("checkFinalSum", "Quit in this else: $i")

            return false
        }

    }
    if(sum==currentPlay) return true
    return false
}

fun updateScore(predictions: grid,score:grid, currentPlay: Int, size: Int){
    for (i in 0 until size){
        var prediction=predictions.get(currentPlay, i+1)
        var wins=score.get(currentPlay,i+1)
        var oldscore=0
        if(currentPlay!=1){
            oldscore=score.get(currentPlay-1,i+1)
        }
        score.updateScore(currentPlay,i+1,oldscore,wins,prediction)
        Log.d("updateScore", "i ${i+1} ,prediction: $prediction , wins: $wins , oldscore: $oldscore")

    }
}