package id.co.gitsolution.kamus.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import id.co.gitsolution.kamus.R;
import id.co.gitsolution.kamus.model.ModelKamus;

public class DetailActivity extends AppCompatActivity {

    private TextView tvKamus, tvTerjemahan;
    private ModelKamus modelKamus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        getSupportActionBar().setTitle(R.string.terjemahan);
        initView();

        if (getIntent().getParcelableExtra("data") != null) {
            modelKamus = getIntent().getParcelableExtra("data");
            initEvent();
        }
    }

    private void initEvent() {
        tvKamus.setText(modelKamus.getKamus());
        tvTerjemahan.setText(modelKamus.getTerjemahan());
    }

    private void initView() {
        tvKamus = findViewById(R.id.tvKamus);
        tvTerjemahan = findViewById(R.id.tvTerjemahan);
    }

    public Intent getSupportParentActivityIntent() {
        onBackPressed();
        return null;
    }
}
