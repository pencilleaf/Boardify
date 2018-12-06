package com.hi.boardify;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.json.JSONArray;

import java.io.IOException;
import java.util.ArrayList;

public class FragSaved extends Fragment {
    ArrayList<ImageModel> data;
    JSONArray request;
    GalleryAdapter mAdapter;
    DatabaseReference mRootDatabaseRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference databaseReference;
    DataHolder dataHolder;
    FirebaseStorage storage;
    StorageReference storageReference;
    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri filePath;


    public FragSaved() {

        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_frag_saved, container, false);
        dataHolder = DataHolder.getInstance();
        databaseReference = mRootDatabaseRef.child(dataHolder.getUserID());
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        super.onCreate(savedInstanceState);
        data = new ArrayList<>();
        final RecyclerView mRecyclerView = view.findViewById(R.id.list);
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),2);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.setHasFixedSize(true);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
                // Opens up Gallery and waits for a pick

            }

        });

        databaseReference.addValueEventListener(
                new ValueEventListener() {
                    @Override
                    //listen for changes, the entire set of data is passed to data snapshot
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        data.clear();
                        for (DataSnapshot datasnapshot : dataSnapshot.getChildren()) {
                            ImageModel imageModel = datasnapshot.getValue(ImageModel.class);
                            data.add(imageModel);
                        }

                        mAdapter = new GalleryAdapter(getActivity(), data);
                        mRecyclerView.setAdapter(mAdapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                }
        );

        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(),
                new RecyclerItemClickListener.OnItemClickListener() {

                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent = new Intent(getActivity(), DetailActivity.class);
                        intent.putParcelableArrayListExtra("data", data);
                        intent.putExtra("pos", position);
                        intent.putExtra("Uniqid", "DownloadBoards");
                        startActivity(intent);

                    }
                }));


        return view;
    }
    @Override
   public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == getActivity().RESULT_OK
                && data != null && data.getData() != null )
        {
            filePath = data.getData();
            try {
                //Converts the picture from Gallery into bitmap and calls uploadImage method
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath);
                uploadImage();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
    private void uploadImage() {

        if(filePath != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(getActivity());
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            //dataHolder stores the integer count of the number of uploads which helps to name the file in order as well for uploads/deletes.
            //So we generate a file name called upload + whatever number count it is at. This has to be persistent so that the we will not replace when we try to upload again
            // Note this child is also what appears on the Firebase Storage so it is the name to use when deleting as well
            StorageReference ref = storageReference.child("images/"+ "upload" + dataHolder.uploadCount);
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(getActivity(), "Uploaded", Toast.LENGTH_SHORT).show();
                            Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl(); //EXTRACT The URL, because that is what we want to store in the database
                            while (!urlTask.isSuccessful());
                            String downloadUrl = urlTask.getResult().toString();
                            ImageModel uploadImage = new ImageModel(downloadUrl,"upload"+dataHolder.uploadCount); //create a new image model with our new URL and name
                            uploadDatabase(uploadImage); // So that we have a copy on database and it loads on DataUpdate
                            dataHolder.adduploadCount(); //increment uploadCount, take note that dataHolder is a Singleton class
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(getActivity(), "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    // displays the loading percentage while uploading
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded "+(int)progress+"%");
                        }
                    });
        }
    }
    private void uploadDatabase(ImageModel uploadImage){
        databaseReference.child(uploadImage.getName()).setValue(uploadImage); //Name that is pushed up onto the Firebase Database
    }
}
