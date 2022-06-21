package com.example.uasdpm2011500086

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ListView

class MainActivity : AppCompatActivity() {
    private lateinit var adpdosen: Adapter_view
    private lateinit var datasimpan: ArrayList<Lecturer>
    private lateinit var lvDataDosen: ListView
    private lateinit var TidakAdaData: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnTambah = findViewById<Button>(R.id.btnTambah)
        lvDataDosen = findViewById(R.id.lvDataDosen)
        TidakAdaData = findViewById(R.id.TidakAdaData)

        datasimpan = ArrayList()
        adpdosen = Adapter_view(this@MainActivity, datasimpan)

        lvDataDosen.adapter =adpdosen

        refresh()

        btnTambah.setOnClickListener {
            val i = Intent(this@MainActivity, Entri_Data_Dosen::class.java)
            startActivity(i)
        }
    }

    override fun onResume() {
        super.onResume()
        refresh()
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if(hasFocus) refresh()
    }

    private fun refresh(){
        val db = Campuss(this@MainActivity)
        val data = db.tampil()
        repeat(datasimpan.size) { datasimpan.removeFirst()}
        if(data.count > 0 ){
            while(data.moveToNext()){
                val data = Lecturer(
                    data.getString(0),
                    data.getString(1),
                    data.getString(2),
                    data.getString(3),
                    data.getString(4),
                    data.getString(5),
                    data.getString(6)
                )
                adpdosen.add(data)
                adpdosen.notifyDataSetChanged()
            }
            lvDataDosen.visibility = View.VISIBLE
            TidakAdaData.visibility  = View.GONE
        } else {
            lvDataDosen.visibility = View.GONE
            TidakAdaData.visibility = View.VISIBLE
        }
    }
}