package com.example.kostku

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kostku.room.Constant
import com.example.kostku.room.Kost
import com.example.kostku.room.KostDb
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    val db by lazy { KostDb(this) }


    lateinit var kostAdapter : KostAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupListener()
        setupRecyclerview()
    }



    override fun onStart() {
        super.onStart()
        loadmovie()
    }

    fun loadmovie(){
        CoroutineScope(Dispatchers.IO).launch {
            val movie = db.kostDao().getkostn()
            Log.d("MainAvtivity", "dbResponse: $movie")
            withContext(Dispatchers.Main) {
                kostAdapter.setData(movie)
            }
        }
    }

    fun setupListener() {
        add_kost.setOnClickListener{
            intentEdit(0, Constant.TYPE_CREATE)
        }
    }

    fun intentEdit(movieId: Int, intentType: Int){
        startActivity(
            Intent(applicationContext, AddActivity::class.java)
                .putExtra("intent_id",movieId)
                .putExtra("intent_type",intentType)
        )
    }

    private fun setupRecyclerview(){
        kostAdapter = KostAdapter(arrayListOf(), object : KostAdapter.OnAdapterListener{
            override fun onClick(kost: Kost) {
                // read detail movie
                intentEdit(kost.id,Constant.TYPE_READ)
            }

            override fun onUpdate(kost: Kost) {
                intentEdit(kost.id,Constant.TYPE_UPDATE)
            }

            override fun onDelete(kost: Kost) {
                deleteDialog(kost)
            }

        })
        rv_kost.apply{
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = kostAdapter
        }
    }

    private fun deleteDialog(kost: Kost) {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.apply {
            setTitle("Konfirmasi")
            setMessage("Yakin mau dihapus nih? ${kost.title}?")
            setNegativeButton("Batal") { dialogInterface, i ->
                dialogInterface.dismiss()
            }
            setPositiveButton("Hapus") { dialogInterface, i ->
                dialogInterface.dismiss()
                CoroutineScope(Dispatchers.IO).launch {
                    db.kostDao().deleteKost(kost)
                    loadmovie()
                }
            }
        }
        alertDialog.show()
    }
}
