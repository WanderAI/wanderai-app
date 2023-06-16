package com.thariqzs.wanderai.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.thariqzs.wanderai.ui.screens.auth.AuthScreen
import com.thariqzs.wanderai.ui.screens.auth.AuthViewModel
import com.thariqzs.wanderai.ui.screens.home.HomeScreen
import com.thariqzs.wanderai.ui.screens.home.HomeViewModel
import com.thariqzs.wanderai.ui.screens.listplan.ListPlanScreen
import com.thariqzs.wanderai.ui.screens.placedetail.PlaceDetailScreen
import com.thariqzs.wanderai.ui.screens.plandetail.PlanDetailScreen
import com.thariqzs.wanderai.ui.screens.profile.ProfileScreen
import com.thariqzs.wanderai.ui.screens.resetpassword.ResetPasswordScreen
import com.thariqzs.wanderai.ui.screens.travelplanning.TravelPlanningScreen
import com.thariqzs.wanderai.ui.screens.travelplanning.TravelPlanningViewModel
import com.thariqzs.wanderai.utils.TokenViewModel

object Routes {
    const val Auth = "auth"
    const val Home = "home"
    const val TravelPlan = "travel_plan"
    const val ListPlan = "list_plan"
    const val PlanDetail = "plan_detail/{doc_id}"
    const val Profile = "profile"
    const val ResetPassword = "reset_password"
    const val PlaceDetail = "place_detail"
//    const val NewGames = "new_games/{minReleaseTimestamp}/{subtitle}"
//    const val UpcomingReleases = "upcoming_releases"
//    const val PopularGames = "popular_games/{minReleaseTimestamp}/{subtitle}"
//    const val Search = "search"
//
//    fun gameDetails(gameId: Long) = "game_details/$gameId"
//    fun newGames(minReleaseTimestamp: Long, subtitle: String) =
//        "new_games/$minReleaseTimestamp/$subtitle"
//
//    fun popularGames(minReleaseTimestamp: Long, subtitle: String) =
//        "popular_games/$minReleaseTimestamp/$subtitle"
}

@RequiresApi(Build.VERSION_CODES.P)
@ExperimentalFoundationApi
@Composable
fun Navigation() {
    val navController = rememberNavController()
    val homeViewModel = hiltViewModel<HomeViewModel>()
    val travelPlanningViewModel = hiltViewModel<TravelPlanningViewModel>()
    val authViewModel = hiltViewModel<AuthViewModel>()
    val tokenViewModel = hiltViewModel<TokenViewModel>()

    NavHost(
        navController = navController,
        startDestination = Routes.Auth,
//        modifier = Modifier.background(MaterialTheme.colors.background),
    ) {
        composable(Routes.Auth) {
            AuthScreen(navController, authViewModel, tokenViewModel)
        }

        composable(Routes.Home) {
            HomeScreen(navController = navController, tokenViewModel, homeViewModel)
        }

        composable(Routes.TravelPlan) {
            TravelPlanningScreen(navController = navController, travelPlanningViewModel)
        }

        composable(Routes.ListPlan) {
            ListPlanScreen(navController = navController, homeViewModel)
        }

        composable(Routes.PlanDetail) { backStackEntry ->
            val docId = backStackEntry.arguments?.getString("doc_id")
            if (docId != null) {
                PlanDetailScreen(navController = navController, docId = docId, homeViewModel)
            }
        }

        composable(Routes.Profile) {
            ProfileScreen(navController = navController, tokenViewModel)
        }
        
        composable(Routes.ResetPassword) {
            val authViewModel = hiltViewModel<AuthViewModel>()
            ResetPasswordScreen(navController = navController, authViewModel)
        }

        composable(Routes.PlaceDetail) {
            PlaceDetailScreen(navController = navController, homeViewModel)
        }
    }
}