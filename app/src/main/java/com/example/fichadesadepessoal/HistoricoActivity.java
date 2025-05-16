package com.example.fichadesadepessoal;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fichadesadepessoal.adapter.FichaAdapter;
import com.example.fichadesadepessoal.database.FichaDatabase;
import com.example.fichadesadepessoal.model.FichaSaude;

import java.util.List;

public class HistoricoActivity extends AppCompatActivity {

    private ListView lvFichas;
    private Button btnVoltar;

    private FichaDatabase dbHelper;
    private FichaAdapter adapter;
    private List<FichaSaude> fichas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Histórico de Fichas");
        }
        dbHelper = new FichaDatabase(this);
        lvFichas = findViewById(R.id.lvFichas);
        btnVoltar = findViewById(R.id.btnVoltar);
        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

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
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
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