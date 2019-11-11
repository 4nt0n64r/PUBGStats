package com.a4nt0n64r.pubgstats.ui.fragments.add_player

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.a4nt0n64r.pubgstats.R
import com.a4nt0n64r.pubgstats.ui.LIST_OF_PLAYERS
import com.a4nt0n64r.pubgstats.ui.MainActivity
import com.a4nt0n64r.pubgstats.ui.base.AbstractAddPlayerPresenter
import com.a4nt0n64r.pubgstats.ui.base.AddPlayerFragmentView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.add_player_frag_layout.*
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import org.koin.core.KoinComponent
import org.koin.core.get


class AddPlayerFragment : MvpAppCompatFragment(), AddPlayerFragmentView, KoinComponent {

    @InjectPresenter
    lateinit var presenter: AbstractAddPlayerPresenter

    @ProvidePresenter
    fun provide(): AbstractAddPlayerPresenter = get()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.add_player_frag_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_find.setOnClickListener {
            presenter.requestPlayer(input_login.text.toString())
        }
    }

    override fun changeFragment() {
        val activity = this@AddPlayerFragment.activity as MainActivity
        activity.changeFragment(LIST_OF_PLAYERS)
    }

    override fun showSnackbar(msg: String) {
        val snack = Snackbar.make(parent, msg, Snackbar.LENGTH_LONG)
        val sv = snack.view
        val tv = sv.findViewById<TextView>(R.id.snackbar_text)
        tv.setTextColor(ContextCompat.getColor(context!!, R.color.colorAccent))
        snack.show()
    }

}
