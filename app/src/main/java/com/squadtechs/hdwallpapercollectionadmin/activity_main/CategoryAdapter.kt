package com.squadtechs.hdwallpapercollectionadmin.activity_main

import android.app.Activity
import android.content.Context
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squadtechs.hdwallpapercollectionadmin.R
import com.squareup.picasso.Picasso


class CategoryAdapter(val context: Context, val list: ArrayList<CategoryModel>) :
    RecyclerView.Adapter<CategoryAdapter.CategoryHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryHolder {
        return CategoryHolder(LayoutInflater.from(context).inflate(R.layout.cell_design, parent, false))
    }

    override fun getItemCount(): Int = list.size

    override fun getItemViewType(position: Int): Int = position

    override fun onBindViewHolder(holder: CategoryHolder, position: Int) {
        adjustLayout(holder, position)
        inflateValues(holder, position)
    }

    private fun inflateValues(holder: CategoryHolder, position: Int) {
        holder.txtTitle.text = list[position].category_name
        Picasso.get().load(list[position].category_image_url).into(holder.imgCell)
    }

    private fun adjustLayout(holder: CategoryHolder, position: Int) {
        val displayMetrics = DisplayMetrics()
        (context as Activity).windowManager.defaultDisplay.getMetrics(displayMetrics)
        val height = displayMetrics.heightPixels
        val width = displayMetrics.widthPixels
        holder.frame.layoutParams = FrameLayout.LayoutParams((width / 2), ((40 * height) / 100))
        if (position % 2 == 0) {
            holder.frame.setPadding(3, 3, 0, 0)
        } else {
            holder.frame.setPadding(0, 3, 3, 0)
        }
    }

    inner class CategoryHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtTitle: TextView = view.findViewById(R.id.txt_title)
        val frame: FrameLayout = view.findViewById(R.id.main_frame)
        val touchView: View = view.findViewById(R.id.touch_view)
        val imgCell: ImageView = view.findViewById(R.id.img_cell)
    }
}