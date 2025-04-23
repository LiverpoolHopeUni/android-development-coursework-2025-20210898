package uk.ac.hope.mcse.android.coursework;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.button.MaterialButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;


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
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @SuppressLint("SetTextI18n")
    public void createNewResidentButton() {
        Context styledContext = new ContextThemeWrapper(requireContext(), R.style.ResidentButtonStyle);
        MaterialButton newButton = new MaterialButton(styledContext);

        newButton.setId(View.generateViewId());
        newButton.setText("New Resident");

        ConstraintLayout layout = binding.ConstraintLayoutForFragmentFirst;
        layout.addView(newButton);

        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(layout);

        int margin = 5;

        // Positioning the new button correctly
        constraintSet.connect(
                newButton.getId(),
                ConstraintSet.TOP,
                R.id.unit_1_resident_1_button,
                ConstraintSet.BOTTOM,
                margin); // Connects the top of the new button to the bottom of unit_1_resident_1_button

        constraintSet.connect(
                newButton.getId(),
                ConstraintSet.BOTTOM,
                ConstraintSet.PARENT_ID,
                ConstraintSet.BOTTOM,
                margin); // Connects the bottom of the new button to the bottom of parent

        constraintSet.connect(
                newButton.getId(),
                ConstraintSet.START,
                ConstraintSet.PARENT_ID,
                ConstraintSet.START,
                margin); // Connects the start of the new button to the start of parent

        constraintSet.connect(
                newButton.getId(),
                ConstraintSet.END,
                ConstraintSet.PARENT_ID,
                ConstraintSet.END,
                margin); // Connects the start of the new button to the start of parent


        // Now applying the constraints to fragment_first's ConstraintLayout
        constraintSet.applyTo(layout);

        // Class instance method is called when the button is clicked
        newButton.setOnClickListener(v -> {
        });
    }

}