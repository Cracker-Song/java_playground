package test;

import java.awt.*;
import java.io.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.event.*;

public class FileInfo extends JFrame{
	private JLabel label ;
	private JButton button_Open;
	private JTextField text_Route;
	private TextArea text_Info;
	/*
	 * 构造函数FileInfor();
	 */
	public FileInfo(){
		buildLayout();	
	}
	public static void main(String[] args) {
		new FileInfo();
	}
	
	/*
	 * 创建主界面buildLayout() , 返回一个容器Container
	 */
	private void buildLayout(){
		final JFrame frame = new JFrame("文件查看小程序");
		Container con = getContentPane();
		JPanel pane = new JPanel();
		pane.setLayout(new FlowLayout());
		
		label = new JLabel("文件路径：" , JLabel.CENTER);
		button_Open = new JButton("Open...");
		text_Route = new JTextField(30);
		text_Info = new TextArea();
		text_Info.setEditable(false);
		
		//增加事件监听内部类
		button_Open.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				String path = getPath(); 	
				JFileChooser chooser = new JFileChooser(path);
				FileNameExtensionFilter filter = 
								new FileNameExtensionFilter("txt & java ", "java", "txt");
			    chooser.setFileFilter(filter);
				int returnVal = chooser.showOpenDialog(frame);
				if(returnVal == JFileChooser.APPROVE_OPTION) {
					showRoute(chooser.getSelectedFile().getAbsolutePath());
					showInfo(chooser.getSelectedFile().getAbsolutePath());
				}
				else{
					JOptionPane.showMessageDialog(null, "您还没有选取任何文件！", "错误提示", JOptionPane.WARNING_MESSAGE); 
				}
				
				setPath(chooser.getSelectedFile().getPath());
			}
		});
		
		pane.add(label);
		pane.add(text_Route);
		pane.add(button_Open);
		con.add(pane , BorderLayout.NORTH);
		con.add(text_Info , BorderLayout.CENTER);
		frame.setContentPane(con);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(350, 150, 500, 400);
		frame.setVisible(true);
		frame.setResizable(false);
	}
	
	/*
	 * 配置路径
	 */
	protected void setPath(String str) {
		// TODO Auto-generated method stub
		File file =  new File("c:\\Windows\\System32\\Fileconfig.ini");
		FileWriter fw;
		try {
			fw = new FileWriter(file);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(str);
			bw.close();
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			 
		} 
		
	}
/*
 * 从文件中获取路径
 */
	protected String getPath() {
		// TODO Auto-generated method stub
		File file = null;
		FileReader fr = null ; 
		BufferedReader br = null ;
		String path = null;
		file = new File("c:\\Windows\\System32\\Fileconfig.ini");
		try {
			fr = new FileReader(file);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				path = "c:\\Windows\\System32";
			}
		}
		br = new BufferedReader(fr);
		try {
			path = br.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			path = "c:\\Windows\\System32";
		}
		return path;
	}
	
	/*
	 * 信息显示
	 */
	protected void showInfo(String str) {
		// TODO Auto-generated method stub
		File file = new File(str);
		text_Info.setText("文件是否是一个标准文件      :  " + file.isFile()+"\r\n");
		text_Info.append("文件是否是一个隐藏文件      :  " + file.isHidden()+"\r\n");
		text_Info.append("文件大小                    :  " + file.length()+"\r\n");
		
		try {
			FileInputStream fis = new FileInputStream(str);
			BufferedInputStream bis = new BufferedInputStream(fis);
			byte[] buf = new byte[1024];
			int len = 0;
			while((len = bis.read(buf))!=-1){
				String string = new String(buf , 0 ,len);
				text_Info.append("文件详情 :  \r\n" + string+"\r\n");
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	protected void showRoute(String str) {
		// TODO Auto-generated method stub
		text_Route.setText(str);
	}	
}
