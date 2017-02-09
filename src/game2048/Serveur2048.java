package game2048;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import game2048.model.Field;

public class Serveur2048 {
	public static final int DEFAULT_PORT = 12016;
	private Selector selector;
	private ServerSocketChannel server;
	private final int port;
	private HashMap<InetSocketAddress,Field> models;

	public Serveur2048() {
		this(DEFAULT_PORT);
	}

	public Serveur2048(int port) {
		this.port = port;
	}

	public void setup() throws IOException {
		this.selector = Selector.open();
		this.server = ServerSocketChannel.open();
		this.server.configureBlocking(false);
		InetAddress ia = InetAddress.getByName("127.0.0.1");
		InetSocketAddress isa = new InetSocketAddress(ia, this.port);
		this.server.socket().bind(isa);
		models = new HashMap<InetSocketAddress,Field>();
	}

	public void start() throws IOException {
		System.out.println("setting up server...");
		this.setup();
		System.out.println("server started...");
		int i=0;
		SelectionKey acceptKey = this.server.register(this.selector, SelectionKey.OP_ACCEPT);
		while (acceptKey.selector().select() > 0) {
			for (Iterator<SelectionKey> it = this.selector.selectedKeys().iterator(); it.hasNext();) {
				SelectionKey key = it.next();
				it.remove();
				if (!key.isValid())
					continue;
				if (key.isAcceptable()) {
					ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
					SocketChannel socket = (SocketChannel) ssc.accept();
					socket.configureBlocking(false);
					
					socket.register(this.selector, SelectionKey.OP_READ);
					System.out.println(" Client " + i++ +" connecté sur  :" + socket.getRemoteAddress().toString());
					
					models.put((InetSocketAddress) socket.getRemoteAddress(), new Field());
				
					

					continue;
				}
				
				if (key.isReadable()) {
					SocketChannel clientChannel = (SocketChannel) key.channel();
					this.doEcho(clientChannel);
					continue;
				}

			}
		}
	}

	public Field get_Value(SocketChannel s) throws IOException
	{
		return models.get(s.getRemoteAddress());
	}
	public void doEcho(SocketChannel socket) throws IOException {
		int msg = this.readInt(socket);
		

		if (msg == 99)
			socket.close();
		else {
			switch (msg) {
			case 1:
			    get_Value(socket).initializeGrid();
			    get_Value(socket).setArrowActive(true);
			    get_Value(socket).addNewCell();
			    get_Value(socket).addNewCell();
		          break;
			case KeyEvent.VK_LEFT:
				if (get_Value(socket).isArrowActive()) {
					if (get_Value(socket).moveCellsLeft()) {
						if (get_Value(socket).isGameOver()) {
							get_Value(socket).setArrowActive(false);
						} else {
							get_Value(socket).addNewCell();
						}
					}
				}
				break;
			case KeyEvent.VK_RIGHT:
				if (get_Value(socket).isArrowActive()) {
					if (get_Value(socket).moveCellsRight()) {
						if (get_Value(socket).isGameOver()) {
							get_Value(socket).setArrowActive(false);
						} else {
							get_Value(socket).addNewCell();
						}
					}
				}

				break;
			case KeyEvent.VK_DOWN:
				if (get_Value(socket).isArrowActive()) {
					if (get_Value(socket).moveCellsDown()) {
						if (get_Value(socket).isGameOver()) {
							get_Value(socket).setArrowActive(false);
						} else {
							get_Value(socket).addNewCell();
						}
					}
				}
				break;
			case KeyEvent.VK_UP:
				if (get_Value(socket).isArrowActive()) {
					if (get_Value(socket).moveCellsUp()) {
						if (get_Value(socket).isGameOver()) {
							get_Value(socket).setArrowActive(false);
						} else {
							get_Value(socket).addNewCell();
						}
					}
				}
				break;
			}
			System.out.println("Request");

			 this.writeModel(socket, get_Value(socket));

		}
	}

	public int readInt(SocketChannel socket) throws IOException {
		ByteBuffer rcvbuf = ByteBuffer.allocate(1024);
		int nBytes = socket.read(rcvbuf);

		rcvbuf.flip();

		int result = rcvbuf.getInt();
		if (result != 99) {
			return result;
		} else
			return 0;

	}
	/*
	public void sendValue( SocketChannel s ,int value) throws IOException
	{
		String req_msg = "";
		req_msg = String.valueOf(value);
		ByteBuffer buffer = ByteBuffer.wrap((req_msg.getBytes()));
		int nBytes = s.write(buffer);
		buffer.clear();
		
	}
	
	public void sendLength( SocketChannel s ,byte[]bs) throws IOException
	{
		ByteBuffer buffer2 = ByteBuffer.wrap((String.valueOf(bs).getBytes()));
		int nBytes2 = s.write(buffer2);
		buffer2.clear();
		
	}*/
	public void writeModel(SocketChannel socket, Field model) throws IOException {
		
	
		for (int x = 0; x < 4; x++) {
	        for (int y = 0; y < 4; y++) {
				String value  = "";
				
				value = String.valueOf(model.getCell(x, y).getValue());
				
				
				
				ByteBuffer buffer = ByteBuffer.wrap((String.valueOf(value.length()).getBytes()));
				int nBytes2 = socket.write(buffer);
				buffer.clear();

				ByteBuffer buffer2 = ByteBuffer.wrap((value.getBytes()));
				int nBytes = socket.write(buffer2);
				buffer2.clear();
				
				/*

				ByteBuffer buffer = ByteBuffer.wrap((req_msg.getBytes()));
				int nBytes = socket.write(buffer);
				buffer.clear();*/
				
				
			
		
		
	        }
		}
		String score="";
		
		score = String.valueOf(model.getScore());
		
		
		ByteBuffer buffer3 = ByteBuffer.wrap((String.valueOf(score.length()).getBytes()));
		int nBytes3 = socket.write(buffer3);
		buffer3.clear();
		

		ByteBuffer buffer4 = ByteBuffer.wrap((score.getBytes()));
		int nBytes4 = socket.write(buffer4);
		buffer4.clear();
		
		
	}

	public static void main(String args[]) {
		Serveur2048 server = new Serveur2048();
		try {
			server.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}