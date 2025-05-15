package com.example.fichadesadepessoal.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.fichadesadepessoal.R;
import com.example.fichadesadepessoal.model.FichaSaude;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class FichaAdapter extends ArrayAdapter<FichaSaude> {

    private Context context;
    private List<FichaSaude> fichas;
    private DecimalFormat df = new DecimalFormat("0.00");
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());

    public FichaAdapter(Context context, List<FichaSaude> fichas) {
        super(context, 0, fichas);
        this.context = context;
        this.fichas = fichas;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null) {
            listItem = LayoutInflater.from(context).inflate(R.layout.item_lista, parent, false);
        }
        FichaSaude ficha = fichas.get(position);
        TextView tvNome = listItem.findViewById(R.id.tvItemNome);
        TextView tvIdade = listItem.findViewById(R.id.tvItemIdade);
        TextView tvPeso = listItem.findViewById(R.id.tvItemPeso);
        TextView tvAltura = listItem.findViewById(R.id.tvItemAltura);
        TextView tvIMC = listItem.findViewById(R.id.tvItemIMC);
        TextView tvPressao = listItem.findViewById(R.id.tvItemPressao);
        tvNome.setText(ficha.getNome());
        tvIdade.setText("Idade: " + ficha.getIdade());
        tvPeso.setText("Peso: " + df.format(ficha.getPeso()) + " kg");
        tvAltura.setText("Altura: " + df.format(ficha.getAltura()) + " m");
        tvIMC.setText("IMC: " + df.format(ficha.calcularIMC()));
        tvPressao.setText("Pressão Arterial: " + ficha.getPressaoArterial());
        return listItem;
    }
}