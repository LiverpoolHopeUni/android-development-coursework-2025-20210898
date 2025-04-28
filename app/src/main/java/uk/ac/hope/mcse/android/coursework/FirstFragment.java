package uk.ac.hope.mcse.android.coursework;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.button.MaterialButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import com.google.android.material.shape.ShapeAppearanceModel;

import uk.ac.hope.mcse.android.coursework.databinding.FragmentFirstBinding;


/**
 *
 */
public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonFirst.setOnClickListener(v ->
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment)
        );

        // These methods are triggered when the buttons are clicked
        // When clicked, they send resident's data to the resident template fragment to be displayed
        binding.unit1Resident1Button.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putInt("imageResID", R.drawable.old_man_profile_pic_512x512);
            bundle.putInt("room_number", 1);
            bundle.putString("name", "Steve Stevenson");
            bundle.putInt("age", 81);
            bundle.putString("bio", "Steve worked as a shoe maker for 20 years.");

            NavHostFragment.findNavController(FirstFragment.this)
                    .navigate(R.id.action_FirstFragment_to_ResidentFragment, bundle);
        });

        binding.unit1Resident2Button.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putInt("imageResID", R.drawable.old_woman__profile_pic_256x256);
            bundle.putInt("room_number", 2);
            bundle.putString("name", "Ellen Ellington");
            bundle.putInt("age", 78);
            bundle.putString("bio", "Ellen is a passionate painter.");

            NavHostFragment.findNavController(FirstFragment.this)
                    .navigate(R.id.action_FirstFragment_to_ResidentFragment, bundle);
        });

        // Getting the ViewModel object associated with the activity and creating buttons for each
        // resident in the resident list intended for the FirstFragment
        SharedResidentsViewModel viewModel = new ViewModelProvider(requireActivity()).get(SharedResidentsViewModel.class);

        viewModel.getFirstFragmentResidents().observe(getViewLifecycleOwner(), residents -> {
            for (Resident r : residents) {
                createNewResidentButton(r.getProfilePicture(), r.getRoomNumber(), r.getName(), r.getAge(), r.getBio());
            }
        });

        // Handle requests from CreateResidentFragment to create a new resident object and store it
        // in the SharedResidentsViewModel object's list for FirstFragment (Unit One)
        getParentFragmentManager().setFragmentResultListener(
            "resident_created",
            getViewLifecycleOwner(),
            (requestKey, bundle) -> {
                Resident newResident = bundle.getParcelable("new_resident");
                if (newResident != null) {
                    viewModel.addResidentToFirstFragment(newResident);
                }
            }
        );
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @SuppressLint("SetTextI18n")
    public void createNewResidentButton(Uri profile_picture, int room_number, String name, int age, String bio) {
        // Issues arose when trying to make this new button use a predefined style in themes.xml
        // therefore, chosen attributes are changed manually here

        // Stores the fragment's context
        // Returns IllegalStateException if the fragment isn't attached to an activity
        Context context = requireContext();

        // Initializing the new button
        MaterialButton newButton = new MaterialButton(context);

        // Setting its attributes
        newButton.setId(View.generateViewId());
        newButton.setText(name);
        newButton.setBackgroundColor(ContextCompat.getColor(context, R.color.green));
        newButton.setTextColor(ContextCompat.getColor(context, R.color.black));

        // This is necessary to remove the borders from the buttons
        ShapeAppearanceModel shapeAppearanceModel = new ShapeAppearanceModel()
                .toBuilder()
                .setAllCornerSizes(0f)  // Sets the button's corner radius to 0
                .build();

        newButton.setShapeAppearanceModel(shapeAppearanceModel);

        // Getting the first fragment's ConstraintLayout using its ID
        ConstraintLayout layout = binding.fragmentFirstConstraintLayout;

        int MARGIN = 5;

        // What the first button created will be anchored to
        int anchorViewID = R.id.unit_1_resident_1_button;

        layout.addView(newButton);

        // This ConstraintSet object clones the first fragment's xml layout
        // and allows us to change constraints programmatically
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(layout);

        // Positioning the new button correctly
        constraintSet.connect(
                newButton.getId(),
                ConstraintSet.TOP,
                anchorViewID,
                ConstraintSet.BOTTOM,
                MARGIN); // Connects the top of the new button to the bottom of unit_1_resident_1_button

        constraintSet.connect(
                newButton.getId(),
                ConstraintSet.BOTTOM,
                ConstraintSet.PARENT_ID,
                ConstraintSet.BOTTOM,
                MARGIN); // Connects the bottom of the new button to the bottom of parent

        constraintSet.connect(
                newButton.getId(),
                ConstraintSet.START,
                ConstraintSet.PARENT_ID,
                ConstraintSet.START,
                MARGIN); // Connects the start of the new button to the start of parent

        constraintSet.connect(
                newButton.getId(),
                ConstraintSet.END,
                ConstraintSet.PARENT_ID,
                ConstraintSet.END,
                MARGIN); // Connects the end of the new button to the end of parent

        // Now applying the constraints to fragment_first's ConstraintLayout
        constraintSet.applyTo(layout);

        // Now the new resident button has been created, when it is clicked it sends the information
        // given by a user to ResidentFragment which displays the information
        newButton.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putParcelable("profile_picture", profile_picture);
            bundle.putInt("room_number", room_number);
            bundle.putString("name", name);
            bundle.putInt("age", age);
            bundle.putString("bio", bio);
            NavHostFragment.findNavController(FirstFragment.this)
                    .navigate(R.id.action_FirstFragment_to_ResidentFragment, bundle);
        });

    }

}