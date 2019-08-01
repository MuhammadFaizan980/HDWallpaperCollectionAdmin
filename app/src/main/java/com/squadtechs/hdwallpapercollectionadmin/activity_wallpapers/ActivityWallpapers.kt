package com.squadtechs.hdwallpapercollectionadmin.activity_wallpapers

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.squadtechs.hdwallpapercollectionadmin.R
import com.squadtechs.hdwallpapercollectionadmin.activity_add_wallpapers.ActivityAddWallpapers

class ActivityWallpapers : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    private lateinit var CATEGORY_REF: String
    private lateinit var recyclerView: RecyclerView
    private lateinit var list: ArrayList<WallpaperModel>
    private lateinit var adapter: WallpaperAdapter
    private lateinit var txtError: TextView
    private val collectionReference = FirebaseFirestore.getInstance().collection("wallpapers")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wallpapers)
        initViews()
        prepareToolbar()
        populateRecyclerView()
    }

    private fun populateRecyclerView() {
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        recyclerView.adapter = adapter
        collectionReference.whereEqualTo("category_ref", CATEGORY_REF).addSnapshotListener{ p0, p1 ->
            list.clear()
            if (!p0!!.isEmpty) {
                for (doc in p0.documents) {
                    val obj = doc.toObject(WallpaperModel::class.java)!!
                    obj.wallpaper_key = doc.id
                    list.add(obj)
                }
            } else {
                txtError.visibility = View.VISIBLE
            }
            adapter.notifyDataSetChanged()
        }
    }

    private fun prepareToolbar() {
        toolbar.title = "Wallpapers"
        toolbar.inflateMenu(R.menu.menu_add)
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black)

        toolbar.setNavigationOnClickListener {
            finish()
        }

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
        recyclerView = findViewById(R.id.recycler_view)
        list = ArrayList()
        adapter = WallpaperAdapter(this, list)
        txtError = findViewById(R.id.txt_error_message)
    }

    override fun onStart() {
        super.onStart()
        txtError.visibility = View.GONE
    }

}
