package com.aos.myapplication.view.video.favorite

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.aos.domain.entity.VideoEntity
import com.aos.myapplication.databinding.FragmentFavoriteVideoBinding
import com.aos.myapplication.view.video.detail.VideoDetailActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

@AndroidEntryPoint
class FavoriteVideoFragment : Fragment() {

    private lateinit var videoFavoritePagingAdapter: VideoFavoritePagingAdapter
    private val viewModel by viewModels<FavoriteVideoViewModel>()

    private var _binding: FragmentFavoriteVideoBinding? = null
    val binding get() = _binding ?: throw IllegalStateException("Binding is not initialized")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding =
            FragmentFavoriteVideoBinding.inflate(LayoutInflater.from(context), container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupVideoList()
        setupViewModelObserver()
    }

    private fun setupVideoList() {
        videoFavoritePagingAdapter = VideoFavoritePagingAdapter(clickedFavoriteBtn = { item ->
            // 즐겨찾기 버튼 삭제 버튼 클릭
            viewModel.deleteFavoriteVideo(item)
        }, clickedOpenDetailBtn = { item ->
            // 상세 페이지 이동 클릭
            openVideoDetail(item)
        })

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
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.pagedVideos.collectLatest {
                    videoFavoritePagingAdapter.submitData(it)
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.eventMessage.collect { msg ->
                    Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun openVideoDetail(clickedIndex: Int) {
        val intent = Intent(requireContext(), VideoDetailActivity::class.java)
        intent.putParcelableArrayListExtra("videos", ArrayList(videoFavoritePagingAdapter.snapshot().items))
        intent.putExtra("initialIndex", clickedIndex)
        intent.putExtra("route", "favorite")
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

}