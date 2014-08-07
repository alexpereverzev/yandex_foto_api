package foto.yandex.com.yandexfoto;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class Main extends ActionBarActivity {


    public ArrayList<String> small=new ArrayList<String>();

    public static Context mContext;

    public GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        small.clear();
        mContext=this;
        new MyTask().execute();
        gridView=(GridView) findViewById(R.id.grid_view);

        final Intent intent=new Intent(this,ImageDetail.class);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               intent.putExtra("href",small.get(i));
                startActivity(intent);
            }
        });
    }


    class MyTask extends AsyncTask<Void, Void, ArrayList<String>> {

        public String url = "http://api-fotki.yandex.ru/api/recent/";
        public ArrayList<String> list;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected ArrayList<String> doInBackground(Void... params) {

            HttpUriRequest request = new HttpGet(url);
            request.addHeader("Accept", "application/json");

            HttpClient client = new DefaultHttpClient();

            try {
                list = new ArrayList<String>();
                // The UI Thread shouldn't be blocked long enough to do the reading in of the stream.
                HttpResponse response = client.execute(request);


                BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
                StringBuilder builder = new StringBuilder();
                for (String line = null; (line = reader.readLine()) != null; ) {
                    builder.append(line).append("\n");
                }
                JSONTokener tokener = new JSONTokener(builder.toString());
                JSONObject json = new JSONObject(tokener);
                JSONArray array = json.getJSONArray("entries");
                for (int i = 0; i < array.length(); i++) {
                    JSONObject object = array.getJSONObject(i).getJSONObject("img");
                    list.add(object.getJSONObject("S").getString("href"));
                    small.add(object.getJSONObject("XL").getString("href"));
                }
                System.out.print(array.toString());
            } catch (Exception e) {
                // TODO handle different exception cases
                e.printStackTrace();
                return null;
            }
            return list;
        }

        @Override
        protected void onPostExecute(ArrayList<String> result) {

            CustomGridViewAdapter adapter = new CustomGridViewAdapter(mContext, R.layout.custom_adapter, result);
            gridView.setAdapter(adapter);
            super.onPostExecute(result);

        }
    }

}



