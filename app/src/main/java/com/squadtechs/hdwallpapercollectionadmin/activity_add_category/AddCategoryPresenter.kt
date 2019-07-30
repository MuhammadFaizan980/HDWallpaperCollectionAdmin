package com.squadtechs.hdwallpapercollectionadmin.activity_add_category

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
import com.google.firebase.firestore.ServerTimestamp
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import com.squadtechs.hdwallpapercollectionadmin.R
import java.util.*
import kotlin.collections.HashMap

class AddCategoryPresenter constructor(val context: Context, val view: AddCategoryContracts.IView) :
    AddCategoryContracts.IPresenter {

    private lateinit var model: AddCategoryContracts.IModel
    private lateinit var storageReference: StorageReference
    private lateinit var collectionReference: CollectionReference

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

    override fun uploadImage(uri: Uri, categoryName: String) {
        storageReference = FirebaseStorage.getInstance().getReference("categories").child("$categoryName.jpg")
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

    override fun saveDataToFirestore(downloadUrl: String, categoryName: String) {
        collectionReference = FirebaseFirestore.getInstance().collection("categories")
        val map = HashMap<String, Any>()
        map["category_name"] = categoryName
        map["category_image_url"] = downloadUrl
        map["server_time_stamp"] = FieldValue.serverTimestamp()
        collectionReference.add(map).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                view.onFirestoreResponse(null)
            } else {
                view.onFirestoreResponse(task.exception)
            }
        }
    }

}