package com.a4nt0n64r.pubgstats.ui.fragments.list_of_players

import android.graphics.Color
import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
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
                R.layout.player_card,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(item: PlayerUI, viewHolder: ViewHolder, payloads: List<Any>) {

        viewHolder.playerName.text = item.name
        viewHolder.playerRegion.text = item.region

        when (item.platform) {
            "steam" -> {
                viewHolder.playerPlatform.setImageResource(R.drawable.computer_icon)
            }
            "psn" -> {
                viewHolder.playerPlatform.setImageResource(R.drawable.playstation_icon)
            }
            "xbox" -> {
                viewHolder.playerPlatform.setImageResource(R.drawable.xbox_icon)
            }
        }

        when (item.region) {
            "pc-ru" -> {
                viewHolder.playerRegion.text = "Russia"
            }
            "pc-as" -> {
                viewHolder.playerRegion.text = "Asia"
            }
            "pc-eu" -> {
                viewHolder.playerRegion.text = "Europe"
            }
            "pc-jp" -> {
                viewHolder.playerRegion.text = "Japan"
            }
            "pc-krjp" -> {
                viewHolder.playerRegion.text = "Korea/Japan"
            }
            "pc-na" -> {
                viewHolder.playerRegion.text = "North America"
            }
            "pc-oc" -> {
                viewHolder.playerRegion.text = "Oceania"
            }
            "pc-sa" -> {
                viewHolder.playerRegion.text = "South America"
            }
            "pc-sea" -> {
                viewHolder.playerRegion.text = "South East Asia"
            }
            "psn-as" -> {
                viewHolder.playerRegion.text = "Asia"
            }
            "psn-eu" -> {
                viewHolder.playerRegion.text = "Europe"
            }
            "psn-na" -> {
                viewHolder.playerRegion.text = "North America"
            }
            "psn-oc" -> {
                viewHolder.playerRegion.text = "Oceania"
            }
            "xbox-as" -> {
                viewHolder.playerRegion.text = "Asia"
            }
            "xbox-eu" -> {
                viewHolder.playerRegion.text = "Europe"
            }
            "xbox-na" -> {
                viewHolder.playerRegion.text = "North America"
            }
            "xbox-oc" -> {
                viewHolder.playerRegion.text = "Oceania"
            }
            "xbox-sa" -> {
                viewHolder.playerRegion.text = "South America"
            }
        }

        when (item.isSelected) {
            true -> {
                viewHolder.card.setCardBackgroundColor(Color.parseColor("#FFAB00"))// acc
                viewHolder.playerName.setTextColor(Color.parseColor("#000000"))// pr dark
                viewHolder.playerRegion.setTextColor(Color.parseColor("#000000"))// pr dark
                viewHolder.playerPlatform.setColorFilter(
                    Color.parseColor(
                        "#000000"
                    ),
                    PorterDuff.Mode.SRC_ATOP
                )// pr dark
            }
            false -> {
                viewHolder.card.setCardBackgroundColor(Color.parseColor("#424242"))// bkg it
                viewHolder.playerName.setTextColor(Color.parseColor("#FFAB00"))// acc
                viewHolder.playerRegion.setTextColor(Color.parseColor("#FFAB00"))// acc
                viewHolder.playerPlatform.setColorFilter(
                    Color.parseColor(
                        "#FFFFFF"
                    ),
                    PorterDuff.Mode.SRC_ATOP
                ) // acc
            }
        }


    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val playerName = itemView.findViewById<TextView>(R.id.player_name)
        val card = itemView.findViewById<CardView>(R.id.item_background)
        val playerRegion = itemView.findViewById<TextView>(R.id.region_tv)
        val playerPlatform = itemView.findViewById<ImageView>(R.id.platform_image)
    }
}
