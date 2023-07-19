package co.tiagoaguiar.fitnesstracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
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
            if(!validateForm()){
                Toast.makeText(this, R.string.fields_message, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else {
                Toast.makeText(this, "Seu IMC é: $res", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun calcImc(w: Int, h: Int): Double {
        return w / ((h/100.0) * (h/100.0))
    }

    private fun validateForm(): Boolean {
        return (weight.text.toString().isNotEmpty()
            && height.text.toString().isNotEmpty()
            && !weight.text.toString().startsWith("0")
            && !height.text.toString().startsWith("0")
        )
    }
}