package com.nithin.foreignexchangeapp.HomeScreen

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.nithin.foreignexchangeapp.ForexRatesByDateScreen.ForexRatesByDateActivity
import com.nithin.foreignexchangeapp.LatestForexRatesScreen.LatestForexRateActivity
import com.nithin.foreignexchangeapp.R
import kotlinx.android.synthetic.main.activity_home.*

const val baseURL = "https://exchangeratesapi.io/api/"

class HomeActivity : AppCompatActivity() {

    private var homeInterface: HomeInterface = HomePresenter()
    private var baseCurrencyTextValue: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val baseCurrencyArray = arrayOf("EUR","USD","GBP","INR","JPY","CAD")

        spinnerBaseCurrency.adapter = ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,baseCurrencyArray)

        spinnerBaseCurrency.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                //TODO
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                baseCurrencyTextValue = baseCurrencyArray[position]
            }

        }

        dateEntryText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                homeInterface.addDelimiterToInputDate(s)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                homeInterface.clearInputDateOnUserRequest(count, dateEntryText.text)
            }
        })

//        val baseCurrencyTextValue = baseCurrencyText.text
        latestForexRatesButton.setOnClickListener {
            if (baseCurrencyTextValue.trim().isNotEmpty()) {
                val intent = Intent(this, LatestForexRateActivity::class.java)
                intent.putExtra("userEnteredBaseCurrency", baseCurrencyTextValue)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Enter Base Currency", Toast.LENGTH_SHORT).show()
            }
        }


        val dateEntryTextValue = dateEntryText.text
        forexRatesByDateButton.setOnClickListener {
            if (dateEntryTextValue.trim().isNotEmpty()) {
                val intent = Intent(this, ForexRatesByDateActivity::class.java)
                intent.putExtra("userEnteredDate", dateEntryTextValue.toString())
                intent.putExtra("userEnteredBaseCurrency", baseCurrencyTextValue)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Enter Date", Toast.LENGTH_SHORT).show()
            }
        }
    }

}
