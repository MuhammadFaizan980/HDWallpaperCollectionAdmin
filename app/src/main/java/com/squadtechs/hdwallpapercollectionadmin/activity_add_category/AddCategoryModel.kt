package com.squadtechs.hdwallpapercollectionadmin.activity_add_category

import android.net.Uri

class AddCategoryModel(val uri: Uri?, val categoryName: String?) : AddCategoryContracts.IModel {
    override fun validateCredentials(): Boolean {
        return uri != null && categoryName != null && !categoryName.equals("")
    }
}