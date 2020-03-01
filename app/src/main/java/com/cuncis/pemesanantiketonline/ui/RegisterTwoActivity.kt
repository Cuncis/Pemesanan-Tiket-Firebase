package com.cuncis.pemesanantiketonline.ui

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.MimeTypeMap
import com.cuncis.pemesanantiketonline.R
import com.cuncis.pemesanantiketonline.data.PrefsManager
import com.cuncis.pemesanantiketonline.util.Utils.Companion.showLog
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_register_two.*

class RegisterTwoActivity : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    private lateinit var storage: StorageReference

    private lateinit var photoLocation: Uri
    private var photoMax = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_two)

        btn_add_photo.setOnClickListener {
            findPhoto()
        }

        btn_continue.setOnClickListener {
            btn_continue.isEnabled = false
            btn_continue.text = "Loading..."

            database = FirebaseDatabase.getInstance().reference.child("Users")
                .child(PrefsManager.getUsername(this).toString())
            storage = FirebaseStorage.getInstance().reference.child("Photousers")
                .child(PrefsManager.getUsername(this).toString())

            val storageReference = storage.child("${System.currentTimeMillis()}.${getFileExtension(photoLocation)}")
            storageReference.putFile(photoLocation)
                .addOnSuccessListener {
                    storageReference.downloadUrl.addOnSuccessListener {
                        showLog("Location: $it")
                        database.ref.child("url_photo_profile").setValue(it.toString())
                        database.ref.child("nama_lengkap").setValue(et_nama_lengkap.text.toString().trim())
                        database.ref.child("bio").setValue(et_bio.text.toString().trim())
                    }
                }.addOnFailureListener {
                    showLog("Location: ${it.message}")
                }.addOnCompleteListener {
                    startActivity(Intent(this, SuccessRegisterActivity::class.java))
                }
        }

        btn_back.setOnClickListener {
            finish()
        }
    }

    private fun getFileExtension(uri: Uri): String? {
        val contextResolver = contentResolver
        val mimeTypeMap = MimeTypeMap.getSingleton()
        return mimeTypeMap.getExtensionFromMimeType(contextResolver.getType(uri))
    }

    private fun findPhoto() {
        val pic = Intent()
        pic.type = "image/*"
        pic.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(pic, photoMax)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == photoMax && resultCode == Activity.RESULT_OK
            && data != null && data.data != null) {
            photoLocation = data.data!!
            Picasso.with(this).load(photoLocation).centerCrop().fit().into(pic_photo_register_user)
        }
    }
}
