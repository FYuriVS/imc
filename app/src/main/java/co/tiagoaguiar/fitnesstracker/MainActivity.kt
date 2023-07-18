package co.tiagoaguiar.fitnesstracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import co.tiagoaguiar.fitnesstracker.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var bindin: ActivityMainBinding
    private lateinit var btnImc: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindin = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindin.root)

        btnImc = bindin.btnImc

        btnImc.setOnClickListener() {
            val i = Intent(this, ImcActivity::class.java)

            startActivity(i)
        }
    }
}