package com.example.splitthebill.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.splitthebill.databinding.PersonBillDetailBinding
import com.example.splitthebill.model.ConstantTypes
import com.example.splitthebill.model.ItemBill
import com.example.splitthebill.model.Person
import kotlin.random.Random

class PersonActivity : AppCompatActivity() {

    private val apb: PersonBillDetailBinding by lazy {
        PersonBillDetailBinding.inflate(layoutInflater)
    }

    private var valorAPagarAtualizado = 0.0
    private var valorFixo = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(apb.root)

        val receivedInfo = intent.getParcelableExtra<Person>(ConstantTypes.EXTRA_PERSON)
        receivedInfo?.let { _receivedInfo ->
            with(apb) {
                with(_receivedInfo){
                    nameEt.setText(name)
                    nameCompra1.setText(compra1.name)
                    descCompra1.setText(compra1.desc)
                    valorCompra1.setText(compra1.value)
                    nameCompra2.setText(compra2.name)
                    descCompra2.setText(compra2.desc)
                    valorCompra2.setText(compra2.value)
                    nameCompra3.setText(compra3.name)
                    descCompra3.setText(compra3.desc)
                    valorCompra3.setText(compra3.value)
                    dividaEt.setText(valorAPagarAutomatico)
                }
            }
            valorFixo = _receivedInfo.valorAPagarFixo.toDouble()
        }

        val viewPerson = intent.getBooleanExtra(ConstantTypes.VIEW_PERSON, false)
        if (viewPerson) {
            with(apb){
                nameEt.isEnabled = false
                nameCompra1.isEnabled = false
                descCompra1.isEnabled = false
                valorCompra1.isEnabled = false
                nameCompra2.isEnabled = false
                descCompra2.isEnabled = false
                valorCompra2.isEnabled = false
                nameCompra3.isEnabled = false
                descCompra3.isEnabled = false
                valorCompra3.isEnabled = false
                saveBt.visibility = View.GONE
            }
        }

        apb.saveBt.setOnClickListener{
            var itemsSoma = 0.0
            var itemDesc1 = apb.descCompra1.text.toString()
            var itemValor1 = "0"
            if(itemDesc1 !== "" || itemDesc1 !== null){
                itemValor1 = apb.valorCompra1.text.toString()
            }
            var itemDesc2 = apb.descCompra2.text.toString()
            var itemValor2 = "0"
            if(itemDesc2 !== "" || itemDesc2 !== null){
                itemValor2 = apb.valorCompra2.text.toString()
            }
            var itemDesc3 = apb.descCompra3.text.toString()
            var itemValor3 = "0"
            if(itemDesc3 !== "" || itemDesc3 !== null){
                itemValor2 = apb.valorCompra3.text.toString()
            }
            itemsSoma = itemValor1.toDouble() + itemValor2.toDouble() + itemValor3.toDouble()
            diferenca(itemsSoma, valorFixo)

            var compra1 = ItemBill(
                name = apb.nameCompra1.text.toString(),
                desc = apb.descCompra1.text.toString(),
                value = itemValor1
            )

            var compra2 = ItemBill(
                name = apb.nameCompra2.text.toString(),
                desc = apb.descCompra2.text.toString(),
                value = itemValor2
            )

            var compra3 = ItemBill(
                name = apb.nameCompra3.text.toString(),
                desc = apb.descCompra3.text.toString(),
                value = itemValor3
            )

            val person = Person(
                id = receivedInfo?.id?: Random(System.currentTimeMillis()).nextInt(),
                name = apb.nameEt.text.toString(),
                valorPago = itemsSoma.toString(),
                compra1 = compra1,
                compra2 = compra2,
                compra3 = compra3,
                valorAPagarAutomatico = valorAPagarAtualizado.toString(),
                valorAPagarFixo = valorFixo.toString()
            )

            val resultIntent = Intent()
            resultIntent.putExtra(ConstantTypes.EXTRA_PERSON, person)
            setResult(RESULT_OK, resultIntent)
            finish()
        }
    }

    fun diferenca(valorPago: Double, valorAPagar: Double){
        var diferenca = valorAPagar - valorPago

        valorAPagarAtualizado = diferenca
    }
}