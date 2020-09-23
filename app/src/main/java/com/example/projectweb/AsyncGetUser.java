package com.example.projectweb;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AsyncGetUser extends AsyncTask<Object, Void, String> {
    private static final Object BASE_URL = "https://rev-o-dates.social:8000";
    private Exception exception;

    private SSLContext CertIt() throws CertificateException, IOException, KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        InputStream caInput = new BufferedInputStream(new FileInputStream("/storage/emulated/0/rev-o-dates-social-chain.crt"));
        Certificate ca;
        try {
            ca = cf.generateCertificate(caInput);
            System.out.println("ca=" + ((X509Certificate) ca).getSubjectDN());
        } finally {
            caInput.close();
        }

        String keyStoreType = KeyStore.getDefaultType();
        KeyStore keyStore = KeyStore.getInstance(keyStoreType);
        keyStore.load(null, null);
        keyStore.setCertificateEntry("ca", ca);

        String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
        TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
        tmf.init(keyStore);

        SSLContext context = SSLContext.getInstance("TLS");
        context.init(null, tmf.getTrustManagers(), null);

// Tell the URLConnection to use a SocketFactory from our SSLContext
        return context;
    }

    private String Refresh(Token tok){
        String access = "";
        try {
            OkHttpClient client = new OkHttpClient.Builder().
                    sslSocketFactory(CertIt().getSocketFactory()).build();

            RequestBody requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("refresh", tok.getRefresh())
                    .build();

            Request request = new Request.Builder()
                    .url(BASE_URL + "/api/auth/refresh/")
                    .addHeader("Authorization", "Bearer " + tok.getAccess())
                    .post(requestBody)
                    .build();
            try {
                Response response = client.newCall(request).execute();
                if (response.isSuccessful()) {
                    String jsonData = response.body().string();
                    JSONObject Jobject = new JSONObject(jsonData);

                    access = (Jobject.getString("access"));
                }
                else{
                    response.code();
                }
                // Do something with the response.
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        return access;
    }


    protected String doInBackground(Object... objs) {
        Token token = (Token) objs[0];
        //String username = (String) objs[1];
        String user = "";

        try {
            OkHttpClient client = new OkHttpClient.Builder().
                    sslSocketFactory(CertIt().getSocketFactory()).build();

            Request request = new Request.Builder()
                    .url(BASE_URL + "/api/app/user/")
                    .addHeader("Authorization", "Bearer " + token.getAccess())
                    .build();
            try {
                Response response = client.newCall(request).execute();
                if (response.isSuccessful()) {
                    String jsonData = response.body().string();
                    JSONObject Jobject = new JSONObject(jsonData);
                    user = Jobject.toString();
//                    user.setGender(Jobject.getString("gender"));
//                    user.setLastName(Jobject.getString("last_name"));
//                    user.setUsername(Jobject.getString("name"));
//                    user.setAge(Jobject.getString("age"));
//                    user.setFirstName(Jobject.getString("first_name"));
                }
                else if (response.code()==401){
                    OkHttpClient client2 = new OkHttpClient.Builder().
                            sslSocketFactory(CertIt().getSocketFactory()).build();

                    Request request2 = new Request.Builder()
                            .url(BASE_URL + "/api/app/user/")
                            .addHeader("Authorization:", "Bearer " + token.access)
                            .build();

                    token.setAccess(Refresh(token));
                    objs[0] = token;

                    Response response2 = client2.newCall(request2).execute();
                    if (response2.isSuccessful()) {
                        String jsonData = response2.body().string();
                        JSONObject Jobject = new JSONObject(jsonData);
                        user = Jobject.toString();
//                        user.setFirstName(Jobject.getString("first_name"));
//                        user.setGender(Jobject.getString("gender"));
//                        user.setLastName(Jobject.getString("last_name"));
//                        user.setUsername(Jobject.getString("name"));
//                        user.setAge(Jobject.getString("age"));
                    }
                }
                // Do something with the response.
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }

        // Если произошла ошибка, вернется пустой токен, иначе нормальный
        return user;
    }
}
