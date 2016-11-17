package com.destroinc.medicinereminder;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Random;


/**
 * A simple {@link Fragment} subclass.
 */
public class InsertFragment extends Fragment implements View.OnClickListener {

    View view;
    private ImageButton takepicture;
    private ImageView medicineImage;
    private ImageView medicineImage2;
    private Button save;
    MedicineDatabaseHelper db;
    private EditText editText_name;
    private CheckBox checkBox_mor;
    private CheckBox checkBox_noon;
    private CheckBox checkBox_afternoon;
    private CheckBox checkBox_night;

    public InsertFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_insert, container, false);
        db = new MedicineDatabaseHelper(this.getContext());
        editText_name = (EditText)view.findViewById(R.id.editText_name);

        takepicture = (ImageButton)view.findViewById(R.id.takepicture);
        medicineImage = (ImageView)view.findViewById(R.id.medicineimage);
        medicineImage2 = (ImageView)view.findViewById(R.id.imageView);
        save = (Button)view.findViewById(R.id.insert);
        checkBox_mor = (CheckBox)view.findViewById(R.id.checkBox_morning);
        checkBox_noon = (CheckBox)view.findViewById(R.id.checkBox_noon);
        checkBox_afternoon = (CheckBox)view.findViewById(R.id.checkBox_afternoon);
        checkBox_night = (CheckBox)view.findViewById(R.id.checkBox_night);



        takepicture.setOnClickListener(this);
        save.setOnClickListener(this);

        return view;


    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Bitmap bp = (Bitmap) data.getExtras().get("data");
        medicineImage.setImageBitmap(bp);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.takepicture){
            Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            this.startActivityForResult(intent, 0);
        }
        else if (v.getId() == R.id.insert){
            /*boolean result = saveMedicine();

            if (result == true){
                Toast.makeText(this.getActivity(),"Data Inserted",Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(this.getActivity(),"Data Inserted",Toast.LENGTH_SHORT).show();
            }*/

            //String msg = writeFileToInternalStorage(this.getContext(), ((BitmapDrawable)medicineImage.getDrawable()).getBitmap());

            //String msg = SaveImage(this.getContext(), ((BitmapDrawable)medicineImage.getDrawable()).getBitmap());

            String msg = createDirectoryAndSaveFile(this.getContext(), ((BitmapDrawable)medicineImage.getDrawable()).getBitmap());

            //File imgFile = new  File(msg, "Image-191.jpg");

            try {
                File directory = this.getContext().getDir(msg+"//", Context.MODE_PRIVATE);
                File mypath =new File(directory,"Image-191.jpg");
            }
            catch (Exception e){
                Toast.makeText(this.getActivity(),e.toString(),Toast.LENGTH_SHORT).show();
            }




            //medicineImage2.setImageDrawable(Drawable.createFromPath(mypath.toString()));


        }


    }


    public boolean saveMedicine(){
        int morning, noon, afternoon, night;

        if (checkBox_mor.isChecked()){
            morning=1;
        }
        else {
            morning=0;
        }

        if (checkBox_noon.isChecked()){
            noon=1;
        }
        else{
            noon=0;
        }

        if (checkBox_afternoon.isChecked()){
            afternoon=1;
        }
        else{
            afternoon=0;
        }

        if (checkBox_night.isChecked()){
            night=1;
        }
        else{
            night=0;
        }

        //byte[] image = medicineImage;
        return true;
    }

    /*public String writeFileToInternalStorage(Context context, Bitmap outputImage) {
        String fileName = Long.toString(System.currentTimeMillis()) + ".png";

        final FileOutputStream fos;
        try {
            fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            outputImage.compress(Bitmap.CompressFormat.PNG, 90, fos);
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
        File cacheDir = context.getCacheDir();
        return (cacheDir+"/"+fileName).toString();
    }*/

    /*public String SaveImage(Context context, Bitmap outputImage) {

        String root = Environment.getExternalStorageDirectory().getAbsolutePath();
        Log.d("Msg",root.toString());
        File myDir = new File(root+"/MedicineReminder");
        Log.d("Msg",myDir.toString());

        if (!myDir.exists()){
            myDir.mkdirs();
            Log.d("Msg",myDir.toString());
        }
        Log.d("Msg","");

        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        String fname = "Image-"+ n +".jpg";

        File file = new File (myDir, fname);

        //if (file.exists ()) file.delete ();
        try {
            FileOutputStream out = new FileOutputStream(file);
            outputImage.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "nothing";

    }*/

    private String createDirectoryAndSaveFile(Context context, Bitmap fileName) {

        File direct = new File(context.getFilesDir(), "MedicineReminder");

        if (!direct.exists()) {
            direct.mkdirs();
        }

        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        String fname = "Image-"+ n +".jpg";

        File file = new File (direct, fname);

        //if (file.exists ()) file.delete ();
        try {
            FileOutputStream out = new FileOutputStream(file);
            fileName.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return direct.toString();
    }


}


//"/data/user/com.destroinc.medicinereminder/cache/1469208455139.png"