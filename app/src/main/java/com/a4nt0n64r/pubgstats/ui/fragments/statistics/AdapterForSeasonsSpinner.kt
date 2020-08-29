package com.a4nt0n64r.pubgstats.ui.fragments.statistics

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.a4nt0n64r.pubgstats.R
import com.a4nt0n64r.pubgstats.domain.model.SeasonDB
import kotlinx.android.synthetic.main.spinner_item.view.*

class AdapterForSeasonsSpinner(context: Context, seasons: List<SeasonDB>) :
    ArrayAdapter<SeasonDB>(context, 0, seasons) {

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
        val season = getItem(position)
        val view = recycledView ?: LayoutInflater.from(context).inflate(
            R.layout.spinner_item,
            parent,
            false
        )
        view.spinner_item_text.text = season?.name
        return view
    }
}

