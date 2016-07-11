package com.woof.dogbreeds;

/**
 * Created by singerm on 5/19/2016.
 */
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class DogBreedListAdapter extends BaseAdapter {

    private final String TAG = "DogBreedListAdapter";
    ArrayList<DogBreed> breedListObj = new ArrayList<DogBreed>();

    Context context;
    Activity mActivity;
    private static LayoutInflater inflater=null;

    public DogBreedListAdapter(DogBreedListActivity dogBreedListActivity) {
        // TODO Auto-generated constructor stub
        context=dogBreedListActivity;

        inflater = ( LayoutInflater )context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        initBreedLists();
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return breedListObj.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return breedListObj.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder = new Holder();

        View vi = convertView;
        if (convertView == null)
            vi = inflater.inflate(R.layout.image_list, null);

        holder.tv1 = (TextView) vi.findViewById(R.id.textView1);
        holder.tv2 = (TextView) vi.findViewById(R.id.textView2);
        holder.img = (ImageView) vi.findViewById(R.id.imageView1);
        holder.flag = (ImageView) vi.findViewById(R.id.imageView2);

        holder.tv1.setText(breedListObj.get(position).getName());
        holder.img.setImageBitmap(breedListObj.get(position).getBitmap());
        holder.tv2.setText(breedListObj.get(position).getCountry());
        //holder.tv2.setText("USA");
        int resId = breedListObj.get(position).getFlagResId();
        if (resId != -1)
            holder.flag.setImageResource(resId);
        else
            holder.flag.setImageResource(R.drawable.q);

        vi.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                //Toast.makeText(context, "You Clicked "+result[position], Toast.LENGTH_LONG).show();
                //Intent intent = new Intent(context, ImageActivity.class);
                //intent.putExtra("filename", result[position]);

                //
                //
                // SHOULD THIS BE startActivityForResult ???????
                //
                //

                Intent mIntent = new Intent(context, DogBreedDetailActivity.class);
                mIntent.putExtra("breed", breedListObj.get(position).getName());
                mIntent.putExtra("breedImage", breedListObj.get(position).getBitmap());
                mIntent.putExtra("flagResId", breedListObj.get(position).getFlagResId());
                context.startActivity(mIntent);

                //context.startActivity(intent);
            }
        });

        notifyDataSetChanged();

        return vi;
    }

    public void initBreedLists() {

        Log.v(TAG, "In initBreedLists");

        breedListObj.add(new DogBreed(context, "Labrador Retriever", "Canada, United Kingdom (England)"));
        breedListObj.add(new DogBreed(context, "Old German Shepherd Dog", "Germany"));
        breedListObj.add(new DogBreed(context, "Golden Retriever", "United Kingdom (Scotland)"));
        breedListObj.add(new DogBreed(context, "Bulldog", "United Kingdom (England)"));
        breedListObj.add(new DogBreed(context, "Beagle", "United Kingdom (England)"));
        breedListObj.add(new DogBreed(context, "French Bulldog", "United Kingdom (England), France"));
        breedListObj.add(new DogBreed(context, "Yorkshire Terrier", "United Kingdom (England)"));
        breedListObj.add(new DogBreed(context, "Poodle", "Germany, France"));
        breedListObj.add(new DogBreed(context, "Rottweiler", "Germany"));
        breedListObj.add(new DogBreed(context, "Boxer", "Germany"));
        breedListObj.add(new DogBreed(context, "German Shorthaired Pointer", "Germany"));
        breedListObj.add(new DogBreed(context, "Siberian Husky", "Russia"));
        breedListObj.add(new DogBreed(context, "Dachshund", "Germany"));
        breedListObj.add(new DogBreed(context, "Doberman Pinscher", "Germany"));
        breedListObj.add(new DogBreed(context, "Great Dane", "Germany"));
        breedListObj.add(new DogBreed(context, "Miniature Schnauzer", "Germany"));
        breedListObj.add(new DogBreed(context, "Australian Shepherd", "United States"));
        breedListObj.add(new DogBreed(context, "Cavalier King Charles Spaniel", "United Kingdom (England)"));
        breedListObj.add(new DogBreed(context, "Shih Tzu", "China"));
        breedListObj.add(new DogBreed(context, "Welsh Corgi, Pembroke", "United Kingdom (Wales)"));

    }

    public class Holder
    {
        TextView tv1;
        TextView tv2;
        ImageView img;
        ImageView flag;
    }

}
