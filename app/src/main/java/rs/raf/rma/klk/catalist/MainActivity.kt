 package rs.raf.rma.klk.catalist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import rs.raf.rma.klk.catalist.core.theme.CatalistTheme
import rs.raf.rma.klk.catalist.navigation.AppNavigation

 class MainActivity : ComponentActivity() {
     @OptIn(ExperimentalMaterial3Api::class)
     override fun onCreate(savedInstanceState: Bundle?) {
         super.onCreate(savedInstanceState)
         setContent {
             CatalistTheme {
                 AppNavigation()
             }
         }
     }

     @OptIn(ExperimentalMaterial3Api::class)
     @Preview(showBackground = true)
     @Composable
     fun GreetingPreview() {
         CatalistTheme {
             AppNavigation()
         }
     }
 }