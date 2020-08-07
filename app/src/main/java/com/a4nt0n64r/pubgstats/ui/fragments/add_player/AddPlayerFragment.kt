package com.a4nt0n64r.pubgstats.ui.fragments.add_player

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.a4nt0n64r.pubgstats.R
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
const val NO_INTERNET = 2


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
            when (platform_spinner.selectedItem) {
                "Steam" -> {
                    when (region_spinner.selectedItem) {
                        getString(R.string.europe) -> {
                            presenter.requestPlayer(
                                input_login.text.toString(),
                                "pc-eu",
                                "steam",
                                checkConnection()
                            )
                        }
                        getString(R.string.asia) -> {
                            presenter.requestPlayer(
                                input_login.text.toString(),
                                "pc-as",
                                "steam",
                                checkConnection()
                            )
                        }
                        getString(R.string.south_east_asia) -> {
                            presenter.requestPlayer(
                                input_login.text.toString(),
                                "pc-sea",
                                "steam",
                                checkConnection()
                            )
                        }
                        getString(R.string.russia) -> {
                            presenter.requestPlayer(
                                input_login.text.toString(),
                                "pc-ru",
                                "steam",
                                checkConnection()
                            )
                        }
                        getString(R.string.japan) -> {
                            presenter.requestPlayer(
                                input_login.text.toString(),
                                "pc-jp",
                                "steam",
                                checkConnection()
                            )
                        }
                        getString(R.string.korea) -> {
                            presenter.requestPlayer(
                                input_login.text.toString(),
                                "pc-krjp",
                                "steam",
                                checkConnection()
                            )
                        }
                        getString(R.string.north_america) -> {
                            presenter.requestPlayer(
                                input_login.text.toString(),
                                "pc-na",
                                "steam",
                                checkConnection()
                            )
                        }
                        getString(R.string.south_america) -> {
                            presenter.requestPlayer(
                                input_login.text.toString(),
                                "pc-sa",
                                "steam",
                                checkConnection()
                            )
                        }
                        getString(R.string.oceania) -> {
                            presenter.requestPlayer(
                                input_login.text.toString(),
                                "pc-oc",
                                "steam",
                                checkConnection()
                            )
                        }
                    }
                }
                "Play Station" -> {
                    when (region_spinner.selectedItem) {
                        getString(R.string.europe) -> {
                            presenter.requestPlayer(
                                input_login.text.toString(),
                                "psn-eu",
                                "steam",
                                checkConnection()
                            )
                        }
                        getString(R.string.asia) -> {
                            presenter.requestPlayer(
                                input_login.text.toString(),
                                "psn-as",
                                "steam",
                                checkConnection()
                            )
                        }
                        getString(R.string.north_america) -> {
                            presenter.requestPlayer(
                                input_login.text.toString(),
                                "psn-na",
                                "steam",
                                checkConnection()
                            )
                        }
                        getString(R.string.oceania) -> {
                            presenter.requestPlayer(
                                input_login.text.toString(),
                                "psn-oc",
                                "steam",
                                checkConnection()
                            )
                        }
                    }
                }
                "Xbox" -> {
                    when (region_spinner.selectedItem) {
                        getString(R.string.europe) -> {
                            presenter.requestPlayer(
                                input_login.text.toString(),
                                "xbox-eu",
                                "steam",
                                checkConnection()
                            )
                        }
                        getString(R.string.asia) -> {
                            presenter.requestPlayer(
                                input_login.text.toString(),
                                "xbox-as",
                                "steam",
                                checkConnection()
                            )
                        }
                        getString(R.string.north_america) -> {
                            presenter.requestPlayer(
                                input_login.text.toString(),
                                "xbox-na",
                                "steam",
                                checkConnection()
                            )
                        }
                        getString(R.string.south_america) -> {
                            presenter.requestPlayer(
                                input_login.text.toString(),
                                "xbox-sa",
                                "steam",
                                checkConnection()
                            )
                        }
                        getString(R.string.oceania) -> {
                            presenter.requestPlayer(
                                input_login.text.toString(),
                                "xbox-oc",
                                "steam",
                                checkConnection()
                            )
                        }
                    }
                }
            }
        }
    }

    private fun setUpTextViews() {
        platform_tv.text = getString(R.string.platform)
        region_tv.text = getString(R.string.region)
    }

    private fun setUpSpinners() {
        val activity = this@AddPlayerFragment.activity as MainActivity
        setUpPCRegions(activity)
        setUpPlatforms(activity)
        setSpinnerOnPlatformSelectedListener()
    }

    private fun setUpPCRegions(activity: MainActivity) {
        val regions = resources.getStringArray(R.array.region_pc)
        val regionPCAdapter = AdapterForSpinner(activity, regions)
        region_spinner.adapter = regionPCAdapter
        regionPCAdapter.notifyDataSetChanged()
    }

    private fun setUpPSNRegions(activity: MainActivity) {
        val regions = resources.getStringArray(R.array.region_psn)
        val regionPSNAdapter = AdapterForSpinner(activity, regions)
        region_spinner.adapter = regionPSNAdapter
        regionPSNAdapter.notifyDataSetChanged()
    }

    private fun setUpXboxRegions(activity: MainActivity) {
        val regions = resources.getStringArray(R.array.region_xbox)
        val regionXboxAdapter = AdapterForSpinner(activity, regions)
        region_spinner.adapter = regionXboxAdapter
        regionXboxAdapter.notifyDataSetChanged()
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
        activity.drawListOfPlayersFragment()
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
            NO_INTERNET -> {
                message = getString(R.string.no_internet_connection)
            }
        }
        val snackbar = Snackbar.make(parent, message, Snackbar.LENGTH_LONG)
        val snackView = snackbar.view
        snackView.setBackgroundColor(ContextCompat.getColor(context!!, R.color.black))
        val tv = snackView.findViewById<TextView>(R.id.snackbar_text)
        tv.setTextColor(ContextCompat.getColor(context!!, R.color.color_yellow_background))
        snackbar.show()
    }

    private fun setSpinnerOnPlatformSelectedListener() {
        platform_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val activity = this@AddPlayerFragment.activity as MainActivity
                when (platform_spinner.selectedItem.toString()) {
                    getString(R.string.steam) -> {
                        setUpPCRegions(activity)
                    }
                    getString(R.string.psn) -> {
                        setUpPSNRegions(activity)
                    }
                    getString(R.string.xbox) -> {
                        setUpXboxRegions(activity)
                    }
                }
            }
        }
    }

    override fun showLoading() {
        loading_screen_addplayer.visibility = VISIBLE
    }

    override fun hideLoading() {
        loading_screen_addplayer.visibility = INVISIBLE
    }

    fun checkConnection(): Boolean {
        val activity = this@AddPlayerFragment.activity as MainActivity
        return activity.connectionAvailabile
    }
}
