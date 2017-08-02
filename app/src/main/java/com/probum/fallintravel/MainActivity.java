package com.probum.fallintravel;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.text.style.TypefaceSpan;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.tsengvn.typekit.TypekitContextWrapper;

public class MainActivity extends AppCompatActivity {

    ActionBarDrawerToggle drawerToggle;
    NavigationView navi;
    TabLayout tabLayout;
    ViewPager viewPager;
    PageAdapter pageAdapter;
    FragmentManager fragmentManager;
    SearchView searchView;
    Typeface typeface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DrawerLayout drawerLayout=(DrawerLayout)findViewById(R.id.layout_drawer);
        navi=(NavigationView)findViewById(R.id.navi);
        tabLayout=(TabLayout)findViewById(R.id.layout_tab);
        viewPager=(ViewPager)findViewById(R.id.pager);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        typeface = Typeface.createFromAsset(getAssets(),"ssanaiL.ttf");

        SpannableString spannableString=new SpannableString("여행에 빠지다");
        spannableString.setSpan(new CustomTypefaceSpan("", typeface),0,spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        ActionBar actionBar= getSupportActionBar();
        actionBar.setTitle(spannableString);

        drawerToggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.action_settings,R.string.action_settings);

        drawerToggle.syncState();
        drawerLayout.addDrawerListener(drawerToggle);

        fragmentManager=getSupportFragmentManager();
        pageAdapter =new PageAdapter(fragmentManager);

        pageAdapter.sendassets(getAssets());

        viewPager.setAdapter(pageAdapter);

        tabLayout.setupWithViewPager(viewPager,true);

        if (Build.VERSION.SDK_INT>=21){ //버전 21 이상은 위에 상태바 색 변경경
           getWindow().setStatusBarColor(0xff55ccc0);
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        final MenuItem item=menu.findItem(R.id.menu_search);

        searchView=(SearchView)item.getActionView();
//        searchView.setQueryHint("");
        searchView.setInputType(InputType.TYPE_CLASS_TEXT);
        searchView.setIconifiedByDefault(true);




        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                Toast.makeText(MainActivity.this, query, Toast.LENGTH_SHORT).show();
//                searchView.setQuery("",true);
                searchView.setIconified(true);
                item.collapseActionView();

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        return super.onOptionsItemSelected(item);
    }



}
