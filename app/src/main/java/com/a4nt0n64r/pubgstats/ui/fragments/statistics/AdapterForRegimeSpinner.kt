package com.a4nt0n64r.pubgstats.ui.fragments.statistics

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.a4nt0n64r.pubgstats.R
import com.a4nt0n64r.pubgstats.domain.model.SeasonDB
import kotlinx.android.synthetic.main.spinner_item.view.*

class AdapterForRegimeSpinner (context: Context, tpp:String, fpp: String) :

    ArrayAdapter<String>(context, 0, arrayOf(tpp,fpp)) {

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
        val regime = getItem(position)
        val view = recycledView ?: LayoutInflater.from(context).inflate(
            R.layout.spinner_item,
            parent,
            false
        )
        view.spinner_item_text.text = regime
        return view
    }
}
