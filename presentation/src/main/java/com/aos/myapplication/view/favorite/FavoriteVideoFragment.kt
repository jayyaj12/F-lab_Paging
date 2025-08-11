package com.aos.myapplication.view.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.aos.myapplication.databinding.FragmentFavoriteVideoBinding
import com.aos.myapplication.view.search.VideoSearchPagingAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class FavoriteVideoFragment : Fragment() {

    private lateinit var videoFavoritePagingAdapter: VideoFavoritePagingAdapter
    private val viewModel by viewModels<FavoriteVideoViewModel>()

    private var _binding: FragmentFavoriteVideoBinding? = null
    val binding get() =  _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoriteVideoBinding.inflate(LayoutInflater.from(context), container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupVideoList()
        setupViewModelObserver()
    }

    private fun setupVideoList() {
        videoFavoritePagingAdapter = VideoFavoritePagingAdapter("즐겨찾기 삭제") { item ->
            // 즐겨찾기 버튼 삭제 버튼 클릭
            viewModel.deleteFavoriteVideo(item)
        }

        with(binding.rvFavoriteList) {
            if (itemDecorationCount == 0) {
                addItemDecoration(
                    DividerItemDecoration(context, RecyclerView.VERTICAL)
                )
            }
            adapter = videoFavoritePagingAdapter
        }
    }

    private fun setupViewModelObserver() {
        lifecycleScope.launch {
            viewModel.pagedVideos.collectLatest {
                Timber.e("pagedVideos $it")
                videoFavoritePagingAdapter.submitData(it)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

}