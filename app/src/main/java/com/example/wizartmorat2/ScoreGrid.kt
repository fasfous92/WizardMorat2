package com.example.wizardmorat2

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAbsoluteAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wizardmorat.ui.theme.barrioFontFamily
import com.example.wizartmorat2.Game
import com.example.wizartmorat2.grid

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScoringGrid(game : Game, players: List<String>, scoreGrid: grid,
                predictionGrid: grid, currentPlay: MutableState<Int>, scoreText: MutableState<Array<Array<String>>>,
                predictionText: MutableState<Array<Array<String>>>
) {
    val rows = game.numberPlays + 1
    val columns = players.size + 1
    val scoreFontSize={
        if(players.size<4){
            20
        }else 16
    }



    Column(
        modifier = Modifier
            .padding((24/columns).dp)

    )
    {

        //fix the first row on it's own

        Box(
            Modifier
                .border(BorderStroke(1.dp, Color.Black))
                .fillMaxWidth()
                .weight(0.1f)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier

                   // .horizontalScroll(rememberScrollState())
                    .fillMaxSize()
                    //.border(BorderStroke(2.dp, Color.Red))
            ) {
                Box(
                    modifier = Modifier
                        .border(BorderStroke(3.dp, Color.Black))
                       // .fillMaxSize()
                        .fillMaxHeight()
                        .weight(0.5f),
                    contentAlignment = Alignment.Center

                ) {
                    Text(text="#",
                        color = Color.Black,
                        fontFamily = barrioFontFamily,
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center
                    )
                }
                players.forEach {
                    Box(
                        modifier = Modifier
//                            .fillMaxSize() // Box should fill the width of the column
//                            .defaultMinSize(minHeight = 100.dp)
//                            .padding(0.dp)
                            .fillMaxSize()
                            //.width(200.dp)
                            .border(BorderStroke(1.dp, Color.Black))
                            .weight(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = it,
                            fontFamily = barrioFontFamily,
                            fontSize = if(columns>5) 16.sp else 20.sp,
                            color = Color.Black,


                            textAlign = TextAlign.Center

                        )
                    }
                }
            }
        }


        //the rest of the rows


        Column (
            Modifier
                .fillMaxSize()
                .weight(2f)
                .verticalScroll(
                    state = rememberScrollState(), // Corrected: Use ScrollState instead of SwipeToDismissBoxState
                    enabled = true // Optional, as true is the default
                )
        )
        {

            for (i in 1 until rows){
                Box(
                    modifier = Modifier
                        .fillMaxWidth() // Box should fill the width of the column
                        .defaultMinSize(minHeight = 50.dp)
                        .padding(0.dp),
                       // .border(BorderStroke(3.dp, Color.Blue)), // Add a border
                    contentAlignment = Alignment.Center
                )  {

                    Row(modifier = Modifier
                        .padding(0.dp)
                        .border(BorderStroke(1.dp, Color.Black)),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center)

                    {
                        // the first column (of number of plays)

                        Box(
                            Modifier
                                //.border(BorderStroke(3.dp, Color.Blue))
                            .fillMaxSize()
                                .fillMaxHeight()
                                .weight(0.5f),
                            contentAlignment = Alignment.Center
                        ){
                            Text(text="$i",
                                fontFamily = barrioFontFamily,
                                fontSize = 20.sp,
                                color = Color.Black,
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding((24 / columns).dp)
                            )

                        }

                        // the rest of columns (Index j is )
                        for (j in 1 until columns) {
                            Box(
                                Modifier
                                    .fillMaxSize()
                                    //.border(BorderStroke(1.dp, Color.Blue))
                                    .weight(1f)
                            ){
                                Row(modifier = Modifier
                                    //.padding(0.dp)
                                    .border(BorderStroke(1.dp, Color.Black)),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center){

                                    //box for Score
                                    Box(
                                        Modifier
                                            .fillMaxHeight()
                                            .border(BorderStroke(1.dp, Color.Black))
                                            .weight(1f)
                                            .padding(vertical = 20.dp),


                                        contentAlignment = Alignment.Center
                                    ){
                                        //scoreText.value[i][j]=scoreGrid.get(i,j).toString()

                                        Text(text=if(i<currentPlay.value) scoreText.value[i][j] else "",

                                            fontFamily = FontFamily.SansSerif,
                                            color = Color.Black,
                                            fontSize = (scoreFontSize()-2).sp,
//                                            if (scoreGrid.get(i, j) > 100 || scoreGrid.get(i, j) < 0) {
//                                                (scoreFontSize() - 2).sp
//                                            } else {
//                                                scoreFontSize().sp
//                                            },



                                            textAlign = TextAlign.Center,
                                            modifier = Modifier
                                                .fillMaxSize()

                                        )

                                    }
                                    //box for prediction
                                    Box(
                                        Modifier

                                            .weight(0.3f)
                                            //.border(BorderStroke(1.dp, Color.Blue))
                                            .padding(vertical = 10.dp),
                                        contentAlignment = Alignment.Center
                                    ) {

                                        //predictionText.value[i][j]=predictionGrid.get(i,j).toString()

                                        Text(text=if(i<=currentPlay.value) predictionText.value[i][j] else "",
                                            fontFamily = FontFamily.SansSerif,
                                            color = Color.Black,
                                            fontSize = 16.sp,
                                            textAlign = TextAlign.Center,
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .padding(2.dp)
                                        )
                                    }

                                }

                            }
                        }




                    }

                }

            }
        }






    }
}

