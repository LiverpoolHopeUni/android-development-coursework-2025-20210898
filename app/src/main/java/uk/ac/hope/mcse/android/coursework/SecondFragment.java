package uk.ac.hope.mcse.android.coursework;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
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

import uk.ac.hope.mcse.android.coursework.databinding.FragmentSecondBinding;


/**
 *
 */
public class SecondFragment extends Fragment {

    private FragmentSecondBinding binding;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentSecondBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonSecond.setOnClickListener(v ->
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment)
        );

        // These methods are triggered when the buttons are clicked
        // When clicked, they send resident's data to the resident template fragment to be displayed
        binding.unit2Resident1Button.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putInt("imageResID", R.drawable.old_man_profile_icon_512x512_02);
            bundle.putInt("room_number", 1);
            bundle.putString("name", getString(R.string.william_wilson_string));
            bundle.putInt("age", 79);
            bundle.putString("bio", "William was a watch maker.");

            NavHostFragment.findNavController(SecondFragment.this)
                    .navigate(R.id.action_SecondFragment_to_ResidentFragment, bundle);
        });

        binding.unit2Resident2Button.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putInt("imageResID", R.drawable.old_woman_profile_icon_256x256_02);
            bundle.putInt("room_number", 2);
            bundle.putString("name", getString(R.string.lucy_lucinda_string));
            bundle.putInt("age", 91);
            bundle.putString("bio", "Lucy was a dress maker.");

            NavHostFragment.findNavController(SecondFragment.this)
                    .navigate(R.id.action_SecondFragment_to_ResidentFragment, bundle);
        });

        ResidentsViewModel viewModel = new ViewModelProvider(requireActivity()).get(ResidentsViewModel.class);

        viewModel.getResidents().observe(getViewLifecycleOwner(), residents -> {
            for (Resident r : residents) {
                createNewResidentButton(r.getProfilePicture(), r.getRoomNumber(), r.getName(), r.getAge(), r.getBio());
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @SuppressLint("SetTextI18n")
    public void createNewResidentButton(Uri profile_picture, int room_number, String name, int age, String bio) {
        ResidentsViewModel viewModel = new ViewModelProvider(requireActivity()).get(ResidentsViewModel.class);

        // Issues arose when trying to make this new button use a predefined style in themes.xml
        // therefore, chosen attributes are changed manually here

        // Stores the fragment's context
        // Returns IllegalStateException if the fragment isn't attached to an activity
        Context context = requireContext();

        // Initializing the new button
        MaterialButton newButton = new MaterialButton(context);

        // Setting its attributes
        Drawable drawable = Drawable.createFromPath(profile_picture.getPath());
        newButton.setIcon(drawable);
        newButton.setId(View.generateViewId());
        newButton.setText(name);
        newButton.setBackgroundColor(ContextCompat.getColor(context, R.color.green));
        newButton.setTextColor(ContextCompat.getColor(context, R.color.black));
        //newButton.setIcon(ContextCompat.getDrawable(context, R.drawable.profile_icon_50x50));
//        newButton.setIconGravity(MaterialButton.ICON_GRAVITY_TOP);

        // This is necessary to remove the borders from the buttons
        ShapeAppearanceModel shapeAppearanceModel = new ShapeAppearanceModel()
                .toBuilder()
                .setAllCornerSizes(0f)  // Sets the button's corner radius to 0
                .build();

        newButton.setShapeAppearanceModel(shapeAppearanceModel);

        // Getting the first fragment's ConstraintLayout using its ID
        ConstraintLayout layout = binding.fragmentSecondConstraintLayout;
        layout.addView(newButton);

        // This ConstraintSet object clones the first fragment's xml layout
        // and allows us to change constraints programmatically
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(layout);

        int MARGIN = 5;

        // Positioning the new button correctly
        constraintSet.connect(
                newButton.getId(),
                ConstraintSet.TOP,
                R.id.unit_2_resident_1_button,
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
            NavHostFragment.findNavController(SecondFragment.this)
                    .navigate(R.id.action_SecondFragment_to_ResidentFragment, bundle);
        });

    }

}