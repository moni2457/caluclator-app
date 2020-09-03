package com.example.tip_calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.SeekBar
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

//Initializing all the default values
private const val TAG = "MainActivity"
private const val DEFAULT_TIP_VALUE = 15
private const val DEFAULT_PERSON = 1
private const val DEFAULT_PERSON_TEXT = "1"
private const val DEFAULT_TIP_TEXT = "15%"

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //setting the default value on activity load
        seekBarTip.progress = DEFAULT_TIP_VALUE
        tvTipValue.text = DEFAULT_TIP_TEXT
        seekBarPerson.progress = DEFAULT_PERSON
        tvPersonValue.text = DEFAULT_PERSON_TEXT

        //seek bar change listener triggers when change in progress of tip
        seekBarTip.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        Log.i(TAG, "onProgressChanged $progress")
                tvTipValue.text = "$progress%"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
        //seek bar change listener triggers when change in progress of person
        seekBarPerson.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                Log.i(TAG, "onProgressChanged $progress")
                tvPersonValue.text = "$progress"

                //validation for default value of person
                if(progress == 0) {
                    seekBarPerson.progress = DEFAULT_PERSON
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }
        })

        //edit text listener triggers when change in the value of bill amount
        editTextBill.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                Log.i(TAG,"afterTextChanged $s")
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        // on Click listener for calculate button
        buttonCalculate.setOnClickListener{
            calculateTipandTotal()
        }

        // on Click listener for clear all button
        buttonClear.setOnClickListener {
            // setting the default value
            editTextBill.text.clear()
            seekBarTip.progress = DEFAULT_TIP_VALUE
            tvTipValue.text = DEFAULT_TIP_TEXT
            seekBarPerson.progress = DEFAULT_PERSON
            tvPersonValue.text = DEFAULT_PERSON_TEXT
            calculateTipandTotal()
        }
    }

    // calculate logic begins here
    private fun calculateTipandTotal() {

        //validation for checking empty value or a "."
        if(editTextBill.text.toString().isEmpty() || editTextBill.text.toString().equals(".")){
            textViewTipvalue.text = "$0.00"
            textViewTotalValue.text = "$0.00"
            textViewTipPPValue.text = "$0.00"
            textViewTotalPPValue.text = "$0.00"
            return
        }

        val billAmount  = editTextBill.text.toString().toDouble()
        val tipPercent = seekBarTip.progress
        val tipAmount = (billAmount * tipPercent)/100
        val totalBill = billAmount + tipAmount
        val totalPerson = seekBarPerson.progress
        val tipPerPerson = tipAmount/ totalPerson
        val amountPerPerson = totalBill/  totalPerson
        textViewTipvalue.text = "$"+"%.2f".format(tipAmount)
        textViewTotalValue.text = "$"+"%.2f".format(totalBill)
        textViewTipPPValue.text = "$"+"%.2f".format(tipPerPerson)
        textViewTotalPPValue.text = "$"+"%.2f".format(amountPerPerson)
    }
}
