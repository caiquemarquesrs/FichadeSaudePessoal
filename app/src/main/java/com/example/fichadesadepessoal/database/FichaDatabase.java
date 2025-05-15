package com.example.fichadesadepessoal.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.fichadesadepessoal.model.FichaSaude;

import java.util.ArrayList;
import java.util.List;

public class FichaDatabase extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "fichasaude.db";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_FICHAS = "fichas";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NOME = "nome";
    public static final String COLUMN_IDADE = "idade";
    public static final String COLUMN_PESO = "peso";
    public static final String COLUMN_ALTURA = "altura";
    public static final String COLUMN_PRESSAO = "pressao_arterial";
    public static final String COLUMN_DATA = "data_cadastro";
    private static final String SQL_CREATE_TABLE =
            "CREATE TABLE " + TABLE_FICHAS + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NOME + " TEXT, " +
                    COLUMN_IDADE + " INTEGER, " +
                    COLUMN_PESO + " REAL, " +
                    COLUMN_ALTURA + " REAL, " +
                    COLUMN_PRESSAO + " TEXT, " +
                    COLUMN_DATA + " INTEGER)";

    public FichaDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FICHAS);
        onCreate(db);
    }
    public long inserirFicha(FichaSaude ficha) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NOME, ficha.getNome());
        values.put(COLUMN_IDADE, ficha.getIdade());
        values.put(COLUMN_PESO, ficha.getPeso());
        values.put(COLUMN_ALTURA, ficha.getAltura());
        values.put(COLUMN_PRESSAO, ficha.getPressaoArterial());
        values.put(COLUMN_DATA, ficha.getDataCadastro());

        long id = db.insert(TABLE_FICHAS, null, values);
        ficha.setId(id);

        return id;
    }
    public int atualizarFicha(FichaSaude ficha) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NOME, ficha.getNome());
        values.put(COLUMN_IDADE, ficha.getIdade());
        values.put(COLUMN_PESO, ficha.getPeso());
        values.put(COLUMN_ALTURA, ficha.getAltura());
        values.put(COLUMN_PRESSAO, ficha.getPressaoArterial());

        return db.update(TABLE_FICHAS, values, COLUMN_ID + " = ?",
                new String[]{String.valueOf(ficha.getId())});
    }
    public void deletarFicha(long id) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_FICHAS, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
    }
    public FichaSaude getFicha(long id) {
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(TABLE_FICHAS, null, COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)}, null, null, null);

        FichaSaude ficha = null;

        if (cursor != null && cursor.moveToFirst()) {
            ficha = cursorToFicha(cursor);
            cursor.close();
        }

        return ficha;
    }
    public FichaSaude getFichaMaisRecente() {
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(TABLE_FICHAS, null, null, null, null, null,
                COLUMN_DATA + " DESC", "1");

        FichaSaude ficha = null;

        if (cursor != null && cursor.moveToFirst()) {
            ficha = cursorToFicha(cursor);
            cursor.close();
        }

        return ficha;
    }
    public List<FichaSaude> getAllFichas() {
        List<FichaSaude> fichas = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(TABLE_FICHAS, null, null, null, null, null,
                COLUMN_DATA + " DESC");

        if (cursor != null && cursor.moveToFirst()) {
            do {
                fichas.add(cursorToFicha(cursor));
            } while (cursor.moveToNext());

            cursor.close();
        }

        return fichas;
    }
    private FichaSaude cursorToFicha(Cursor cursor) {
        FichaSaude ficha = new FichaSaude();

        ficha.setId(cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_ID)));
        ficha.setNome(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOME)));
        ficha.setIdade(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_IDADE)));
        ficha.setPeso(cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_PESO)));
        ficha.setAltura(cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_ALTURA)));
        ficha.setPressaoArterial(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PRESSAO)));
        ficha.setDataCadastro(cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_DATA)));

        return ficha;
    }
}