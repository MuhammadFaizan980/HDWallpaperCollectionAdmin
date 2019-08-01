package com.squadtechs.hdwallpapercollectionadmin.activity_main

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
import com.google.firebase.firestore.Query
import com.squadtechs.hdwallpapercollectionadmin.R
import com.squadtechs.hdwallpapercollectionadmin.activity_add_category.ActivityAddCategory

class MainActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    private lateinit var recyclerView: RecyclerView
    private lateinit var list: ArrayList<CategoryModel>
    private lateinit var adapter: CategoryAdapter
    private lateinit var txtError: TextView
    private val collectionReference = FirebaseFirestore.getInstance().collection("categories")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
        prepareToolbar()
        populateRecyclerView()
    }

    private fun populateRecyclerView() {
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        recyclerView.adapter = adapter
        collectionReference.orderBy("server_time_stamp", Query.Direction.DESCENDING)
            .addSnapshotListener{ p0, p1 ->
                list.clear()
                if (!p0!!.isEmpty) {
                    for (doc in p0.documents) {
                        val obj = doc.toObject(CategoryModel::class.java)!!
                        obj.category_key = doc.id
                        list.add(obj)
                    }
                } else {
                    txtError.visibility = View.VISIBLE
                }
                adapter.notifyDataSetChanged()
            }
    }

    private fun prepareToolbar() {
        toolbar.title = "Categories"
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
        recyclerView = findViewById(R.id.recycler_view)
        list = ArrayList()
        adapter = CategoryAdapter(this, list)
        txtError = findViewById(R.id.txt_error_message)
    }

    override fun onStart() {
        super.onStart()
        txtError.visibility = View.GONE
    }

}
