package com.guidoproyectotdp.tasksavertdp;

public class constantes {

    public static final String nombre_tabla="db_tareas";

    public static final String campo_id="id";
    public static final String campo_nombre="nombre";
    public static final String campo_completado="completado";

    public static final String crear_tabla="CREATE TABLE "+ nombre_tabla +" ("+ campo_id +" INTEGER PRIMARY KEY, "
            + campo_nombre +" TEXT, "+ campo_completado +" INTEGER)";

}
