package uk.ac.hope.mcse.android.coursework;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import java.util.Objects;

import uk.ac.hope.mcse.android.coursework.databinding.FragmentCreateResidentBinding;

public class CreateResidentFragment extends Fragment {

    private FragmentCreateResidentBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCreateResidentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.createResidentButton.setOnClickListener(v -> {
            int room_number = Integer.parseInt(Objects.requireNonNull(binding.enterRoomNumberTextinputlayout.getEditText()).getText().toString());
            String name = Objects.requireNonNull(binding.enterNameTextinputlayout.getEditText()).getText().toString();
            int age = Integer.parseInt(Objects.requireNonNull(binding.enterAgeTextinputlayout.getEditText()).getText().toString());
            String bio = Objects.requireNonNull(binding.enterBioTextinputlayout.getEditText()).getText().toString();

            Bundle result = new Bundle();
            result.putInt("room_number", room_number);
            result.putString("name", name);
            result.putInt("age", age);
            result.putString("bio", bio);
            getParentFragmentManager().setFragmentResult("newResident", result);
            NavHostFragment.findNavController(this).popBackStack();
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
