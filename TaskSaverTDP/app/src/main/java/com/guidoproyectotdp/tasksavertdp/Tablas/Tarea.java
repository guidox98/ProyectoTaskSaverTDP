package com.guidoproyectotdp.tasksavertdp.Tablas;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.guidoproyectotdp.tasksavertdp.ActividadPrincipal;
import com.guidoproyectotdp.tasksavertdp.ConectorSQLite;
import com.guidoproyectotdp.tasksavertdp.constantes;

public class Tarea {

    private ConectorSQLite conector;
    private int id;
    private String nombre;
    private int completado;

    public Tarea( int id, String nombre, int completado, ConectorSQLite conector) {
        this.conector = conector;
        this.id = id;
        this.nombre = nombre;
        this.completado = completado;
    }

    public Tarea(String nombre, int completado, ConectorSQLite c) {
        conector=c;

        this.nombre = nombre;
        this.completado = completado;

        SQLiteDatabase db=conector.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(constantes.campo_nombre, nombre);
        values.put(constantes.campo_completado, 0);
        Long idRes= db.insert(constantes.nombre_tabla, constantes.campo_id,values);
        id = idRes.intValue();
        db.close();
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCompletado() {
        return completado;
    }

    public void setCompletado(int completado) {
        this.completado = completado;
    }

    public void CambiarCompletado(int comp) {
        completado = comp;

    }

    public void completar(){
        SQLiteDatabase db=conector.getWritableDatabase();
        ContentValues cv= new ContentValues();
        int opuesto;
        if(completado== 1){
            opuesto=0;
        }else{ opuesto=1;
        }
        cv.put(constantes.campo_completado, opuesto);
        db.update(constantes.nombre_tabla, cv, constantes.campo_id+"="+id , null);

        db.close();

       completado= opuesto;
    }

}
