package uk.ac.hope.mcse.android.coursework;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
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

        binding.unit1Resident1Button.setOnClickListener(v ->
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_unit_one_to_unit_one_resident_one)
        );
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @SuppressLint("SetTextI18n")
    public void createNewResidentButton() {
        // Stores the fragment's context
        // Returns IllegalStateException if the fragment isn't attached to an activity
        Context context = requireContext();

        // Initializing the new button
        MaterialButton newButton = new MaterialButton(context);

        // Setting its attributes
        newButton.setId(View.generateViewId());
        newButton.setText(getString(R.string.new_resident_button_label));
        newButton.setBackgroundColor(ContextCompat.getColor(context, R.color.green));
        newButton.setTextColor(ContextCompat.getColor(context, R.color.black));
        //newButton.setIcon(ContextCompat.getDrawable(context, R.drawable.profile_icon_50x50));
        newButton.setIconGravity(MaterialButton.ICON_GRAVITY_TOP);

        // This is necessary to remove the borders from the buttons
        ShapeAppearanceModel shapeAppearanceModel = new ShapeAppearanceModel()
                .toBuilder()
                .setAllCornerSizes(0f)  // Sets the button's corner radius to 0
                .build();

        newButton.setShapeAppearanceModel(shapeAppearanceModel);

        // Getting the first fragment's ConstraintLayout using its id
        ConstraintLayout layout = binding.fragmentFirstConstraintLayout;
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
                R.id.unit_1_resident_1_button,
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

        // Class instance method is called when the button is clicked
        newButton.setOnClickListener(v -> {

        });

    }

}