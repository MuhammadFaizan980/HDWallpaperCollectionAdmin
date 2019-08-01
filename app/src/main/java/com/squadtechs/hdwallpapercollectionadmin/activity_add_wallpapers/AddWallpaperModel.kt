package com.squadtechs.hdwallpapercollectionadmin.activity_add_wallpapers

import android.net.Uri

class AddWallpaperModel(val uri: Uri?, val categoryRef: String?) : AddWallpaperContracts.IModel {
    override fun validateCredentials(): Boolean {
        return uri != null && categoryRef != null
    }
}