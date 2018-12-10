package com.example.freddy.foodrestaurant;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.freddy.foodrestaurant.Collections.ItemRestaurant;
import com.example.freddy.foodrestaurant.Collections.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class ListaRestaurantesActivity extends AppCompatActivity {

    List<ItemRestaurant> lstBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_restaurantes);


        lstBook = new ArrayList<>();
        lstBook.add(new ItemRestaurant("el fogon","category book","Description book",R.drawable.imagen1));
        lstBook.add(new ItemRestaurant("la casona","category book","Description book",R.drawable.imagen2));
        lstBook.add(new ItemRestaurant("pollo rico","category book","Description book",R.drawable.imagen3));
        lstBook.add(new ItemRestaurant("vegetarian","category book","Description book",R.drawable.imagen4));
        lstBook.add(new ItemRestaurant("fastfood","category book","Description book",R.drawable.imagen5));
        lstBook.add(new ItemRestaurant("alitas","category book","Description book",R.drawable.imagen6));
        lstBook.add(new ItemRestaurant("el fogon","category book","Description book",R.drawable.imagen1));
        lstBook.add(new ItemRestaurant("la casona","category book","Description book",R.drawable.imagen2));
        lstBook.add(new ItemRestaurant("pollo rico","category book","Description book",R.drawable.imagen3));
        lstBook.add(new ItemRestaurant("vegetarian","category book","Description book",R.drawable.imagen4));
        lstBook.add(new ItemRestaurant("fastfood","category book","Description book",R.drawable.imagen5));
        lstBook.add(new ItemRestaurant("alitas","category book","Description book",R.drawable.imagen6));

        RecyclerView myrv = (RecyclerView) findViewById(R.id.reclyclerview);
        RecyclerViewAdapter myadapter = new RecyclerViewAdapter(this,lstBook);
        myrv.setLayoutManager(new GridLayoutManager(this,2));
        myrv.setAdapter(myadapter);
    }
}
