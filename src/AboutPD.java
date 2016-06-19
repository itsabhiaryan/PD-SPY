import javax.swing.border.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
class AboutPD extends JDialog implements ActionListener {
    PDG pd;
    AboutPD about;

    AboutPD(PDG pd) {

        super(pd,"About PD-SPY.",true);
        this.pd=pd;
        about=this;
        getContentPane().add(new AboutTab(pd));

        JButton okButton=new JButton(" Close ");
        okButton.addActionListener(this);
        JPanel buttonPanel=new JPanel();
        buttonPanel.add(okButton);
        add(buttonPanel,BorderLayout.SOUTH);
        setSize(355,200);
        setResizable(false);
        setLocation(pd.getLocation().x+50,pd.getLocation().y+50);
        setVisible(true);
        setAlwaysOnTop(true);
    }

    public void actionPerformed(ActionEvent e)
{
about.dispose();
}
}

class AboutTab extends JPanel {
    PDG pd;
    AboutTab(PDG pd) {
        this.pd=pd;
        setLayout(new BorderLayout());
        JTabbedPane tabbedPane=new JTabbedPane(JTabbedPane.TOP);
        String tabs[]={"Product","Author","Configuration"};
        for(int i=0;i<tabs.length;i++) {
            tabbedPane.addTab(tabs[i],createPane(tabs[i]));
        }
        tabbedPane.setSelectedIndex(0);
        add(tabbedPane,BorderLayout.CENTER);
    }

    JPanel createPane(String s) {
        if(s.equals("Product")){

            JPanel p=new JPanel();
            p.setLayout(new BorderLayout());

            JLabel aboutLabel=new JLabel("<html><p>  </p><center><font size=3><p><b>PD-SPY</b> is a SPY Software Coded in JAVA"
                                        +"Technology, developed by <u>ABHISHEK ARY$N</u>.</p>"
                                        +"<p> <p>"
                                        +"<p>For more information, please visit <a href=http://www.aryan9234.blogspot.com title=aryan9234>www.aryan9234.typepad.com</a></p></font></center></html>",JLabel.CENTER);

            p.add(aboutLabel,BorderLayout.CENTER);
            return p;
        }
        else if(s.equals("Author")) {
                JPanel p=new JPanel(new BorderLayout());
                JLabel authorImage=new JLabel(new ImageIcon(AboutPD.class.getResource("images/ary1.jpg")));
                p.add(authorImage,BorderLayout.WEST);
                JLabel aboutAuthor=new JLabel("<html><font size=3><center><b>ABHISHEK ARYAN</b></center>"
                                                              +"<ul><li>Specification : JAVA</li>"
                                                              +"<li>Qualification : B.tech (CSE)</li>"
                                                              + "<li>Email : aryan9234@gmail.com</li>"
                                                              +"<li>Contact No.: +918427440232</li>"
                                                              +"<li>Website : aryan9234.hpage.com </li></ul></font>"
                                                              +"</html>");
                p.add(aboutAuthor,BorderLayout.CENTER);
                return p;
        }
        else{
              JPanel p=new JPanel(new BorderLayout());
              JTextArea detailArea=new JTextArea();
              detailArea.append("OS : "+System.getProperty("os.name")+"\n");
              detailArea.append("OS Arch. : "+System.getProperty("os.arch")+"\n");
              detailArea.append("OS Version : "+System.getProperty("os.version")+"\n");
              detailArea.append("User : "+System.getProperty("user.name")+"\n");
              detailArea.append("Java Vendor : "+System.getProperty("java.vendor")+"\n");
              detailArea.append("Vendor link : "+System.getProperty("java.vendor.url")+"\n");
              detailArea.append("Java Version : "+System.getProperty("java.version")+"\n");
              detailArea.append("Installed Dir. : "+System.getProperty("java.home")+"\n");

             detailArea.setCaretPosition(0);
             detailArea.setEditable(false);
             JScrollPane systemdetail=new JScrollPane(detailArea);
             p.add(systemdetail,BorderLayout.CENTER);

            return p;
        }
    }
}