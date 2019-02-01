package com.example.a2dam.quicktrade;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class EditarProductos extends AppCompatActivity implements RecyclerProductEdit.interfazEditarProd {

    private RecyclerView rc;
    private RecyclerProductEdit adaptadorRecycler;
    private RecyclerView.LayoutManager rvLM;

    private DatabaseReference bbdd;
    private FirebaseAuth mAuth;
    private FirebaseUser usuario;

    private Button cargar;

    private ArrayList<Producto> arrayProductos;
    private ArrayList<String> arrayIDS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_productos);

        cargar = (Button) findViewById(R.id.btn_cargar_edit_prod);
        cargar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarRecycler();
            }
        });

        arrayIDS = new ArrayList<>();
        arrayProductos = new ArrayList<>();

        mAuth = FirebaseAuth.getInstance();
        usuario = mAuth.getCurrentUser();
        bbdd = FirebaseDatabase.getInstance().getReference("productos");

        bbdd.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                arrayProductos.clear();
                arrayIDS.clear();

                for (DataSnapshot aux: dataSnapshot.getChildren())
                {
                    Producto auxiliar = aux.getValue(Producto.class);

                    Log.d("MIO","CREADOR "+auxiliar.getCreador());
                    Log.d("MIO","USUARIO "+usuario.getUid());

                    if (auxiliar.getCreador().compareToIgnoreCase(usuario.getUid())==0)
                    {
                        Log.d("MIO","prod del user");
                        arrayProductos.add(auxiliar);
                        arrayIDS.add(aux.getKey());
                    }
                    else
                    {
                        Log.d("MIO","prod de otro user");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void cargarRecycler()
    {
        rc = (RecyclerView) findViewById(R.id.recycler_edit_prod);
        rvLM = new LinearLayoutManager(this);
        rc.setLayoutManager(rvLM);

        adaptadorRecycler = new RecyclerProductEdit(arrayProductos,arrayIDS, this);
        rc.setAdapter(adaptadorRecycler);
    }

    @Override
    public void productoCambia(Producto p, String id) {
        Log.d("MIO","Editando "+bbdd.child(id));
        Log.d("MIO","El producto "+p.toString());
        final String idAux = id;
        final String nombre = p.getNombre().toString();
        final String descrip = p.getDescripcion();
        final String precio = p.getPrecio();

        bbdd.child(idAux).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("MIO","nombre "+nombre+" descrip "+descrip+" precio "+precio);
                dataSnapshot.getRef().child("nombre").setValue(nombre);
                dataSnapshot.getRef().child("descripcion").setValue(descrip);
                dataSnapshot.getRef().child("precio").setValue(precio);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void productoElimina(String id) {
        Log.d("MIO","eliminando "+bbdd.child(id));
        bbdd.child(id).removeValue();
    }
}
