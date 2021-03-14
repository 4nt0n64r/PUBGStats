package com.a4nt0n64r.pubgstats.ui

import android.Manifest
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.WindowManager
import androidx.core.app.ActivityCompat
import com.a4nt0n64r.pubgstats.R
import com.a4nt0n64r.pubgstats.domain.model.PlayerDB
import com.a4nt0n64r.pubgstats.ui.base.AbstractActivityPresenter
import com.a4nt0n64r.pubgstats.ui.base.ActivityView
import com.a4nt0n64r.pubgstats.ui.fragments.addPlayer.AddPlayerFragment
import com.a4nt0n64r.pubgstats.ui.fragments.listOfPlayers.ListOfPlayersFragment
import com.a4nt0n64r.pubgstats.ui.fragments.statistics.StatisticsFragment
import moxy.MvpAppCompatActivity
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import org.koin.core.KoinComponent
import org.koin.core.get


const val ADD_PLAYER = 0

const val FRAGMENT_CHANGED = "fragment_changed"

const val ADD_PLAYER_BACKSTACK = "add"
const val STATISTICS_BACKSTACK = "stat"


const val NAME = "name"
const val ID = "id"
const val REGION = "region"
const val PLATFORM = "platform"

const val REQUEST_PERMISSION_CODE = 0


class MainActivity : MvpAppCompatActivity(), ActivityView, KoinComponent,
    ConnectivityReceiver.ConnectivityReceiverListener {

    var connectionAvailabile: Boolean = true

    @InjectPresenter
    lateinit var presenter: AbstractActivityPresenter

    @ProvidePresenter
    fun provide(): AbstractActivityPresenter = get()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_main)
        registerReceiver(
            ConnectivityReceiver(),
            IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        )

        presenter.loadListOfPlayersFragment()

    }

    override fun drawAddPlayerFragment() {
        displayAddPlayerFragment()
    }

    override fun drawListOfPlayersFragment() {
        displayListOfPlayersFragment()
    }

    private fun displayListOfPlayersFragment() {
        supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.frame,
                ListOfPlayersFragment(), FRAGMENT_CHANGED
            )
            .commit()
    }

    private fun displayAddPlayerFragment() {
        supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.frame,
                AddPlayerFragment(), FRAGMENT_CHANGED
            )
            .addToBackStack(ADD_PLAYER_BACKSTACK)
            .commit()
    }

    private fun displayStatisticsFragment(fragment: StatisticsFragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.frame,
                fragment, FRAGMENT_CHANGED
            )
            .addToBackStack(STATISTICS_BACKSTACK)
            .commit()
    }

    override fun showStatisticsFragmet(player: PlayerDB) {

        //TODO("переработать чтобы код выглядел лучше")
        val statisticsFragment = StatisticsFragment()

        val bundle = Bundle()
        bundle.putString(NAME, player.name)
        bundle.putString(ID, player.id)
        bundle.putString(PLATFORM, player.platform)
        bundle.putString(REGION, player.region)

        statisticsFragment.arguments = bundle

        displayStatisticsFragment(statisticsFragment)
    }


    override fun requestInternetPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.INTERNET),
            REQUEST_PERMISSION_CODE
        )
    }

    override fun requestStoragePermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ),
            REQUEST_PERMISSION_CODE
        )
    }

    override fun onResume() {
        super.onResume()

        ConnectivityReceiver.connectivityReceiverListener = this
    }

    public override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        connectionAvailabile = isConnected
    }
}





