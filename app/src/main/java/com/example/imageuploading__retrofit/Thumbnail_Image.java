package com.example.imageuploading__retrofit;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

public class Thumbnail_Image extends AppCompatActivity {
ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thumbnail__image);
    imageView=findViewById(R.id.viewIMage);
    showImage();
    }

    public void showImage(){
        Bundle bundle=getIntent().getExtras();
        Bitmap images = (Bitmap) bundle.getParcelable("bits"); // or use getStringExtra() if you have url instead of resource id.
        Log.d(MainActivity.class.getSimpleName(),"image in thumbnail:::::"+images);
        imageView.setImageBitmap(images);
    }
}

