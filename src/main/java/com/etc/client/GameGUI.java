package com.etc.client;

import com.etc.service.GameService;
import com.etc.service.Impl.GameServiceImpl;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

/**
 * Created by whitewave on 2015/9/1.
 */
public class GameGUI {
    private int timeLength=0;
    private int mineCount=10;
    private JFrame frame;
    private JPanel topPanel,menuPanel,timePanel,gamePanel;
    private JMenuBar jMenuBar;
    private JMenu jMenu;
    private JMenuItem jMenuItem1,jMenuItem2,jMenuItem3;
    private JButton[][] jButtons;
    public int[][] buttonsValue;
    private JLabel timeLabel,resultLabel,mineCountLabel;
    public GameGUI(){
        frame=new JFrame("扫雷游戏");
        topPanel=new JPanel();
        menuPanel=new JPanel();
        timePanel=new JPanel();
        gamePanel=new JPanel();
        jMenuBar=new JMenuBar();
        jMenu=new JMenu("游戏设置");
        jMenuItem1=new JMenuItem("初级");
        jMenuItem2=new JMenuItem("中级");
        jMenuItem3=new JMenuItem("开始一局");
        jMenuBar.add(jMenu);
        jMenu.add(jMenuItem1);
        jMenu.add(jMenuItem2);
        jMenu.add(jMenuItem3);
        menuPanel.add(jMenuBar);

        jButtons = new JButton[11][11];
        buttonsValue=new int[11][11];
        for (int i=0;i<11;i++){
            for (int j=0;j<11;j++){
                jButtons[i][j]=new JButton();
                buttonsValue[i][j]=0;
            }
        }

        //设置地雷
        int[] Count;
        Count=new int[mineCount];
        Random random = new Random();
        for (int n=0;n<mineCount;n++){
            int temp=random.nextInt(81);
            for (int m=0;m<Count.length;m++){
                if (Count[m]==temp){
                    temp=random.nextInt(81);
                    m=0;
                }
            }
            Count[n]=temp;
            int x=Count[n]/9+1;
            int y=Count[n]%9+1;
            buttonsValue[x][y]=10;
            jButtons[x][y].setText("Q");
        }
        //设置周围地雷数
        for (int i = 1; i <= 9; i++) {
            for (int j = 1; j <= 9; j++) {
                if (buttonsValue[i][j] != 10) {
                    for (int x = j - 1; x <= j + 1; x++) {
                        if (buttonsValue[i - 1][x] == 10) {
                            buttonsValue[i][j]++;
                        }
                        if (buttonsValue[i + 1][x] == 10) {
                            buttonsValue[i][j]++;
                        }
                    }
                    if (buttonsValue[i][j - 1] == 10) {
                        buttonsValue[i][j]++;
                    }
                    if (buttonsValue[i][j + 1] == 10) {
                        buttonsValue[i][j]++;
                    }
					jButtons[i][j].setText(new Integer(buttonsValue[i][j]).toString());
                }
            }
        }

        timeLabel=new JLabel("游戏时间："+new Integer(timeLength).toString()+"秒");
        resultLabel=new JLabel("状态：游戏进行中");
        mineCountLabel=new JLabel("剩余地雷数："+mineCount);
        Container container=frame.getContentPane();
        timePanel.add(timeLabel);
        timePanel.add(resultLabel);
        timePanel.add(mineCountLabel);
        gamePanel.setLayout(new GridLayout(9, 9));
        for (int i=1;i<10;i++){
            for (int j=1;j<10;j++){
                gamePanel.add(jButtons[i][j]);
            }
        }
        topPanel.setLayout(new BorderLayout());
        topPanel.add(menuPanel, BorderLayout.NORTH);
        topPanel.add(timePanel,BorderLayout.SOUTH);
        container.setLayout(new BorderLayout());
        container.add(topPanel, BorderLayout.NORTH);
        container.add(gamePanel,BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(297,377);
        frame.setBounds(100,100,500,500);
        frame.setVisible(true);
    }
}
