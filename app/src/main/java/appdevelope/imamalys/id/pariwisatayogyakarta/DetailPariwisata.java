package appdevelope.imamalys.id.pariwisatayogyakarta;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailPariwisata extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    @BindView(R.id.openmaps)
    Button opengmaps;
    @BindView(R.id.alamat)
    TextView alamatpariwisata;
    @BindView(R.id.caption)
    TextView caption;
    @BindView(R.id.gambar)
    NetworkImageView gambarpariwisata;
    @BindView(R.id.detailpariwisata)
    TextView detailpariwisata;
    @BindView(R.id.bottom_sheet)
    LinearLayout layoutBottomSheet;
    BottomSheetBehavior sheetBehavior;
    public String alamat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pariwisata);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String nama = intent.getStringExtra("nama");
        alamat = intent.getStringExtra("alamat");
        String detail = intent.getStringExtra("detail");
        String gambar = intent.getStringExtra("gambar");


        detailpariwisata.setText(detail);
        alamatpariwisata.setText(alamat);
        com.android.volley.toolbox.ImageLoader imageLoader = AppController.getInstance().getImageLoader();
        imageLoader.get(gambar, com.android.volley.toolbox.ImageLoader.getImageListener(gambarpariwisata,R.drawable.loader, android.R.drawable.ic_dialog_alert));
        gambarpariwisata.setImageUrl(gambar, imageLoader);

        Toolbar toolbar = findViewById(R.id.toolbardetail);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(nama);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sheetBehavior = BottomSheetBehavior.from(layoutBottomSheet);

        sheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {

                    case BottomSheetBehavior.STATE_EXPANDED: {
                        caption.setText("Geser kebawah untuk menutup");
                    }
                    break;
                    case BottomSheetBehavior.STATE_COLLAPSED: {
                        caption.setText("Geser keatas untuk melihat alamat");
                    }
                    break;

                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
    }
    @OnClick(R.id.openmaps)
    public void toggleBottomSheet() {
        String uri = "http://maps.google.com/maps?q="+alamat;
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        intent.setPackage("com.google.android.apps.maps");
        startActivity(intent);
    }
}

