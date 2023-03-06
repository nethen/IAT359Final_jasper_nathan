package com.example.arcanamini;

import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.common.util.concurrent.ListenableFuture;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraProvider;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;

public class CameraActivity extends AppCompatActivity implements View.OnClickListener {

    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;
    PreviewView previewView;
    private CameraProvider cameraProvider;

    private Button buttonCaptureShow, saveButton;
    private ImageCapture imageCapture;
    private ImageView imageViewCaptured;

    private static final int img_id = 1;

    // new
    private Executor executor = Executors.newSingleThreadExecutor();
    private int REQUEST_CODE_PERMISSIONS = 1001;
    private final String[] REQUIRED_PERMISSIONS = new String[]{"android.permission.CAMERA"};

    //after image taken
    TextView notes, time;
    EditText noteContent;
    String date;
    ReadDatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        buttonCaptureShow = findViewById(R.id.buttonCaptureShow);
//        previewView = findViewById(R.id.previewView);
//        previewView.setVisibility(View.GONE);
        imageViewCaptured = findViewById(R.id.imageViewCapturedImg);
        imageViewCaptured.setVisibility(View.GONE);

        buttonCaptureShow.setOnClickListener(this);

        //after image taken items
        saveButton = findViewById(R.id.newReflectionSaveButton);
        saveButton.setOnClickListener(this);
        saveButton.setVisibility(View.GONE);
        notes = findViewById(R.id.newReflectionNotesTextView);
        notes.setVisibility(View.GONE);
        time = findViewById(R.id.newReflectionTimeTextView);
        time.setVisibility(View.GONE);
        noteContent = findViewById(R.id.newReflectionContentEditText);
        noteContent.setVisibility(View.GONE);

        databaseHelper = new ReadDatabaseHelper(this);
        databaseHelper.getWritableDatabase();


        if (allPermissionsGranted()) {
            startCamera(); //start camera if permission has been granted by user
        } else {
            ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS);
        }

        //retrieve data
        Bundle extra_data = getIntent().getExtras();
        if (extra_data!= null) {
            date = extra_data.getString("DATE");
            time.setText(date);
        } else{
            Toast.makeText(this,"No data recieved", Toast.LENGTH_SHORT);
        }

    }

    private Executor getExecutor() {
        return ContextCompat.getMainExecutor(this);
    }

    private void startCamera() {

        final ListenableFuture<ProcessCameraProvider> cameraProviderFuture = ProcessCameraProvider.getInstance(this);

        cameraProviderFuture.addListener(new Runnable() {
            @Override
            public void run() {
                try {
                    ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
//                    bindPreview(cameraProvider);

                } catch (ExecutionException | InterruptedException e) {
                    // This should never be reached.
                }
            }
        }, ContextCompat.getMainExecutor(this));
    }

//    void bindPreview(@NonNull ProcessCameraProvider cameraProvider) {
//
//        cameraProvider.unbindAll();
//
//        Preview preview = new Preview.Builder()
//                .build();
//
//        CameraSelector cameraSelector = new CameraSelector.Builder()
//                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
//                .build();
//
//
//        imageCapture = new ImageCapture.Builder()
//                .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
//                .build();
////        preview.setSurfaceProvider(previewView.getSurfaceProvider());
//        cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture);
//    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonCaptureShow: {
                //capturePhoto();   //for saving an image!!!
                Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(camera_intent, img_id);
                break;
            }
            case R.id.newReflectionSaveButton: {
                //save reflection
                String content = noteContent.getText().toString();
                databaseHelper.insertDataReflection(date, content);
                int position = databaseHelper.getLength() - 1;
                Intent intent = new Intent (this, ReflectionDetailsActivity.class);
                intent.putExtra ("ITEM_KEY", position);
                this.startActivity(intent);
                Toast.makeText(this,"saved", Toast.LENGTH_LONG).show();
                break;
            }
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap photo = (Bitmap) data.getExtras().get("data");
        //after taking an image:
        imageViewCaptured.setImageBitmap(photo);
        imageViewCaptured.setVisibility(View.VISIBLE);
        buttonCaptureShow.setText(R.string.retakePhotoButtonText);
        notes.setVisibility(View.VISIBLE);
        time.setVisibility(View.VISIBLE);
        noteContent.setVisibility(View.VISIBLE);
        saveButton.setVisibility(View.VISIBLE);
    }

//    private void capturePhoto() {
//        long timeStamp = System.currentTimeMillis();
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, timeStamp);
//        contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg");
//
//
//        imageCapture.takePicture(
//                new ImageCapture.OutputFileOptions.Builder(
//                        getContentResolver(),
//                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
//                        contentValues
//                ).build(),
//                getExecutor(),
//                new ImageCapture.OnImageSavedCallback() {
//                    @Override
//                    public void onImageSaved(@NonNull ImageCapture.OutputFileResults outputFileResults) {
//                        Toast.makeText(CameraActivity.this, "Saving...", Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onError(@NonNull ImageCaptureException exception) {
//                        Toast.makeText(CameraActivity.this, "Error: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                });
//    }

    private boolean allPermissionsGranted() {

        for (String permission : REQUIRED_PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }
}
