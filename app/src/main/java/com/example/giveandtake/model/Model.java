//package com.example.giveandtake.model;
//
//import android.graphics.Bitmap;
//import android.util.Log;
//
//import androidx.annotation.NonNull;
//import androidx.lifecycle.LiveData;
//import androidx.lifecycle.MutableLiveData;
//
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.Timestamp;
//import com.google.firebase.firestore.DocumentSnapshot;
//import com.google.firebase.firestore.QuerySnapshot;
//
//import java.util.LinkedList;
//import java.util.List;
//import java.util.concurrent.Executor;
//import java.util.concurrent.Executors;
//
//public class Model {
//    private static final Model _instance = new Model();
//
//    //private Executor executor = Executors.newSingleThreadExecutor();
//    public static Model instance(){
//        return _instance;
//    }
//    //AppLocalDbRepository localDb = AppLocalDb.getAppDb();
//    private FireBaseModel firebaseModel = new FireBaseModel();
//    private Model(){
//    }
//
//    public interface Listener<T>{
//        void onComplete(T data);
//    }
//
//
//    public enum LoadingState{
//        LOADING,
//        NOT_LOADING
//    }
//    final public MutableLiveData<LoadingState> EventUsersListLoadingState = new MutableLiveData<LoadingState>(LoadingState.NOT_LOADING);
//
//
//    private LiveData<List<User>> usersList;
//    public LiveData<List<User>> getAllUsers() {
//        if(usersList == null){
//            //usersList = (LiveData<List<User>>) localDb.userDao().getAll();
//            //refreshAllUsers();
//        }
//        return usersList;
//    }
//
////    private User getUser(String id){
////        //User user = localDb.userDao().getUserById(id);
////        //return user;
////    }
//
//
////    public void refreshAllUsers(){
////        EventUsersListLoadingState.setValue(LoadingState.LOADING);
////        // get local last update
////        Long localLastUpdate = User.getLocalLastUpdate();
////        // get all updated recorde from firebase since local last update
////        firebaseModel.getAllStudentsSince(localLastUpdate,list->{
////            executor.execute(()->{
////                Log.d("TAG", " firebase return : " + list.size());
////                Long time = localLastUpdate;
////                for(User st:list){
////                    // insert new records into ROOM
////                    localDb.userDao().insertAll(st);
////                    if (time < st.getLastUpdated()){
////                        time = st.getLastUpdated();
////                    }
////                }
////                try {
////                    Thread.sleep(3000);
////                } catch (InterruptedException e) {
////                    e.printStackTrace();
////                }
////                // update local last update
////                User.setLocalLastUpdate(time);
////                EventUsersListLoadingState.postValue(LoadingState.NOT_LOADING);
////            });
////        });
////    }
//
////    public void addStudent(User user, Listener<Void> listener){
////        firebaseModel.addStudent(user,(Void)->{
////            refreshAllUsers();
////            listener.onComplete(null);
////        });
////    }
////
////    public void uploadImage(String name, Bitmap bitmap, Listener<String> listener) {
////        firebaseModel.uploadImage(name,bitmap,listener);
////    }
//
//}
