package it.unisalento.pps1920.carsharing.view;

import it.unisalento.pps1920.carsharing.business.ControlloAutomezziAddettoBusiness;
import it.unisalento.pps1920.carsharing.business.ControlloStatoPrenotazioniBusiness;
import it.unisalento.pps1920.carsharing.dao.mysql.PrenotazioneDAO;
import it.unisalento.pps1920.carsharing.model.Accessorio;
import it.unisalento.pps1920.carsharing.model.Modello;
import it.unisalento.pps1920.carsharing.model.Prenotazione;
import it.unisalento.pps1920.carsharing.view.Listener.BottonAddettoListener;
import it.unisalento.pps1920.carsharing.view.Listener.BottonAdminListener;
import it.unisalento.pps1920.carsharing.view.Listener.ErrorMessages.AllErrorMessages;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class FinestraAddetto extends  JFrame
{

    int idAdd;
    int stato=0;
    JButton butt = new JButton("Visualizza Accessori Automezzo");
    JLabel lab = new JLabel("ID PRENOTAZIONE: ");
    JPanel jp1=new JPanel(new BorderLayout());
    JPanel jp2=new JPanel(new BorderLayout());
    JPanel jp3=new JPanel(new FlowLayout());
    JPanel jp2_1= new JPanel(new FlowLayout());
    JPanel jp2_2= new JPanel(new BorderLayout());
    public JTextField jt = new JTextField(20);
    JButton b2= new JButton("Pannello Segnalazioni");
    BottonAddettoListener listener;
    public FinestraAddetto(int idAddetto, String nomeAddetto)
    {

        super("ADDETTO : "+nomeAddetto.toUpperCase());
        getIdAdd(idAddetto);
        listener = new BottonAddettoListener(this,idAddetto,nomeAddetto);
        setSize(800,800);
        Container c = new Container();
        c=this.getContentPane();
        c.add(jp1,BorderLayout.CENTER);
        c.add(jp2,BorderLayout.NORTH);
        c.add(jp3,BorderLayout.SOUTH);
        jp2.add(jp2_1,BorderLayout.NORTH);
        jp2.add(jp2_2,BorderLayout.SOUTH);
        jp3.add(b2);
        JLabel bb= new JLabel("  ");
        jp3.add(bb);
        jp3.setBackground(Color.yellow);

        butt.addActionListener(listener);
        butt.setActionCommand(BottonAddettoListener.PULSANTE_VISUALIZZA_ACCESSORI_AUTOMEZZO);
        jp2_1.add(lab);
        jp2_1.add(jt);
        jp2_1.add(butt);
        setupPannelloPrenotazioni();
        Dimension screenSize = Toolkit.getDefaultToolkit ( ).getScreenSize ( );
        setLocation ( ( screenSize.width / 2 ) - ( this.getWidth ( ) / 2 ), (screenSize.height / 2 ) - ( this.getHeight ( ) / 2 ) );
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

    }

    public void setupPannelloPrenotazioni()
    {
        ArrayList<String[]>prenotazioni= new ArrayList<String[]>();
        ControlloAutomezziAddettoBusiness cont = new ControlloAutomezziAddettoBusiness();
        prenotazioni=cont.checkPrenotazioni(idAdd);

        if(prenotazioni==null)
        {
            jp2_1.add(new JLabel("Non ci sono automezzi da preparare in questo momento!"));
            menu();
            jp2_1.remove(butt);
            jp2_1.remove(lab);
            jp2_1.remove(jt);
            AllErrorMessages al= new AllErrorMessages(3);

        }
        else
        {
            stato=1;
            TablePrenotazioniAddetto tmp = new TablePrenotazioniAddetto(prenotazioni);
            JTable tabellaPrenotazioni = new JTable(tmp);
            JTable intestazione = new JTable(1,4);
            intestazione.setValueAt("ID PRENOTAZIONE",0,0);
            intestazione.setValueAt("MARCA",0,1);
            intestazione.setValueAt("TIPOLOGIA",0,2);
            intestazione.setValueAt("DATA INIZIO NOLEGGIO",0,3);
            jp2_2.add(intestazione, BorderLayout.SOUTH);
            jp1.add(tabellaPrenotazioni,BorderLayout.CENTER);
        }

    }


    public void visualizzaAccessoriAutomezzo(int idPrenotazione)
    {
        int state=0;
        ControlloAutomezziAddettoBusiness cont = new ControlloAutomezziAddettoBusiness();
        ArrayList<String[]> st= new ArrayList<>();
        st=cont.checkPrenotazioniFilteredByIdStation(idAdd,idPrenotazione);

        if(st.get(0)[0].equals("-1"))
        {
            state=1;
            menu();
            AllErrorMessages msg= new AllErrorMessages(9);
        }

         if(st.get(0)[0].equals("-2"))
        {
            state=2;
            menu();
            AllErrorMessages msg= new AllErrorMessages(10);
        }

            ArrayList<Accessorio> acc= new ArrayList<>();
             acc=cont.getAccessoriFromIdPrenotazione(idPrenotazione);

             if(state==0)
        {
            BorderLayout al = (BorderLayout) this.getContentPane().getLayout();
            this.getContentPane().remove(al.getLayoutComponent(BorderLayout.CENTER));
            this.getContentPane().remove(al.getLayoutComponent(BorderLayout.NORTH));

            JPanel pan = new JPanel(new GridLayout(acc.size()+1,2));
            JPanel pan1 = new JPanel(new BorderLayout());
            this.getContentPane().add(pan, BorderLayout.CENTER);
            this.getContentPane().add(pan1, BorderLayout.NORTH);


            for(int c=0; c< acc.size() ; c++)
            {
                pan.add(new JLabel("Accessorio "+(c+1)));
                pan.add(new JLabel(acc.get(c).getNome()));
            }
            JButton butt1= new JButton("Veicolo preparato");
            JButton butt2= new JButton("Veicolo non ancora preparato");
            butt1.addActionListener(listener);
            butt1.setActionCommand(BottonAddettoListener.PULSANTE_PRONTO);
            butt2.addActionListener(listener);
            butt2.setActionCommand(BottonAddettoListener.PULSANTE_NON_PRONTO);
            pan.add(butt1);
            pan.add(butt2);
            butt1.setBackground(Color.green);
            butt2.setBackground(Color.red);
            if(acc.isEmpty())
            {
                pan.remove(butt1);
                pan.remove(butt2);
                pan.setLayout(new BorderLayout());
                JPanel pan2= new JPanel(new FlowLayout());
                JPanel pan3= new JPanel(new FlowLayout());
                JLabel labb= new JLabel("Non sono presenti accessori!");
                pan.add(pan2,BorderLayout.CENTER);
                pan.add(pan3,BorderLayout.SOUTH);
                pan3.add(butt1);
                pan3.add(butt2);
                pan2.add(labb);
                this.setSize(450,300);
            }

            else
            this.setSize(450,600);
            repaint();
            revalidate();
        }
        state=0;

    }

    public void menu()
    {
        if(stato==1)
        {
            BorderLayout cl = (BorderLayout) this.getContentPane().getLayout();
            this.getContentPane().remove(cl.getLayoutComponent(BorderLayout.CENTER));
            this.getContentPane().remove(cl.getLayoutComponent(BorderLayout.NORTH));

            this.getContentPane().add(jp1, BorderLayout.CENTER);
            this.getContentPane().add(jp2, BorderLayout.NORTH);
            this.setSize(800,800);
            setupPannelloPrenotazioni();
            repaint();
            revalidate();
            Dimension screenSize = Toolkit.getDefaultToolkit ( ).getScreenSize ( );
            setLocation ( ( screenSize.width / 2 ) - ( this.getWidth ( ) / 2 ), (screenSize.height / 2 ) - ( this.getHeight ( ) / 2 ) );
        }

    }


    void getIdAdd(int idAddetto)
    {
        this.idAdd=idAddetto;
    }


}
