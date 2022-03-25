package com.geekbrains.tests.presenter.search

import com.geekbrains.tests.model.SearchResponse
import com.geekbrains.tests.repository.GitHubRepository
import com.geekbrains.tests.repository.GitHubRepository.GitHubRepositoryCallback
import com.geekbrains.tests.view.ViewContract
import com.geekbrains.tests.view.search.ViewSearchContract
import retrofit2.Response

/**
 * В архитектуре MVP все запросы на получение данных адресуются в Репозиторий.
 * Запросы могут проходить через Interactor или UseCase, использовать источники
 * данных (DataSource), но суть от этого не меняется.
 * Непосредственно Презентер отвечает за управление потоками запросов и ответов,
 * выступая в роли регулировщика движения на перекрестке.
 */

internal class SearchPresenter internal constructor(
    private val viewContract: ViewContract,
    private val repository: GitHubRepository
) : PresenterSearchContract, GitHubRepositoryCallback {


    private var viewSearchContract: ViewSearchContract? = null

    override fun searchGitHub(searchQuery: String) {
        viewSearchContract?.displayLoading(true)
        repository.searchGithub(searchQuery, this)
    }

    override fun onAttach() {
        viewSearchContract = viewContract as ViewSearchContract
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
