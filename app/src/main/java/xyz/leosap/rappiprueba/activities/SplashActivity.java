package xyz.leosap.rappiprueba.activities;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import xyz.leosap.rappiprueba.R;
import xyz.leosap.rappiprueba.common.Constants;
import xyz.leosap.rappiprueba.common.Functions;
import xyz.leosap.rappiprueba.models.Tema;


public class SplashActivity extends AppCompatActivity {

    TextView tv1, tv2;
    ImageView iv1;
    boolean connected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        tv1 = (TextView) findViewById(R.id.textView);
        tv2 = (TextView) findViewById(R.id.textView2);
        iv1 = (ImageView) findViewById(R.id.imageView2);

        connected = false;
        connected = Functions.isConnected(this);

        if (connected)
            cargaInicial();
        else
            Snackbar.make(findViewById(R.id.vw_container), R.string.offline, Snackbar.LENGTH_LONG).show();

        animacion();
    }

    public void animacion() {
        Animation anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fadein);
        anim.setDuration(2000);
        tv1.startAnimation(anim);//aparecemos el primer titulo
        iv1.startAnimation(anim);

        Animation anim2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fadein);
        anim2.setDuration(4000);
        anim2.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {//Cuando acabe la segunda animación, se abre el activity principal si no hay conexion
                if (!connected)
                    goMain();

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        tv2.startAnimation(anim2);//aparecemos el segundo titulo

    }

    private void cargaInicial() {

        AsyncHttpClient client = new AsyncHttpClient();
        client.setLoggingEnabled(true);
        client.setTimeout(600000);
        client.get(Constants.url_json, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                if (Constants.debug) Log.d("LS response", response.toString());
                SQLiteDatabase db = Functions.getDB(getApplicationContext());
                try {
                    Tema.truncate(db);
                    JSONArray items = response.getJSONObject("data").getJSONArray("children");
                    for (int i = 0; i < items.length(); i++) {

                        JSONObject object = items.getJSONObject(i).getJSONObject("data");
                        Tema tema = new Tema();
                        tema.setId(object.getString("id"));
                        tema.setTitle(object.getString("title"));
                        tema.setHeaderTitle(object.getString("header_title"));
                        tema.setDisplayName(object.getString("display_name"));
                        tema.setDescription(object.getString("description"));
                        tema.setCreated(object.getLong("created"));
                        tema.setPublicDescription(object.getString("public_description"));
                        tema.setBannerImg(object.getString("banner_img"));
                        tema.setHeaderImg(object.getString("header_img"));
                        tema.setIconImg(object.getString("icon_img"));
                        tema.setUrl(object.getString("url"));
                        tema.setKeyColor(object.getString("key_color"));
                        tema.setSubmitText(object.getString("submit_text"));
                        tema.setSubscribers(object.getInt("subscribers"));
                        tema.setPublicDescriptionHtml(object.getString("public_description_html"));
                        tema.setDescriptionHtml(object.getString("description_html"));
                        tema.save(db);

                    }


                } catch (JSONException e) {
                    if (Constants.debug) Log.d("LS error", e.getMessage());
                }

                db.close();
                if(connected)//Si el flag es verdaddero, abrimos el activity principal
                    goMain();

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject responseString) {

                if (throwable != null) {
                    Snackbar.make(findViewById(R.id.vw_container), R.string.error_server, Snackbar.LENGTH_SHORT).show();
                    if (Constants.debug) Log.d("LS error", throwable.getMessage());
                    goMain();
                }

            }

        });

    }

    private void goMain() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

    }

}
