package com.example.permissiontest

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import com.example.permissiontest.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding

    private val cameraAndLocationResultLauncher:ActivityResultLauncher<Array<String>> =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()){
            permissions->
            permissions.entries.forEach{
                val permissionName = it.key
                val isGranted = it.value
                if (isGranted){
                    when (permissionName) {
                        Manifest.permission.ACCESS_FINE_LOCATION -> {
                            Toast.makeText(this,"Permission granted for fine location",Toast.LENGTH_LONG).show()
                        }
                        Manifest.permission.ACCESS_COARSE_LOCATION -> {
                            Toast.makeText(this,"Permission granted for coarse location",Toast.LENGTH_LONG).show()
                        }
                        else -> {
                            Toast.makeText(this,"Permission granted for Camera",Toast.LENGTH_LONG).show()
                        }
                    }
                }else{
                    when (permissionName) {
                        Manifest.permission.ACCESS_FINE_LOCATION -> {
                            Toast.makeText(this,"Permission denied for fine location",Toast.LENGTH_LONG).show()
                        }
                        Manifest.permission.ACCESS_COARSE_LOCATION -> {
                            Toast.makeText(this,"Permission denied for coarse location",Toast.LENGTH_LONG).show()
                        }
                        else -> {
                            Toast.makeText(this,"Permission denied for Camera",Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.permission.setOnClickListener {
            if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)){
                showRationalDialog("Permission Demo requires camera access",
                    "Camera cannot be used because Camera access is denied")
            }else{
                cameraAndLocationResultLauncher.launch(
                    arrayOf(Manifest.permission.CAMERA,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                        )
                )
            }
        }
    }

    private fun showRationalDialog(
        title:String,
        message:String,
    ){
        val builder:AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle(title)
            .setMessage(message)
            .setPositiveButton("Cancel"){
                dialog,_->dialog.dismiss()
            }
        builder.create().show()
    }
}