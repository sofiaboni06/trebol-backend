package com.trebol.dto;

public class IaResponseDTO {
    private String tipo;
    private String mensaje;
    private String redirectUrl;
    private boolean requiereCita;

    public IaResponseDTO() {
    }

    public IaResponseDTO(String tipo, String mensaje, String redirectUrl, boolean requiereCita) {
        this.tipo = tipo;
        this.mensaje = mensaje;
        this.redirectUrl = redirectUrl;
        this.requiereCita = requiereCita;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public boolean isRequiereCita() {
        return requiereCita;
    }

    public void setRequiereCita(boolean requiereCita) {
        this.requiereCita = requiereCita;
    }
}
