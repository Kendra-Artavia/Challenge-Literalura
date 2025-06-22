package org.example.literalura.servive;

public interface IConvierteDatos {
    <T> T obtenerDatos(String json, Class<T> clase);
}
