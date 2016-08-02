package com.nayragames.pdspy;

import javax.swing.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Date;
import java.util.HashSet;

/**
 * (c) 2016 Abhishek Aryan
 *
 * @author Abhishek Aryan
 * @since 01-11-2014
 *
 */
public class NewDri extends Thread {

    String driveName ;
    HashSet<String> al;
    PrintStream p;
    MyPD mypd;
    NewDri(String label,MyPD mypd) {

        this.driveName=label;
        this.mypd=mypd;
        al=new HashSet<String>();
        start();
    }

    public void run() {

        long size=new File(driveName).getFreeSpace();
        log();
        p.println(new Date().toString());
        p.println("**************************");
        mypd.pdg.getLog();
        while(true) {

            try {

                if(new File(driveName).getFreeSpace()!=size){
                    if(new File(driveName).getFreeSpace()<size){
                        list(new File(driveName));
                        p.println("After File Insertation");
                        p.println(al);
                        mypd.pdg.getLog();
                    }
                    else
                        delete();
                size=new File(driveName).getFreeSpace();}
                }
                catch(NullPointerException e) {
                    JOptionPane.showMessageDialog(mypd.pdg,"Drive Removed","AATech.",JOptionPane.WARNING_MESSAGE);
                    break;
                }
        }
    }

    public void list(File drive) {

        String file[]=drive.list();
        for(int i=0;i<file.length;i++){
            if(new File(drive.getPath()+File.separator+file[i]).isDirectory())
                list(new File(drive.getPath()+File.separator+file[i]));
            else
            al.add(file[i]);
        }
    }

    public void log() {

        try{
                if(!new File("C://IBI").exists())
                    new File("C://IBI").mkdir();
                p=new PrintStream(new FileOutputStream("C://IBI/pdlog.txt",true));
            }catch(Exception e){}
    }

    public void delete() {

        al.clear();
        list(new File(driveName));
        p.println("After File Deletation");
        p.println(al);
        mypd.pdg.getLog();
    }
}