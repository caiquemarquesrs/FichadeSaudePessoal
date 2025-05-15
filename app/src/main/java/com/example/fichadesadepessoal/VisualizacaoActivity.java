package com.example.fichadesadepessoal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fichadesadepessoal.R;
import com.example.fichadesadepessoal.database.FichaDatabase;
import com.example.fichadesadepessoal.model.FichaSaude;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class VisualizacaoActivity extends AppCompatActivity {

    private TextView tvNome;
    private TextView tvIdade;
    private TextView tvPeso;
    private TextView tvAltura;
    private TextView tvPressao;
    private TextView tvIMC;
    private TextView tvClassificacaoIMC;
    private FichaDatabase dbHelper;
    private FichaSaude ficha;
    private long fichaId;

    private DecimalFormat df = new DecimalFormat("0.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizacao);
        dbHelper = new FichaDatabase(this);
        tvNome = findViewById(R.id.tvNome);
        tvIdade = findViewById(R.id.tvIdade);
        tvPeso = findViewById(R.id.tvPeso);
        tvAltura = findViewById(R.id.tvAltura);
        tvPressao = findViewById(R.id.tvPressao);
        tvIMC = findViewById(R.id.tvIMC);
        tvClassificacaoIMC = findViewById(R.id.tvClassificacaoIMC);
        if (getIntent().hasExtra("FICHA_ID")) {
            fichaId = getIntent().getLongExtra("FICHA_ID", -1);
        } else {
            FichaSaude fichaMaisRecente = dbHelper.getFichaMaisRecente();
            if (fichaMaisRecente != null) {
                fichaId = fichaMaisRecente.getId();
            } else {
                Toast.makeText(this, "Nenhuma ficha encontrada", Toast.LENGTH_SHORT).show();
                finish();
                return;
            }
        }
    }
}
