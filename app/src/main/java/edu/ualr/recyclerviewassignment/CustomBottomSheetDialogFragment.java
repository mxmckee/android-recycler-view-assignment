package edu.ualr.recyclerviewassignment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.button.MaterialButton;

import edu.ualr.recyclerviewassignment.model.Device;

public class CustomBottomSheetDialogFragment extends BottomSheetDialogFragment implements View.OnClickListener {

    String name;
    TextView deviceName;
    private static final String TAG = CustomBottomSheetDialogFragment.class.getSimpleName();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  getLayoutInflater().inflate(R.layout.device_detail_fragment, container, false);

        final ImageView deviceImage = (ImageView) view.findViewById(R.id.image_icon);
        ImageView deviceStatusImage = (ImageView) view.findViewById(R.id.detail_status_mark);
        deviceName = (TextView) view.findViewById(R.id.detail_device_name_edittext);
        TextView deviceStatus = (TextView) view.findViewById(R.id.detail_device_status);
        TextView deviceLastConnection = (TextView) view.findViewById(R.id.last_time_connection_textview);

        String deviceType = this.getArguments().getString("DEVICE_TYPE").toString();
        name = this.getArguments().getString("DEVICE_NAME").toString().toUpperCase();
        String status = this.getArguments().getString("DEVICE_STATUS").toString();
        String lastConnection = this.getArguments().getString("DEVICE_LAST_CONNECTION").toString();

        deviceName.setText(name);
        deviceStatus.setText(status);
        deviceLastConnection.setText(lastConnection);

        if (status.matches(Device.DeviceStatus.Linked.toString())) {
            deviceStatusImage.setImageResource(android.R.color.transparent);
        } else if (status.matches(Device.DeviceStatus.Connected.toString())) {
            deviceStatusImage.setImageResource(R.drawable.status_mark_connected);
        } else {
            deviceStatusImage.setImageResource(R.drawable.status_mark_ready);
        }

        Spinner spinner = view.findViewById(R.id.device_type_spinner);
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(getContext(), R.array.device_array, R.layout.spinner_list_item);
        spinnerAdapter.setDropDownViewResource(R.layout.spinner_selector_item);
        spinner.setAdapter(spinnerAdapter);
        int spinnerSelection;

        if (deviceType.contains(Device.DeviceType.Unknown.toString())) {
            deviceImage.setImageResource(R.drawable.ic_unknown_device);
            spinnerSelection = 0;
        } else if (deviceType.contains(Device.DeviceType.Desktop.toString())) {
            deviceImage.setImageResource(R.drawable.ic_pc);
            spinnerSelection = 1;
        } else if (deviceType.contains(Device.DeviceType.Laptop.toString())) {
            deviceImage.setImageResource(R.drawable.ic_laptop);
            spinnerSelection = 2;
        } else if (deviceType.contains(Device.DeviceType.Tablet.toString())) {
            deviceImage.setImageResource(R.drawable.ic_tablet_android);
            spinnerSelection = 4;
        } else if (deviceType.contains(Device.DeviceType.Smartphone.toString())) {
            deviceImage.setImageResource(R.drawable.ic_phone_android);
            spinnerSelection = 3;
        } else if (deviceType.contains(Device.DeviceType.SmartTV.toString())) {
            deviceImage.setImageResource(R.drawable.ic_tv);
            spinnerSelection = 5;
        } else {
            deviceImage.setImageResource(R.drawable.ic_gameconsole);
            spinnerSelection = 6;
        }

        spinner.setSelection(spinnerSelection);

        MaterialButton saveButton = view.findViewById(R.id.save_btn);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CustomBottomSheetDialogFragment.this.dismiss();
            }
        });

        return view;
    }

    @Override
    public void onClick(View view) {
        this.dismiss();
    }
}
