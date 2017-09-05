package ir.berimbasket.app.activity.fragment;

import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import ir.berimbasket.app.Adapter.AdapterMatchBoard;
import ir.berimbasket.app.Adapter.AdapterPlayer;
import ir.berimbasket.app.Adapter.AdapterStadium;
import ir.berimbasket.app.R;
import ir.berimbasket.app.bundle.BundlePlayer;
import ir.berimbasket.app.bundle.BundleStadium;
import ir.berimbasket.app.json.HttpHandler;

/**
 * Created by mohammad hosein on 5/1/2017.
 */

public class FragmentMatchBoard extends Fragment {

    private SliderLayout sliderMatch;
    private TextView txtMorePlayer, txtMoreStadium;
    private AppCompatButton btnMorePlayer, btnMoreStadium;
    private static String _URL = "http://imenservice.com/bball/getPlayers.php?id=0";
    private static String _URL_STADIUM = "http://imenservice.com/bball/getPlayGroundJson.php?id=0";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_match_board, container, false);
        setupPlayerRecycler(view, playerList);
        sliderMatch = (SliderLayout) view.findViewById(R.id.sliderMatch);
        btnMoreStadium = (AppCompatButton) view.findViewById(R.id.btnMoreStadium);
        btnMorePlayer = (AppCompatButton) view.findViewById(R.id.btnMorePlayer);
        txtMoreStadium = (TextView) view.findViewById(R.id.txtMoreStadium);
        txtMorePlayer = (TextView) view.findViewById(R.id.txtMorePlayer);

        Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/yekan.ttf");

        btnMorePlayer.setTypeface(typeface);
        txtMorePlayer.setTypeface(typeface);
        btnMoreStadium.setTypeface(typeface);
        txtMoreStadium.setTypeface(typeface);

        HashMap<String, Integer> url_maps = new HashMap<String, Integer>();
        url_maps.put("Hannibal", R.drawable.slider1);
        url_maps.put("House of Cards", R.drawable.slider2);
        url_maps.put("Game of Thrones", R.drawable.slider3);

        for (String name : url_maps.keySet()) {
            TextSliderView textSliderView = new TextSliderView(getActivity());
            textSliderView
                    .image(url_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.CenterCrop);

            textSliderView.bundle(new Bundle());
            textSliderView.getBundle().putString("extra", name);

            sliderMatch.addSlider(textSliderView);
        }

        sliderMatch.setPresetTransformer(SliderLayout.Transformer.Default);
        sliderMatch.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        sliderMatch.setCustomAnimation(new DescriptionAnimation());
        sliderMatch.setDuration(4000);

        new GetPlayers().execute();
        new GetStadium().execute();

        return view;
    }

    private void setupMatchRecyclerView(View view) {

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerMatchBoard);
        AdapterMatchBoard adapterMatchBoard = new AdapterMatchBoard(view.getContext());
        recyclerView.setAdapter(adapterMatchBoard);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setItemAnimator(new DefaultItemAnimator());

    }

    private void setupPlayerRecycler(View view, ArrayList<BundlePlayer> playerList) {
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerPlayer);
        AdapterPlayer adapterPlayer = new AdapterPlayer(playerList, view.getContext(), getActivity());
        recyclerView.setAdapter(adapterPlayer);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    private void setupStadiumRecycler(View view, ArrayList<BundleStadium> stadiumList) {
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerStadium);
        AdapterStadium adapterStadium = new AdapterStadium(stadiumList, view.getContext());
        recyclerView.setAdapter(adapterStadium);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    private ArrayList<BundlePlayer> playerList = new ArrayList<>();

    private class GetPlayers extends AsyncTask<Void, Void, Void> {

        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("لطفا صبر کنید ...");
            pDialog.setCancelable(false);
        }

        @Override
        protected Void doInBackground(Void... voids) {

            HttpHandler sh = new HttpHandler(HttpHandler.RequestType.GET);

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(_URL);
            if (jsonStr != null) {
                try {
                    // Getting JSON Array node
                    JSONArray locations = new JSONArray(jsonStr);

                    // looping through All Contacts
                    for (int i = 0; i < locations.length(); i++) {
                        JSONObject c = locations.getJSONObject(i);

                        String id = c.getString("id");
                        String username = c.getString("username");
                        String namefa = c.getString("namefa");
                        String address = c.getString("address");
                        String uImage = c.getString("uImages");
                        String uInstagramId = c.getString("uInstagramId");
                        String uTelegramId = c.getString("uTelegramlId");
                        String height = c.getString("height");
                        String weight = c.getString("weight");
                        String city = c.getString("city");
                        String age = c.getString("age");
                        String coach = c.getString("coach");
                        String teamname = c.getString("teamname");
                        String experience = c.getString("experience");
                        String post = c.getString("post");
                        String telegramPhone = c.getString("telegramphone");

                        Log.i("name", id);

                        BundlePlayer bundlePlayer = new BundlePlayer();
                        // adding each child node to HashMap key => value

                        bundlePlayer.setId(id != "null" ? Integer.parseInt(id) : -1);
                        bundlePlayer.setUsername(username);
                        bundlePlayer.setName(namefa);
                        bundlePlayer.setAddress(address);
                        bundlePlayer.setProfileImage(uImage);
                        bundlePlayer.setInstagramId(uInstagramId);
                        bundlePlayer.setTelegramId(uTelegramId);
                        bundlePlayer.setHeight(height != "null" ? Integer.parseInt(height) : -1);
                        bundlePlayer.setWeight(weight != "null" ? Integer.parseInt(weight) : -1);
                        bundlePlayer.setCity(city);
                        bundlePlayer.setAge(age != "null" ? Integer.parseInt(age) : -1);
                        bundlePlayer.setCoachName(coach);
                        bundlePlayer.setTeamName(teamname);
                        bundlePlayer.setExperience(experience);
//                        bundlePlayer.setPost(post != "" ? Integer.parseInt(post) : -1);
                        bundlePlayer.setPhone(telegramPhone);

                        // adding contact to contact list
                        playerList.add(bundlePlayer);


                    }
                } catch (final JSONException e) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }
            } else {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                    }
                });

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            pDialog.cancel();

            setupPlayerRecycler(getView(), playerList);
        }
    }

    ArrayList<BundleStadium> stadiumList = new ArrayList<>();
    private class GetStadium extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            HttpHandler sh = new HttpHandler(HttpHandler.RequestType.GET);

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(_URL_STADIUM);
            if (jsonStr != null) {
                try {
                    // Getting JSON Array node
                    JSONArray locations = new JSONArray(jsonStr);

                    // looping through All Contacts
                    for (int i = 0; i < locations.length(); i++) {
                        JSONObject c = locations.getJSONObject(i);

                        String id = c.getString("id");
                        String title = c.getString("title");
                        String latitude = c.getString("PlaygroundLatitude");
                        String longitude = c.getString("PlaygroundLongitude");
                        String type = c.getString("PlaygroundType");
                        String zoomLevel = c.getString("ZoomLevel");
                        String address = c.getString("address");
                        String images = c.getString("PgImages");
                        String instagramId = c.getString("PgInstagramId");
                        String telegramChannelId = c.getString("PgTlgrmChannelId");
                        String telegramGroupId = c.getString("PgTlgrmGroupJoinLink");
                        String telegramAdminId = c.getString("PgTlgrmGroupAdminId");

                        BundleStadium bundleStadium = new BundleStadium();
                        // adding each child node to HashMap key => value

                        bundleStadium.setId(id != "null" ? Integer.parseInt(id) : -1);
                        bundleStadium.setTitle(title);
                        bundleStadium.setLatitude(latitude);
                        bundleStadium.setLongitude(longitude);
                        bundleStadium.setAddress(address);
                        bundleStadium.setTelegramGroupId(telegramGroupId);
                        bundleStadium.setTelegramChannelId(telegramChannelId);
                        bundleStadium.setTelegramAdminId(telegramAdminId);
                        bundleStadium.setInstagramId(instagramId);
                        bundleStadium.setImages(images.split(".jpg"));
                        bundleStadium.setType(type);
                        bundleStadium.setZoomLevel(zoomLevel != "null" ? Integer.parseInt(zoomLevel) : -1);

                        // adding contact to contact list
                        stadiumList.add(bundleStadium);


                    }
                } catch (final JSONException e) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            setupStadiumRecycler(getView(), stadiumList);
        }
    }
}
