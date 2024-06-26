package com.pepe.application.ui.Gps;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.pepe.application.R;
import android.widget.PopupWindow;
import android.media.MediaPlayer;

import java.util.Arrays;
import java.util.List;
public class mapap extends Fragment implements OnMapReadyCallback {

    private RadioButton radioSucursal1, radioSucursal2, radioSucursal3;
    private GoogleMap mMap;
    private Marker mMarkerFrom;
    private Marker mMarkerTo;
    private LatLng mFromLatLng;
    private LatLng mToLatLng;
    private TextView tvFrom;
    private TextView tvTo;

    private TextView tvDistance;
    private TextView tvTime;
    private TextView tvPrice;

    private MediaPlayer mediaPlayer;

    private static final int REQUEST_CODE_AUTOCOMPLETE_FROM = 1;
    private static final int REQUEST_CODE_AUTOCOMPLETE_TO = 2;
    private static final String TAG = "mapap";


    private MediaPlayer mediaPlayerButton;
    private MediaPlayer mediaPlayerButton1;
    private MediaPlayer mediaPlayerButton2;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mapap, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvFrom = view.findViewById(R.id.tvFrom);
        tvTo = view.findViewById(R.id.tvTo);

        mediaPlayerButton = MediaPlayer.create(requireContext(), R.raw.sonido0);
        mediaPlayerButton1 = MediaPlayer.create(requireContext(), R.raw.inicio);
        mediaPlayerButton2 = MediaPlayer.create(requireContext(), R.raw.destino);

        tvDistance = view.findViewById(R.id.tvDistance);
        tvTime = view.findViewById(R.id.tvTime);
        tvPrice = view.findViewById(R.id.tvPrice);
        Button btnButton1 = view.findViewById(R.id.button4);
        btnButton1.setOnClickListener(v -> playSound(R.raw.sonido0));

        Button btnFrom = view.findViewById(R.id.btnFrom);
        btnFrom.setOnClickListener(v -> playSound(R.raw.inicio));

        Button btnTo = view.findViewById(R.id.btnTo);
        btnTo.setOnClickListener(v -> playSound(R.raw.destino));

        // Dentro del método onViewCreated después de configurar btnTo
        Button btnTacos = view.findViewById(R.id.btnTacos);
        btnTacos.setOnClickListener(v -> {
            // Muestra la ventana emergente al hacer clic en btnTacos
            setDestinationToTaqueria();
            showPopupWindow(view);
        });

        setupMap();
    }

    private void setupMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
            setupPlaces();  // Mover la llamada a setupPlaces() aquí
        }
    }

    private void setupPlaces() {

        Places.initialize(requireContext(), getString(R.string.api_key));

        Button btnFrom = requireView().findViewById(R.id.btnFrom);

        Button btnTo = requireView().findViewById(R.id.btnTo);

        btnFrom.setOnClickListener(view -> startAutocomplete(REQUEST_CODE_AUTOCOMPLETE_FROM));

        btnTo.setOnClickListener(view -> startAutocomplete(REQUEST_CODE_AUTOCOMPLETE_TO));
    }

    private void startAutocomplete(int requestCode) {
        List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG, Place.Field.ADDRESS);
        Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields).build(requireContext());
        startActivityForResult(intent, requestCode);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (data != null) {
                Place place = Autocomplete.getPlaceFromIntent(data);
                Log.i(TAG, "Place Id: " + place.getId());

                if (requestCode == REQUEST_CODE_AUTOCOMPLETE_FROM) {
                    tvFrom.setText(getString(R.string.label_from) + place.getAddress());
                    mFromLatLng = place.getLatLng();
                    setMarkerFrom(mFromLatLng);
                } else if (requestCode == REQUEST_CODE_AUTOCOMPLETE_TO) {
                    tvTo.setText(getString(R.string.label_to) + place.getAddress());
                    mToLatLng = place.getLatLng();
                    setMarkerTo(mToLatLng);
                }
            }
        } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
            if (data != null) {
                Log.i(TAG, Autocomplete.getStatusFromIntent(data).getStatusMessage());
            }
        }
    }

    private void setMarkerFrom(LatLng latLng) {

        if (mMap != null) {

            if (mMarkerFrom != null) {
                mMarkerFrom.remove();
            }
            mMarkerFrom = mMap.addMarker(new MarkerOptions()
                    .position(latLng)
                    .title(getString(R.string.marker_title_from)));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            updateDistanceTextView();

        }

    }

    private void setMarkerTo(LatLng latLng) {
        if (mMap != null) {
            if (mMarkerTo != null) {
                mMarkerTo.remove();
            }
            mMarkerTo = mMap.addMarker(new MarkerOptions()
                    .position(latLng)
                    .title(getString(R.string.marker_title_to)));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            updateDistanceTextView();
        }
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMinZoomPreference(15f);
        mMap.setMaxZoomPreference(20f);

        LatLng defaultLocation = new LatLng(-34, 151);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(defaultLocation));
    }

    private void updateDistanceTextView() {
        if (mMarkerFrom != null && mMarkerTo != null) {
            // Obtén las coordenadas de las ubicaciones
            LatLng fromLatLng = mMarkerFrom.getPosition();
            LatLng toLatLng = mMarkerTo.getPosition();

            // Calcula la distancia en línea recta (haversine) en kilómetros
            double distance = calculateDistance(fromLatLng, toLatLng) / 1000.0;

            // Actualiza los TextViews con la información calculada
            tvDistance.setText(getString(R.string.label_distance)+ " " + String.format("%.2f", distance) + " Kilometros");

            // Puedes ajustar la lógica para calcular el tiempo y el precio según tus necesidades
            double timeInMinutes = estimateTime(distance);
            double priceInSoles = estimatePrice(distance, timeInMinutes);

            tvTime.setText(getString(R.string.label_time) + " "+ String.format("%.2f", timeInMinutes) + " Minutos");
            tvPrice.setText(getString(R.string.label_price)+ " " + String.format("%.2f", priceInSoles) + " soles");
        }
    }
    private double calculateDistance(LatLng fromLatLng, LatLng toLatLng) {
        Location fromLocation = new Location("From");
        fromLocation.setLatitude(fromLatLng.latitude);
        fromLocation.setLongitude(fromLatLng.longitude);

        Location toLocation = new Location("To");
        toLocation.setLatitude(toLatLng.latitude);
        toLocation.setLongitude(toLatLng.longitude);

        // La distancia en metros
        return fromLocation.distanceTo(toLocation);
    }
    private double estimateTime(double distance) {
        // Estimación del tiempo en minutos basada en una velocidad promedio
        double averageSpeed = 20.0; // ajusta según tus necesidades (kilómetros por hora)
        return distance / averageSpeed * 60.0; // convierte de horas a minutos
    }
    private double estimatePrice(double distance, double timeInMinutes) {
        // Estimación del precio en soles basada en tarifas por kilómetro y por minuto
        double pricePerKilometer = 0.5; // ajusta según tus necesidades
        double pricePerMinute = 0.1;   // ajusta según tus necesidades

        return (distance * pricePerKilometer) + (timeInMinutes * pricePerMinute);
    }
    private void playSound(int soundResourceId) {
        try {
            mediaPlayer = MediaPlayer.create(getActivity().getApplicationContext(), soundResourceId);
            if (mediaPlayer != null) {
                mediaPlayer.start();
                mediaPlayer.setOnCompletionListener(mp -> releaseMediaPlayer());
            } else {
                Log.e(TAG, "Error creating MediaPlayer");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Liberar recursos cuando se destruye la vista
        releaseMediaPlayer();
    }

    // Método para liberar el MediaPlayer
    private void releaseMediaPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    private void setDestinationToTaqueria() {
        // Coordenadas de la Taqueria (ajusta según sea necesario)
        double taqueriaLatitude = -13.526829;
        double taqueriaLongitude = -71.9492516;

        // Establecer el destino en el TextView
        tvTo.setText(getString(R.string.label_to) + " Taqueria");

        // Configurar el marcador en el mapa
        LatLng taqueriaLatLng = new LatLng(taqueriaLatitude, taqueriaLongitude);
        setMarkerTo(taqueriaLatLng);

        // Actualizar la distancia y otros detalles
        updateDistanceTextView();
    }

    private void showPopupWindow(View view) {
        // Inflar el diseño de la ventana flotante
        View popupView = LayoutInflater.from(getContext()).inflate(R.layout.popup_layout, null);

        // Configurar la imagen en la ventana flotante (cambia R.drawable.rias_bunny con tu propia imagen)
        ImageView imageView = popupView.findViewById(R.id.popup_layout);
        imageView.setImageResource(R.drawable.tacobarril);

        // Crear la ventana flotante con animaciones de entrada y salida
        PopupWindow popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setAnimationStyle(android.R.style.Animation_Dialog); // Animación de desvanecimiento

        // Mostrar la ventana flotante en el centro
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        // Cerrar la ventana flotante después de 5 segundos (puedes ajustar el tiempo según tus necesidades)
        new Handler().postDelayed(() -> {
            popupWindow.dismiss();
        }, 5000);
    }

}
