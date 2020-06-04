package com.example.delivery.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;
import com.example.delivery.MainActivity;
import com.example.delivery.R;
import com.example.delivery.adapters.MarkAdapter;
import com.example.delivery.databases.DatabaseHelper;
import com.example.delivery.interfaces.AdapterClickListener;
import com.example.delivery.interfaces.ActiveMarksCount;
import com.example.delivery.templates.Mark;
import java.util.ArrayList;

public class StopsFragment extends Fragment {

    private RecyclerView recyclerView;
    private MarkAdapter adapter;
    private LinearLayoutManager layoutManager;
    private ArrayList<Mark> marks;
    private DatabaseHelper dbHelper;
    private int currentMarkPosition;
    private RecyclerView.SmoothScroller smoothScroller;
    private ActiveMarksCount activeMarksCount;

    private final String TAG = "StopFragment";

    @Override
    public void onAttach(Context context) {
        if (context instanceof MainActivity){
            activeMarksCount = (MainActivity)context;
        }
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.stops_fragment_layout,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        recyclerView = view.findViewById(R.id.recycle_view_stops_id);
        layoutManager = new LinearLayoutManager(view.getContext(), RecyclerView.VERTICAL, false);

        dbHelper = new DatabaseHelper(getContext());
        if (dbHelper.checkTableEmpty()){
            Log.d(TAG, "--------------MARK TABLE IS NOT EMPTY");
        }else{
            Log.d(TAG, "--------------MARK TABLE IS EMPTY");
            dbHelper.addMark(new Mark("Moskovyan 29/4", "Yerevan", "25486", "09:45", "08:00 - 10:00", "40.187905, 44.515924",2));
            dbHelper.addMark(new Mark("Buzand 87", "Yerevan", "9451267", "10:00", "10:00 - 12:00", "40.1831189,44.5079256",2));
            dbHelper.addMark(new Mark("Arshakunyats 7", "Yerevan", "136798", "10:15", "10:00 - 12:00", "40.1710826,44.5091975",2));
            dbHelper.addMark(new Mark("Mikoyan 17/2", "Yerevan", "634972", "10:35", "10:00 - 12:00", "40.1824156,44.5652008",2));
            dbHelper.addMark(new Mark("Bagrevand 5", "Yerevan", "7197341", "10:50", "10:00 - 12:00", "40.203606,44.5668193",2));
            dbHelper.addMark(new Mark("Komitas 60/2", "Yerevan", "312579", "11:05", "10:00 - 12:00", "40.2055916,44.5256226",2));
            dbHelper.addMark(new Mark("Kievyan 19", "Yerevan", "9431827", "11:20", "10:00 - 12:00", "40.1978889,44.5033925",2));
            dbHelper.addMark(new Mark("Charents 94", "Yerevan", "12457", "11:38", "10:00 - 12:00", "40.1675637,44.5242748",2));
            dbHelper.addMark(new Mark("Nar-Dos 39", "Yerevan", "9763157", "11:47", "10:00 - 12:00", "40.1653016,44.5157068",2));
            dbHelper.addMark(new Mark("Dzorap 19/4", "Yerevan", "58497", "12:07", "12:00 - 14:00", "40.1833606,44.5008411",2));
            dbHelper.addMark(new Mark("Abovyan 34/3", "Yerevan", "31679579", "12:19", "12:00 - 14:00", "40.1809415,44.5142378",2));
            dbHelper.addMark(new Mark("Hr. Kochar 44/54", "Yerevan", "259781", "12:25", "12:00 - 14:00", "40.2037068,44.5109667",2));
            dbHelper.addMark(new Mark("Nazarbekyan 25/5", "Yerevan", "3197957", "12:40", "12:00 - 14:00", "40.2139139,44.4850603",2));
            dbHelper.addMark(new Mark("Arzumanyan 22", "Yerevan", "22694375", "12:55", "12:00 - 14:00", "40.1942352,44.4692082",2));
            dbHelper.addMark(new Mark("Koryun 2", "Yerevan", "6317887", "13:15", "12:00 - 14:00", "40.1899664,44.5222157",2));
            dbHelper.addMark(new Mark("Kajaznuni 9", "Yerevan", "16913467", "13:29", "12:00 - 14:00", "40.1700883,44.5207043",2));
            dbHelper.addMark(new Mark("Isahakyan 1/19", "Yerevan", "366754", "13:41", "12:00 - 14:00", "40.2156852,44.5717595",2));
            dbHelper.addMark(new Mark("Pushkin 56a", "Yerevan", "9673164", "13:56", "12:00 - 14:00", "40.1859556,44.5091863",2));
            dbHelper.addMark(new Mark("Tumanyan 40", "Yerevan", "3129788", "14:14", "14:00 - 16:00", "40.1863006,44.511348",2));
            dbHelper.addMark(new Mark("Teryan 44", "Yerevan", "16549", "14:36", "14:00 - 16:00", "40.1843272,44.5151174",2));
//            dbHelper.currentMarkIndex(0,0);
        }

        marks = new ArrayList<Mark>(dbHelper.readMarksData());
        if (marks.size() == dbHelper.getCurrentMark()+1){
            activeMarksCount.onItemsCount(0);
        }else{
            activeMarksCount.onItemsCount(marks.size()-dbHelper.getCurrentMark());
        }

        adapter = new MarkAdapter(marks, getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        smoothScroller = new LinearSmoothScroller(getContext()) {
            @Override protected int getVerticalSnapPreference() {
                return LinearSmoothScroller.SNAP_TO_START;
            }
        };

        layoutManager.scrollToPositionWithOffset(dbHelper.getCurrentMark(),0);
//###################################################################################################################### INTERFACE INIT
        adapter.onOpenGoogleMapListener(new AdapterClickListener() {
            @Override
            public void onOpenMapClicked(int position) {
                currentMarkPosition = position;
                showNavigationAlertDialog();
            }

            @Override
            public void onFinishClicked(int position) {
                showFinishAlertDialog(position);
                Log.d(TAG,"----------------------------------------------------------------------------------------------   " + dbHelper.getCurrentMark());
            }
        });
    }

    //################################################################################################################    ALERT DIALOG NAVIGATION
    public void showNavigationAlertDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Navigation");
        builder.setMessage("Open in Google Maps?");
        //--------------------------------------------------------------------------------YES
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                final String googleKey = "http://maps.google.com/maps?&daddr=";
                Uri uri = Uri.parse(googleKey + marks.get(currentMarkPosition).getDestination());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                intent.setPackage("com.google.android.apps.maps");

                try{
                    if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                        startActivity(intent);
                    }
                }catch (NullPointerException e){
                    Log.e(TAG, "onClick: NullPointerException: Couldn't open map." + e.getMessage() );
                }


            }
        });
        //---------------------------------------------------------------------------------NO
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();

    }


    //################################################################################################################    ALERT DIALOG FINISH


    public void showFinishAlertDialog(final int position) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(marks.get(position).getName());
        builder.setMessage("Finish Current Task?");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                currentMarkPosition = position;
                marks.get(position).setType(0);
                if (position == marks.size()-1){
                    dbHelper.updateType(position + 1, 0);
                    activeMarksCount.onItemsCount(0);
                    adapter.notifyItemChanged(position+1);

                }else{
                    marks.get(position + 1).setType(1);
                    dbHelper.updateType(position + 1, 0);
                    dbHelper.updateType(position + 2, 1);
                    dbHelper.currentMarkIndex(1,position+1);
                    adapter.notifyItemChanged(position+1);
                    activeMarksCount.onItemsCount(marks.size()-dbHelper.getCurrentMark());
                }

                smoothScroller.setTargetPosition(dbHelper.getCurrentMark());
                layoutManager.startSmoothScroll(smoothScroller);
                adapter.notifyItemChanged(position);

            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();

    }



}
