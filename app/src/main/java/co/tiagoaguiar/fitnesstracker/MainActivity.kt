package co.tiagoaguiar.fitnesstracker

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.tiagoaguiar.fitnesstracker.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var rvMain: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mainItems = mutableListOf<MainItem>()
        mainItems.add(
            MainItem(
                id = 1,
                drawableId = R.drawable.baseline_wb_sunny_24,
                texStringId = R.string.label_imc,
                color = Color.GREEN
            )
        );
        mainItems.add(
            MainItem(
                id = 2,
                drawableId = R.drawable.baseline_whatshot_24,
                texStringId = R.string.tmb,
                color = Color.RED
            )
        )

        /*
        *  1. Criar o layout que vai ser mostrado;
        *  2. Onde o recyclerView vai aparecer(na tela principal, então coloca ela no xml da tela principal e binda em uma variável)
        *  3. Lógica -> conectar o xml da celula dentro do recyclerView + a quantidade de elementos
        * */

        val adapter = MainAdapter(mainItems) { id, item  ->
            when (id) {
                1 -> {
                    val intent = Intent(this@MainActivity, ImcActivity::class.java)
                    startActivity(intent)
                    Toast.makeText(this, item.texStringId, Toast.LENGTH_SHORT).show()
                }

                2 -> {}
            }
        }
        rvMain = binding.rvMain
        rvMain.adapter = adapter
        rvMain.layoutManager = GridLayoutManager(this, 2)

    }

    private inner class MainAdapter(
        private val mainItems: List<MainItem>,
        private val onItemClickListener: (Int, MainItem) -> Unit,
    ) : RecyclerView.Adapter<MainAdapter.MainViewHolder>() {
        // 1° informa qual o layout da celula expecífica (item)
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
            val view = layoutInflater.inflate(R.layout.main_item, parent, false)
            return MainViewHolder(view)

        }

        // 2° Informar quantas celulas a listagem terá
        override fun getItemCount(): Int {
            return mainItems.size
        }

        // 3° Disparado quando houver uma rolagem na tela e for necessário trocar o conteudo da celula
        override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
            val currentItem = mainItems[position]
            holder.bind(currentItem)
        }

        private inner class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            fun bind(item: MainItem) {
                val card: LinearLayout = itemView.findViewById(R.id.card)
//                card.setBackgroundColor(item.color)

                val text: TextView = itemView.findViewById(R.id.text_imc)
                text.setText(item.texStringId)

                val icon: ImageView = itemView.findViewById(R.id.img_imc)
                icon.setImageResource(item.drawableId)

                card.setOnClickListener {
                    onItemClickListener.invoke(item.id, item)
                }
            }
        }

    }


}