package com.example.sampleapplication.login.roomdatabase;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import java.util.List;

public class UserRepository {

    public UserDao userDao;
    public LiveData<List<UserEntity>> getAllPosts;
    private UserDatabase userDatabase;
    public LiveData<Integer> getCount;


    public UserRepository(Application application){
        userDatabase=UserDatabase.getInstance(application);
        userDao= userDatabase.userDao();
        getAllPosts=userDao.getValues();
        getCount = userDao.getCount();
    }
    public LiveData<List<UserEntity>> getAllPosts(){
        return getAllPosts;
    }

//    public void insert(UserEntity userEntity) {
//        new insertAsyncTask(userDao).execute(userEntity);
//    }
//
//    private static class insertAsyncTask extends AsyncTask <List<UserEntity>,Void,Void> {
//        private UserDao PostuserDao;
//
//        public insertAsyncTask(UserDao userDao) {
//            this.PostuserDao = userDao;
//        }
//
//        @Override
//        protected Void doInBackground(List<UserEntity>... userEntities) {
//            PostuserDao.insert(userEntities[0]);
//            return null;
//        }
//    }
    public void InsertTask(final UserEntity userEntity)
    {
        new AsyncTask<Void,Void,Void>(){

            @Override
            protected Void doInBackground(Void... voids) {
                userDao.insert(userEntity);
                return null;
            }
            @Override
            protected void onPostExecute(Void aVoid){
                super.onPostExecute(aVoid);
                Log.d("data","database created");
            }
        }.execute();
    }
    public LiveData<Integer> getCount() {
        return getCount;
    }
}
