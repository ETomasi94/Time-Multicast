/*ASSIGNMENT NUMERO 9 LABORATORIO DI RETI A.A 2019-2020
Nome Assignment : TimeMulticastServer (Lato Server)

Autore : Enrico Tomasi
Numero Matricola : 503527

OVERVIEW : Implementazione di un time server che, tramite il protocollo UDP,
invia ad un gruppo multicast la data e l'ora correnti.
*/
package timemulticastserver;

import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Date;
/*
    @CLASS MainClass
    @OVERVIEW Classe che implementa il ciclo di vita del server.
*/
public class MainClass 
{    
    private static final int PORT = 8500;
    /*
     Main DateGroupIP
    
     DateGroupIP Indirizzo IP del gruppo multicast.
    */
    public static void main(String[] args) 
    {
        try 
        {    
            DatagramSocket Socket = new DatagramSocket();
            
            /*L'indirizzo IP del gruppo è preso da riga di comando, è stato
            scelto l'indirizzo 230.250.0.1 perché è compreso tra 224.0.0.0 to 239.255.255.255
            ed è quindi un indirizzo valido*/
            InetAddress GroupAddress = InetAddress.getByName(args[0]);
                
            while(true)
            {
                /*----FORMATTAZIONE DI DATA ED ORA D'INVIO----*/
                SimpleDateFormat Format = new SimpleDateFormat("'INVIATO GIORNO 'dd-MM-yyyy 'alle' HH:mm:ss ");
                
                Date date = new Date(System.currentTimeMillis());
                
                byte[] CurrentDate = Format.format(date).getBytes();
                
                //INVIO DEL PACCHETTO
                DatagramPacket Packet = new DatagramPacket(CurrentDate,CurrentDate.length,GroupAddress,PORT);
                
                Socket.send(Packet);
                
                //INTERVALLO DI DUE SECONDI TRA UN INVIO E L'ALTRO
                Thread.sleep(2000);
            }
        } 
        catch (IOException ex) 
        {
            System.out.println("ERRORE: Errore di I/O relativo al socket");
        } 
        catch (InterruptedException ex) 
        {
            System.out.println("ERRORE: Interruzione avvenuta nell'intervallo di tempo tra un invio ed il successivo\n");
        }
    }
    
}
