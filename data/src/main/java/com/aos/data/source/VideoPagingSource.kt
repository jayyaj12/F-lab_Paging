package com.aos.data.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.aos.data.api.VideoService
import com.aos.data.datasource.VideoRemoteDataSource
import com.aos.data.datasource.VideoRemoteDataSourceImpl
import com.aos.data.entity.GetVideoEntity
import com.aos.data.mapper.toDomain
import com.aos.data.mapper.toVideoModel
import com.aos.data.util.RetrofitFailureStateException
import com.aos.domain.model.Video
import com.aos.domain.model.VideoType
import com.aos.domain.usecase.SearchVideoUseCase
import com.aos.domain.util.NetworkState
import timber.log.Timber
import javax.inject.Inject

class VideoPagingSource(
    private val query: String,
    private val videoRemoteDataSourceImpl: VideoRemoteDataSource
) : PagingSource<Int, Video>() {

    var currentPageLastType: VideoType = VideoType.TYPE_B // 마지막 타입 저장 변수
    var currentPageLastIndex = 0 // 마지막 타입이 몇번 추가되었는지 판단하는 변수

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Video> {
        val pageNumber = params.key ?: 1
        val pageSize = params.loadSize

        try {
            val getVideoEntity: GetVideoEntity = when (val result =
                videoRemoteDataSourceImpl.getVideos(query, pageNumber, pageSize)) {
                is NetworkState.Success -> result.body
                is NetworkState.Failure -> return LoadResult.Error(Throwable(result.error))
                is NetworkState.NetworkError -> return LoadResult.Error(result.error)
                is NetworkState.UnknownError -> return LoadResult.Error(
                    result.t ?: Throwable("Unknown error")
                )
            }

            val mappingResult = getVideoEntity.toVideoModel(
                initialType = currentPageLastType,
                initialIndex = currentPageLastIndex
            )

            currentPageLastType = mappingResult.nextStartingType
            currentPageLastIndex = mappingResult.nextStartingIndex

            val nextKey = if (getVideoEntity.meta.isEnd) null else pageNumber + 1
            val prevKey = if (pageNumber == 1) null else pageNumber - 1

            return LoadResult.Page(
                data = mappingResult.videos,
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Video>): Int? {
        return state.anchorPosition?.let { anchor ->
            state.closestPageToPosition(anchor)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchor)?.nextKey?.minus(1)
        }
    }


}