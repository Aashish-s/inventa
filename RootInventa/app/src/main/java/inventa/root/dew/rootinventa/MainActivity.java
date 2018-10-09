package inventa.root.dew.rootinventa;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.bumptech.glide.request.RequestOptions;


import proto.inventa.cct.com.inventalibrary.auth.LoginAuth;
import proto.inventa.cct.com.inventalibrary.auth.LoginEventListener;
import proto.inventa.cct.com.inventalibrary.exceptions.APIException;
import proto.inventa.cct.com.inventalibrary.ui.fragment.StoreDataListFragment;

import static proto.inventa.cct.com.inventalibrary.discovery.DiscoveryHelper.KEY_GEOZONE_ID;
import static proto.inventa.cct.com.inventalibrary.discovery.DiscoveryHelper.KEY_INSTORE_ID;
import static proto.inventa.cct.com.inventalibrary.discovery.DiscoveryHelper.KEY_engagementZONE_ID;

public class MainActivity extends AppCompatActivity {

    private ImageView imgView;
    private static final String FLAG_COMMIT_FRAGMENT = "storeModelsListFragment";
    String g_id;
    String e_id;
    String storeId;
    private String title = "All Stores List";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        doLogin("rahuls@dewsolutions.in", "rahul@123");
//        imgView = (ImageView) findViewById(R.id.imgView);

      //setGlide();

//        String url  = "https://d1kuqs143iyp10.cloudfront.net/1170x/5954a3e50761600009cceb7c-5955fafe076160000984e893-Tata-Cliq-Blogbeats.jpg";
        // Code using glide request
//        loadImageWithGlideRequests(url,true);
    }


    private void doLogin(String username, String password) {

        if(!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)) {

            LoginAuth loginAuth = LoginAuth.getInstance();
            loginAuth.doLogin(username, password, new LoginEventListener() {
                @Override
                public void onLoginSuccess() {
                    setupStoreViews();
                }

                @Override
                public void onLoginFailure() {
                    Toast.makeText(getApplicationContext(), "Login Failed", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onApiError(APIException error) {

                }
            });
        }
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


    private void setupStoreViews() {

        /* create/find the fragment and load it in frame layout */
        StoreDataListFragment storeDataModelsListFragment = (StoreDataListFragment) getSupportFragmentManager().findFragmentByTag(FLAG_COMMIT_FRAGMENT);

        g_id = getIntent().getStringExtra(KEY_GEOZONE_ID);
        e_id = getIntent().getStringExtra(KEY_engagementZONE_ID);
        storeId = getIntent().getStringExtra(KEY_INSTORE_ID);

        StoreDataListFragment storeDataListFragment = new StoreDataListFragment();
        Bundle bundle = new Bundle();

        // show Specific Stores if coming from notification
        //geozone id

        if (g_id != null && !g_id.isEmpty()) {
            bundle.putString("g_id", g_id);
            title = "Geozone Stores";
        }
        //ezone
        else  if (e_id != null && !e_id.isEmpty()) {
            bundle.putString("e_id", e_id);
            title = "Engagement Zone Stores";
        }
        //instore
        else if (storeId != null && !storeId.isEmpty()) {
            bundle.putString("st_id", storeId);
            title = "Inside Store";
        }

//        setActionBarTitle(title);
        //default : show all stores
        storeDataListFragment.setArguments(bundle);
        launchStoreDataListFragment(storeDataListFragment);
    }

    private void launchStoreDataListFragment(StoreDataListFragment storeDataListFragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.activity_main, storeDataListFragment, FLAG_COMMIT_FRAGMENT)
                .commit();
    }
}
