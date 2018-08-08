package software.msouti.veginfo.Activity;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.GridLayoutAnimationController;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import software.msouti.veginfo.Adapter.VegAdapter;
import software.msouti.veginfo.R;
import software.msouti.veginfo.Utils.Tools;
import software.msouti.veginfo.Utils.VegListType;

public class MainActivity extends AppCompatActivity implements VegAdapter.ListItemClickListener {
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.headerImageView) ImageView header;
    @BindView(R.id.mainRecyclerView) RecyclerView recyclerView;
    @BindView(R.id.adView2) AdView mAdView;
    GridLayoutManager gridLayoutManager;
    ArrayList<VegListType> list;
    VegAdapter adapter;
    ArrayList<VegListType> result;
    @BindView(R.id.search_view)
    SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        list= new ArrayList<>();
        list= Objects.requireNonNull(getIntent().getExtras()).getParcelableArrayList("list");
        toolbar.setTitle(R.string.app_name);
        gridLayoutManager= new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(gridLayoutManager);
        runAnimations(recyclerView);


        //Load AdView with sample ad Unit Id (for test propose )
        AdView adView = new AdView(this);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId(getString(R.string.sample_ad_unit_id));
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        Intent intent = new Intent(this,VegDetailsActivity.class);

        if (result==null){
            intent.putExtra(getString(R.string.intentListKey),list.get(clickedItemIndex));
        }else {
            intent.putExtra(getString(R.string.intentListKey),result.get(clickedItemIndex));

        }
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);


        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        final MenuItem searchItem = menu.findItem(R.id.action_search);
        ComponentName cn = new ComponentName(this, MainActivity.class);

        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(cn));
        searchView.setIconifiedByDefault(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText!=null && !newText.isEmpty()){
                    result = new ArrayList<>();
                    for (int i=0;i<=list.size()-1;i++) {
                        if(list.get(i).getTitle().contains(newText.trim())||list.get(i).getTitle().contains(newText.trim().toUpperCase())){
                            result.add(list.get(i));
                        }
                    }
                    adapter = new VegAdapter(MainActivity.this, result,MainActivity.this);
                    recyclerView.setAdapter(adapter);

                }else {
                    result=null;
                    adapter = new VegAdapter(MainActivity.this, list,MainActivity.this);
                    recyclerView.setAdapter(adapter);
                }
                return true;
            }
        });

        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean queryTextFocused) {
                if (!queryTextFocused) {
                    result=null;
                    MenuItemCompat.collapseActionView(searchItem);
                }
            }
        });

        return true;
    }

    private void runAnimations(RecyclerView rView){
        Context context=rView.getContext();
        LayoutAnimationController controller=
                AnimationUtils.loadLayoutAnimation(context,R.anim.layout_slide_from_bot);

        adapter = new VegAdapter(this, list,MainActivity.this);
        rView.setAdapter(adapter);
        rView.setLayoutAnimation(controller);
        rView.getAdapter().notifyDataSetChanged();
        rView.scheduleLayoutAnimation();


    }

    //Capstone stage2 Final steps
    //TODO: setup open VegDetailsActivity Animations
    //TODO: be sure that every Strings is saved in string.xml
}
