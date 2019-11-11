package com.a4nt0n64r.pubgstats.ui.fragments.list_of_players

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.a4nt0n64r.pubgstats.R
import com.a4nt0n64r.pubgstats.domain.model.PlayerDB
import com.a4nt0n64r.pubgstats.domain.model.PlayerDBUI
import com.a4nt0n64r.pubgstats.ui.ADD_PLAYER
import com.a4nt0n64r.pubgstats.ui.MainActivity
import com.a4nt0n64r.pubgstats.ui.STATISTICS
import com.a4nt0n64r.pubgstats.ui.base.AbstractListOfPlayersPresenter
import com.a4nt0n64r.pubgstats.ui.base.ListOfPlayersFragmentView
import kotlinx.android.synthetic.main.list_of_players_frag_layout.*
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import org.koin.core.KoinComponent
import org.koin.core.get


class ListOfPlayersFragment : MvpAppCompatFragment(), ListOfPlayersFragmentView, KoinComponent {

    @InjectPresenter
    lateinit var presenter: AbstractListOfPlayersPresenter

    @ProvidePresenter
    fun provide(): AbstractListOfPlayersPresenter = get()

    val adapter = PlayersRVAdapter()

    override fun showMinusButton() {
        fab_delPlayer.show()
    }

    override fun hideMinusButton() {
        fab_delPlayer.hide()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.list_of_players_frag_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        my_recycler_view.layoutManager = LinearLayoutManager(activity!!.applicationContext)
        my_recycler_view.adapter = adapter

        //TODO("в первый раз при пустой приложухе игрока не показывает")

        presenter.requestPlayersFromDB()

        my_recycler_view.addOnItemTouchListener(
            RecyclerItemClickListener(
                this@ListOfPlayersFragment.activity!!,
                my_recycler_view,
                object : RecyclerItemClickListener.OnItemClickListener {

                    override fun onItemClick(view: View, position: Int) {
                        presenter.onPlayerTouched(position)
                    }

                    override fun onLongItemClick(view: View?, position: Int) {
                        presenter.onPlayerPressed(position)
                    }
                })
        )

        fab_addPlayer.setOnClickListener {
            presenter.letsAddNewPlayer()
        }

        fab_delPlayer.setOnClickListener {
            presenter.deleteSelectedPlayers()
        }
    }

    override fun showPlayers(players: List<PlayerDBUI>) {
        adapter.setData(players)
    }

    override fun changeFragment(fragId: Int) {
        when (fragId) {
            ADD_PLAYER -> {
                val activity = this@ListOfPlayersFragment.activity as MainActivity
                activity.changeFragment(ADD_PLAYER)
            }
        }
    }

    override fun showPlayerStatistics(player: PlayerDB) {
        val activity = this@ListOfPlayersFragment.activity as MainActivity
        activity.showStatisticsFragmet(player)
    }
}
