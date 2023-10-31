package com.example.splitthebill.view

import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.splitthebill.R
import com.example.splitthebill.adapter.PersonAdapter
import com.example.splitthebill.databinding.ListParticipantsBinding
import com.example.splitthebill.model.Bill
import com.example.splitthebill.model.ConstantTypes
import com.example.splitthebill.model.ConstantTypes.BILL_PEOPLE
import com.example.splitthebill.model.Person

class ListParticipants : AppCompatActivity() {
    private val almb: ListParticipantsBinding by lazy {
        ListParticipantsBinding.inflate(layoutInflater)
    }

    private var quantidadePessoas = 0
    private var valorPorPessoa = 0.0

    private val personList: MutableList<Person> = mutableListOf()
    private lateinit var carl: ActivityResultLauncher<Intent>
    private lateinit var marl: ActivityResultLauncher<Intent>

    private lateinit var peopleAdapter: PersonAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(almb.root)

        marl = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(),
        ) { resultado ->
            if (resultado.resultCode == RESULT_OK) {
                Toast.makeText(this,"ok", Toast.LENGTH_SHORT).show()
            }}

        var bill = intent.getParcelableExtra<Bill>(BILL_PEOPLE)
        bill?.let { _bill ->
            quantidadePessoas = _bill.numberOfPeople.toInt();
            valorPorPessoa = _bill.valuePerPerson.toDouble()
        }

        fillContactList()
        peopleAdapter = PersonAdapter(this, personList)
        almb.peopleLv.adapter = peopleAdapter

        carl = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(),
        ) { res ->
            if (res.resultCode == RESULT_OK) {
                val person = res.data?.getParcelableExtra<Person>(ConstantTypes.EXTRA_PERSON)
                person?.let { _person ->
                    if (personList.any{ it.id == _person.id}){
                        val position = personList.indexOfFirst { it.id == _person.id }
                        personList[position] = _person
                    } else {
                        personList.add(_person)
                    }
                    peopleAdapter.notifyDataSetChanged()
                }
            }
        }

        registerForContextMenu(almb.peopleLv)

        almb.peopleLv.onItemClickListener = object: AdapterView.OnItemClickListener {
            override fun onItemClick(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val person = personList[position]
                val personIntent = Intent(this@ListParticipants, PersonActivity::class.java)
                personIntent.putExtra(ConstantTypes.EXTRA_PERSON, person)
                personIntent.putExtra(ConstantTypes.VIEW_PERSON, true)
                startActivity(personIntent)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.addPersonMi -> {
                carl.launch(Intent(this, PersonActivity::class.java))
                true
            }

            R.id.changeBillMi -> {
                marl.launch(Intent(this, MainActivity::class.java))
                true
            }
            else -> { false }
        }
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        menuInflater.inflate(R.menu.menu_changes_main, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val position = (item.menuInfo as AdapterView.AdapterContextMenuInfo).position
        return when(item.itemId) {
            R.id.removePersonMi -> {
                personList.removeAt(position)
                peopleAdapter.notifyDataSetChanged()
                Toast.makeText(this, "Pessoa removida com sucesso!", Toast.LENGTH_SHORT).show()
                true
            }

            R.id.editPersonMi -> {
                val person = personList[position]
                val personIntent = Intent(this, PersonActivity::class.java)
                personIntent.putExtra(ConstantTypes.EXTRA_PERSON, person)
                personIntent.putExtra(ConstantTypes.VIEW_PERSON, false)
                carl.launch(personIntent)
                true
            }

            else -> { false }
        }
    }

    private fun fillContactList() {
        for (i in 1 ..quantidadePessoas.toInt()){
            personList.add(
                Person(
                    id = i,
                    name = "$i",
                    valorPago = "0.00",
                    compras = "",
                    valorAPagarAutomatico = "$valorPorPessoa",
                    valorAPagarFixo = "$valorPorPessoa",
                )
            )
        }
    }
}