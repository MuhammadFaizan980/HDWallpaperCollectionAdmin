package com.squadtechs.hdwallpapercollectionadmin.activity_add_category

import android.content.Context
import android.net.Uri

class AddCategoryPresenter constructor(val context: Context, val view: AddCategoryContracts.IView) :
    AddCategoryContracts.IPresenter {

    private lateinit var model: AddCategoryContracts.IModel

    override fun initValidation(uri: Uri?, categoryName: String) {
        model = AddCategoryModel(uri, categoryName)
        view.onValidationResult(model.validateCredentials())
    }

    override fun uploadImage(uri: Uri) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun saveDataToFirestore(downloadUrl: String, categoryName: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}