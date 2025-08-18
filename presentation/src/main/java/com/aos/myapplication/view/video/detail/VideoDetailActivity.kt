package com.aos.myapplication.view.video.detail
// ... existing imports ...
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.aos.domain.entity.VideoEntity
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Scaffold
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import com.aos.myapplication.view.video.VideoScreenRoute
import com.aos.myapplication.view.video.detail.ui.theme.MyApplicationTheme
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class VideoDetailActivity : ComponentActivity() {
    private val viewModel: VideoDetailViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val videos = intent.getParcelableArrayListExtra<VideoEntity>("videos") ?: arrayListOf()
        val initialIndex = intent.getIntExtra("initialIndex", 0)
        val route = intent.getStringExtra("route") ?: VideoScreenRoute.FAVORITE.name

        viewModel.setVideos(videos)

        setContent {
            MyApplicationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    if (videos.isNotEmpty()) {
                        VideoDetailPagerScreen(
                            videos = viewModel.videos,
                            initialPage = initialIndex,
                            pageSize = when(route) {
                                VideoScreenRoute.SEARCH.name -> minOf(videos.size, 10)
                                else -> videos.size
                            },
                            onFavoriteToggle = { page ->
                                viewModel.toggleFavorite(page)
                            },
                            modifier = Modifier.padding(innerPadding)
                        )
                    } else {
                        Text("비디오 정보가 없습니다.")
                    }
                }
            }
        }
    }

    override fun onStop() {
        super.onStop()
        Timber.e("onStop")
        viewModel.saveVideosState()
    }
}

@Composable
fun VideoDetailPagerScreen(
    videos: List<VideoEntity>,
    initialPage: Int,
    pageSize: Int,
    onFavoriteToggle: (Int) -> Unit,
    modifier: Modifier
) {
    val pagerState = rememberPagerState(initialPage = initialPage, pageCount = { pageSize })
    HorizontalPager(
        state = pagerState
    ) { page ->
        VideoDetailScreen(video = videos[page], onFavoriteToggle = {
            onFavoriteToggle(page)
        })
    }
}

@Composable
fun VideoDetailScreen(
    video: VideoEntity,
    onFavoriteToggle: () -> Unit,
    modifier: Modifier = Modifier
) {
    val favoriteIcon = if (video.isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder
    val favoriteText = if (video.isFavorite) "즐겨찾기됨" else "즐겨찾기"

    Column(
        modifier = modifier.padding(16.dp).fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = video.thumbnail,
            contentDescription = "${video.title}의 썸네일 이미지" ,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = video.title, style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(24.dp))
        IconButton(onClick = onFavoriteToggle) {
            Icon(
                imageVector = favoriteIcon,
                contentDescription = favoriteText
            )
        }
        Text(favoriteText)
    }
}

@Preview(showBackground = true)
@Composable
fun VideoDetailScreenPreview() {
    MyApplicationTheme {
        VideoDetailScreen(
            video = VideoEntity(
                id = "1",
                title = "Sample Video",
                thumbnail = "https://example.com/sample.jpg",
                isFavorite = false
            ),
            onFavoriteToggle = { }
        )
    }
}
