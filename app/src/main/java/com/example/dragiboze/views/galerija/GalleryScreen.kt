package com.example.dragiboze.views.galerija

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import coil3.compose.AsyncImage
import com.example.dragiboze.ui.theme.RoyalBlue
import com.example.dragiboze.views.galerija.GalleryContract.*

fun NavGraphBuilder.galerimjau(
    route: String,
    onSlikaClick: (String, String) -> Unit,
    onClose:() -> Unit,

) = composable (
    route = route
) {

    val slicId = it.arguments?.getString("id") ?: ""
    val macName = it.arguments?.getString("rasa") ?: ""

    if (slicId.isEmpty()){
        throw IllegalStateException("Za Cecu nema")
    }

    val galleryViewModel: GalleryViewModel = hiltViewModel<GalleryViewModel>()

    val state = galleryViewModel.state.collectAsState()

    GalleryScreen (
        state = state.value,
        onSlikaClick = onSlikaClick,
        onClose = onClose,
        macName
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GalleryScreen(
    state: UiState,
    onSlikaClick: (String, String) -> Unit,
    onClose: () -> Unit,
    rasa: String
){

    val scrollState = rememberScrollState()
    Scaffold  (
        topBar = {
            LargeTopAppBar(
                title = {
                    Text(text = "Slike za ${rasa}")
                },
                navigationIcon = {
                    IconButton(onClick = onClose) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Byebye",
                        )
                    }
                }
            )
        },
        content = { paddingValues ->

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                when {
                    state.error != null -> {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 16.dp),
                            contentAlignment = Alignment.Center,
                        ) {
                            val greska = when (state.error) {
                                is GalleryContract.errorOnData.dataFail ->
                                    "Nesto je puklooo. Error kaze: ${state.error.error?.message}"
                            }
                            Text(text = greska)
                        }
                    }

                    state.loading -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }

                    else ->{
                        LazyVerticalStaggeredGrid(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 4.dp),
                            columns = StaggeredGridCells.Fixed(3),
                            contentPadding = PaddingValues(),
                            horizontalArrangement = Arrangement.spacedBy(5.dp),
                        ){
                            if (!state.data.isEmpty()){
                                state.data.forEach { slika ->

                                    item{
                                        Box(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(150.dp)
                                                .width(150.dp)
                                                .padding(4.dp)
                                        ) {
                                            //Log.d("URL MACKE", slika.url ?: "")
                                            slika.url.let {
                                                AsyncImage(
                                                    modifier = Modifier
                                                        .clip(RoundedCornerShape(10.dp))
                                                        .border(
                                                            5.dp,
                                                            RoyalBlue,
                                                            RoundedCornerShape(10.dp)
                                                        )
                                                        .clickable { onSlikaClick(slika.id,
                                                            state.data.indexOf(slika).toString()
                                                        ) },
                                                    model = it,
                                                    contentDescription = slika.id,
                                                    contentScale = ContentScale.Fit,

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
    )

}