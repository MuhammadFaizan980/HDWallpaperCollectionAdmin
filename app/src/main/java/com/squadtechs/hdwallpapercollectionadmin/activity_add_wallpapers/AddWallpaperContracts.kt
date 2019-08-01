package com.squadtechs.hdwallpapercollectionadmin.activity_add_wallpapers

import android.net.Uri

interface AddWallpaperContracts {
    interface IView {
        fun onValidationResult(isValid: Boolean)
        fun onFirebaseStorageResponse(exception: Exception?, downloadUrl: String?)
        fun onFirestoreResponse(exception: Exception?)
    }

    interface IPresenter {
        fun prepareToolbar(toolbar: androidx.appcompat.widget.Toolbar)
        fun initValidation(uri: Uri?, categoryRef: String?)
        fun selectImage()
        fun uploadImage(uri: Uri, categoryRef: String)
        fun saveDataToFirestore(downloadUrl: String, categoryRef: String)
    }

    interface IModel {
        fun validateCredentials(): Boolean
    }

}