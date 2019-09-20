package com.bootcamp.solusiasean;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bootcamp.solusiasean.model.Country;
import com.bootcamp.solusiasean.service.APIClient;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterCountry extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
  Context context;
  List<Country> lstCountry;

  public AdapterCountry(Context context , List<Country> lstCountry ) {
    this.context = context;
    this.lstCountry = lstCountry;
  }

  @NonNull
  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    RecyclerView.ViewHolder vh;
    View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_country, parent, false);
    vh = new ContohViewHolder(v);
    return vh;
  }

  @Override
  public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
    if (holder instanceof ContohViewHolder) {
      ContohViewHolder view = (ContohViewHolder) holder;
      final Country country = lstCountry.get(position);

      Picasso.get().load(country.getFlag()).into(view.ivFlag);
      view.txtName.setText(country.getName());
    }
  }

  @Override
  public int getItemCount() {
    return lstCountry.size();
  }

  public class ContohViewHolder extends RecyclerView.ViewHolder {
    ImageView ivFlag;
    TextView txtName;

    public ContohViewHolder(@NonNull View itemView) {
      super(itemView);
      ivFlag = itemView.findViewById(R.id.ivFlag);
      txtName = itemView.findViewById(R.id.txtName);
    }
  }
}
