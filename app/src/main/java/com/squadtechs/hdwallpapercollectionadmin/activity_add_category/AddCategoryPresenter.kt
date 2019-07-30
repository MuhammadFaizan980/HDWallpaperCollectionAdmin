package com.squadtechs.hdwallpapercollectionadmin.activity_add_category

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.widget.Toolbar
import com.squadtechs.hdwallpapercollectionadmin.R

class AddCategoryPresenter constructor(val context: Context, val view: AddCategoryContracts.IView) :
    AddCategoryContracts.IPresenter {

    private lateinit var model: AddCategoryContracts.IModel

    override fun prepareToolbar(toolbar: Toolbar) {
        toolbar.title = "Add Category"
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black)
        toolbar.setNavigationOnClickListener {
            (context as Activity).finish()
        }
    }

    override fun initValidation(uri: Uri?, categoryName: String?) {
        model = AddCategoryModel(uri, categoryName)
        view.onValidationResult(model.validateCredentials())
    }

    override fun selectImage() {
        (context as Activity).startActivityForResult(Intent(Intent.ACTION_GET_CONTENT).setType("image/*"), 69)
    }

    override fun uploadImage(uri: Uri) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun saveDataToFirestore(downloadUrl: String, categoryName: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}