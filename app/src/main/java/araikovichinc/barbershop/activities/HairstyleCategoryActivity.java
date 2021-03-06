package araikovichinc.barbershop.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

import javax.inject.Inject;

import araikovichinc.barbershop.R;
import araikovichinc.barbershop.adapters.HairstyleCategoryRecyclerAdapter;
import araikovichinc.barbershop.callbacks.CategoryOnClickListener;
import araikovichinc.barbershop.mvp.views.HairstyleCategoryActivityView;
import araikovichinc.barbershop.pojo.HairstyleCategoryCard;
import araikovichinc.barbershop.presenters.HairstyleCategoryActivityPresenter;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Tigran on 13.02.2018.
 */

public class HairstyleCategoryActivity extends MvpAppCompatActivity implements HairstyleCategoryActivityView {

    @InjectPresenter
    HairstyleCategoryActivityPresenter presenter;

    Toolbar toolbar;
    RecyclerView recyclerView;
    ProgressBar progressBar;
    HairstyleCategoryRecyclerAdapter adapter;
    CircleImageView refresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hairstyle_category);
        initView();
    }

    private void initView(){
        toolbar = (Toolbar)findViewById(R.id.hairstyle_categories_toolbar);
        recyclerView = (RecyclerView)findViewById(R.id.hairstyles_category_recycler_view);
        progressBar = (ProgressBar)findViewById(R.id.hairstyle_categories_progressbar);
        refresh = (CircleImageView)findViewById(R.id.hairstyle_categories_refresh);

        //set up toolbar;
        final Intent intent = getIntent();
        String s = intent.getStringExtra("title");
        Log.d("MyLogs", s);
        presenter.changetitle(intent.getStringExtra("title"));
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_back));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.setCards(intent.getIntExtra("genderId", 0));
            }
        });

        //set up recycler view
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        if(adapter == null){
            adapter = new HairstyleCategoryRecyclerAdapter(this);
        }else {
            adapter.setContext(this);
        }
        recyclerView.setAdapter(adapter);

        adapter.setOnClickListener(new CategoryOnClickListener() {
            @Override
            public void onClick(int cardId, String title) {
                presenter.next(cardId, title);
            }
        });


        //load data
        if (!adapter.isLoaded())
            presenter.setCards(intent.getIntExtra("genderId", 0));
    }

    @Override
    public void setProgressBar(int visibility) {
        progressBar.setVisibility(visibility);
    }

    @Override
    public void setAdapter(ArrayList<HairstyleCategoryCard> cards) {
        adapter.setCards(cards);
    }

    @Override
    public void setTitle(String title) {
        toolbar.setTitle(title);
    }

    @Override
    public void showToast(String toast) {
        Toast.makeText(this, toast, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showRefresh(int visibility) {
        refresh.setVisibility(visibility);
    }

    @Override
    public void nextActivity(int hairstyleId, String hairstyleTitle) {
        Intent intent = new Intent(HairstyleCategoryActivity.this, HairstyleDetailActivity.class);
        intent.putExtra("hairstyleId", hairstyleId);
        intent.putExtra("hairstyleTitle", hairstyleTitle);
        startActivity(intent);
    }

}
