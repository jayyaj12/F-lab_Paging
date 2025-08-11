package com.aos.myapplication.view.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.aos.myapplication.databinding.FragmentSearchVideoBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class SearchVideoFragment : Fragment() {

    private val viewModel by viewModels<SearchVideoViewModel>()
    private lateinit var videoSearchPagingAdapter: VideoSearchPagingAdapter

    private var _binding: FragmentSearchVideoBinding? = null
    val binding get() = _binding!!

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
        videoSearchPagingAdapter = VideoSearchPagingAdapter("즐겨찾기 등록") { item ->
            // 즐겨찾기 버튼 클릭됨
            viewModel.onClickedAddFavorite(item)
        }

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
            val errorState = when {
                loadStates.refresh is LoadState.Error -> loadStates.refresh as LoadState.Error
                loadStates.prepend is LoadState.Error -> loadStates.prepend as LoadState.Error
                loadStates.append is LoadState.Error -> loadStates.append as LoadState.Error
                else -> null
            }

            errorState?.let {
                Timber.e("[PagingError]: ${it.error.message}")
                Toast.makeText(context, "에러 발생: ${it.error.message}", Toast.LENGTH_SHORT).show()
            }

            // 데이터 조회 시 빈 데이터 여부 확인
            val isEmpty =
                loadStates.refresh is LoadState.NotLoading && loadStates.append.endOfPaginationReached && videoSearchPagingAdapter.itemCount == 0

            viewModel.isEmptyVideos(isEmpty)
        }
    }

    private fun setupViewModelObserver() {
        lifecycleScope.launch {
            viewModel.pagedVideos.collectLatest {
                videoSearchPagingAdapter.submitData(it)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

}