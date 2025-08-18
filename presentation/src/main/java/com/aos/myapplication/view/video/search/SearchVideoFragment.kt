package com.aos.myapplication.view.video.search

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
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.aos.myapplication.databinding.FragmentSearchVideoBinding
import com.aos.myapplication.view.video.VideoScreenRoute
import com.aos.myapplication.view.video.detail.VideoDetailActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class SearchVideoFragment : Fragment() {

    private val viewModel by viewModels<SearchVideoViewModel>()
    private lateinit var videoSearchPagingAdapter: VideoSearchPagingAdapter

    private var _binding: FragmentSearchVideoBinding? = null
    val binding get() = _binding ?: throw IllegalStateException("Binding is not initialized")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding =
            FragmentSearchVideoBinding.inflate(LayoutInflater.from(context), container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.vm = viewModel


        setupVideoList()
        setupViewModelObserver()
    }

    private fun setupVideoList() {
        videoSearchPagingAdapter = VideoSearchPagingAdapter(clickedFavoriteBtn = {
            item ->
            // 즐겨찾기 버튼 클릭됨
            viewModel.onClickedAddFavorite(item)
        }, clickedOpenDetailBtn = {
            openVideoDetail(it)
        })

        with(binding.rvVideoList) {
            if (itemDecorationCount == 0) {
                addItemDecoration(
                    DividerItemDecoration(context, RecyclerView.VERTICAL)
                )
            }
            adapter = videoSearchPagingAdapter
        }

        videoSearchPagingAdapter.addLoadStateListener { loadStates ->
            // 에러처리
            val errorState = loadStates.source.append as? LoadState.Error
                ?: loadStates.source.prepend as? LoadState.Error
                ?: loadStates.source.refresh as? LoadState.Error
                ?: loadStates.append as? LoadState.Error
                ?: loadStates.prepend as? LoadState.Error
                ?: loadStates.refresh as? LoadState.Error

            errorState?.let {
                Timber.e("[PagingError]: ${it.error.message}")
                Toast.makeText(context, "에러 발생: ${it.error.message}", Toast.LENGTH_SHORT).show()
            }

            // 데이터 조회 시 빈 데이터 여부 확인
            val isEmpty = loadStates.refresh is LoadState.NotLoading &&
                    loadStates.append.endOfPaginationReached &&
                    videoSearchPagingAdapter.itemCount < 1

            viewModel.isEmptyVideos(isEmpty)
        }
    }

    private fun setupViewModelObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.pagedVideos.collectLatest {
                    videoSearchPagingAdapter.submitData(it)
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
        intent.putExtra("videos", ArrayList(videoSearchPagingAdapter.snapshot().items))
        intent.putExtra("initialIndex", clickedIndex)
        intent.putExtra("route", VideoScreenRoute.SEARCH.name)

        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

}