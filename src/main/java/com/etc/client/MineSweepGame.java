package com.etc.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.Timer;


public class MineSweepGame {
	// 声明需要的组件
	private JFrame frame;
	private Container contentPane;
	private JPanel menuPanel, timePanel, gamePanel;
	private JLabel timeLabel, resultLabel, mineCountLabel;
	private JMenuItem menuItem1, menuItem2, menuItem3;

	private JButton[][] buttons;
	private int[][] buttonsValue;
	private boolean[][] buttonsFlag;


	private int timeLength = 0;
	private int row, col;
	private int mineCount = 10;

	private int mineRealCount = 10;
	private boolean winGame = false;

	private Timer timer;
	private int gameStatus=0;

	private int level=1;

	// 在构造方法中，创建组件，进行赋值
	public MineSweepGame() {
		row = 9;
		col = 9;

		frame = new JFrame("中软卓越扫雷游戏");
		contentPane = frame.getContentPane();

		menuPanel = new JPanel();
		timePanel = new JPanel();
		gamePanel = new JPanel();

		timeLabel = new JLabel("游戏时间: " + new Integer(timeLength).toString()
				+ "秒     ");
		resultLabel = new JLabel("   状态：游戏进行中");
		mineCountLabel = new JLabel("  剩余地雷个数：" + mineCount);

		this.initButtonsAllValues();

		timer=new Timer(1000,new TimerActionListener());

	}
	//对按钮相关的值进行初始化
	public void initButtonsAllValues() {
		buttons = new JButton[row + 2][col + 2];
		buttonsValue = new int[row + 2][col + 2];
		buttonsFlag=new boolean[row+2][col+2];

		for (int i = 0; i < row + 2; i++) {
			for (int j = 0; j < col + 2; j++) {
				buttons[i][j] = new JButton();
				buttons[i][j].setMargin(new Insets(0, 0, 0, 0));
				buttons[i][j].setFont(new Font(null, Font.BOLD, 25));
				buttons[i][j].setText("");

				buttonsValue[i][j] = 0;
				buttonsFlag[i][j]=false;
			}
		}
	}

	// 对组件进行布局
	public void initGame() {
		JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu("游戏设置");
		menuItem1 = new JMenuItem("初级");
		menuItem2 = new JMenuItem("中级");
		menuItem3 = new JMenuItem("再来一次");
		menuBar.add(menu);
		menu.add(menuItem1);
		menu.add(menuItem2);
		menu.add(menuItem3);
		menuPanel.add(menuBar);

		timePanel.add(timeLabel);
		timePanel.add(mineCountLabel);
		timePanel.add(resultLabel);

		gamePanel.setLayout(new GridLayout(row, col, 0, 0));
		for (int i = 1; i <= row; i++) {
			for (int j = 1; j <= col; j++) {
				gamePanel.add(buttons[i][j]);
			}
		}

		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(menuPanel, BorderLayout.NORTH);
		panel.add(timePanel, BorderLayout.SOUTH);
		contentPane.add(panel, BorderLayout.NORTH);
		contentPane.add(gamePanel, BorderLayout.CENTER);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setSize(297, 377);
		frame.setBounds(400, 100, 400, 500);
		frame.setVisible(true);

		// 设置地雷，计算数字，注册监听器
		setMines(mineCount);
		setButtonValue();
		addListener();
	}

	// 设置地雷
	public void setMines(int mineCount) {
		this.mineCount = mineCount;
		int[] randomValue = new int[mineCount];
		// mineCount是地雷的个数，先获得10个不重复的随机数，然后通过随机数计算出雷的位置，保证随机性，最后用整数值标记雷，10
		for (int i = 0; i < mineCount; i++) {

			int temp = (int) (Math.random() * row * col);
			for (int j = 0; j < randomValue.length; j++) {
				if (randomValue[j] == temp) {
					temp = (int) (Math.random() * row * col);
					j = 0;
				}
			}
			randomValue[i] = temp;
			// 把随机数转换成坐标
			int x = randomValue[i] / col + 1;
			int y = randomValue[i] % col + 1;
			// 对应坐标的位置设置为地雷
			buttonsValue[x][y] = 10;

			// 临时显示地雷位置，作为测试使用。每次运行，随机产生。
//			 buttons[x][y].setText("Q");
		}
	}

	// 对非地雷的按钮值进行计算，周围没有地雷的，默认值为0，有雷的，显示地雷的个数。
	public void setButtonValue() {
		for (int i = 1; i <= row; i++) {
			for (int j = 1; j <= col; j++) {
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
					// 将数字显示，作为本阶段测试
//					buttons[i][j].setText(new Integer(buttonsValue[i][j])
//					.toString());
				}
			}
		}
	}

	class TimerActionListener implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			timeLength++;
			timeLabel.setText("游戏时间: "+new Integer(timeLength).toString()+"秒     ");

		}

	}

	class ButtonActionListener implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			if(gameStatus==0){
				timer.start();
			}
			gameStatus=1;

			for (int i = 1; i <= row; i++) {
				for (int j = 1; j <= col; j++) {
					if (buttons[i][j] == e.getSource()) {
						if (buttons[i][j].getText().equals("Q")) {
							if (buttonsValue[i][j] == 0) {
								mineCount++;
								mineCountLabel.setText("  剩余地雷个数：" + mineCount);
								markZero(i, j);
								isWinner();
							} else if (buttonsValue[i][j] == 10) {
								markMine(i, j);
								stopGame();
							} else {
								buttons[i][j].setText(new Integer(
										buttonsValue[i][j]).toString());
								mineCount++;
								mineCountLabel.setText("  剩余地雷个数：" + mineCount);
								markNumber(i, j);
								isWinner();
							}
						} else {
							if (buttonsValue[i][j] == 0) {
								markZero(i, j);
							} else if (buttonsValue[i][j] == 10) {
								markMine(i, j);
								stopGame();
							} else {
								markNumber(i, j);
							}
						}
					}
				}
			}

		}

	}

	class FindMineMouseListener extends MouseAdapter{
		public void mouseClicked(MouseEvent e) {
			if(gameStatus==0){
				timer.start();
			}
			gameStatus=1;
			for(int i=1;i<=row;i++){
				for(int j=1;j<=col;j++){
					if(e.getSource()==buttons[i][j]&&e.getButton()==MouseEvent.BUTTON3){
						findMine(i,j);
					}
				}
			}
		}

	}

	public void addListener(){
		for(int i=1;i<=row;i++){
			for(int j=1;j<=col;j++){
				buttons[i][j].addActionListener(new ButtonActionListener());
				buttons[i][j].addMouseListener(new FindMineMouseListener());
			}
		}
		menuItem1.addActionListener(new MenuItemListener());
		menuItem2.addActionListener(new MenuItemListener());
		menuItem3.addActionListener(new MenuItemListener());
	}

	public void markNumber(int i,int j){
		buttons[i][j].setText(new Integer(buttonsValue[i][j]).toString());
		buttons[i][j].setEnabled(false);
		buttonsFlag[i][j]=true;
	}

	public void markMine(int i,int j){
		buttons[i][j].setForeground(Color.red);
		buttons[i][j].setText("Q");
		buttons[i][j].setEnabled(false);
		buttonsFlag[i][j]=true;
	}

	public void markZero(int i,int j){
		buttons[i][j].setEnabled(false);
		if (buttonsFlag[i][j] == true && !buttons[i][j].getText().equals("Q")){
			return;
		}else{
			buttonsFlag[i][j]=true;

			if(buttons[i][j].getText().equals("Q")&&buttonsValue[i][j]!=10){
				mineCount++;
				mineCountLabel.setText("  剩余地雷个数：" + mineCount);
				isWinner();
			}

			if(buttonsValue[i][j]!=10&&buttonsValue[i][j]!=0){
				markNumber(i,j);
			}

			if(buttonsValue[i][j]==0){
				buttons[i][j].setText("");
				for(int s=i-1;s>=0&&s<=row&&s<=i+1;s++)
					for(int t=j-1;t>=0&&t<=col&&t<=j+1;t++)
						markZero(s,t);
			}
		}
	}

	public void findMine(int i,int j){
		buttonsFlag[i][j]=true;
		buttons[i][j].setForeground(Color.red);
		buttons[i][j].setText("Q");

		mineCount--;
		mineCountLabel.setText("  剩余地雷个数："+mineCount);

		if (buttonsValue[i][j] == 10) {
			mineRealCount--;
		}
		isWinner();
	}

	public void isWinner() {
		if (mineRealCount == 0&&mineCount==0) {
			winGame = true;
			stopGame();
		}
	}

	public void stopGame() {
		for (int i = 1; i <= row; i++) {
			for (int j = 1; j <= col; j++) {
				if (buttonsFlag[i][j] == false) {
					if (buttonsValue[i][j] == 0) {
						buttons[i][j].setText("");
					} else if (buttonsValue[i][j] == 10) {
						buttons[i][j].setText("Q");
					} else {
						buttons[i][j].setText(new Integer(buttonsValue[i][j])
								.toString());
					}
				}
				buttons[i][j].setEnabled(false);
				if (winGame) {
					resultLabel.setText("状态： 恭喜你，你赢了！");
				} else {
					resultLabel.setText("状态： 你踩到地雷了，输了！");
				}
			}
		}
		timer.stop();
	}

	//菜单项事件监听器
	class MenuItemListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if(e.getSource()==menuItem1){
				level=1;
				relayoutGame(level);
			}
			if(e.getSource()==menuItem2){
				level=2;
				relayoutGame(level);
			}
			if(e.getSource()==menuItem3){
				relayoutGame(level);
			}
		}
	}

	//	刷新游戏界面
	public void refreshGamePanel(){
		frame.remove(gamePanel);
		this.initButtonsAllValues();

		gamePanel=new JPanel();
		gamePanel.setLayout(new GridLayout(row,col,0,0));
		for(int i=1;i<=row;i++){
			for(int j=1;j<=col;j++){
				gamePanel.add(buttons[i][j]);
			}
		}

		timeLabel.setText("游戏时间: "+new Integer(timeLength).toString()+"秒     ");
		mineCountLabel.setText("  剩余地雷个数："+mineCount);
		resultLabel.setText("   状态：游戏进行中");

		contentPane.add(gamePanel,BorderLayout.CENTER);

		addListener();
		this.setMines(mineCount);
		this.setButtonValue();
	}


	//	重新开始游戏
	public void relayoutGame(int level){
		timer.stop();
		winGame=false;
		gameStatus=0;
		timeLength=0;

		if(level==1){
			this.mineRealCount=10;
			this.mineCount=10;
			row=9;
			col=9;
			refreshGamePanel();
		}

		if(level==2){
			this.mineRealCount=20;
			this.mineCount=20;
			row=12;
			col=12;
			refreshGamePanel();
		}
	}

}



