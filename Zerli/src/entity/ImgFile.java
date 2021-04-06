package entity;

import java.io.Serializable;

public class ImgFile implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6814263013064339170L;
	private String Description=null;
	private String fileName=null;	
	private int size=0;
	public  byte[] mybytearray;
	private String exe; // example: jpg,png
	
	
	public void initArray(int size)
	{
		mybytearray = new byte [size];	
	}
	
	public ImgFile( String fileName) {
		this.fileName = fileName;
	}
	
	
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public byte[] getMybytearray() {
		return mybytearray;
	}
	
	public byte getMybytearray(int i) {
		return mybytearray[i];
	}

	public void setMybytearray(byte[] mybytearray) {
		
		for(int i=0;i<mybytearray.length;i++)
		this.mybytearray[i] = mybytearray[i];
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public String getExe() {
		return exe;
	}

	public void setExe(String exe) {
		this.exe = exe;
	}	
}

