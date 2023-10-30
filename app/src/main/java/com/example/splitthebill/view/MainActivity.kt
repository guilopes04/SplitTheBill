package com.example.splitthebill.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.splitthebill.model.Bill
import com.example.splitthebill.model.ConstantTypes
import com.example.splitthebill.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val amb: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private lateinit var marl: ActivityResultLauncher<Intent>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(amb.root)

        marl = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(),
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                Toast.makeText(this,"ok", Toast.LENGTH_SHORT).show()
            }}

        amb.dividirContaBt.setOnClickListener {
            val valor = (amb.valorTotalEt.text.toString()).toDouble()
            val quantidade = (amb.quantidadePessoasEt.text.toString()).toInt()
            val valorPessoa = (valor/quantidade).toString()

            val bill = Bill(
                valorTotal = amb.valorTotalEt.text.toString(),
                quantidadePessoas = amb.quantidadePessoasEt.text.toString(),
                valorPorPessoa = valorPessoa
            )

            val peopleIntent = Intent(this@MainActivity, ListParticipants::class.java)
            peopleIntent.putExtra(ConstantTypes.BILL_PEOPLE, bill)
            startActivity(peopleIntent)
        }
    }
}