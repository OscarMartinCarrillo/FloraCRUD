package org.izv.flora.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import org.izv.flora.model.Repository;
import org.izv.flora.model.entity.Flora;
import org.izv.flora.model.entity.Imagen;

import java.util.ArrayList;

public class MainActivityViewModel extends AndroidViewModel {

    private Repository repository;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
    }

    public MutableLiveData<ArrayList<Flora>> getFloraLiveData() {
        return repository.getFloraLiveData();
    }

    public void deleteFlora(long id) {
        repository.deleteFlora(id);
    }

    public MutableLiveData<Long> getDeleteLiveData() {
        return repository.getDeleteLiveData();
    }

    public void getFlora(long id) {
        repository.getFlora(id);
    }

    public void getFlora() {
        repository.getFlora();
    }

    public void createFlora(Flora flora) {
        repository.createFlora(flora);
    }

    public void editFlora(long id, Flora flora) {
        repository.editFlora(id, flora);
    }

    public void getImagenes(long id){
        repository.getImagenes(id);
    }

    public MutableLiveData<ArrayList<Imagen>> getImagenesLiveData() {
        return repository.getImagenesLiveData();
    }

    public MutableLiveData<Flora> getOneFlora() {
        return repository.getOneFlora();
    }
}