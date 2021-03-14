package com.a4nt0n64r.pubgstats.ui.fragments.listOfPlayers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.a4nt0n64r.pubgstats.R
import com.a4nt0n64r.pubgstats.domain.model.PlayerDB
import com.a4nt0n64r.pubgstats.domain.model.PlayerUI
import com.a4nt0n64r.pubgstats.ui.ADD_PLAYER
import com.a4nt0n64r.pubgstats.ui.MainActivity
import com.a4nt0n64r.pubgstats.ui.base.AbstractListOfPlayersPresenter
import com.a4nt0n64r.pubgstats.ui.base.ListOfPlayersFragmentView
import com.a4nt0n64r.pubgstats.ui.fragments.addPlayer.NO_INTERNET
import com.google.android.material.snackbar.Snackbar
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

    var selectionMode = false

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

        presenter.requestPlayersFromDB()

        setUpRVListener()

        fab_addPlayer.setOnClickListener {
            presenter.letsAddNewPlayer()
        }

        fab_delPlayer.setOnClickListener {
            presenter.deleteSelectedPlayers()
        }
    }

    private fun setUpRVListener() {
        my_recycler_view.addOnItemTouchListener(
            RecyclerItemClickListener(
                this@ListOfPlayersFragment.activity!!,
                my_recycler_view,
                object : RecyclerItemClickListener.OnItemClickListener {

                    override fun onItemClick(view: View, position: Int) {
                        if (selectionMode){
                            presenter.onPlayerPressed(position)
                        }else{
                            presenter.onPlayerTouched(position, checkConnection())
                        }
                    }

                    override fun onLongItemClick(view: View?, position: Int) {
                        presenter.onPlayerPressed(position)
                        selectionMode = true
                    }
                })
        )
    }

    override fun selectionModeOff() {
        selectionMode = false
    }

    override fun onResume() {
        super.onResume()
        presenter.requestPlayersFromDB()
    }

    override fun showPlayers(players: List<PlayerUI>) {
        adapter.setData(players)
    }

    override fun changeFragment(fragId: Int) {
        when (fragId) {
            ADD_PLAYER -> {
                val activity = this@ListOfPlayersFragment.activity as MainActivity
                activity.drawAddPlayerFragment()
            }
        }
    }

    override fun showErrorTextAndImage() {
        layout_error_list.visibility = VISIBLE
    }

    override fun hideErrorTextAndImage() {
        layout_error_list.visibility = INVISIBLE
    }

    override fun showPlayerStatistics(player: PlayerDB) {
        val activity = this@ListOfPlayersFragment.activity as MainActivity
        activity.showStatisticsFragmet(player)
    }

    fun checkConnection(): Boolean {
        val activity = this@ListOfPlayersFragment.activity as MainActivity
        return activity.connectionAvailabile
    }

    override fun showSnackbar(messageId: Int) {
        var message = ""
        when (messageId) {
            NO_INTERNET -> {
                message = getString(R.string.no_internet_connection)
            }
        }
        val snackbar = Snackbar.make(constr, message, Snackbar.LENGTH_LONG)
        val snackView = snackbar.view
        snackView.setBackgroundColor(ContextCompat.getColor(context!!, R.color.black))
        val tv = snackView.findViewById<TextView>(R.id.snackbar_text)
        tv.setTextColor(ContextCompat.getColor(context!!, R.color.color_yellow_background))
        snackbar.show()
    }
}
