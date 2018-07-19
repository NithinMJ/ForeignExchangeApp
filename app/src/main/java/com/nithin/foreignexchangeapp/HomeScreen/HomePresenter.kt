package com.nithin.foreignexchangeapp.HomeScreen

import android.text.Editable

open class HomePresenter : HomeInterface {
    override fun clearInputDateOnUserRequest(count: Int, inputDate: Editable) {
        if (count == 0) {
            inputDate.clear()
        }
    }

    override fun addDelimiterToInputDate(inputDate: Editable) {
        if (inputDate.length == 4 || inputDate.length == 7) {
            inputDate.append('-')
        }
    }
}