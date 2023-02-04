package com.vholodynskyi.assignment.presentation.contactslist

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vholodynskyi.assignment.databinding.ItemContactListBinding
import com.vholodynskyi.assignment.data.db.contacts.DbContact

class ContactAdapter (
    private val context: Activity,
    private val onItemClicked: ItemClick
) : RecyclerView.Adapter<ViewHolder>() {

    var items: List<DbContact> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder {
        return ViewHolder(
            ItemContactListBinding.inflate(LayoutInflater.from(context), parent, false)
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        with(holder.binding) {
            text.text = item.firstName+ "\n"+item.email.toString()
            root.setOnClickListener {
                onItemClicked(item.id)
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}

class ViewHolder (val binding: ItemContactListBinding) : RecyclerView.ViewHolder(binding.root)

typealias ItemClick = (Int) -> Unit