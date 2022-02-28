package com.parkinapp.HelperClasses;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.parkinapp.BookNext;
import com.parkinapp.DummyMainActivity;
import com.parkinapp.Explore;
import com.parkinapp.R;

import java.util.ArrayList;

public class adapterphone extends RecyclerView.Adapter<adapterphone.PhoneViewHold>  {

    ArrayList<phonehelper> phoneLocations;
    final private ListItemClickListener mOnClickListener;

    public adapterphone(ArrayList<phonehelper> phoneLocations, Explore listener) {
        this.phoneLocations = phoneLocations;
        mOnClickListener = listener;
    }

    @NonNull

    @Override
    public PhoneViewHold onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card, parent, false);
        return new PhoneViewHold(view);

    }

    @Override
    public void onBindViewHolder(@NonNull PhoneViewHold holder, int position) {


        phonehelper phonehelper = phoneLocations.get(position);
        holder.image.setImageResource(phonehelper.getImage());
        holder.title.setText(phonehelper.getTitle());
        holder.address.setText(phonehelper.getAddress());
        holder.price.setText(phonehelper.getPrice());
        holder.charge.setText(phonehelper.getCharge());
        holder.relativeLayout.setBackground(phonehelper.getgradient());
    }

    @Override
    public int getItemCount() {
        return phoneLocations.size();

    }

    public interface ListItemClickListener {
        void onphoneListClick(int clickedItemIndex);
    }

    public class PhoneViewHold extends RecyclerView.ViewHolder implements View.OnClickListener {


        ImageView image;
        TextView title, address, price, charge;
        RelativeLayout relativeLayout;
        Button book;

        private final Context context;

        public PhoneViewHold(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            itemView.setOnClickListener(this);
            //hooks

            image = itemView.findViewById(R.id.phone_image);
            title = itemView.findViewById(R.id.phone_title);
            address = itemView.findViewById(R.id.phone_address);
            price = itemView.findViewById(R.id.price);
            charge = itemView.findViewById(R.id.chargaval);
            relativeLayout = itemView.findViewById(R.id.background_color);

            itemView.findViewById(R.id.book).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    TextView textView = itemView.findViewById(R.id.chargaval);
                    String str = title.getText().toString();
                    String str2 = address.getText().toString();
                    String str3 = price.getText().toString();
                    String str4 = charge.getText().toString();

                    if(str4 == "Charging Available"){
                        Intent book = new Intent(context , DummyMainActivity.class);
                        book.putExtra("title", str);
                        book.putExtra("address", str2);
                        book.putExtra("price", str3);
                        book.putExtra("charge", str4);
                        context.startActivity(book);
                    }else{
                        Intent book = new Intent(context , BookNext.class);
                        book.putExtra("title", str);
                        book.putExtra("address", str2);
                        book.putExtra("price", str3);
                        book.putExtra("charge", str4);
                        context.startActivity(book);
                    }

                }
            });

        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onphoneListClick(clickedPosition);
        }

    }

}
