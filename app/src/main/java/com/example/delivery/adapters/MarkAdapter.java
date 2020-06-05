package com.example.delivery.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.delivery.R;
import com.example.delivery.interfaces.AdapterClickListener;
import com.example.delivery.templates.Mark;
import java.util.ArrayList;
public class MarkAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    final String TAG = "MarkAdapter";
    final int FINISHED = 0;
    final int CURRENT = 1;
    final int NEXT = 2;

    private AdapterClickListener adapterClickListener;
    private ArrayList<Mark> items;
    private Context context;

    public MarkAdapter(ArrayList<Mark> items, Context context) {
        this.items = items;
        this.context = context;
    }

    public void onOpenGoogleMapListener(AdapterClickListener adapterClickListener) {
        this.adapterClickListener = adapterClickListener;
    }

    //############################################################################### GET VIEW TYPE
    @Override
    public int getItemViewType(int position) {
        switch (items.get(position).getType()) {

            case 0:
                return 0;
            case 1:
                return 1;
            case 2:
                return 2;
            default:
                return -1;
        }
    }

    //############################################################################################### CREATE HOLDER
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;

        switch (viewType) {
            case FINISHED:
                itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.finished_mark_item_layout, parent, false);
                ViewHolderFinished holderFinished = new ViewHolderFinished(itemView);
                return holderFinished;
            case CURRENT:
                itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.current_mark_layout, parent, false);
                ViewHolderCurrent holderCurrent = new ViewHolderCurrent(itemView);
                return holderCurrent;
            default:
                itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.next_mark_layout, parent, false);
                ViewHolderNext holderNext = new ViewHolderNext(itemView);
                return holderNext;
        }
    }

    //###################################################################################   ON BIND
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        Mark mark = items.get(position);

        switch (mark.getType()) {

            case FINISHED:
                ((ViewHolderFinished) holder).initFinished(mark);
                break;
            case CURRENT:
                ((ViewHolderCurrent) holder).initCurrent(mark);
                Log.d(TAG, "#############################  onBind CURRENT func");
                break;
            case NEXT:
                ((ViewHolderNext) holder).initNext(mark);
                break;
        }
    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    //#################################################################################   FINISHED HOLDER CLASS
    public class ViewHolderFinished extends RecyclerView.ViewHolder {

        private TextView numbers;
        private TextView name;
        private TextView address;

        public ViewHolderFinished(@NonNull View itemView) {
            super(itemView);

            numbers = itemView.findViewById(R.id.finished_mark_string_id);
            name = itemView.findViewById(R.id.finished_mark_name_id);
            address = itemView.findViewById(R.id.finished_mark_address_id);
        }

        public void initFinished(Mark mark) {

            name.setText(mark.getName());
            address.setText(mark.getAddress());
            numbers.setText(mark.getNumbers());

        }
    }


//######################################################################################   CURRENT HOLDER CLASS

    public class ViewHolderCurrent extends RecyclerView.ViewHolder {

        private TextView numbers;
        private TextView name;
        private TextView address;
        private TextView index;
        private TextView timeToDeliver;
        private TextView timeInterval;
        private View navBtn;
        private View finishBtn;

        public ViewHolderCurrent(@NonNull View itemView) {
            super(itemView);

            numbers = itemView.findViewById(R.id.selected_mark_string_id);
            name = itemView.findViewById(R.id.selected_mark_name_id);
            address = itemView.findViewById(R.id.selected_mark_address_id);
            index = itemView.findViewById(R.id.selected_marks_list_item_number_id);
            timeToDeliver = itemView.findViewById(R.id.selected_mark_time_to_deliver_id);
            timeInterval = itemView.findViewById(R.id.selected_mark_time_interval_id);
            navBtn = itemView.findViewById(R.id.selected_mark_nav_sign_id);
            finishBtn = itemView.findViewById(R.id.selected_mark_check_sign_id);

            navBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    adapterClickListener.onOpenMapClicked(getAdapterPosition());
                }
            });
            finishBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    adapterClickListener.onFinishClicked(getAdapterPosition());
//                    showFinishAlertDialog(getAdapterPosition());
                }
            });
        }

        public void initCurrent(Mark mark) {
            name.setText(mark.getName());
            address.setText(mark.getAddress());
            numbers.setText(mark.getNumbers());
            index.setText(String.valueOf(getAdapterPosition() + 1));
            timeToDeliver.setText(mark.getTimeToDeliver());
            timeInterval.setText(mark.getTimeInterval());
        }
    }

//########################################################################################   NEXT HOLDER CLASS

    public class ViewHolderNext extends RecyclerView.ViewHolder {

        private TextView numbers;
        private TextView name;
        private TextView address;
        private TextView timeToDeliver;
        private TextView timeInterval;
        private TextView index;

        public ViewHolderNext(@NonNull View itemView) {
            super(itemView);

            numbers = itemView.findViewById(R.id.unselected_mark_string_id);
            name = itemView.findViewById(R.id.unselected_mark_name_id);
            address = itemView.findViewById(R.id.unselected_mark_address_id);
            timeToDeliver = itemView.findViewById(R.id.unselected_mark_time_to_deliver_id);
            timeInterval = itemView.findViewById(R.id.unselected_mark_time_interval_id);
            index = itemView.findViewById(R.id.unselected_marks_list_item_number_id);
        }

        public void initNext(Mark mark) {
            name.setText(mark.getName());
            address.setText(mark.getAddress());
            numbers.setText(mark.getNumbers());
            index.setText(String.valueOf(getAdapterPosition() + 1));
            timeToDeliver.setText(mark.getTimeToDeliver());
            timeInterval.setText(mark.getTimeInterval());
        }

    }
}
