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

public class seguirUsuario extends AppCompatActivity {





    private RecyclerView rc;
    private RecyclerBuscarUsuarios adaptadorRecycler;
    private RecyclerView.LayoutManager rvLM;

    ArrayAdapter<String> adapter;
    //private ArrayList<String> arrayIDS;
    private FirebaseAuth mAuth;
    private DatabaseReference bbdd;
    private Button cargar;
    private ArrayList<Usuario> arrayUsuarios;
    private ArrayList<Usuario> arrayFiltrado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seguir_usuario);
        //arrayIDS = new ArrayList<>();

        cargar=(Button)findViewById(R.id.btn_cargarUsu);
        arrayUsuarios = new ArrayList<Usuario>();
        arrayFiltrado = new ArrayList<Usuario>();

        rc = (RecyclerView) findViewById(R.id.buscarusu_recycler);
        rvLM = new LinearLayoutManager(seguirUsuario.this);
        rc.setLayoutManager(rvLM);




        bbdd = FirebaseDatabase.getInstance().getReference("usuarios");
        mAuth = FirebaseAuth.getInstance();

        bbdd.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("MIO","Hijos "+dataSnapshot.getChildrenCount());
                arrayUsuarios.clear();
               // arrayIDS.clear();
                for (DataSnapshot aux: dataSnapshot.getChildren())
                {
                    Usuario auxiliar = aux.getValue(Usuario.class);
                    arrayUsuarios.add(auxiliar);
                    //arrayIDS.add();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        cargar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarRecycler();
            }
        });



    }
    private void cargarRecycler()
    {
        rc = (RecyclerView) findViewById(R.id.buscarusu_recycler);
        rvLM = new LinearLayoutManager(this);
        rc.setLayoutManager(rvLM);

        adaptadorRecycler = new RecyclerBuscarUsuarios(arrayUsuarios, this);
        rc.setAdapter(adaptadorRecycler);
    }



}
