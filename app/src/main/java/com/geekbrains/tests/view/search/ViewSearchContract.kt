package com.geekbrains.tests.view.search

import com.geekbrains.tests.model.SearchResult
import com.geekbrains.tests.view.ViewContract

internal interface ViewSearchContract : ViewContract {
    override fun displaySearchResults(
        searchResults: List<SearchResult>,
        totalCount: Int
    )

    override fun displayError()
    override fun displayError(error: String)
    override fun displayLoading(show: Boolean)
}
