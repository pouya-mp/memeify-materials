/*
 * Copyright (c) 2019 Razeware LLC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * Notwithstanding the foregoing, you may not use, copy, modify, merge, publish,
 * distribute, sublicense, create a derivative work, and/or sell copies of the
 * Software in any work that is designed, intended, or marketed for pedagogical or
 * instructional purposes related to programming, coding, application development,
 * or information technology.  Permission for such use, copying, modification,
 * merger, publication, distribution, sublicensing, creation of derivative works,
 * or sale is expressly withheld.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.raywenderlich.memeify

import android.app.Activity
import android.content.ClipData
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.core.content.FileProvider.getUriForFile
import kotlinx.android.synthetic.main.activity_take_picture.*
import java.io.File

class  TakePictureActivity : Activity(), View.OnClickListener {
  
  private var selectedPhotoPath: Uri? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_take_picture)

    pictureImageview.setOnClickListener(this)
    enterTextButton.setOnClickListener(this)

  }

  override fun onClick(v: View) {
    when (v.id) {
      R.id.pictureImageview -> {}
      R.id.enterTextButton -> {}
      else -> println("No case satisfied")
    }
  }

  private fun takePictureWithCamera() {
    // 1
    val captureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

    // 2
    val imagePath = File(filesDir, "images")
    val newFile = File(imagePath, "default_image.jpg")
    if (newFile.exists()) {
      newFile.delete()
    } else {
      newFile.parentFile.mkdirs()
    }
    selectedPhotoPath = getUriForFile(this, BuildConfig.APPLICATION_ID +
            ".fileprovider", newFile)

    // 3
    captureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
            selectedPhotoPath)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      captureIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
    } else {
      val clip = ClipData.newUri(contentResolver, "A photo", selectedPhotoPath)
      captureIntent.clipData = clip
      captureIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
    }
  }


  companion object {
    private const val MIME_TYPE_IMAGE = "image/"
    private const val TAKE_PHOTO_REQUEST_CODE = 1
  }
}
