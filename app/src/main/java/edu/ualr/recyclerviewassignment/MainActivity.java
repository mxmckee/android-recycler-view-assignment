package edu.ualr.recyclerviewassignment;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String FRAGMENT_TAG = "BottomSheetDialog";

    List<Item> items = DataGenerator.getDevicesDataset(5);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initRecyclerView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
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

        recyclerView = (RecyclerView) findViewById(R.id.devices_recycler_view);
        recyclerView.setAdapter(mAdapter);

        LinearLayoutManager linearVertical = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearVertical);

        mAdapter.setOnItemClickListener(new AdapterListBasic.OnItemClickListener() {
            @Override
            public void onItemClick(View view, Device obj, int position) {

                Bundle bundle = new Bundle();

                bundle.putString("DEVICE_TYPE", obj.getDeviceType().name().toString());
                bundle.putString("DEVICE_NAME", obj.getName().toString());
                bundle.putString("DEVICE_STATUS", obj.getDeviceStatus().toString());

                String lastConnection;
                if (obj.getDeviceStatus().toString().matches(Device.DeviceStatus.Connected.name())) {
                    Date currDate = new Date();
                    obj.setLastConnection(currDate);
                    lastConnection = getResources().getString(R.string.currently_connected);
                }
                else if (obj.getLastConnection() == null) {
                    lastConnection = getResources().getString(R.string.never_connected);
                }
                else {
                    lastConnection = getResources().getString(R.string.recently);
                }
                bundle.putString("DEVICE_LAST_CONNECTION", lastConnection);

                CustomBottomSheetDialogFragment dialog = new CustomBottomSheetDialogFragment();
                dialog.show(getSupportFragmentManager(), FRAGMENT_TAG);
                dialog.setArguments(bundle);
            }
        });

        mAdapter.setOnButtonClickListener(new AdapterListBasic.OnButtonClickListener() {
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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.connect_action:
                mAdapter.connectAll();
                initRecyclerView();
                return true;
            case R.id.disconnect_action:
                mAdapter.disconnectAll();
                initRecyclerView();
                return true;
            case R.id.show_linked_action:
                return true;
            default: return super.onOptionsItemSelected(item);
        }
    }
}