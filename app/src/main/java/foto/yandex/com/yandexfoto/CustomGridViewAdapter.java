package foto.yandex.com.yandexfoto;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import foto.yandex.com.yandexfoto.R;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by appus_dd6n on 6/3/14.
 */
public class CustomGridViewAdapter extends ArrayAdapter<String> {


    int layoutResourceId;
    ArrayList<String> data = new ArrayList<String>();
    Context context;


    public CustomGridViewAdapter(Context context, int layoutResourceId,
                                 ArrayList<String> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        RecordHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new RecordHolder();
            holder.imageItem = (ImageView) row.findViewById(R.id.image);


            row.setTag(holder);
        } else {
            holder = (RecordHolder) row.getTag();
        }

        String item = data.get(position);
        holder.imageItem.setTag(item);
        new DownloadImagesTask().execute(holder.imageItem);

        // holder.imageItem.setImageDrawable(item.getImage());
        return row;

    }

    static class RecordHolder {

        ImageView imageItem;


    }



}



