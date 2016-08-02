package com.nayragames.pdspy;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * (c) 2016 Abhishek Aryan
 *
 * @author Abhishek Aryan
 * @since 01-11-2014
 *
 */

public class AboutPD extends JDialog implements ActionListener {
    private PDG pd;
    private AboutPD about;

    public AboutPD(PDG pd) {

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

