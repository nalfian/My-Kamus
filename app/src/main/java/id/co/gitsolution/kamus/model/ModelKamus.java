package id.co.gitsolution.kamus.model;

import android.os.Parcel;
import android.os.Parcelable;

public class ModelKamus implements Parcelable {

    private int id;
    private String kamus;
    private String terjemahan;

    public ModelKamus() {
    }

    public ModelKamus(String kamus, String terjemahan) {
        this.kamus = kamus;
        this.terjemahan = terjemahan;
    }

    public ModelKamus(Parcel in) {
        id = in.readInt();
        kamus = in.readString();
        terjemahan = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(kamus);
        dest.writeString(terjemahan);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ModelKamus> CREATOR = new Creator<ModelKamus>() {
        @Override
        public ModelKamus createFromParcel(Parcel in) {
            return new ModelKamus(in);
        }

        @Override
        public ModelKamus[] newArray(int size) {
            return new ModelKamus[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKamus() {
        return kamus;
    }

    public void setKamus(String kamus) {
        this.kamus = kamus;
    }

    public String getTerjemahan() {
        return terjemahan;
    }

    public void setTerjemahan(String terjemahan) {
        this.terjemahan = terjemahan;
    }
}
