package com.pepe.application.ui.Redes;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.pepe.application.R;

public class RedesFragment extends Fragment {

    private WebView webView;
    private MediaPlayer mediaPlayerInstagram; // Agregar instancia de MediaPlayer para Instagram
    private MediaPlayer mediaPlayerFacebook;  // Agregar instancia de MediaPlayer para Facebook

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_redes, container, false);

        // Inicializar el MediaPlayer con el sonido para Instagram
        mediaPlayerInstagram = MediaPlayer.create(getActivity(), R.raw.instagram);

        // Inicializar el MediaPlayer con el sonido para Facebook
        mediaPlayerFacebook = MediaPlayer.create(getActivity(), R.raw.facebook);

        // Obtén una referencia al WebView y configúralo
        webView = view.findViewById(R.id.webView);
        configureWebView();

        // Obtén referencias a los botones
        Button btnInstagram = view.findViewById(R.id.btnTwitter);
        Button btnFacebook = view.findViewById(R.id.btnFacebook);

        // Asigna acciones a los botones
        btnInstagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Reproducir el sonido de Instagram al hacer clic en el botón
                playSound(mediaPlayerInstagram);
                loadPage("https://www.instagram.com/explore/locations/102853618747382/el-barril-cusco/");
            }
        });

        btnFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Reproducir el sonido de Facebook al hacer clic en el botón
                playSound(mediaPlayerFacebook);

                String facebookUrl = "https://www.facebook.com/TacosMexicanosElBarrilOficial";
                try {
                    // Intenta abrir la aplicación de Facebook
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("fb://facewebmodal/f?href=" + facebookUrl)));
                } catch (Exception e) {
                    // Si falla, abre en el navegador externo o en el WebView
                    loadFacebookPage(facebookUrl);
                }
            }
        });

        return view;
    }

    // Método para reproducir el sonido
    private void playSound(MediaPlayer mediaPlayer) {
        if (mediaPlayer != null) {
            mediaPlayer.start();
        }
    }

    private void loadFacebookPage(String url) {
        // Manipula la URL de Facebook para que se abra correctamente en el WebView
        String mobileUrl = "https://m.facebook.com/TacosMexicanosElBarrilOficial?wtsid=rdr_00KLybxdp5ujDxc9z" + Uri.parse(url).getPath();
        webView.loadUrl(mobileUrl);
    }

    private void configureWebView() {
        // Configura el WebView
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true); // Habilita JavaScript (si es necesario)
        webView.setWebViewClient(new WebViewClient()); // Permite la navegación interna del WebView
    }

    private void loadPage(String url) {
        // Carga la página en el WebView
        webView.loadUrl(url);
    }
}
