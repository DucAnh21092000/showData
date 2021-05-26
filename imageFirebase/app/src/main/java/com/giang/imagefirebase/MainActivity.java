package com.example.imagefirebase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class MainActivity extends AppCompatActivity {

    private static final int Gallery_Code =1;
    Uri imageUrl= null;
    ProgressDialog progressDialog;
    DatabaseReference mRef;
    FirebaseStorage mStorage;

    FirebaseDatabase mDatabase;

     ImageButton imageButton;
     Button mUpload;
     EditText mName;
     EditText mAddress;
     EditText mHovsTen;
     EditText mMSSV_Khoa;
     EditText mTruong;
     EditText mVien;
     EditText mCpa;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageButton = findViewById(R.id.img_button);
        mUpload = findViewById(R.id.button_upload);
        mName  = findViewById(R.id.edit_name);
        mAddress = findViewById(R.id.edit_address);
        mHovsTen = findViewById(R.id.editHovsTen);
        mMSSV_Khoa = findViewById(R.id.edit_mssv_khoa);
        mTruong = findViewById(R.id.edit_truong);
        mVien = findViewById(R.id.edit_vien);
        mCpa = findViewById(R.id.edit_cpa);


         mDatabase = FirebaseDatabase.getInstance();
         mRef = mDatabase.getReference().child("Sinh Vien");
         mStorage = FirebaseStorage.getInstance();
         progressDialog = new ProgressDialog(this);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, Gallery_Code);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Gallery_Code && resultCode == RESULT_OK){
            imageUrl =  data.getData();
            imageButton.setImageURI(imageUrl);
        }

        mUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Name = mName.getText().toString().trim();
                String Address = mAddress.getText().toString().trim();
                String HovsTen = mHovsTen.getText().toString().trim();
                String MSSV_Khoa = mMSSV_Khoa.getText().toString().trim();
                String Truong = mTruong.getText().toString().trim();
                String Vien = mVien.getText().toString().trim();
                String Cpa = mCpa.getText().toString().trim();

                if (!(Name.isEmpty() && Address.isEmpty() && HovsTen.isEmpty() && MSSV_Khoa.isEmpty() && Truong.isEmpty() && Vien.isEmpty() && Cpa.isEmpty()  && imageUrl!= null)){
                    progressDialog.setTitle("Uploading...");
                    progressDialog.show();


                    StorageReference imagePath = mStorage.getReference().child("ImagePost").child(imageUrl.getLastPathSegment());
                     imagePath.putFile(imageUrl).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                         @Override
                         public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                             Task<Uri> downloadUrl = taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                 @Override
                                 public void onComplete(@NonNull Task<Uri> task) {
                                     String  t = task.getResult().toString();

                                     DatabaseReference newPost = mRef.push();

                                     newPost.child("Name").setValue(Name);
                                     newPost.child("Address").setValue(Address);
                                     newPost.child("HovsTen").setValue(HovsTen);
                                     newPost.child("MSSV_Khoa").setValue(MSSV_Khoa);
                                     newPost.child("Trường").setValue(Truong);
                                     newPost.child("Viện").setValue(Vien);
                                     newPost.child("CPA").setValue(Cpa);



                                     newPost.child("image").setValue(task.getResult().toString());
                                     progressDialog.dismiss();

                                     Intent intent = new Intent(MainActivity.this,RecyclerViewMenu.class);
                                     startActivity(intent);

                                     //
                                 }
                             });
                         }
                     });
                }
            }
        });
    }
}