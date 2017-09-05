package ir.berimbasket.app.activity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import ir.berimbasket.app.R;
import ir.berimbasket.app.activity.fragment.FragmentSetMarker;
import ir.berimbasket.app.json.HttpHandler;

public class ActivitySetMarker extends AppCompatActivity {

    private ImageView btnSendLocation;
    private AppCompatEditText edtLocationName;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_marker);

        initViews();
        initListeners();

    }

    private void initViews() {
        btnSendLocation = (ImageView) findViewById(R.id.btnSendMarker);
        edtLocationName = (AppCompatEditText) findViewById(R.id.edtLocationName);
    }

    private void initListeners() {
        btnSendLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.mapFrag);
                FragmentSetMarker fragmentSetMarker = (FragmentSetMarker) fragment;
                double latitude = fragmentSetMarker.getLatitude();
                double longitude = fragmentSetMarker.getLongitude();
                Log.i("message", String.valueOf(latitude));
                if (!edtLocationName.getText().toString().equals("")) {
                    String Url = "http://imenservice.com/bball/set.php?token=jkhfgkljhasfdlkh&lat=" + latitude + "&long=" + longitude + "&title=" + edtLocationName.getText();
                    Url.replace(" ", "%20");
                    new PostLocation().execute(Url);

                } else {
                    Toast.makeText(getApplicationContext(), "لطفا نام ورزشگاه را وارد کنید", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private class PostLocation extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ActivitySetMarker.this);
            pDialog.setMessage("لطفا صبر کنید ...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            HttpHandler sh = new HttpHandler(HttpHandler.RequestType.POST);
            // Making a request to url and getting response
            String hg = sh.makeServiceCall(strings[0]);
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pDialog.cancel();
            Toast.makeText(getApplicationContext(), "نقطه مد نظر شما با موفقیت ثبت شد", Toast.LENGTH_SHORT).show();
            ActivitySetMarker.this.finish();
        }
    }
}
