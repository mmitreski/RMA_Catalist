package rs.raf.rma.klk.catalist.breeds.details

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import coil.compose.SubcomposeAsyncImage
import rs.raf.rma.klk.catalist.core.compose.MyAppIconButton
import rs.raf.rma.klk.catalist.core.theme.myColor
import rs.raf.rma.klk.catalist.domain.BreedData

@OptIn(ExperimentalMaterial3Api::class)
fun NavGraphBuilder.breedsDetailsScreen(
    route: String,
    navController: NavController,
) = composable(route = route) {navBackStackEntry->
    val breedId = navBackStackEntry.arguments?.getString("id")
        ?: throw IllegalArgumentException("id is required.")

    val breedsDetailsViewModel = viewModel<BreedsDetailsViewModel>(
        factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                // We pass the passwordId which we read from arguments above
                return BreedsDetailsViewModel(breedId = breedId) as T
            }
        },
    )
    val state by breedsDetailsViewModel.state.collectAsState()

    BreedsDetailsScreen(
        state = state,
        onClose = {
            navController.navigateUp()
        }
    )
}

@ExperimentalMaterial3Api
@Composable
private fun BreedsDetailsScreen(
    state: BreedsDetailsState,
    onClose: () -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarColors(Color.White, Color.White, Color.White, Color.White, Color.White),
                title = {
                    Text(
                        text = "Catalist",
                        style = TextStyle(
                            color = myColor,
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp
                        )
                    )
                },
                navigationIcon = {
                    MyAppIconButton(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        onClick = onClose,
                        tint = myColor
                    )
                }
            )
        },
        content = {
            LazyColumn(
                modifier = Modifier.padding(it)
            ) {
                if(state.imageUrl != null) {
                    item {
                        Image(
                            url = state.imageUrl
                        )
                    }
                }
                if(state.breed != null) {
                    item {
                        BreedInfo(
                            breed = state.breed
                        )
                    }
                }
            }
        }
    )
}

@Composable
fun BreedInfo(breed: BreedData) {
    Spacer(modifier = Modifier.height(20.dp))
    Text(
        text = "${breed.name} ${if (breed.rare == 1) "(rare)" else ""}",
        style = TextStyle(
            fontSize = 48.sp,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Italic
        ),
        modifier = Modifier.padding(horizontal=16.dp)
    )
    BreedField(
        fieldName = "Other names",
        fieldText = if(breed.altNames != null && breed.altNames != "") breed.altNames else "None",
    )
    BreedField(
        fieldName = "Description",
        fieldText = breed.description,
        textAlign = TextAlign.Justify
    )
    BreedField(
        fieldName = "Countries of origin",
        fieldText = breed.origin,
    )
    BreedField(
        fieldName = "Temperament",
        fieldText = breed.temperament,
    )
    BreedField(
        fieldName = "Life span",
        fieldText = "${breed.lifeSpan} Years",
    )
    if(breed.weight != null) {
        BreedField(
            fieldName = "Weight",
            fieldText = "${breed.weight.metric} kg\n${breed.weight.imperial} lbs"
        )
    }
    BreedField(
        fieldName = "Behaviour",
        fieldText = """
            Adaptability: ${breed.adaptability}
            Intelligence: ${breed.intelligence}
            Vocalisation: ${breed.vocalisation}
            Energy level: ${breed.energy}
            Grooming: ${breed.grooming}
        """.trimIndent())
    if(breed.wikipediaLink != null) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val uriHandler = LocalUriHandler.current
            Button(
                onClick = {
                    uriHandler.openUri(breed.wikipediaLink)
                },
                shape = RoundedCornerShape(20),
                colors = ButtonColors(
                    myColor.copy(alpha=0.75F),
                    Color.Black,
                    myColor.copy(alpha=0.75F),
                    Color.Black),
            ) {
                Text(
                    text = "Wikipedia Link",
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Blue,
                        textDecoration = TextDecoration.Underline
                    ),
                )
            }
        }
    }
}

@Composable
private fun BreedField(
    fieldName: String,
    nameFontSize: TextUnit = 26.sp,
    nameFontStyle: FontStyle = FontStyle.Normal,
    nameFontWeight: FontWeight = FontWeight.SemiBold,
    fieldText: String,
    textFontSize: TextUnit = 20.sp,
    textFontStyle: FontStyle = FontStyle.Italic,
    textFontWeight: FontWeight = FontWeight.Normal,
    textAlign: TextAlign = TextAlign.Left
) {
    Spacer(modifier = Modifier.height(5.dp))
    Box {
        Surface (
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 5.dp),
            color = myColor.copy(alpha= 0.3F),
            shape = RoundedCornerShape(10.dp)
        ) {
            Column (
                Modifier.padding(10.dp)
            ){
                Text(
                    text = fieldName,
                    style = TextStyle(
                        fontSize = nameFontSize,
                        fontStyle = nameFontStyle,
                        fontWeight = nameFontWeight
                    ),
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                )
                Text(
                    text = fieldText,
                    style = TextStyle(
                        fontSize = textFontSize,
                        fontStyle = textFontStyle,
                        fontWeight = textFontWeight,
                        textAlign = textAlign,
                    ),
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                )
            }
        }
    }
}

@Composable
fun Image(url: String) {
    SubcomposeAsyncImage(
        modifier = Modifier.fillMaxWidth(),
        model = url,
        contentDescription = null,
        loading = {
            Box(
                modifier = Modifier.size(50.dp),
                contentAlignment = Alignment.Center,
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(36.dp),
                )
            }
        }
    )
}

