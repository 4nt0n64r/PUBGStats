package com.a4nt0n64r.pubgstats.ui.fragments.list_of_players

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.a4nt0n64r.pubgstats.R
import com.a4nt0n64r.pubgstats.domain.model.PlayerUI
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter

class PlayersRVAdapter : ListDelegationAdapter<List<PlayerUI>>() {

    init {
        delegatesManager.addDelegate(PlayerDelegate())
    }

    fun setData(data: List<PlayerUI>) {
        this.items = data
        notifyDataSetChanged()
    }
}

private class PlayerDelegate :
    AbsListItemAdapterDelegate<PlayerUI, PlayerUI, PlayerDelegate.ViewHolder>() {

    override fun isForViewType(item: PlayerUI, items: List<PlayerUI>, position: Int): Boolean {
        return item is PlayerUI
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

    override fun onBindViewHolder(item: PlayerUI, viewHolder: ViewHolder, payloads: List<Any>) {

        viewHolder.playerName.text = item.name
        viewHolder.playerPlatform.text = item.platform
        viewHolder.playerRegion.text = item.region

        when (item.isSelected) {
            true -> {
                viewHolder.card.setCardBackgroundColor(Color.parseColor("#FFAB00"))// acc
                viewHolder.playerName.setTextColor(Color.parseColor("#000000"))// pr dark
                viewHolder.playerRegion.setTextColor(Color.parseColor("#000000"))// pr dark
                viewHolder.playerPlatform.setTextColor(Color.parseColor("#000000"))// pr dark
            }
            false -> {
                viewHolder.card.setCardBackgroundColor(Color.parseColor("#424242"))// bkg it
                viewHolder.playerName.setTextColor(Color.parseColor("#FFAB00"))// acc
                viewHolder.playerRegion.setTextColor(Color.parseColor("#FFAB00"))// acc
                viewHolder.playerPlatform.setTextColor(Color.parseColor("#FFAB00"))// acc
            }
        }

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val playerName: TextView = itemView.findViewById(R.id.item_name)
        val card: CardView = itemView.findViewById(R.id.item_background)
        val playerRegion: TextView = itemView.findViewById(R.id.item_region)
        val playerPlatform: TextView = itemView.findViewById(R.id.item_platform)
    }
}
