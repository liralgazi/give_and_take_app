package com.example.giveandtake.model;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import androidx.core.os.HandlerCompat;

import androidx.lifecycle.MutableLiveData;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class UserModel {
    private static final UserModel _instance = new UserModel();

    private Executor executor = Executors.newSingleThreadExecutor();
    private Handler mainHandler = HandlerCompat.createAsync(Looper.getMainLooper());
    private FireBaseModel fireBaseModel = new FireBaseModel();
    AppLocalDbRepository localDb = AppLocalDb.getAppDb();
    private FireBaseModel firebaseModel = new FireBaseModel();

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

    final public MutableLiveData<LoadingState> EventUsersListLoadingState = new MutableLiveData<LoadingState>(LoadingState.NOT_LOADING);

    public void getAllUsers(){
        executor.execute(()->{
            List<User> data = localDb.userDao().getAll();
        });
    }

    private User getUser(String id){
        User user = localDb.userDao().getUserById(id);
        return user;
    }

    private Post getPost(String id){
        Post post = localDb.postDao().getPostById(id);
        return post;
    }


    public void refreshAllUsers(){
        EventUsersListLoadingState.setValue(LoadingState.LOADING);
        // get local last update
        Long localLastUpdate = User.getLocalLastUpdate();
        // get all updated recorde from firebase since local last update
        firebaseModel.getAllUsersSince(localLastUpdate,list->{
            executor.execute(()->{
                Log.d("TAG", " firebase return : " + list.size());
                Long time = localLastUpdate;
                for(User st:list){
                    // insert new records into ROOM
                    localDb.userDao().insertAll(st);
                    if (time < st.getLastUpdated()){
                        time = st.getLastUpdated();
                    }
                }
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // update local last update
                User.setLocalLastUpdate(time);
                EventUsersListLoadingState.postValue(LoadingState.NOT_LOADING);
            });
        });
    }
    public void refreshAllPosts(){
        EventUsersListLoadingState.setValue(LoadingState.LOADING);
        // get local last update
        Long localLastUpdate = Post.getLocalLastUpdate();
        // get all updated recorde from firebase since local last update
        firebaseModel.getAllPostsSince(localLastUpdate,list->{
            executor.execute(()->{
                Log.d("TAG", " firebase return : " + list.size());
                Long time = localLastUpdate;
                for(Post post:list){
                    // insert new records into ROOM
                    localDb.userDao().insertAll();
                    if (time < post.getLastUpdated()){
                        time = post.getLastUpdated();
                    }
                }
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // update local last update
                Post.setLocalLastUpdate(time);
                EventUsersListLoadingState.postValue(LoadingState.NOT_LOADING);
            });
        });
    }
    public void addUser(User user){
        firebaseModel.addUser(user);
    }

    public void addPost(Post post,  Listener<Void> listener){
        firebaseModel.addPost(post,(Void)->{
            refreshAllPosts();
            listener.onComplete(null);
        });
    }

    public void uploadImage(String name, Bitmap bitmap, Listener<String> listener) {
        firebaseModel.uploadImage(name,bitmap,listener);
    }

}
