package com.example.nafis.agrosewa

import android.Manifest
import android.animation.ObjectAnimator
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.view.animation.LinearInterpolator
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.Camera
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.R
import androidx.camera.core.TorchState
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import com.example.nafis.agrosewa.R.*
import com.example.nafis.agrosewa.databinding.ActivityCameraBinding
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.label.ImageLabeler
import com.google.mlkit.vision.label.ImageLabeling
import com.google.mlkit.vision.label.defaults.ImageLabelerOptions
import java.io.File
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class Camera : AppCompatActivity() {

    private lateinit var binding: ActivityCameraBinding
    private lateinit var cameraExecutor: ExecutorService
    private var imageCapture: ImageCapture? = null
    private var labeler: ImageLabeler? = null
    private lateinit var selectImageLauncher: ActivityResultLauncher<String>
    private var imgUri:Uri?=null
    private var camera: Camera? = null
    private lateinit var cameraProvider: ProcessCameraProvider
    private lateinit var cameraSelector: CameraSelector


    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                startCamera()
            } else {
                Toast.makeText(this, "Camera permission denied", Toast.LENGTH_SHORT).show()
            }
        }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN)

        cameraExecutor = Executors.newSingleThreadExecutor()
        try {
            labeler = ImageLabeling.getClient(ImageLabelerOptions.DEFAULT_OPTIONS)
        } catch (e: Exception) {
            Log.e(TAG, "Error initializing ImageLabeler", e)
        }


        selectImageLauncher =
            registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
                imgUri = uri
                updateUIVisibility(View.GONE, View.GONE, View.VISIBLE, View.VISIBLE,View.GONE,View.GONE,View.GONE)
                binding.scanMoveUp.clearAnimation()
                binding.scanningimg.setImageURI(imgUri)
            }

        binding.gallerybtn.setOnClickListener {
            selectImageLauncher.launch("image/*")
        }

        // Request camera permissions
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            == PackageManager.PERMISSION_GRANTED) {
            startCamera()
        } else {
            requestPermissionLauncher.launch(Manifest.permission.CAMERA)
        }

        binding.camerashutterbtn.setOnClickListener { takePhoto() }

        binding.flashoff.setOnClickListener { setFlashIcon() }

        startAnimation()

    }

    private fun startAnimation() {
        val scanneranim=AnimationUtils.loadAnimation(this,com.example.nafis.agrosewa.R.anim.scanneranim)
        binding.scanMoveUp.startAnimation(scanneranim)

//        val moveDown=AnimationUtils.loadAnimation(this,com.example.nafis.agrosewa.R.anim.movedownscaner)
//        binding.scanMoveDown.startAnimation(moveDown)

    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener({
            cameraProvider = cameraProviderFuture.get()
            val preview = androidx.camera.core.Preview.Builder().build()
                .also {
                    it.setSurfaceProvider(binding.viewFinder.surfaceProvider)
                }

            imageCapture = ImageCapture.Builder().build()

            cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                cameraProvider.unbindAll()
                camera = cameraProvider.bindToLifecycle(
                    this, cameraSelector, preview, imageCapture)

            } catch (exc: Exception) {
                Log.e(TAG, "Use case binding failed", exc)
            }

        }, ContextCompat.getMainExecutor(this))
    }



    private fun takePhoto() {
        val imageCapture = imageCapture ?: return

        val photoFile = File(externalMediaDirs.firstOrNull(), "example.jpg")
        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

        imageCapture.takePicture(
            outputOptions, ContextCompat.getMainExecutor(this), object : ImageCapture.OnImageSavedCallback {
                override fun onError(exc: ImageCaptureException) {
                    Log.e(TAG, "Photo capture failed: ${exc.message}", exc)
                }

                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    progress.toastShow(this@Camera,"Click")
                    val msg = "Photo capture succeeded: ${output.savedUri}"
                    Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
                    updateUIVisibility(View.GONE, View.GONE, View.VISIBLE, View.VISIBLE,View.GONE,View.GONE,View.GONE)
                    binding.scanMoveUp.clearAnimation()
                    val imageUri = output.savedUri
                    progress.toastShow(this@Camera,"Click")
                    binding.scanningimg.setImageURI(imageUri)
                    output.savedUri?.let { uri ->
                        analyzeImage(uri)
                    }
                }
            }
        )
    }


    private fun setFlashIcon() {
        val camera = this.camera ?: run {
            Toast.makeText(this, "Camera not initialized", Toast.LENGTH_SHORT).show()
            return
        }

        if (camera.cameraInfo.hasFlashUnit()) {
            if (camera.cameraInfo.torchState.value == TorchState.OFF) {
                camera.cameraControl.enableTorch(true)
                binding.flashoff.setImageResource(drawable.flashon)
            } else {
                camera.cameraControl.enableTorch(false)
                binding.flashoff.setImageResource(drawable.flashoff)
            }
        } else {
            Toast.makeText(this, "Flash is not available", Toast.LENGTH_LONG).show()
            binding.flashoff.isEnabled = false
        }
    }




    private fun analyzeImage(uri: Uri) {
        try {
            val image = InputImage.fromFilePath(this, uri)
            labeler?.let { labeler ->
                labeler.process(image)
                    .addOnSuccessListener { labels ->
                        for (label in labels) {
                            val text = label.text
                            val confidence = label.confidence
                            val index = label.index
                            Log.d(TAG, "Label: $text, Confidence: $confidence, Index: $index")
                        }
                    }
                    .addOnFailureListener { e ->
                        Log.e(TAG, "Image labeling failed", e)
                    }
            } ?: run {
                Log.e(TAG, "Labeler is not initialized.")
            }
        } catch (e: Exception) {
            Log.e(TAG, "Failed to load image", e)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }

    companion object {
        private const val TAG = "CameraActivity"
    }


    override fun onBackPressed() {
        if (binding.scanningimg.visibility == View.VISIBLE) {
            updateUIVisibility(View.VISIBLE, View.VISIBLE, View.GONE, View.GONE,View.VISIBLE,View.VISIBLE,View.VISIBLE)
            startAnimation()
        } else {
            super.onBackPressed()
        }
    }

    private fun updateUIVisibility(viewFinderVisibility: Int, cameraShutterVisibility: Int, scanningImgVisibility: Int, processBtnVisibility: Int,flashoffBtnVisibility: Int,scanUpVisibility:Int,scanBorder:Int) {
        binding.viewFinder.visibility = viewFinderVisibility
        binding.camerashutterbtn.visibility = cameraShutterVisibility
        binding.scanningimg.visibility = scanningImgVisibility
        binding.processbtn.visibility = processBtnVisibility
        binding.flashoff.visibility=flashoffBtnVisibility
        binding.scanMoveUp.visibility=scanUpVisibility
        binding.scannigBorder.visibility=scanBorder

    }
}
