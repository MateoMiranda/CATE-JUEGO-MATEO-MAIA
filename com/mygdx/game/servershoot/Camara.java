package com.mygdx.game.servershoot;

import com.badlogic.gdx.graphics.OrthographicCamera;

public class Camara {
    private OrthographicCamera camara;
    private int altura = 400, ancho = 400;

    public Camara() {
        camara = new OrthographicCamera(); // Crea una nueva cámara ortográfica
        camara.setToOrtho(false, ancho, altura); // Configura la cámara con el ancho y alto dados, sin invertir el eje Y
    }

    // Método para obtener la cámara ortográfica
    public OrthographicCamera getCamara() {
        return camara;
    }

    public void update(float deltaTime) {
        camara.update(); // Actualiza el estado de la cámara
    }

    // Método para obtener la altura de la cámara
    public int getAltura() {
        return altura;
    }

    // Método para obtener el ancho de la cámara
    public int getAncho() {
        return ancho;
    }
}
