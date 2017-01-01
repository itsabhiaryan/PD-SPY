package com.ng.pdspy;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

/**
 * (c) 2013 Abhishek Aryan
 *
 * @author Abhishek Aryan
 * @since 01-11-2013
 *
 */
public class AboutTab extends JPanel {

    private PDG pd;

    public AboutTab(PDG pd) {
        this.pd = pd;
        setLayout(new BorderLayout());
        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        String tabs[] = {"Product", "Author", "Configuration"};
        for (int i = 0; i < tabs.length; i++) {
            tabbedPane.addTab(tabs[i], createPane(tabs[i]));
        }
        tabbedPane.setSelectedIndex(0);
        add(tabbedPane, BorderLayout.CENTER);
    }

    private JPanel createPane(String s) {
        if (s.equals("Product")) {

            JPanel p = new JPanel();
            p.setLayout(new BorderLayout());

            JLabel aboutLabel = new JLabel("<html><p>  </p><center><font size=3><p><b>PD-SPY</b> is a SPY Software Coded in JAVA"
                    + "Technology, developed by <u>ABHISHEK ARY$N</u>.</p>"
                    + "<p> <p>"
                    + "<p>For more information, please write at <a href=http://www.itsabhiaryan.wordpress.com title=itsabhiaryan>www.itsabhiaryan.wordpress.com</a></p></font></center></html>", JLabel.CENTER);

            p.add(aboutLabel, BorderLayout.CENTER);
            return p;
        } else if (s.equals("Author")) {
            JPanel p = new JPanel(new BorderLayout());

            /*Image image = null;
            try {

                File file = new File("assets/ary1.jpg");
                System.out.println(file.isFile()+"FILE");
                image = ImageIO.read(file);


            } catch (Exception e1) {

            }*/

            //JLabel authorImage=new JLabel(new ImageIcon(AboutPD.class.getResource("images/ary1.jpg")));

            ClassLoader classLoader=getClass().getClassLoader();
            URL url=classLoader.getResource("assets/"+"ary1.jpg");
            Image im=Toolkit.getDefaultToolkit().getImage(url);

            JLabel authorImage = new JLabel(new ImageIcon(im, "image"));
            p.add(authorImage, BorderLayout.WEST);
            JLabel aboutAuthor = new JLabel("<html><font size=3><center><b>ABHISHEK ARYAN</b></center>"
                    + "<ul><li>Specification : Nothing</li>"
                    + "<li>Qualification : Not yet</li>"
                    + "<li>Email : itsabhiaryan@gmail.com</li>"
                    + "<li>Contact No.: +918X2X44XXXX</li>"
                    + "<li>Website : itsabhiaryan.wordpress.com </li></ul></font>"
                    + "</html>");
            p.add(aboutAuthor, BorderLayout.CENTER);
            return p;
        } else {
            JPanel p = new JPanel(new BorderLayout());
            JTextArea detailArea = new JTextArea();
            detailArea.append("OS : " + System.getProperty("os.name") + "\n");
            detailArea.append("OS Arch. : " + System.getProperty("os.arch") + "\n");
            detailArea.append("OS Version : " + System.getProperty("os.version") + "\n");
            detailArea.append("User : " + System.getProperty("user.name") + "\n");
            detailArea.append("Java Vendor : " + System.getProperty("java.vendor") + "\n");
            detailArea.append("Vendor link : " + System.getProperty("java.vendor.url") + "\n");
            detailArea.append("Java Version : " + System.getProperty("java.version") + "\n");
            detailArea.append("Installed Dir. : " + System.getProperty("java.home") + "\n");

            detailArea.setCaretPosition(0);
            detailArea.setEditable(false);
            JScrollPane systemdetail = new JScrollPane(detailArea);
            p.add(systemdetail, BorderLayout.CENTER);

            return p;
        }
    }
}
