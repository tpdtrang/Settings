package com.example.trang.settings;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

import org.json.JSONObject;

import java.util.Arrays;
import java.util.Locale;

import sqlite.MainOpinion;

public class MainActivity extends AppCompatActivity {
    TextView tvchiase, tvdanhgia, tvykien;
    CallbackManager callbackManager;
    Button btnadd;
    private final int MENU_LANGUAGE_EN = 0;
    private final int MENU_LANGUAGE_VI = 1;
    LinearLayout layoyshare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sp = getPreferences(MODE_PRIVATE);
        String language = sp.getString("key_language", "");
        if (language.equals("")) {
            language = "en";
        }
        setLanguage(language);
        setContentView(R.layout.activity_main);
        init();
        tvykien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MainOpinion.class);
                startActivity(intent);

            }
        });


        layoyshare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FacebookSdk.sdkInitialize(getApplicationContext());
                callbackManager = CallbackManager.Factory.create();


//                dong no tạo vao show dialog
                final Dialog dialog = new Dialog(MainActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialog.setContentView(R.layout.row_shared);
                LoginButton btnlogin;
                btnlogin = dialog.findViewById(R.id.btnlogin);
                btnlogin.setReadPermissions(Arrays.asList("public_profile", "email"));
                setLogin_Button(btnlogin, dialog);
                dialog.show();
            }
        });


    }

    public void init() {
        tvchiase = (TextView) findViewById(R.id.tvchiase);
        tvdanhgia = (TextView) findViewById(R.id.tvdanhgia);
        tvykien = findViewById(R.id.tvykien);
        layoyshare = findViewById(R.id.layoyshare);
    }

    public void showSelectLanguage(View view) {
        final String[] languages = getResources().getStringArray(R.array.languages);
        new AlertDialog.Builder(this)
                .setTitle("Languages")
                .setItems(languages, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String language = "";
                        switch (which) {
                            case MENU_LANGUAGE_EN:
                                language = "en";
                                break;
                            case MENU_LANGUAGE_VI:
                                language = "vi";
                                break;
                        }
                        setLanguage(language);

                        //refresh layout
                        setContentView(R.layout.activity_main);
                        init();
                        tvykien.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(MainActivity.this, MainOpinion.class);
                                startActivity(intent);

                            }
                        });
                        layoyshare.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                FacebookSdk.sdkInitialize(getApplicationContext());
                                callbackManager = CallbackManager.Factory.create();


//                dong no tạo vao show dialog
                                final Dialog dialog = new Dialog(MainActivity.this);
                                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                                dialog.setContentView(R.layout.row_shared);
                                LoginButton btnlogin;
                                btnlogin = dialog.findViewById(R.id.btnlogin);
                                btnlogin.setReadPermissions(Arrays.asList("public_profile", "email"));
                                setLogin_Button(btnlogin, dialog);
                                dialog.show();
                            }
                        });


                    }
                })
                .create()
                .show();
    }

    private void setLanguage(String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration configuration = getResources().getConfiguration();
        configuration.locale = locale;
        getResources().updateConfiguration(configuration, getResources().getDisplayMetrics());

        SharedPreferences sp = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("key_language", language);
        editor.commit();
    }


    private void setLogin_Button(final LoginButton btnlogin, final Dialog dialog) {
        btnlogin.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                result();
                ShareDialog shareDialog;
                ShareLinkContent shareLinkContent = null;
                shareDialog = new ShareDialog(MainActivity.this);

                if (shareDialog.canShow(ShareLinkContent.class)) {
                    shareLinkContent = new ShareLinkContent.Builder().setContentTitle("abc").setContentDescription("abc")
                            .setContentUrl(Uri.parse("https://developers.facebook.com/apps/104663747075390/dashboard/")).build();
                }
                shareDialog.show(shareLinkContent);
                dialog.dismiss();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
    }

    private void result() {
        GraphRequest graphRequest = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                Log.d("Json", response.getJSONObject().toString());
//                profilePictureView.setProfileId(Profile.getCurrentProfile().getId());
            }
        });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "name,email,first_name");
        graphRequest.setParameters(parameters);
        graphRequest.executeAsync();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }


    public void showRating(View view) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Rating");
        builder.setCancelable(true);
        builder.setMessage("Do you like app?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String link = "https://developers.facebook.com/apps/104663747075390/dashboard/";
                Uri uri = Uri.parse(link);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setNeutralButton("Not now", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }


}

