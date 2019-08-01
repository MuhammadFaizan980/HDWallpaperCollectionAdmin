package com.squadtechs.hdwallpapercollectionadmin.activity_add_wallpapers

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.widget.Toolbar
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import com.squadtechs.hdwallpapercollectionadmin.R

class AddWallpaperPresenter constructor(val context: Context, val view: AddWallpaperContracts.IView) :
    AddWallpaperContracts.IPresenter {

    private lateinit var model: AddWallpaperContracts.IModel
    private lateinit var storageReference: StorageReference
    private val collectionReference: CollectionReference = FirebaseFirestore.getInstance().collection("wallpapers")
    private lateinit var RANDOM_KEY: String

    override fun prepareToolbar(toolbar: Toolbar) {
        toolbar.title = "Add Wallpaper"
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black)
        toolbar.setNavigationOnClickListener {
            (context as Activity).finish()
        }
    }

    override fun initValidation(uri: Uri?, categoryRef: String?) {
        model = AddWallpaperModel(uri, categoryRef)
        view.onValidationResult(model.validateCredentials())
    }

    override fun selectImage() {
        (context as Activity).startActivityForResult(Intent(Intent.ACTION_GET_CONTENT).setType("image/*"), 70)
    }

    override fun uploadImage(uri: Uri, categoryRef: String) {
        RANDOM_KEY = collectionReference.document().id
        storageReference = FirebaseStorage.getInstance().getReference("wallpapers").child("$RANDOM_KEY.jpg")
        val uploadTask = storageReference.putFile(uri)
        val urlTask = uploadTask.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>> { task ->
            if (!task.isSuccessful) {
                task.exception?.let {
                    throw it
                }
            }
            return@Continuation storageReference.downloadUrl
        }).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                view.onFirebaseStorageResponse(null, task.result.toString())
            } else {
                view.onFirebaseStorageResponse(task.exception!!, null)
            }
        }
    }

    override fun saveDataToFirestore(downloadUrl: String, categoryRef: String) {
        val map = HashMap<String, Any>()
        map["category_ref"] = categoryRef
        map["wallpaper_image_url"] = downloadUrl
        map["server_time_stamp"] = FieldValue.serverTimestamp()
        collectionReference.document(RANDOM_KEY).set(map).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                view.onFirestoreResponse(null)
            } else {
                view.onFirestoreResponse(task.exception)
            }
        }
    }

}