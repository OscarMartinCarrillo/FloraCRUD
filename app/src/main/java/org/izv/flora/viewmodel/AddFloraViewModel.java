package org.izv.flora.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import org.izv.flora.model.Repository;
import org.izv.flora.model.entity.Flora;
import org.izv.flora.model.entity.RowsResponse;

public class AddFloraViewModel extends AndroidViewModel {

    private Repository repository;

    public AddFloraViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
    }

    public MutableLiveData<Long> getAddFloraLiveData() {

        return repository.getAddFloraLiveData();
    }

    public void createFlora(Flora flora) {
        repository.createFlora(flora);
    }

    public MutableLiveData<RowsResponse> getEditLiveData() {
        return repository.getEditLiveData();
    }

    public void editFlora(long id, Flora flora) {
        repository.editFlora(id, flora);
    }
}