package appdevelope.imamalys.id.pariwisatayogyakarta;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity {
    // Log tag
    private static final String TAG = MainActivity.class.getSimpleName();

    // Pariwisata json url
    private static final String url = "http://erporate.com/bootcamp/jsonBootcamp.php";

    private ProgressDialog pDialog;
    private List<ArticlePariwisata> apList = new ArrayList<ArticlePariwisata>();
    private ListView listView;
    private CustomListAdapter adapter;
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.list);
        adapter = new CustomListAdapter(this, apList);
        listView.setAdapter(adapter);

        // Click event for single list row
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                ArticlePariwisata aP = apList.get(position);
                Intent intent = new Intent(MainActivity.this, DetailPariwisata.class);
                intent.putExtra("nama", aP.getNama());
                intent.putExtra("detail", aP.getDetail());
                intent.putExtra("alamat", aP.getAlamat());
                intent.putExtra("gambar", aP.getGambar());

                startActivity(intent);
            }
        });

        Toolbar toolbar = findViewById(R.id.toolbarmain);
        setSupportActionBar(toolbar);

        //get firebase auth instance
        auth = FirebaseAuth.getInstance();

        //get current user
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    finish();
                }
            }
        };

        pDialog = new ProgressDialog(this);
        // Showing progress dialog before making http request
        pDialog.setMessage("Loading...");
        pDialog.show();

        // Creating volley request obj
        StringRequest movieReq = new StringRequest(url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response.toString());
                        hidePDialog();
                        try {
                            JSONObject obj = new JSONObject(response);
                            if (obj.getBoolean("result") == true) {
                                JSONArray apArray = obj.getJSONArray("data");
                                for (int i = 0; i < response.length(); i++) {


                                    JSONObject apobj = (JSONObject) apArray.get(i);
                                    ArticlePariwisata ap = new ArticlePariwisata();
                                    ap.setNama(apobj.getString("nama_pariwisata"));
                                    ap.setGambar(apobj.getString("gambar_pariwisata"));
                                    ap.setAlamat(apobj.getString("alamat_pariwisata"));
                                    ap.setDetail(apobj.getString("detail_pariwisata"));

                                    // adding pariwisata to ap array
                                    apList.add(ap);
                                }
                            }else {

                            }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                         adapter.notifyDataSetChanged();
                        }


                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                hidePDialog();

            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(movieReq);
    }

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.action_logout:
                auth.signOut();
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        auth.addAuthStateListener(authListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (authListener != null) {
            auth.removeAuthStateListener(authListener);
        }
    }
}

