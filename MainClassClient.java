/*ASSIGNMENT NUMERO 9 LABORATORIO DI RETI A.A 2019-2020
Nome Assignment : TimeMulticastServer

Autore : Enrico Tomasi
Numero Matricola : 503527

OVERVIEW : Implementazione di un time client che, dopo essersi registrato ad un 
gruppo multicast UDP, riceve dieci messaggi contenenti la data e l'ora dell'invio.
*/
package timemulticastclient;

import java.io.*;
import java.net.*;
/*
    @CLASS MainClass
    @OVERVIEW Classe che implementa il ciclo di vita del client.
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
        //Specifichiamo la versione del protocollo IP che desideriamo utilizzare
        System.setProperty("java.net.preferIPv4Stack","true");
        try 
        {
            //Apertura di una multicast socket per il gruppo alla porta 8500
            MulticastSocket Socket = new MulticastSocket(PORT);
 
            //L'indirizzo IP del gruppo è preso da riga di comando
            InetAddress GroupAddress = InetAddress.getByName(args[0]);
            
            //Il client si unisce al gruppo
            Socket.joinGroup(GroupAddress);
    
            byte[] Buffer = new byte[1024];
            
            int i=0;
            
            while(i < 10)
            {
               DatagramPacket Packet = new DatagramPacket(Buffer,Buffer.length);
               
               //Ricezione del paccketto
               Socket.receive(Packet);
               
               String RCV = new String(Packet.getData());
               
               System.out.println(RCV);
               
               i++;
            }            
            
            //Una volta finito, il client lascia il gruppo e chiude la sua connessione
            Socket.leaveGroup(GroupAddress);
            Socket.close();
            
        } 
        catch (UnknownHostException ex) 
        {
            System.out.println("ERRORE: Indirizzo sconosciuto - "+args[0]);
        } 
        catch (SocketException ex) 
        {
            System.out.println("ERRORE: Errore di apertura socket");
        } 
        catch (IOException ex) 
        {
            System.out.println("ERRORE: Errore di I/O");
        }
        
        
    }
}