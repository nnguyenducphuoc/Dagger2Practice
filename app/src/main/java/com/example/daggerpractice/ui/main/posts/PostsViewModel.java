package com.example.daggerpractice.ui.main.posts;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.daggerpractice.SessionManager;
import com.example.daggerpractice.di.network.main.MainApi;
import com.example.daggerpractice.models.Post;
import com.example.daggerpractice.ui.main.Resource;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class PostsViewModel extends ViewModel {
    private static final String TAG = "PostsViewModel";

    private MediatorLiveData<Resource<List<Post>>> posts;

    // inject
    private final SessionManager sessionManager;
    private final MainApi mainApi;

    @Inject
    public PostsViewModel(SessionManager sessionManager, MainApi mainApi) {
        this.mainApi = mainApi;
        this.sessionManager = sessionManager;
        Log.d(TAG, "PostsViewModel");
    }

    public LiveData<Resource<List<Post>>> observerPosts() {
        if (posts == null) {
            posts = new MediatorLiveData<>();
            posts.setValue(Resource.loading((List<Post>)null));

            final LiveData<Resource<List<Post>>> source = LiveDataReactiveStreams
                    .fromPublisher(
                            mainApi.getPostsFromUser(sessionManager.getAuthUser().getValue().data.getId())
                                    .onErrorReturn(new Function<Throwable, List<Post>>() {
                                        @Override
                                        public List<Post> apply(Throwable throwable) throws Throwable {
                                            Log.e(TAG, "apply: ", throwable);
                                            Post post = new Post();
                                            post.setId(-1);
                                            ArrayList<Post> posts = new ArrayList<>();
                                            posts.add(post);
                                            return posts;
                                        }
                                    })
                                    .map(new Function<List<Post>, Resource<List<Post>>>() {
                                        @Override
                                        public Resource<List<Post>> apply(List<Post> posts) throws Throwable {
                                            if (posts.size()>0) {
                                                if (posts.get(0).getId() == -1) {
                                                    return Resource.error("wrong", null);
                                                }
                                            }
                                            return Resource.success(posts);
                                        }
                                    })
                                    .subscribeOn(Schedulers.io())
                    );
            posts.addSource(source, new Observer<Resource<List<Post>>>() {
                @Override
                public void onChanged(Resource<List<Post>> listResource) {
                    posts.setValue(listResource);
                    posts.removeSource(source);
                }
            });
        }
        return posts;
    }
}
