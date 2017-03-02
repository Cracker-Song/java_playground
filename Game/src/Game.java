import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * Created by souryou on 16/1/14.
 */
public class Game {
    public static void main(String[] args)
    {
        Frame mine=new Frame("Game");
        //JButton[] bs=new JButton[Nx*Ny];
        Panel p=new Panel();
        TextField t1=new TextField();
        TextField t2=new TextField();
        JButton b_con=new JButton("确认");
        t1.setBounds(100,40,100,30);
        t2.setBounds(250,40,100,30);
        b_con.setBounds(400,40,100,30);
        b_con.addActionListener(new GetText(p,t1,t2));
        mine.add(t1);
        mine.add(t2);
        mine.add(b_con);
        mine.setBounds(0,0,600,600+100);
        mine.setLayout(null);
        p.setLayout(null);
        p.setBounds(0,100,600,600+50);
        p.setBackground(Color.gray);
        mine.add(p);
        mine.addWindowListener(new MyListener());
        mine.setVisible(true);
    }
}
class GetText implements ActionListener
{
    Panel p;
    TextField t1,t2;
    int x,y,Sizex,Sizey;
    public GetText(Panel p,TextField t1,TextField t2)
    {
        this.p=p;
        this.t1=t1;
        this.t2=t2;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            x=Integer.parseInt(t1.getText());
            y=Integer.parseInt(t2.getText());
        }
        catch (Exception e1)
        {
            x=3;
            y=3;
        }
        p.removeAll();
        if(x==0||y==0)
        {
            x=3;
            y=3;
        }
        Sizex=600/x;
        Sizey=600/y;
        //System.out.print(t1.getText());
        JButton[] bs=new JButton[x*y];
        for(int i=0;i<x*y;i++)
        {
            bs[i]=new JButton("Black");
            bs[i].setForeground(Color.black);
            bs[i].setFont(new Font("华文行楷",Font.ITALIC,30*3/(int)Math.sqrt(x*y)));
            bs[i].setFocusable(false);
            bs[i].setBackground(Color.black);
            bs[i].setBounds((i%x)*Sizex,(i/x)*Sizey,Sizex,Sizey);
            bs[i].addActionListener(new MyJButton(bs,i,x,y));
            bs[i].setBorderPainted(false);
            p.add(bs[i]);
        }
        p.repaint();
    }
}
class MyListener implements WindowListener
{
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
class MyJButton implements ActionListener
{
    JButton[] b;
    int i,x,y;
    public MyJButton(JButton[] b,int i,int x,int y)
    {
        this.b=b;
        this.i=i;
        this.x=x;
        this.y=y;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(b[i].getBackground()==Color.black)
        {
            b[i].setBackground(Color.red);
            b[i].setText("red");
            b[i].setForeground(Color.red);
        }
        else
        {
            b[i].setBackground(Color.black);
            b[i].setText("Black");
            b[i].setForeground(Color.black);
        }
        if(i/x>=1)
        {
            if(b[(i/x-1)*x+i%x].getBackground()==Color.black)
            {
                b[(i/x-1)*x+i%x].setBackground(Color.red);
                b[(i/x-1)*x+i%x].setText("red");
                b[(i/x-1)*x+i%x].setForeground(Color.red);
            }
            else
            {
                b[(i/x-1)*x+i%x].setBackground(Color.black);
                b[(i/x-1)*x+i%x].setText("Black");
                b[(i/x-1)*x+i%x].setForeground(Color.black);
            }
        }

        if(i/x<y-1)
        {
            if(b[(i/x+1)*x+i%x].getBackground()==Color.black)
            {
                b[(i/x+1)*x+i%x].setBackground(Color.red);
                b[(i/x+1)*x+i%x].setText("red");
                b[(i/x+1)*x+i%x].setForeground(Color.red);
            }
            else
            {
                b[(i/x+1)*x+i%x].setBackground(Color.black);
                b[(i/x+1)*x+i%x].setText("Black");
                b[(i/x+1)*x+i%x].setForeground(Color.black);
            }
        }

        if(i%x!=x-1)
        {
            if(b[i+1].getBackground()==Color.black)
            {
                b[i+1].setBackground(Color.red);
                b[i+1].setText("red");
                b[i+1].setForeground(Color.red);
            }
            else
            {
                b[i+1].setBackground(Color.black);
                b[i+1].setText("Black");
                b[i+1].setForeground(Color.black);
            }
        }

        if(i%x!=0)
        {
            if(b[i-1].getBackground()==Color.black)
            {
                b[i-1].setBackground(Color.red);
                b[i-1].setText("red");
                b[i-1].setForeground(Color.red);
            }
            else
            {
                b[i-1].setBackground(Color.black);
                b[i-1].setText("Black");
                b[i-1].setForeground(Color.black);
            }
        }
        int judge=0;
        for(JButton bi:b)
        {
            if(bi.getBackground()==Color.black)
                judge++;
        }
        if(judge==0)
        {
            JOptionPane.showMessageDialog(null,"Success!");
            System.exit(0);
        }
        else
            judge=0;
    }
}