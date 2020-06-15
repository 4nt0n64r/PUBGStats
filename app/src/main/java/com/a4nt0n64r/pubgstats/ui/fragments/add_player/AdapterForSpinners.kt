package com.a4nt0n64r.pubgstats.ui.fragments.add_player

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.a4nt0n64r.pubgstats.R
import kotlinx.android.synthetic.main.spinner_item.view.*


class AdapterForSpinner(context: Context, items: Array<String>) :
    ArrayAdapter<String>(context, 0, items) {

    fun updateView() {
        notifyDataSetChanged()
    }

    override fun getView(position: Int, recycledView: View?, parent: ViewGroup): View {
        return this.createView(position, recycledView, parent)
    }

    override fun getDropDownView(position: Int, recycledView: View?, parent: ViewGroup): View {
        return this.createView(position, recycledView, parent)
    }

    private fun createView(position: Int, recycledView: View?, parent: ViewGroup): View {
        val item = getItem(position)
        val view = recycledView ?: LayoutInflater.from(context).inflate(
            R.layout.spinner_item,
            parent,
            false
        )
        view.spinner_item_text.text = item
        return view
    }
}