package Server;

import java.awt.*;

import javax.swing.*;

import javax.swing.table.DefaultTableModel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ServerForm extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JFrame frame;
	private JTextField txtFile;
	public JTextArea txtArea;
	private Server server;
	private File file;
	protected DefaultTableModel table ;
	protected DefaultTableModel tableuseronl ;
	JList<String> list;
	JScrollPane scrollPane;
	JLabel listuser_onl;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ServerForm window = new ServerForm();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ServerForm() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		table = new DefaultTableModel();
		table.addColumn("username");
		table.addColumn("pass");
		table.addColumn("ip");
		table.addColumn("port");

		tableuseronl = new DefaultTableModel();
		tableuseronl.addColumn("username");
		tableuseronl.addColumn("pass");
		tableuseronl.addColumn("ip");
		tableuseronl.addColumn("port");
		
		frame = new JFrame("Server");
		frame.setBounds(100, 100, 600, 350);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		SpringLayout springLayout = new SpringLayout();
		frame.getContentPane().setLayout(springLayout);

		JLabel lblServer = new JLabel("SERVER");
		springLayout.putConstraint(SpringLayout.NORTH, lblServer, 23, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, lblServer, -250, SpringLayout.EAST, frame.getContentPane());
		lblServer.setFont(new Font("Tahoma", Font.PLAIN, 22));
		frame.getContentPane().add(lblServer);
		
		JLabel lblDatafile = new JLabel("DataFile:");
		lblDatafile.setFont(new Font("Tahoma", Font.PLAIN, 14));
		springLayout.putConstraint(SpringLayout.NORTH, lblDatafile, 61, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, lblDatafile, 27, SpringLayout.WEST, frame.getContentPane());
		frame.getContentPane().add(lblDatafile);
		
		JButton btnBrowse = new JButton("Browse");
		btnBrowse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser c = new JFileChooser();
				
				int rVal = c.showOpenDialog(ServerForm.this);
				if (rVal == JFileChooser.APPROVE_OPTION) {
					txtFile.setText(c.getCurrentDirectory().toString());
					file = c.getSelectedFile();
				}
			}
		});
		springLayout.putConstraint(SpringLayout.NORTH, btnBrowse, -4, SpringLayout.NORTH, lblDatafile);
		springLayout.putConstraint(SpringLayout.EAST, btnBrowse, -123, SpringLayout.EAST, frame.getContentPane());
		btnBrowse.setFont(new Font("Tahoma", Font.PLAIN, 14));
		frame.getContentPane().add(btnBrowse);

		txtFile = new JTextField();
		txtFile.setEditable(false);
		springLayout.putConstraint(SpringLayout.NORTH, txtFile, 0, SpringLayout.NORTH, lblDatafile);
		springLayout.putConstraint(SpringLayout.WEST, txtFile, 6, SpringLayout.EAST, lblDatafile);
		springLayout.putConstraint(SpringLayout.EAST, txtFile, -5, SpringLayout.WEST, btnBrowse);
		frame.getContentPane().add(txtFile);
		txtFile.setColumns(10);

		JButton btnStart = new JButton("Start");
		btnStart.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				try{
					DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		            Document doc = docBuilder.parse(file);
		            doc.getDocumentElement().normalize();
		            
		            NodeList nList = doc.getElementsByTagName("PEER");
		            for(int i = 0; i< nList.getLength(); i++){
		            	Node nNode = nList.item(i);
		            	if(nNode.getNodeType()==Node.ELEMENT_NODE)
		            	{
		            		Element eElement = (Element)nNode;
			            	
		            		String userName = eElement.getElementsByTagName("USER_NAME").item(0).getTextContent();
		            		String pass = eElement.getElementsByTagName("PASSWORD").item(0).getTextContent();
		            		
		            		String[] data = {userName,pass,"",""};
		            		table.addRow(data);
		            	}
		            }
		            StartMouseClicked(arg0);
		            
					}
					catch(Exception ei){
						txtArea.append("Error read file");
						System.out.println(ei.getMessage());
					}
			}
		});
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		springLayout.putConstraint(SpringLayout.WEST, btnStart, 6, SpringLayout.EAST, btnBrowse);
		springLayout.putConstraint(SpringLayout.EAST, btnStart, 83, SpringLayout.EAST, btnBrowse);
		btnStart.setFont(new Font("Tahoma", Font.PLAIN, 14));
		frame.getContentPane().add(btnStart);



		txtArea = new JTextArea();
		JScrollPane txtScoll = new JScrollPane(txtArea);
		springLayout.putConstraint(SpringLayout.SOUTH, btnStart, -29, SpringLayout.NORTH, txtScoll);
		springLayout.putConstraint(SpringLayout.NORTH, txtScoll, 29, SpringLayout.SOUTH, btnBrowse);
		springLayout.putConstraint(SpringLayout.WEST, txtScoll, 27, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, txtScoll, -30, SpringLayout.SOUTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, txtScoll, -100, SpringLayout.EAST, btnStart);
		frame.getContentPane().add(txtScoll);

		/*springLayout.putConstraint(SpringLayout.SOUTH, btnStart, -29, SpringLayout.NORTH, txtArea);
		springLayout.putConstraint(SpringLayout.NORTH, txtArea, 29, SpringLayout.SOUTH, btnBrowse);
		springLayout.putConstraint(SpringLayout.WEST, txtArea, 27, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, txtArea, 200, SpringLayout.SOUTH, lblDatafile);
		springLayout.putConstraint(SpringLayout.EAST, txtArea, -100, SpringLayout.EAST, btnStart);
		frame.getContentPane().add(txtArea);*/

		JLabel Show_data = new JLabel("Show data");
		springLayout.putConstraint(SpringLayout.NORTH, Show_data,-20 , SpringLayout.NORTH, txtScoll);
		springLayout.putConstraint(SpringLayout.WEST, Show_data, 10, SpringLayout.WEST, txtScoll);
		lblServer.setFont(new Font("Tahoma", Font.PLAIN, 22));
		frame.getContentPane().add(Show_data);




        scrollPane = new JScrollPane();
        springLayout.putConstraint(SpringLayout.NORTH, scrollPane, 113, SpringLayout.NORTH, frame.getContentPane());
        springLayout.putConstraint(SpringLayout.WEST, scrollPane, -120, SpringLayout.EAST, frame.getContentPane());
        springLayout.putConstraint(SpringLayout.EAST, scrollPane, 10, SpringLayout.EAST, btnStart);
        scrollPane.setSize(new Dimension(150, 350));
        springLayout.putConstraint(SpringLayout.SOUTH, scrollPane, -30, SpringLayout.SOUTH, frame.getContentPane());
        frame.getContentPane().add(scrollPane);

		listuser_onl =  new JLabel("User Online: 0");
		springLayout.putConstraint(SpringLayout.SOUTH, listuser_onl, -4, SpringLayout.NORTH, scrollPane);
		springLayout.putConstraint(SpringLayout.EAST, listuser_onl, -5, SpringLayout.EAST, scrollPane);
		lblServer.setFont(new Font("Tahoma", Font.PLAIN, 22));
		frame.getContentPane().add(listuser_onl);

        list = new JList<String>();
        list.setSize(new Dimension(150, 350));
        list.setEnabled(false);
        scrollPane.setViewportView(list);
	}
	public void RetryStart(int port){
		if(server != null)
			server.Stop();
		server = new Server(this,port);
	}
	public void UpdateJList(DefaultTableModel ta){
		try{
			try{
				DefaultListModel<String> tb = (DefaultListModel<String>) list.getModel();
				tb.removeAllElements();
			}catch(Exception e){}

			DefaultListModel<String> tmp = new DefaultListModel<String>();
			listuser_onl.setText("User Online: "+ta.getRowCount());
			list.setModel(tmp);
			if (ta.getRowCount()==0){
				tmp.addElement("");
			}else{
				for (int i = 0; i < ta.getRowCount(); i++){
					tmp.addElement(ta.getValueAt(i, 0).toString());
				}
			}
		}
		catch(Exception e){
			System.out.println("Cann't take list user");
		}

	}
	 private void StartMouseClicked(java.awt.event.MouseEvent evt) {
	        server = new Server(this);
	    }
}
