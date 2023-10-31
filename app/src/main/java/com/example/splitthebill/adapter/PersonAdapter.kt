package com.example.splitthebill.adapter

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.splitthebill.R
import com.example.splitthebill.databinding.PersonComponentBinding
import com.example.splitthebill.model.Person

class PersonAdapter (
        context: Context,
        private val personList:
        MutableList<Person>
        ): ArrayAdapter<Person>(context, R.layout.person_component, personList) {

        private data class PeopleComponentHolder(val nameTv: TextView, val valorTv: TextView)
        private lateinit var pcb: PersonComponentBinding

        constructor(parcel: Parcel) : this(
            TODO("context"),
            TODO("peopleList")
        ) {}

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val contact = personList[position]
            var personComponentView = convertView

            if(personComponentView == null) {
                pcb = PersonComponentBinding.inflate(
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater,
                    parent,
                    false
                )

                personComponentView = pcb.root
                val peopleComponentHolder = PeopleComponentHolder(pcb.nameTv, pcb.valorpagoTv)
                personComponentView.tag = peopleComponentHolder
            }

            with (personComponentView.tag as PeopleComponentHolder) {
                nameTv.text = contact.name
                valorTv.text = contact.valorPago
            }

            return personComponentView
        }

        companion object CREATOR : Parcelable.Creator<PersonAdapter> {
            override fun createFromParcel(parcel: Parcel): PersonAdapter {
                return PersonAdapter(parcel)
            }

            override fun newArray(size: Int): Array<PersonAdapter?> {
                return arrayOfNulls(size)
            }
        }

    }
