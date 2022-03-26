package com.example.testcounter

import com.geekbrains.tests.presenter.search.SearchPresenter
import com.geekbrains.tests.repository.GitHubRepository
import com.geekbrains.tests.view.search.ViewSearchContract
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class SearchPresenterTest {

    private lateinit var searchPresenter: SearchPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        searchPresenter = SearchPresenter(viewContract, repository)
    }

    @Mock
    private lateinit var viewContract: ViewSearchContract

    @Mock
    private lateinit var repository: GitHubRepository

    @Test
    fun `test onAttach SearchPresenter`() {
        Assert.assertNull(searchPresenter.viewSearchContract)
        searchPresenter.onAttach()
        Assert.assertNotNull(searchPresenter.viewSearchContract)
    }

    @Test
    fun `test onDetach SearchPresenter`(){
        searchPresenter.onAttach()
        Assert.assertNotNull(searchPresenter.viewSearchContract)
        searchPresenter.onDetach()
        Assert.assertNull(searchPresenter.viewSearchContract)
    }

}