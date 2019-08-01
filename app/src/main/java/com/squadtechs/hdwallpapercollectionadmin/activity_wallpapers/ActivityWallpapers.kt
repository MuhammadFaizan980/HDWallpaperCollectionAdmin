package com.squadtechs.hdwallpapercollectionadmin.activity_wallpapers

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.squadtechs.hdwallpapercollectionadmin.R
import com.squadtechs.hdwallpapercollectionadmin.activity_add_wallpapers.ActivityAddWallpapers

class ActivityWallpapers : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    private lateinit var CATEGORY_REF: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wallpapers)
        initViews()
        prepareToolbar()
    }

    private fun prepareToolbar() {
        toolbar.title = "Wallpapers"
        toolbar.inflateMenu(R.menu.menu_add)
        toolbar.setOnMenuItemClickListener { item: MenuItem? ->
            when (item!!.itemId) {
                R.id.item_add -> {
                    startActivity(
                        Intent(this, ActivityAddWallpapers::class.java).putExtra(
                            "category_ref",
                            CATEGORY_REF
                        )
                    )
                }
            }
            return@setOnMenuItemClickListener true
        }
    }

    private fun initViews() {
        toolbar = findViewById(R.id.toolbar)
        CATEGORY_REF = intent!!.extras!!.getString("category_ref") as String
    }

}
