package net.simplifiedcoding.androidpermissions

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*
import java.util.jar.Manifest

@RequiresApi(Build.VERSION_CODES.M)
class MainActivity : AppCompatActivity() {

    // Step #1
    // we need an instance of ActivityResultLauncher first
    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Step #2
        // we will save the instance by calling the function registerForActivityResult()
        // It takes ActivityResultContracts.RequestPermission()
        // And inside the trailing lambda we will get the callback
        // whenever user will allow or deny a permission
        // with the help of the boolean value inside the trailing lambda
        // we can check whether the permission was granted or not
        requestPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
                textView.text = if (granted) {
                    "Storage Permission Granted"

                    //Here you can go with the action that requires permission
                    //For now I am just showing a normal String in TextView
                } else {
                    "Storage Permission NOT Granted"

                    //Here you need to skip the functionality that requires permission
                    //Because user has denied the permission
                    //you can ask permission again when user will request the feature
                    //that requires this permission
                }
            }

        button.setOnClickListener {
            // OnClick of the Button we will request the WRITE_EXTERNAL_STORAGE permission
            // It is just for the example you can request the permission that you requires

            // Step #3
            // Request Permission
            requestStoragePermission()
        }
    }

    private fun requestStoragePermission(){
        when {
            ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED -> {
                // The permission is granted
                // you can go with the flow that requires permission here
            }
            shouldShowRequestPermissionRationale(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) -> {
                // This case means user previously denied the permission
                // So here we can display an explanation to the user
                // That why exactly we need this permission
                showPermissionRequestExplanation(
                    getString(R.string.write_storage),
                    getString(R.string.permission_request)
                ) { requestPermissionLauncher.launch(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) }
            }
            else -> {
                // Everything is fine you can simply request the permission
                requestPermissionLauncher.launch(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
        }
    }
}