package com.example.a2dam.quicktrade;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class BuscarProd extends AppCompatActivity {

    private Spinner categoria;
    private Button buscar;
    private EditText usuario;
    private RadioGroup radioGroup;
    private RadioButton porUsuario;
    private RadioButton porCategoria;

    private RecyclerView rc;
    private RecyclerBuscarProd adaptadorRecycler;
    private RecyclerView.LayoutManager rvLM;

    ArrayAdapter<String> adapter;

    private FirebaseAuth mAuth;
    private DatabaseReference bbdd;

    private ArrayList<Producto> arrayProductos;
    private ArrayList<Producto> arrayFiltrado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_prod);

        categoria = (Spinner) findViewById(R.id.buscar_spinner);
        buscar = (Button) findViewById(R.id.buscar_btn_buscar);
        usuario = (EditText) findViewById(R.id.buscar_et_user);
        radioGroup = (RadioGroup) findViewById(R.id.buscar_radioGroup);
        porCategoria = (RadioButton) findViewById(R.id.buscar_radio_categoria);
        porUsuario = (RadioButton) findViewById(R.id.buscar_radio_user);

        arrayProductos = new ArrayList<Producto>();
        arrayFiltrado = new ArrayList<Producto>();

        rc = (RecyclerView) findViewById(R.id.buscar_recycler);
        rvLM = new LinearLayoutManager(BuscarProd.this);
        rc.setLayoutManager(rvLM);

        String[] arraySpinner = new String[] {"Tecnología", "Coches", "Hogar"};
        adapter = new ArrayAdapter<String>(BuscarProd.this, android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categoria.setAdapter(adapter);

        bbdd = FirebaseDatabase.getInstance().getReference("productos");
        mAuth = FirebaseAuth.getInstance();

        bbdd.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("MIO","Hijos "+dataSnapshot.getChildrenCount());
                for (DataSnapshot aux: dataSnapshot.getChildren())
                {
                    Producto auxiliar = aux.getValue(Producto.class);
                    arrayProductos.add(auxiliar);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (radioMarcado())
                {
                    arrayFiltrado.clear();
                    for (Producto aux: arrayProductos)
                    {
                        if (porUsuario.isChecked())
                        {
                            if (aux.getCreador().compareToIgnoreCase(usuario.getText().toString())==0)
                            {
                                arrayFiltrado.add(aux);
                            }
                        }
                        else
                        {
                            if (aux.getCategoria().compareToIgnoreCase(categoria.getSelectedItem().toString())==0)
                            {
                                arrayFiltrado.add(aux);
                            }
                        }
                    }

                    adaptadorRecycler = new RecyclerBuscarProd(arrayFiltrado, BuscarProd.this);
                    rc.setAdapter(adaptadorRecycler);
                }
            }
        });
    }

    private boolean radioMarcado()
    {
        if (radioGroup.getCheckedRadioButtonId()==-1)
        {
            return false;
        }
        else
        {
            return true;
        }
    }
}
