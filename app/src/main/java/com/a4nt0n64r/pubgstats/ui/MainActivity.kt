package com.a4nt0n64r.pubgstats.ui

import android.os.Bundle
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

    //TODO("добавить колбэк что фрагментов не осталось и вырубить приложение")
    //TODO("когда поворачиваем экран он откатывает фрагмент статки на меню")
    //Метод popBackStack() удаляет транзакцию с верхушки бэкстэка, возвращает true,
    // если бэкстэк хранил хотя бы одну транзакцию.
    override public fun changeFragment(fragmentId: Int) {
        when (fragmentId) {
            ADD_PLAYER -> {
                supportFragmentManager
                    .beginTransaction()
                    .replace(
                        R.id.frame,
                        AddPlayerFragment(), FRAGMENT_CHANGED
                    )
                    .addToBackStack(ADD_TO_BACKSTACK)
                    .commit()
            }
            LIST_OF_PLAYERS -> {
                supportFragmentManager
                    .beginTransaction()
                    .replace(
                        R.id.frame,
                        ListOfPlayersFragment(), FRAGMENT_CHANGED
                    )
                    .addToBackStack(ADD_TO_BACKSTACK)
                    .commit()
            }

        }
    }

    override fun showStatisticsFragmet(player: PlayerDB) {

        val currentFrag = StatisticsFragment()

        val bundle = Bundle()
        bundle.putString(NAME, player.name)
        bundle.putString(ID, player.id)
        currentFrag.arguments = bundle

        supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.frame,
                currentFrag, FRAGMENT_CHANGED
            )
            .addToBackStack(ADD_TO_BACKSTACK)
            .commit()
    }


    //Вызываем у ActivityPresenter метод onDestroy, чтобы избежать утечек контекста и прочих неприятностей.
    public override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

}





