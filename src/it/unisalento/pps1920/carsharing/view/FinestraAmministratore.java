package it.unisalento.pps1920.carsharing.view;

import it.unisalento.pps1920.carsharing.business.ControlloPrenotazioniAdminBusiness;
import it.unisalento.pps1920.carsharing.business.PrenotazioneBusiness;
import it.unisalento.pps1920.carsharing.dao.mysql.PrenotazioneDAO;
import it.unisalento.pps1920.carsharing.model.Modello;
import it.unisalento.pps1920.carsharing.model.Prenotazione;
import it.unisalento.pps1920.carsharing.model.Stazione;
import it.unisalento.pps1920.carsharing.util.PdfHelper;
import it.unisalento.pps1920.carsharing.view.Listener.BottonAdminListener;
import it.unisalento.pps1920.carsharing.view.Listener.ErrorMessages.AllErrorMessages;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class FinestraAmministratore extends JFrame
{
    public JComboBox<Stazione> partenza = new JComboBox<Stazione>();
    public JComboBox<String> modello = new JComboBox<String>();
    public JComboBox<Modello> marca = new JComboBox<Modello>();
    public JPanel jp1 = new JPanel();
    public JPanel jp2 = new JPanel();
    public JPanel jp3 = new JPanel();
   public JTextField jt= new JTextField(20);
   public JLabel lab1= new JLabel("Scegli la  stazione: ");
    public JLabel lab2= new JLabel("Scegli la marca: ");
    public  JButton b1 = new JButton("Data");
    public JButton b2 = new JButton("Stazione");
    public JButton b3 = new JButton("Modello");
    public JButton b4 = new JButton("Marca");
    public  JButton b5 = new JButton("Centro Segnalazioni");
    public JButton b6 = new JButton("Menu'");
    public  JButton b7 = new JButton("Stampa PDF");
    public JButton b8 = new JButton("Stampa PDF");
    public JButton b9 = new JButton("Stampa PDF");
    public JButton b10 = new JButton("Stampa PDF");
    public JButton b11 = new JButton("Stampa PDF");
   public JLabel lab= new JLabel("Inserisci data nel formato: Anno-Mese-Giorno");
    Container c = new Container();
    ArrayList<Prenotazione> prenotazioni;
    ArrayList<Stazione> stazioni;
    ArrayList<Modello> mod;


    public BottonAdminListener listener;
    public FinestraAmministratore(int id,String nome)
    {

        super("AMMINISTRATORE : " + nome.toUpperCase());
        this.setSize(800,800);
        listener = new BottonAdminListener(this);
        b1.addActionListener(listener);
        b1.setActionCommand(BottonAdminListener.PULSANTE_DATA);
        b2.addActionListener(listener);
        b2.setActionCommand(BottonAdminListener.PULSANTE_STAZIONE);
        b3.addActionListener(listener);
        b3.setActionCommand(BottonAdminListener.PULSANTE_MODELLO);
        b4.addActionListener(listener);
        b4.setActionCommand(BottonAdminListener.PULSANTE_MARCA);
        b5.addActionListener(listener);
        b5.setActionCommand(BottonAdminListener.PULSANTE_SEGNALAZIONI);
        b6.addActionListener(listener);
        b6.setActionCommand(BottonAdminListener.PULSANTE_MENU);
        b7.addActionListener(listener);
        b7.setActionCommand(BottonAdminListener.PULSANTE_STAMPA_PDF);
        b8.addActionListener(listener);
        b8.setActionCommand(BottonAdminListener.PULSANTE_STAMPA_PDF_DATA);
        b9.addActionListener(listener);
        b9.setActionCommand(BottonAdminListener.PULSANTE_STAMPA_PDF_STAZIONE);
        b10.addActionListener(listener);
        b10.setActionCommand(BottonAdminListener.PULSANTE_STAMPA_PDF_MODELLO);
        b11.addActionListener(listener);
        b11.setActionCommand(BottonAdminListener.PULSANTE_STAMPA_PDF_MARCA);


        c=this.getContentPane();
        c.setLayout(new BorderLayout());
        c.add(new JScrollPane(jp1), BorderLayout.CENTER);
        c.add(jp2,BorderLayout.NORTH);
        c.add(jp3,BorderLayout.SOUTH);
        jp1.setLayout(new BorderLayout());
        jp2.setLayout(new BorderLayout());
        jp3.setLayout(new FlowLayout());
        JPanel jp2_1= new JPanel();
        JPanel jp2_2= new JPanel();
        jp2_1.setLayout(new FlowLayout());
        jp2_2.setLayout(new FlowLayout());
        jp2.add(jp2_1,BorderLayout.NORTH);
        jp2.add(jp2_2,BorderLayout.SOUTH);
        JLabel txx= new JLabel("Filtra per: ");
        jp2_2.add(txx);
        jp2_2.add(b1);
        jp2_2.add(b2);
        jp2_2.add(b3);
        jp2_2.add(b4);
        jp2.add(b7);
       jp3.add(b5);
        JLabel bb= new JLabel("  ");
        jp3.add(bb);
       jp3.add(b6);
        jp3.setBackground(Color.RED);
        JLabel tx= new JLabel("<<<TABELLA PRENOTAZIONI>>>");
        jp2_1.add(tx);
        setupPannelloPrenotazioni(id);
        Dimension screenSize = Toolkit.getDefaultToolkit ( ).getScreenSize ( );
        setLocation ( ( screenSize.width / 2 ) - ( this.getWidth ( ) / 2 ), (screenSize.height / 2 ) - ( this.getHeight ( ) / 2 ) );
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }


    public void setupPannelloPrenotazioni(int id)
    {
        ArrayList<Prenotazione> prenotazioni = new PrenotazioneDAO().findAll();
        TablePrenotazioniOperatore tmp = new TablePrenotazioniOperatore(prenotazioni);
        JTable tabellaPrenotazioni = new JTable(tmp);
        JTable intestazione = new JTable(1,5);
        intestazione.setValueAt("ID PRENOTAZIONE",0,0);
        intestazione.setValueAt("DATA",0,1);
        intestazione.setValueAt("DATA/ORA INZIO",0,2);
        intestazione.setValueAt("DATA/ORA FINE",0,3);
        intestazione.setValueAt("STATO",0,4);
        jp1.add(intestazione, BorderLayout.NORTH);
        jp1.add(tabellaPrenotazioni,BorderLayout.CENTER);
    }


    public void mostraPannelloPrenotazioniPerData()
    {
         JButton j= new JButton("Cerca");
        //1. eliminare quello che c'è nell'area centrale
        BorderLayout al = (BorderLayout) this.getContentPane().getLayout();
        this.getContentPane().remove(al.getLayoutComponent(BorderLayout.CENTER));
        this.getContentPane().remove(al.getLayoutComponent(BorderLayout.NORTH));

        //2. inserire pannello della funzionalita specifica
        JPanel pan = new JPanel(new BorderLayout());
         JPanel pan1 = new JPanel(new BorderLayout());
        this.getContentPane().add(pan, BorderLayout.CENTER);
        this.getContentPane().add(pan1, BorderLayout.NORTH);
        pan.setLayout(new FlowLayout());
        pan1.setLayout(new FlowLayout());

        j.addActionListener(listener);
        j.setActionCommand(BottonAdminListener.PULSANTE_CERCA);
        pan1.add(lab);
        pan.add(jt);
        pan.add(j);

        //3. refresh della UI
        repaint();
        revalidate();
    }

    public void showPrenotazioniPerData(String data)
    {

        prenotazioni = new ArrayList<Prenotazione>();
        ControlloPrenotazioniAdminBusiness ad= new ControlloPrenotazioniAdminBusiness();
        prenotazioni=ad.checkPrenotazioniFromDate(data);
        if(prenotazioni==null)
        {
            int tipo=7;
            AllErrorMessages u= new AllErrorMessages(tipo);
            menu();

        }
        else
        {
            BorderLayout bl = (BorderLayout) this.getContentPane().getLayout();
            this.getContentPane().remove(bl.getLayoutComponent(BorderLayout.CENTER));
            this.getContentPane().remove(bl.getLayoutComponent(BorderLayout.NORTH));
            JPanel fp1 = new JPanel(new BorderLayout());
            JPanel fp2 = new JPanel(new BorderLayout());
            this.getContentPane().add(fp1, BorderLayout.CENTER);
            this.getContentPane().add(fp2, BorderLayout.NORTH);


            JPanel jp2_1= new JPanel();
            jp2_1.setLayout(new BorderLayout());
            fp2.add(jp2_1,BorderLayout.NORTH);
            jp2_1.add(b8,BorderLayout.NORTH);


            TablePrenotazioniOperatore   tmp = new TablePrenotazioniOperatore(prenotazioni);

            JTable tabellaPrenotazioni = new JTable(tmp);
            JTable intestazione = new JTable(1,5);
            intestazione.setValueAt("ID PRENOTAZIONE",0,0);
            intestazione.setValueAt("DATA",0,1);
            intestazione.setValueAt("DATA/ORA INZIO",0,2);
            intestazione.setValueAt("DATA/ORA FINE",0,3);
            intestazione.setValueAt("STATO",0,4);
            jp2_1.add(intestazione, BorderLayout.SOUTH);
            fp1.add(tabellaPrenotazioni,BorderLayout.CENTER);

            repaint();
            revalidate();
        }

    }

    public void menu()
    {

        BorderLayout cl = (BorderLayout) this.getContentPane().getLayout();
        this.getContentPane().remove(cl.getLayoutComponent(BorderLayout.CENTER));
        this.getContentPane().remove(cl.getLayoutComponent(BorderLayout.NORTH));

        this.getContentPane().add(jp1, BorderLayout.CENTER);
        this.getContentPane().add(jp2, BorderLayout.NORTH);
        this.setSize(800,800);
        repaint();
        revalidate();
        Dimension screenSize = Toolkit.getDefaultToolkit ( ).getScreenSize ( );
        setLocation ( ( screenSize.width / 2 ) - ( this.getWidth ( ) / 2 ), (screenSize.height / 2 ) - ( this.getHeight ( ) / 2 ) );
    }

    public void mostraPannelloPrenotazioniPerStazione()
    {
        JButton find= new JButton("Cerca");
        find.addActionListener(listener);
        find.setActionCommand(BottonAdminListener.PULSANTE_CERCA_STAZ);
        BorderLayout dl = (BorderLayout) this.getContentPane().getLayout();
        this.getContentPane().remove(dl.getLayoutComponent(BorderLayout.CENTER));
        this.getContentPane().remove(dl.getLayoutComponent(BorderLayout.NORTH));
        this.setSize(250,200);
        JPanel pp1 = new JPanel(new FlowLayout());
        JPanel pp2 = new JPanel(new FlowLayout());
        this.getContentPane().add(pp1, BorderLayout.CENTER);
        this.getContentPane().add(pp2, BorderLayout.NORTH);
        partenza.removeAllItems();
        stazioni = PrenotazioneBusiness.getInstance().getStazioni();
        for(Stazione s : stazioni)
            partenza.addItem(s);
        pp2.add(lab1);
        pp1.add(partenza);
        pp1.add(find);
        repaint();
        revalidate();
        Dimension screenSize = Toolkit.getDefaultToolkit ( ).getScreenSize ( );
        setLocation ( ( screenSize.width / 2 ) - ( this.getWidth ( ) / 2 ), (screenSize.height / 2 ) - ( this.getHeight ( ) / 2 ) );
    }

    public void showPannelloPrenotazioniPerStazione(Stazione staz)
    {
        BorderLayout el = (BorderLayout) this.getContentPane().getLayout();
        this.getContentPane().remove(el.getLayoutComponent(BorderLayout.CENTER));
        this.getContentPane().remove(el.getLayoutComponent(BorderLayout.NORTH));
        this.setSize(800,800);
        ControlloPrenotazioniAdminBusiness ad= new ControlloPrenotazioniAdminBusiness();
        ArrayList<Prenotazione> pre= new ArrayList<>();
        pre=ad.checkPrenotazioniFromStation(staz.getNome());

        if(pre==null)
        {
            int tipo=3;
            AllErrorMessages u= new AllErrorMessages(tipo);
            menu();

        }
        else
        {
            JPanel ff1 = new JPanel(new BorderLayout());
            JPanel ff2 = new JPanel(new BorderLayout());
            this.getContentPane().add(ff1, BorderLayout.CENTER);
            this.getContentPane().add(ff2, BorderLayout.NORTH);

            JPanel jp2_1= new JPanel();
            jp2_1.setLayout(new BorderLayout());
            ff2.add(jp2_1,BorderLayout.NORTH);
            jp2_1.add(b9,BorderLayout.NORTH);

            TablePrenotazioniOperatore   tmp = new TablePrenotazioniOperatore(pre);
            JTable tabellaPrenotazioni = new JTable(tmp);
            JTable intestazione = new JTable(1,5);
            intestazione.setValueAt("ID PRENOTAZIONE",0,0);
            intestazione.setValueAt("DATA",0,1);
            intestazione.setValueAt("DATA/ORA INZIO",0,2);
            intestazione.setValueAt("DATA/ORA FINE",0,3);
            intestazione.setValueAt("STATO",0,4);
            jp2_1.add(intestazione, BorderLayout.SOUTH);
            ff1.add(tabellaPrenotazioni,BorderLayout.CENTER);
            Dimension screenSize = Toolkit.getDefaultToolkit ( ).getScreenSize ( );
            setLocation ( ( screenSize.width / 2 ) - ( this.getWidth ( ) / 2 ), (screenSize.height / 2 ) - ( this.getHeight ( ) / 2 ) );
            setVisible(true);
        }

    }

    public void mostraPannelloPrenotazioniPerModello()
    {
        modello.removeAllItems();
        JLabel labb= new JLabel("Scegli tipologia veicolo: ");
        JButton find= new JButton("Cerca");
        find.addActionListener(listener);
        find.setActionCommand(BottonAdminListener.PULSANTE_CERCA_MODELLO);

        BorderLayout fl = (BorderLayout) this.getContentPane().getLayout();
        this.getContentPane().remove(fl.getLayoutComponent(BorderLayout.CENTER));
        this.getContentPane().remove(fl.getLayoutComponent(BorderLayout.NORTH));
        this.setSize(300,200);

        JPanel ppp1 = new JPanel(new FlowLayout());
        JPanel ppp2 = new JPanel(new FlowLayout());
        this.getContentPane().add(ppp1, BorderLayout.CENTER);
        this.getContentPane().add(ppp2, BorderLayout.NORTH);

        String c="CAMPER";
        String a="AUTO";
        String cc="CAMION";
        String f="FURGONE";
        modello.addItem(a);
        modello.addItem(c);
        modello.addItem(cc);
        modello.addItem(f);
        ppp2.add(labb);
        ppp1.add(modello);
        ppp1.add(find);
        repaint();
        revalidate();
        Dimension screenSize = Toolkit.getDefaultToolkit ( ).getScreenSize ( );
        setLocation ( ( screenSize.width / 2 ) - ( this.getWidth ( ) / 2 ), (screenSize.height / 2 ) - ( this.getHeight ( ) / 2 ) );
        setVisible(true);

    }

    public void showPannelloPrenotazioniPerModello(String mod)
    {
        ArrayList<Prenotazione> pre= new ArrayList<>();
        ControlloPrenotazioniAdminBusiness con= new ControlloPrenotazioniAdminBusiness();
        pre=con.checkPrenotazioniFromModel(mod);

        if(pre==null)
        {
            int tipo=2;
            AllErrorMessages u= new AllErrorMessages(tipo);
            menu();

        }
        else
        {
            BorderLayout fl = (BorderLayout) this.getContentPane().getLayout();
            this.getContentPane().remove(fl.getLayoutComponent(BorderLayout.CENTER));
            this.getContentPane().remove(fl.getLayoutComponent(BorderLayout.NORTH));
            this.setSize(800,800);

            JPanel fg1 = new JPanel(new BorderLayout());
            JPanel fg2 = new JPanel(new BorderLayout());
            this.getContentPane().add(fg1, BorderLayout.CENTER);
            this.getContentPane().add(fg2, BorderLayout.NORTH);

            JPanel jp2_1= new JPanel();
            jp2_1.setLayout(new BorderLayout());
            fg2.add(jp2_1,BorderLayout.NORTH);
            jp2_1.add(b10,BorderLayout.NORTH);

            TablePrenotazioniOperatore   tmp = new TablePrenotazioniOperatore(pre);
            JTable tabellaPrenotazioni = new JTable(tmp);
            JTable intestazione = new JTable(1,5);
            intestazione.setValueAt("ID PRENOTAZIONE",0,0);
            intestazione.setValueAt("DATA",0,1);
            intestazione.setValueAt("DATA/ORA INZIO",0,2);
            intestazione.setValueAt("DATA/ORA FINE",0,3);
            intestazione.setValueAt("STATO",0,4);
            fg2.add(intestazione, BorderLayout.SOUTH);
            fg1.add(tabellaPrenotazioni,BorderLayout.CENTER);
            Dimension screenSize = Toolkit.getDefaultToolkit ( ).getScreenSize ( );
            setLocation ( ( screenSize.width / 2 ) - ( this.getWidth ( ) / 2 ), (screenSize.height / 2 ) - ( this.getHeight ( ) / 2 ) );
            setVisible(true);
        }



    }

    public void mostraPannelloPrenotazionePerMarca()
    {
        JButton find= new JButton("Cerca");
        find.addActionListener(listener);
        find.setActionCommand(BottonAdminListener.PULSANTE_CERCA_MARCA);
        BorderLayout hl = (BorderLayout) this.getContentPane().getLayout();
        this.getContentPane().remove(hl.getLayoutComponent(BorderLayout.CENTER));
        this.getContentPane().remove(hl.getLayoutComponent(BorderLayout.NORTH));
        this.setSize(250,200);
        JPanel pp1 = new JPanel(new FlowLayout());
        JPanel pp2 = new JPanel(new FlowLayout());
        this.getContentPane().add(pp1, BorderLayout.CENTER);
        this.getContentPane().add(pp2, BorderLayout.NORTH);
        marca.removeAllItems();
        mod = PrenotazioneBusiness.getInstance().getModelli();
        for(Modello m : mod)
            marca.addItem(m);
        pp2.add(lab2);
        pp1.add(marca);
        pp1.add(find);
        repaint();
        revalidate();
        Dimension screenSize = Toolkit.getDefaultToolkit ( ).getScreenSize ( );
        setLocation ( ( screenSize.width / 2 ) - ( this.getWidth ( ) / 2 ), (screenSize.height / 2 ) - ( this.getHeight ( ) / 2 ) );



    }


    public void showPannelloPrenotazionePerMarca(Modello mod)
    {
        BorderLayout il = (BorderLayout) this.getContentPane().getLayout();
        this.getContentPane().remove(il.getLayoutComponent(BorderLayout.CENTER));
        this.getContentPane().remove(il.getLayoutComponent(BorderLayout.NORTH));
        this.setSize(800,800);
        ControlloPrenotazioniAdminBusiness ad= new ControlloPrenotazioniAdminBusiness();
        ArrayList<Prenotazione> pre= new ArrayList<>();
        pre=ad.checkPrenotazioniFromBrand(mod.getNome());


        if(pre==null)
        {
            int tipo=6;
            AllErrorMessages u= new AllErrorMessages(tipo);
            menu();

        }
        else
        {
            JPanel kk1 = new JPanel(new BorderLayout());
            JPanel kk2 = new JPanel(new BorderLayout());
            this.getContentPane().add(kk1, BorderLayout.CENTER);
            this.getContentPane().add(kk2, BorderLayout.NORTH);

            JPanel jp2_1= new JPanel();
            jp2_1.setLayout(new BorderLayout());
            kk2.add(jp2_1,BorderLayout.NORTH);
            jp2_1.add(b11,BorderLayout.NORTH);

            TablePrenotazioniOperatore   tmp = new TablePrenotazioniOperatore(pre);
            JTable tabellaPrenotazioni = new JTable(tmp);
            JTable intestazione = new JTable(1,5);
            intestazione.setValueAt("ID PRENOTAZIONE",0,0);
            intestazione.setValueAt("DATA",0,1);
            intestazione.setValueAt("DATA/ORA INZIO",0,2);
            intestazione.setValueAt("DATA/ORA FINE",0,3);
            intestazione.setValueAt("STATO",0,4);
            kk2.add(intestazione, BorderLayout.SOUTH);
            kk1.add(tabellaPrenotazioni,BorderLayout.CENTER);
            Dimension screenSize = Toolkit.getDefaultToolkit ( ).getScreenSize ( );
            setLocation ( ( screenSize.width / 2 ) - ( this.getWidth ( ) / 2 ), (screenSize.height / 2 ) - ( this.getHeight ( ) / 2 ) );
            setVisible(true);
        }
    }

    public void stampaPdf()
    {
        ArrayList<Prenotazione> prenotazioni = new PrenotazioneDAO().findAll();
        ArrayList<String> testo = new ArrayList<String>();

        for (Prenotazione p : prenotazioni)
        {
            testo.add("Codice prenotazione: "+p.getId());
            testo.add("Data e ora prenotazione: "+p.getData());
            testo.add("Data e ora di inizio noleggio: "+p.getDataInizio());
            testo.add("Data e ora di fine noleggio: "+p.getDataFine());
            testo.add("Stato: CONFERMATA");
            testo.add("---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        }
        PdfHelper.getInstance().creaPdf(testo);
    }

    public void stampaPdfDataFiltering(String data)
    {
        ArrayList<Prenotazione> prenotazioni = new ControlloPrenotazioniAdminBusiness().checkPrenotazioniFromDate(data);
        ArrayList<String> testo = new ArrayList<String>();

        for (Prenotazione p : prenotazioni)
        {
            testo.add("Codice prenotazione: "+p.getId());
            testo.add("Data e ora prenotazione: "+p.getData());
            testo.add("Data e ora di inizio noleggio: "+p.getDataInizio());
            testo.add("Data e ora di fine noleggio: "+p.getDataFine());
            testo.add("Stato: CONFERMATA");
            testo.add("---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        }
        PdfHelper.getInstance().creaPdf(testo);
    }

    public void stampaPdfStationFiltering(Stazione staz)
    {
        ArrayList<Prenotazione> prenotazioni = new ControlloPrenotazioniAdminBusiness().checkPrenotazioniFromStation(staz.getNome());
        ArrayList<String> testo3 = new ArrayList<String>();

        for (Prenotazione p : prenotazioni)
        {
            testo3.add("Codice prenotazione: "+p.getId());
            testo3.add("Data e ora prenotazione: "+p.getData());
            testo3.add("Data e ora di inizio noleggio: "+p.getDataInizio());
            testo3.add("Data e ora di fine noleggio: "+p.getDataFine());
            testo3.add("Stato: CONFERMATA");
            testo3.add("---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        }
        PdfHelper.getInstance().creaPdf(testo3);
    }

    public void stampaPdfModelFiltering(String mod)
    {
        ArrayList<Prenotazione> prenotazioni = new ControlloPrenotazioniAdminBusiness().checkPrenotazioniFromModel(mod);
        ArrayList<String> testo1 = new ArrayList<String>();

        for (Prenotazione p : prenotazioni)
        {
            testo1.add("Codice prenotazione: "+p.getId());
            testo1.add("Data e ora prenotazione: "+p.getData());
            testo1.add("Data e ora di inizio noleggio: "+p.getDataInizio());
            testo1.add("Data e ora di fine noleggio: "+p.getDataFine());
            testo1.add("Stato: CONFERMATA");
            testo1.add("---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        }
        PdfHelper.getInstance().creaPdf(testo1);
    }

    public void stampaPdfBrandFiltering(Modello mod)
    {
        ArrayList<Prenotazione> prenotazioni = new ControlloPrenotazioniAdminBusiness().checkPrenotazioniFromBrand(mod.getNome());
        ArrayList<String> testo2 = new ArrayList<String>();

        for (Prenotazione p : prenotazioni)
        {
            testo2.add("Codice prenotazione: "+p.getId());
            testo2.add("Data e ora prenotazione: "+p.getData());
            testo2.add("Data e ora di inizio noleggio: "+p.getDataInizio());
            testo2.add("Data e ora di fine noleggio: "+p.getDataFine());
            testo2.add("Stato: CONFERMATA");
            testo2.add("---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        }
        PdfHelper.getInstance().creaPdf(testo2);
    }

}
