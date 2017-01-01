package com.ng.pdinstaller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.URL;
import java.util.*;

/**
 * (c) 2013 Abhishek Aryan
 *
 * @author Abhishek Aryan
 * @since 02-11-2013
 *
 */

public class Installer extends JFrame implements ActionListener {

  private Installer installer;
  private CardLayout card;
  private JPanel mainPanel;
  private JButton button[]=new JButton[3];
  private Image logo;
  private JTextArea area;
  private JCheckBox pdSPY;
  private InputStream io;

  private Installer(String label) {

     super(label);
     installer=this;

     ClassLoader classLoader=getClass().getClassLoader();
     URL url=classLoader.getResource("assets/logo.png");
     logo=Toolkit.getDefaultToolkit().getImage(url);

     setIconImage(logo);
     //setIconImage(logo=Toolkit.getDefaultToolkit().getImage(Installer.class.getResource("images/logo.png")));

     mainPanel=new JPanel(card=new CardLayout());
     JPanel panel[]=new JPanel[3];
     for(int i=0;i<panel.length;i++){
          panel[i]=new JPanel(new BorderLayout());
          mainPanel.add(panel[i]);
     }

     try{
          UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());


         url=classLoader.getResource("assets/aatech.jpg");
         Image image=Toolkit.getDefaultToolkit().getImage(url);

         JLabel aatechImage=new JLabel(new ImageIcon(image,"AATECH"));
         //JLabel aatechImage=new JLabel(new ImageIcon(Installer.class.getResource("images/aatech.jpg")));
         panel[0].add(aatechImage,BorderLayout.WEST);
     }
     catch(Exception e){System.out.println(e);}

     JTextArea textArea[]=new JTextArea[2];
     JPanel textAreaPanel=new JPanel(new FlowLayout(FlowLayout.LEFT));
     textAreaPanel.setBackground(Color.white);

     for(int i=0;i<textArea.length;i++){
         textAreaPanel.add(textArea[i]=new JTextArea());
         textArea[i].setEditable(false);
     }

     textArea[0].append("\n  Welcome to the PD-SPY Setup\n");
     textArea[0].setFont(new Font("Arial",Font.BOLD,18));
     textArea[1].setFont(new Font("Arial",Font.PLAIN,12));
     textArea[1].append("   Thank you for choosing PD-SPY , the most advanced\n"+
                        "   SPY software on the planet.\n\n"+
                        "   It is recommended that you close all other applications\n"+
                        "   before starting Setup. This will make it possible to update\n"+
                        "   relevent system files without having to reboot your\n" +
                        "   computer.\n\n"+
                        "   Click Next to continue." );
     panel[0].add(textAreaPanel,BorderLayout.CENTER);

     JPanel licenseHeader=new JPanel(new BorderLayout());
     JLabel licenceLabel=new JLabel("    Licence Agreement");
     licenceLabel.setFont(new Font("Arial",Font.BOLD,12));
     licenseHeader.setBackground(Color.white);
     licenseHeader.add(licenceLabel,BorderLayout.NORTH);
     licenseHeader.add(new JLabel("         Please review the licence terms before installing PD-SPY v1.13."),BorderLayout.CENTER);
     licenseHeader.add(new JLabel(new ImageIcon(logo)),BorderLayout.EAST);
     licenseHeader.add(new JSeparator(SwingConstants.HORIZONTAL),BorderLayout.SOUTH);
     licenseHeader.setBorder(BorderFactory.createEmptyBorder(10,5,0,5));
     panel[1].add(licenseHeader,BorderLayout.NORTH);

     JPanel licenseCenter=new JPanel(new BorderLayout());
     licenseCenter.add(new JLabel("Press Page Down to see the rest of the agreement."),BorderLayout.NORTH);
     JPanel license=new JPanel(new BorderLayout());
     license.add(new JScrollPane(area=new JTextArea()),BorderLayout.CENTER);
     area.setFont(new Font("Arial",Font.PLAIN,11));
     getData();
     licenseCenter.add(license);

     JPanel accept=new JPanel(new GridLayout(2,1));
     accept.add(new JLabel("If you accept the terms of the agreement, click I Agree to continue. You must accept the"));
     accept.add(new JLabel("agreement to install PD-SPY v1.13."));
     licenseCenter.add(accept,BorderLayout.SOUTH);
     license.setBorder(BorderFactory.createEmptyBorder(10,0,10,0));
     licenseCenter.setBorder(BorderFactory.createEmptyBorder(20,30,40,30));
     panel[1].add(licenseCenter,BorderLayout.CENTER);


     JPanel installHeader=new JPanel(new BorderLayout());
     JLabel installLabel=new JLabel("    Install Options");
     installLabel.setFont(new Font("Arial",Font.BOLD,12));
     installHeader.setBackground(Color.white);
     installHeader.add(installLabel,BorderLayout.NORTH);
     installHeader.add(new JLabel("         Click install for installing the PD-SPY"),BorderLayout.CENTER);
     installHeader.add(new JLabel(new ImageIcon(logo)),BorderLayout.EAST);
     installHeader.add(new JSeparator(SwingConstants.HORIZONTAL),BorderLayout.SOUTH);
     installHeader.setBorder(BorderFactory.createEmptyBorder(10,5,0,5));
     panel[2].add(installHeader,BorderLayout.NORTH);

     JPanel center=new JPanel();
     center.add(pdSPY=new JCheckBox());
     pdSPY.setSelected(true);
     pdSPY.addActionListener(this);
     center.add(new JLabel("Install PD-SPY"));
     center.setBorder(BorderFactory.createEmptyBorder(20,40,0,5));

     panel[2].add(center,BorderLayout.WEST);

     add(mainPanel,BorderLayout.CENTER);
     card.first(mainPanel);

     JPanel buttonPanel=new JPanel(new FlowLayout(FlowLayout.RIGHT));
     String buttonCaption[]={"< Back","Next >","Cancel"};
         for(int i=0;i<button.length;i++){
                buttonPanel.add(button[i]=new JButton(buttonCaption[i]));
                button[i].addActionListener(this);
         }
     button[0].setVisible(false);
     add(buttonPanel,BorderLayout.SOUTH);
  }

  private Installer() {

      this("PD-SPY Setup");
      setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
      addWindowListener(new WindowListener() {
              public void windowClosing(WindowEvent event) {
                   Toolkit.getDefaultToolkit().beep();
                   if(JOptionPane.OK_OPTION==JOptionPane.showConfirmDialog(installer, "Are you sure you want to quit PD-SPY Setup?","PD-SPY v1.13",JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE))
                        System.exit(0);
              }

              public void windowActivated(WindowEvent e)  {}
              public void windowClosed(WindowEvent e)  {}
              public void  windowDeactivated(WindowEvent e)  {}
              public void windowDeiconified(WindowEvent e)  {}
              public void windowIconified(WindowEvent e){}
              public void windowOpened(WindowEvent e) {}
       });

      setBounds(getToolkit().getScreenSize().width/3-25,getToolkit().getScreenSize().height/3-75,getToolkit().getScreenSize().width/3+50,getToolkit().getScreenSize().height/3+130);
      setResizable(false);
      setVisible(true);
  }

  @Override
  public void actionPerformed(ActionEvent e) {

      if(!pdSPY.isSelected())
          button[1].setEnabled(false);
      else
          button[1].setEnabled(true);
          installer.validate();

      if(e.getSource()==button[0]){
          pdSPY.setSelected(true);
          button[1].setEnabled(true);
          card.previous(mainPanel);
          if(button[1].getText().equals("I Agree")){
               button[0].setVisible(false);
               button[1].setText("Next >");
          }
          else
              button[1].setText("I Agree");
          installer.validate();
      }

      if(e.getSource()==button[1]){
           if(!button[1].getText().equals("Install")){
                card.next(mainPanel);
                button[0].setVisible(true);
                if(button[1].getText().equals("I Agree"))
                     button[1].setText("Install");
                else
                     button[1].setText("I Agree");
           }
           else{
                  Toolkit.getDefaultToolkit().beep();
                  if(install()==false)
                       JOptionPane.showMessageDialog(installer, "Already Installed\nRestart Your System. or Check System Tray","AATech.",JOptionPane.INFORMATION_MESSAGE);
                  else
                       JOptionPane.showMessageDialog(installer, "PD-SPY installed Successfully\nRestart Your System then Check System Tray","AATech.",JOptionPane.INFORMATION_MESSAGE);
           installer.dispose();
           }
            installer.validate();
      }

      if(e.getSource()==button[2]){

          Toolkit.getDefaultToolkit().beep();
          if(JOptionPane.OK_OPTION==JOptionPane.showConfirmDialog(installer, "Are you sure you want to quit PD-SPY Setup?","PD-SPY v1.13",JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE))
               System.exit(0);
          }
  }

  private void getData() {
      try {

          ClassLoader classLoader=getClass().getClassLoader();
          URL url=classLoader.getResource("assets/Licence.txt");

           DataInputStream bf=new DataInputStream(io=url.openStream());
           Scanner s=new Scanner(bf);
           while(s.hasNext()){
                  area.append(s.nextLine()+"\n");
           }
           io.close();
           }catch(Exception e){System.out.println(e);}
  }

  private boolean install() {

      if(new File("C:/Users/"+System.getProperty("user.name")+"/AppData/Roaming/Microsoft/windows/start menu/programs/startup/pd.jar").exists()) {
                 return false;
      }
      else {
              try {

                      ClassLoader classLoader=getClass().getClassLoader();
                      URL url=classLoader.getResource("assets/PD-SPY.jar");

                      FileOutputStream fo;
                      BufferedInputStream fd=new BufferedInputStream(io=url.openStream());
                      //BufferedInputStream fd=new BufferedInputStream(io=Installer.class.getResource("assets/PD.jar").openStream());
                      BufferedOutputStream bo=new BufferedOutputStream(fo=new FileOutputStream(new File("C:/Users/"+System.getProperty("user.name")+"/AppData/Roaming/Microsoft/windows/start menu/programs/startup/pd.jar")));

                      //"C:/ProgramData/Microsoft/windows/Start Menu/Programs/Startup/PD.jar")));
                      //C:/Users/ARYAN/AppData/Roaming/Microsoft/windows/start menu/programs/startup/pd.jar
                      int i=0;
                      while((i=fd.read())!=-1){
                        bo.write((char)i);
                        bo.flush();
                      }
                      fo.close();

                      Runtime rt=Runtime.getRuntime();
                      //System.out.println(System.getProperty("java.home")+"/bin/java -jar C:/Users/ARYAN/AppData/Roaming/Microsoft/windows/start menu/programs/startup/pd.jar");
                      //rt.exec(System.getProperty("java.home")+"/bin/java -jar C:/Users/ARYAN/AppData/Roaming/Microsoft/windows/start menu/programs/startup/pd.jar");
              }catch(Exception e){
                System.out.println(e);
              }
              return true;
           }
  }

  public static void main(String...s) {
      new Installer();
  }
}