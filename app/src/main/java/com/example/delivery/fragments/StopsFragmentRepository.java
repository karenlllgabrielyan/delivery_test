package com.example.delivery.fragments;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.delivery.dao.MarkDao;
import com.example.delivery.databases.MarkDatabase;
import com.example.delivery.templates.Mark;

import java.util.ArrayList;

public class StopsFragmentRepository {

    private MarkDao markDao;
    private LiveData<ArrayList<Mark>> marks;
//    private MutableLiveData<ArrayList<Mark>> liveData;


    public StopsFragmentRepository(Application application) {
        MarkDatabase database = MarkDatabase.getInstance(application);
        markDao = database.markDao();
        marks = markDao.getAllMarks();
    }

    public void insert(Mark mark) {

    }

    public void update(Mark mark) {

    }

    public void delete(Mark mark){

    }

    public void deleteAllMarks(){

    }

    public LiveData<ArrayList<Mark>> getMarks() {
        return marks;
    }

    //    public void initMarksList(MutableLiveData<ArrayList<Mark>> markListLiveData){
//        liveData = markListLiveData;
//        ArrayList<Mark> marks = new ArrayList<>();
//        marks.add(new Mark("asas", "asasas","asas","asas","asas","as",4));
//        liveData.postValue(marks);
//    }

}
