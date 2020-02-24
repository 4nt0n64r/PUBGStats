package com.a4nt0n64r.pubgstats.ui

import android.Manifest
import android.os.Bundle
import androidx.core.app.ActivityCompat
import com.a4nt0n64r.pubgstats.R
import com.a4nt0n64r.pubgstats.domain.model.PlayerDB
import com.a4nt0n64r.pubgstats.ui.base.AbstractActivityPresenter
import com.a4nt0n64r.pubgstats.ui.base.ActivityView
import com.a4nt0n64r.pubgstats.ui.fragments.add_player.AddPlayerFragment
import com.a4nt0n64r.pubgstats.ui.fragments.list_of_players.ListOfPlayersFragment
import com.a4nt0n64r.pubgstats.ui.fragments.statistics.StatisticsFragment
import moxy.MvpAppCompatActivity
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import org.koin.core.KoinComponent
import org.koin.core.get


const val ADD_PLAYER = 0
const val STATISTICS = 1
const val LIST_OF_PLAYERS = 2
const val FRAGMENT_CHANGED = "fragment_changed"
const val ADD_TO_BACKSTACK = "backstack"

const val NAME = "name"
const val ID = "id"

const val REQUEST_PERMISSION_CODE = 0


class MainActivity : MvpAppCompatActivity(), ActivityView, KoinComponent {

    private val TAG = "MainActivity"

    @InjectPresenter
    lateinit var presenter: AbstractActivityPresenter

    @ProvidePresenter
    fun provide(): AbstractActivityPresenter = get()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter.loadFragment(LIST_OF_PLAYERS)

    }

    // TODO("добавить колбэк что фрагментов не осталось и вырубить приложение")
    // TODO("когда поворачиваем экран он откатывает фрагмент статки на меню")
    // Метод popBackStack() удаляет транзакцию с верхушки бэкстэка, возвращает true,
    // если бэкстэк хранил хотя бы одну транзакцию.
    // TODO(пермишшены!)

    override fun drawFragment(fragmentId: Int) {
        when (fragmentId) {
            ADD_PLAYER -> {
                displayAddPlayerFragment()
            }
            LIST_OF_PLAYERS -> {
                displayListOfPlayersFragment()
            }
        }
    }

    private fun displayListOfPlayersFragment() {
        supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.frame,
                ListOfPlayersFragment(), FRAGMENT_CHANGED
            )
            .addToBackStack(ADD_TO_BACKSTACK)
            .commit()
    }

    private fun displayAddPlayerFragment() {
        supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.frame,
                AddPlayerFragment(), FRAGMENT_CHANGED
            )
            .addToBackStack(ADD_TO_BACKSTACK)
            .commit()
    }

    private fun displayStatisticsFragment(fragment: StatisticsFragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.frame,
                fragment, FRAGMENT_CHANGED
            )
            .addToBackStack(ADD_TO_BACKSTACK)
            .commit()
    }

    override fun showStatisticsFragmet(player: PlayerDB) {


        //TODO("переработать чтобы код выглядел лучше")
        val statisticsFragment = StatisticsFragment()

        val bundle = Bundle()
        bundle.putString(NAME, player.name)
        bundle.putString(ID, player.id)
        statisticsFragment.arguments = bundle

        //Нужно передать аргументы
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


    //Вызываем у ActivityPresenter метод onDestroy, чтобы избежать утечек контекста и прочих неприятностей.
    public override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

}





