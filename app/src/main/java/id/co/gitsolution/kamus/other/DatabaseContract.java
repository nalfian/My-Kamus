package id.co.gitsolution.kamus.other;

import android.provider.BaseColumns;

class DatabaseContract {

    static String TABLE_INDO = "indo";
    static String TABLE_ENGLISH = "english";

    static final class KamusColumns implements BaseColumns {
        static String KAMUS = "kamus";
        static String TERJEMAHAN = "terjemahan";
    }
}
