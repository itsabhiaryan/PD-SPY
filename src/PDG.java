import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import javax.swing.tree.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.table.*;

public class PDG extends JFrame implements ActionListener,TreeSelectionListener {

    PDG pd;
    JLabel header;
    String action="W";
    JMenuItem about,log;
    ImageIcon image[]=new ImageIcon[7];
    JMenuItem menuItem[][]=new JMenuItem[1][5];

    JTree jt;
    DefaultMutableTreeNode root=new DefaultMutableTreeNode("Record");
    DefaultTreeModel tm=new DefaultTreeModel(root);
    JSplitPane splitPane;
    JTextArea ta=new JTextArea(12,3);
    ArrayList<String> al=new ArrayList<String>();
    ArrayList<String> al2=new ArrayList<String>();
    JTable table=new JTable();
    DefaultTableModel model=(DefaultTableModel)table.getModel();
    MyPD pdt;

    PDG() {

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
            for(int i=0;i<image.length;i++)
                image[i]=new ImageIcon(PDG.class.getResource("images/"+imageName[i]+".png"));
            }
            catch(Exception e) {
                System.out.println(e);
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

    public void getLog(){

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

    public void operation(String label) {

        char ch[]=label.toCharArray();
        pdt.set(ch[0]+"");
        set(ch[0]+"");
    }

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

    public void getData(int start,int end) {

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

    public void set(String action) {

        this.action=action;
        header.setText("CURRENT OPERATION -'"+action+"'");
    }

    public static void main(String...s)
{
new PDG();
}
}