package com.example.jocsjavafx;

import java.security.PrivilegedAction;
import java.util.Random;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

//branch bryan

public class PingPong extends Application {

    //Variables
    private static final int ancho = 800;
    private static final int alto = 600;
    private static final int ALTO_JUGADOR = 100;
    private static final int ANCHO_JUGADOR = 15;
    private static final double BOLA_R = 15;

    private int bolaYvelocidad = 1;
    private int bolaXvelocidad = 1;

    private double jugadorUnoYPos = alto / 2;
    private double jugadorDosYPos = alto /2;

    private double bolaXPos = ancho / 2;
    private double bolaYPos = alto / 2;


    private int puntuacionP1 = 0;
    private int puntuacionP2 = 0;

    private boolean gameStarted;

    private int jugadorUnoXPos = 0;
    private double jugadorDosXPos = ancho - ANCHO_JUGADOR;


    @Override
    public void start(Stage stage) throws Exception {

        stage.setTitle("P O N G");
        //tamaño del fondo
        Canvas canvas = new Canvas(ancho, alto);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        Timeline tl = new Timeline(new KeyFrame(Duration.millis(10), e ->run(gc)));
        tl.setCycleCount(Timeline.INDEFINITE);

        //Control del ratón
        canvas.setOnMouseMoved(e -> jugadorUnoYPos = e.getY());
        canvas.setOnMouseClicked(e -> gameStarted = true);
        stage.setScene(new Scene(new StackPane(canvas)));
        stage.show();
        tl.play();

    }

    private void run(GraphicsContext gc) {
        //Fondo del juego
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, ancho, alto);

        //Color del texto
        gc.setFill(Color.WHITE);
        gc.setFont(Font.font(25));

        if (gameStarted) {
            //movimiento del balon
            bolaXPos += bolaXvelocidad;
            bolaYPos += bolaYvelocidad;

            //ordenador

            if (bolaXPos < ancho - ancho / 4) {
                jugadorDosYPos = bolaYPos - ALTO_JUGADOR / 2;
            } else {
                jugadorDosYPos = bolaYPos > jugadorDosYPos + ALTO_JUGADOR / 2 ? jugadorDosYPos + 1 : jugadorDosYPos - 1;
            }

            //bola
            gc.fillOval(bolaXPos, bolaYPos, BOLA_R, BOLA_R);

        } else {
            // texto inicial
            gc.setStroke(Color.WHITE);
            gc.setTextAlign(TextAlignment.CENTER);
            gc.strokeText("dando clic", ancho / 2, alto / 2);

            // posicion inicial bola
            bolaXPos = ancho / 2;
            bolaYPos = alto / 2;

            // velocidad inicial bola
            bolaXvelocidad = new Random().nextInt(2) == 0 ? 1 : -1;
            bolaYvelocidad = new Random().nextInt(2) == 0 ? 1 : -1;
        }

        // que la bola no salga de pantalla
        if (bolaYPos > alto || bolaYPos < 0) bolaYvelocidad *= -1;

        // puntuacion ordenador
        if (bolaXPos < jugadorUnoXPos - ANCHO_JUGADOR) {
            puntuacionP2++;
            gameStarted = false;
        }

        // conseguir puntos
        if (bolaXPos > jugadorDosXPos + ANCHO_JUGADOR) {
            puntuacionP1++;
            gameStarted = false;
        }

        // aumenta velocidad bola
        if (((bolaXPos + BOLA_R > jugadorDosXPos) && bolaYPos >= jugadorDosYPos && bolaYPos <= jugadorDosYPos + ALTO_JUGADOR) ||
        ((bolaXPos < jugadorUnoXPos + ANCHO_JUGADOR) && bolaYPos >= jugadorUnoYPos && bolaYPos <= jugadorUnoYPos + ALTO_JUGADOR)){
            bolaYvelocidad+=1 * Math.signum(bolaYvelocidad);
            bolaXvelocidad +=1 * Math.signum(bolaXvelocidad);
            bolaXvelocidad *= -1;
            bolaYvelocidad *= -1;

        }


        // mostrar puntuacion
        gc.fillText(puntuacionP1 + "\t\t\t\t\t\t\t\t" + puntuacionP2, ancho / 2 , 100);

        // mostrar jugador uno y 2
        gc.fillRect(jugadorDosXPos, jugadorDosYPos, ANCHO_JUGADOR, ALTO_JUGADOR);
        gc.fillRect(jugadorUnoXPos, jugadorUnoYPos, ANCHO_JUGADOR, ALTO_JUGADOR);

        }


        // iniciar juego
    public  static void main(String[] args) {
        launch(args);
    }
}
//hola dia 1