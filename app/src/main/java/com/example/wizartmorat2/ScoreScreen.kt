package com.example.wizardmorat2

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.wizardmorat.ui.theme.barrioFontFamily
import com.example.wizartmorat2.Game
import com.example.wizartmorat2.grid


@Preview
@Composable
fun ScoreScreenPreview() {
    ScoreScreen(navController = rememberNavController(),data = """{"title":"game1","players":["youssef","hank","user1","user2","user3","user4"]}""")
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScoreScreen(navController: NavController,data: String
) {
    var game = Game(data)
    var table = remember { mutableStateListOf<String>() }
    val players = game.score.keys.toMutableList()

    val rows = game.numberPlays + 1
    val columns = players.size + 1
    var scoreGrid = grid(rows, columns)
    var predictionGrid = grid(rows, columns)
    var currentPlay = remember { mutableStateOf(1) }
    val ScoreText = remember {
        mutableStateOf(
            Array(rows) { row ->
                Array(columns) { col -> "" }
            }
        )
    }
    val predictionText = remember {
        mutableStateOf(
            Array(rows) { row ->
                Array(columns) { col -> "" }
            }
        )
    }
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
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .weight(0.25f),
                contentAlignment = Alignment.Center // Align the text to the center
            ) {

                Text(
                    text = game.title,
                    fontSize = 24.sp, // Set the font size to 24sp,
                    fontFamily = barrioFontFamily,
                    fontWeight = FontWeight.Bold, // Set the font weight to bold
                    color = Color.Black
                )
            }

            Box(
                modifier = Modifier
                    .weight(3f)
                    .fillMaxSize()

            ) {


                ScoringGrid(
                    game,
                    players,
                    scoreGrid,
                    predictionGrid,
                    currentPlay,
                    ScoreText,
                    predictionText
                )
            }



            Box(
                modifier = Modifier
                    .weight(0.25f)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                var boolStart = remember { mutableStateOf(false) }
                var boolpred = remember { mutableStateOf(true) }
                var buttonText = remember { mutableStateOf("Start Round") }
                var boolscore= remember { mutableStateOf(false) }
                Row(
                    modifier = Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    if (boolscore.value) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(0.5f)
                                .fillMaxHeight(0.7f)
                                .weight(1f),
                            contentAlignment = Alignment.Center
                        ) {
                            PopupScore(
                                boolscore,
                                currentPlay,
                                scoreGrid,
                                predictionGrid,
                                players,
                                ScoreText
                            )
                        }
                    }



                    if (boolpred.value) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(0.5f)
                                .fillMaxHeight(0.7f)
                                .weight(1f),
                            contentAlignment = Alignment.Center
                        ) {
                            PopupPredictions(
                                boolStart,
                                currentPlay,
                                predictionGrid,
                                players,
                                predictionText
                            )
                        }
                    }
                    if (boolStart.value) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(0.5f)
                                .fillMaxHeight(0.7f)
                                .weight(1f),
                            contentAlignment = Alignment.Center
                        ) {
                            Button(
                                onClick = {
                                    if (boolpred.value) { //start Round
                                        boolpred.value = false
                                        buttonText.value = "End Round"
                                    } else { //end Round
                                        boolpred.value = true
                                        boolscore.value=true
                                        boolStart.value = false
                                        buttonText.value = "Start Round"

                                        //currentPlay.value++
                                        //make popup showup for inputing scores
                                    }

                                },
                                modifier = Modifier.fillMaxSize(0.8f),
                                colors = if (boolpred.value) ButtonDefaults.buttonColors(
                                    Color(
                                        0xFF50C878
                                    )
                                ) else ButtonDefaults.buttonColors(
                                    Color(0xFFFF5733)
                                )


                            ) {
                                Text(text = buttonText.value)
                            }
                        }

                    }

                }
            }


        }
    }


}


fun getString(value :Int,currentPlay :Int, lineIndex:Int): String {
    if(lineIndex>currentPlay) return ""
    return value.toString()
}


