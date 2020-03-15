package com.cuncis.pemesanantiketonline.ui

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.MimeTypeMap
import android.widget.Toast
import com.cuncis.pemesanantiketonline.R
import com.cuncis.pemesanantiketonline.data.PrefsManager
import com.cuncis.pemesanantiketonline.util.Utils.Companion.showLog
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_edit_profile.*

class EditProfileActivity : AppCompatActivity() {

    private lateinit var databaseUsers: DatabaseReference
    private lateinit var storage: StorageReference

    private var photoLocation: Uri? = null
    private var photoMax = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        databaseUsers = FirebaseDatabase.getInstance().reference.child("Users")
            .child(PrefsManager.getUsername(this).toString())
        storage = FirebaseStorage.getInstance().reference.child("Photousers")
            .child(PrefsManager.getUsername(this).toString())

        databaseUsers.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                et_nama_lengkap.setText(dataSnapshot.child("nama_lengkap").value.toString())
                et_bio.setText(dataSnapshot.child("bio").value.toString())
                et_username.setText(dataSnapshot.child("username").value.toString())
                et_password.setText(dataSnapshot.child("password").value.toString())
                et_email_address.setText(dataSnapshot.child("email_address").value.toString())
                Picasso.with(this@EditProfileActivity).load(dataSnapshot.child("url_photo_profile").value.toString())
                    .into(photo_edit_profile)

                showLog("List of Data: $dataSnapshot")
            }
            override fun onCancelled(p0: DatabaseError) {
                showLog("EditProfile Users Error: " + p0.message)
            }
        })

        btn_save.setOnClickListener {
            btn_save.isEnabled = false
            btn_save.text = "Loading..."

            databaseUsers.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    dataSnapshot.ref.child("username").setValue(et_username.text.toString())
                    dataSnapshot.ref.child("password").setValue(et_password.text.toString())
                    dataSnapshot.ref.child("bio").setValue(et_bio.text.toString())
                    dataSnapshot.ref.child("email_address").setValue(et_email_address.text.toString())
                    dataSnapshot.ref.child("nama_lengkap").setValue(et_nama_lengkap.text.toString())

                    if (photoLocation != null) {
                        val storageReference = storage.child("${System.currentTimeMillis()}.${getFileExtension(photoLocation!!)}")
                        storageReference.putFile(photoLocation!!)
                            .addOnSuccessListener {
                                storageReference.downloadUrl.addOnSuccessListener {
                                    databaseUsers.ref.child("url_photo_profile").setValue(it.toString())
                                }
                            }.addOnFailureListener {
                                showLog("Location: ${it.message}")
                            }.addOnCompleteListener {
                                btn_save.isEnabled = true
                                btn_save.text = "SAVE PROFILE"

                                Toast.makeText(this@EditProfileActivity, "Data Updated Successfully!", Toast.LENGTH_SHORT).show()
                                val intent = Intent(this@EditProfileActivity, MyProfileActivity::class.java)
                                startActivity(intent)
                            }
                    } else {
                        btn_save.isEnabled = true
                        btn_save.text = "SAVE PROFILE"

                        Toast.makeText(this@EditProfileActivity, "Data Updated Successfully!", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@EditProfileActivity, MyProfileActivity::class.java)
                        startActivity(intent)
                    }
                }
                override fun onCancelled(p0: DatabaseError) {
                    btn_save.isEnabled = true
                    btn_save.text = "SAVE PROFILE"
                    showLog("BtnSave Error: ${p0.message}")
                }
            })
        }

        btn_add_new_photo.setOnClickListener {
            findPhoto()
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
            Picasso.with(this).load(photoLocation).centerCrop().fit().into(photo_edit_profile)
        }
    }

}
