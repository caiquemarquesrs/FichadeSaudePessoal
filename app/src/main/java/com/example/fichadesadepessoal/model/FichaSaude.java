package com.example.fichadesadepessoal.model;

public class FichaSaude {
    private long id;
    private String nome;
    private int idade;
    private double peso;
    private double altura;
    private String pressaoArterial;

    public FichaSaude(String nome, int idade, double peso, double altura, String pressaoArterial) {
        this.nome = nome;
        this.idade = idade;
        this.peso = peso;
        this.altura = altura;
        this.pressaoArterial = pressaoArterial;
    }

    public FichaSaude() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public double getAltura() {
        return altura;
    }

    public void setAltura(double altura) {
        this.altura = altura;
    }

    public String getPressaoArterial() {
        return pressaoArterial;
    }

    public void setPressaoArterial(String pressaoArterial) {
        this.pressaoArterial = pressaoArterial;
    }


    public double calcularIMC() {
        if (altura <= 0) return 0;
        if (altura < 3) {
            return peso / (altura * altura);
        } else {
            double alturaEmMetros = altura / 100;
            return peso / (alturaEmMetros * alturaEmMetros);
        }
    }

    public String getClassificacaoIMC() {
        double imc = calcularIMC();

        if (imc < 18.5) {
            return "Abaixo do peso";
        } else if (imc < 25) {
            return "Peso normal";
        } else if (imc < 30) {
            return "Sobrepeso";
        } else if (imc < 35) {
            return "Obesidade Grau I";
        } else if (imc < 40) {
            return "Obesidade Grau II";
        } else {
            return "Obesidade Grau III";
        }
    }
}
