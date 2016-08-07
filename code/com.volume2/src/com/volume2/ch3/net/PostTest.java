package com.volume2.ch3.net;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;

/**
 * This program demonstrates how to use the URLConnection class for
 * a POST request.<br/>
 * 
 * 类似模拟http 请求发送POST请求.
 * 
 * @author huangjw(mailto:as1124huang@gmail.com)
 *
 */
public class PostTest {
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				JFrame frame = new PostTestFrame();
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setVisible(true);
			}
		});

	}

}

class PostTestFrame extends JFrame{
	
	private static final long serialVersionUID = 1L;

	private JPanel northPanel;
	
	public PostTestFrame() {
		setTitle("PostTest");
		setSize(600, 800);
		
		northPanel = new JPanel();
		add(northPanel, BorderLayout.NORTH);
		northPanel.setLayout(new GridLayout(0, 2));
		
		northPanel.add(new JLabel("Host: ", SwingConstants.TRAILING));
		final JTextField hostField = new JTextField();
		northPanel.add(hostField);
		
		northPanel.add(new JLabel("Action: ", SwingConstants.TRAILING));
		final JTextField actionField = new JTextField();
		northPanel.add(actionField);
		
		for(int i=1; i<=8; i++){
			northPanel.add(new JTextField());
		}
		final JTextArea result = new JTextArea(20, 40);
		add(new JScrollPane(result));
		
		JPanel southPanel = new JPanel();
		add(southPanel, BorderLayout.SOUTH);
		JButton addButton = new JButton("More");
		southPanel.add(addButton);
		addButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				northPanel.add(new JTextField());
				northPanel.add(new JTextField());
				pack();
			}
		});
		
		
		JButton getButton = new JButton("Get");
		southPanel.add(getButton);
		getButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				result.setText("");
				final Map<String, String> post = new HashMap<String, String>();
				for(int i=4;i<northPanel.getComponentCount(); i+=2){
					String name = ((JTextField)northPanel.getComponent(i)).getText();
					if(name.length() > 0){
						String value = ((JTextField)northPanel.getComponent(i+1)).getText();
						post.put(name, value);
					}
				}
				
				new SwingWorker<Void, Void>() {
					@Override
					protected Void doInBackground() throws Exception {
						String urlString = hostField.getText()+"/"+actionField.getText();
						try {
							result.setText(doPost(urlString, post));
						} catch (Exception e) {
							result.setText(""+e);
							e.printStackTrace();
						}
						return null;
					}
				}.execute();;
			}
		});
		
		pack();
	}
	
	/**
	 * Makes a POST request and returns the server response.
	 * @param urlString
	 * @param nameValuePairs
	 * @return
	 * @throws IOException
	 */
	public static String doPost(String urlString, Map<String, String> nameValuePairs) 
			throws IOException {
		URL url = new URL(urlString);
		URLConnection connection = url.openConnection();
		connection.setDoOutput(true);
		
		PrintWriter out = new PrintWriter(connection.getOutputStream());
		boolean first = true;
		for(Map.Entry<String, String> pair : nameValuePairs.entrySet()) {
			if(first)
				first = false;
			else out.println("&");
			String name = pair.getKey();
			String value = pair.getValue();
			out.print(name);
			out.print('=');
			out.print(URLEncoder.encode(value, "UTF-8"));
		}
		
		out.close();
		Scanner in;
		StringBuilder response = new StringBuilder();
		try {
			in = new Scanner(connection.getInputStream());
			
		} catch (Exception e) {
			if(!(connection instanceof HttpURLConnection)){
				throw e;
			}
			InputStream err = ((HttpURLConnection)connection).getErrorStream();
			if(err != null)
				throw e;
			in = new Scanner(err);
		}
		
		while(in.hasNextLine()){
			response.append(in.nextLine());
			response.append("\n");
		}
		in.close();
		return response.toString();
	
	}
	
	
	
}