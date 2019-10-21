package edu.ualr.recyclerviewassignment;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.ualr.recyclerviewassignment.adapter.AdapterListBasic;
import edu.ualr.recyclerviewassignment.data.DataGenerator;
import edu.ualr.recyclerviewassignment.model.Device;
import edu.ualr.recyclerviewassignment.model.Item;
import edu.ualr.recyclerviewassignment.model.SectionHeader;

public class MainActivity extends AppCompatActivity {

    private AdapterListBasic mAdapter;
    private RecyclerView recyclerView;

    List<Item> items = DataGenerator.getDevicesDataset(5);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initRecyclerView();
    }

    private void initRecyclerView(){
        // TODO. Create and initialize the RecyclerView instance here

        //Store items in a temporary list, tempList
        List<Item> tempList = new ArrayList<>();
        tempList.addAll(items);

        //Remove items from items
        for (int i = items.size() - 1; i >= 0; i--) {
            items.remove(i);
        }

        //Repopulates items by arranging items according to connection status
        items = sortList(items, tempList);

        mAdapter = new AdapterListBasic(this, items);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setAdapter(mAdapter);

        LinearLayoutManager linearVertical = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearVertical);

        mAdapter.setOnItemClickListener(new AdapterListBasic.OnItemClickListener() {
            @Override
            public void onItemClick(View view, Device obj, int position) {
                //Update last connection
                updateLastConnection(obj);

                //Reinitialize RecyclerView with updated information
                initRecyclerView();
            }
        });
    }

    private List<Item> sortList(List<Item> items, List<Item> tempList) {
        //Search for items with a connected status
        for (int i = 0; i < tempList.size(); i++) {
            if (!tempList.get(i).isSection()) {
                if (((Device) tempList.get(i)).getDeviceStatus().toString().matches(Device.DeviceStatus.Connected.name())) {
                    items.add(tempList.get(i));
                }
            }
        }
        //Search for items with a ready status
        for (int i = 0; i < tempList.size(); i++) {
            if (!tempList.get(i).isSection()) {
                if (((Device) tempList.get(i)).getDeviceStatus().toString().matches(Device.DeviceStatus.Ready.name())) {
                    items.add(tempList.get(i));
                }
            }
        }
        //Search for items with a linked status
        for (int i = 0; i < tempList.size(); i++) {
            if (!tempList.get(i).isSection()) {
                if (((Device) tempList.get(i)).getDeviceStatus().toString().matches(Device.DeviceStatus.Linked.name())) {
                    items.add(tempList.get(i));
                }
            }
        }

        boolean connected = true;
        boolean ready = true;
        boolean linked = true;

        for (int i = 0; i < items.size(); i++) {
            if (((Device) items.get(i)).getDeviceStatus().toString().matches(Device.DeviceStatus.Connected.name())) {
                if (connected) {
                    items.add(i, new SectionHeader(Device.DeviceStatus.Connected.name()));
                    connected = false;
                }
                continue;
            }

            if (((Device) items.get(i)).getDeviceStatus().toString().matches(Device.DeviceStatus.Ready.name())) {
                if (ready) {
                    items.add(i, new SectionHeader(Device.DeviceStatus.Ready.name()));
                    ready = false;
                }
                continue;
            }

            if (((Device) items.get(i)).getDeviceStatus().toString().matches(Device.DeviceStatus.Linked.name())) {
                if (linked) {
                    items.add(i, new SectionHeader(Device.DeviceStatus.Linked.name()));
                    linked = false;
                }
            }
        }
        return items;
    }

    private void updateLastConnection(Device obj) {
        if (obj.getDeviceStatus().toString().matches(Device.DeviceStatus.Ready.name())) {
            obj.setDeviceStatus(Device.DeviceStatus.Connected);
            Date currDate = new Date();
            obj.setLastConnection(currDate);
        }
        else if (obj.getDeviceStatus().toString().matches(Device.DeviceStatus.Connected.name())) {
            obj.setDeviceStatus(Device.DeviceStatus.Ready);
        }
    }
}