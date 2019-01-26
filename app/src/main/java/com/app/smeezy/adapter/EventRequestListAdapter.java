package com.app.smeezy.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.smeezy.R;
import com.app.smeezy.activity.EventDetailActivity;
import com.app.smeezy.interfacess.EventRequestListener;
import com.app.smeezy.interfacess.FriendRequestListener;
import com.app.smeezy.responsemodels.Event;
import com.app.smeezy.utills.StaticData;
import com.bumptech.glide.Glide;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Rahul on 03-01-2018.
 */

public class EventRequestListAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private ArrayList<Event> eventList;
    private EventRequestListener eventRequestListener;


    public EventRequestListAdapter(Context context, ArrayList<Event> userList,
                                   EventRequestListener eventRequestListener) {

        this.mContext = context;
        this.eventList = userList;
        this.eventRequestListener = eventRequestListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.event_request_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        if (holder instanceof ViewHolder) {

            ViewHolder viewHolder = (ViewHolder) holder;

            final Event event = eventList.get(position);

            viewHolder.tv_name.setText(event.getTitle());
            viewHolder.tv_date.setText(event.getStartDate());
            viewHolder.tv_location.setText(event.getLocation());

            Glide.with(mContext)
                    .load(event.getImage() + StaticData.THUMB_100)
                    .asBitmap()
                    .into(viewHolder.img_event);

            SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat outputDateFormat = new SimpleDateFormat("MMM dd, yyyy 'at' hh:mm a");

            Date inputDate;

            try {
                inputDate = inputDateFormat.parse(event.getStartDate());
                viewHolder.tv_date.setText(outputDateFormat.format(inputDate));
            }catch (ParseException e){
                e.printStackTrace();
            }

            viewHolder.ll_event_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   /* Intent intent = new Intent(mContext, EventDetailActivity.class);
                    intent.putExtra("event", event);
                    mContext.startActivity(intent);*/
                }
            });

            viewHolder.btn_yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    eventRequestListener.onYes(position, event.getInviteId());
                }
            });

            viewHolder.btn_no.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    eventRequestListener.onNo(position, event.getInviteId());
                }
            });

            viewHolder.btn_maybe.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    eventRequestListener.onMaybe(position, event.getInviteId());
                }
            });

        }

    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public LinearLayout ll_event_item;
        public TextView tv_name, tv_location, tv_date;
        public ImageView img_event;
        public Button btn_yes, btn_no, btn_maybe;

        public ViewHolder(View itemView) {
            super(itemView);

            ll_event_item = (LinearLayout) itemView.findViewById(R.id.ll_event_request_item);
            tv_name = (TextView) itemView.findViewById(R.id.tv_event_request_item_name);
            tv_date = (TextView) itemView.findViewById(R.id.tv_event_request_item_date);
            tv_location = (TextView) itemView.findViewById(R.id.tv_event_request_item_location);
            img_event = (ImageView) itemView.findViewById(R.id.img_event_request_request_item);
            btn_yes = (Button) itemView.findViewById(R.id.btn_event_request_yes);
            btn_no = (Button) itemView.findViewById(R.id.btn_event_request_no);
            btn_maybe = (Button) itemView.findViewById(R.id.btn_event_request_maybe);
        }
    }
}