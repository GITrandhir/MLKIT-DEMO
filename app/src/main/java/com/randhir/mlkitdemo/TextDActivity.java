package com.randhir.mlkitdemo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;

public class TextDActivity extends AppCompatActivity {

    private ImageView capture , image;

    TextView text;


    static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_d);

        capture =  findViewById(R.id.im1);


        image = findViewById(R.id.imageview);

        text = (TextView) findViewById(R.id.textview);

        if(checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            //ask for camera
            requestPermissions(new String[]{Manifest.permission.CAMERA},101);
        }






    }

    public void doProcess(View view){
        // Open the camera and create an intent object
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,101);
    }

    // Once doProcess is over this function will be automatically called
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Bundle bundle = data.getExtras();
        //from bundle extract the image

        Bitmap bitmap = (Bitmap) bundle.get("data"); // Storing image in Bitmap

        // Now set image in imageview

        image.setImageBitmap(bitmap);

        // Process the image to extract text

        //1. create a FirebaseVisionImage object from a Bitmap object

        FirebaseVisionImage firebaseVisionImage = FirebaseVisionImage.fromBitmap(bitmap);

        //2. Get an instance of firebase vision

        FirebaseVision firebaseVision = FirebaseVision.getInstance();

        //3. Create an instance of FirebaseVisionTextRec....

        FirebaseVisionTextRecognizer firebaseVisionTextRecognizer = firebaseVision.getOnDeviceTextRecognizer();

        //4. Create a task to process the image

       Task<FirebaseVisionText> task =  firebaseVisionTextRecognizer.processImage(firebaseVisionImage);

       //5. If task is successful

            task.addOnSuccessListener(new OnSuccessListener<FirebaseVisionText>() {
                @Override
                public void onSuccess(FirebaseVisionText firebaseVisionText) {
                   String s = firebaseVisionText.getText();
                   text.setText(s);
                }
            });

        //6. If task failed

            task.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(TextDActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
                }
            });
    }


}