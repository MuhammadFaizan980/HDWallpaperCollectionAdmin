package com.squadtechs.hdwallpapercollectionadmin.activity_add_category

import android.net.Uri

interface AddCategoryContracts {
    interface IView {
        fun onValidationResult(isValid: Boolean)
        fun onFirebaseStorageResponse(exception: Exception?, downloadUrl: String?)
        fun onFirestoreResponse(exception: Exception?)
    }

    interface IPresenter {
        fun prepareToolbar(toolbar: androidx.appcompat.widget.Toolbar)
        fun initValidation(uri: Uri?, categoryName: String?)
        fun selectImage()
        fun uploadImage(uri: Uri, categoryName: String)
        fun saveDataToFirestore(downloadUrl: String, categoryName: String)
    }

    interface IModel {
        fun validateCredentials(): Boolean
    }
}