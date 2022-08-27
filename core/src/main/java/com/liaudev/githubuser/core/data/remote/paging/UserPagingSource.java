package com.liaudev.githubuser.core.data.remote.paging;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.paging.PagingState;
import androidx.paging.rxjava2.RxPagingSource;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.liaudev.githubuser.core.data.remote.network.ApiRequest;
import com.liaudev.githubuser.core.data.remote.network.Constants;
import com.liaudev.githubuser.core.data.remote.network.CustomRequest;
import com.liaudev.githubuser.core.data.remote.response.UserResponse;
import com.liaudev.githubuser.core.domain.model.User;
import com.liaudev.githubuser.core.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.Single;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Budiliauw87 on 2022-06-22.
 * budiliauw87.github.io
 * Budiliauw87@gmail.com
 */
public class UserPagingSource extends RxPagingSource<String, User> {
    private final ApiRequest api;
    private String mQuery;
    private int methodQuery = 0;

    public UserPagingSource(String query, ApiRequest apiRequest, int method) {
        this.api = apiRequest;
        this.mQuery = query;
        this.methodQuery = method;
    }

    @Nullable
    @Override
    public String getRefreshKey(@NonNull PagingState<String, User> pagingState) {
        return null;
    }

    @NonNull
    @Override
    public Single<LoadResult<String, User>> loadSingle(@NonNull LoadParams<String> loadParams) {
        String cursor = loadParams.getKey() != null ? loadParams.getKey() : "";

        mQuery = mQuery != null ? mQuery : "";
        return fetchUser(mQuery, cursor)
                .subscribeOn(Schedulers.io())
                .map((Function<UserResponse, LoadResult<String, User>>) userResponse -> {
                    return new LoadResult.Page(userResponse.getUserList(), cursor.isEmpty() ? null : cursor, userResponse.getNextCursor());
                }).onErrorReturn(LoadResult.Error::new);
    }

    private Single<UserResponse> fetchUser(String query, String lastCursor) {
        return Single.create((emitter) -> {
            try {
                final String queryGraph = Utils.getQueryGraph(query, lastCursor, methodQuery);
                JSONObject jsonparams = new JSONObject();
                jsonparams.put("query", queryGraph);
                final String payload = jsonparams.toString();
                api.addToRequestQueue(new CustomRequest(Request.Method.POST, Constants.URL_GRAPH_GITHUB, null, response -> {
                    List<User> userList = new ArrayList<>();
                    String finalCursor = null;
                    try {
                        if (response.has("data")) {
                            JSONArray jsonArray = null;
                            JSONObject jsonData = response.getJSONObject("data");

                            if(jsonData.has("search")){
                                JSONObject searchobj = jsonData.getJSONObject("search");
                                jsonArray = searchobj.getJSONArray("edges");
                            }
                            if(jsonData.has("user")){
                                JSONObject jsonuser = jsonData.getJSONObject("user");
                                if(jsonuser.has("followers")){
                                    JSONObject jsonuserfollowers = jsonuser.getJSONObject("followers");
                                    jsonArray = jsonuserfollowers.getJSONArray("edges");
                                }
                                if(jsonuser.has("following")){
                                    JSONObject jsonuserfollowing = jsonuser.getJSONObject("following");
                                    jsonArray = jsonuserfollowing.getJSONArray("edges");
                                }
                            }

                            if(jsonArray !=null){
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObj = jsonArray.getJSONObject(i);
                                    JSONObject jsonnode = jsonObj.getJSONObject("node");
                                    User user = new User();
                                    user.setId(jsonnode.getString("id"));
                                    user.setName(jsonnode.getString("name"));
                                    user.setLogin(jsonnode.getString("login"));
                                    user.setAvatarUrl(jsonnode.getString("avatarUrl"));
                                    user.setCompany(jsonnode.getString("company"));
                                    user.setLocation(jsonnode.getString("location"));
                                    user.setEmail(jsonnode.getString("email"));

                                    JSONObject jsonfollowers = jsonnode.getJSONObject("followers");
                                    JSONObject jsonfollowing = jsonnode.getJSONObject("following");
                                    JSONObject jsonrepositories = jsonnode.getJSONObject("repositories");
                                    user.setRepositories(jsonrepositories.getInt("totalCount"));
                                    user.setFollower(jsonfollowers.getInt("totalCount"));
                                    user.setFollowing(jsonfollowing.getInt("totalCount"));
                                    user.setCursor(jsonObj.getString("cursor"));
                                    if (i == jsonArray.length() - 1)
                                        finalCursor = jsonObj.getString("cursor");
                                    userList.add(user);
                                }
                            }
                            emitter.onSuccess(new UserResponse(userList, finalCursor));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        emitter.onError(e);
                    }

                }, error -> {
                    final String errorMsg = api.parseNetworkError(error);
                    emitter.onError(new VolleyError(errorMsg));
                }) {
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        return api.getHeaders();
                    }

                    @Override
                    public byte[] getBody() {
                        return payload == null ? null : payload.getBytes(StandardCharsets.UTF_8);
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
                emitter.onError(e);
            }
        });
    }
}
