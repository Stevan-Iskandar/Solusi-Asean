package com.bootcamp.solusiasean;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import com.bootcamp.solusiasean.model.Country;
import com.bootcamp.solusiasean.service.APIClient;
import com.bootcamp.solusiasean.service.APIInterfacesRest;

import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
  RecyclerView rvCountry;
  List<Country> listCountry;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    rvCountry = findViewById(R.id.rvCountry);
    callCountryAPI();
  }

  APIInterfacesRest apiInterface;
  ProgressDialog progressDialog;
  public void callCountryAPI() {
    apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
    progressDialog = new ProgressDialog(MainActivity.this);
    progressDialog.setTitle("Loading");
    progressDialog.show();
    Call<List<Country>> mulaiRequest = apiInterface.getAllCountry();
    mulaiRequest.enqueue(new Callback<List<Country>>() {
      @Override
      public void onResponse(Call<List<Country>> call, Response<List<Country>> response) {
        progressDialog.dismiss();
        listCountry = response.body();
        if (listCountry != null) {
          AdapterCountry toadapter = new AdapterCountry (MainActivity.this, listCountry);
          LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false);
          rvCountry.setLayoutManager(linearLayoutManager);

          rvCountry.setAdapter(toadapter);
        } else {
          try {
            JSONObject jObjError = new JSONObject(response.errorBody().string());
            Toast.makeText(MainActivity.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
          } catch (Exception e) {
            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
          }
        }
      }

      @Override
      public void onFailure(Call<List<Country>> call, Throwable t) {
        progressDialog.dismiss();
        Toast.makeText(getApplicationContext(), "Maaf koneksi bermasalah", Toast.LENGTH_LONG).show();
        call.cancel();
      }
    });
  }
}
