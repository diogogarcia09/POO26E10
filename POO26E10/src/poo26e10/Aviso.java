package poo26e10;

import java.util.Date;

public class Aviso {

    private String idAviso;
    private String mensagem;
    private String destinatario;
    private Date datEnvio;

    public Aviso(String idAviso, String mensagem, String destinatario) {
        this.idAviso= idAviso;
        this.mensagem= mensagem;
        this.destinatario= destinatario;
        this.datEnvio= null;
    }

    public String getIdAviso() {
        return idAviso;
    }

    public String getMensagem() {
        return mensagem;
    }

    public String getDestinatario() {
        return destinatario;
    }

    public Date getDatEnvio() {
        return datEnvio;
    }

    public void setMensagem(String mensagem) {
        this.mensagem= mensagem;
    }

    public void setDestinatario(String destinatario) {
        this.destinatario= destinatario;
    }

    // metodo
    public void enviar() {
        this.datEnvio= new Date();

        System.out.println("Aviso enviado para:" + destinatario);
        System.out.println("ID do aviso: " + idAviso);
        System.out.println("Mensagem: " + mensagem);
        System.out.println("Data de envio: " + datEnvio);
    }

    @Override
    public String toString() { 
    	return "ID: " + idAviso + "\nMensagem: " + mensagem + "\nDestinatário: " + destinatario + "\nData: " + datEnvio; }
}