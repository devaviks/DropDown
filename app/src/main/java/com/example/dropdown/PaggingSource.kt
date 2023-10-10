package com.example.dropdown


import androidx.paging.PagingSource
import androidx.paging.PagingState

class CoursePagingSource(private val dbHandler: DBHandler, private val pageSize: Int) :
    PagingSource<Int, Course>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Course> {
        return try {
            val pageNumber = params.key ?: 0
            val data = dbHandler.getAllCourses(pageNumber, pageSize)
            LoadResult.Page(
                data = data,
                prevKey = if (pageNumber == 0) null else pageNumber - 1,
                nextKey = if (data.isEmpty()) null else pageNumber + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Course>): Int? {
        return null
    }
}



