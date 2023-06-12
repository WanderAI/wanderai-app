package com.thariqzs.wanderai.ui

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
    const val PlanDetail = "plan_detail"
    const val Profile = "profile"
    const val ResetPassword = "reset_password"
//    const val GameDetails = "game_details/{gameId}"
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

@ExperimentalFoundationApi
@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.Auth,
//        modifier = Modifier.background(MaterialTheme.colors.background),
    ) {
        composable(Routes.Auth) {
            val authViewModel = hiltViewModel<AuthViewModel>()
            val tokenViewModel = hiltViewModel<TokenViewModel>()
            AuthScreen(navController, authViewModel, tokenViewModel)
        }

        composable(Routes.Home) {
            val tokenViewModel = hiltViewModel<TokenViewModel>()
            val homeViewModel = hiltViewModel<HomeViewModel>()
            HomeScreen(navController = navController, tokenViewModel, homeViewModel)
        }

        composable(Routes.TravelPlan) {
            val travelPlanningViewModel = hiltViewModel<TravelPlanningViewModel>()
            TravelPlanningScreen(navController = navController, travelPlanningViewModel)
        }

        composable(Routes.ListPlan) {
            ListPlanScreen(navController = navController)
        }

        composable(Routes.PlanDetail) {
            PlanDetailScreen(navController = navController)
        }

        composable(Routes.Profile) {
            val tokenViewModel = hiltViewModel<TokenViewModel>()
            ProfileScreen(navController = navController, tokenViewModel)
        }
        
        composable(Routes.ResetPassword) {
            val authViewModel = hiltViewModel<AuthViewModel>()
            ResetPasswordScreen(navController = navController, authViewModel)
        }
    }
}