package com.ng.pdspy;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;

/**
 * (c) 2013 Abhishek Aryan
 *
 * @author Abhishek Aryan
 * @since 01-11-2013
 *
 */

public class MyTray extends MouseAdapter implements ActionListener {

    private PDG pdg;
    private MenuItem menuItem[]=new MenuItem[7];
    private TrayIcon trayIcon=null;

    public MyTray(PDG pdg) {

        this.pdg=pdg;
        if(SystemTray.isSupported()){
            SystemTray tray=SystemTray.getSystemTray();
            PopupMenu popup=new PopupMenu();
            String menuItemCaption[]={"PDSpy","C.","M.","D.","W.","Stop","Exit"};
            for(int i=0;i<menuItem.length;i++){
                popup.add(menuItem[i]=new MenuItem(menuItemCaption[i]));
                menuItem[i].addActionListener(this);
            }


         /*   try{

             File pathToFile=new File("ibi16.png");
             image= ImageIO.read(pathToFile);

            }catch(IOException e){
                System.out.println("IOEXCEPTION");
                e.printStackTrace();
            }*/

            ClassLoader classLoader=getClass().getClassLoader();
            URL url=classLoader.getResource("assets/ibi16.png");
            Image image=Toolkit.getDefaultToolkit().getImage(url);

            //image=   new ImageIcon("/assets/ibi16.png").getImage();

            //Toolkit.getDefaultToolkit().getImage(MyTray.class.getResource("ibi16.png"))
            trayIcon=new TrayIcon(image,"PDSPY.",popup);
            trayIcon.addMouseListener(this);
            try{
                tray.add(trayIcon);
            }
            catch(AWTException e){
                System.out.println("AWT EXCE");
                System.out.println(e);
            }
        }
        else
            System.out.println("System tray is not Supported");
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource()==menuItem[0])
            pdg.setVisible(true);
        for(int i=1;i<6;i++){
            if(e.getSource()==menuItem[i])
                pdg.operation(menuItem[i].getLabel());
        }
        if(e.getSource()==menuItem[6]){
            pdg.setVisible(false);
            pdg.dispose();
            System.exit(0);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        trayIcon.displayMessage("PDSPY.","Current Operation "+pdg.action, TrayIcon.MessageType.INFO);
    }
}