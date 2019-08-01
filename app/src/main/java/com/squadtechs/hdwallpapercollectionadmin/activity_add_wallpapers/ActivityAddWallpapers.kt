package com.squadtechs.hdwallpapercollectionadmin.activity_add_wallpapers

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.squadtechs.hdwallpapercollectionadmin.R

class ActivityAddWallpapers : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    private val CATEGORY_REF = intent!!.extras!!.getString("category_ref")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_wallpapers)
    }
}
