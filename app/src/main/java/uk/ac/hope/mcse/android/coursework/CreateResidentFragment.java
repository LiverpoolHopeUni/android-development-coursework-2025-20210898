package uk.ac.hope.mcse.android.coursework;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import java.util.Objects;

import uk.ac.hope.mcse.android.coursework.databinding.FragmentCreateResidentBinding;

public class CreateResidentFragment extends Fragment {

    private FragmentCreateResidentBinding binding;
    private Uri residentProfilePictureUri;

    // When this launcher is used, it prompts the user to select an image from their device
    // When an image has been chosen, the residentProfilePicture variable is set to the file path of
    // the selected image.
    private final ActivityResultLauncher<Intent> getPictureLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                    Intent data = result.getData();
                    residentProfilePictureUri = data.getData();
                    binding.showResidentPictureImageview.setImageURI(residentProfilePictureUri);
                } else {
                    binding.showResidentPictureImageview.setImageResource(R.drawable.default_profile_icon_50x50);
                }
            });

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCreateResidentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            Bundle args = getArguments();
            if (args != null) {
                String targetFragment = args.getString("targetFragment", "");

                if (targetFragment.equals("FirstFragment")) {
                    SharedResidentsViewModel viewModel = new ViewModelProvider(requireParentFragment()).get(SharedResidentsViewModel.class);
                }

                else if (targetFragment.equals("SecondFragment")) {
                    SharedResidentsViewModel viewModel = new ViewModelProvider(requireParentFragment()).get(SharedResidentsViewModel.class);
                }

            }

            // When the user clicks the select profile picture button, the image launcher is ran
            binding.getResidentPictureButton.setOnClickListener(v -> {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                getPictureLauncher.launch(intent); // Launch the activity for result
            });

            // When the "Create" button is clicked,
            // data inside the textInputLayouts will be taken and added into the list from the
            // ResidentsViewModel class.
            binding.createResidentButton.setOnClickListener(v -> {

                // Handling if the user does or doesn't select a profile picture.
                Uri profile_picture = (residentProfilePictureUri != null) ?
                        residentProfilePictureUri :
                        Uri.parse("android.resource://" + getContext().getPackageName() + "/" + R.drawable.default_profile_icon_50x50);


                int room_number = Integer.parseInt(Objects.requireNonNull(binding.enterRoomNumberTextinputlayout.getEditText()).getText().toString());
                String name = Objects.requireNonNull(binding.enterNameTextinputlayout.getEditText()).getText().toString();
                int age = Integer.parseInt(Objects.requireNonNull(binding.enterAgeTextinputlayout.getEditText()).getText().toString());
                String bio = Objects.requireNonNull(binding.enterBioTextinputlayout.getEditText()).getText().toString();

                Bundle result = new Bundle();

                result.putParcelable("new_resident", new Resident(profile_picture, room_number, name, age, bio));

                getParentFragmentManager().setFragmentResult("resident_created", result);

                NavHostFragment.findNavController(this).popBackStack();
            });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
