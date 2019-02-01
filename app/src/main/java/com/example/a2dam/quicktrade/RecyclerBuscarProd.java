package com.example.a2dam.quicktrade;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class RecyclerBuscarProd extends RecyclerView.Adapter<RecyclerBuscarProd.BuscarProdViewHolder> {

    private ArrayList<Producto> arrayProductos;

    public static class BuscarProdViewHolder extends android.support.v7.widget.RecyclerView.ViewHolder implements AdapterView.OnClickListener {

        public TextView nombre;
        public TextView descrip;
        public TextView precio;
        public TextView categoria;

        public Context context;
        public View v;

        public BuscarProdViewHolder(@NonNull View itemView) {
            super(itemView);

            nombre = (TextView) itemView.findViewById(R.id.rc_buscar_nombre);
            descrip = (TextView) itemView.findViewById(R.id.rc_buscar_descrip);
            precio = (TextView) itemView.findViewById(R.id.rc_buscar_precio);
            categoria = (TextView) itemView.findViewById(R.id.rc_buscar_categ);

            v = itemView;
            context = itemView.getContext();
        }

        @Override
        public void onClick(View v) {

        }
    }

    public RecyclerBuscarProd(ArrayList<Producto> array, BuscarProd context)
    {
        this.arrayProductos = array;
    }

    @NonNull
    @Override
    public BuscarProdViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_buscar_prod,viewGroup, false);

        return new BuscarProdViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull BuscarProdViewHolder buscarProdViewHolder, int i) {
        buscarProdViewHolder.nombre.setText(arrayProductos.get(i).getNombre());
        buscarProdViewHolder.descrip.setText(arrayProductos.get(i).getDescripcion());
        buscarProdViewHolder.precio.setText(arrayProductos.get(i).getPrecio());
        buscarProdViewHolder.categoria.setText(arrayProductos.get(i).getCategoria());
    }

    @Override
    public int getItemCount() {
        return arrayProductos.size();
    }
}
