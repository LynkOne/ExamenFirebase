package com.example.a2dam.quicktrade;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class RecyclerBuscarUsuarios extends RecyclerView.Adapter<RecyclerBuscarUsuarios.BuscarUsuViewHolder> {

    private ArrayList<Usuario> arrayUsuarios;

    public static class BuscarUsuViewHolder extends RecyclerView.ViewHolder implements AdapterView.OnClickListener {

        public TextView nombre;

        private Button seguir;
        public Context context;
        public View v;

        public BuscarUsuViewHolder(@NonNull View itemView) {
            super(itemView);

            nombre = (TextView) itemView.findViewById(R.id.rc_et_usunombre);
            seguir=(Button)itemView.findViewById(R.id.rc_btn_Seguir);

            v = itemView;
            context = itemView.getContext();
        }

        @Override
        public void onClick(View v) {

        }
    }

    public RecyclerBuscarUsuarios(ArrayList<Usuario> array, seguirUsuario context)
    {
        this.arrayUsuarios = array;
    }

    @NonNull
    @Override
    public BuscarUsuViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_buscar_usuario,viewGroup, false);

        return new BuscarUsuViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull BuscarUsuViewHolder buscarUsuViewHolder, int i) {
        final int aux = i;
       buscarUsuViewHolder.nombre.setText(arrayUsuarios.get(i).getNombre());

       buscarUsuViewHolder.seguir.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
            //String nombreuserfollow=arrayUsuarios.get(aux).getNombreUser();

           }
       });

    }

    @Override
    public int getItemCount() {
        return arrayUsuarios.size();
    }
    public interface rcseguirUsuarios{
        void seguirUsuarios(String id);
    }
}
