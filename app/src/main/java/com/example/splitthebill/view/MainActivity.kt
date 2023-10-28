package com.example.splitthebill.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.splitthebill.R
import com.example.splitthebill.model.Bill
import com.example.splitthebill.model.ConstantTypes

class MainActivity : AppCompatActivity() {

    private lateinit var marl: ActivityResultLauncher<Intent>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        marl = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(),
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                Toast.makeText(this,"ok", Toast.LENGTH_SHORT).show()
            }}

        val divirContaBt: Button = findViewById(R.id.dividirContaBt)

        divirContaBt.setOnClickListener {
            val valor = R.id.valorTotalEt.toString()
            val quantidade = R.id.quantidadePessoasEt.toString()
            val valorPessoa = (valor.toDouble()/quantidade.toInt()).toString()

            val bill = Bill(
                valorTotal = valor,
                quantidadePessoas = quantidade,
                valorPorPessoa = valorPessoa
            )

            val participantsIntent = Intent(this@MainActivity, ListParticipants::class.java)
            participantsIntent.putExtra(ConstantTypes.BILL_PEOPLE, bill)
            startActivity(participantsIntent)
        }
    }
}