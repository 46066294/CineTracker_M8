package marc.cinetracker_m8;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;


public class Work_Fragment extends Fragment implements LocationListener {

    Location localizacion = null; // Localización


    public Work_Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_work_lay, container, false);

        // Localizacion
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(
                getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return view;
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 10, this);
        ////////////
        createFirebase app = (createFirebase)getActivity().getApplication();
        Firebase ref = app.getRef();

        ref.child("hijo1").setValue("valor_1");
        ref.child("hijo2").setValue("valor_2");

        PojoForNote notaInicial = new PojoForNote("titol-a", "Institut Poblenou", 41.39834,2.20318);
        PojoForNote notaTest = new PojoForNote("titol-b", "Institut lalala", 41.0,2.0);

        ref.child("prueba").child("Notas").child("nota1").setValue(notaInicial);
        ref.child("prueba").child("Notas").child("nota2").setValue(notaTest);


        ref.child("hijo1").child("Notas").child("nota2")
                .addValueEventListener(new ValueEventListener() {
                    //@TargetApi(Build.VERSION_CODES.M)
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        System.out.println("onDataChange:" + snapshot.getValue());
                        msgToast(getContext(), "titulo", snapshot.getValue().toString());
                    }

                    //@TargetApi(Build.VERSION_CODES.M)
                    @Override
                    public void onCancelled(FirebaseError error) {
                        msgToast(getContext(), "Error", "Listener");
                    }
                });

        Button boton = (Button) view.findViewById(R.id.bCaptura);
        final EditText etTitulo = (EditText) view.findViewById(R.id.etTitolPeli);
        final EditText etDesc = (EditText) view.findViewById(R.id.etDescrip);

        //FALTA VER MAPA CON UBICACION
        // Listener button
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (localizacion != null) {
                    msgToast(v.getContext(), "", String.valueOf(localizacion.getLatitude()) + " - " + String.valueOf(localizacion.getLongitude()));

                }
                String tits = etTitulo.getText().toString();
                String descs = etDesc.getText().toString();
            }
        });
        ////////////
        return view;
    }



    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    public void msgToast (Context context, String tag, String msg){
        Toast.makeText(context, tag + ": " + msg, Toast.LENGTH_LONG).show();
    }

}
