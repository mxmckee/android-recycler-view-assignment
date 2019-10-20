package edu.ualr.recyclerviewassignment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Date;
import java.util.List;

import edu.ualr.recyclerviewassignment.R;
import edu.ualr.recyclerviewassignment.model.Device;
import edu.ualr.recyclerviewassignment.model.Item;
import edu.ualr.recyclerviewassignment.model.SectionHeader;
import edu.ualr.recyclerviewassignment.utils.Tools;

public class AdapterListBasic extends RecyclerView.Adapter {

    private static final int DEVICE_VIEW = 0;
    private static final int HEADER_VIEW = 1;

    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, Device obj, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }

    private final List<Item> mItems;
    private final Context mContext;

    public AdapterListBasic(Context context, List<Item> items) {
        this.mItems = items;
        this.mContext = context;
    }

    @Override
    public int getItemViewType(int position) {
        return this.mItems.get(position).isSection()? HEADER_VIEW : DEVICE_VIEW;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh = null;
        View itemView = null;

        switch (viewType) {
            case (HEADER_VIEW):
                itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_section, parent, false);
                vh = new SectionHeaderViewHolder(itemView);
                break;
            case (DEVICE_VIEW):
                itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_device, parent, false);
                vh = new DeviceViewHolder(itemView);
                break;
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        Item item = mItems.get(position);
        if (holder instanceof DeviceViewHolder) {
            DeviceViewHolder viewHolder = (DeviceViewHolder) holder;
            Device d = (Device) item;

            viewHolder.name.setText(d.getName());

            if (d.getDeviceStatus().toString().matches(Device.DeviceStatus.Connected.name())) {
                viewHolder.status.setText(R.string.currently_connected);
                Date currDate = new Date();
                d.setLastConnection(currDate);
            }
            else if (d.getLastConnection() == null) {
                viewHolder.status.setText(R.string.never_connected);
            }
            else {
                viewHolder.status.setText(R.string.recently);
            }

            if (d.getDeviceStatus().toString().matches(Device.DeviceStatus.Connected.name())) {
                viewHolder.statusMark.setImageResource(R.drawable.status_mark_connected);
                viewHolder.connectionImage.setImageResource(R.drawable.ic_btn_disconnect);
            }
            else if (d.getDeviceStatus().toString().matches(Device.DeviceStatus.Ready.name())) {
                viewHolder.statusMark.setImageResource(R.drawable.status_mark_ready);
                viewHolder.connectionImage.setImageResource(R.drawable.ic_btn_connect);
            }
            else {
                viewHolder.statusMark.setImageResource(android.R.color.transparent);
                viewHolder.connectionImage.setImageResource(android.R.color.transparent);
            }

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
        else {
            //Instance of SectionHeaderViewHolder
            SectionHeaderViewHolder viewHolder = (SectionHeaderViewHolder) holder;
            SectionHeader section = (SectionHeader) item;
            viewHolder.label.setText(section.getLabel());
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
        public ImageView statusMark;
        public ImageView connectionImage;

        public DeviceViewHolder(@NonNull View v) {
            super(v);
            deviceImage = v.findViewById(R.id.device_image);
            name = v.findViewById(R.id.name);
            status = v.findViewById(R.id.status);
            lyt_parent = v.findViewById(R.id.lyt_parent);
            statusMark = v.findViewById(R.id.status_mark);
            connectionImage = v.findViewById(R.id.connection_image);
            lyt_parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickListener.onItemClick(view, (Device) mItems.get(getLayoutPosition()), getLayoutPosition());
                }
            });
        }
    }

    public class SectionHeaderViewHolder extends RecyclerView.ViewHolder {
        public TextView label;

        public SectionHeaderViewHolder(@NonNull View itemView) {
            super(itemView);
            this.label = itemView.findViewById(R.id.title_section);
        }
    }
}
