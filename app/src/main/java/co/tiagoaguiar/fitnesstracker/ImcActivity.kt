package co.tiagoaguiar.fitnesstracker

import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import co.tiagoaguiar.fitnesstracker.databinding.ActivityImcBinding
import co.tiagoaguiar.fitnesstracker.databinding.ActivityMainBinding

class ImcActivity : AppCompatActivity() {
    private lateinit var binding: ActivityImcBinding

    private lateinit var weight: EditText
    private lateinit var height: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImcBinding.inflate(layoutInflater)
        setContentView(binding.root)

        weight = binding.editImcWeight
        height = binding.editImcHeight
        val btnSend = binding.btnImc

        var res: Double

        btnSend.setOnClickListener() {
            res = calcImc(weight.text.toString().toInt(), height.text.toString().toInt())
            if (!validateForm()) {
                Toast.makeText(this, R.string.fields_message, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val imcResponseId = imcResponse(res)
            val title = getString(R.string.imc_response, res)

            AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(imcResponseId)
                .setPositiveButton(android.R.string.ok, object : DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        weight.setText("")
                        height.setText("")
                    }
                })
                .create()
                .show()

            val service = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            service.hideSoftInputFromWindow(currentFocus?.windowToken, 0)

        }

    }

    @StringRes
    private fun imcResponse(imc: Double): Int {
        return when {
            imc < 15.0 -> R.string.imc_severely_low_weight
            imc < 16.0 -> R.string.imc_very_low_weight
            imc < 18.5 -> R.string.imc_low_weight
            imc < 25.0 -> R.string.normal
            imc < 30.0 -> R.string.imc_high_weight
            imc < 35.0 -> R.string.imc_so_high_weight
            imc < 40 -> R.string.imc_severely_high_weight
            else -> R.string.imc_extreme_weight
        }
    }

    private fun calcImc(w: Int, h: Int): Double {
        return w / ((h / 100.0) * (h / 100.0))
    }

    private fun validateForm(): Boolean {
        return (weight.text.toString().isNotEmpty()
                && height.text.toString().isNotEmpty()
                && !weight.text.toString().startsWith("0")
                && !height.text.toString().startsWith("0")
                )
    }
}