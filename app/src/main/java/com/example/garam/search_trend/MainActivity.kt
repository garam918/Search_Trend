package com.example.garam.search_trend

import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import android.widget.EditText
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.garam.search_trend.databinding.ActivityMainBinding
import com.example.garam.search_trend.viewModel.SearchViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel : SearchViewModel

    private lateinit var binding: ActivityMainBinding

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        binding.lifecycleOwner = this

        viewModel = ViewModelProvider(this).get(SearchViewModel::class.java)
        binding.viewModel = viewModel

        binding.startDate.setOnClickListener {
            setDatePicker(binding.startDate)
        }

        binding.endDate.setOnClickListener {
            setDatePicker(binding.endDate)
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun setDatePicker(editText: EditText) {
        val datePicker = DatePickerDialog(this)
        datePicker.show().apply {
            datePicker.setOnDateSetListener { datePicker, year, monthOfYear, dayOfMonth ->
                when(editText.id) {
                    R.id.startDate -> {
                        viewModel.setStartDate(setDate(year,monthOfYear,dayOfMonth))
                    }

                    R.id.endDate -> {
                        viewModel.setEndDate(setDate(year,monthOfYear,dayOfMonth))
                    }
                }
            }
        }
    }

    private fun setDate(year: Int, monthOfYear: Int, dayOfMonth: Int) : String{

        val year = year.toString()
        val month = when(monthOfYear+1) {
            in 1 .. 9 -> {
                "0${monthOfYear+1}"
            }
            else -> {
                "${monthOfYear+1}"
            }
        }
        val day = when(dayOfMonth) {
            in 0 .. 9 -> {
                "0$dayOfMonth"
            }
            else -> {
                "$dayOfMonth"
            }
        }
        return "$year-$month-$day"
    }
}