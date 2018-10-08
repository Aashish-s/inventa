package inventa.root.dew.rootinventa;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

public class MainActivity extends Activity{

    private ImageView imgView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imgView = (ImageView) findViewById(R.id.imgView);

        setGlide();
    }

    private void setGlide() {

        String url  = "https://d1kuqs143iyp10.cloudfront.net/1170x/5954a3e50761600009cceb7c-5955fafe076160000984e893-Tata-Cliq-Blogbeats.jpg";
        Glide
                .with(this)
                .load(url)
                .apply(new RequestOptions().placeholder(R.drawable.avatar)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .dontAnimate()
                        .centerCrop()
                        .dontTransform())
                .into(imgView);
    }
}
