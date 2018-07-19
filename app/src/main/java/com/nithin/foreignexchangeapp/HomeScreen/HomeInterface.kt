package com.nithin.foreignexchangeapp.HomeScreen

import android.text.Editable

interface HomeInterface {

    fun addDelimiterToInputDate(inputDate:Editable)

    fun clearInputDateOnUserRequest(count:Int,inputDate: Editable)
}