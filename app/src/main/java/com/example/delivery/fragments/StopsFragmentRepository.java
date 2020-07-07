package com.example.delivery.fragments;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

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
//####################################################################################### DB METHODTS
    //------------------------------------------------------- INSERT
    public void insert(Mark mark) {
        new InsertMarkAsyncTask(markDao).execute(mark);
    }
    //------------------------------------------------------- UPDATE
    public void update(Mark mark) {
        new UpdateMarkAsyncTask(markDao).execute(mark);
    }
    //------------------------------------------------------- DELETE
    public void delete(Mark mark){
        new DeleteMarkAsyncTask(markDao).execute(mark);
    }
    //------------------------------------------------------- DELETE ALL
    public void deleteAllMarks(){
        new DeleteAllMarksAsyncTask(markDao).execute();
    }

    public LiveData<ArrayList<Mark>> getMarks() {
        return marks;
    }
//############################################################################################## DB METHOD ASYNC TASKS

    //----------------------------------------------------------------------------------- INSERT
    private static class InsertMarkAsyncTask extends AsyncTask<Mark, Void, Void>{

        private MarkDao markDao;

        private InsertMarkAsyncTask(MarkDao markDao){
            this.markDao = markDao;
        }

        @Override
        protected Void doInBackground(Mark... marks) {
            markDao.insert(marks[0]);
            return null;
        }
    }

    //----------------------------------------------------------------------------------- UPDATE
    private static class UpdateMarkAsyncTask extends AsyncTask<Mark, Void, Void>{

        private MarkDao markDao;

        private UpdateMarkAsyncTask(MarkDao markDao){
            this.markDao = markDao;
        }

        @Override
        protected Void doInBackground(Mark... marks) {
            markDao.update(marks[0]);
            return null;
        }
    }

    //----------------------------------------------------------------------------------- DELETE
    private static class DeleteMarkAsyncTask extends AsyncTask<Mark, Void, Void>{

        private MarkDao markDao;

        private DeleteMarkAsyncTask(MarkDao markDao){
            this.markDao = markDao;
        }

        @Override
        protected Void doInBackground(Mark... marks) {
            markDao.delete(marks[0]);
            return null;
        }
    }

    //----------------------------------------------------------------------------------- DELETE ALL MARKS
    private static class DeleteAllMarksAsyncTask extends AsyncTask<Void, Void, Void>{

        private MarkDao markDao;

        private DeleteAllMarksAsyncTask(MarkDao markDao){
            this.markDao = markDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            markDao.deleteAllMarks();
            return null;
        }
    }

    //    public void initMarksList(MutableLiveData<ArrayList<Mark>> markListLiveData){
//        liveData = markListLiveData;
//        ArrayList<Mark> marks = new ArrayList<>();
//        marks.add(new Mark("asas", "asasas","asas","asas","asas","as",4));
//        liveData.postValue(marks);
//    }

}
