package com.zkrallah.cat_calc.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zkrallah.cat_calc.HistoryAdapter
import com.zkrallah.cat_calc.databinding.ActivityMainBinding
import com.zkrallah.cat_calc.model.History
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var equation: StringBuilder
    private lateinit var top: TextView
    private lateinit var bottom: TextView
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: HistoryAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize the ViewModel
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[MainViewModel::class.java]

        // Update the UI with the history once the user enters the app
        updateUI()

        // Assign the values
        top = binding.top
        bottom = binding.bottom
        equation = java.lang.StringBuilder()
        recyclerView = binding.recyclerView

        // Stack from the end for better UX
        val layoutManager = LinearLayoutManager(this)
        layoutManager.stackFromEnd = true
        layoutManager.reverseLayout = true
        recyclerView.layoutManager = layoutManager

        // Take response from user clicks
        initClickListeners()
    }

    private fun calculate(equationPassed: String) {
        // The calculating algorithm with char Array, first, second numbers and the operator
        val chars = equationPassed.toCharArray()
        var firstNum = ""
        var secondNum = ""
        var operator = ""

        // Iterate over the char Array to get the first, second numbers and the operator
        for (i in chars.indices) {
            if (!Character.isDigit(chars[i]) && chars[i] != '.'){
                firstNum = equationPassed.slice(0 until i)
                secondNum = equationPassed.slice((i + 1) until chars.size)
                operator = chars[i].toString()
                break
            }
        }

        // Handle invalid equations
        if (secondNum.isEmpty()){
            Toast.makeText(this, "Two numbers are required !", Toast.LENGTH_LONG).show()
            return
        }

        // Get ready to calculate the answer and handle invalid operations
        var answer = 0.0f
        try{
            when(operator){
                "+" -> answer = firstNum.toFloat() + secondNum.toFloat()
                "-" -> answer = firstNum.toFloat() - secondNum.toFloat()
                "*" -> answer = firstNum.toFloat() * secondNum.toFloat()
                "/" -> answer = firstNum.toFloat() / secondNum.toFloat()
            }
        }catch (ex: NumberFormatException){
            Toast.makeText(this, "You entered an invalid equation !", Toast.LENGTH_LONG).show()
            return
        }

        // Move the equation to the top textView and show the answer on the bottom one
        // The handler to avoid UI conflicts
        top.text = equation
        Handler(Looper.getMainLooper()).postDelayed({
            bottom.text = answer.toString()
        }, 100)

        // Update the equation to be the answer so you can do further calculations
        equation.clear()
        equation.append(answer)

        // Insert the equation and the answer to the database and update UI
        lifecycleScope.launch(Dispatchers.IO) {
            viewModel.insert(History(top.text.toString() + " = " + answer))
        }
        updateUI()
    }

    private fun updateUI() {
        viewModel.allHistory.observe(this) { list ->
            list?.let {
                adapter = HistoryAdapter(list)
                recyclerView.adapter = adapter
            }
        }
    }

    private fun initClickListeners() {
        binding.zero.setOnClickListener {
            clicked("0")
        }
        binding.one.setOnClickListener {
            clicked("1")
        }
        binding.two.setOnClickListener {
            clicked("2")
        }
        binding.three.setOnClickListener {
            clicked("3")
        }
        binding.four.setOnClickListener {
            clicked("4")
        }
        binding.five.setOnClickListener {
            clicked("5")
        }
        binding.six.setOnClickListener {
            clicked("6")
        }
        binding.seven.setOnClickListener {
            clicked("7")
        }
        binding.eight.setOnClickListener {
            clicked("8")
        }
        binding.nine.setOnClickListener {
            clicked("9")
        }
        binding.c.setOnClickListener {
            clicked("c")
        }
        binding.equal.setOnClickListener {
            clicked("=")
        }
        binding.times.setOnClickListener {
            clicked("X")
        }
        binding.divide.setOnClickListener {
            clicked("%")
        }
        binding.plus.setOnClickListener {
            clicked("+")
        }
        binding.minus.setOnClickListener {
            clicked("-")
        }
        binding.dot.setOnClickListener {
            clicked(".")
        }


    }

    private fun clicked(clicked: String) {
        when(clicked){
            "0" -> equation.append("0")
            "1" -> equation.append("1")
            "2" -> equation.append("2")
            "3" -> equation.append("3")
            "4" -> equation.append("4")
            "5" -> equation.append("5")
            "6" -> equation.append("6")
            "7" -> equation.append("7")
            "8" -> equation.append("8")
            "9" -> equation.append("9")
            "." -> equation.append(".")
            "%" -> equation.append("/")
            "X" -> equation.append("*")
            "+" -> equation.append("+")
            "-" -> equation.append("-")
            "=" -> calculate(equation.toString())
            "c" -> equation.clear()
        }
        bottom.text = equation.toString()
    }

    // Saving the screen state with a bundle
    // NOTE : THIS SAVES THE STATE OF THE TWO TEXTVIEWS ONLY AS THE HISTORY IS SAVED
    // AUTOMATICALLY WITH THE VIEWMODEL
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("eqn", top.text.toString())
        outState.putString("ans", bottom.text.toString())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        equation.clear()
        equation.append(savedInstanceState.getString("ans"))
        bottom.text = equation
        top.text = savedInstanceState.getString("eqn")
    }
}