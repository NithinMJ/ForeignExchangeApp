package com.nithin.foreignexchangeapp.HomeScreen

import android.text.Editable
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

class HomePresenterTest {

    private val presenter = HomePresenter()
    val editable = mock(Editable::class.java)

    @Test
    fun `WHEN clearInputDateOnUserRequest is called AND count is 0 THEN clear the edit text field`() {

        //arrange

        //act
        presenter.clearInputDateOnUserRequest(0, editable)

        //assert
        verify(editable).clear()
    }
}