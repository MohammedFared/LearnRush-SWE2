package com.learnrush.addgame.view.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.learnrush.R;
import com.learnrush.addgame.model.GameModel;
import com.squareup.picasso.Picasso;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddGameDetailsFragmentListener} interface
 * to handle interaction events.
 */
public class AddGameDetailsFragment extends Fragment {

    private String TAG = "AddGameDetailsFragtLOG";

    private EditText nameET, descriptionET;
    private Spinner categorySpinner;
    private ImageView addImageBtn;
    private Button continueBtn;
    private Uri image;

    private AddGameDetailsFragmentListener mListener;
    private int PICK_IMAGE = 100;

    public AddGameDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mView = inflater.inflate(R.layout.fragment_add_game_details, container, false);
        nameET = (EditText) mView.findViewById(R.id.et_game_name);
        categorySpinner = (Spinner) mView.findViewById(R.id.spinner_category);
        descriptionET = (EditText) mView.findViewById(R.id.et_game_desc);
        addImageBtn = (ImageView) mView.findViewById(R.id.img_game_btn);
        continueBtn = (Button) mView.findViewById(R.id.btn_continue_Questions);
        return mView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onContinueClicked(view);
            }
        });
        addImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAddImageClicked(view);
            }
        });
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.CATEGORY_ARRAY, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        categorySpinner.setAdapter(adapter);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof AddGameDetailsFragmentListener) {
            mListener = (AddGameDetailsFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement AddGameDetailsFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    private StorageReference mStorageRef = FirebaseStorage.getInstance().getReference();

    public void onContinueClicked(View view) {
        String name = nameET.getText().toString();
        String desc = descriptionET.getText().toString();
        String category = String.valueOf(categorySpinner.getSelectedItem());

        validate(name, category, desc, String.valueOf(image));

//        StorageReference riversRef = mStorageRef.child("gameImages").child("image.jpg");
//        riversRef.putFile(image)
//                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                    @Override
//                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                        // Get a URL to the uploaded content
//                        String downloadUrl = taskSnapshot.getDownloadUrl().toString();
//                        Log.d(TAG, "onSuccess: " + downloadUrl);
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception exception) {
//                        Log.e(TAG, "onFailure: ", exception);
//                        // Handle unsuccessful uploads
//                        // ...
//                    }
//                });

        GameModel gameModel = new GameModel(name, category, desc, image);

        if (mListener != null) {
            mListener.onGameDetailsContinueClicked(gameModel);
        } else
            Toast.makeText(getActivity(), "Error AddGameDetailsFragment.mListener is null!!", Toast.LENGTH_SHORT).show();
    }

    private void validate(String image, String name, String category, String desc) {
        if (name.trim().length() == 0)
            nameET.setError("required");
        if (desc==null || desc.trim().length() == 0)
            descriptionET.setError("required");
        if (categorySpinner.getSelectedItemPosition() == 0)
            Toast.makeText(getActivity(), "Please choose one of the categories", Toast.LENGTH_LONG).show();
        if (image.trim().length() == 0)
            Toast.makeText(getActivity(), "Please selected game image", Toast.LENGTH_LONG).show();

    }

    public void onAddImageClicked(View view) {
        Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
        getIntent.setAction("image/*");

        Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setType("image/*");

        Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});

        startActivityForResult(chooserIntent, PICK_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            if (data == null) {
                Toast.makeText(getActivity(), "No Image Selected", Toast.LENGTH_SHORT).show();
                return;
            }
            image = data.getData();
            Log.d(TAG, "onActivityResult: " + image);
            Picasso.with(getActivity()).load(image).into(addImageBtn);
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface AddGameDetailsFragmentListener {
        void onGameDetailsContinueClicked(GameModel gameModel);
    }
}
