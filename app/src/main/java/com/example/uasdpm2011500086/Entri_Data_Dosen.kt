package com.example.uasdpm2011500086

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*

class Entri_Data_Dosen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_entri_data_dosen)
        val modeEdit = intent.hasExtra("nidn") && intent.hasExtra("nama") &&
                intent.hasExtra("jabatan") && intent.hasExtra("golpat") &&
                intent.hasExtra("pendidikan") && intent.hasExtra("keahlian") &&
                intent.hasExtra("studi")
        title = if(modeEdit) "Edit Data Dosen" else "Entri Data Dosen"

        val etkdnidn = findViewById<EditText>(R.id.etkdNIDN)
        val etnmDosen = findViewById<EditText>(R.id.etnmDosen)
        val spnJabatan = findViewById<Spinner>(R.id.spnJabatan)
        val spnGolonganpangkat = findViewById<Spinner>(R.id.spnGolonganpangkat)
        val rdS2 = findViewById<RadioButton>(R.id.rdS2)
        val rdS3 = findViewById<RadioButton>(R.id.rdS3)
        val etkeahlian = findViewById<EditText>(R.id.etkeahlian)
        val etprgstudi = findViewById<EditText>(R.id.etprgstudi)
        val btnSimpan = findViewById<Button>(R.id.btnSimpan)
        val etjabatan = arrayOf("Tenaga Pengajar","Asisten Ahli","Lektor","Lektor Kepala","Guru Besar")
        val pangkat = arrayOf("III/a - Penata Muda","III/b - Penata Muda Tingkat I","III/c - Penata","III/d - Penata Tingkat I",
            "IV/a - Pembina","IV/b - Pembina Tingkat I","IV/c - Pembina Utama Muda","IV/d - Pembina Utama Madya",
            "IV/e - Pembina Utama")
        val adpGolpat = ArrayAdapter(
            this@Entri_Data_Dosen,
            android.R.layout.simple_spinner_dropdown_item,
            pangkat
        )
        spnGolonganpangkat.adapter = adpGolpat

        val adpJabatan = ArrayAdapter(
            this@Entri_Data_Dosen,
            android.R.layout.simple_spinner_dropdown_item,
            etjabatan
        )
        spnJabatan.adapter = adpJabatan

        if(modeEdit) {
            val kodeNidn = intent.getStringExtra("nidn")
            val nama = intent.getStringExtra("nama")
            val jabatan = intent.getStringExtra("jabatan")
            val golpat = intent.getStringExtra("golpat")
            val pendidikan= intent.getStringExtra("pendidikan")
            val keahlian = intent.getStringExtra("keahlian")
            val studi = intent.getStringExtra("studi")

            etkdnidn.setText(kodeNidn)
            etnmDosen.setText(nama)
            spnJabatan.setSelection(etjabatan.indexOf(jabatan))
            spnGolonganpangkat.setSelection(pangkat.indexOf(golpat))
            if(pendidikan == "S2") rdS2.isChecked = true else rdS3.isChecked = true
            etkeahlian.setText(keahlian)
            etprgstudi.setText(studi)
        }
        etkdnidn.isEnabled = !modeEdit

        btnSimpan.setOnClickListener {
            if("${etkdnidn.text}".isNotEmpty() && "${etnmDosen.text}".isNotEmpty()
                && "${etkeahlian.text}".isNotEmpty() && "${etprgstudi.text}".isNotEmpty() &&
                (rdS2.isChecked || rdS3.isChecked)) {
                val db = Campuss(this@Entri_Data_Dosen)
                db.nidn = "${etkdnidn.text}"
                db.namadosen = "${etnmDosen.text}"
                db.jabatan = spnJabatan.selectedItem as String
                db.golonganpkt = spnGolonganpangkat.selectedItem as String
                db.pendidikan = if(rdS2.isChecked) "S2" else "S3"
                db.keahlian = "${etkeahlian.text}"
                db.prostudi = "${etprgstudi.text}"
                if(if(!modeEdit) db.simpan() else db.ubah("${etkdnidn.text}")) {
                    Toast.makeText(
                        this@Entri_Data_Dosen,
                        "Data Dosen pengampu berhasil disimpan",
                        Toast.LENGTH_SHORT
                    ).show()
                    finish()
                }else
                    Toast.makeText(
                        this@Entri_Data_Dosen,
                        "Data Dosen Pengampu kuliah gagal disimpan",
                        Toast.LENGTH_SHORT
                    ).show()
            }else
                Toast.makeText(
                    this@Entri_Data_Dosen,
                    "Data Dosen Pengampu belum lengkap",
                    Toast.LENGTH_SHORT
                ).show()
        }
    }
}