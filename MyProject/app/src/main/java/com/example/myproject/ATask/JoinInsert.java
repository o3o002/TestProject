package com.example.myproject.ATask;

import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import static com.example.myproject.CommonMethod.CommonMethod.ipConfig;

public class JoinInsert extends AsyncTask<Void, Void, String> {

    String id, passwd, name, phonenumber, address;

    public JoinInsert(String id, String passwd, String name, String phonenumber, String address) {
        this.id = id;
        this.passwd = passwd;
        this.name = name;
        this.phonenumber = phonenumber;
        this.address = address;
    }

    // 데이터베이스에 삽입결과 0보다크면 삽입성공, 같거나 작으면 실패
    String state = "";

    //이건 통신할 때 무조건 만들어야함!!!
    HttpClient httpClient;
    HttpPost httpPost;
    HttpResponse httpResponse;
    HttpEntity httpEntity;

    @Override
    protected String doInBackground(Void... voids) {
        try {
            // MultipartEntityBuild 생성


            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            builder.setCharset(Charset.forName("UTF-8"));

            // 문자열 및 데이터 추가
            builder.addTextBody("id", id, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("passwd", passwd, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("name", name, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("phonenumber", phonenumber, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("address", address, ContentType.create("Multipart/related", "UTF-8"));

            String postURL = ipConfig + "/app/anJoin";

            // 전송
            InputStream inputStream = null; //받는부분
            httpClient = AndroidHttpClient.newInstance("Android"); //클라이언트 객체 만들고
            httpPost = new HttpPost(postURL);   //port 붙이고
            httpPost.setEntity(builder.build()); //build를 붙이고
            httpResponse = httpClient.execute(httpPost); //post를 붙여서 실행을 시키는 거! 서버에 던지는 부분
            httpEntity = httpResponse.getEntity();  //서버에 던진 것을 넣어서
            inputStream = httpEntity.getContent();

            // 응답 => String 형태로 받을 때
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            StringBuilder stringBuilder = new StringBuilder();
            String line = null;
            while ((line = bufferedReader.readLine()) != null){
                stringBuilder.append(line + "\n");
            }
            state = stringBuilder.toString();

            inputStream.close();
        }catch (Exception e) {

        }finally {

        }


        return state;


    }

    @Override
    protected void onPostExecute(String state) {
        super.onPostExecute(state);


    }
}
