package uk.ac.hope.mcse.android.coursework;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class ResidentsViewModel extends ViewModel {

    private final MutableLiveData<List<Resident>> residents = new MutableLiveData<>(new ArrayList<>());

    public LiveData<List<Resident>> getResidents() {
        return residents;
    }

    public void addResident(Resident resident) {
        List<Resident> currentList = residents.getValue();
        if (currentList == null) {
            currentList = new ArrayList<>();
        }

        currentList.add(resident);
        residents.setValue(new ArrayList<>(currentList));
    }
}
