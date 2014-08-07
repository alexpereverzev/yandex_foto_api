package foto.yandex.com.yandexfoto;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

/**
 * Created by Александр on 07.08.2014.
 */
public class ImageDetail extends Activity {

    private ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_image);
        Intent data= getIntent();
        String image_url=data.getStringExtra("href");
        img=(ImageView) findViewById(R.id.img_view);
        img.setTag(image_url);
        new DownloadImagesTask().execute(img);
    }
}