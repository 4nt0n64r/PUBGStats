package com.a4nt0n64r.pubgstats.ui.fragments.list_of_players

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.a4nt0n64r.pubgstats.R
import com.a4nt0n64r.pubgstats.domain.model.PlayerDBUI
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter

class PlayersRVAdapter : ListDelegationAdapter<List<PlayerDBUI>>() {

    init {
        delegatesManager.addDelegate(PlayerDelegate())
    }

    fun setData(data: List<PlayerDBUI>) {
        this.items = data
        notifyDataSetChanged()
    }
}

private class PlayerDelegate :
    AbsListItemAdapterDelegate<PlayerDBUI, PlayerDBUI, PlayerDelegate.ViewHolder>() {

    override fun isForViewType(item: PlayerDBUI, items: List<PlayerDBUI>, position: Int): Boolean {
        return item is PlayerDBUI
    }

    override fun onCreateViewHolder(parent: ViewGroup): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.player_name_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(item: PlayerDBUI, viewHolder: ViewHolder, payloads: List<Any>) {
        viewHolder.playerName.text = item.name

        when (item.isSelected) {
            true -> {
                viewHolder.card.setCardBackgroundColor(Color.parseColor("#FFAB00"))// acc
                viewHolder.playerName!!.setTextColor(Color.parseColor("#000000"))// pr dark
            }
            false -> {
                viewHolder.card.setCardBackgroundColor(Color.parseColor("#424242"))// bkg it
                viewHolder.playerName!!.setTextColor(Color.parseColor("#FFAB00"))// acc
            }
        }

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val playerName: TextView = itemView.findViewById(R.id.item_name)
        val card:CardView = itemView.findViewById(R.id.item_background)
    }
}
