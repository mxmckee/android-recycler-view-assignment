package edu.ualr.recyclerviewassignment;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.ualr.recyclerviewassignment.adapter.AdapterListBasic;
import edu.ualr.recyclerviewassignment.data.DataGenerator;
import edu.ualr.recyclerviewassignment.model.Device;
import edu.ualr.recyclerviewassignment.model.Item;
import edu.ualr.recyclerviewassignment.model.SectionHeader;

public class MainActivity extends AppCompatActivity {

    private AdapterListBasic mAdapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initRecyclerView();
    }

    private void initRecyclerView(){
        // TODO. Create and initialize the RecyclerView instance here
        List<Item> items = DataGenerator.getDevicesDataset(5);

        for (int i = 0; i < items.size(); i++) {
            if (i == 0) {
                items.add(i, new SectionHeader(Device.DeviceStatus.Connected.name()));
            }
            if (i == 3) {
                items.add(i, new SectionHeader(Device.DeviceStatus.Ready.name()));
            }
            if (i == 6) {
                items.add(i, new SectionHeader(Device.DeviceStatus.Linked.name()));
            }
        }

        mAdapter = new AdapterListBasic(this, items);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setAdapter(mAdapter);

        //RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        //recyclerView.setLayoutManager(layoutManager);
        LinearLayoutManager linearVertical = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearVertical);
    }
}
