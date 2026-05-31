package com.trebol.dto;

public class IaRequestDTO {
    private String pregunta;
    private String tema;

    public IaRequestDTO() {
    }

    public IaRequestDTO(String pregunta, String tema) {
        this.pregunta = pregunta;
        this.tema = tema;
    }

    public String getPregunta() {
        return pregunta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    public String getTema() {
        return tema;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }
}
