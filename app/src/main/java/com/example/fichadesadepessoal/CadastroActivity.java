package com.example.fichadesadepessoal;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fichadesadepessoal.database.FichaDatabase;
import com.example.fichadesadepessoal.model.FichaSaude;

public class CadastroActivity extends AppCompatActivity {

    private EditText etNome;
    private EditText etIdade;
    private EditText etPeso;
    private EditText etAltura;
    private EditText etPressao;
    private Button btnSalvar;
    private Button btnVoltar;

    private FichaDatabase dbHelper;
    private FichaSaude fichaAtual;
    private boolean modoEdicao = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Cadastro de Ficha");
        }
        dbHelper = new FichaDatabase(this);
        etNome = findViewById(R.id.etNome);
        etIdade = findViewById(R.id.etIdade);
        etPeso = findViewById(R.id.etPeso);
        etAltura = findViewById(R.id.etAltura);
        etPressao = findViewById(R.id.etPressao);
        btnSalvar = findViewById(R.id.btnSalvar);
        btnVoltar = findViewById(R.id.btnVoltar);
        if (getIntent().hasExtra("FICHA_ID")) {
            modoEdicao = true;
            long fichaId = getIntent().getLongExtra("FICHA_ID", -1);
            fichaAtual = dbHelper.getFicha(fichaId);

            if (fichaAtual != null) {
                preencherCampos(fichaAtual);
            }
        }
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvarFicha();
            }
        });
        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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

    private void preencherCampos(FichaSaude ficha) {
        etNome.setText(ficha.getNome());
        etIdade.setText(String.valueOf(ficha.getIdade()));
        etPeso.setText(String.valueOf(ficha.getPeso()));
        etAltura.setText(String.valueOf(ficha.getAltura()));
        etPressao.setText(ficha.getPressaoArterial());
    }

    private void salvarFicha() {
        if (etNome.getText().toString().trim().isEmpty() ||
                etIdade.getText().toString().trim().isEmpty() ||
                etPeso.getText().toString().trim().isEmpty() ||
                etAltura.getText().toString().trim().isEmpty() ||
                etPressao.getText().toString().trim().isEmpty()) {

            Toast.makeText(this, "Por favor, preencha todos os campos", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            String nome = etNome.getText().toString().trim();
            int idade = Integer.parseInt(etIdade.getText().toString().trim());
            double peso = Double.parseDouble(etPeso.getText().toString().trim());
            double altura = Double.parseDouble(etAltura.getText().toString().trim());
            String pressao = etPressao.getText().toString().trim();
            if (modoEdicao && fichaAtual != null) {
                fichaAtual.setNome(nome);
                fichaAtual.setIdade(idade);
                fichaAtual.setPeso(peso);
                fichaAtual.setAltura(altura);
                fichaAtual.setPressaoArterial(pressao);
                dbHelper.atualizarFicha(fichaAtual);
                Toast.makeText(this, "Ficha atualizada com sucesso!", Toast.LENGTH_SHORT).show();
            } else {
                FichaSaude novaFicha = new FichaSaude(nome, idade, peso, altura, pressao);
                long id = dbHelper.inserirFicha(novaFicha);
                if (id > 0) {
                    Toast.makeText(this, "Ficha salva com sucesso!", Toast.LENGTH_SHORT).show();
                    limparCampos();
                } else {
                    Toast.makeText(this, "Erro ao salvar ficha", Toast.LENGTH_SHORT).show();
                }
            }

            finish();

        } catch (NumberFormatException e) {
            Toast.makeText(this, "Por favor, insira valores numéricos válidos", Toast.LENGTH_SHORT).show();
        }
    }

    private void limparCampos() {
        etNome.setText("");
        etIdade.setText("");
        etPeso.setText("");
        etAltura.setText("");
        etPressao.setText("");
        etNome.requestFocus();
    }
}