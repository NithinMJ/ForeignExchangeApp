package com.nithin.foreignexchangeapp.HomeScreen

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import com.nithin.foreignexchangeapp.ForexRatesByDateScreen.ForexRatesByDateActivity
import com.nithin.foreignexchangeapp.LatestForexRatesScreen.LatestForexRateActivity
import com.nithin.foreignexchangeapp.R
import kotlinx.android.synthetic.main.activity_home.*

const val baseURL = "https://exchangeratesapi.io/api/"

class HomeActivity : AppCompatActivity() {

    private var homeInterface: HomeInterface = HomePresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        dateEntryText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                homeInterface.addDelimiterToInputDate(s)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                homeInterface.clearInputDateOnUserRequest(count,dateEntryText.text)
            }
        })

        val dateEntryTextValue = dateEntryText.text
        latestForexRatesButton.setOnClickListener { startActivity(Intent(this, LatestForexRateActivity::class.java)) }

        forexRatesByDateButton.setOnClickListener {
            if (dateEntryTextValue.trim().isNotEmpty()) {
                val intent = Intent(this, ForexRatesByDateActivity::class.java)
                intent.putExtra("userEnteredDate", dateEntryTextValue.toString())
                startActivity(intent)
            } else {
                Toast.makeText(this, "Enter Date", Toast.LENGTH_SHORT).show()
            }
        }
    }

}
