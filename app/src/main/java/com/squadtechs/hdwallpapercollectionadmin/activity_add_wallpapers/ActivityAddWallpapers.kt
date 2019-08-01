package com.squadtechs.hdwallpapercollectionadmin.activity_add_wallpapers

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.squadtechs.hdwallpapercollectionadmin.R
import com.wang.avi.AVLoadingIndicatorView

class ActivityAddWallpapers : AppCompatActivity(), AddWallpaperContracts.IView {

    private lateinit var toolbar: Toolbar
    private lateinit var CATEGORY_REF: String
    private lateinit var imgWallpaper: ImageView
    private lateinit var imgSelectImage: ImageView
    private lateinit var btnSave: Button
    private lateinit var loader: AVLoadingIndicatorView
    private lateinit var presenter: AddWallpaperContracts.IPresenter
    private var uri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_wallpapers)
        initViews()
        initAddWallpaperProcess()
    }

    private fun initAddWallpaperProcess() {
        presenter.prepareToolbar(toolbar)
        btnSave.setOnClickListener {
            presenter.initValidation(uri, CATEGORY_REF)
        }
        imgSelectImage.setOnClickListener {
            presenter.selectImage()
        }
    }

    private fun initViews() {
        toolbar = findViewById(R.id.toolbar)
        imgWallpaper = findViewById(R.id.img_wallpaper)
        imgSelectImage = findViewById(R.id.img_choose_wallpaper)
        btnSave = findViewById(R.id.btn_add_wallpaper)
        CATEGORY_REF = intent.extras!!.getString("category_ref") as String
        loader = findViewById(R.id.loader)
        presenter = AddWallpaperPresenter(this, this)
    }

    override fun onValidationResult(isValid: Boolean) {
        if (isValid) {
            presenter.uploadImage(uri!!, CATEGORY_REF!!)
            loader.visibility = View.VISIBLE
            btnSave.isEnabled = false
        } else {
            Toast.makeText(this, "Select an image first", Toast.LENGTH_LONG).show()
        }
    }

    override fun onFirebaseStorageResponse(exception: Exception?, downloadUrl: String?) {
        if (exception == null) {
            presenter.saveDataToFirestore(downloadUrl!!, CATEGORY_REF!!)
        } else {
            Toast.makeText(this, exception.message, Toast.LENGTH_LONG).show()
            loader.visibility = View.INVISIBLE
            btnSave.isEnabled = true
        }
    }

    override fun onFirestoreResponse(exception: Exception?) {
        if (exception == null) {
            Toast.makeText(this, "Wallpaper added successfully", Toast.LENGTH_LONG).show()
            finish()
        } else {
            Toast.makeText(this, exception.message, Toast.LENGTH_LONG).show()
            loader.visibility = View.INVISIBLE
            btnSave.isEnabled = true
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 70 && resultCode == Activity.RESULT_OK && data != null) {
            uri = data.data
            imgWallpaper.setImageURI(uri!!)
        }
    }

    override fun onStart() {
        super.onStart()
        loader.visibility = View.INVISIBLE
    }

}
