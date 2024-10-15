package sptech.school;

public class PesquisaFiltrada {
    private Double banheiro;
    private Double bagagem;
    private Double fila;
    private Double transporte;


    public PesquisaFiltrada(){

    }

    public PesquisaFiltrada(Double banheiro, Double bagagem, Double fila, Double transporte) {
        this.banheiro = banheiro;
        this.bagagem = bagagem;
        this.fila = fila;
        this.transporte = transporte;
    }

    public Double getBanheiro() {
        return banheiro;
    }

    public void setBanheiro(Double banheiro) {
        this.banheiro = banheiro;
    }

    public Double getBagagem() {
        return bagagem;
    }

    public void setBagagem(Double bagagem) {
        this.bagagem = bagagem;
    }

    public Double getFila() {
        return fila;
    }

    public void setFila(Double fila) {
        this.fila = fila;
    }

    public Double getTransporte() {
        return transporte;
    }

    public void setTransporte(Double transporte) {
        this.transporte = transporte;
    }
}
