package id.co.gitsolution.kamus.view;

import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import id.co.gitsolution.kamus.R;
import id.co.gitsolution.kamus.model.ModelKamus;
import id.co.gitsolution.kamus.other.KamusHelper;
import id.co.gitsolution.kamus.other.SPManager;

public class SplashActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private SPManager spManager;
    private TextView tvLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        progressBar = findViewById(R.id.progress_bar);
        tvLoading = findViewById(R.id.tvLoading);

        spManager = new SPManager(this);

        if (spManager.getSPSudahSimpan()) {
            progressBar.setVisibility(View.INVISIBLE);
            tvLoading.setVisibility(View.INVISIBLE);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
                }
            }, 2000);
        } else {
            new LoadDataIndo().execute();
        }
    }

    private class LoadDataIndo extends AsyncTask<Void, Integer, Void> {
        final String TAG = LoadDataIndo.class.getSimpleName();
        KamusHelper kamusHelper;
        double progress;
        double maxprogress = 100;

        @Override
        protected void onPreExecute() {
            tvLoading.setText(R.string.simpan_indo);
            kamusHelper = new KamusHelper(getBaseContext());
        }

        @Override
        protected Void doInBackground(Void... params) {

            ArrayList<ModelKamus> modelKamuses = preLoadRawIndo();

            kamusHelper.open();

            progress = 30;
            publishProgress((int) progress);
            Double progressMaxInsert = 80.0;
            Double progressDiff = (progressMaxInsert - progress) / modelKamuses.size();

            kamusHelper.beginTransaction();

            try {
                for (ModelKamus model : modelKamuses) {
                    kamusHelper.insertIndo(model);
                    progress += progressDiff;
                    publishProgress((int) progress);
                }

            } catch (Exception e) {
                Log.e(TAG, "doInBackground: Exception");
            }

            kamusHelper.setTransactionSuccess();
            kamusHelper.endTransaction();

            kamusHelper.close();
            publishProgress((int) maxprogress);
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            progressBar.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(Void result) {
            new LoadDataEng().execute();
        }
    }

    public ArrayList<ModelKamus> preLoadRawIndo() {
        ArrayList<ModelKamus> modelKamuses = new ArrayList<>();
        String line;
        BufferedReader reader;
        try {
            Resources res = getResources();
            InputStream raw_dict = res.openRawResource(R.raw.indonesia_english);

            reader = new BufferedReader(new InputStreamReader(raw_dict));
            int count = 0;
            do {
                line = reader.readLine();
                String[] splitstr = line.split("\t");

                ModelKamus modelKamus;

                modelKamus = new ModelKamus(splitstr[0], splitstr[1]);
                modelKamuses.add(modelKamus);
                count++;
            } while (true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return modelKamuses;
    }


    private class LoadDataEng extends AsyncTask<Void, Integer, Void> {
        final String TAG = LoadDataIndo.class.getSimpleName();
        KamusHelper kamusHelper;
        double progress;
        double maxprogress = 100;

        @Override
        protected void onPreExecute() {
            kamusHelper = new KamusHelper(getBaseContext());
            tvLoading.setText(R.string.simpan_eng);
        }

        @Override
        protected Void doInBackground(Void... params) {

            ArrayList<ModelKamus> modelKamuses = preLoadRawEng();

            kamusHelper.open();

            progress = 30;
            publishProgress((int) progress);
            Double progressMaxInsert = 80.0;
            Double progressDiff = (progressMaxInsert - progress) / modelKamuses.size();

            kamusHelper.beginTransaction();

            try {
                for (ModelKamus model : modelKamuses) {
                    kamusHelper.insertEng(model);
                    progress += progressDiff;
                    publishProgress((int) progress);
                }

            } catch (Exception e) {
                Log.e(TAG, "doInBackground: Exception");
            }

            kamusHelper.setTransactionSuccess();
            kamusHelper.endTransaction();

            kamusHelper.close();
            publishProgress((int) maxprogress);
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            progressBar.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(Void result) {
            spManager.saveSPBoolean(SPManager.SP_SUDAH_SIMPAN, true);
            spManager.saveSPString(SPManager.SP_BAHASA, getString(R.string.indo));
            startActivity(new Intent(getBaseContext(), MainActivity.class));
            finish();
        }
    }

    public ArrayList<ModelKamus> preLoadRawEng() {
        ArrayList<ModelKamus> modelKamuses = new ArrayList<>();
        String line;
        BufferedReader reader;
        try {
            Resources res = getResources();
            InputStream raw_dict = res.openRawResource(R.raw.english_indonesia);

            reader = new BufferedReader(new InputStreamReader(raw_dict));
            int count = 0;
            do {
                line = reader.readLine();
                String[] splitstr = line.split("\t");

                ModelKamus modelKamus;

                modelKamus = new ModelKamus(splitstr[0], splitstr[1]);
                modelKamuses.add(modelKamus);
                count++;
            } while (true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return modelKamuses;
    }
}
