package com.example.roompractise;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private MovieAdapter movieAdapter;
    ArrayList<MovieFav> moviesArrayList=new ArrayList<>();
    private RecyclerView rv;
    private AppDatabase appDatabase;
    EditText editTexttitle, editTextposter, editTexttitle2, editTextposter2,editTexttitle3;
    Button button,button2,button3,button4;
    TextView textView,textView2;
    boolean flag=false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTexttitle=findViewById(R.id.editTexttitle);
        editTextposter=findViewById(R.id.editTextposter);
        button=findViewById(R.id.button);
        rv=findViewById(R.id.rv);

        editTexttitle2=findViewById(R.id.editTexttitle2);
        editTextposter2=findViewById(R.id.editTextposter2);
        button2=findViewById(R.id.button2);
        editTexttitle3=findViewById(R.id.editTexttitle3);
        button3=findViewById(R.id.button3);
        button4=findViewById(R.id.button4);

        rv.setHasFixedSize(true);
        rv.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));
        rv.setAdapter(movieAdapter);


        // MovieFavDAO movieFavDAO = appDatabase.movieFavDAO();

        appDatabase = AppDatabase.getInstance(this);


        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                MovieFav fav=new MovieFav();
                fav.setTitle(editTexttitle.getText().toString());
                fav.setPoster_path(editTextposter.getText().toString());

                if(!editTexttitle.getText().toString().isEmpty() && !editTextposter.getText().toString().isEmpty())
                {
                    appDatabase.movieFavDAO().insertFav(fav);
                    List<MovieFav>movies = appDatabase.movieFavDAO().getAllMovieFavs();
                    moviesArrayList.addAll(movies);

                    Log.e("arraylist size",String.valueOf(moviesArrayList.size()));

                    movieAdapter=new MovieAdapter(MainActivity.this,moviesArrayList);
                }


            }
        });

        button2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                MovieWatchlist movieWatchlist=new MovieWatchlist();
                movieWatchlist.setTitle(editTexttitle2.getText().toString());
                movieWatchlist.setPoster_path(editTextposter2.getText().toString());

                appDatabase.movieFavDAO().insertWatchlist(movieWatchlist);

                List<MovieWatchlist>movies = appDatabase.movieFavDAO().getAllMovieWatchlist();
                ArrayList<MovieWatchlist> movieWatchArrayList=new ArrayList<>();

                movieWatchArrayList.addAll(movies);

                Log.e("arraylist size",String.valueOf(movieWatchArrayList.size()));

                for (int i=0;i<movieWatchArrayList.size();i++)
                {
                    Log.e((i+1)+"title",movieWatchArrayList.get(i).getTitle());
                    Log.e((i+1)+"poster",movieWatchArrayList.get(i).getPoster_path());

                    System.out.println(i+"title-"+movieWatchArrayList.get(i).getTitle());
                    System.out.println(i+"poster-"+movieWatchArrayList.get(i).getPoster_path());
                }

            }
        });

        button3.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                appDatabase.movieFavDAO().deleteFav(new MovieFav(editTextposter.getText().toString()));

                if(!editTexttitle3.getText().toString().isEmpty())
                {
                   // appDatabase.movieFavDAO().deleteFav(new MovieFav(Integer.parseInt(editTexttitle3.getText().toString())));

                }
            }
        });

        button4.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (flag)
                {
                    flag=false;
                    appDatabase.movieFavDAO().deleteFav(new MovieFav(editTexttitle.getText().toString(),editTextposter.getText().toString()));
                    rv.setHasFixedSize(true);
                    movieAdapter=new MovieAdapter(MainActivity.this,moviesArrayList);
                    rv.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));
                    rv.setAdapter(movieAdapter);
                }
                else
                {
                    flag=true;

                    MovieFav fav=new MovieFav();
                    fav.setTitle(editTexttitle.getText().toString());
                    fav.setPoster_path(editTextposter.getText().toString());

                    if(!editTexttitle.getText().toString().isEmpty() && !editTextposter.getText().toString().isEmpty())
                    {
                        appDatabase.movieFavDAO().insertFav(fav);
                        List<MovieFav>movies = appDatabase.movieFavDAO().getAllMovieFavs();
                        moviesArrayList.addAll(movies);

                        Log.e("arraylist size",String.valueOf(moviesArrayList.size()));

                        rv.setHasFixedSize(true);
                        movieAdapter=new MovieAdapter(MainActivity.this,moviesArrayList);
                        rv.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));
                        rv.setAdapter(movieAdapter);
                    }
                }
            }
        });




    }
}