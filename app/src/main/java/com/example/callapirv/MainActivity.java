package com.example.callapirv;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    RecyclerView rvUser;
    UserAdapter userAdapter;
    List<DataUser> listUser;
    List<User.data> listUser1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvUser = findViewById(R.id.rvUser);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvUser.setLayoutManager(linearLayoutManager);

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        rvUser.addItemDecoration(itemDecoration);

        listUser = new ArrayList<>();
        getListUser();

    }

    private void getListUser() {
        ApiService.API_SERVICE.getUser().enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Toast.makeText(MainActivity.this, "Call API Succses", Toast.LENGTH_SHORT).show();
                listUser1 = response.body().getData();
                for (User.data data : listUser1) {
                    Log.e("email", data.getEmail());
                    listUser.add(new DataUser(data.getEmail(), data.getFirst_name(), data.getLast_name(), data.getAvatar()));
                    userAdapter = new UserAdapter(listUser, MainActivity.this);
                    rvUser.setAdapter(userAdapter);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Call API Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}