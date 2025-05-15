package com.example.fichadesadepessoal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fichadesadepessoal.R;
import com.example.fichadesadepessoal.adapter.FichaAdapter;
import com.example.fichadesadepessoal.database.FichaDatabase;
import com.example.fichadesadepessoal.model.FichaSaude;

import java.util.List;

public class HistoricoActivity extends AppCompatActivity {

    private ListView lvFichas;

    private FichaDatabase dbHelper;
    private FichaAdapter adapter;
    private List<FichaSaude> fichas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico);
        dbHelper = new FichaDatabase(this);
        lvFichas = findViewById(R.id.lvFichas);
        lvFichas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FichaSaude ficha = fichas.get(position);
                Intent intent = new Intent(HistoricoActivity.this, VisualizacaoActivity.class);
                intent.putExtra("FICHA_ID", ficha.getId());
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregarFichas();
    }

    private void carregarFichas() {
        fichas = dbHelper.getAllFichas();

        if (fichas.isEmpty()) {
            lvFichas.setVisibility(View.GONE);
        } else {
            lvFichas.setVisibility(View.VISIBLE);
            adapter = new FichaAdapter(this, fichas);
            lvFichas.setAdapter(adapter);
        }
    }
}