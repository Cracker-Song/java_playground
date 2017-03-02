package text_editor;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

public class text_editor {
    public static void main(String[] args)
    {
        JFrame background_frame = new JFrame("Text Editor");
        JPanel background_panel = new JPanel();
        JTextPane input_area = new JTextPane();
        input_area.setText("File中有保存文件 新建文件 读取文件 \nEdit中有Undo快捷键为ctrl + z, 设置颜色 字体 大小 均为点一次换一种样式.\n快捷键只为测试用因此只设计了undo的快捷键");
        JLabel input_statistic = new JLabel("字符数:0    行数:0", JLabel.CENTER);
        JMenuBar menus = new JMenuBar();
        JMenu menu_file = new JMenu("File");
        JMenu menu_edit = new JMenu("Edit");
        JMenu menu_help = new JMenu("Help");

        StringBuilder last_modify = new StringBuilder("");

        background_frame.setLayout(null);
        background_panel.setLayout(null);

        background_frame.setBounds(0, 0, 500, 500);
        background_panel.setBounds(0, 0, 500, 500);
        input_area.setBounds(10, 50, 480, 400);
        input_statistic.setBounds(10, 450, 480, 30);
        menus.setBounds(10, 10, 480, 30);

        //input_area.setLineWrap(true);

        background_frame.addWindowListener(new my_frame_window_listener());
        background_frame.addComponentListener(new my_frame_component_listener(background_panel, input_area, menus, input_statistic));
        input_area.getDocument().addDocumentListener(new my_text_area_listener(input_statistic, input_area, last_modify));
        input_area.addKeyListener(new my_input_area_keylistener(input_area, last_modify));
        //background_panel.addKeyListener();

        JMenuItem menu_file_new = new JMenuItem("New File");
        menu_file_new.addActionListener(new my_menu_listener("menu_file_new", input_area, last_modify));
        menu_file.add(menu_file_new);
        JMenuItem menu_file_open = new JMenuItem("Open File");
        menu_file_open.addActionListener(new my_menu_listener("menu_file_open", input_area, last_modify));
        menu_file.add(menu_file_open);
        JMenuItem menu_file_save = new JMenuItem("Save File");
        menu_file_save.addActionListener(new my_menu_listener("menu_file_save", input_area, last_modify));
        menu_file.add(menu_file_save);


        JMenuItem menu_edit_undo = new JMenuItem("Undo Ctrl+Z");
        menu_edit_undo.addActionListener(new my_menu_listener("menu_edit_undo", input_area, last_modify));
        menu_edit.add(menu_edit_undo);
        JMenuItem menu_edit_font = new JMenuItem("Font");
        menu_edit_font.addActionListener(new my_menu_listener("menu_edit_font", input_area, last_modify));
        menu_edit.add(menu_edit_font);
        JMenuItem menu_edit_color = new JMenuItem("Color");
        menu_edit_color.addActionListener(new my_menu_listener("menu_edit_color", input_area, last_modify));
        menu_edit.add(menu_edit_color);
        JMenuItem menu_edit_size = new JMenuItem("Size");
        menu_edit_size.addActionListener(new my_menu_listener("menu_edit_size", input_area, last_modify));
        menu_edit.add(menu_edit_size);

        background_frame.add(background_panel);

        menus.add(menu_file);
        menus.add(menu_edit);
        menus.add(menu_help);

        background_panel.add(input_area);
        background_panel.add(input_statistic);
        background_panel.add(menus);

        background_frame.setVisible(true);
    }
}

class my_frame_window_listener implements WindowListener {

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        System.exit(0);
    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}

class my_text_area_listener implements DocumentListener {
    JLabel input_staticis;
    JTextPane input_area;
    StringBuilder last_modify;
    String str_now;
    public void my_test_area_undo() {
        if(!this.input_area.getText().equals(this.str_now)) {
            this.last_modify.delete(0, this.last_modify.length());
            this.last_modify.append(this.str_now.toCharArray());
            this.str_now = this.input_area.getText();
        }
    }
    public my_text_area_listener(JLabel input_statisic, JTextPane input_area, StringBuilder last_modify) {
        this.input_staticis = input_statisic;
        this.input_area = input_area;
        this.last_modify = last_modify;
        this.str_now = this.input_area.getText();
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        my_test_area_undo();
        int number_of_enter = 0;
        for(char word : this.input_area.getText().toCharArray()) {
            if(word == 10) {
                number_of_enter ++;
            }
        }
        this.input_staticis.setText(String.format("字符数:%d    行数:%d", this.input_area.getText().length(), number_of_enter));
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        my_test_area_undo();
        int number_of_enter = 0;
        for(char word : this.input_area.getText().toCharArray()) {
            if(word == 10) {
                number_of_enter ++;
            }
        }
        this.input_staticis.setText(String.format("字符数:%d    行数:%d", this.input_area.getText().length(), number_of_enter));
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        my_test_area_undo();
        int number_of_enter = 0;
        for(char word : this.input_area.getText().toCharArray()) {
            if(word == 10) {
                number_of_enter ++;
            }
        }
        this.input_staticis.setText(String.format("字符数:%d    行数:%d", this.input_area.getText().length(), number_of_enter));
    }
}

class my_menu_listener implements ActionListener {
    String choose;
    JTextPane input_area;
    StringBuilder last_modify;
    JFileChooser filechooser = new JFileChooser();
    int color_num = 0;
    int size_num = 0;
    public my_menu_listener(String choose, JTextPane input_area, StringBuilder last_modify) {
        this.choose = choose;
        this.input_area = input_area;
        this.last_modify = last_modify;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        //System.out.print(this.choose);
        switch (this.choose) {
            case "menu_edit_undo": {
                char[] tmp = this.input_area.getText().toCharArray();
                this.input_area.setText(this.last_modify.toString());
                this.last_modify.delete(0, this.last_modify.length());
                this.last_modify.append(tmp);
                break;
            }
            case "menu_edit_color": {
                switch (this.color_num) {
                    case 0: {
                        MutableAttributeSet attr = new SimpleAttributeSet();
                        StyleConstants.setForeground(attr, Color.blue);
                        this.input_area.setCharacterAttributes(attr, false);
                        break;
                    }
                    case 1: {
                        MutableAttributeSet attr = new SimpleAttributeSet();
                        StyleConstants.setForeground(attr, Color.red);
                        this.input_area.setCharacterAttributes(attr, false);
                        break;
                    }
                    case 2: {
                        MutableAttributeSet attr = new SimpleAttributeSet();
                        StyleConstants.setForeground(attr, Color.yellow);
                        this.input_area.setCharacterAttributes(attr, false);
                        break;
                    }
                    case 3: {
                        MutableAttributeSet attr = new SimpleAttributeSet();
                        StyleConstants.setForeground(attr, Color.green);
                        this.input_area.setCharacterAttributes(attr, false);
                        break;
                    }
                    case 4: {
                        MutableAttributeSet attr = new SimpleAttributeSet();
                        StyleConstants.setForeground(attr, Color.black);
                        this.input_area.setCharacterAttributes(attr, false);
                        break;
                    }
                }
                this.color_num = (this.color_num + 1) % 5;
                break;
            }
            case "menu_edit_size": {
                System.out.print(this.input_area.getFont());
                switch (this.size_num) {
                    case 0: {
                        MutableAttributeSet attr = new SimpleAttributeSet();
                        StyleConstants.setFontSize(attr, 16);
                        this.input_area.setCharacterAttributes(attr, false);
                        break;
                    }
                    case 1: {
                        MutableAttributeSet attr = new SimpleAttributeSet();
                        StyleConstants.setFontSize(attr, 19);
                        this.input_area.setCharacterAttributes(attr, false);
                        break;
                    }
                    case 2: {
                        MutableAttributeSet attr = new SimpleAttributeSet();
                        StyleConstants.setFontSize(attr, 22);
                        this.input_area.setCharacterAttributes(attr, false);
                        break;
                    }
                    case 3: {
                        MutableAttributeSet attr = new SimpleAttributeSet();
                        StyleConstants.setFontSize(attr, 25);
                        this.input_area.setCharacterAttributes(attr, false);
                        break;
                    }
                    case 4: {
                        MutableAttributeSet attr = new SimpleAttributeSet();
                        StyleConstants.setFontSize(attr, 13);
                        this.input_area.setCharacterAttributes(attr, false);
                        break;
                    }
                }
                this.size_num = (this.size_num + 1) % 5;
                break;
            }
            case "menu_edit_font": {
                MutableAttributeSet attr = new SimpleAttributeSet();
                StyleConstants.setFontFamily(attr, String.valueOf(Font.ITALIC));
                this.input_area.setCharacterAttributes(attr, false);
                break;
            }
            case "menu_file_save": {
                int j = filechooser.showSaveDialog(this.input_area); //显示保存文件对话框
                if (j == JFileChooser.APPROVE_OPTION) {  //点击对话框中保存按钮
                    File f = filechooser.getSelectedFile(); //得到选择的文件
                    try {
                        FileOutputStream out = new FileOutputStream(f);  //得到文件输出流
                        out.write(input_area.getText().getBytes()); //写出文件
                    } catch (Exception ex) {
                        ex.printStackTrace(); //输出出错信息
                    }
                }
                break;
            }
            case "menu_file_open": {
                int i = filechooser.showOpenDialog(this.input_area); //显示打开文件对话框
                if (i == JFileChooser.APPROVE_OPTION) { //点击对话框中打开选项
                    File f = filechooser.getSelectedFile(); //得到选择的文件
                    try {
                        InputStream is = new FileInputStream(f); //得到文件输入流
                        input_area.read(is, "d"); //读入文件到文本窗格
                    } catch (Exception ex) {
                        ex.printStackTrace();  //输出出错信息
                    }
                }
                break;
            }
            case "menu_file_new": {
                input_area.setText("");
                break;
            }

        }
    }
}

class my_input_area_keylistener implements KeyListener {

    JTextPane input_area;
    StringBuilder last_modify;
    public my_input_area_keylistener(JTextPane input_area, StringBuilder last_modify) {
        this.input_area = input_area;
        this.last_modify = last_modify;
    }
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int tmp_key = e.getKeyCode();
        if(e.isControlDown() && tmp_key == KeyEvent.VK_Z) {
            char[] tmp = this.input_area.getText().toCharArray();
            this.input_area.setText(this.last_modify.toString());
            this.last_modify.delete(0, this.last_modify.length());
            this.last_modify.append(tmp);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}

class my_frame_component_listener implements ComponentListener {

    JPanel background_panel;
    JTextPane input_area;
    JMenuBar menus;
    JLabel input_statistic;
    public my_frame_component_listener(JPanel background_panel, JTextPane input_area, JMenuBar menus, JLabel input_statistic) {
        this.background_panel = background_panel;
        this.input_area = input_area;
        this.input_statistic = input_statistic;
        this.menus = menus;
    }
    @Override
    public void componentResized(ComponentEvent e) {
        Dimension frame_size = e.getComponent().getSize();
        int height = (int)frame_size.getHeight();
        int width = (int)frame_size.getWidth();
        //System.out.println(frame_size);
        this.background_panel.setBounds(0, 0, width, height);
        this.input_area.setBounds(10*width/500, 50*height/500, 480*width/500, 400*height/500);
        this.menus.setBounds(10*width/500, 10*height/500, 480*width/500, 30*height/500);
        this.input_statistic.setBounds(10*width/500, 450*height/500, 480*width/500, 30*height/500);
        //System.out.print(this.input_area.getSize());

    }

    @Override
    public void componentMoved(ComponentEvent e) {

    }

    @Override
    public void componentShown(ComponentEvent e) {

    }

    @Override
    public void componentHidden(ComponentEvent e) {

    }
}