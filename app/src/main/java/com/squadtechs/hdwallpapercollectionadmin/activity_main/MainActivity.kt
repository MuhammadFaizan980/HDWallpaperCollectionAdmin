package com.squadtechs.hdwallpapercollectionadmin.activity_main

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.squadtechs.hdwallpapercollectionadmin.R
import com.squadtechs.hdwallpapercollectionadmin.activity_add_category.ActivityAddCategory

class MainActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
        prepareToolbar()
    }

    private fun prepareToolbar() {
        toolbar.title = "Wallpapers"
        toolbar.inflateMenu(R.menu.menu_add)
        toolbar.setOnMenuItemClickListener { item: MenuItem? ->
            when (item!!.itemId) {
                R.id.item_add -> {
                    startActivity(Intent(this, ActivityAddCategory::class.java))
                }
            }
            return@setOnMenuItemClickListener true
        }
    }

    private fun initViews() {
        toolbar = findViewById(R.id.toolbar)
    }

}
