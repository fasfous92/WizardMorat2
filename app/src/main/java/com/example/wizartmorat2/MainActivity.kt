package com.example.wizartmorat2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.wizardmorat2.GameScreen
import com.example.wizardmorat2.HomeScreen
import com.example.wizardmorat2.ScoreScreen
import com.example.wizartmorat2.ui.theme.WizartMorat2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WizartMorat2Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    Greeting(
//                        name = "Android",
//                        modifier = Modifier.padding(innerPadding)
//                    )
                    Game()
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    WizartMorat2Theme {
//        Greeting("Android")
//    }
//}


@Composable
fun Game()
{
    val navController = rememberNavController()

    // Set up the NavHost for navigation between screens
    NavHost(navController = navController, startDestination = "HomeScreen") {
        composable("homeScreen") { HomeScreen(navController) }
        composable("gameScreen") { GameScreen(navController) }

        composable(
            "scoreScreen?gameJson={gameJson}",
            arguments = listOf(navArgument("gameJson") { type = NavType.StringType })
        ) { backStackEntry ->
            //val jsonString="""{"title":"game1","players":["youssef","hank","user1"]}"""
            val jsonString = backStackEntry.arguments?.getString("gameJson")
            // Log.d("nav", "gameJson: $jsonString")
            //Log.d("nav", "heree")
            ScoreScreen(navController,jsonString.toString())

        }

//            composable(
//                route = "ScoreScreen/{game}",
//                arguments = listOf(navArgument("game") { type = NavType.ParcelableType(Game::class.java) })
//            ) { backStackEntry ->
//                val game = backStackEntry.arguments?.getParcelable<Game>("game")
//                Log.d("nav", "game: $game")
//                //ScoreScreen(navController = navController, game = game)
//            }
    }
    //navController.navigate("GameScreen")
}

