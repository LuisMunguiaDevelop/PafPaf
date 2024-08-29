package com.example.pafpaf.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.pafpaf.features.login.LoginScreen
import com.example.pafpaf.features.login.LoginScreenNav
import com.example.pafpaf.features.album.AlbumScreen
import com.example.pafpaf.features.album.AlbumScreenNav
import com.example.pafpaf.features.album_list.AlbumListScreen
import com.example.pafpaf.features.album_list.AlbumListScreenNav
import kotlin.system.exitProcess


@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = LoginScreenNav
    ) {

        composable<LoginScreenNav> {
            LoginScreen(
                onLoginSucceed = { navController.navigate(AlbumListScreenNav)}
            )
        }

        composable<AlbumListScreenNav>
        {
            AlbumListScreen(
                onAlbumClicked = { album ->
                    navController.navigate(
                        AlbumScreenNav(
                            id = album.id,
                            title = album.title
                        )
                    )
                },
                onBackclicked = {
                    exitProcess(0)
                }
            )
        }

        composable<AlbumScreenNav> { backStackEntry ->
            val nav: AlbumScreenNav = backStackEntry.toRoute()
            AlbumScreen(
                album = nav.toAlbum(),
                onBackPressed = { navController.popBackStack() }
            )
        }
    }
}


