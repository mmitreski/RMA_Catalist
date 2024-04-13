package rs.raf.rma.klk.catalist.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import rs.raf.rma.klk.catalist.breeds.details.breedsDetailsScreen
import rs.raf.rma.klk.catalist.breeds.list.breedsListScreen

@ExperimentalMaterial3Api
@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "breeds"
    ) {
        breedsListScreen(
            route = "breeds",
            navController = navController,
        )

        breedsDetailsScreen(
            route = "breeds/{id}",
            navController = navController,
        )
    }
}