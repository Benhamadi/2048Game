package game2048;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;




public class ClientWriter_Reader {
	
	private OutputStream outputStream;
	private ByteArrayOutputStream baos = new ByteArrayOutputStream ();
	private DataOutputStream output = new DataOutputStream (baos);
	
	
	
	private DataInputStream in;
	int res;
	
	public ClientWriter_Reader(OutputStream _out)
	{
		outputStream=_out;
	}
	public ClientWriter_Reader(InputStream _in) throws IOException
	{
		this.in = new DataInputStream(_in);
		res=-1;
	}
	 
	public void WriteMessage( int message)
	{
		try {
			output.writeInt(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public int readLenght() throws IOException
	{
		byte[] length_b=new byte[1];
		length_b[0]=in.readByte();
		return Integer.valueOf(new String(length_b));
	}
	public int readValue() throws IOException
	{
		byte[] b=new byte[readLenght()];
		for(int i=0 ;i<b.length;i++){
			
			b[i]=in.readByte();

			}
		return Integer.valueOf( new String(b));
	}
	
	public int ReadMessage() throws IOException 
	{
		return readValue();
	}
	
	
	public void send() {
		byte [] message = baos.toByteArray();
		try {
			outputStream.write(message);
			outputStream.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

}
