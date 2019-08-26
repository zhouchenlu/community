package zhou.hardcat.communtiy.provider;

import com.alibaba.fastjson.JSON;
import okhttp3.*;
import org.springframework.stereotype.Component;
import zhou.hardcat.communtiy.datatransferobject.AccessTokenDTO;
import zhou.hardcat.communtiy.datatransferobject.GithubUser;

import java.io.IOException;

@Component
public class GithubProvider {
    //调用GitHub  的 Api accsesstoken
    public String getAccessToken(AccessTokenDTO accessTokenDTO) {
        MediaType mt
                = MediaType.get("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(mt, JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            String[] split = string.split("&");
            String[] split1 = split[0].split("=");
            return split1[1];
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public GithubUser getUser(String accessToken) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token=" + accessToken)
                .build();

        try (
                Response response = client.newCall(request).execute()) {
            String responseBody = response.body().string();
            GithubUser githubUser = JSON.parseObject(responseBody, GithubUser.class);
            return githubUser;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
