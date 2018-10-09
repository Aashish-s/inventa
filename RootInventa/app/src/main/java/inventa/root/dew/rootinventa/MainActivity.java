package inventa.root.dew.rootinventa;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.bumptech.glide.request.RequestOptions;

public class MainActivity extends Activity{

    private ImageView imgView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imgView = (ImageView) findViewById(R.id.imgView);

      //setGlide();

        String url  = "https://d1kuqs143iyp10.cloudfront.net/1170x/5954a3e50761600009cceb7c-5955fafe076160000984e893-Tata-Cliq-Blogbeats.jpg";
        // Code using glide request
        loadImageWithGlideRequests(url,true);
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
        // Code using glide request
        //loadImageWithGlideRequests(url, true);
    }

    private void loadImageWithGlideRequests(String url, boolean b){
        try {
            LazyHeaders auth = new LazyHeaders.Builder()
                    .build();
           GlideRequests glideRequests = GlideApp.with(this);
            RequestOptions requestOptions =
                    new RequestOptions().placeholder(R.drawable.avatar)
                            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).dontTransform();
            glideRequests
                    .load(b ? new GlideUrl(url, auth) : new GlideUrl(url))
                    .apply(requestOptions)
                    .into(imgView).clearOnDetach();
        } catch (Exception e) {
            // do handling here
        }
    }
}
