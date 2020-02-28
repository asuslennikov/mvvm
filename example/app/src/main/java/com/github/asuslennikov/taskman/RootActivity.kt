package com.github.asuslennikov.taskman

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class RootActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.root)
    }

    override fun onBackPressed() {
        val navFragment = supportFragmentManager.fragments[0]
        navFragment.childFragmentManager.fragments[0]
            ?.takeIf { it is Fragment<*, *, *> }
            ?.apply {
                if (!(this as Fragment<*, *, *>).onBackPressed()) {
                    super.onBackPressed()
                }
            }
            ?: super.onBackPressed()
    }
}
