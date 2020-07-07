package com.example.delivery.fragments;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.delivery.base.BaseFragmentViewModel;
import com.example.delivery.templates.Mark;
import java.util.ArrayList;

public class StopsFragmentViewModel extends BaseFragmentViewModel {

    private StopsFragmentRepository repository = new StopsFragmentRepository();
    private MutableLiveData<ArrayList<Mark>> marksLiveData = new MutableLiveData<ArrayList<Mark>>();

    public LiveData<ArrayList<Mark>> getMarksLiveData (){
        return marksLiveData;
    }

    public void loadMarks(){
        repository.initMarksList(marksLiveData);
    }
}
