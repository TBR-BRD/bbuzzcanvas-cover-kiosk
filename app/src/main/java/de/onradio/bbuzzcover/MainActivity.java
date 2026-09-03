package de.onradio.bbuzzcover;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;

public class MainActivity extends Activity {

    private static final String PREFS = "bbuzz";
    private static final String KEY_URL = "cover_url";

    private WebView webView;
    private String coverUrl;

    private final Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );

        getWindow().addFlags(
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
        );

        fullscreen();

        SharedPreferences prefs =
                getSharedPreferences(PREFS, MODE_PRIVATE);

        coverUrl =
                prefs.getString(KEY_URL, "");

        if (coverUrl.isEmpty()) {
            askForUrl();
        } else {
            startWebView();
        }
    }

    private void fullscreen() {

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        );
    }

    private void startWebView() {

        webView = new WebView(this);

        webView.setBackgroundColor(Color.BLACK);
        webView.setKeepScreenOn(true);

        WebSettings settings =
                webView.getSettings();

        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setLoadsImagesAutomatically(true);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setSupportZoom(false);

        webView.setWebViewClient(
                new WebViewClient() {

            @Override
            public void onReceivedError(
                    WebView view,
                    int errorCode,
                    String description,
                    String failingUrl) {

                handler.postDelayed(
                        () -> webView.loadUrl(coverUrl),
                        10000
                );
            }
        });

        webView.setOnLongClickListener(v -> {

            askForUrl();
            return true;
        });

        setContentView(webView);

        webView.loadUrl(coverUrl);
    }

    private void askForUrl() {

        final EditText input =
                new EditText(this);

        input.setHint(
                "http://<PI-IP>:8080/cover"
        );

        input.setText(coverUrl);

        new AlertDialog.Builder(this)

                .setTitle("BBuzz Cover")

                .setMessage(
                        "Cover-URL des Raspberry Pi:"
                )

                .setView(input)

                .setPositiveButton(
                        "Speichern",
                        (dialog, which) -> {

                            String value =
                                    input.getText()
                                            .toString()
                                            .trim();

                            if (!value.isEmpty()) {

                                getSharedPreferences(
                                        PREFS,
                                        MODE_PRIVATE
                                )
                                .edit()
                                .putString(
                                        KEY_URL,
                                        value
                                )
                                .apply();

                                coverUrl = value;

                                startWebView();
                            }
                        })

                .setCancelable(false)

                .show();
    }

    @Override
    protected void onResume() {

        super.onResume();

        fullscreen();
    }

    @Override
    public void onBackPressed() {

        fullscreen();
    }
}
