package com.geekbrains.tests.presenter.search

import com.geekbrains.tests.model.SearchResponse
import com.geekbrains.tests.repository.RepositoryCallback
import com.geekbrains.tests.repository.RepositoryContract
import com.geekbrains.tests.view.search.ViewSearchContract
import retrofit2.Response

internal class SearchPresenter internal constructor(
    private val viewContract: ViewSearchContract,
    private val repository: RepositoryContract
) : PresenterSearchContract, RepositoryCallback {


    //    private
    var viewSearchContract: ViewSearchContract? = null

    override fun searchGitHub(searchQuery: String) {
        viewSearchContract?.displayLoading(true)
        repository.searchGithub(searchQuery, this)
    }

    override fun onAttach() {
        viewSearchContract = viewContract
    }

    override fun onDetach() {
        viewSearchContract = null
    }

    override fun handleGitHubResponse(response: Response<SearchResponse?>?) {
        viewSearchContract?.displayLoading(false)
        if (response != null && response.isSuccessful) {
            val searchResponse = response.body()
            val searchResults = searchResponse?.searchResults
            val totalCount = searchResponse?.totalCount
            if (searchResults != null && totalCount != null) {
                viewSearchContract?.displaySearchResults(
                    searchResults,
                    totalCount
                )
            } else {
                viewSearchContract?.displayError("Search results or total count are null")
            }
        } else {
            viewSearchContract?.displayError("Response is null or unsuccessful")
        }
    }

    override fun handleGitHubError() {
        viewSearchContract?.displayLoading(false)
        viewSearchContract?.displayError()
    }
}
