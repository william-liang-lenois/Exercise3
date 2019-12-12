package com.example.Exercise3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    lateinit var myData: MyData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        myData = ViewModelProviders.of(this).get(MyData::class.java)

        display()
        buttonCalculate.setOnClickListener(){
            cal(it)
            display()
        }
        findViewById<Button>(R.id.buttonReset).setOnClickListener {
            reset()
        }
    }
    fun display(){
        if(myData.totalPremium != 0.0)
            textViewPremium.text = "Insurance Premium : "+myData.totalPremium.toString()
    }

    private fun cal(view: View) {
        val age = findViewById<Spinner>(R.id.spinnerAge)
        val radio = findViewById<RadioGroup>(R.id.radioGroupGender)
        val genderId = radio.checkedRadioButtonId.toInt()
        val smoker = findViewById<CheckBox>(R.id.checkBoxSmoker)
        val resultView = findViewById<TextView>(R.id.textViewPremium)

        val ageText = age.selectedItem.toString()
        var premium = 0.0;
        var mulMale = 0;
        var mulSmoke = 0;
        val value = 50;

        if(ageText != "Less than 17"){
            when (ageText){
                "17 to 25" -> premium = 70.0
                "26 to 30" -> premium = 90.0
                "31 to 40" -> premium = 120.0
                else -> premium = 150.0
            }

            when (ageText){
                "17 to 25" -> mulMale = 1
                "26 to 30" -> mulMale = 2
                "31 to 40" -> mulMale = 3
                else -> mulMale = 4
            }

            when (ageText){
                "17 to 25" -> mulSmoke = 2
                "26 to 30" -> mulSmoke = 3
                "31 to 40" -> mulSmoke = 4
                "41 to 55" -> mulSmoke = 5
                else -> mulSmoke = 6
            }

            if(genderId != -1){
                val male = findViewById<RadioButton>(R.id.radioButtonMale)
                if(male.isChecked.toString() == "true"){
                    //male
                    premium = (value * mulMale) + premium;
                }
            }
            else{
                //no gender selected
                resultView.text = "no gender selected"
            }

            if(smoker.isChecked.toString() == "true"){
                //smoker
                premium = (value * mulSmoke) + premium;
            }

        }
        else{
            premium = 60.0
        }

        myData.totalPremium = premium
    }

    private fun reset(){
        val age = findViewById<Spinner>(R.id.spinnerAge)
        val smoker = findViewById<CheckBox>(R.id.checkBoxSmoker)
        val radio = findViewById<RadioGroup>(R.id.radioGroupGender)
        val resultView = findViewById<TextView>(R.id.textViewPremium)

        age.setSelection(0)
        radio.clearCheck()
        smoker.setChecked(false)
        resultView.text = "Insurance Premium : "

        Toast.makeText(this, "All Reset",
            Toast.LENGTH_SHORT).show()
    }
}
