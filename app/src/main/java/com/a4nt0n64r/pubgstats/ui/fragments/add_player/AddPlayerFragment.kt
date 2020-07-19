package com.a4nt0n64r.pubgstats.ui.fragments.add_player

import android.os.Bundle

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

const val NOT_FOUND = 0
const val EMPTY_NAME = 1


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

        requestInternetPermissionFromFragment()

        setUpTextViews()

        setUpSpinners()

        btn_find.setOnClickListener {
            presenter.requestPlayer(
                input_login.text.toString(),
                region_spinner.selectedItem.toString(),
                platform_spinner.selectedItem.toString()
            )
        }
    }

    private fun setUpTextViews() {
        platform_tv.text = getString(R.string.platform)
        region_tv.text = getString(R.string.region)
    }

    private fun setUpSpinners() {
        val activity = this@AddPlayerFragment.activity as MainActivity
        setUpRegions(activity)
        setUpPlatforms(activity)
    }

    private fun setUpRegions(activity: MainActivity) {
        val regions = resources.getStringArray(R.array.region)
        val regionAdapter = AdapterForSpinner(activity, regions)
        region_spinner.adapter = regionAdapter
    }

    private fun setUpPlatforms(activity: MainActivity) {
        val platforms = resources.getStringArray(R.array.platform)
        val platformAdapter = AdapterForSpinner(activity, platforms)
        platform_spinner.adapter = platformAdapter
    }

    override fun requestInternetPermissionFromFragment() {
        val activity = this@AddPlayerFragment.activity as MainActivity
        activity.requestInternetPermission()
    }

    override fun changeFragment() {
        val activity = this@AddPlayerFragment.activity as MainActivity
        activity.drawFragment(LIST_OF_PLAYERS)
    }

    override fun showSnackbar(msg_id: Int) {
        var message = ""
        when (msg_id) {
            NOT_FOUND -> {
                message = getString(R.string.err_player_not_found)
            }
            EMPTY_NAME -> {
                message = getString(R.string.err_enter_player_name)
            }
        }
        val snackbar = Snackbar.make(parent, message, Snackbar.LENGTH_LONG)
        val snackView = snackbar.view
        snackView.setBackgroundColor(ContextCompat.getColor(context!!, R.color.black))
        val tv = snackView.findViewById<TextView>(R.id.snackbar_text)
        tv.setTextColor(ContextCompat.getColor(context!!, R.color.color_yellow_background))
        snackbar.show()
    }
}
