import javax.swing.*;
import java.awt.*;

/**
 * Created by whitewave on 2015/9/1.
 */
public class TestGUI {
    private int timeLength=0;
    private int mineCount=10;
    public TestGUI(){
        JFrame frame=new JFrame("扫雷游戏");
        JPanel panel1=new JPanel();
        JPanel panel2=new JPanel();
        JPanel menuPanel=new JPanel();
        JMenuBar menuBar=new JMenuBar();
        JMenu jMenu=new JMenu("游戏设置");
        JMenuItem jMenuItem1=new JMenuItem("初级");
        JMenuItem jMenuItem2=new JMenuItem("中级");
        JMenuItem jMenuItem3=new JMenuItem("再来一次");
        menuBar.add(jMenu);
        jMenu.add(jMenuItem1);
        jMenu.add(jMenuItem2);
        jMenu.add(jMenuItem3);
        menuPanel.add(menuBar);

        JButton[][] jButtons = new JButton[11][11];
        for (int i=0;i<11;i++){
            for (int j=0;j<11;j++){
                jButtons[i][j]=new JButton();
            }
        }
        JLabel timeLabel=new JLabel("游戏时间："+new Integer(timeLength).toString()+"秒");
        JLabel resultLabel2=new JLabel("状态：游戏进行中");
        JLabel mineCountLabel=new JLabel("剩余地雷数："+mineCount);
        Container container=frame.getContentPane();
        panel1.add(timeLabel);
        panel1.add(resultLabel2);
        panel1.add(mineCountLabel);
        panel2.setLayout(new GridLayout(10, 10));
        for (int i=1;i<10;i++){
            for (int j=1;j<10;j++){
                panel2.add(jButtons[i][j]);
            }
        }
        JPanel panel=new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(menuPanel, BorderLayout.NORTH);
        panel.add(panel1,BorderLayout.SOUTH);
        container.setLayout(new BorderLayout());
        container.add(panel, BorderLayout.NORTH);
        container.add(panel2,BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(297,377);
        frame.setBounds(100,100,500,500);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new TestGUI();
    }
}
