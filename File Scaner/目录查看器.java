import javax.swing.*; 
import javax.swing.event.*; 
import javax.swing.*; 
import javax.swing.event.*; 
import javax.swing.tree.*; 
import java.awt.*; 
import java.awt.event.*; 
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*; 
public class file extends JFrame 
implements ActionListener,TreeExpansionListener,TreeSelectionListener
{ 
    JTree jtree = new JTree(createTreeModel()); 
    JPanel JPsub=new JPanel(new GridLayout(100,3)); 
    JPanel road=new JPanel(new BorderLayout());
    JSplitPane JSP; 
    JLabel status=new JLabel(" "); 
public file() 
{ 
    super("目录查看器");
    //设置窗口大小 
    Dimension dimension = getToolkit().getScreenSize(); 
    int i = 400; 
    int j = 100;
    setBounds(i,j,640,480); 
    addWindowListener(
            new WindowAdapter()
            { 
                public void windowClosing(WindowEvent windowevent)
                { 
                    System.exit(0); 
                    } 
                }
            ); 
    //水平分割窗口，左scrollPane内放tree，右放JPsub用于显示文件 
    JSP=new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, new JScrollPane(jtree),new JScrollPane(JPsub)); 
    JSP.setLastDividerLocation(200); 
    getContentPane().add(JSP); 
    //背景色为白 
    JPsub.setBackground(Color.white); 
    //给树添加展开监听器 
    jtree.addTreeExpansionListener(this); 
    jtree.addTreeSelectionListener(this); 
    //设置树的外形 ,改变文件夹的图案
    try { 
        UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel"); 
        SwingUtilities.updateComponentTreeUI(this); 
        }
    catch (Exception ex) {} 
    getContentPane().add(road,BorderLayout.NORTH); 
    road.add(status,BorderLayout.WEST); 
    } 
//树展开 
public void treeExpanded(TreeExpansionEvent e) 
{ 
//getLastSelectedPathComponent()返回当前选择的第一个节点中的最后一个路径组件。 
//首选节点的 TreePath 中的最后一个 Object，若未选择任何内容，则返回 null 
    if (jtree.getLastSelectedPathComponent()==null) 
        return; 
    if (jtree.getLastSelectedPathComponent().toString().trim().equals("Local")) 
        return; 
    TreePath tpath = e.getPath(); 
    FileNode node = (FileNode)tpath.getLastPathComponent(); 
    if( ! node.isExplored()) 
    { 
        DefaultTreeModel model = (DefaultTreeModel)jtree.getModel(); 
        node.Explore(); 
        model.nodeStructureChanged(node); 
    } 
} 
//树折叠 
public void treeCollapsed(TreeExpansionEvent e) {} 
//值变化 
public void valueChanged(TreeSelectionEvent e) 
{ 
    try{ 
        String s=""; 
        //如果该结点最后一个对象返回值为空，即为底层结点，返回，清空JPsub 
        if (jtree.getLastSelectedPathComponent()==null) 
            return; 
        JPsub.removeAll();         
        //如果是本地，则产生目录 
        if (jtree.getLastSelectedPathComponent().toString().trim().equals("Local"))
        { 
            File roots[]=File.listRoots();//硬盘的名字 
            for(int i=1;i <roots.length;i++) 
            { 
                String DiskName=roots[i].toString(); 
                DiskName=DiskName.substring(0,DiskName.indexOf(":")+1); 
                addButton(DiskName,""); 
                } 
            }  
        else
        { 
            Object []path= e.getPath().getPath(); 
            String st=""; 
            for(int i=1;i <path.length;i++) 
                st+=File.separator+path[i].toString(); 
            File f = new File(st.substring(1)); 
            status.setText(f.toString()); 
            String[] list= f.list();            
            //定义Vector变量 ，创建向量类Vector
            Vector vFile=new Vector(),vDir=new Vector(); 
            for(int i = 0; i < list.length; i++)
            { 
                if ((new File(st+File.separator+list[i])).isDirectory()) 
                    vDir.addElement(list[i]); 
                else 
                    vFile.addElement(list[i]); 
                }            
            //排序 
            sortElements(vFile); 
            sortElements(vDir);            
            for(int i=0;i <vDir.size();i++) 
                addButton((String)(vDir.elementAt(i)),st); 
            for(int i=0;i <vFile.size();i++) 
                addButton((String)(vFile.elementAt(i)),st); 
            } 
        JPsub.doLayout(); 
        JPsub.repaint(); 
        }
    catch(Exception ee){} 
} 
//交换
private void swap(int loc1,int loc2,Vector v)
{ 
    Object tmp=v.elementAt(loc1); 
    v.setElementAt(v.elementAt(loc2),loc1); 
    v.setElementAt(tmp,loc2); 
} 
//排序 
public void sortElements(Vector v)
{ 
    for(int i=0;i <v.size();i++)
    { 
        int k=i; 
        for(int j=i+1;j <v.size();j++) 
            if(((String)(v.elementAt(j))).toLowerCase().compareTo(((String)(v.elementAt(k))).toLowerCase()) <0) 
                k=j; 
        if(k!=i)swap(k,i,v);//进行交换 
        } 
} 
//添加右侧按钮 ，点击打开右侧文件夹
private void addButton(String fileName,String filePath)
{ 
    JButton btt=new JButton(fileName); 
    btt.setBorder(null); 
    btt.setHorizontalAlignment(SwingConstants.LEFT); 
    btt.setBackground(Color.white); 
    if ((new File(filePath+File.separator+fileName)).isDirectory()) 
        btt.setIcon(UIManager.getIcon("Tree.closedIcon")); 
    else 
        btt.setIcon(UIManager.getIcon("Tree.leafIcon")); 
    JPsub.add(btt); 
    btt.addActionListener(this); 
} 
//列出文件，并显示相关信息
public void actionPerformed(ActionEvent e)
{ 
    try
    { 
        TreePath p=jtree.getLeadSelectionPath(); 
        String text=((JButton)(e.getSource())).getText(); 
        Object []path= p.getPath(); 
        String sr=""; 
        for(int i=1;i <path.length;i++) 
            sr+=File.separator+path[i].toString(); 
        sr=sr.substring(1); 
        File f = new File(sr+File.separator+text); 
        status.setText(f.toString()+"   Last Modified:"+(new SimpleDateFormat("yyyy-MM-dd")).format(f.lastModified())+"   Size:"+f.length()); 
        if(f.isDirectory())
        { 
            int index=jtree.getRowForPath(p); 
            jtree.expandRow(index); 
            while (!(jtree.getLastSelectedPathComponent().toString().trim().equals(text))) 
                jtree.setSelectionRow(index++); 
            jtree.expandRow(index-1); 
            } 
        
        else
        { 
            String postfix=text.toUpperCase(); 
            if(postfix.indexOf(".TXT")!=-1||postfix.indexOf(".JAVA")!=-1|| 
                    postfix.indexOf(".HTM")!=-1||postfix.indexOf(".LOG")!=-1) 
                Runtime.getRuntime().exec("NotePad.exe "+sr+File.separator+text); 
            } 
        }
    catch(Exception ee){} 
} 
//创建树 
private DefaultMutableTreeNode createTreeModel() 
{ 
    DefaultMutableTreeNode rootNode=new DefaultMutableTreeNode("Local"); 
    File[] rootPath = File.listRoots(); 
    for(int i=0;i <rootPath.length;i++)
    { 
        FileNode Node = new FileNode(rootPath[i]); 
        Node.Explore(); 
        rootNode.add(Node); 
        } 
    return rootNode; 
} 
public static void main(String args[]) 
{ 
    new file().setVisible(true); 
} 
class FileNode extends javax.swing.tree.DefaultMutableTreeNode 
{ 
    private boolean explored = false; 
    public FileNode(File file)
    { 
        setUserObject(file); 
    } 
    @Override 
    public boolean getAllowsChildren() 
    { 
        return isDirectory(); 
    } 
    @Override 
    public boolean isLeaf() 
    { 
        return !isDirectory(); 
    } 
    public File getFile() 
    { 
        return (File)getUserObject(); 
    } 
    public boolean isExplored() 
    { 
        return explored; 
    } 
    public boolean isDirectory() 
    { 
        return getFile().isDirectory(); 
    } 
    @Override 
    public String toString() 
    { 
        File file = (File)getUserObject(); 
        String filename = file.toString(); 
        int index = filename.lastIndexOf(File.separator); 
        return (index != -1 && index != filename.length()-1) ? filename.substring(index+1) : filename; 
        }  
    public void Explore()
    { 
        if(!isDirectory()) return; 
        if(!isExplored()) 
        { 
            File file = getFile(); 
            File[] children = file.listFiles(); 
            for(int i=0; i < children.length; ++i) 
            { 
                File f=children[i]; 
                if(f.isDirectory())add(new FileNode(children[i])); 
                } 
            explored = true; 
            } 
        } 
    } 
}