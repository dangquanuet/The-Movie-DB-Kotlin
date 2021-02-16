package com.example.moviedb.data.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.moviedb.data.constants.Constants.DEFAULT_FIRST_PAGE

abstract class BasePagingSource<Item : Any> : PagingSource<Int, Item>() {

    override fun getRefreshKey(state: PagingState<Int, Item>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Item> {
        return try {
            val page = params.key ?: getFirstPage()
            LoadResult.Page(
                data = loadData(params = params) ?: listOf(),
                prevKey = if (page == getFirstPage()) null else page - 1,
                nextKey = page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        } finally {

        }
    }

    /**
     * load data
     */
    abstract suspend fun loadData(
        params: LoadParams<Int>
    ): List<Item>?

    open fun getFirstPage(): Int = DEFAULT_FIRST_PAGE
}

