package com.example.projectweb;

import android.os.AsyncTask;

import com.example.projectweb.chat.User;

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

public class AsyncSignup extends AsyncTask<User, Void, Integer> {
    private Exception exception;
    private static final Object BASE_URL = "https://rev-o-dates.social:8000";
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


    protected Integer doInBackground(User... users) {
        Integer code = 1;
        Token token = new Token("", "");

        try {
            OkHttpClient client = new OkHttpClient.Builder().
                    sslSocketFactory(CertIt().getSocketFactory()).build();
            RequestBody requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("name", users[0].getUsername())
                    .addFormDataPart("password", users[0].getPassword())
                    .addFormDataPart("gender", users[0].getGender())
                    .addFormDataPart("age", users[0].getAge())
                    .build();

            Request request = new Request.Builder()
                    .url(BASE_URL + "/api/auth/signup/")
                    .post(requestBody)
                    .build();
            try {
                Response response = client.newCall(request).execute();
                if(response.isSuccessful()){
                    String jsonData = response.body().string();
                    JSONObject Jobject = new JSONObject(jsonData);

                    token.setAccess(Jobject.getString("access"));
                    token.setRefresh(Jobject.getString("refresh"));
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
        return code;
    }

    protected void onPostExecute(Void v) {
        // TODO: check this.exception
        // TODO: do something with the feed
    }
}
