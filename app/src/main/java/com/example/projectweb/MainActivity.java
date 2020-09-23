package com.example.projectweb;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.androidnetworking.AndroidNetworking;
import com.example.projectweb.chat.MessageType;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketException;
import com.neovisionaries.ws.client.WebSocketExtension;
import com.neovisionaries.ws.client.WebSocketFactory;

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
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

public class MainActivity extends AppCompatActivity {

    public static Token token_;
    public static String username_;
    public static String lastChatter = "";
    public static String nowChatter = "";
    public static boolean isChat = false;
    public static String itsMe = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        startActivity(new Intent(this, LoginActivity.class));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton myFab = (FloatingActionButton) findViewById(R.id.addChatFloatingActionButton);
        myFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    onAddChatClick();
                } catch (WebSocketException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (CertificateException e) {
                    e.printStackTrace();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (KeyStoreException e) {
                    e.printStackTrace();
                } catch (KeyManagementException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        AndroidNetworking.initialize(getApplicationContext());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Включаем кнопку настроек
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_settings, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            // Открытие activity настроек
            case R.id.action_settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                break;

            default:
                break;
        }

        return true;
    }

    public static final String SERVER = "wss://rev-o-dates.social:8000/api/app/chat/?token=";
    public static final CountDownLatch CHAT_LATCH = new CountDownLatch(1);
    public static WebSocket WEB_SOCKET = null;

    private void onAddChatClick() throws IOException, WebSocketException, CertificateException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException, InterruptedException {
        // установили соединение
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 111);
        // Вызываем всякие REST-штуки чтобы получить инфу
        AsyncGetUser getUser = (AsyncGetUser) new AsyncGetUser();
        Context context = getApplicationContext();
        // Получаем в итоге токен
        String user = null;

        try {
            user = getUser.execute(MainActivity.token_).get();
            if(user.isEmpty()){
                Toast toastee = Toast.makeText(context, "Could not get user information", Toast.LENGTH_LONG);
                toastee.show();
            }else{
                try {
                    JSONObject userJson = new JSONObject(user);
                    MainActivity.itsMe = user;
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        WEB_SOCKET = connect();
        WEB_SOCKET.sendText("{\"type\":\"init.connection\"}");

        CHAT_LATCH.await();
        Intent intent = new Intent(this, ChatActivity.class);
        startActivity(intent);
    }

    private static SSLContext CertIt() throws CertificateException, IOException, KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
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


    private static WebSocket connect() throws IOException, WebSocketException, CertificateException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        return new WebSocketFactory()
                .setVerifyHostname(false)
                .setConnectionTimeout(5000)
                .setSSLSocketFactory(CertIt().getSocketFactory())
                .createSocket(SERVER+MainActivity.token_.getAccess())
                .addListener(new WebSocketAdapter() {
                    // A text message arrived from the server.
                    public void onTextMessage(WebSocket websocket, String message) throws JSONException {
                        Log.d("MSG", message);
                        JSONObject jsonka = new JSONObject(message);


                        if (jsonka.getString("type").equals("request.connection")){
                            String opponent = jsonka.getString("user");
                            String herchannel = jsonka.getString("channel");
                            if(!isChat && !lastChatter.equals(opponent)){
                                websocket.sendText(String.format("{\"type\":\"accept.connection\"," +
                                        "\"user\":%s,\"channel\":\"%s\"}", jsonka.getString("user"), herchannel));
                                Log.d("DBG", String.format("{\"type\":\"accept.connection\"," +
                                        "\"user\":%s,\"channel\":\"%s\"}", jsonka.getString("user"), herchannel));
                                MainActivity.nowChatter = opponent;
                                isChat = true;
                            } else if (nowChatter.equals(opponent)){
                                websocket.sendText(String.format("{\"type\":\"connection.established\"," +
                                        "\"user\":%s," +
                                        "\"channel\":\"%s\"}", jsonka.getString("user"), herchannel));
                                CHAT_LATCH.countDown();

                            } else {
                                websocket.sendText(String.format("{\"type\":\"deny.connection\"," +
                                        "\"user\":\"%s\",\"channel\":\"%s\"}", jsonka.getString("user"), herchannel));
                            }
                        }
                        else if(jsonka.getString("type").equals("connection.established")){
                            CHAT_LATCH.countDown();
                        }
                        else if(jsonka.getString("type").equals("deny.connection")){

                        }
                        else if(jsonka.getString("type").equals("message")){
                            JSONObject msg = jsonka.getJSONObject("message");
                            if(msg.getString("author").equals(nowChatter)){
                                // рисуем чужое смс
                                ChatActivity.getInstance().AddMessage(msg.getString("text"), MessageType.received);
                            }
                        }
                    }
                })
                .addExtension(WebSocketExtension.PERMESSAGE_DEFLATE)
                .connect();
    }

    public static void showMessageBox(String text, Context context) {
        AlertDialog.Builder dlgAlert = new AlertDialog.Builder(context);
        dlgAlert.setMessage(text);
        dlgAlert.setPositiveButton("OK", null);
        dlgAlert.setCancelable(false);
        dlgAlert.create().show();
    }
}