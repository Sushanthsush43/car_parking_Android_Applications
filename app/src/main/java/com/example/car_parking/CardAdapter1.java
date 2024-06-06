package com.example.car_parking;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CardAdapter1 extends RecyclerView.Adapter<CardAdapter1.CardViewHolder> {
    private List<CardItem1> cardItems;

    public CardAdapter1(List<CardItem1> cardItems) {
        this.cardItems = cardItems;
    }

    public static class CardViewHolder extends RecyclerView.ViewHolder {
        private TextView place;
        private TextView fromTimeLabel;
        private TextView fromTime;
        private TextView toTimeLabel;
        private TextView toTime;
        private TextView dateLabel;
        private TextView date;
        private TextView slotNameLabel;
        private TextView slotName;
        private TextView price;

        public CardViewHolder(View itemView) {
            super(itemView);
            place = itemView.findViewById(R.id.place);
            fromTimeLabel = itemView.findViewById(R.id.fromTimeLabel);
            fromTime = itemView.findViewById(R.id.fromTime);
            toTimeLabel = itemView.findViewById(R.id.toTimeLabel);
            toTime = itemView.findViewById(R.id.toTime);
            dateLabel = itemView.findViewById(R.id.dateLabel);
            date = itemView.findViewById(R.id.date);
            slotNameLabel = itemView.findViewById(R.id.slotNameLabel);
            slotName = itemView.findViewById(R.id.slotName);
            price = itemView.findViewById(R.id.price);
        }
    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.slotcard, parent, false);
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CardViewHolder holder, int position) {
        CardItem1 cardItem = cardItems.get(position);

        holder.place.setText(cardItem.place);
        holder.fromTime.setText(cardItem.fromTime);
        holder.toTime.setText(cardItem.toTime);
        holder.date.setText(cardItem.date);
        holder.slotName.setText(cardItem.slotName);
        holder.price.setText(cardItem.price);
    }

    @Override
    public int getItemCount() {
        return cardItems.size();
    }
}

