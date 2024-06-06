package com.example.car_parking;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardViewHolder> {
    String date,fromtime,totime,slot,location,email,parking,cityName;
    int price;

    private List<CardItem> cardItems;

    public CardAdapter(List<CardItem> cardItems, String email, String location,String parking, String cityName, String date, String fromtime, String totime, String slot, int price) {
        this.cardItems = cardItems;
        this.parking= parking;
        this.cityName=cityName;
        this.date=date;
        this.fromtime=fromtime;
        this.totime=totime;
        this.slot=slot;
        this.location=location;
        this.email=email;
        this.price=price;
    }

    public static class CardViewHolder extends RecyclerView.ViewHolder {
        private TextView bn,cnn,n,e;

        public CardViewHolder(View itemView) {
            super(itemView);
            bn = itemView.findViewById(R.id.t1);
            cnn=itemView.findViewById(R.id.cn);
            n=itemView.findViewById(R.id.name);
            e=itemView.findViewById(R.id.ex);
        }
    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_item, parent, false);
        return new CardViewHolder(view);
    }

    @SuppressLint("LongLogTag")
    @Override
    public void onBindViewHolder(CardViewHolder holder, int position) {
        CardItem cardItem = cardItems.get(position);
        holder.n.setText(cardItem.getName());
        holder.cnn.setText(cardItem.cn);
        holder.bn.setText(cardItem.bname);
        holder.e.setText(cardItem.ex);
        // Set a click listener for the card view
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(), Otp.class);

                // Pass data to the OTP activity
                intent.putExtra("SELECTED_LOCATION", location);
                intent.putExtra("ParkingName",parking);
                intent.putExtra("cityName", cityName);
                intent.putExtra("email",email);
                intent.putExtra("date", date);
                intent.putExtra("fromtime", fromtime);
                intent.putExtra("totime", totime);
                intent.putExtra("slot", slot);
                intent.putExtra("price", price); // Pass the phone number

                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return cardItems.size();
    }
}

