package id.co.gitsolution.kamus.other;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

public class SPManager {

    private static final String SP_KAMUS_APP = "spKamusApp";
    public static final String SP_SUDAH_SIMPAN = "spSudahSimpan";
    public static final String SP_BAHASA = "spBahasa";

    private SharedPreferences sp;
    private SharedPreferences.Editor spEditor;

    @SuppressLint("CommitPrefEdits")
    public SPManager(Context context) {
        sp = context.getSharedPreferences(SP_KAMUS_APP, Context.MODE_PRIVATE);
        spEditor = sp.edit();
    }

    public void saveSPString(String keySP, String value) {
        spEditor.putString(keySP, value);
        spEditor.commit();
    }

    public void saveSPBoolean(String keySP, boolean value) {
        spEditor.putBoolean(keySP, value);
        spEditor.commit();
    }

    public Boolean getSPSudahSimpan() {
        return sp.getBoolean(SP_SUDAH_SIMPAN, false);
    }

    public String getSpBahasa() {
        return sp.getString(SP_BAHASA, "");
    }
}

