package id.co.gitsolution.kamus.view;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import id.co.gitsolution.kamus.R;
import id.co.gitsolution.kamus.adapter.AKamus;
import id.co.gitsolution.kamus.model.ModelKamus;
import id.co.gitsolution.kamus.other.KamusHelper;
import id.co.gitsolution.kamus.other.SPManager;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView rvKamus;
    private SearchView svKamus;
    private List<ModelKamus> kamusList;
    private AKamus aKamus;
    private SPManager spManager;
    private KamusHelper kamusHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        initView();
        initEvent();
    }

    private void initEvent() {
        svKamus.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                aKamus.filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                aKamus.filter(newText);
                return false;
            }
        });
    }

    private void initView() {
        rvKamus = findViewById(R.id.rvKamus);
        svKamus = findViewById(R.id.svKamus);

        kamusList = new ArrayList<>();
        kamusHelper = new KamusHelper(this);
        kamusHelper.open();

        spManager = new SPManager(this);
        if (spManager.getSpBahasa().equalsIgnoreCase(getString(R.string.indo))) {
            kamusList.addAll(kamusHelper.queryIndo());
            Collections.reverse(kamusList);
            getSupportActionBar().setTitle(getString(R.string.app_name) + " " + getString(R.string.indonesia));
        } else if (spManager.getSpBahasa().equalsIgnoreCase(getString(R.string.eng))) {
            kamusList.addAll(kamusHelper.queryEng());
            Collections.reverse(kamusList);
            getSupportActionBar().setTitle(getString(R.string.app_name) + " " + getString(R.string.inggris));
        }

        kamusHelper.close();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvKamus.setLayoutManager(linearLayoutManager);
        rvKamus.setNestedScrollingEnabled(false);
        aKamus = new AKamus(this, kamusList);
        rvKamus.setAdapter(aKamus);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_toenglish) {
            spManager.saveSPString(SPManager.SP_BAHASA, getString(R.string.indo));
            initView();
        } else if (id == R.id.nav_toindo) {
            spManager.saveSPString(SPManager.SP_BAHASA, getString(R.string.eng));
            initView();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
