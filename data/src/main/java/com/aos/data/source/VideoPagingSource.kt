package com.aos.data.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.aos.data.api.VideoService
import com.aos.domain.model.Video
import com.aos.domain.usecase.SearchVideoUseCase
import timber.log.Timber
import javax.inject.Inject

class VideoPagingSource(
    private val query: String,
    private val searchVideoUseCase: SearchVideoUseCase
) :
    PagingSource<Int, Video>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Video> {
        val page = params.key ?: 1
        return searchVideoUseCase(query, page, params.loadSize).fold(
            onSuccess = { videoModel ->
                val videos = videoModel.videos.toMutableList()
                if (videoModel.isEnd && videos.isNotEmpty()) {
                    videos.last().isLast = true
                }

                LoadResult.Page(
                    data = videos,
                    prevKey = if (page == 1) null else page - 1,
                    nextKey = if (videoModel.isEnd) null else page + 1
                )
            },onFailure = {
                LoadResult.Error(it)
            }
        )
    }

    override fun getRefreshKey(state: PagingState<Int, Video>): Int? {
        return state.anchorPosition?.let { anchor ->
            state.closestPageToPosition(anchor)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchor)?.nextKey?.minus(1)
        }
    }


}