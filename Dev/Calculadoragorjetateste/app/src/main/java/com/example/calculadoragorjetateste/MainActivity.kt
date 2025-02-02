package com.example.calculadoragorjetateste

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.RadioButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.calculadoragorjetateste.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        var percentage: Int = 0

        binding.rbTen.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked){
                percentage = 10
            }
        }

        binding.rbFifteen.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked){
                percentage = 15
            }
        }

        binding.rbTwenty.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked){
                percentage = 20
            }
        }

        val adapter = ArrayAdapter.createFromResource(this, R.array.num_people, android.R.layout.simple_spinner_item)

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.spinnerNumberOfPeople.adapter = adapter

        var numOfPeopleSelected = 0
        binding.spinnerNumberOfPeople.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    numOfPeopleSelected = position
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {

                }
            }


        binding.btnClean.setOnClickListener {
            clean()
        }

        binding.btnCalculate.setOnClickListener {

            val totalAmountTemp = binding.tietAmount.text

            if (totalAmountTemp?.isEmpty() == true) {

                Snackbar.make(binding.tietAmount, "Please fill in all fields", Snackbar.LENGTH_LONG)
                    .show()

            } else {

                val totalAmount: Float = totalAmountTemp.toString().toFloat()
                val people: Int = numOfPeopleSelected

                val totalTemp = totalAmount / people
                val tips = totalTemp * percentage / 100
                val totalTips = totalTemp + tips

                intent = Intent(this, SummaryActivity::class.java)
                intent.apply {
                    putExtra("totalAmount", totalAmount)
                    putExtra("numPeople", numOfPeopleSelected)
                    putExtra("percentage", percentage)
                    putExtra("total", totalTips )
                }
                startActivity(intent)
            }
        }
    }

    private fun clean(){
        binding.tietAmount.setText("")
        binding.rbTwenty.isChecked = false
        binding.rbFifteen.isChecked = false
        binding.rbTen.isChecked = false
    }
}