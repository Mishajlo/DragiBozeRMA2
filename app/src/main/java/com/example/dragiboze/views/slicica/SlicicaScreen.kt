package com.example.dragiboze.views.slicica

import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
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
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
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
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import coil3.compose.AsyncImage
import com.example.dragiboze.views.galerija.GalleryContract
import com.example.dragiboze.views.slicica.SlicicaContract.*

fun NavGraphBuilder.slicica(
    route: String,
    onClose:() -> Unit
) = composable (
    route = route,
    enterTransition = { slideInVertically { it } },
    popExitTransition = { slideOutVertically() { it } }
) {

    val slicId = it.arguments?.getString("id") ?: ""
    val macIndex = it.arguments?.getString("index") ?: ""

    if (slicId.isEmpty()){
        throw IllegalStateException("Za Cecu nema")
    }

    val slicicaViewModel: SlicicaViewModel = hiltViewModel<SlicicaViewModel>()

    val state = slicicaViewModel.state.collectAsState()

    SlicicaScreen (
        state = state.value,
        onClose = onClose,
        macIndex = macIndex.toInt()
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SlicicaScreen (
    state: UiState,
    onClose: () -> Unit,
    macIndex: Int
){

    val trenutno = rememberPagerState(
        pageCount = { state.data.size }
    )

    Scaffold  (
        topBar = {
            LargeTopAppBar(
                title = {
                    Text(text = "")
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
                                is errorOnData.dataFail ->
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
                        if (state.data.isNotEmpty()) {
                            HorizontalPager(
                                modifier = Modifier.fillMaxSize(),
                                pageSize = PageSize.Fill,
                                pageSpacing = 10.dp,
                                state = trenutno,
                                key = { state.data[it].id }
                            ) { pageIndex ->
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize(),
                                    contentAlignment = Alignment.Center,
                                ) {
                                    state.data[pageIndex].url.let {
                                        AsyncImage(
                                            modifier = Modifier
                                                .fillMaxSize(),
                                            model = it,
                                            contentDescription = "",
                                            contentScale = ContentScale.Fit,
                                            )
                                    }
                                }
                            }
                        } else {
                            Text(
                                modifier = Modifier.fillMaxSize(),
                                text = "No images.",
                            )
                        }
                    }
                }
            }

        }
    )

}