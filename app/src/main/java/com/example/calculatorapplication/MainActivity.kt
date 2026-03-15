package com.example.calculatorapplication

// for activity lifecycle and onCreate
import android.os.Bundle

// for handling button clicks
import android.view.View
import android.widget.Button

// import textview to display text on screen
import android.widget.TextView

// import Toast to show messages to user
import android.widget.Toast

// import for main activity lifecycle methods
import androidx.appcompat.app.AppCompatActivity

// import sqrt from Kotlin's math lib for square root function
import kotlin.math.sqrt

// Main activity for calculator app to handle input, operations, and display
class MainActivity : AppCompatActivity()
{
    // displays current input and calculation results
    private lateinit var textViewResult: TextView

    // store current number input as string
    private var currentInput = ""

    // store first number entered before operator
    private var firstNumber = 0.0

    // store selected operator as string
    private var operator = ""

    // onCreate, called when activity first created to initialize layout and UI
    override fun onCreate(savedInstanceState: Bundle?)
    {
        // set up activity
        super.onCreate(savedInstanceState)

        // set layout for activity
        setContentView(R.layout.activity_main)

        // connect TextView in xml to textViewResult variable
        textViewResult = findViewById(R.id.textViewResult)
    }

    // Number buttons (0–9)
    fun onNumberClick(view: View)
    {
        // use view as a button to use .text
        val button = view as Button


        // convert button text to string and append to currentInput
        currentInput += button.text.toString()


        // update display to show what the user is typing
        textViewResult.text = currentInput
    }

    // Decimal point button
    fun onDecimalClick(view: View)
    {
        // add decimal if not already in currentInput
        if (!currentInput.contains("."))
        {
            if (currentInput.isEmpty()) currentInput = "0"
            currentInput += "."
            textViewResult.text = currentInput
        }
    }

    // Function to handle when user presses an operator button
    fun onOperatorClick(view: View)
    {
        // use view as a button to use .text
        val button = view as Button


        // handle negative number input
        if (button.text == "-" && currentInput.isEmpty())
        {
            currentInput = "-"
            textViewResult.text = currentInput
            return
        }


        // Toast message to type number if they haven't yet
        if (currentInput.isEmpty())
        {
            Toast.makeText(this, "Enter a number first", Toast.LENGTH_SHORT).show()
            return
        }

        // store first number the user typed
        firstNumber = currentInput.toDouble()

        // store operator button display text as selected operator
        operator = button.text.toString()

        // clear currentInput for next number
        currentInput = ""

        // update display to show operator after first number
        textViewResult.text = formatResult(firstNumber) + " " + operator + " "
    }

    // Equals button function
    fun onEqualClick(view: View)
    {
        // check if user entered second number, if not, display Toast message
        if (currentInput.isEmpty())
        {
            Toast.makeText(this, "Enter second number", Toast.LENGTH_SHORT).show()
            return
        }

        val secondNumber = currentInput.toDouble()

        // when statement, for checking value of operator
        val result = when (operator)
        {
            "+" -> add(firstNumber, secondNumber)
            "-" -> subtract(firstNumber, secondNumber)
            "×" -> multiply(firstNumber, secondNumber)
            "÷" -> divide(firstNumber, secondNumber)
            else -> 0.0
        }

        // convert to string for textView with neat formatting
        textViewResult.text = formatResult(result)

        // save input result
        currentInput = result.toString()

        // reset operator
        operator = ""
    }

    // Clear button
    fun onClearClick(view: View)
    {
        // reset string
        currentInput = ""

        // reset stored first number
        firstNumber = 0.0

        // remove stored operator
        operator = ""

        // make screen show '0' again
        textViewResult.text = "0"
    }

    // Square function
    fun onSquareClick(view: View)
    {
        // Toast message to handle empty input
        if (currentInput.isEmpty())
        {
            Toast.makeText(this, "Enter a number first", Toast.LENGTH_SHORT).show()
            return
        }

        val number = currentInput.toDouble()

        // use square function
        val result = square(number)

        // convert to string for textView with neat formatting
        textViewResult.text = formatResult(result)

        // save input result
        currentInput = result.toString()
    }

    // Square Root function
    fun onSqrtClick(view: View)
    {
        // Toast message to handle no input
        if (currentInput.isEmpty())
        {
            Toast.makeText(this, "Enter a number first", Toast.LENGTH_SHORT).show()
            return
        }

        val number = currentInput.toDouble()

        // Toast message to handle negative input, can't do that with sqrt
        if (number < 0)
        {
            Toast.makeText(this, "Cannot take square root of negative number", Toast.LENGTH_SHORT).show()
            return
        }

        // use sqrt() function from Kotlin's math lib to do operation
        val result = sqrt(number)

        // convert to string for textView with neat formatting
        textViewResult.text = formatResult(result)

        // save input result
        currentInput = result.toString()
    }

    // Arithmetic functions

    // Add function
    private fun add(a: Double, b: Double): Double = a + b

    // Subtract function
    private fun subtract(a: Double, b: Double): Double = a - b

    // Multiply function
    private fun multiply(a: Double, b: Double): Double = a * b

    // Divide function with Toast message to handle division by zero
    private fun divide(a: Double, b: Double): Double
    {
        if (b == 0.0)
        {
            Toast.makeText(this, "Cannot divide by zero", Toast.LENGTH_SHORT).show()
            return 0.0
        }
        return a / b
    }

    // Square function
    private fun square(a: Double): Double = a * a
    
    // Helper function to format result for display
    private fun formatResult(number: Double): String
    {
        return if (number % 1.0 == 0.0)
        {
            // if whole number, show without decimal
            number.toInt().toString()
        }
        else
        {
            // otherwise, round to 8 decimals max
            String.format("%.8f", number).trimEnd('0').trimEnd('.')
        }
    }
}

