package xyz.leosap.rappiprueba.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.apache.commons.lang3.StringEscapeUtils;

import xyz.leosap.rappiprueba.R;
import xyz.leosap.rappiprueba.common.BlurTransformation;
import xyz.leosap.rappiprueba.common.Constants;
import xyz.leosap.rappiprueba.common.Functions;
import xyz.leosap.rappiprueba.models.Tema;

public class DetailActivity extends AppCompatActivity {

    private Tema tema;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tema = Tema.find(Functions.getDB(getApplicationContext()), getIntent().getStringExtra("id"));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Constants.debug) Log.d("LS url", Constants.base_url + tema.getUrl());

                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(Constants.base_url + tema.getUrl()));
                startActivity(browserIntent);
            }
        });


        cargaInicial();


    }

    private void cargaInicial() {
        setTitle(Functions.cleanContent(tema.getTitle()));

        String img = tema.getIconImg();
        if (Constants.debug) Log.d("LS img icon", "--> " + tema.getIconImg());
        if (img.equalsIgnoreCase("")) {
            img = tema.getHeaderImg();
            if (Constants.debug) Log.d("LS img header", "--> " + tema.getHeaderImg());
        }

        Picasso.with(getApplicationContext())
                .load(img)

                .fit()
                .noFade()

                .transform(new BlurTransformation(getApplicationContext()))
                .error(R.drawable.ic_icon_ph)
                .centerInside()
                .into((ImageView) findViewById(R.id.imgToolbar));


        ((TextView) findViewById(R.id.tv_url)).setText(tema.getUrl());
        ((TextView) findViewById(R.id.tv_suscriptors)).setText(Functions.numberToFormat(tema.getSubscribers()));

        ((TextView) findViewById(R.id.tv_desc)).setText(tema.getPublicDescription());


        WebView wv = (WebView) findViewById(R.id.wv_desc);
        WebSettings settings = wv.getSettings();
        settings.setDefaultTextEncodingName("utf-8");
        String html = StringEscapeUtils.unescapeHtml4(tema.getDescriptionHtml());
        wv.loadDataWithBaseURL(null, html, "text/html", "utf-8", null);
        wv.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(browserIntent);
                return true;
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        supportFinishAfterTransition();
        super.onBackPressed();
    }
}
