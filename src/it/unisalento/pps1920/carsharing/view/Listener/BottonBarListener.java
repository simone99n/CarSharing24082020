package it.unisalento.pps1920.carsharing.view.Listener;

import it.unisalento.pps1920.carsharing.business.PrenotazioneBusiness;
import it.unisalento.pps1920.carsharing.dao.interfaces.IClienteDAO;
import it.unisalento.pps1920.carsharing.dao.interfaces.ILocalitaDAO;
import it.unisalento.pps1920.carsharing.dao.interfaces.IMezzoDAO;
import it.unisalento.pps1920.carsharing.dao.interfaces.IStazioneDAO;
import it.unisalento.pps1920.carsharing.dao.mysql.ClienteDAO;
import it.unisalento.pps1920.carsharing.dao.mysql.LocalitaDAO;
import it.unisalento.pps1920.carsharing.dao.mysql.MezzoDAO;
import it.unisalento.pps1920.carsharing.dao.mysql.StazioneDAO;
import it.unisalento.pps1920.carsharing.model.*;
import it.unisalento.pps1920.carsharing.util.DateUtil;
import it.unisalento.pps1920.carsharing.util.Session;
import it.unisalento.pps1920.carsharing.view.FinestraConGerarchia;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

public class BottonBarListener implements ActionListener {

    public static final String PULSANTE_OK = "PULSANTE_OK";
    public static final String PULSANTE_ANNULLA = "PULSANTE_ANNULLA";
    public static final String PULSANTE_NUOVA_PRENOTAZONE = "PULSANTE_NUOVA_PRENOTAZONE";
    public static final String PULSANTE_SALVA_PRENOTAZIONE = "PULSANTE_SALVA_PRENOTAZIONE";

    private FinestraConGerarchia win;

    public BottonBarListener(FinestraConGerarchia win) {
        this.win = win;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        JButton button = (JButton)e.getSource();

        System.out.println("Pulsante premuto "+button.getText());

        /* SBAGLIATO !!!
        if("OK".equals(button.getText()))
            System.out.println("Valore del campo di testo: "+win.getTesto());
         */

        String command = e.getActionCommand();
        if(PULSANTE_OK.equals(command))
            System.out.println("Valore del campo di testo: "+win.getTesto());
        else if(PULSANTE_NUOVA_PRENOTAZONE.equals(command)) {
            win.mostraPannelloNuovaPrenotazione();
        }
        else if(PULSANTE_SALVA_PRENOTAZIONE.equals(command)) {
            Prenotazione nuova = salvaPrenotazione();
        }
    }

    private Prenotazione salvaPrenotazione() {
        Cliente clienteLoggato = (Cliente)Session.getInstance().ottieni(Session.UTENTE_LOGGATO);
        Stazione partenza = (Stazione)win.partenza.getSelectedItem();
        Stazione arrivo = (Stazione)win.arrivo.getSelectedItem();
        Localita posto = (Localita)win.localita.getSelectedItem();
        Modello modello = (Modello)win.modello.getSelectedItem();
        Mezzo mezzo = new Mezzo();
        int postiOccupati = Integer.parseInt(win.numPostiOccupati.getText());

        //TODO rimuovere i riferimenti ai DAO sostituendo con metodi di business
        Prenotazione p = new Prenotazione();

        p.setDataInizio(DateUtil.dateTimeFromString("2019-12-03 09:00:00")); //sistemare
        p.setDataFine(DateUtil.dateTimeFromString("2019-12-06 19:00:00"));   //sistemare
        p.setData(new Date());
        p.setCliente(clienteLoggato);
        p.setNumPostiOccupati(postiOccupati);
        p.setLocalita(posto);
        p.setPartenza(partenza);
        p.setArrivo(arrivo);
        if(PrenotazioneBusiness.getInstance().disponibilitaMezzo(modello)){
            mezzo = PrenotazioneBusiness.getInstance().returnOneMezzo(modello);
        }
        //TODO creare un else
        /*IMezzoDAO mDao = new MezzoDAO();
        p.setMezzo(mDao.findById(2));
         */
        p.setMezzo(mezzo);
        System.out.println("BLATTE1");
        PrenotazioneBusiness.getInstance().inviaPrenotazione(p); // <----problema
        System.out.println("BLATTE2");

        return p;
    }
}
