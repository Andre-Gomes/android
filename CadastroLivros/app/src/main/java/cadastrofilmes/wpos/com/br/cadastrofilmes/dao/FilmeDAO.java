package cadastrofilmes.wpos.com.br.cadastrofilmes.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import cadastrofilmes.wpos.com.br.cadastrofilmes.model.Filme;
import cadastrofilmes.wpos.com.br.cadastrofilmes.model.Genero;

/**
 * Created by gomes on 16/04/2017.
 */

public class FilmeDAO extends SQLiteOpenHelper {

    public FilmeDAO(Context context) {
        super(context, "CadastroFilmes", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE Filme (codigo INTEGER PRIMARY KEY, titulo TEXT NOT NULL, diretor TEXT NULL, anoLancamento INTEGER NOT NULL, genero INTEGER NOT NULL)";
        db.execSQL(sql);
        sql = "CREATE TABLE Genero (codigo INTEGER PRIMARY KEY, nome TEXT NOT NULL)";
        db.execSQL(sql);

        Genero genero = new Genero();
        genero.setNome("Ação");
        insereGenero(genero, db);

        genero = new Genero();
        genero.setNome("Romance");
        insereGenero(genero, db);

        genero = new Genero();
        genero.setNome("Comédia");
        insereGenero(genero, db);

        genero = new Genero();
        genero.setNome("Ficção Científica");
        insereGenero(genero, db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS Filme";
        db.execSQL(sql);
        sql = "DROP TABLE IF EXISTS Genero";
        db.execSQL(sql);
        onCreate(db);
    }

    public void insere(Filme filme) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues dados = pegaDadosDoFilme(filme);
        db.insert("Filme", null, dados);
    }

    @NonNull
    private ContentValues pegaDadosDoFilme(Filme filme) {
        ContentValues dados = new ContentValues();
        dados.put("titulo", filme.getTitulo());
        dados.put("diretor", filme.getDiretor());
        dados.put("anoLancamento", filme.getAnoLancamento());
        dados.put("genero", filme.getGenero().getCodigo());
        return dados;
    }

    public List<Filme> buscaFilmes() {
        String sql = "SELECT * from Filme;";
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(sql, null);

        List<Filme> filmes = new ArrayList<>();
        while(c.moveToNext()){
            Filme filme = new Filme();
            filme.setCodigo(c.getInt(c.getColumnIndex("codigo")));
            filme.setTitulo(c.getString(c.getColumnIndex("titulo")));
            filme.setDiretor(c.getString(c.getColumnIndex("diretor")));
            filme.setAnoLancamento(c.getInt(c.getColumnIndex("anoLancamento")));

            Integer codigoGenero = c.getInt(c.getColumnIndex("genero"));
            if (codigoGenero != null) {
                filme.setGenero(this.buscaGenero(codigoGenero));
            }
            filmes.add(filme);
        }
        c.close();
        return filmes;
    }

    public void deleta(Filme filme) {
        SQLiteDatabase db = getWritableDatabase();
        String[] params = {filme.getCodigo().toString()};
        db.delete("Filme", "codigo = ?", params);
    }

    public void altera(Filme filme) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues dados = pegaDadosDoFilme(filme);

        String[] params = {filme.getCodigo().toString()};
        db.update("Filme", dados, "codigo = ?", params);
    }

    public void insereGenero(Genero genero, SQLiteDatabase db) {
        ContentValues dados = pegaDadosDoGenerco(genero);
        db.insert("Genero", null, dados);
    }

    @NonNull
    private ContentValues pegaDadosDoGenerco(Genero genero) {
        ContentValues dados = new ContentValues();
        dados.put("nome", genero.getNome());
        return dados;
    }

    public List<Genero> buscaGeneros() {
        String sql = "SELECT * from Genero;";
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(sql, null);

        List<Genero> generos = new ArrayList<>();
        while(c.moveToNext()){
            Genero genero = new Genero();
            genero.setCodigo(c.getInt(c.getColumnIndex("codigo")));
            genero.setNome(c.getString(c.getColumnIndex("nome")));
            generos.add(genero);
        }
        c.close();
        return generos;
    }

    public Genero buscaGenero(Integer codigo) {
        String sql = "SELECT * from Genero where codigo = " + codigo + " ;";
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(sql, null);

        List<Genero> generos = new ArrayList<>();
        while(c.moveToNext()){
            Genero genero = new Genero();
            genero.setCodigo(c.getInt(c.getColumnIndex("codigo")));
            genero.setNome(c.getString(c.getColumnIndex("nome")));
            generos.add(genero);
        }
        c.close();
        if (!generos.isEmpty()){
            return generos.get(0);
        }
        return null;
    }

    public void deletaGenero(Genero genero) {
        SQLiteDatabase db = getWritableDatabase();
        String[] params = {genero.getCodigo().toString()};
        db.delete("Genero", "codigo = ?", params);
    }

    public void alteraGenero(Genero genero) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues dados = pegaDadosDoGenerco(genero);

        String[] params = {genero.getCodigo().toString()};
        db.update("Genero", dados, "codigo = ?", params);
    }
}

