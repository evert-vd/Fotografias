package com.apps.evertvd.fotografias;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 1;
    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 2;

    private static final String TAG = "CameraActivity";

    private Button btnCamara;
    private ImageView img;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //btnCamara = (Button) findViewById(R.id.btnCamara);
        //btnCamara.setOnClickListener(this);


        //Relacionamos con el XML
        img = (ImageView)this.findViewById(R.id.imgMostrar);
        btnCamara = (Button) this.findViewById(R.id.btn_camara);
        btnCamara.setOnClickListener(this);

        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int permissionCheck2 = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);


        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

            } else {

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);

            }
        }


        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.CAMERA)) {

            } else {

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA},
                        MY_PERMISSIONS_REQUEST_CAMERA);

            }
        }


    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //Comprovamos que la foto se a realizado
        if (requestCode == 1 && resultCode == RESULT_OK) {
            //Creamos un bitmap con la imagen recientemente
            //almacenada en la memoria
            Bitmap bMap = BitmapFactory.decodeFile(
                    Environment.getExternalStorageDirectory()+
                            "/FotosPruebasAF/"+"foto.jpg");
            //Añadimos el bitmap al imageView para
            //mostrarlo por pantalla
            img.setImageBitmap(bMap);
        }
    }


    @Override
    public void onClick(View v) {

        if (v.getId()==R.id.btn_camara){
            //Creamos el Intent para llamar a la Camara
            Intent cameraIntent = new Intent(
                    android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            //Creamos una carpeta en la memeria del terminal
            File imagesFolder = new File(
                    Environment.getExternalStorageDirectory(), "FotosPruebasAF");
            imagesFolder.mkdirs();
            //añadimos el nombre de la imagen
            File image = new File(imagesFolder, "foto.jpg");
            Uri uriSavedImage = Uri.fromFile(image);
            //Le decimos al Intent que queremos grabar la imagen
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriSavedImage);
            //Lanzamos la aplicacion de la camara con retorno (forResult)
            startActivityForResult(cameraIntent, 1);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 1
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {

                }
                return;
            }
            case MY_PERMISSIONS_REQUEST_CAMERA: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 2 && grantResults[1] == PackageManager.PERMISSION_GRANTED) {

                } else {

                }
                return;
            }
        }

    }

}
