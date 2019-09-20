package com.a4nt0n64r.cahetest.ui

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.util.Log
import com.a4nt0n64r.cahetest.R
import com.a4nt0n64r.cahetest.core.CacheTestApplication
import com.a4nt0n64r.cahetest.ui.base.Presenter
import com.a4nt0n64r.cahetest.ui.base.View
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject


class MainActivity : MvpAppCompatActivity(), View {

    private val TAG = "MainActivity"

    @Inject
    @InjectPresenter
    lateinit var presenter: Presenter

    @ProvidePresenter
    fun providePresenter(): Presenter {
        return presenter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        (application as CacheTestApplication).coreComponent.inject(this)

        save.setOnClickListener {
            presenter.onSaveButtonWasClicked(name_tv.text.toString(),data_tv.text.toString())
        }

        delete.setOnClickListener {
            presenter.onDeleteButtonWasClicked(find_or_delete_tv.text.toString())
        }

        find.setOnClickListener {
            presenter.onFindButtonWasClicked(find_or_delete_tv.text.toString())
        }

        show.setOnClickListener {
            presenter.onShowButtonWasClicked()
        }

        network.setOnClickListener {
            presenter.onNetButtonWasClicked()
        }

    }

    //Вызываем у Presenter метод onDestroy, чтобы избежать утечек контекста и прочих неприятностей.
    public override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
        Log.d(TAG, "onDestroy()")
    }

    override fun fillName(name: String) {
        tv1.text = name
    }

    override fun fillData(data: String) {
        tv2.text = data
    }

    override fun showSnackbar(msg: String) {
        Snackbar.make(constr, msg, Snackbar.LENGTH_LONG).show()
    }
}





