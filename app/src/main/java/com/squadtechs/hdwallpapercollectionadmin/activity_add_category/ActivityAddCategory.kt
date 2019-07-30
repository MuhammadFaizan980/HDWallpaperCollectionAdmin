package com.squadtechs.hdwallpapercollectionadmin.activity_add_category

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.squadtechs.hdwallpapercollectionadmin.R
import com.wang.avi.AVLoadingIndicatorView

class ActivityAddCategory : AppCompatActivity(), AddCategoryContracts.IView {
    private lateinit var toolbar: Toolbar
    private lateinit var imgCategory: ImageView
    private lateinit var imgSelectImage: ImageView
    private lateinit var edtCategoryName: EditText
    private lateinit var btnSave: Button
    private lateinit var loader: AVLoadingIndicatorView
    private lateinit var presenter: AddCategoryContracts.IPresenter
    private var uri: Uri? = null
    private var categoryName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_category)
        initViews()
        initAddCategoryProcess()
    }

    private fun initAddCategoryProcess() {
        presenter.prepareToolbar(toolbar)
        btnSave.setOnClickListener {
            categoryName = edtCategoryName.text.toString().trim()
            presenter.initValidation(uri, categoryName)
            loader.visibility = View.VISIBLE
            btnSave.isEnabled = false
        }
        imgSelectImage.setOnClickListener {
            presenter.selectImage()
        }
    }

    private fun initViews() {
        toolbar = findViewById(R.id.toolbar)
        imgCategory = findViewById(R.id.img_category)
        imgSelectImage = findViewById(R.id.img_add_image)
        edtCategoryName = findViewById(R.id.edt_category_name)
        btnSave = findViewById(R.id.btn_add_category)
        loader = findViewById(R.id.loader)
        presenter = AddCategoryPresenter(this, this)
    }

    override fun onValidationResult(isValid: Boolean) {
        if (isValid) {
            presenter.uploadImage(uri!!, categoryName!!)
        } else {
            Toast.makeText(this, "Select an image and enter category name first", Toast.LENGTH_LONG).show()
            loader.visibility = View.INVISIBLE
            btnSave.isEnabled = true
        }
    }

    override fun onFirebaseStorageResponse(exception: Exception?, downloadUrl: String?) {
        if (exception == null) {
            presenter.saveDataToFirestore(downloadUrl!!, categoryName!!)
        } else {
            Toast.makeText(this, exception.message, Toast.LENGTH_LONG).show()
            loader.visibility = View.INVISIBLE
            btnSave.isEnabled = true
        }
    }

    override fun onFirestoreResponse(exception: Exception?) {
        if (exception == null) {
            Toast.makeText(this, "Category added successfully", Toast.LENGTH_LONG).show()
            finish()
        } else {
            Toast.makeText(this, exception.message, Toast.LENGTH_LONG).show()
            loader.visibility = View.INVISIBLE
            btnSave.isEnabled = true
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 69 && resultCode == Activity.RESULT_OK && data != null) {
            uri = data.data
            imgCategory.setImageURI(uri!!)
        }
    }

    override fun onStart() {
        super.onStart()
        loader.visibility = View.INVISIBLE
    }

}
