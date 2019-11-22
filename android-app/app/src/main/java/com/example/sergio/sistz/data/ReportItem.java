package com.example.sergio.sistz.data;

public class ReportItem {
    private String title;
    private String description;
    private int imagen;
    private int idReporte;
    private boolean hasConfiguration;

    public boolean isHasConfiguration() {
        return hasConfiguration;
    }

    public void setHasConfiguration(boolean hasConfiguration) {
        this.hasConfiguration = hasConfiguration;
    }

    public ReportItem(int id, String title, String description, int imagen, boolean hasConfiguration) {
        this.idReporte = id;
        this.title = title;
        this.imagen = imagen;
        this.description = description;
        this.hasConfiguration = hasConfiguration;
    }

    public int getIdReporte() {
        return idReporte;
    }

    public void setIdReporte(int idReporte) {
        this.idReporte = idReporte;
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

    public int getImagen() {
        return imagen;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }
}