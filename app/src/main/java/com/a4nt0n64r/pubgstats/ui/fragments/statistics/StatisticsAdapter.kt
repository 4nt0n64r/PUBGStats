package com.a4nt0n64r.pubgstats.ui.fragments.statistics

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.a4nt0n64r.pubgstats.R
import com.a4nt0n64r.pubgstats.domain.model.StatisticsItem
import com.a4nt0n64r.pubgstats.domain.model.StatisticsHeader
import com.a4nt0n64r.pubgstats.domain.model.StatisticsPoints
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter


class StatisticsAdapter : ListDelegationAdapter<List<StatisticsItem>>() {

    init {
        delegatesManager.addDelegate(StatElemDelegate())
        delegatesManager.addDelegate(DescriptionElemDelegate())
    }

    fun setData(data: List<StatisticsItem>) {
        this.items = data
        notifyDataSetChanged()
    }
}

private class StatElemDelegate :
    AbsListItemAdapterDelegate<StatisticsPoints, StatisticsItem, StatElemDelegate.ViewHolder>() {

    override fun isForViewType(item: StatisticsItem, items: List<StatisticsItem>, position: Int): Boolean {
        return item is StatisticsPoints
    }

    override fun onCreateViewHolder(parent: ViewGroup): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.statistics_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(item: StatisticsPoints, viewHolder: ViewHolder, payloads: List<Any>) {
        viewHolder.statText.text = item.text
        viewHolder.statPoints.text = item.points
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val statText: TextView = itemView.findViewById(R.id.stat_text)
        val statPoints: TextView = itemView.findViewById(R.id.stat_points)
    }
}

private class DescriptionElemDelegate :
    AbsListItemAdapterDelegate<StatisticsHeader, StatisticsItem, DescriptionElemDelegate.ViewHolder>() {

    override fun isForViewType(item: StatisticsItem, items: List<StatisticsItem>, position: Int): Boolean {
        return item is StatisticsHeader
    }

    override fun onCreateViewHolder(parent: ViewGroup): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.stat_description_layout,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(item: StatisticsHeader, viewHolder: ViewHolder, payloads: List<Any>) {
        viewHolder.descText.text = item.text
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val descText: TextView = itemView.findViewById(R.id.description_tv)
    }
}
