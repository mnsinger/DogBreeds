package com.woof.dogbreeds;

/**
 * Created by singerm on 5/19/2016.
 */
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.SearchView;
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
import java.util.HashMap;

public class DogBreedAdapter extends BaseAdapter implements Filterable {

    private final String TAG = "DogBreedAdapter";
    ArrayList<DogBreed> quizBreeds =  new ArrayList<DogBreed>();
    ArrayList<DogBreed> filtBreeds =  new ArrayList<DogBreed>();
    ArrayList<DogBreed> origBreeds = new ArrayList<DogBreed>();
    HashMap<Integer, Bitmap> countryFlags = new HashMap<Integer, Bitmap>();

    Context context;
    Activity mActivity;
    SearchView mSearchView;
    private static LayoutInflater inflater=null;
    private ItemFilter mFilter = new ItemFilter();

    public DogBreedAdapter(BaseActivity mainActivity, SearchView searchView) {
        // TODO Auto-generated constructor stub
        context=mainActivity;
        mActivity=mainActivity;
        mSearchView=searchView;
        inflater = ( LayoutInflater )context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setQuizResults(ArrayList<Integer> quizCorrectIndexes, ArrayList<Integer> quizInCorrectIndexes) {
        quizBreeds.clear();
        quizBreeds.add(new DogBreed(context, "CORRECT RESULTS", ""));
        for (int c = 0; c < quizCorrectIndexes.size(); c++) {
            if (!quizBreeds.contains(origBreeds.get(quizCorrectIndexes.get(c))))
                quizBreeds.add(origBreeds.get(quizCorrectIndexes.get(c)));
        }
        quizBreeds.add(new DogBreed(context, "INCORRECT RESULTS", ""));
        for (int c = 0; c < quizInCorrectIndexes.size(); c++) {
            if (!quizBreeds.contains(origBreeds.get(quizInCorrectIndexes.get(c))))
                quizBreeds.add(origBreeds.get(quizInCorrectIndexes.get(c)));
        }
    }

    public void init() {
        initBreedLists();
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return filtBreeds.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return filtBreeds.get(position);
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

        holder.tv1.setText(filtBreeds.get(position).getName());
        holder.img.setImageBitmap(filtBreeds.get(position).getBitmap());
        holder.tv2.setText(filtBreeds.get(position).getCountry());
        //holder.tv2.setText("USA");
        int flagResId = filtBreeds.get(position).getFlagResId();
        if (flagResId != -1) {
            if (countryFlags.get(flagResId) == null) {
                countryFlags.put(flagResId, filtBreeds.get(position).decodeSampledBitmapFromResource(context.getResources(), flagResId, 48, 44));
                //holder.flag.setImageResource(flagResId);
            }
            holder.flag.setImageBitmap(countryFlags.get(flagResId));
        }
        else
            holder.flag.setImageResource(R.drawable.q);

        if (filtBreeds.get(position).getName().equals("CORRECT RESULTS")) {
            final ColorDrawable drawable = new ColorDrawable();
            drawable.setColor(Color.GREEN);
            holder.img.setImageResource(android.R.color.transparent);
            holder.flag.setImageResource(android.R.color.transparent);
            vi.setBackground(drawable);
        }
        else if (filtBreeds.get(position).getName().equals("INCORRECT RESULTS")) {
            final ColorDrawable drawable = new ColorDrawable();
            drawable.setColor(Color.RED);
            holder.img.setImageResource(android.R.color.transparent);
            holder.flag.setImageResource(android.R.color.transparent);
            vi.setBackground(drawable);
        }
        else {
            //if (vi.getBackground().equals(Color.RED) || vi.getBackground().equals(Color.GREEN)) {
                final ColorDrawable drawable = new ColorDrawable();
                vi.setBackground(drawable);
            //}
            holder.flag.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    //mFilter.publishResults("", mFilter.performFiltering(filtBreeds.get(position).getCountry().split(",")[0]));
                    String country = filtBreeds.get(position).getCountry().split(",")[0];
                    mSearchView.setQuery(country, true);
                    mSearchView.setFocusable(true);
                    mSearchView.setIconified(false);
                    mSearchView.requestFocusFromTouch();

                }
            });

            vi.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub

                    Intent mIntent = new Intent(context, DogBreedDetailActivity.class);
                    mIntent.putExtra("breed", filtBreeds.get(position).getName().replaceAll("[\\.\\d+]", "").replaceAll("^\\s+", ""));
                    mIntent.putExtra("breedImage", filtBreeds.get(position).getBitmapLarge());
                    mIntent.putExtra("flagResId", filtBreeds.get(position).getFlagResId());
                    context.startActivity(mIntent);

                }
            });
        }

        notifyDataSetChanged();

        return vi;
    }

    public void initBreedLists() {

        Log.v(TAG, "In initBreedLists");

        origBreeds.add(new DogBreed(context, "Affenpinscher", "Germany, France"));
        origBreeds.add(new DogBreed(context, "Afghan Hound", "Afghanistan"));
        origBreeds.add(new DogBreed(context, "Afghan Shepherd", "Afghanistan"));
        origBreeds.add(new DogBreed(context, "Aidi", "Morocco"));
        origBreeds.add(new DogBreed(context, "Airedale Terrier", "United Kingdom (England)"));
        origBreeds.add(new DogBreed(context, "Akbash", "Turkey"));
        origBreeds.add(new DogBreed(context, "Akita Inu", "Japan"));
        origBreeds.add(new DogBreed(context, "Alano Español", "Spain"));
        origBreeds.add(new DogBreed(context, "Alaskan Klee Kai", "United States"));
        origBreeds.add(new DogBreed(context, "Alaskan Malamute", "United States"));
        origBreeds.add(new DogBreed(context, "Alpine Dachsbracke", "Austria"));
        origBreeds.add(new DogBreed(context, "Alpine Spaniel", "Switzerland, Savoy"));
        origBreeds.add(new DogBreed(context, "American Akita", "Japan, United States"));
        origBreeds.add(new DogBreed(context, "American Bulldog", "United States"));
        origBreeds.add(new DogBreed(context, "American Cocker Spaniel", "United States"));
        origBreeds.add(new DogBreed(context, "American Eskimo Dog", "Germany"));
        origBreeds.add(new DogBreed(context, "American Foxhound", "United States"));
        origBreeds.add(new DogBreed(context, "American Hairless Terrier", "United States"));
        origBreeds.add(new DogBreed(context, "American Pit Bull Terrier", "United States"));
        origBreeds.add(new DogBreed(context, "American Staffordshire Terrier", "United States"));
        origBreeds.add(new DogBreed(context, "American Water Spaniel", "United States"));
        origBreeds.add(new DogBreed(context, "Anatolian Shepherd Dog", "Turkey"));
        origBreeds.add(new DogBreed(context, "Andalusian Hound", "Spain"));
        origBreeds.add(new DogBreed(context, "Anglo-Français de Petite Vénerie", "France"));
        origBreeds.add(new DogBreed(context, "Appenzeller Sennenhund", "Switzerland"));
        origBreeds.add(new DogBreed(context, "Ariege Pointer", "France"));
        origBreeds.add(new DogBreed(context, "Ariegeois", "France"));
        origBreeds.add(new DogBreed(context, "Armant", "Egypt"));
        origBreeds.add(new DogBreed(context, "Armenian Gampr Dog", "Armenia"));
        origBreeds.add(new DogBreed(context, "Artois Hound", "France"));
        origBreeds.add(new DogBreed(context, "Australian Cattle Dog", "Australia"));
        origBreeds.add(new DogBreed(context, "Australian Kelpie", "Australia"));
        origBreeds.add(new DogBreed(context, "Australian Shepherd", "United States"));
        origBreeds.add(new DogBreed(context, "Australian Silky Terrier", "Australia"));
        origBreeds.add(new DogBreed(context, "Australian Stumpy Tail Cattle Dog", "Australia"));
        origBreeds.add(new DogBreed(context, "Australian Terrier", "Australia"));
        origBreeds.add(new DogBreed(context, "Austrian Black and Tan Hound", "Austria"));
        origBreeds.add(new DogBreed(context, "Austrian Pinscher", "Austria"));
        origBreeds.add(new DogBreed(context, "Azawakh", "Mali"));
        origBreeds.add(new DogBreed(context, "Bakharwal Dog", "Pakistan"));
        origBreeds.add(new DogBreed(context, "Barbet", "France"));
        origBreeds.add(new DogBreed(context, "Basenji", "Democratic Republic of the Congo"));
        origBreeds.add(new DogBreed(context, "Basque Ratter", "Spain"));
        origBreeds.add(new DogBreed(context, "Basque Shepherd Dog", "Spain"));
        origBreeds.add(new DogBreed(context, "Basset Artésien Normand", "France"));
        origBreeds.add(new DogBreed(context, "Basset Bleu de Gascogne", "France"));
        origBreeds.add(new DogBreed(context, "Basset Fauve de Bretagne", "France"));
        origBreeds.add(new DogBreed(context, "Basset Griffon Vendéen, Grand", "France"));
        origBreeds.add(new DogBreed(context, "Basset Griffon Vendéen, Petit", "France"));
        origBreeds.add(new DogBreed(context, "Basset Hound", "France"));
        origBreeds.add(new DogBreed(context, "Bavarian Mountain Hound", "Germany"));
        origBreeds.add(new DogBreed(context, "Beagle", "United Kingdom (England)"));
        origBreeds.add(new DogBreed(context, "Beagle-Harrier", "France"));
        origBreeds.add(new DogBreed(context, "Bearded Collie", "United Kingdom (Scotland)"));
        origBreeds.add(new DogBreed(context, "Beauceron", "France"));
        origBreeds.add(new DogBreed(context, "Bedlington Terrier", "United Kingdom"));
        origBreeds.add(new DogBreed(context, "Belgian Shepherd Dog (Groenendael)", "Belgium"));
        origBreeds.add(new DogBreed(context, "Belgian Shepherd Dog (Laekenois)", "Belgium"));
        origBreeds.add(new DogBreed(context, "Belgian Shepherd Dog (Malinois)", "Belgium"));
        origBreeds.add(new DogBreed(context, "Belgian Shepherd (Tervuren)", "Belgium"));
        origBreeds.add(new DogBreed(context, "Bergamasco Shepherd", "Italy"));
        origBreeds.add(new DogBreed(context, "Berger Blanc Suisse", "Switzerland"));
        origBreeds.add(new DogBreed(context, "Berger Picard", "France"));
        origBreeds.add(new DogBreed(context, "Berner Laufhund", "Switzerland"));
        origBreeds.add(new DogBreed(context, "Bernese Mountain Dog", "Switzerland"));
        origBreeds.add(new DogBreed(context, "Bichon Frisé", "Spain, Belgium"));
        origBreeds.add(new DogBreed(context, "Billy", "France"));
        origBreeds.add(new DogBreed(context, "Black and Tan Coonhound", "United States"));
        origBreeds.add(new DogBreed(context, "Black and Tan Virginia Foxhound", "United States"));
        origBreeds.add(new DogBreed(context, "Black Norwegian Elkhound", "Norway"));
        origBreeds.add(new DogBreed(context, "Black Russian Terrier", "Russia"));
        origBreeds.add(new DogBreed(context, "Blackmouth Cur", "United States"));
        origBreeds.add(new DogBreed(context, "Bleu de Gascogne, Grand", "France"));
        origBreeds.add(new DogBreed(context, "Bleu de Gascogne, Petit", "France"));
        origBreeds.add(new DogBreed(context, "Bloodhound", "Belgium, France"));
        origBreeds.add(new DogBreed(context, "Blue Heeler", "Australia"));
        origBreeds.add(new DogBreed(context, "Blue Lacy", "United States"));
        origBreeds.add(new DogBreed(context, "Blue Paul Terrier", "United Kingdom (Scotland)"));
        origBreeds.add(new DogBreed(context, "Bluetick Coonhound", "United States"));
        origBreeds.add(new DogBreed(context, "Boerboel", "South Africa"));
        origBreeds.add(new DogBreed(context, "Bohemian Shepherd", "Czech Republic"));
        origBreeds.add(new DogBreed(context, "Bolognese", "Italy"));
        origBreeds.add(new DogBreed(context, "Border Collie", "United Kingdom"));
        origBreeds.add(new DogBreed(context, "Border Terrier", "United Kingdom"));
        origBreeds.add(new DogBreed(context, "Borzoi", "Russia"));
        origBreeds.add(new DogBreed(context, "Bosnian Coarse-haired Hound", "Bosnia and Herzegovina"));
        origBreeds.add(new DogBreed(context, "Boston Terrier", "United States"));
        origBreeds.add(new DogBreed(context, "Bouvier des Ardennes", "Belgium"));
        origBreeds.add(new DogBreed(context, "Bouvier des Flandres", "Belgium"));
        origBreeds.add(new DogBreed(context, "Boxer", "Germany"));
        origBreeds.add(new DogBreed(context, "Boykin Spaniel", "United States"));
        origBreeds.add(new DogBreed(context, "Bracco Italiano", "Italy"));
        origBreeds.add(new DogBreed(context, "Braque d'Auvergne", "France"));
        origBreeds.add(new DogBreed(context, "Braque du Bourbonnais", "France"));
        origBreeds.add(new DogBreed(context, "Braque du Puy", "France"));
        origBreeds.add(new DogBreed(context, "Braque Francais", "France"));
        origBreeds.add(new DogBreed(context, "Braque Saint-Germain", "France"));
        origBreeds.add(new DogBreed(context, "Brazilian Dogo", "Brazil"));
        origBreeds.add(new DogBreed(context, "Brazilian Terrier", "Brazil"));
        origBreeds.add(new DogBreed(context, "Briard", "France"));
        origBreeds.add(new DogBreed(context, "Briquet Griffon Vendéen", "France"));
        origBreeds.add(new DogBreed(context, "Brittany", "France"));
        origBreeds.add(new DogBreed(context, "Broholmer", "Denmark"));
        origBreeds.add(new DogBreed(context, "Bruno Jura Hound", "Switzerland, France"));
        origBreeds.add(new DogBreed(context, "Bucovina Shepherd Dog", "Romania"));
        origBreeds.add(new DogBreed(context, "Bull and Terrier", "United Kingdom"));
        origBreeds.add(new DogBreed(context, "Bull Terrier", "United Kingdom (England)"));
        origBreeds.add(new DogBreed(context, "Bull Terrier (Miniature)", "United Kingdom (England)"));
        origBreeds.add(new DogBreed(context, "Bulldog", "United Kingdom (England)"));
        origBreeds.add(new DogBreed(context, "Bullenbeisser", "Germany"));
        origBreeds.add(new DogBreed(context, "Bullmastiff", "United Kingdom (England)"));
        origBreeds.add(new DogBreed(context, "Bully Kutta", "Pakistan"));
        origBreeds.add(new DogBreed(context, "Burgos Pointer", "Spain"));
        origBreeds.add(new DogBreed(context, "Cairn Terrier", "United Kingdom (Scotland)"));
        origBreeds.add(new DogBreed(context, "Canaan Dog", "Middle East Lebanon, Israel"));
        origBreeds.add(new DogBreed(context, "Canadian Eskimo Dog", "Canada"));
        origBreeds.add(new DogBreed(context, "Cane Corso", "Italy"));
        origBreeds.add(new DogBreed(context, "Cantabrian Water Dog", "Spain"));
        origBreeds.add(new DogBreed(context, "Cão da Serra de Aires", "Portugal"));
        origBreeds.add(new DogBreed(context, "Cão de Castro Laboreiro", "Portugal"));
        origBreeds.add(new DogBreed(context, "Cão Fila de São Miguel", "Portugal"));
        origBreeds.add(new DogBreed(context, "Carolina Dog", "United States"));
        origBreeds.add(new DogBreed(context, "Carpathian Shepherd Dog", "Romania"));
        origBreeds.add(new DogBreed(context, "Catahoula Cur", "United States"));
        origBreeds.add(new DogBreed(context, "Catalan Sheepdog", "Spain"));
        origBreeds.add(new DogBreed(context, "Caucasian Shepherd Dog", "Georgia, Armenia, Azerbaijan"));
        origBreeds.add(new DogBreed(context, "Cavalier King Charles Spaniel", "United Kingdom (England)"));
        origBreeds.add(new DogBreed(context, "Central Asian Shepherd Dog", "Russia"));
        origBreeds.add(new DogBreed(context, "Cesky Fousek", "Czech Republic"));
        origBreeds.add(new DogBreed(context, "Cesky Terrier", "Czech Republic"));
        origBreeds.add(new DogBreed(context, "Chesapeake Bay Retriever", "United States"));
        origBreeds.add(new DogBreed(context, "Chien Français Blanc et Noir", "France"));
        origBreeds.add(new DogBreed(context, "Chien Français Blanc et Orange", "France"));
        origBreeds.add(new DogBreed(context, "Chien Français Tricolore", "France"));
        origBreeds.add(new DogBreed(context, "Chien-gris", "France"));
        origBreeds.add(new DogBreed(context, "Chihuahua", "Mexico"));
        origBreeds.add(new DogBreed(context, "Chilean Fox Terrier", "Chile"));
        origBreeds.add(new DogBreed(context, "Chinese Chongqing Dog", "China"));
        origBreeds.add(new DogBreed(context, "Chinese Crested Dog", "China"));
        origBreeds.add(new DogBreed(context, "Chinese Imperial Dog", "China"));
        origBreeds.add(new DogBreed(context, "Chinook", "United States"));
        origBreeds.add(new DogBreed(context, "Chippiparai", "India"));
        origBreeds.add(new DogBreed(context, "Chow Chow", "China"));
        origBreeds.add(new DogBreed(context, "Cierny Sery", "Slovakia"));
        origBreeds.add(new DogBreed(context, "Cirneco dell'Etna", "Italy"));
        origBreeds.add(new DogBreed(context, "Clumber Spaniel", "United Kingdom (England)"));
        origBreeds.add(new DogBreed(context, "Collie, Rough", "United Kingdom (Scotland)"));
        origBreeds.add(new DogBreed(context, "Collie, Smooth", "United Kingdom (Scotland)"));
        origBreeds.add(new DogBreed(context, "Combai", "India"));
        origBreeds.add(new DogBreed(context, "Cordoba Fighting Dog", "Argentina"));
        origBreeds.add(new DogBreed(context, "Coton de Tulear", "Madagascar"));
        origBreeds.add(new DogBreed(context, "Cretan Hound", "Greece"));
        origBreeds.add(new DogBreed(context, "Croatian Sheepdog", "Croatia"));
        origBreeds.add(new DogBreed(context, "Cumberland Sheepdog", "United Kingdom (England)"));
        origBreeds.add(new DogBreed(context, "Curly Coated Retriever", "United Kingdom (England)"));
        origBreeds.add(new DogBreed(context, "Cursinu", "Corsica (France)"));
        origBreeds.add(new DogBreed(context, "Czechoslovak Wolfdog", "Czech Republic, Slovak Republic"));
        origBreeds.add(new DogBreed(context, "Dachshund", "Germany"));
        origBreeds.add(new DogBreed(context, "Dalmatian", "Croatia"));
        origBreeds.add(new DogBreed(context, "Dandie Dinmont Terrier", "United Kingdom (Scotland)"));
        origBreeds.add(new DogBreed(context, "Danish Swedish Farmdog", "Denmark, Sweden"));
        origBreeds.add(new DogBreed(context, "Decker Rat Terrier", "United States"));
        origBreeds.add(new DogBreed(context, "Deutsche Bracke", "Germany"));
        origBreeds.add(new DogBreed(context, "Doberman Pinscher", "Germany"));
        origBreeds.add(new DogBreed(context, "Dogo Argentino", "Argentina"));
        origBreeds.add(new DogBreed(context, "Dogo Cubano", "Cuba"));
        origBreeds.add(new DogBreed(context, "Dogue de Bordeaux", "France"));
        origBreeds.add(new DogBreed(context, "Drentse Patrijshond", "Netherlands"));
        origBreeds.add(new DogBreed(context, "Drever", "Sweden"));
        origBreeds.add(new DogBreed(context, "Dunker", "Norway"));
        origBreeds.add(new DogBreed(context, "Dutch Shepherd Dog", "Netherlands"));
        origBreeds.add(new DogBreed(context, "Dutch Smoushond", "Netherlands"));
        origBreeds.add(new DogBreed(context, "East Siberian Laika", "Russia"));
        origBreeds.add(new DogBreed(context, "East-European Shepherd", "Russia"));
        origBreeds.add(new DogBreed(context, "Elo", "Germany"));
        origBreeds.add(new DogBreed(context, "English Cocker Spaniel", "United Kingdom (England)"));
        origBreeds.add(new DogBreed(context, "English Coonhound", "United States"));
        origBreeds.add(new DogBreed(context, "English Foxhound", "United Kingdom (England)"));
        origBreeds.add(new DogBreed(context, "English Mastiff", "United Kingdom (England)"));
        origBreeds.add(new DogBreed(context, "English Setter", "United Kingdom (England)"));
        origBreeds.add(new DogBreed(context, "English Shepherd", "United States"));
        origBreeds.add(new DogBreed(context, "English Springer Spaniel", "United Kingdom (England)"));
        origBreeds.add(new DogBreed(context, "English Toy Terrier", "United Kingdom (England)"));
        origBreeds.add(new DogBreed(context, "English Water Spaniel", "United Kingdom"));
        origBreeds.add(new DogBreed(context, "English White Terrier", "United Kingdom"));
        origBreeds.add(new DogBreed(context, "Entlebucher Mountain Dog", "Switzerland"));
        origBreeds.add(new DogBreed(context, "Épagneul Bleu de Picardie", "France"));
        origBreeds.add(new DogBreed(context, "Estonian Hound", "Estonia"));
        origBreeds.add(new DogBreed(context, "Estrela Mountain Dog", "Portugal"));
        origBreeds.add(new DogBreed(context, "Eurasier", "Germany"));
        origBreeds.add(new DogBreed(context, "Field Spaniel", "United Kingdom (England)"));
        origBreeds.add(new DogBreed(context, "Fila Brasileiro", "Brazil"));
        origBreeds.add(new DogBreed(context, "Finnish Hound", "Finland"));
        origBreeds.add(new DogBreed(context, "Finnish Lapphund", "Finland"));
        origBreeds.add(new DogBreed(context, "Finnish Spitz", "Finland"));
        origBreeds.add(new DogBreed(context, "Flat-Coated Retriever", "United Kingdom"));
        origBreeds.add(new DogBreed(context, "Fox Terrier (Smooth)", "United Kingdom (England)"));
        origBreeds.add(new DogBreed(context, "Fox Terrier, Wire", "United Kingdom (England)"));
        origBreeds.add(new DogBreed(context, "French Brittany", "France"));
        origBreeds.add(new DogBreed(context, "French Bulldog", "United Kingdom (England), France"));
        origBreeds.add(new DogBreed(context, "French Spaniel", "France"));
        origBreeds.add(new DogBreed(context, "Gaddi Dog", "Pakistan"));
        origBreeds.add(new DogBreed(context, "Galgo Español", "Spain"));
        origBreeds.add(new DogBreed(context, "Galician Cattle Dog", "Spain"));
        origBreeds.add(new DogBreed(context, "Garafía Shepherd Dog", "Spain"));
        origBreeds.add(new DogBreed(context, "Gascon Saintongeois", "France"));
        origBreeds.add(new DogBreed(context, "Georgian Shepherd Dog", "Georgia"));
        origBreeds.add(new DogBreed(context, "German Longhaired Pointer", "Germany"));
        origBreeds.add(new DogBreed(context, "German Pinscher", "Germany"));
        origBreeds.add(new DogBreed(context, "German Rough-haired Pointer", "Germany"));
        origBreeds.add(new DogBreed(context, "German Shepherd Dog", "Germany"));
        origBreeds.add(new DogBreed(context, "German Shorthaired Pointer", "Germany"));
        origBreeds.add(new DogBreed(context, "German Spaniel", "Germany"));
        origBreeds.add(new DogBreed(context, "German Spitz", "Germany"));
        origBreeds.add(new DogBreed(context, "German Wirehaired Pointer", "Germany"));
        origBreeds.add(new DogBreed(context, "Giant Schnauzer", "Germany"));
        origBreeds.add(new DogBreed(context, "Glen of Imaal Terrier", "Ireland"));
        origBreeds.add(new DogBreed(context, "Golden Retriever", "United Kingdom (Scotland)"));
        origBreeds.add(new DogBreed(context, "Gordon Setter", "United Kingdom (Scotland)"));
        origBreeds.add(new DogBreed(context, "Gran Mastín de Borínquen", "Puerto Rico"));
        origBreeds.add(new DogBreed(context, "Grand Anglo-Français Blanc et Noir", "France"));
        origBreeds.add(new DogBreed(context, "Grand Anglo-Français Blanc et Orange", "France"));
        origBreeds.add(new DogBreed(context, "Grand Anglo-Français Tricolore", "France"));
        origBreeds.add(new DogBreed(context, "Grand Griffon Vendéen", "France"));
        origBreeds.add(new DogBreed(context, "Great Dane", "Germany"));
        origBreeds.add(new DogBreed(context, "Great Pyrenees", "France, Spain"));
        origBreeds.add(new DogBreed(context, "Greater Swiss Mountain Dog", "Switzerland"));
        origBreeds.add(new DogBreed(context, "Greek Harehound", "Greece"));
        origBreeds.add(new DogBreed(context, "Greenland Dog", "Greenland"));
        origBreeds.add(new DogBreed(context, "Greyhound", "United Kingdom, Middle East"));
        origBreeds.add(new DogBreed(context, "Griffon Bleu de Gascogne", "France"));
        origBreeds.add(new DogBreed(context, "Griffon Bruxellois", "Belgium"));
        origBreeds.add(new DogBreed(context, "Griffon Fauve de Bretagne", "France"));
        origBreeds.add(new DogBreed(context, "Griffon Nivernais", "France"));
        origBreeds.add(new DogBreed(context, "Guatemalan Dogo", "Guatemala"));
        origBreeds.add(new DogBreed(context, "Gull Terrier", "Pakistan"));
        origBreeds.add(new DogBreed(context, "Hamiltonstövare", "Sweden"));
        origBreeds.add(new DogBreed(context, "Hanover Hound", "Germany"));
        origBreeds.add(new DogBreed(context, "Hare Indian Dog", "Canada, United States"));
        origBreeds.add(new DogBreed(context, "Harrier", "United Kingdom"));
        origBreeds.add(new DogBreed(context, "Havanese", "Cuba"));
        origBreeds.add(new DogBreed(context, "Hawaiian Poi Dog", "United States"));
        origBreeds.add(new DogBreed(context, "Himalayan Sheepdog", "Nepal"));
        origBreeds.add(new DogBreed(context, "Hokkaido Ken", "Japan"));
        origBreeds.add(new DogBreed(context, "Hortaya Borzaya", "Ukraine, Southern Russia, Belarus"));
        origBreeds.add(new DogBreed(context, "Hovawart", "Germany"));
        origBreeds.add(new DogBreed(context, "Huntaway", "New Zealand"));
        origBreeds.add(new DogBreed(context, "Hygenhund", "Norway"));
        origBreeds.add(new DogBreed(context, "Ibizan Hound", "Spain"));
        origBreeds.add(new DogBreed(context, "Icelandic Sheepdog", "Iceland"));
        origBreeds.add(new DogBreed(context, "Indian pariah dog", "India"));
        origBreeds.add(new DogBreed(context, "Indian Spitz", "India"));
        origBreeds.add(new DogBreed(context, "Irish Red and White Setter", "Ireland"));
        origBreeds.add(new DogBreed(context, "Irish Setter", "Ireland"));
        origBreeds.add(new DogBreed(context, "Irish Terrier", "Ireland"));
        origBreeds.add(new DogBreed(context, "Irish Water Spaniel", "Ireland"));
        origBreeds.add(new DogBreed(context, "Irish Wolfhound", "Ireland"));
        origBreeds.add(new DogBreed(context, "Istrian Coarse-haired Hound", "Croatia"));
        origBreeds.add(new DogBreed(context, "Istrian Shorthaired Hound", "Croatia"));
        origBreeds.add(new DogBreed(context, "Italian Greyhound", "Italy"));
        origBreeds.add(new DogBreed(context, "Jack Russell Terrier", "United Kingdom (England)"));
        origBreeds.add(new DogBreed(context, "Jagdterrier", "Germany"));
        origBreeds.add(new DogBreed(context, "Jämthund", "Sweden"));
        origBreeds.add(new DogBreed(context, "Japanese Chin", "Japan"));
        origBreeds.add(new DogBreed(context, "Japanese Spitz", "Japan"));
        origBreeds.add(new DogBreed(context, "Japanese Terrier", "Japan"));
        origBreeds.add(new DogBreed(context, "Kaikadi", "India"));
        origBreeds.add(new DogBreed(context, "Kai Ken", "Japan"));
        origBreeds.add(new DogBreed(context, "Kangal Dog", "Turkey"));
        origBreeds.add(new DogBreed(context, "Kanni", "India"));
        origBreeds.add(new DogBreed(context, "Karakachan Dog", "Bulgaria"));
        origBreeds.add(new DogBreed(context, "Karelian Bear Dog", "Finland"));
        origBreeds.add(new DogBreed(context, "Karst Shepherd", "Slovenia"));
        origBreeds.add(new DogBreed(context, "Keeshond", "Netherlands, Germany"));
        origBreeds.add(new DogBreed(context, "Kerry Beagle", "Ireland"));
        origBreeds.add(new DogBreed(context, "Kerry Blue Terrier", "Ireland"));
        origBreeds.add(new DogBreed(context, "King Charles Spaniel", "United Kingdom (England)"));
        origBreeds.add(new DogBreed(context, "King Shepherd", "United States"));
        origBreeds.add(new DogBreed(context, "Kintamani", "Indonesia"));
        origBreeds.add(new DogBreed(context, "Kishu Ken", "Japan"));
        origBreeds.add(new DogBreed(context, "Komondor", "Hungary"));
        origBreeds.add(new DogBreed(context, "Kooikerhondje", "Netherlands"));
        origBreeds.add(new DogBreed(context, "Koolie", "Australia"));
        origBreeds.add(new DogBreed(context, "Korean Jindo Dog", "South Korea"));
        origBreeds.add(new DogBreed(context, "Kromfohrländer", "Germany"));
        origBreeds.add(new DogBreed(context, "Kumaon Mastiff", "India"));
        origBreeds.add(new DogBreed(context, "Kunming Wolfdog", "China"));
        origBreeds.add(new DogBreed(context, "Kuri", "New Zealand"));
        origBreeds.add(new DogBreed(context, "Kuvasz", "Hungary"));
        origBreeds.add(new DogBreed(context, "Kyi-Leo", "United States"));
        origBreeds.add(new DogBreed(context, "Labrador Husky", "Canada"));
        origBreeds.add(new DogBreed(context, "Labrador Retriever", "Canada, United Kingdom (England)"));
        origBreeds.add(new DogBreed(context, "Lagotto Romagnolo", "Italy"));
        origBreeds.add(new DogBreed(context, "Lakeland Terrier", "United Kingdom (England)"));
        origBreeds.add(new DogBreed(context, "Lancashire Heeler", "United Kingdom (England)"));
        origBreeds.add(new DogBreed(context, "Landseer", "Canada"));
        origBreeds.add(new DogBreed(context, "Lapponian Herder", "Finland"));
        origBreeds.add(new DogBreed(context, "Leonberger", "Germany"));
        origBreeds.add(new DogBreed(context, "Lhasa Apso", "Tibet"));
        origBreeds.add(new DogBreed(context, "Lithuanian Hound", "Lithuania"));
        origBreeds.add(new DogBreed(context, "Longhaired Whippet", "United States"));
        origBreeds.add(new DogBreed(context, "Löwchen", "Germany, France, Netherlands, Spain"));
        origBreeds.add(new DogBreed(context, "Magyar Agár", "Hungary, Transylvania"));
        origBreeds.add(new DogBreed(context, "Mahratta Greyhound", "India"));
        origBreeds.add(new DogBreed(context, "Majorca Ratter", "Spain"));
        origBreeds.add(new DogBreed(context, "Majorca Shepherd Dog", "Spain"));
        origBreeds.add(new DogBreed(context, "Maltese", "Italy"));
        origBreeds.add(new DogBreed(context, "Manchester Terrier", "United Kingdom (England)"));
        origBreeds.add(new DogBreed(context, "Maremma Sheepdog", "Italy"));
        origBreeds.add(new DogBreed(context, "McNab", "United States"));
        origBreeds.add(new DogBreed(context, "Mexican Hairless Dog", "Mexico"));
        origBreeds.add(new DogBreed(context, "Miniature Australian Shepherd", "United States"));
        origBreeds.add(new DogBreed(context, "Miniature American Shepherd", "United States"));
        origBreeds.add(new DogBreed(context, "Miniature Fox Terrier", "Australia"));
        origBreeds.add(new DogBreed(context, "Miniature Pinscher", "Germany"));
        origBreeds.add(new DogBreed(context, "Miniature Schnauzer", "Germany"));
        origBreeds.add(new DogBreed(context, "Miniature Shar Pei", "China"));
        origBreeds.add(new DogBreed(context, "Mioritic", "Romania"));
        origBreeds.add(new DogBreed(context, "Molossus", "Greece"));
        origBreeds.add(new DogBreed(context, "Molossus of Epirus", "Greece"));
        origBreeds.add(new DogBreed(context, "Montenegrin Mountain Hound", "Montenegro"));
        origBreeds.add(new DogBreed(context, "Moscow Watchdog", "Russia"));
        origBreeds.add(new DogBreed(context, "Moscow Water Dog", "Russia"));
        origBreeds.add(new DogBreed(context, "Mountain Cur", "United States"));
        origBreeds.add(new DogBreed(context, "Mucuchies", "Venezuela"));
        origBreeds.add(new DogBreed(context, "Mudhol Hound", "India"));
        origBreeds.add(new DogBreed(context, "Mudi", "Hungary"));
        origBreeds.add(new DogBreed(context, "Münsterländer, Large", "Germany"));
        origBreeds.add(new DogBreed(context, "Münsterländer, Small", "Germany"));
        origBreeds.add(new DogBreed(context, "Murcian Ratter", "Spain"));
        origBreeds.add(new DogBreed(context, "Neapolitan Mastiff", "Italy"));
        origBreeds.add(new DogBreed(context, "Newfoundland", "Canada"));
        origBreeds.add(new DogBreed(context, "New Zealand Heading Dog", "New Zealand"));
        origBreeds.add(new DogBreed(context, "Norfolk Spaniel", "United Kingdom"));
        origBreeds.add(new DogBreed(context, "Norfolk Terrier", "United Kingdom"));
        origBreeds.add(new DogBreed(context, "Norrbottenspets", "Sweden"));
        origBreeds.add(new DogBreed(context, "North Country Beagle", "United Kingdom"));
        origBreeds.add(new DogBreed(context, "Northern Inuit Dog", "United Kingdom (England)"));
        origBreeds.add(new DogBreed(context, "Norwegian Buhund", "Norway"));
        origBreeds.add(new DogBreed(context, "Norwegian Elkhound", "Norway"));
        origBreeds.add(new DogBreed(context, "Norwegian Lundehund", "Norway"));
        origBreeds.add(new DogBreed(context, "Norwich Terrier", "United Kingdom"));
        origBreeds.add(new DogBreed(context, "Nova Scotia Duck-Tolling Retriever", "Canada"));
        origBreeds.add(new DogBreed(context, "Old Croatian Sighthound", "Croatia"));
        origBreeds.add(new DogBreed(context, "Old Danish Pointer", "Denmark"));
        origBreeds.add(new DogBreed(context, "Old English Sheepdog", "United Kingdom (England)"));
        origBreeds.add(new DogBreed(context, "Old English Terrier", "United Kingdom (England)"));
        origBreeds.add(new DogBreed(context, "Old German Shepherd Dog", "Germany"));
        origBreeds.add(new DogBreed(context, "Old Time Farm Shepherd", "United States"));
        origBreeds.add(new DogBreed(context, "Olde English Bulldogge", "United States"));
        origBreeds.add(new DogBreed(context, "Otterhound", "United Kingdom (England)"));
        origBreeds.add(new DogBreed(context, "Pachon Navarro", "Spain"));
        origBreeds.add(new DogBreed(context, "Pandikona", "India"));
        origBreeds.add(new DogBreed(context, "Paisley Terrier", "United Kingdom (Scotland)"));
        origBreeds.add(new DogBreed(context, "Papillon", "Spain, Belgium, France"));
        origBreeds.add(new DogBreed(context, "Parson Russell Terrier", "United Kingdom (England)"));
        origBreeds.add(new DogBreed(context, "Patterdale Terrier", "United Kingdom (England)"));
        origBreeds.add(new DogBreed(context, "Pekingese", "China"));
        origBreeds.add(new DogBreed(context, "Perro de Presa Canario", "Spain"));
        origBreeds.add(new DogBreed(context, "Perro de Presa Mallorquin", "Spain"));
        origBreeds.add(new DogBreed(context, "Perro fino Colombiano", "Colombia"));
        origBreeds.add(new DogBreed(context, "Peruvian Hairless Dog", "Peru"));
        origBreeds.add(new DogBreed(context, "Phalène", "Belgium, Spain"));
        origBreeds.add(new DogBreed(context, "Pharaoh Hound", "Malta"));
        origBreeds.add(new DogBreed(context, "Phu Quoc ridgeback dog", "Vietnam"));
        origBreeds.add(new DogBreed(context, "Picardy Spaniel", "France"));
        origBreeds.add(new DogBreed(context, "Plott Hound", "United States"));
        origBreeds.add(new DogBreed(context, "Podenco Canario", "Spain"));
        origBreeds.add(new DogBreed(context, "Pointer", "United Kingdom (England)"));
        origBreeds.add(new DogBreed(context, "Poitevin", "France"));
        origBreeds.add(new DogBreed(context, "Polish Greyhound", "Poland"));
        origBreeds.add(new DogBreed(context, "Polish Hound", "Poland"));
        origBreeds.add(new DogBreed(context, "Polish Hunting Dog", "Poland"));
        origBreeds.add(new DogBreed(context, "Polish Lowland Sheepdog", "Poland"));
        origBreeds.add(new DogBreed(context, "Polish Tatra Sheepdog", "Poland"));
        origBreeds.add(new DogBreed(context, "Pomeranian", "Germany, Poland"));
        origBreeds.add(new DogBreed(context, "Pont-Audemer Spaniel", "France"));
        origBreeds.add(new DogBreed(context, "Poodle", "Germany, France"));
        origBreeds.add(new DogBreed(context, "Porcelaine", "France"));
        origBreeds.add(new DogBreed(context, "Portuguese Podengo", "Portugal"));
        origBreeds.add(new DogBreed(context, "Portuguese Pointer", "Portugal"));
        origBreeds.add(new DogBreed(context, "Portuguese Water Dog", "Portugal"));
        origBreeds.add(new DogBreed(context, "Posavac Hound", "Croatia"));
        origBreeds.add(new DogBreed(context, "Pražský Krysarík", "Czech Republic"));
        origBreeds.add(new DogBreed(context, "Pudelpointer", "Germany"));
        origBreeds.add(new DogBreed(context, "Pug", "China"));
        origBreeds.add(new DogBreed(context, "Puli", "Hungary"));
        origBreeds.add(new DogBreed(context, "Pumi", "Hungary"));
        origBreeds.add(new DogBreed(context, "Pungsan Dog", "North Korea"));
        origBreeds.add(new DogBreed(context, "Pyrenean Mastiff", "Spain"));
        origBreeds.add(new DogBreed(context, "Pyrenean Shepherd", "France"));
        origBreeds.add(new DogBreed(context, "Rafeiro do Alentejo", "Portugal"));
        origBreeds.add(new DogBreed(context, "Rajapalayam", "India"));
        origBreeds.add(new DogBreed(context, "Rampur Greyhound", "India"));
        origBreeds.add(new DogBreed(context, "Rastreador Brasileiro", "Brazil"));
        origBreeds.add(new DogBreed(context, "Ratonero Bodeguero Andaluz", "Spain"));
        origBreeds.add(new DogBreed(context, "Ratonero Valenciano", "Spain"));
        origBreeds.add(new DogBreed(context, "Rat Terrier", "United States"));
        origBreeds.add(new DogBreed(context, "Redbone Coonhound", "United States"));
        origBreeds.add(new DogBreed(context, "Rhodesian Ridgeback", "Zimbabwe"));
        origBreeds.add(new DogBreed(context, "Rottweiler", "Germany"));
        origBreeds.add(new DogBreed(context, "Russian Spaniel", "Russia"));
        origBreeds.add(new DogBreed(context, "Russian Toy", "Russia"));
        origBreeds.add(new DogBreed(context, "Russian tracker", "Russia"));
        origBreeds.add(new DogBreed(context, "Russo-European Laika", "Russia"));
        origBreeds.add(new DogBreed(context, "Russell Terrier", "United Kingdom (England)"));
        origBreeds.add(new DogBreed(context, "Saarlooswolfhond", "Netherlands, Germany"));
        origBreeds.add(new DogBreed(context, "Sabueso Español", "Spain"));
        origBreeds.add(new DogBreed(context, "Saint-Usuge Spaniel", "France"));
        origBreeds.add(new DogBreed(context, "Sakhalin Husky", "Japan"));
        origBreeds.add(new DogBreed(context, "Saluki", "Middle East Syria, Lebabnon"));
        origBreeds.add(new DogBreed(context, "Samoyed", "Russia"));
        origBreeds.add(new DogBreed(context, "Santal Hound", "India"));
        origBreeds.add(new DogBreed(context, "Sapsali", "South Korea, North Korea"));
        origBreeds.add(new DogBreed(context, "Šarplaninac", "Serbia, Republic of Macedonia"));
        origBreeds.add(new DogBreed(context, "Schapendoes", "Netherlands"));
        origBreeds.add(new DogBreed(context, "Schillerstövare", "Sweden"));
        origBreeds.add(new DogBreed(context, "Schipperke", "Belgium"));
        origBreeds.add(new DogBreed(context, "Standard Schnauzer", "Germany"));
        origBreeds.add(new DogBreed(context, "Schweizer Laufhund", "Switzerland"));
        origBreeds.add(new DogBreed(context, "Schweizerischer Niederlaufhund", "Switzerland"));
        origBreeds.add(new DogBreed(context, "Scotch Collie", "United Kingdom (Scotland)"));
        origBreeds.add(new DogBreed(context, "Scottish Deerhound", "United Kingdom (Scotland)"));
        origBreeds.add(new DogBreed(context, "Scottish Terrier", "United Kingdom (Scotland)"));
        origBreeds.add(new DogBreed(context, "Sealyham Terrier", "United Kingdom (Wales)"));
        origBreeds.add(new DogBreed(context, "Segugio Italiano", "Italy"));
        origBreeds.add(new DogBreed(context, "Seppala Siberian Sleddog", "Canada"));
        origBreeds.add(new DogBreed(context, "Serbian Hound", "Serbia"));
        origBreeds.add(new DogBreed(context, "Serbian Tricolour Hound", "Serbia"));
        origBreeds.add(new DogBreed(context, "Seskar Seal Dog", "Finland"));
        origBreeds.add(new DogBreed(context, "Shar Pei", "China"));
        origBreeds.add(new DogBreed(context, "Shetland Sheepdog", "United Kingdom (Scotland)"));
        origBreeds.add(new DogBreed(context, "Shiba Inu", "Japan"));
        origBreeds.add(new DogBreed(context, "Shih Tzu", "China"));
        origBreeds.add(new DogBreed(context, "Shikoku Ken", "Japan"));
        origBreeds.add(new DogBreed(context, "Shiloh Shepherd Dog", "United States"));
        origBreeds.add(new DogBreed(context, "Siberian Husky", "Russia"));
        origBreeds.add(new DogBreed(context, "Silken Windhound", "United States"));
        origBreeds.add(new DogBreed(context, "Sinhala Hound", "Sri Lanka"));
        origBreeds.add(new DogBreed(context, "Skye Terrier", "United Kingdom (Scotland)"));
        origBreeds.add(new DogBreed(context, "Sloughi", "Morocco"));
        origBreeds.add(new DogBreed(context, "Slovak Cuvac", "Slovakia"));
        origBreeds.add(new DogBreed(context, "Slovakian Rough-haired Pointer", "Slovakia"));
        origBreeds.add(new DogBreed(context, "Slovenský Kopov", "Slovakia"));
        origBreeds.add(new DogBreed(context, "Smålandsstövare", "Sweden"));
        origBreeds.add(new DogBreed(context, "Small Greek Domestic Dog", "Greece"));
        origBreeds.add(new DogBreed(context, "Soft-Coated Wheaten Terrier", "Ireland"));
        origBreeds.add(new DogBreed(context, "South Russian Ovcharka", "Russia"));
        origBreeds.add(new DogBreed(context, "Southern Hound", "United Kingdom"));
        origBreeds.add(new DogBreed(context, "Spanish Mastiff", "Spain"));
        origBreeds.add(new DogBreed(context, "Spanish Water Dog", "Spain"));
        origBreeds.add(new DogBreed(context, "Spinone Italiano", "Italy"));
        origBreeds.add(new DogBreed(context, "Sporting Lucas Terrier", "United Kingdom (England)"));
        origBreeds.add(new DogBreed(context, "St. Bernard", "Switzerland, Italy"));
        origBreeds.add(new DogBreed(context, "St. John's Water Dog", "Canada (Newfoundland)"));
        origBreeds.add(new DogBreed(context, "Stabyhoun", "Netherlands"));
        origBreeds.add(new DogBreed(context, "Staffordshire Bull Terrier", "United Kingdom (England)"));
        origBreeds.add(new DogBreed(context, "Stephens Cur", "United States"));
        origBreeds.add(new DogBreed(context, "Styrian Coarse-haired Hound", "Austria"));
        origBreeds.add(new DogBreed(context, "Sussex Spaniel", "United Kingdom (England)"));
        origBreeds.add(new DogBreed(context, "Swedish Lapphund", "Sweden"));
        origBreeds.add(new DogBreed(context, "Swedish Vallhund", "Sweden"));
        origBreeds.add(new DogBreed(context, "Tahltan Bear Dog", "Canada"));
        origBreeds.add(new DogBreed(context, "Taigan", "Kyrgyzstan"));
        origBreeds.add(new DogBreed(context, "Taiwan Dog", "Taiwan"));
        origBreeds.add(new DogBreed(context, "Talbot", "United Kingdom, Belgium, France"));
        origBreeds.add(new DogBreed(context, "Tamaskan Dog", "Finland"));
        origBreeds.add(new DogBreed(context, "Teddy Roosevelt Terrier", "United States"));
        origBreeds.add(new DogBreed(context, "Telomian", "Malaysia"));
        origBreeds.add(new DogBreed(context, "Tennessee Treeing Brindle", "United States"));
        origBreeds.add(new DogBreed(context, "Tenterfield Terrier", "Australia"));
        origBreeds.add(new DogBreed(context, "Terceira Mastiff", "Portugal"));
        origBreeds.add(new DogBreed(context, "Thai Bangkaew Dog", "Thailand"));
        origBreeds.add(new DogBreed(context, "Thai Ridgeback", "Thailand"));
        origBreeds.add(new DogBreed(context, "Tibetan Mastiff", "Tibet"));
        origBreeds.add(new DogBreed(context, "Tibetan Spaniel", "Tibet"));
        origBreeds.add(new DogBreed(context, "Tibetan Terrier", "Tibet"));
        origBreeds.add(new DogBreed(context, "Tornjak", "Bosnia and Herzegovina, Croatia"));
        origBreeds.add(new DogBreed(context, "Tosa", "Japan"));
        origBreeds.add(new DogBreed(context, "Toy Bulldog", "United Kingdom (England)"));
        origBreeds.add(new DogBreed(context, "Toy Fox Terrier", "United States"));
        origBreeds.add(new DogBreed(context, "Toy Manchester Terrier", "United Kingdom (England), United States"));
        origBreeds.add(new DogBreed(context, "Toy Trawler Spaniel", "United Kingdom"));
        origBreeds.add(new DogBreed(context, "Transylvanian Hound", "Hungary"));
        origBreeds.add(new DogBreed(context, "Treeing Cur", "United States"));
        origBreeds.add(new DogBreed(context, "Treeing Walker Coonhound", "United States"));
        origBreeds.add(new DogBreed(context, "Trigg Hound", "United States (Kentucky)"));
        origBreeds.add(new DogBreed(context, "Tweed Water Spaniel", "United Kingdom"));
        origBreeds.add(new DogBreed(context, "Tyrolean Hound", "Austria"));
        origBreeds.add(new DogBreed(context, "Uruguayan Cimarron", "Uruguay"));
        origBreeds.add(new DogBreed(context, "Vanjari Hound", "India"));
        origBreeds.add(new DogBreed(context, "Villano de las Encartaciones", "Spain"));
        origBreeds.add(new DogBreed(context, "Vizsla", "Hungary"));
        origBreeds.add(new DogBreed(context, "Volpino Italiano", "Italy"));
        origBreeds.add(new DogBreed(context, "Weimaraner", "Germany"));
        origBreeds.add(new DogBreed(context, "Welsh Corgi, Cardigan", "United Kingdom (Wales)"));
        origBreeds.add(new DogBreed(context, "Welsh Corgi, Pembroke", "United Kingdom (Wales)"));
        origBreeds.add(new DogBreed(context, "Welsh Sheepdog", "United Kingdom (Wales)"));
        origBreeds.add(new DogBreed(context, "Welsh Springer Spaniel", "United Kingdom (Wales)"));
        origBreeds.add(new DogBreed(context, "Welsh Terrier", "United Kingdom (Wales)"));
        origBreeds.add(new DogBreed(context, "West Highland White Terrier", "United Kingdom (Scotland)"));
        origBreeds.add(new DogBreed(context, "West Siberian Laika", "Russia"));
        origBreeds.add(new DogBreed(context, "Westphalian Dachsbracke", "Germany"));
        origBreeds.add(new DogBreed(context, "Wetterhoun", "Netherlands"));
        origBreeds.add(new DogBreed(context, "Whippet", "England"));
        origBreeds.add(new DogBreed(context, "White Shepherd", "United States"));
        origBreeds.add(new DogBreed(context, "Wirehaired Pointing Griffon", "Netherlands, France"));
        origBreeds.add(new DogBreed(context, "Wirehaired Vizsla", "Hungary"));
        origBreeds.add(new DogBreed(context, "Xiasi Dog", "China"));
        origBreeds.add(new DogBreed(context, "Yorkshire Terrier", "United Kingdom (England)"));

        filtBreeds = origBreeds;
        Log.v(TAG, "filtBreeds: " + filtBreeds.toString());

    }

    public class Holder
    {
        TextView tv1;
        TextView tv2;
        ImageView img;
        ImageView flag;
    }

    public Filter getFilter() {
        return mFilter;
    }

    private class ItemFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            // constraint is set toLowerCase in MainActivity.java

            Log.v(TAG, "filter is: " + constraint);

            FilterResults results = new FilterResults();

            ArrayList<DogBreed> nBreeds;

            if (constraint.toString().equals("top breeds")) {

                nBreeds = new ArrayList<DogBreed>(20);

                nBreeds.add(new DogBreed(context, " 1. Labrador Retriever", "Canada, United Kingdom (England)"));
                nBreeds.add(new DogBreed(context, " 2. German Shepherd Dog", "Germany"));
                nBreeds.add(new DogBreed(context, " 3. Golden Retriever", "United Kingdom (Scotland)"));
                nBreeds.add(new DogBreed(context, " 4. Bulldog", "United Kingdom (England)"));
                nBreeds.add(new DogBreed(context, " 5. Beagle", "United Kingdom (England)"));
                nBreeds.add(new DogBreed(context, " 6. French Bulldog", "United Kingdom (England), France"));
                nBreeds.add(new DogBreed(context, " 7. Yorkshire Terrier", "United Kingdom (England)"));
                nBreeds.add(new DogBreed(context, " 8. Poodle", "Germany, France"));
                nBreeds.add(new DogBreed(context, " 9. Rottweiler", "Germany"));
                nBreeds.add(new DogBreed(context, "10. Boxer", "Germany"));
                nBreeds.add(new DogBreed(context, "11. German Shorthaired Pointer", "Germany"));
                nBreeds.add(new DogBreed(context, "12. Siberian Husky", "Russia"));
                nBreeds.add(new DogBreed(context, "13. Dachshund", "Germany"));
                nBreeds.add(new DogBreed(context, "14. Doberman Pinscher", "Germany"));
                nBreeds.add(new DogBreed(context, "15. Great Dane", "Germany"));
                nBreeds.add(new DogBreed(context, "16. Miniature Schnauzer", "Germany"));
                nBreeds.add(new DogBreed(context, "17. Australian Shepherd", "United States"));
                nBreeds.add(new DogBreed(context, "18. Cavalier King Charles Spaniel", "United Kingdom (England)"));
                nBreeds.add(new DogBreed(context, "19. Shih Tzu", "China"));
                nBreeds.add(new DogBreed(context, "20. Welsh Corgi, Pembroke", "United Kingdom (Wales)"));
            }
            else if (constraint.toString().equals("quiz results")) {
                nBreeds = new ArrayList<DogBreed>(27);

                nBreeds = quizBreeds;

            }
            else {
                nBreeds = new ArrayList<DogBreed>(origBreeds.size());

                for (int i = 0; i < origBreeds.size(); i++) {
                    if (origBreeds.get(i).getName().toLowerCase().indexOf(constraint.toString()) != -1 ||
                            origBreeds.get(i).getCountry().toLowerCase().indexOf(constraint.toString()) != -1) {
                        nBreeds.add(origBreeds.get(i));
                        Log.v(TAG, "match: " + origBreeds.get(i).getName());
                    }
                }
            }

            results.values = nBreeds;
            results.count = nBreeds.size();

            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filtBreeds = (ArrayList<DogBreed>) results.values;
            notifyDataSetChanged();
        }

    }

}
