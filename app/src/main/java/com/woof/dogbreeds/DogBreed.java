package com.woof.dogbreeds;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.lang.reflect.Field;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by singerm on 6/3/2016.
 */
public class DogBreed {

    private int id, flagResId;
    private String name, country;
    Bitmap bitmap;
    private final String TAG = "DogBreed";
    static Context lContext;
    //static HashMap<Integer, Bitmap> countryFlags = new HashMap<Integer, Bitmap>();

    public DogBreed(Context context, String name, String country) {
        lContext = context;
        this.name = name;
        this.name = Normalizer.normalize(name, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
        this.country = country;
        this.flagResId = getFlagResId();

        //this.bitmap = BitmapFactory.decodeResource(context.getResources(), getResId("cavalierkingcharlesspaniel", R.drawable.class));

        new Thread(new Runnable() {
            @Override
            public void run() {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;

                String fileResourceString = DogBreed.this.name.toLowerCase().replaceAll("[\\ \\,\\-()\\'\\.\\d+]", "");
                int resID = getResId(fileResourceString, R.drawable.class);
                id = resID;
                if (resID != -1) {
                    DogBreed.this.bitmap = decodeSampledBitmapFromResource(lContext.getResources(), resID, 48, 48);
                }
                else
                    DogBreed.this.bitmap = BitmapFactory.decodeResource(lContext.getResources(), R.mipmap.ic_launcher);
            }
        }).start();

    }

    public long getId() {
        return id;
    }

    public String getName() {
        return this.name;
    }

    public Bitmap getBitmap() {
        return this.bitmap;
    }

    public Bitmap getBitmapLarge() {
        String fileResourceString = DogBreed.this.name.toLowerCase().replaceAll("[\\ \\,\\-()\\'\\.\\d+]", "");
        Bitmap lBitmap;
        int resID = getResId(fileResourceString, R.drawable.class);
        id = resID;
        if (resID != -1) {
            lBitmap = decodeSampledBitmapFromResource(lContext.getResources(), resID, 1, 200);
        }
        else
            lBitmap = BitmapFactory.decodeResource(lContext.getResources(), R.mipmap.ic_launcher);
        return lBitmap;
    }

    public String getCountry() {
        return this.country;
    }

    public int getFlagResId() {
        switch (this.country) {
            case "Germany, France":
                return getResId("germany", R.drawable.class);
            case "United Kingdom (Scotland)":
                return getResId("scotland", R.drawable.class);
            case "United Kingdom (England)":
                return getResId("england", R.drawable.class);
            case "Switzerland, Savoy":
                return getResId("switzerland", R.drawable.class);
            case "Japan, United States":
                return getResId("japan", R.drawable.class);
            case "Spain, Belgium":
                return getResId("spain", R.drawable.class);
            case "Belgium, France":
                return getResId("belgium", R.drawable.class);
            case "Corsica (France)":
                return getResId("france", R.drawable.class);
            case "Switzerland, France":
                return getResId("switzerland", R.drawable.class);
            case "Middle East Lebanon, Israel":
                return getResId("lebanon", R.drawable.class);
            case "Georgia, Armenia, Azerbaijan":
                return getResId("georgia", R.drawable.class);
            case "Denmark, Sweden":
                return getResId("denmark", R.drawable.class);
            case "United Kingdom (England), France":
                return getResId("england", R.drawable.class);
            case "France, Spain":
                return getResId("france", R.drawable.class);
            case "United Kingdom, Middle East":
                return getResId("unitedkingdom", R.drawable.class);
            case "Canada, United States":
                return getResId("canada", R.drawable.class);
            case "Canada, United Kingdom (England)":
                return getResId("canada", R.drawable.class);
            case "United Kingdom (Wales)":
                return getResId("wales", R.drawable.class);
            case "Ukraine, Southern Russia, Belarus":
                return getResId("ukraine", R.drawable.class);
            case "Netherlands, Germany":
                return getResId("netherlands", R.drawable.class);
            case "Germany, France, Netherlands, Spain":
                return getResId("germany", R.drawable.class);
            case "Hungary, Transylvania":
                return getResId("hungary", R.drawable.class);
            case "Spain, Belgium, France":
                return getResId("spain", R.drawable.class);
            case "Belgium, Spain":
                return getResId("belgium", R.drawable.class);
            case "Germany, Poland":
                return getResId("germany", R.drawable.class);
            case "Middle East Syria, Lebabnon":
                return getResId("syria", R.drawable.class);
            case "South Korea, North Korea":
                return getResId("southkorea", R.drawable.class);
            case "Serbia, Republic of Macedonia":
                return getResId("serbia", R.drawable.class);
            case "Canada (Newfoundland)":
                return getResId("canada", R.drawable.class);
            case "Switzerland, Italy":
                return getResId("switzerland", R.drawable.class);
            case "Czech Republic, Slovak Republic":
                return getResId("czechrepublic", R.drawable.class);
            case "United Kingdom, Belgium, France":
                return getResId("unitedkingdom", R.drawable.class);
            case "Bosnia and Herzegovina, Croatia":
                return getResId("bosniaandherzegovina", R.drawable.class);
            case "United Kingdom (England), United States":
                return getResId("england", R.drawable.class);
            case "United States (Kentucky)":
                return getResId("unitedstates", R.drawable.class);
            case "Netherlands, France":
                return getResId("netherlands", R.drawable.class);
            default:
                return getResId(this.country.replace(" ", "").toLowerCase(), R.drawable.class);
        }
    }

    //public ArrayList<DogBreed> getAllDogBreeds() {
    //    return
    //}

    public void setName(String name) {
        this.name = name;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return name;
    }

    public static int getResId(String resName, Class<?> c) {
        try {
            Field idField = c.getDeclaredField(resName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId, int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }



}
