package com.guidoproyectotdp.tasksavertdp;

import android.app.ActionBar;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.guidoproyectotdp.tasksavertdp.Tablas.Tarea;

import java.util.ArrayList;

public class ActividadPrincipal extends AppCompatActivity {
    LinearLayout layout;
    EditText TextoNuevaTarea;
    ConectorSQLite conector;
    ArrayList<Tarea> listaTareas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad_principal);

        layout= (LinearLayout) findViewById(R.id.applayout);
        conector= new ConectorSQLite(getApplicationContext(), constantes.nombre_tabla, null, 1);

        TextoNuevaTarea= (EditText) findViewById(R.id.editTarea);

        listaTareas=consultarTareas();
        mostrarTareas(listaTareas);

    }

    public void crearTarea(View v){

        Tarea tarea= new Tarea(TextoNuevaTarea.getText().toString(), 0, conector);
        listaTareas.add(tarea);
        createButtonTarea(tarea);

        Toast.makeText(getApplicationContext(),"ID: "+ tarea.getId(), Toast.LENGTH_SHORT).show();
    }

    public ArrayList<Tarea> consultarTareas(){
        SQLiteDatabase db=conector.getReadableDatabase();

        Tarea tarea;
        ArrayList<Tarea> lista=new ArrayList<Tarea>();

        Cursor cursor=db.rawQuery("SELECT * FROM "+ constantes.nombre_tabla, null);
        //cursor deberia tener elementos con forma |ID|Nombre|Completado| por lo q es |INT|STRING|INT|
        while(cursor.moveToNext()){
            tarea= new Tarea( cursor.getInt(0), // ID
                            cursor.getString(1), // NOMBRE
                            cursor.getInt(2),   //COMPLETADO
                            conector);
            lista.add(tarea);
        }
        db.close();
        return lista;
    }

    public void createButtonTarea(final Tarea tarea){

        Button btn = new Button(getApplicationContext());
        btn.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        btn.setText(tarea.getNombre());//el boton muestra el texto de la tarea
        btn.setTag(tarea);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tarea t=(Tarea) v.getTag();
                Button b= (Button) v;
                t.completar();

                if(t.getCompletado()==1) {
                    b.setPaintFlags(b.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    Toast.makeText(getApplicationContext(),"completado", Toast.LENGTH_SHORT).show();
                }else{ b.setPaintFlags(b.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                    Toast.makeText(getApplicationContext(),"descompletado", Toast.LENGTH_SHORT).show();
                }
            }
        });
        layout.addView(btn);
    }

    public void mostrarTareas(ArrayList<Tarea> lista){

        Tarea tarea;
        for(int i=0; i< lista.size(); i++){
            tarea=lista.get(i);
            createButtonTarea(tarea);
        }
    }

}
