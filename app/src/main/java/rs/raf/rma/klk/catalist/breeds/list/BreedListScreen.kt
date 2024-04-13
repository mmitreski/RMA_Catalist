package rs.raf.rma.klk.catalist.breeds.list

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import rs.raf.rma.klk.catalist.R
import rs.raf.rma.klk.catalist.core.theme.myColor
import rs.raf.rma.klk.catalist.domain.BreedData

@ExperimentalMaterial3Api
fun NavGraphBuilder.breedsListScreen(
    route: String,
    navController: NavController,
) = composable(route = route) {
    val breedsListViewModel = viewModel<BreedsListViewModel>()
    val state by breedsListViewModel.state.collectAsState()

    BreedListScreen(
        state = state,
        onItemClick = {
            navController.navigate(route = "breeds/${it.breedId}")
        },
        onQueryChange = breedsListViewModel::onSearchTextChange,
        onClearFocus = breedsListViewModel::onClearFocus,
    )
}

@ExperimentalMaterial3Api
@Composable
fun BreedListScreen(
    state: BreedsListState,
    onItemClick: (BreedData) -> Unit,
    onQueryChange: (String) -> Unit,
    onClearFocus: () -> Unit
) {
    val manager = LocalFocusManager.current
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarColors(Color.White, Color.White, Color.White, Color.White, Color.White),
                title = {
                    Row {
                        Image(
                            painter = painterResource(
                                id = R.drawable.catalistsmall
                            ),
                            contentDescription = "Catalist"
                        )
                        Spacer(modifier = Modifier.width(20.dp))
                        Text(
                            text = "Catalist",
                            style = TextStyle(
                                color = myColor,
                                fontWeight = FontWeight.Bold,
                                fontSize = 24.sp
                            )
                        )
                    }
                },
                actions = {
                    Button(
                        colors = ButtonColors(Color.White, myColor, Color.White, myColor),
                        onClick = {
                            manager.clearFocus()
                            onClearFocus()
                        }
                    ) {
                        Text(
                            text = "Clear Search",
                            style = TextStyle(
                                color = myColor,
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp
                            )
                        )
                    }
                }
            )
        },
        content = {
            Column (Modifier.padding(it)) {
                Surface (color = Color.White) {
                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .focusRequester(state.focusRequester),
                        value = state.searchText,
                        onValueChange = onQueryChange,
                        placeholder = { Text(text = "Search") },
                        label = { Text(text = "Search") },
                    )
                }
                Surface(
                    color = myColor.copy(alpha = 0.3F),
                ) {
                    if (state.breeds.isNotEmpty()) {
                        BreedsList(
                            state = state,
                            onItemClick = onItemClick
                        )
                    } else {
                        when (state.fetching) {
                            true -> {
                                Box(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.Center,
                                ) {
                                    CircularProgressIndicator()
                                }
                            }

                            false -> {
                                if (state.error != null) {
                                    Box(
                                        modifier = Modifier.fillMaxSize(),
                                        contentAlignment = Alignment.Center,
                                    ) {
                                        val errorMessage = when (state.error) {
                                            is BreedsListState.ListError.BreedListFetchingFailed -> "Failed to fetch breed list. Error message: ${state.error.cause?.message}"
                                        }
                                        Surface(
                                            modifier = Modifier
                                                .padding(20.dp),
                                            shape = RoundedCornerShape(20.dp)
                                        ) {
                                            Text(
                                                modifier = Modifier.padding(10.dp),
                                                text = errorMessage
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
    )
}

@Composable
private fun BreedsList(
    state: BreedsListState,
    onItemClick: (BreedData) -> Unit
) {
    LazyColumn (
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical=10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ) {
        items(state.breeds.filter { data -> data.name.startsWith(state.searchText, true) }) {
                item -> key(item.breedId) {
            Spacer(modifier = Modifier.height(10.dp))
            BreedListItem (
                data = item,
                delimiter = state.delimiter,
                onClick = { onItemClick(item) },
            )
        }
        }
    }
}

@Composable
private fun BreedListItem(
    data: BreedData,
    delimiter: String,
    onClick: () -> Unit,
) {
    Card(
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .fillMaxWidth()
            .clickable {
                onClick()
            },
        colors = CardColors(Color.White, Color.Black, Color.White, Color.Black)
    ) {
        Spacer(Modifier.height(15.dp))
        Row {
            Spacer(Modifier.width(10.dp))
            Text(
                modifier = Modifier.padding(horizontal = 6.dp),
                text = data.name,
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 30.sp
                )
            )
        }
        if(data.altNames != null && data.altNames != "") {
            Spacer(Modifier.height(10.dp))
            Row {
                Spacer(Modifier.width(10.dp))
                Text(
                    text = data.altNames,
                    modifier = Modifier.padding(horizontal = 6.dp),
                    style = TextStyle(
                        fontStyle = FontStyle.Italic,
                        fontSize = 20.sp
                    )
                )
            }
        }
        Spacer(Modifier.height(10.dp))
        Row {
            Spacer(Modifier.width(16.dp))
            Text(
                modifier = Modifier.padding(end=16.dp),
                text = if(data.description.length > 250) "${data.description.dropLast(data.description.length-250)}..." else data.description ,
                style = TextStyle(
                    fontSize = 18.sp,
                    textAlign = TextAlign.Justify
                )
            )
        }
        Spacer(Modifier.height(10.dp))
        Row {
            Spacer(Modifier.width(10.dp))
            for (i: Int in data.temperament.split(delimiter).indices) {
                if(i > 2) break
                SuggestionChip(
                    modifier = Modifier.padding(horizontal = 5.dp),
                    onClick = onClick,
                    label = {
                        Text(
                            text = data.temperament.split(delimiter)[i],
                            style = TextStyle(
                                fontSize = 13.sp
                            )
                        )
                    }
                )
            }
        }
    }
}