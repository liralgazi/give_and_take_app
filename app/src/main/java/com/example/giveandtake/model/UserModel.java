package com.example.giveandtake.model;
import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


public class UserModel {
    private Executor executor = Executors.newSingleThreadExecutor();
    private static final UserModel _instance = new UserModel();
    private FireBaseModel firebaseModel = new FireBaseModel();
    AppLocalDbRepository localDb = AppLocalDb.getAppDb();
    final public MutableLiveData<LoadingState> EventUserListLoadingState = new MutableLiveData<LoadingState>(LoadingState.NOT_LOADING);
    public static UserModel instance(){
        return _instance;
    }

    private UserModel(){
    }

    public interface Listener<T>{
        void onComplete(T data);
    }

    public enum LoadingState{
        LOADING,
        NOT_LOADING

    }

    private LiveData<List<User>> usersList;
    public LiveData<List<User>> getAllUsers() {
        if(usersList == null){
            usersList = localDb.userDao().getAll();
            refreshAllUsers();
        }
        return usersList;
    }

    User user;
    public User getUserById(String id){
        firebaseModel.getUserById(id);
        return user;
    }

    public void refreshAllUsers(){
        EventUserListLoadingState.setValue(LoadingState.LOADING);
        // get local last update
        Long localLastUpdate;
        if(User.getLocalLastUpdate().equals("users_local_last_update"))
            localLastUpdate = 0L;
        else
            localLastUpdate = User.getLocalLastUpdate();
        // get all updated recorde from firebase since local last update
        firebaseModel.getAllUsersSince(localLastUpdate,list->{
            executor.execute(()->{
                Log.d("TAG", " firebase return : " + list.size());
                Long time = localLastUpdate;
                for(User user:list){
                    // insert new records into ROOM
                    localDb.userDao().insertAll(user);
                    if (time < user.getLastUpdated()){
                        time = user.getLastUpdated();
                    }
                }
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // update local last update
                User.setLocalLastUpdate(time);
                EventUserListLoadingState.postValue(LoadingState.NOT_LOADING);
            });
        });
    }

    public void addUser(User user, Listener<Void> listener){
        firebaseModel.addUser(user,(Void)->{
            refreshAllUsers();
            listener.onComplete(null);
        });
    }

//    public void uploadImage(String name, Bitmap bitmap, Listener<String> listener) {
//        firebaseModel.uploadImage(name,bitmap,listener);
//    }
}


