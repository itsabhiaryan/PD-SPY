package com.nayragames.pdspy;

import java.io.*;

/**
 * (c) 2016 Abhishek Aryan
 *
 * @author Abhishek Aryan
 * @since 01-11-2014
 *
 */
public class MyPD extends Thread {

	private MyPD pd;
	private String action;
	PDG pdg;

	public MyPD(String action,PDG pdg) {

		this.pd=this;
		this.action=action;
		this.pdg=pdg;
		start();
	}

	public void run() {

		File f[]=File.listRoots();
		while(true) {

			File f1[]=File.listRoots();
			if(f1.length!=f.length){
				if(f1.length>f.length){
					try{
						if(action.equals("C"))
							copy(new File(getNewDrive(f,f1)),new File(f[1].getPath()));
						if(action.equals("M"))
							move(new File(getNewDrive(f,f1)),new File(f[1].getPath()));
						if(action.equals("D"))
    						del(new File(getNewDrive(f,f1)));
						if(action.equals("W")){
							new NewDri(getNewDrive(f,f1),pd);
							pdg.validate();
						}
					}
					catch(Exception i){
						System.out.println(i);
					}
				}
				//else
				//System.out.println("Drive Removed");
				f=f1;
			}
		}
	}

	public String getNewDrive(File oldRoot[],File newRoot[]) {

		String newDrive=null;
		for(int i=0;i<newRoot.length;i++){
			int j=0;
			while(j<oldRoot.length){
				if(!newRoot[i].getPath().equals(oldRoot[j].getPath())){
					j++;
				if(j==oldRoot.length)
					newDrive=newRoot[i].getPath();}
				else
					break;
				}
		}
		return newDrive;
	}

	public void copy(File f,File newFile)throws IOException {

		if(f.exists()){
			if(f.isFile()){
				FileInputStream fi=new FileInputStream(f);
				BufferedInputStream bi=new BufferedInputStream(fi);
				FileOutputStream fo=new FileOutputStream(newFile);
				BufferedOutputStream bo=new BufferedOutputStream(fo);
				int i=0;
				while((i=bi.read())!=-1)
					bo.write((char)i);
				bo.flush();
				fi.close();
				fo.close();
			}
			else {
				File dir=new File(newFile.getPath()+File.separator+f.getName());
				if(!dir.exists())
					dir.mkdir();
				String s3[]=f.list();
				for(int i=0;i<s3.length;i++){
					File f1=new File(f.getPath()+File.separator+s3[i]);
					if(f1.isFile()){
						File dirFile=new File(dir.getPath()+File.separator+f1.getName());
						copy(f1,dirFile);
					}
					else
						copy(f1,dir);
				}
			}
		}
		else
			System.out.println("File not exist");
	}

	public void move(File f,File newFile)throws IOException {

		if(f.exists()){
			if(f.isFile()){
				FileInputStream fi=new FileInputStream(f);
				BufferedInputStream bi=new BufferedInputStream(fi);
				FileOutputStream fo=new FileOutputStream(newFile);
				BufferedOutputStream bo=new BufferedOutputStream(fo);
				int i=0;
				while((i=bi.read())!=-1)
					bo.write((char)i);
				bo.flush();
				fi.close();
				fo.close();
				f.delete();
			}
			else {
				File dir=new File(newFile.getPath()+File.separator+f.getName());
				if(!dir.exists())
					dir.mkdir();
				String s3[]=f.list();
					for(int i=0;i<s3.length;i++){
				File f1=new File(f.getPath()+File.separator+s3[i]);
				if(f1.isFile()){
					File dirFile=new File(dir.getPath()+File.separator+f1.getName());
					move(f1,dirFile);
				}
				else
					move(f1,dir);
				f1.delete();}
				if(!checkDrive(f)==true)
					f.delete();
			}
		}
		else
			System.out.println("File not exist");
	}

	public boolean checkDrive(File f1) {

		File f[]=File.listRoots();
		boolean drive=false;
		for(int i=0;i<f.length;i++){
			if(f1.getPath().equals(f[i].getPath()))
				drive=true;
		}
		return drive;
	}

	public void del(File f) {

		if(f.exists()){
			if(f.isFile())
				f.delete();
			String s3[]=f.list();
			for(int i=0;i<s3.length;i++){
				File f1=new File(f.getPath()+File.separator+s3[i]);
				if(f1.isFile())
					f1.delete();
				else
					del(f1);
				f1.delete();
			}
			if(!checkDrive(f)==true)
				f.delete();
			}
		else
			System.out.println("File not exitst");
	}


	public void set(String action)
{
this.action=action;
}

	/*public static void main(String...s){

		new PD();
	}*/
}