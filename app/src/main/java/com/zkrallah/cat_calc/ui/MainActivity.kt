package com.zkrallah.cat_calc.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.TextView
import android.widget.Toast
import com.zkrallah.cat_calc.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var equation: StringBuilder
    private lateinit var top: TextView
    private lateinit var bottom: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        top = binding.top
        bottom = binding.bottom
        equation = java.lang.StringBuilder()

        initClickListeners()
    }

    private fun calculate(equationPassed: String) {
        val chars = equationPassed.toCharArray()
        var firstNum = ""
        var secondNum = ""
        var operator = ""

        for (i in chars.indices) {
            if (!Character.isDigit(chars[i]) && chars[i] != '.'){
                firstNum = equationPassed.slice(0 until i)
                secondNum = equationPassed.slice((i + 1) until chars.size)
                operator = chars[i].toString()
                break
            }
        }
        var answer = 0.0f
        when(operator){
            "+" -> answer = firstNum.toFloat() + secondNum.toFloat()
            "-" -> answer = firstNum.toFloat() - secondNum.toFloat()
            "*" -> answer = firstNum.toFloat() * secondNum.toFloat()
            "/" -> answer = firstNum.toFloat() / secondNum.toFloat()
        }
        top.text = equation
        Handler(Looper.getMainLooper()).postDelayed({
            bottom.text = answer.toString()
        }, 100)
        equation.clear()
        equation.append(answer)
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
}