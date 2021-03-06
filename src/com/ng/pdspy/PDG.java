package com.ng.pdspy;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * (c) 2013 Abhishek Aryan
 *
 * @author Abhishek Aryan
 * @since 01-11-2013
 *
 */
public class PDG extends JFrame implements ActionListener,TreeSelectionListener {

    private PDG pd;
    private JLabel header;
    String action="W";
    private JMenuItem about,log;
    private ImageIcon image[]=new ImageIcon[7];
    private JMenuItem menuItem[][]=new JMenuItem[1][5];

    private JTree jt;
    private DefaultMutableTreeNode root=new DefaultMutableTreeNode("Record");
    private DefaultTreeModel tm=new DefaultTreeModel(root);
    private JSplitPane splitPane;
    private JTextArea ta=new JTextArea(12,3);
    private ArrayList<String> al=new ArrayList<String>();
    private ArrayList<String> al2=new ArrayList<String>();
    private JTable table=new JTable();
    private DefaultTableModel model=(DefaultTableModel)table.getModel();
    private MyPD pdt;

    public PDG() {

        super("PD-SPY.");
        this.pd=this;
        header=new JLabel("CURRENT OPERATION -'"+action+"'",JLabel.CENTER);
        header.setForeground(Color.blue);
        add(header,BorderLayout.NORTH);
        header.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));

        JPanel panel=new JPanel();
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
        panel.add(new JScrollPane(ta));
        panel.add(new JScrollPane(table));
        model.addColumn("List of File in PD After Last Operation");

        splitPane=new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,new JScrollPane(jt=new JTree()),panel);
        splitPane.setOneTouchExpandable(true);
        splitPane.setDividerLocation(130);
        jt.addTreeSelectionListener(this);

        try{
            String imageName[]={"copy16","cut16","delete16","sea","cancel16","add","ibi16"};

            for(int i=0;i<image.length;i++) {

                //  image[i]=new ImageIcon(PDG.class.getResource("images/"+imageName[i]+".png"));
               // File file=new File("assets/" + imageName[i] + ".png");
               // System.out.println(file.exists()+"File");

                ClassLoader classLoader=getClass().getClassLoader();
                URL url=classLoader.getResource("assets/"+imageName[i]+".png");
                Image im=Toolkit.getDefaultToolkit().getImage(url);
                image[i] = new ImageIcon(im,imageName[i]);
               // image[i] = new ImageIcon("assets/"+imageName[i] + ".png");
            }
            }
            catch(Exception e) {
                System.out.println("HERE"+e);
            }
        JMenuBar menuBar=new JMenuBar();
        JMenu menu[]=new JMenu[3];
        String menuCaption[]={"PD","Extra","Help"};
        int keyEvent[]={KeyEvent.VK_P,KeyEvent.VK_E,KeyEvent.VK_H,KeyEvent.VK_C,KeyEvent.VK_M,KeyEvent.VK_D,KeyEvent.VK_W,KeyEvent.VK_S};
        String menuItemCaption[][]={{"Copy","Mov","Del","Watch","Stop"},{"Log"},{"About"}};
        for(int i=0;i<menu.length;i++){
            menuBar.add(menu[i]=new JMenu(menuCaption[i]));
            menu[i].setMnemonic(keyEvent[i]);
        }
        for(int j=0;j<menuItem[0].length;j++){
            menu[0].add(menuItem[0][j]=new JMenuItem(menuItemCaption[0][j],image[j]));
            menuItem[0][j].addActionListener(this);
            menuItem[0][j].setAccelerator(KeyStroke.getKeyStroke(keyEvent[3+j],ActionEvent.CTRL_MASK));}
            menu[1].add(log=new JMenuItem(menuItemCaption[1][0],image[5]));
            log.addActionListener(this);
            menu[2].add(about=new JMenuItem(menuItemCaption[2][0],image[6]));
            about.addActionListener(this);
            about.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A,ActionEvent.CTRL_MASK));
            setJMenuBar(menuBar);
        try
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch(Exception e){
            System.out.println(e);
        }
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setBounds(getToolkit().getScreenSize().width/3-100,getToolkit().getScreenSize().height/3-50,getToolkit().getScreenSize().width/3+100,getToolkit().getScreenSize().height/3+50);
        setResizable(false);
        setVisible(true);
        pdt=new MyPD("W",pd);
        new MyTray(pd);
    }

    void getLog(){

        al.clear();
        al2.clear();
        if(root.getChildCount()>0){
            for(int i=root.getChildCount()-1;i>=0;i--)
                root.remove(i);
        }

        try{
            BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream("C://IBI/PdLog.txt")));
            String s1="";
            while(s1!=null){
                String date=s1;
                s1=br.readLine();
                if(s1!=null){
                    al2.add(s1);
                if(s1.equals("**************************")){
                        root.add(new DefaultMutableTreeNode(date));
                        al.add(date);}}
                }
        }catch(Exception e){
            System.out.println(e);
        }
        tm.reload(root);
        jt.setModel(tm);
        ta.setEditable(false);
        pd.add(splitPane,BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        for(int i=0;i<5;i++){
            if(e.getSource()==menuItem[0][i])
                operation(menuItem[0][i].getText());
        }
        if(e.getActionCommand().equals("About"))
            new AboutPD(pd);
        if(e.getActionCommand().equals("Log")){
            if(new File("C://IBI/pdlog.txt").exists()){
                getLog();
                pd.validate();
            }
            else
            JOptionPane.showMessageDialog(pd,"No Data Exist","AATech.",JOptionPane.INFORMATION_MESSAGE);
        }
    }

    void operation(String label) {

        char ch[]=label.toCharArray();
        pdt.set(ch[0]+"");
        set(ch[0]+"");
    }

    @Override
    public void valueChanged(TreeSelectionEvent e) {

        Object o[]=e.getPath().getPath();
        if(o.length==2){
            if(al.indexOf(o[1].toString())<al.size()-1)
                getData(al2.indexOf(o[1].toString()),al2.indexOf(al.get(al.indexOf(o[1].toString())+1)));
            else {
                getData(al2.indexOf(o[1].toString()),al2.size());
            }
        }
    }

    private void getData(int start,int end) {

        ta.setText(null);
        for(int i=model.getRowCount()-1;i>=0;i--)
            model.removeRow(i);
        for(int i=start;i<end-1;i++)
            ta.append(al2.get(i)+"\n");
        ta.setCaretPosition(0);

        if(end-start>2){
            char ch[]=al2.get(end-1).toCharArray();
            char ch1[]=new char[ch.length-2];
            for(int j=1;j<ch.length-1;j++)
                ch1[j-1]=ch[j];
        StringTokenizer str=new StringTokenizer(new String(ch1),",");
        while(str.hasMoreTokens()){
            model.addRow(new Object[]{str.nextToken().trim()});}
        }
    }

    private void set(String action) {

        this.action=action;
        header.setText("CURRENT OPERATION -'"+action+"'");
    }

    public static void main(String...s)
{
new PDG();
}
}