package com.example.testcounter

import com.geekbrains.tests.presenter.details.DetailsPresenter
import com.geekbrains.tests.presenter.details.PresenterDetailsContract
import com.geekbrains.tests.view.ViewContract
import com.geekbrains.tests.view.details.ViewDetailsContract
import junit.framework.Assert.assertEquals
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class DetailsPresenterTest {

    private lateinit var presenter: DetailsPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = DetailsPresenter(viewContract)
    }

    @Mock
    lateinit var viewContract: ViewContract

    @Test
    fun test_Increment_Count(){
        for (i in 1..3){
            presenter.onIncrement()
        }
        Assert.assertEquals(presenter.count, 3)
    }

    @Test
    fun `checking the method onIncrement Count`(){
        for (i in 1..5){
            presenter.onDecrement()
        }
        Assert.assertEquals(presenter.count, -5)
    }

    @Test
    fun `checking the method onDetach`(){
        presenter.onDetach()
        Assert.assertNull(presenter.viewDetailsContract)
    }



}