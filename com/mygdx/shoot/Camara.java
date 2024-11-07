package com.mygdx.shoot;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Camara {
    private OrthographicCamera camara;
    private int altura=400,ancho=400;

    public Camara() {
        camara = new OrthographicCamera();
        camara.setToOrtho(false, ancho, altura); 
    }

    public OrthographicCamera getCamara() {
        return camara;
    }

    public void update(float deltaTime) {
        camara.update();
    }

	public int getAltura() {
		return altura;
	}

	public int getAncho() {
		return ancho;
	}

}