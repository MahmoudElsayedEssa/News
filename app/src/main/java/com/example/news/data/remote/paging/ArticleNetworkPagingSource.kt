package com.example.news.data.remote.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.news.data.remote.mappers.NewsDataMapper
import com.example.news.data.remote.paging.fetcher.PagedDataFetcher
import com.example.news.domain.model.Article


class ArticleNetworkPagingSource(
    private val pagedDataFetcher: PagedDataFetcher
) : PagingSource<Int, Article>() {

    companion object {
        private const val STARTING_PAGE_INDEX = 1
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        val pageNumber = params.key ?: STARTING_PAGE_INDEX
        val pageSizeToLoad = pagedDataFetcher.apiPageSize

        return try {
            val responseResult =
                pagedDataFetcher.fetch(page = pageNumber, pageSize = pageSizeToLoad)

            responseResult.fold(onSuccess = { newsResponseDto ->
                val articlesDto = newsResponseDto.articles
                val articlesDomain = articlesDto.mapNotNull { dto ->
                    NewsDataMapper.mapToArticle(dto).getOrNull()
                }

                val nextKey = if (articlesDomain.isEmpty()) {
                    null
                } else {
                    pageNumber + 1
                }
                val prevKey = if (pageNumber == STARTING_PAGE_INDEX) null else pageNumber - 1

                LoadResult.Page(
                    data = articlesDomain, prevKey = prevKey, nextKey = nextKey
                )
            }, onFailure = { throwable ->
                LoadResult.Error(throwable)
            })
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}