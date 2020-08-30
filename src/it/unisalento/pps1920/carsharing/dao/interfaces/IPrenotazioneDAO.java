package it.unisalento.pps1920.carsharing.dao.interfaces;

import it.unisalento.pps1920.carsharing.model.Accessorio;
import it.unisalento.pps1920.carsharing.model.Prenotazione;

import java.util.ArrayList;

public interface IPrenotazioneDAO extends IBaseDAO<Prenotazione> {

    //creare altri metodi diversi da findById e findAll
    //che possono essere utili per la traccia, es.
    //dammi tutte le prenotazioni di un cliente

    public boolean salvaPrenotazione(Prenotazione p);
    public ArrayList<String> sharingCheck(Prenotazione p);
    public ArrayList<String[]> getClienteInfo(int idPrenotazione, int index);
    public int getNumClienti(int idPrenotazione);
    public boolean inserisciAccessori(Accessorio acc);
    public ArrayList<Prenotazione> findAllForCliente();
}
