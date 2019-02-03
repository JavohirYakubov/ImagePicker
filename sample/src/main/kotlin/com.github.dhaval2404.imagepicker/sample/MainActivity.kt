package com.github.dhaval2404.imagepicker.sample

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.github.dhaval2404.imagepicker.ImagePicker
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_camera_only.*
import kotlinx.android.synthetic.main.content_gallery_only.*
import kotlinx.android.synthetic.main.content_profile.*

class MainActivity : AppCompatActivity() {

    companion object {

        private const val PROFILE_IMAGE_REQ_CODE = 101
        private const val GALLERY_IMAGE_REQ_CODE = 102
        private const val CAMERA_IMAGE_REQ_CODE = 103
        private const val DEFAULT_IMAGE_URL =
            "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png"

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab_add_photo.setOnClickListener {
            ImagePicker.with(this)
                .cropSquare()
                .maxResultSize(320, 320)  //2.0 Megapixel
                .start(PROFILE_IMAGE_REQ_CODE)
        }

        fab_add_gallery_photo.setOnClickListener {
            ImagePicker.with(this)
                .galleryOnly()
                .maxResultSize(320, 320)
                .start(GALLERY_IMAGE_REQ_CODE)
        }

        fab_add_camera_photo.setOnClickListener {
            ImagePicker.with(this)
                .cameraOnly()
                .compress(40)
                .start(CAMERA_IMAGE_REQ_CODE)
        }

        imgProfile.setRemoteImage(DEFAULT_IMAGE_URL, true)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            //File object will not be null for RESULT_OK
            val file = ImagePicker.getFile(data)

            Log.e("TAG", "Path:${file?.absolutePath}")
            when (requestCode) {
                PROFILE_IMAGE_REQ_CODE -> imgProfile.setLocalImage(file!!, true)
                GALLERY_IMAGE_REQ_CODE -> imgGallery.setLocalImage(file!!)
                CAMERA_IMAGE_REQ_CODE -> imgCamera.setLocalImage(file!!, false)
            }
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show()
        }
    }

}
