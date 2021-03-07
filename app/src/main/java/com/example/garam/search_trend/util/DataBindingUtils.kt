package com.example.garam.search_trend.util

import android.widget.RadioGroup
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import com.example.garam.search_trend.R.id.*

object DataBindingUtils {
    @BindingAdapter("dateRangeCheck")
    @JvmStatic
    fun bindDateRange(radioGroup: RadioGroup, value : MutableLiveData<String>) {
        radioGroup.setOnCheckedChangeListener { radioGroup, i ->
            when(i) {
                dateRangeDay -> {
                    value.value = "date"
                }
                dateRangeWeek -> {
                    value.value = "week"
                }
                dateRangeMonth -> {
                    value.value = "month"
                }
            }
        }
    }

    @BindingAdapter("deviceCheck")
    @JvmStatic
    fun bindDeviceCheck(radioGroup: RadioGroup, value: MutableLiveData<String>) {
        radioGroup.setOnCheckedChangeListener { radioGroup, i ->
            when(i) {
                device_Mobile -> {
                    value.value = "mo"
                }
                device_PC -> {
                    value.value = "pc"
                }
                device_Whole -> {
                    value.value = null
                }
            }
        }
    }

    @BindingAdapter("genderCheck")
    @JvmStatic
    fun bindGenderCheck(radioGroup: RadioGroup, value: MutableLiveData<String>){
        radioGroup.setOnCheckedChangeListener { radioGroup, i ->
            when(i) {
                gender_Female -> {
                    value.value = "f"
                }
                gender_Male -> {
                    value.value = "m"
                }
                gender_Whole -> {
                    value.value = null
                }
            }
        }
    }
}