package com.aos.myapplication.view.video.detail
// ... existing imports ...
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
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
import com.aos.myapplication.view.video.detail.ui.theme.MyApplicationTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.runBlocking

@AndroidEntryPoint
class VideoDetailActivity : ComponentActivity() {
    private val viewModel: OpenVideoDetailViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val videos = intent.getParcelableArrayListExtra<VideoEntity>("videos") ?: arrayListOf()
        val initialIndex = intent.getIntExtra("initialIndex", 0)
        val route = intent.getStringExtra("route")
        viewModel.setVideos(videos)

        /* 전달받은 비디오 객체 */
        setContent {
            MyApplicationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    if (videos.isNotEmpty()) {
                        when (route) {
                            "search" -> {
                                VideoDetailPagerScreen(
                                    initialPage = initialIndex,
                                    pageSize = if(videos.size > 10) 10 else videos.size,
                                    viewModel,
                                    modifier = Modifier.padding(innerPadding)
                                )
                            }

                            "favorite" -> {
                                VideoDetailPagerScreen(
                                    initialPage = initialIndex,
                                    pageSize = videos.size,
                                    viewModel,
                                    modifier = Modifier.padding(innerPadding)
                                )
                            }
                        }
                    } else {
                        Text("비디오 정보가 없습니다.")
                    }
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()

        viewModel.saveVideosState()
    }
}

@Composable
fun VideoDetailPagerScreen(
    initialPage: Int,
    pageSize: Int,
    viewModel: OpenVideoDetailViewModel,
    modifier: Modifier
) {
    val videos = viewModel.videos // 항상 ViewModel에서 관측!
    val pagerState = rememberPagerState(initialPage = initialPage, pageCount = { pageSize })
    HorizontalPager(
        state = pagerState
    ) { page ->
        VideoDetailScreen(video = videos[page], onFavoriteToggle = {
            viewModel.toggleFavorite(page)
        })
    }
}


@Composable
fun VideoDetailScreen(
    video: VideoEntity,
    onFavoriteToggle: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = video.thumbnail,
            contentDescription = "Video Thumbnail",
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = video.title, style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(24.dp))
        IconButton(onClick = onFavoriteToggle) {
            Icon(
                imageVector = if (video.isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                contentDescription = "즐겨찾기"
            )
        }
        Text(if (video.isFavorite) "즐겨찾기됨" else "즐겨찾기")
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyApplicationTheme {
//        VideoDetailScreen(
//            VideoEntity(
//                id = "1",
//                title = "Sample Video",
//                thumbnail = "https://example.com/sample.jpg",
//            )
//        )
    }
}