package uk.ac.hope.mcse.android.coursework;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import uk.ac.hope.mcse.android.coursework.databinding.FragmentResidentBinding;

public class ResidentFragment extends Fragment {

    private FragmentResidentBinding binding;

    public ResidentFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentResidentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle args = getArguments();
        if (args != null) {

            Uri profile_picture_uri = args.getParcelable("profile_picture");
            int profile_picture_not_uri = args.getInt("imageResID");
            int room_number = args.getInt("room_number", 0);
            String name = args.getString("name", "No name");
            int age = args.getInt("age", 0);
            String bio = args.getString("bio", "No bio");

            binding.residentProfilePictureImageview.setImageURI(profile_picture_uri != null ?
                    profile_picture_uri :
                    Uri.parse("android.resource://" + getContext().getPackageName() +
                            "/" + profile_picture_not_uri));
            binding.residentRoomNumberTextview.append(" " + room_number);
            binding.residentNameTextview.append(" " + name);
            binding.residentAgeTextview.append(" " + age);
            binding.residentBioTextview.append(" " + bio);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
