package com.aos.data.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.aos.data.api.VideoService
import com.aos.data.datasource.VideoRemoteDataSource
import com.aos.data.datasource.VideoRemoteDataSourceImpl
import com.aos.data.entity.GetVideoEntity
import com.aos.data.mapper.toDomain
import com.aos.data.mapper.toUiVideoModel
import com.aos.data.state.VideoState
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
            // 1. remoteDataSource.getVideos(...) 호출하여 GetVideoEntity 획득
            val getVideoEntity: GetVideoEntity = when (val result =
                videoRemoteDataSourceImpl.getVideos(query, pageNumber, pageSize)) {
                is NetworkState.Success -> result.body
                is NetworkState.Failure -> return LoadResult.Error(Throwable(result.error))
                is NetworkState.NetworkError -> return LoadResult.Error(result.error)
                is NetworkState.UnknownError -> return LoadResult.Error(
                    result.t ?: Throwable("Unknown error")
                )
            }

            var typeForThisPage = currentPageLastType
            var indexForThisPage = currentPageLastIndex

            Timber.e("==============================")
            Timber.e("typeForThisPage $typeForThisPage")
            Timber.e("indexForThisPage $indexForThisPage")
            Timber.e("==============================")

            val addPreviousType = if (indexForThisPage != 0) {
                3 - indexForThisPage
            } else {
                0
            }

            Timber.e("addPreviousType $addPreviousType")
            val domainVideos = getVideoEntity.videos.mapIndexed { videoIndex, video ->
                val currentItemType: VideoType
                Timber.e("videoIndex $videoIndex")
                Timber.e("indexForThisPage $indexForThisPage")

                if (videoIndex >= addPreviousType) {
                    val adjustedIndex = if (indexForThisPage != 0) {
                        (videoIndex + indexForThisPage) % 3
                    } else {
                        videoIndex % 3
                    }
                    Timber.e("adjustedIndex $adjustedIndex")

                    if (adjustedIndex == 0) {
                        typeForThisPage =
                            if (typeForThisPage == VideoType.TYPE_A) VideoType.TYPE_B else VideoType.TYPE_A
                        indexForThisPage = 0
                    }

//                    currentItemType = typeForThisPage
                    indexForThisPage++

                }
                Timber.e("video.toDomain(typeForThisPage) ${video.toDomain(typeForThisPage).isType}")
                video.toDomain(typeForThisPage)
            }

            Timber.e("typeForThisPage $typeForThisPage")
            Timber.e("indexForThisPage $indexForThisPage")

            currentPageLastType = typeForThisPage
            currentPageLastIndex = indexForThisPage

            val nextKey = if (getVideoEntity.meta.isEnd) null else pageNumber + 1
            val prevKey = if (pageNumber == 1) null else pageNumber - 1

            return LoadResult.Page(
                data = domainVideos,
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