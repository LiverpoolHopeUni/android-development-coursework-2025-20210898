// SharedResidentsViewModel.java
package uk.ac.hope.mcse.android.coursework;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import java.util.ArrayList;
import java.util.List;

public class SharedResidentsViewModel extends ViewModel {

    private final MutableLiveData<List<Resident>> firstFragmentResidents = new MutableLiveData<>(new ArrayList<>());
    private final MutableLiveData<List<Resident>> secondFragmentResidents = new MutableLiveData<>(new ArrayList<>());

    public LiveData<List<Resident>> getFirstFragmentResidents() {
        return firstFragmentResidents;
    }

    public LiveData<List<Resident>> getSecondFragmentResidents() {
        return secondFragmentResidents;
    }

    public void addResidentToFirstFragment(Resident resident) {
        List<Resident> current = firstFragmentResidents.getValue();
        if (current != null) {
            current.add(resident);
            firstFragmentResidents.setValue(current);
        }
    }

    public void addResidentToSecondFragment(Resident resident) {
        List<Resident> current = secondFragmentResidents.getValue();
        if (current != null) {
            current.add(resident);
            secondFragmentResidents.setValue(current);
        }
    }
}
