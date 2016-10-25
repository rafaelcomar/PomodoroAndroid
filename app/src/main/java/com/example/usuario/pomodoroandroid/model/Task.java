package com.example.usuario.pomodoroandroid.model;

import java.io.Serializable;

/**
 * Created by Usuário on 22/10/2016.
 */

public class Task implements Serializable {


    private String title;
    private String description;
    private String qtdPomodoros;
    private String qtdSegundos;
    private boolean descanso;
    private boolean concluded;
    private int pomodorosFeitos=0;

    public int getPomodorosFeitos() {
        return pomodorosFeitos;
    }

    public void setPomodorosFeitos(int pomodorosFeitos) {
        this.pomodorosFeitos = pomodorosFeitos;
    }

    public boolean isDescanso() {
        return descanso;
    }

    public void setDescanso(boolean descanso) {
        this.descanso = descanso;
    }

    public boolean isConcluded() {
        return concluded;
    }

    public void setConcluded(boolean concluded) {
        this.concluded = concluded;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getQtdPomodoros() {
        return qtdPomodoros;
    }

    public void setQtdPomodoros(String qtdPomodoros) {
        this.qtdPomodoros = qtdPomodoros;
    }

    public String getQtdSegundos() {
        return qtdSegundos;
    }

    public void setQtdSegundos(String qtdSegundos) {
        this.qtdSegundos = qtdSegundos;
    }
}
