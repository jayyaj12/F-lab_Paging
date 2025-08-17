package com.aos.data.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.aos.data.BuildConfig
import com.aos.data.api.VideoApi
import com.aos.data.mapper.toVideoModel
import com.aos.domain.entity.VideoEntity
import com.aos.domain.entity.VideoType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class VideoPagingSource(
    private val query: String,
    private val videoApi: VideoApi
) : PagingSource<Int, VideoEntity>() {

    var currentPageLastType: VideoType = VideoType.TYPE_B // 마지막 타입 저장 변수
    var currentPageLastIndex = 0 // 마지막 타입이 몇번 추가되었는지 판단하는 변수

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, VideoEntity> {
        val pageNumber = params.key ?: 1
        val pageSize = params.loadSize

        return runCatching {
            val videoResponse = withContext(Dispatchers.IO) {
                videoApi.getVideo("KakaoAK ${BuildConfig.KAKAO_API_KEY}", query, pageNumber, pageSize)
            }

            val mappingResult = videoResponse.toVideoModel(
                initialType = currentPageLastType,
                initialIndex = currentPageLastIndex
            )

            currentPageLastType = mappingResult.nextStartingType
            currentPageLastIndex = mappingResult.nextStartingIndex

            val nextKey = if (videoResponse.meta.isEnd) null else pageNumber + 1
            val prevKey = if (pageNumber == 1) null else pageNumber - 1

            LoadResult.Page(
                data = mappingResult.videoEntities,
                prevKey = prevKey,
                nextKey = nextKey
            )
        }.getOrElse {
            LoadResult.Error(it)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, VideoEntity>): Int? {
        return state.anchorPosition?.let { anchor ->
            state.closestPageToPosition(anchor)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchor)?.nextKey?.minus(1)
        }
    }


}