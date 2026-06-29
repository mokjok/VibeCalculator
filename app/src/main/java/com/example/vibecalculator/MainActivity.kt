package com.example.vibecalculator

import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.view.Window
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var spinnerMoodA: Spinner
    private lateinit var spinnerMoodB: Spinner
    private lateinit var spinnerOperation: Spinner
    private lateinit var tvHistory: TextView

    private val moods = listOf(
        "Calm", "Excited", "Curious", "Nostalgic", "Bold",
        "Cozy", "Mysterious", "Playful", "Focused", "Dreamy"
    )

    private val operations = listOf("Blend", "Clash", "Amplify", "Balance")

    // Each result name maps to a description, an emoji, and a colour.
    private data class VibeResult(val name: String, val description: String, val emoji: String, val color: Int)

    private val historyLog = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        spinnerMoodA = findViewById(R.id.spinnerMoodA)
        spinnerMoodB = findViewById(R.id.spinnerMoodB)
        spinnerOperation = findViewById(R.id.spinnerOperation)
        tvHistory = findViewById(R.id.tvHistory)

        val moodAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, moods)
        spinnerMoodA.adapter = moodAdapter
        spinnerMoodB.adapter = moodAdapter

        val opAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, operations)
        spinnerOperation.adapter = opAdapter
        spinnerMoodB.setSelection(1)

        val btnCalculate: Button = findViewById(R.id.btnCalculate)
        val btnReset: Button = findViewById(R.id.btnReset)

        btnCalculate.setOnClickListener { calculateVibe() }

        btnReset.setOnClickListener {
            spinnerMoodA.setSelection(0)
            spinnerMoodB.setSelection(1)
            spinnerOperation.setSelection(0)
            historyLog.clear()
            tvHistory.text = ""
            Toast.makeText(this, "Calculator reset", Toast.LENGTH_SHORT).show()
        }
    }

    private fun calculateVibe() {
        val moodA = spinnerMoodA.selectedItem.toString()
        val moodB = spinnerMoodB.selectedItem.toString()
        val operation = spinnerOperation.selectedItem.toString()

        if (moodA == moodB) {
            Toast.makeText(this, "Pick two different moods for a real vibe!", Toast.LENGTH_SHORT).show()
            return
        }

        val result = computeVibe(moodA, moodB, operation)
        showResultDialog(result)

        val entry = "$moodA $operation $moodB \u2192 ${result.name}"
        historyLog.add(0, entry)
        if (historyLog.size > 6) historyLog.removeAt(historyLog.size - 1)
        tvHistory.text = historyLog.joinToString("\n")
    }

    /**
     * Deterministically "calculates" a creative, non-numeric vibe from two moods
     * and an operation, instead of performing arithmetic on numbers.
     */
    private fun computeVibe(moodA: String, moodB: String, operation: String): VibeResult {
        val seed = (moodA + operation + moodB).sumOf { it.code }
        val palette = listOf(
            Color.parseColor("#7E57C2"), Color.parseColor("#26A69A"),
            Color.parseColor("#EF5350"), Color.parseColor("#FFCA28"),
            Color.parseColor("#42A5F5"), Color.parseColor("#EC407A"),
            Color.parseColor("#66BB6A"), Color.parseColor("#FF7043")
        )
        val emojis = listOf("\u2728", "\ud83c\udf19", "\ud83d\udd25", "\ud83c\udf08", "\ud83c\udf3f", "\u26a1", "\ud83c\udf0a", "\ud83c\udf3c")

        val color = palette[seed % palette.size]
        val emoji = emojis[(seed / 7) % emojis.size]

        val (verb, explainer) = when (operation) {
            "Blend" -> "blends into" to "The two moods merge smoothly, creating a single layered feeling."
            "Clash" -> "clashes with" to "The two moods push against each other, creating tension and contrast."
            "Amplify" -> "amplifies" to "The first mood is intensified by the second, turning the volume up."
            else -> "balances" to "The two moods settle into an even, grounded state."
        }

        val name = when (operation) {
            "Blend" -> "$moodA-$moodB Blend"
            "Clash" -> "$moodA vs $moodB"
            "Amplify" -> "Amplified $moodA"
            else -> "$moodA/$moodB Equilibrium"
        }

        val description = "$moodA $verb $moodB. $explainer"

        return VibeResult(name, description, emoji, color)
    }

    private fun showResultDialog(result: VibeResult) {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_vibe_result)
        dialog.setCancelable(true)

        val tvEmoji: TextView = dialog.findViewById(R.id.tvDialogEmoji)
        val tvName: TextView = dialog.findViewById(R.id.tvDialogResultName)
        val tvDesc: TextView = dialog.findViewById(R.id.tvDialogDescription)
        val swatch = dialog.findViewById<android.view.View>(R.id.viewColorSwatch)
        val btnClose: Button = dialog.findViewById(R.id.btnDialogClose)

        tvEmoji.text = result.emoji
        tvName.text = result.name
        tvDesc.text = result.description
        swatch.setBackgroundColor(result.color)

        btnClose.setOnClickListener { dialog.dismiss() }

        dialog.show()
    }
}
