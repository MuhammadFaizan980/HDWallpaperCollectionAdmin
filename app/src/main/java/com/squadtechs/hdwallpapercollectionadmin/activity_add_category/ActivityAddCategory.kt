package com.squadtechs.hdwallpapercollectionadmin.activity_add_category

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.squadtechs.hdwallpapercollectionadmin.R

class ActivityAddCategory : AppCompatActivity(), AddCategoryContracts.IView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_category)
    }

    override fun onValidationResult(isValid: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onFirebaseStorageResponse(exception: Exception?, downloadUrl: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun OnFirstoreResponse(exception: Exception?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
