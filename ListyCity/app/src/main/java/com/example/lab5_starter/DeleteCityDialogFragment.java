package com.example.lab5_starter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class DeleteCityDialogFragment extends DialogFragment {



    public interface CityDeleteListener {
        public void deleteCity(City city);
    }

    private CityDeleteListener listener;

    public static DeleteCityDialogFragment newInstance(City city){
        Bundle args = new Bundle();
        args.putSerializable("City", city);

        DeleteCityDialogFragment fragment = new DeleteCityDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof CityDeleteListener){
            listener = (CityDeleteListener) context;
        }
        else {
            throw new RuntimeException(context + " must implement CityDeleteListener");
        }
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        View view = getLayoutInflater().inflate(R.layout.fragment_delete_city, null);
        TextView delCity = view.findViewById(R.id.delete_city);
        TextView delProvince = view.findViewById(R.id.delete_province);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        Bundle bundle = getArguments();
        City city;
        if (bundle != null) {
            city = (City) bundle.getSerializable("City");
            assert city != null;
        }
        else {
            city = null;
        }

        assert city != null;
        delCity.setText(city.getName());
        delProvince.setText(city.getProvince());
        //assert city != null;

        return builder
                .setView(view)
                .setTitle("Delete City?")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Delete", (dialog, which) -> {
                    System.out.println("Got Here " + city.getName());
                    assert city != null;
                    listener.deleteCity(city);
                })
                .create();
    }
}
