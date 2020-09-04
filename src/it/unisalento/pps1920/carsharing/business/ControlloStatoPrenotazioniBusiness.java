
package it.unisalento.pps1920.carsharing.business;

import it.unisalento.pps1920.carsharing.dao.interfaces.IAddettoDAO;
import it.unisalento.pps1920.carsharing.dao.interfaces.IPrenotazioneDAO;
import it.unisalento.pps1920.carsharing.dao.interfaces.IStazioneDAO;
import it.unisalento.pps1920.carsharing.dao.mysql.*;
import it.unisalento.pps1920.carsharing.model.Operatore;
import it.unisalento.pps1920.carsharing.model.Prenotazione;
import it.unisalento.pps1920.carsharing.model.Stazione;
import it.unisalento.pps1920.carsharing.util.Session;

import java.util.ArrayList;

public class ControlloStatoPrenotazioniBusiness {           //Eseguita dall'operatore

    private static ControlloStatoPrenotazioniBusiness instance;

    public static synchronized ControlloStatoPrenotazioniBusiness getInstance() {
        if(instance == null)
            instance = new ControlloStatoPrenotazioniBusiness();
        return instance;
    }

    public ArrayList<String[]> mostraVeicoliPronti(int idOperatore) {
        ArrayList<String[]> pre = new ArrayList<String[]>();
        OperatoreDAO odao = new OperatoreDAO();
        StazioneDAO sdao = new StazioneDAO();
        Stazione s = new Stazione();
        IAddettoDAO iDao = new AddettoDAO();
        s = sdao.findStationByAddettoId(iDao.getIdAddettoFromIdOperatore(idOperatore));
        pre = odao.findByStation3(s);
        return pre;
    }

    public ArrayList<Prenotazione> checkPrenotazioni(int id) {
        OperatoreDAO odao = new OperatoreDAO();
        StazioneDAO sdao = new StazioneDAO();
        Stazione s = new Stazione();
        ArrayList<Prenotazione>pre = new ArrayList<Prenotazione>();
        s=sdao.findStationByOperatorId(id);
        pre= odao.findByStation(s);
        if(pre==null)
        {
            System.out.println("Mi spiace ma al momento non sono presenti delle prenotazioni.");
            return null;
        }
        return pre;
    }


    public void inserisciPagato(int idPrenotazione){
        IPrenotazioneDAO iDao = new PrenotazioneDAO();
        iDao.setPagato(idPrenotazione);
    }

    public String nomeStazioneFromOperatore(int idOperatore){
        IStazioneDAO iDao = new StazioneDAO();
        return  iDao.nomeStazioneFromOperatore(idOperatore);
    }

    public String nomeStazioneFromAddetto(int idAddetto) {
        IStazioneDAO iDao = new StazioneDAO();
        return  iDao.nomeStazioneFromAddetto(idAddetto);
    }


}