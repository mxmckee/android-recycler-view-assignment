package edu.ualr.recyclerviewassignment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.ualr.recyclerviewassignment.R;
import edu.ualr.recyclerviewassignment.model.Device;
import edu.ualr.recyclerviewassignment.utils.Tools;

public class AdapterListBasic extends RecyclerView.Adapter {

    private final List<Device> mItems;
    private final Context mContext;

    public AdapterListBasic(Context context, List<Device> items) {
        this.mItems = items;
        this.mContext = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.device_layout, parent, false);
        vh = new DeviceViewHolder(itemView);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        DeviceViewHolder viewHolder = (DeviceViewHolder) holder;
        Device d = mItems.get(position);
        viewHolder.name.setText(d.getName());
        viewHolder.status.setText(d.getDeviceStatus().toString());
        if (d.getName().contains(Device.DeviceType.Unknown.toString())) {
            Tools.displayImageRound(mContext, viewHolder.deviceImage, R.drawable.ic_unknown_device);
        }
        else if (d.getName().contains(Device.DeviceType.Desktop.toString())) {
            Tools.displayImageRound(mContext, viewHolder.deviceImage, R.drawable.ic_pc);
        }
        else if (d.getName().contains(Device.DeviceType.Laptop.toString())) {
            Tools.displayImageRound(mContext, viewHolder.deviceImage, R.drawable.ic_laptop);
        }
        else if (d.getName().contains(Device.DeviceType.Tablet.toString())) {
            Tools.displayImageRound(mContext, viewHolder.deviceImage, R.drawable.ic_tablet_android);
        }
        else if (d.getName().contains(Device.DeviceType.Smartphone.toString())) {
            Tools.displayImageRound(mContext, viewHolder.deviceImage, R.drawable.ic_phone_android);
        }
        else if (d.getName().contains(Device.DeviceType.SmartTV.toString())) {
            Tools.displayImageRound(mContext, viewHolder.deviceImage, R.drawable.ic_tv);
        }
        else {
            Tools.displayImageRound(mContext, viewHolder.deviceImage, R.drawable.ic_gameconsole);
        }
    }

    @Override
    public int getItemCount() {
        return this.mItems.size();
    }

    public class DeviceViewHolder extends RecyclerView.ViewHolder {
        public ImageView deviceImage;
        public TextView name;
        public TextView status;
        public View lyt_parent;

        public DeviceViewHolder(View v) {
            super(v);
            deviceImage = v.findViewById(R.id.device_image);
            name = v.findViewById(R.id.name);
            status = v.findViewById(R.id.status);
            lyt_parent = v.findViewById(R.id.lyt_parent);
        }
    }
}
