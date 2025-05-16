package com.example.fichadesadepessoal;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fichadesadepessoal.database.FichaDatabase;
import com.example.fichadesadepessoal.model.FichaSaude;

import java.text.DecimalFormat;

public class VisualizacaoActivity extends AppCompatActivity {

    private TextView tvNome;
    private TextView tvIdade;
    private TextView tvPeso;
    private TextView tvAltura;
    private TextView tvPressao;
    private TextView tvIMC;
    private TextView tvClassificacaoIMC;
    private Button btnVoltar;
    private FichaDatabase dbHelper;
    private FichaSaude ficha;
    private long fichaId;

    private DecimalFormat df = new DecimalFormat("0.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizacao);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Visualização da Ficha");
        }
        dbHelper = new FichaDatabase(this);
        tvNome = findViewById(R.id.tvNome);
        tvIdade = findViewById(R.id.tvIdade);
        tvPeso = findViewById(R.id.tvPeso);
        tvAltura = findViewById(R.id.tvAltura);
        tvPressao = findViewById(R.id.tvPressao);
        tvIMC = findViewById(R.id.tvIMC);
        tvClassificacaoIMC = findViewById(R.id.tvClassificacaoIMC);
        btnVoltar = findViewById(R.id.btnVoltar);
        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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
        carregarDados();
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
        carregarDados();
    }

    private void carregarDados() {
        ficha = dbHelper.getFicha(fichaId);

        if (ficha != null) {
            tvNome.setText(ficha.getNome());
            tvIdade.setText(String.valueOf(ficha.getIdade()));
            tvPeso.setText(df.format(ficha.getPeso()) + " kg");
            tvAltura.setText(df.format(ficha.getAltura()) + " m");
            tvPressao.setText(ficha.getPressaoArterial());

            double imc = ficha.calcularIMC();
            tvIMC.setText(df.format(imc));
            tvClassificacaoIMC.setText(ficha.getClassificacaoIMC());
        }
    }
}