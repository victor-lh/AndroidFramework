package com.victorlh.android.framework.core.activitys

import android.content.res.Configuration
import android.os.Bundle
import androidx.annotation.AnimRes
import androidx.appcompat.app.AppCompatActivity
import com.victorlh.android.framework.core.flujo.ITransitionStackAnimation
import com.victorlh.android.framework.core.fragment.AbstractFragment
import com.victorlh.android.framework.core.idioma.IIdioma
import java.util.*

/**
 * @author Victor
 * 02/06/2018
 */

abstract class AbstractActivity(contentLayoutId: Int) : AppCompatActivity(contentLayoutId) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        establecerIdioma()
    }

    protected abstract val idiomaAplicacion: IIdioma?

    fun setFragment(layout: Int, fragment: AbstractFragment, tag: String) {
        setFragment(layout, fragment, tag, null)
    }

    fun setFragment(layout: Int, fragment: AbstractFragment, tag: String, animation: ITransitionStackAnimation?) {
        setFragment(layout, fragment, tag, animation?.xmlEntrada ?: 0, animation?.xmlSalida ?: 0)
    }

    fun setFragment(layout: Int, fragment: AbstractFragment, tag: String, @AnimRes xmlEntrada: Int, @AnimRes xmlSalida: Int) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        if (xmlEntrada != 0 && xmlSalida != 0) {
            fragmentTransaction.setCustomAnimations(xmlEntrada, xmlSalida)
        }
        fragmentTransaction.replace(layout, fragment, tag)
        fragmentTransaction.commit()
    }

    private fun establecerIdioma() {
        if (this.idiomaAplicacion != null) {
            Locale.setDefault(this.idiomaAplicacion!!.locale)
            val config = Configuration()
            config.setLocale(this.idiomaAplicacion!!.locale)
            createConfigurationContext(config)
            //TODO - Provar
        }
    }

    public override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
    }

    public override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
    }
}