package bkap.android.demoappconnectapi;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    TextView mTxtResult;
    ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTxtResult = findViewById(R.id.txtTest);
        mImageView = findViewById(R.id.imageView);
        loadJSON();
        Runnable taskDownload = new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                    loadImage();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        Thread thread = new Thread(taskDownload);
        thread.start();
    }

    private void loadJSON() {
        String urlAPI = "https://api.rss2json.com/v1/api.json?rss_url=https%3A%2F%2Fvnexpress.net%2Frss%2Fthe-thao.rss";

        // Load thành công
        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                mTxtResult.setText(response);
//                Gson gson = new Gson();
//                Sanpham sp = gson.fromJson(response);
//                List<Sanpham> lst = gson.fromJson(response, tokentype.getType());
            }
        };

        // Lỗi
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("BKAP", error.toString());
            }
        };
        StringRequest stringRequest = new StringRequest(urlAPI, listener, errorListener);

        // Cách 2: lấy trực tiếp về dữ liệu JsonObject / JsonArray....
//        Response.Listener<JSONObject> listener1 = new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//                Toast.makeText(MainActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
//            }
//        };
//
//        Response.ErrorListener errorListener1 = new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
//            }
//        };
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(urlAPI, null, listener1, errorListener1);
//        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(....);

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void loadImage(){
        String urlImage = "https://vcdn-ngoisao.vnecdn.net/2018/03/22/truongquynhanh-1-JPG-7564-1521700441.jpg";
        Response.Listener<Bitmap> listener = new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                mImageView.setImageBitmap(response);
            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Lỗi: " + error.toString(), Toast.LENGTH_SHORT).show();
            }
        };
        ImageRequest imageRequest = new ImageRequest(urlImage, listener, 1024, 1024, null, errorListener);
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(imageRequest);
    }

}
