package com.a4nt0n64r.pubgstats.ui.fragments.statistics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.a4nt0n64r.pubgstats.R
import com.a4nt0n64r.pubgstats.domain.model.PlayerDB
import com.a4nt0n64r.pubgstats.domain.model.SeasonDB
import com.a4nt0n64r.pubgstats.domain.model.StatisticsItem
import com.a4nt0n64r.pubgstats.ui.ID
import com.a4nt0n64r.pubgstats.ui.NAME
import com.a4nt0n64r.pubgstats.ui.base.AbstractStatisticsPresenter
import com.a4nt0n64r.pubgstats.ui.base.StatisticsFragmentView
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.statistics_frag_layout.*
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import org.koin.core.KoinComponent
import org.koin.core.get

class StatisticsFragment : MvpAppCompatFragment(), StatisticsFragmentView, KoinComponent {

    lateinit var playerId: String
    lateinit var nameOfPlayer: String
    val rvAdapter = StatisticsAdapter()

    @InjectPresenter
    lateinit var presenter: AbstractStatisticsPresenter

    @ProvidePresenter
    fun provide(): AbstractStatisticsPresenter = get()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.statistics_frag_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        stat_data_rv.layoutManager = LinearLayoutManager(activity!!.applicationContext)
        stat_data_rv.adapter = rvAdapter

        getParametersFromBundle()
        setParametersInPresenter()
        setOnNavigationItemListener()

    }

    private fun getParametersFromBundle() {
        val bundle = this.arguments
        if (bundle != null) {
            nameOfPlayer = bundle.getString(NAME)!!
            playerId = bundle.getString(ID)!!
        }
    }

    private fun setParametersInPresenter() {
        presenter.setParameters(
            PlayerDB(nameOfPlayer, playerId),
            getString(R.string.prev_season),
            getString(R.string.current_season)
        )
        presenter.setSeasons()
    }

    private fun setOnNavigationItemListener() {
        val onNavigationItemSelectedListener =
            BottomNavigationView.OnNavigationItemSelectedListener { item ->

                return@OnNavigationItemSelectedListener true
            }

        navigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
    }


    override fun showPlayerName(player: PlayerDB) {
        txt_playerName.text = player.name
    }

    override fun showStatistics(statistics: List<StatisticsItem>) {
        rvAdapter.setData(statistics)
    }

    override fun showSeasons(seasons: List<SeasonDB>) {
        val spAdapter = AdapterForSpinner(context!!, seasons)
        spinner.adapter = spAdapter
        spAdapter.updateView()
    }
}