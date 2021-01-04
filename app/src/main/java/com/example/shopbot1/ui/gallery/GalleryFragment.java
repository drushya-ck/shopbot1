package com.example.shopbot1.ui.gallery;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.shopbot1.R;
import  com.example.shopbot1.ui.gallery.GalleryViewModel;
import com.google.firebase.auth.FirebaseAuth;


public class GalleryFragment extends Fragment {

    private GalleryViewModel galleryViewModel;
    FirebaseAuth firebaseAuth;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                ViewModelProviders.of(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        firebaseAuth= FirebaseAuth.getInstance();

        TextView userName = (TextView) root.findViewById(R.id.atvUsernameReg);
        userName.setText(firebaseAuth.getCurrentUser().getDisplayName());
        TextView emailId = (TextView) root.findViewById(R.id.atvEmailReg);
        emailId.setText(firebaseAuth.getCurrentUser().getEmail());
        TextView phoneNum = (TextView) root.findViewById(R.id.atvPhoneReg);
        phoneNum.setText(firebaseAuth.getCurrentUser().getPhoneNumber());
        Button button = root.findViewById(R.id.edit);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                Intent activityChangeIntent = new Intent(getActivity(),editpro.class);

                // currentContext.startActivity(activityChangeIntent);

                GalleryFragment.this.startActivity(activityChangeIntent);
            }
        });
        return root;
    }

}