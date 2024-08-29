package com.example.pafpaf


import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.compose.rememberNavController
import com.example.pafpaf.navigation.AppNavHost
import com.example.pafpaf.ui.theme.PafPafTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PafPafTheme {
                val navController = rememberNavController()
                AppNavHost(navController = navController)
            }
        }
    }
}



