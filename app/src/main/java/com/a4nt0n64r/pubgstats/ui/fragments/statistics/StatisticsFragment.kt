package com.a4nt0n64r.pubgstats.ui.fragments.statistics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.LinearLayoutManager
import com.a4nt0n64r.pubgstats.R
import com.a4nt0n64r.pubgstats.domain.model.PlayerDB
import com.a4nt0n64r.pubgstats.domain.model.StatisticsItem
import com.a4nt0n64r.pubgstats.ui.*
import com.a4nt0n64r.pubgstats.ui.base.AbstractStatisticsPresenter
import com.a4nt0n64r.pubgstats.ui.base.StatisticsFragmentView
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.add_player_frag_layout.*
import kotlinx.android.synthetic.main.statistics_frag_layout.*
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import org.koin.core.KoinComponent
import org.koin.core.get

class StatisticsFragment : MvpAppCompatFragment(), StatisticsFragmentView, KoinComponent {

    lateinit var playerId: String
    lateinit var nameOfPlayer: String
    lateinit var playerRegion: String
    lateinit var playerPlatform: String
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
        showPlayerName(nameOfPlayer)
        setOnNavigationItemListener()

        presenter.setSeasons()

    }


    private fun getParametersFromBundle() {
        val bundle = this.arguments
        if (bundle != null) {
            nameOfPlayer = bundle.getString(NAME)!!
            playerId = bundle.getString(ID)!!
            playerRegion = bundle.getString(REGION)!!
            playerPlatform = bundle.getString(PLATFORM)!!
        }
    }

    private fun setParametersInPresenter() {
        presenter.setPlayerParameters(
            PlayerDB(nameOfPlayer, playerId, playerRegion, playerPlatform)
        )
        presenter.setSeasonParameters(
            getString(R.string.prev_season),
            getString(R.string.current_season)
        )
        presenter.setRegimeParameters(
            getString(R.string.tpp),
            getString(R.string.fpp)
        )
    }


    private fun setOnNavigationItemListener() {
        val onNavigationItemSelectedListener =
            BottomNavigationView.OnNavigationItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.navigation_solo -> {
                        when (regime_spinner.selectedItem.toString()) {
                            getString(R.string.tpp) -> {
                                presenter.setSoloTpp()
                            }
                            getString(R.string.fpp) -> {
                                presenter.setSoloFpp()
                            }
                        }
                    }
                    R.id.navigation_duo -> {
                        when (regime_spinner.selectedItem.toString()) {
                            getString(R.string.tpp) -> {
                                presenter.setDuoTpp()
                            }
                            getString(R.string.fpp) -> {
                                presenter.setDuoFpp()
                            }
                        }
                    }
                    R.id.navigation_squad -> {
                        when (regime_spinner.selectedItem.toString()) {
                            getString(R.string.tpp) -> {
                                presenter.setSquadTpp()
                            }
                            getString(R.string.fpp) -> {
                                presenter.setSquadFpp()
                            }
                        }
                    }
                }
                return@OnNavigationItemSelectedListener true
            }

        navigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
    }

    override fun showPlayerName(name: String) {
        txt_playerName.text = name
    }

    override fun showStatistics(statistics: List<StatisticsItem>) {
        rvAdapter.setData(statistics)
    }

    //код оставлен для следующего патча
//    override fun showSeasons(seasons: List<SeasonDB>) {
//        val spAdapter = AdapterForSeasonsSpinner(context!!, seasons)
//        season_spinner.adapter = spAdapter
//        spAdapter.updateView()
//    }

    override fun showRegimes(tpp: String, fpp: String) {
        val spAdapter = AdapterForRegimeSpinner(context!!, tpp, fpp)
        regime_spinner.adapter = spAdapter
        spAdapter.updateView()
    }

    //переделать слушателя на спиннере
    override fun initiateStatisticsLoading() {
        presenter.setStatistics()
        setSpinnerOnItemSelectedListener()
    }

    private fun setSpinnerOnItemSelectedListener() {
        regime_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when (regime_spinner.selectedItem.toString()) {
                    getString(R.string.tpp) -> {
                        navigation.setSelectedItemId(R.id.navigation_solo)
                    }
                    getString(R.string.fpp) -> {
                        navigation.setSelectedItemId(R.id.navigation_solo)
                    }
                }
            }
        }
    }

    override fun showLoading() {
        loading_screen_statistics.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        loading_screen_statistics.visibility = View.INVISIBLE
    }
}




