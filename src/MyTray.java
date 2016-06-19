import java.awt.*;
import java.awt.event.*;
class MyTray extends MouseAdapter implements ActionListener {

    PDG pdg;
    MenuItem menuItem[]=new MenuItem[7];
    TrayIcon trayIcon=null;

    MyTray(PDG pdg) {

        this.pdg=pdg;
        if(SystemTray.isSupported()){
            SystemTray tray=SystemTray.getSystemTray();
            PopupMenu popup=new PopupMenu();
            String menuItemCaption[]={"PDSpy","C.","M.","D.","W.","Stop","Exit"};
            for(int i=0;i<menuItem.length;i++){
                popup.add(menuItem[i]=new MenuItem(menuItemCaption[i]));
                menuItem[i].addActionListener(this);
            }
            trayIcon=new TrayIcon(Toolkit.getDefaultToolkit().getImage(MyTray.class.getResource("images/ibi16.png")),"PDSPY.",popup);
            trayIcon.addMouseListener(this);
            try{
                tray.add(trayIcon);
            }
            catch(AWTException e){
                System.out.println(e);
            }
        }
        else
            System.out.println("System tray is not Supported");
    }

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

    public void mouseClicked(MouseEvent e) {

        trayIcon.displayMessage("PDSPY.","Current Operation "+pdg.action, TrayIcon.MessageType.INFO);
    }
}