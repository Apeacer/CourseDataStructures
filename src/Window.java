import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.*;

import javax.sql.rowset.CachedRowSet;
import javax.swing.*;
import javax.swing.text.JTextComponent;

//运行方法
class Window
{
	public static void main (String[] arts)
	{
		JFrame window=new JFrame("内部排序算法分析");//定义框框
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//叉子的功能
		window.setBounds(150, 100, 1000, 450);//窗口位置
		
		JTabbedPane tab = new JTabbedPane();//多面板切换功能
		tab.addTab("内部排序算法的性能分析", new Analyse());//面板一，用于放题目九
		tab.addTab("一元稀疏多项式计算器", new Calculator());//面板二用于放题目一
		tab.addTab("公交线路优化查询", new Bus());//面板三用于放题目二十
		
		window.add(tab);//善后
		window.setResizable(true);
		window.setVisible(true);//可见
		window.pack();//初始大小合适
	}
}
//end~------------------------------------------------------------------------
//9.内部排序算法的性能分析-----------------------------------------------------------
class Analyse extends JPanel {

	String data;//get textarea 
	Vector <Integer> count;//储存输入的数组;
	JTextArea ta,ta1,ta2,ta3,ta4,ta5;//文本框
	JTextField ta6;
    
	int little;//随机数个数
	int compare1=0,compare2=0,compare3=0,compare4=0,compare5=0;//比较数
	int foot1=0,foot2=0,foot3=0,foot4=0,foot5=0;//移动数
	
	public Analyse()//构造函数
	{
		setPreferredSize(new Dimension (1000,450));//size
		setLayout (null);//绝对布局
		
		count=new Vector<Integer>();//初始化组件
		JLabel l1=new JLabel("请输入待测序列，用空格隔开~"),l2=new JLabel("个");
		ta=new JTextArea();
		ta1=new JTextArea();
		ta2=new JTextArea();
		ta3=new JTextArea();
		ta4=new JTextArea();
		ta5=new JTextArea();
		ta6=new JTextField();
		JButton b1=new JButton("确定输入");
		JButton b2=new JButton("一键比较");
		JButton b3=new JButton("清空输入");
		JButton b4=new JButton("自动输入");
		
		b1.addActionListener(new b1Listener());//设置监听方法
		b2.addActionListener(new b2Listener());
		b3.addActionListener(new b3Listener());
		b4.addActionListener(new b4Listener());
		
		JScrollPane sp = new JScrollPane(ta);//滑动条设置
		JScrollPane sp1 = new JScrollPane(ta1);
		JScrollPane sp2 = new JScrollPane(ta2);
		JScrollPane sp3 = new JScrollPane(ta3);
		JScrollPane sp4 = new JScrollPane(ta4);
		JScrollPane sp5 = new JScrollPane(ta5);
		
		l1.setBounds(5, 20, 200, 15);//布局与尺寸
		l2.setBounds(282, 417, 15, 15);
		sp.setBounds(5, 45, 990, 25);
		sp1.setBounds(005,85,195,300);
		sp2.setBounds(204,85,195,300);
		sp3.setBounds(403,85,195,300);
		sp4.setBounds(602,85,195,300);
		sp5.setBounds(801,85,195,300);
		ta6.setBounds(250, 415, 30, 20);//随几个数
		b4.setBounds(300, 400, 100, 50);
		b1.setBounds(400, 400, 100, 50);
		b2.setBounds(500, 400, 100, 50);
		b3.setBounds(600, 400, 100, 50);
		//添加到面板
		add(l1);add(l2);                
		add(b1);add(b2);add(b3);add(b4);
		add(sp);add(sp1);add(sp2);add(sp3);add(sp4);add(sp5);add(ta6);
	}

//按钮确定输入的监听----------------------------------------------------------------
    private class b1Listener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
	    {
	        data=ta.getText();	//得到输入
	        if(data.length()==0||data.equals("要输入哦，不然会乱的~")||data.equals("要输入随机输入的个数~"))//输入为空
		    {
		    	ta.setText("要输入哦，不然会乱的~");
		    }
		    else//不为空
		    {
		    	String[]msg=data.split(" "); //拆分输入
		    	for(int i=0;i<msg.length;i++)//转型并加入到vector中
			    {
				    count.add(Integer.parseInt(msg[i]));
			    }
			    //初始化比较数移动数
			    compare1=0;compare2=0;compare3=0;compare4=0;compare5=0;
			    foot1=0;foot2=0;foot3=0;foot4=0;foot5=0;		
		    }
	    }
    }

//按钮排序的监听------------------------------------------------------------------
    private class b2Listener implements ActionListener
    {
	    public void actionPerformed(ActionEvent e) 
	    {	
		    //五种排序的缓存
		    Vector <Integer> count1=new Vector<Integer>();
		    Vector <Integer> count2=new Vector<Integer>();
		    Vector <Integer> count3=new Vector<Integer>();
		    Vector <Integer> count4=new Vector<Integer>();
		    Vector <Integer> count5=new Vector<Integer>();
		    count5.add(0);//堆排序，头滞空
		    for(int z=0;z<count.size();z++)//加入排序数据
		    {
			    count1.add(count.get(z));
			    count2.add(count.get(z));
			    count3.add(count.get(z));
			    count4.add(count.get(z));
			    count5.add(count.get(z));
		    }
            //排序并显示
		    ta1.setText("冒泡排序：\r\n");
		    ta2.setText("直接排序：\r\n");
		    ta3.setText("选择排序：\r\n");
		    ta4.setText("快速排序：\r\n");
		    ta5.setText("堆排序：\r\n");
		    BubbleSort(count1);//冒泡	
		    InsertSort(count2);//直接插入
		    SelectSort(count3);//简单选择
		    QuickSort(count4, 0, count4.size()-1);//快速
		    HeapSort(count5);//堆	
		    }
	    }

//清除按钮的监听类-----------------------------------------------------------------
    private class b3Listener implements ActionListener
    {
		public void actionPerformed(ActionEvent e)
		{   
			ta.setText("");//clear text area
			ta1.setText("");
			ta2.setText("");
			ta3.setText("");
			ta4.setText("");
			ta5.setText("");
			//clear data
			compare1=0;compare2=0;compare3=0;compare4=0;compare5=0;//compare count
			foot1=0;foot2=0;foot3=0;foot4=0;foot5=0;//clear foot
			little=0;
			//clear vector
			count.clear();
		}
    }

//随机生成按钮的监听类--------------------------------------------------------------
    private class b4Listener implements ActionListener
    {
		public void actionPerformed(ActionEvent e)
		{
			ta.setText("");//clear TxtArea
			String data2=ta6.getText();//得到随几个数
			if(data2.length()==0)//未输入
			{
				ta.setText("要输入随机输入的个数~");
			}
			else//已输入
			{
				little=Integer.parseInt(data2);//随机数个数转型
				Random r=new Random();//随机数产生
				for(int i=0;i<little;i++)
				{
					int number=r.nextInt(101);
					ta.append(number+"");
					if(i!=little-1)
					{
						ta.append(" ");//随机数输出
					}
				}
				
			}
		}
    }//监听 end-----------------------------------------------------------------
//冒泡-------------------------------------------------------------------------
	public void BubbleSort(Vector<Integer> count1)
	{
		for(int i1=count1.size();i1>1;i1--)
		{
			for(int j1=0;j1<(count1.size()-1);j1++)
			{
				compare1++;
				if(count1.get(j1)>count1.get(j1+1))
				{
					int temp=count1.get(j1);
					count1.set(j1,count1.get(j1+1) );
					count1.set(j1+1, temp);
					foot1+=3;
//////输出					
					for(int v=0;v<count1.size();v++)
					{
						ta1.append(count1.get(v)+" ");
						if(v==count1.size()-1)
							ta1.append(" 比较:"+compare1+"次 "+"移动:"+foot1+"次\r\n");
					}
				}
			}
		}
	}
	
//直接插入----------------------------------------------------------------------
	public void InsertSort(Vector<Integer> count2)
	{
	    for(int i2=1;i2<(count2.size());i2++)
		{
		    int t=count2.get(i2);
			foot2++;
			int j2;
			for (j2=i2-1;j2>=0&&t<count2.get(j2);j2--)
			{
				compare2++;
				count2.set(j2+1, count2.get(j2));
				foot2++;
			}
			foot2++;
			compare2++;
			count2.set(j2+1, t);
//////输出			
			for(int v=0;v<count2.size();v++)
			{
				ta2.append(count2.get(v)+" ");
				if(v==count2.size()-1)
					ta2.append(" 比较:"+compare2+"次 "+"移动:"+foot2+"次\r\n");
			}
		}
	}
	
//简单选择------------------------------------------------------------
	public void SelectSort(Vector<Integer> count3)
	{
	    for(int i3=count3.size();i3>1;i3--)
		{
			int pos=0;
			for(int j3=1;j3<i3;j3++)
			{
				compare3++;
				if(count3.get(pos)<count3.get(j3))
				{
					pos=j3;
							
				}
			}
			int temp2=count3.get(pos);
			count3.set(pos,count3.get(i3-1));
			count3.set(i3-1, temp2);
			foot3+=3;
//////输出
			for(int v=0;v<count3.size();v++)
			{
				ta3.append(count3.get(v)+" ");
				if(v==count3.size()-1)
					ta3.append(" 比较:"+compare3+"次 "+"移动:"+foot3+"次\r\n");
			}
					
		}
	}
	
//快速排序方法--------------------------------------------------------------------
	public void QuickSort(Vector<Integer> count6,int start,int end)
	{
		Vector<Integer>count4=count6;
		int i4=start,j4=end;
		while(i4<j4)
		{
			while(i4<j4&&count4.get(i4)<=count4.get(j4))
			{
				j4--;
				compare4++;
			}
			if(i4<j4)
			{
				int temp3=count4.get(i4);
				count4.set(i4, count4.get(j4));
				count4.set(j4, temp3);
				foot4+=3;
////////输出
				for(int v=0;v<count4.size();v++)
				{
					ta4.append(count4.get(v)+" ");
					if(v==count4.size()-1)
						ta4.append(" 比较:"+compare4+"次 "+"移动:"+foot4+"次\r\n");
				}
				
			}
			while(i4<j4&&count4.get(i4)<count4.get(j4))
			{
				i4++;
				compare4++;
			}
			if(i4<j4)
			{
				int temp3=count4.get(i4);
				count4.set(i4, count4.get(j4));
				count4.set(j4, temp3);
				foot4+=3;
////////输出
				for(int v=0;v<count4.size();v++)
				{
					ta4.append(count4.get(v)+" ");
					if(v==count4.size()-1)
						ta4.append(" 比较:"+compare4+"次 "+"移动:"+foot4+"次\r\n");
				}
			}
		}
		if(i4-start>1)
		{
			QuickSort(count6,start,i4-1);
		}
		if(end-i4>1)
		{
			QuickSort(count6,i4+1,end);
		}
	}
	
//堆--------------------------------------------------------------------------
	public void HeapSort(Vector<Integer> count5)
	{
		//初始化堆
		int current=count5.size()-1;
		for(int i5=current/2;i5>=1;i5--)
		{
			int y=count5.get(i5);
			foot5++;
			int c=2*i5;
			while(c<=current)
			{
				compare5+=2;
				if(c<current&&count5.get(c)<count5.get(c+1))
				{
					c++;
				}
				if(y>=count5.get(c))
				{
					break;
				}
				count5.set((c/2), count5.get(c));
				c*=2;
				foot5++;
			}
			count5.set((c/2), y);
			foot5++;
//////输出
			for(int v=count5.size()-1;v>0;v--)
			{
				ta5.append(count5.get(v)+" ");
				if(v==1)
					ta5.append(" 比较:"+compare5+"次 "+"移动:"+foot5+"次\r\n");
			}
		}
		//堆删除排序
		Vector<Integer> count6=new Vector<Integer>();
		int current2=count5.size()-1;
		for(int j6=count5.size()-1;j6>=1;j6--)
		{
			count6.add(count5.get(1));
			
		    int y=count5.get(current2--);
		    foot5++;
		    int i6=1,ci=2;
		    while(ci<=current2)
		    {
			    compare5+=2;
			    if(ci<current2&&count5.get(ci)<count5.get(ci+1))
				    ci++;
			    if(y>count5.get(ci))
				    break;
			    count5.set(i6, count5.get(ci));
			    foot5++;
			    i6=ci;
			    ci*=2;
		    }
		    count5.set(i6, y);
		    foot5++;
//////输出
		    for(int v=j6-1;v>0;v--)
		    {
			    ta5.append(count5.get(v)+" ");
			
		    }
		    for(int j7=count6.size()-1;j7>=0;j7--)
		    {
			    ta5.append(count6.get(j7)+" ");
			    if(j7==0)
				    ta5.append(" 比较:"+compare5+"次 "+"移动:"+foot5+"次\r\n");
		    }
		}
	}
//Sort end----------------------------------------
}//题目一end~-------------------------------------------------------------------

//1.一元稀疏多项式简单计算器----------------------------------------------------------
class Calculator extends JPanel
{
	JTextArea ta1,ta2,ta3;//声明文本框
	String data1,result;
	ArrayList<String>datas;
	ArrayList<String>sign;
	
	public Calculator()
	{
		setPreferredSize(new Dimension (1000,450));//size
		setLayout (null);//布局
		//组件
		ta1=new JTextArea();//txtarea
		ta2=new JTextArea();
		ta3=new JTextArea();
		JScrollPane sp1 = new JScrollPane(ta1);//滑动条
		JScrollPane sp2 = new JScrollPane(ta2);
		JScrollPane sp3 = new JScrollPane(ta3);
		JButton b1=new JButton("+");//按钮
		JButton b2=new JButton("-");
		JButton b3=new JButton("=");
		JButton b4=new JButton("AC");
		JButton b5=new JButton("随机输入x");
		
		datas=new ArrayList<String>();//每次输入
		sign=new ArrayList<String>();//每次符号
		data1="";
		result="";

		ta2.setLineWrap(true);//自动换行
		b1.addActionListener(new b1Listener());//监听方法
		b2.addActionListener(new b2Listener());
		b3.addActionListener(new b3Listener());
		b4.addActionListener(new b4Listener());
		b5.addActionListener(new b5Listener());
		
		sp1.setBounds(100,30,640,30);//布局
		sp2.setBounds(100,90,800,300);
		sp3.setBounds(740, 30, 160, 30);
		b1.setBounds(260, 60, 160, 30);
		b2.setBounds(420, 60, 160, 30);
		b3.setBounds(580, 60, 160, 30);
		b4.setBounds(100, 60, 160, 30);
		b5.setBounds(740, 60, 160, 30);
		
		add(b1);add(b2);add(b3);add(b4);add(b5);//添加组件到面板
		add(sp1);add(sp2);add(sp3);
		
	}
//"+"按钮监听--------------------------------------------------------
	private class b1Listener implements ActionListener
	{
	    public void actionPerformed(ActionEvent e)
		{
			String cache=ta1.getText();//得到输入
			datas.add(cache);//加入到结果运算字符串中
			sign.add("+");//加符号
			data1=data1+cache+"+";
			ta1.setText("");//清空输入
		}
	}
//"-"按钮监听----------------------------------------------------------	
	private class b2Listener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			String cache=ta1.getText();//同"+"
			datas.add(cache);
			sign.add("-");
			data1=data1+cache+"-";
			ta1.setText("");
		}
	}
//"="按钮监听----------------------------------------------------------
	private class b3Listener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			String cache=ta1.getText();//最后一个输入
			datas.add(cache);
			data1=data1+cache;
			ta1.setText("");
				
			ta2.setText("");
			for(int i=0;i<datas.size();i++)
			{
				ta2.append("("+datas.get(i)+")");
				if(i<datas.size()-1)
				    ta2.append(sign.get(i));
				if(i==datas.size()-1)
					ta2.append("=");
			}
			Sort();//排序并显示的方法
				
		}
	}
	//"AC"按钮监听-------------------------------------------------------------
	private class b4Listener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			ta1.setText("");//各种clear
			ta2.setText("");
			datas.clear();
			sign.clear();
			data1="";
		}
	}
	//随机输入按钮监听-----------------------------------------------------------
	private class b5Listener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			Random random=new Random();
			int x=random.nextInt(101);
			ta3.setText(x+"");
		}
	}
	
	public void Sort()
	{
		//数据处理
		for(int i=0;i<data1.length();i++)
		{
			//在-前面加+,且不是--，^-的情况
			if(i>0&&data1.substring(i,i+1).equals("-")&&!data1.substring(i-1,i).equals("^")&&!data1.substring(i+1,i+2).equals("-")&&!data1.substring(i-1,i).equals("-"))
			{
				String s1=data1.substring(0,i);
				String s2=data1.substring(i,data1.length());
				data1=s1+"+"+s2;
				i++;
			}
            //--情况，把--变为+
			if(i>0&&i<data1.length()-1&&data1.substring(i,i+1).equals("-")&&data1.substring(i+1,i+2).equals("-"))
			{
				String s1=data1.substring(0,i);
				String s2=data1.substring(i+2,data1.length());
				data1=s1+"+"+s2;
				i--;
			}
		}		
		String msg1=data1;//-变成+（-）
		String msgs1[]=msg1.split("\\+");//单项式分开
		int [][]msgs2=new int[msgs1.length][2];//声明2维数组来存系数和指数
		//单项式二数分开存入msgs2
	    for(int m=0;m<msgs1.length;m++)
	    {
	    	for(int o=0;o<msgs1[m].length();o++)//把每一个单项式从头搜索
	    	{   
	    		//判断指为1时-------------------------------------------------
	    		if(msgs1[m].substring(o, o+1).equals("x")&&o==msgs1[m].length()-1)
	    		{
	    			String[]cache=msgs1[m].split("x");

	    			if(msgs1[m].length()==1)//系数为1时
	    			{
	    				msgs2[m][0]=1;
	    			}
	    			else //系数不为1
	    			{
	    				if(msgs1[m].length()==2)
	    					msgs2[m][0]=-1;
	    				else
	    				msgs2[m][0]=Integer.parseInt(cache[0]);	
	    			}
	    			msgs2[m][1]=1;	
	    			break;
	    		}
	    		//指数不为1------------------------------------------------
	    		if(msgs1[m].substring(o, o+1).equals("x") &&msgs1[m].substring(o+1,o+2).equals("^"))
	    		{
	    			String[]cache=msgs1[m].split("x\\^");
    				if(msgs1[m].substring(0, 1).equals("x"))//系数为1
    				{
    					msgs2[m][0]=1;
    				}
    				if(msgs1[m].substring(0, 2).equals("-x"))//系数为负1
    				{
    					msgs2[m][0]=-1;
    				}
    				if(!msgs1[m].substring(0, 1).equals("x")&&!msgs1[m].substring(0, 2).equals("-x"))//正常 
    				{
    					msgs2[m][0]=Integer.parseInt(cache[0]);	
					}
    				msgs2[m][1]=Integer.parseInt(cache[1]);
    				break;
	    		}
	    		//常数----------------------------------------------------------
	    		if(o==msgs1[m].length()-1)
	    		{
	    			msgs2[m][0]=Integer.parseInt(msgs1[m]);
	    			msgs2[m][1]=0;
	    		}
	    		
	    	}    	
	    }
		//冒泡sort按降幂--------------------------------------
		for(int i8=msgs2.length;i8>1;i8--)
		{
			for(int j8=0;j8<(msgs2.length-1);j8++)
			{
				if(msgs2[j8][1]>msgs2[j8+1][1])
				{
					int temp=msgs2[j8][0];
					msgs2[j8][0]=msgs2[j8+1][0];
					msgs2[j8+1][0]=temp;
					
					int temp2=msgs2[j8][1];
					msgs2[j8][1]=msgs2[j8+1][1];
					msgs2[j8+1][1]=temp2;
					}
				}
		}

		//输出与计算多项式重排----------------------------------------------
//		int count=0;//计算
//		for(int i=0;i<msgs2.length-count;i++)
//		{
//			for(int j=i+1;j<msgs2.length-count;j++)
//			{
//				if(msgs2[i][1]==msgs2[j][1])
//				{
//					msgs2[i][0]+=msgs2[j][0];
//					int temp=msgs2[j][1];
//					msgs2[j][1]=msgs2[msgs2.length-count-1][1];
//					msgs2[msgs2.length-count-1][1]=temp;
//
//					int temp2=msgs2[j][0];
//					msgs2[j][1]=msgs2[msgs2.length-count-1][0];
//					msgs2[msgs2.length-count-1][0]=temp2;
//					count++;
//				}
//			}
//		}
		
		
		
		double result=0,x=Integer.parseInt(ta3.getText());
		String resultAnd="";
		for(int v2=0;v2<msgs2.length;v2++)//////////////////////////////
		{
			resultAnd+=(msgs2[v2][0]+"*x^"+msgs2[v2][1]);
			if(v2!=msgs2.length-1&&msgs2[v2+1][0]>0)////////////
			{
				resultAnd+="+";
            }
			result+=(int)msgs2[v2][0]*Math.pow(x,msgs2[v2][1]);
		}
		ta2.append(resultAnd);
		ta2.append("\r\n多项式结果是："+result+"\r\n");
		//输出与计算多项式要求输出
		ta2.append(msgs2.length+",");//////////////////////////
		for(int v=msgs2.length-1;v>=0;v--)////////////////
		{
		    ta2.append(msgs2[v][0]+","+msgs2[v][1]);
		    if(v!=0)
		    	ta2.append(",");
		    
		}
	}
}

//题目二十公交车线路查询------------------------------------------------------------------------
class Bus extends JPanel
{
	//组件
	JTextArea ta1,ta2,ta3;
	JButton b1,b2,b3,b4;
	JLabel l1,l2;
	//显示缓存
	ArrayList<ArrayList<Integer>>lines;
	ArrayList<Double>time;
	ArrayList<Integer>change;
	ArrayList<Integer>spend;
	//应用类
	Map m;
	Find f;
	Core c;
	
	public Bus()
	{
		setPreferredSize(new Dimension (1000,450));//size
		setLayout(null);//绝对布局
		l1=new JLabel("此处输入起点站：");
		l2=new JLabel("此处输入目标站：");
		ta1=new JTextArea();
		ta2=new JTextArea();
		ta3=new JTextArea();
		b1=new JButton("输出时间最短路线");
		b2=new JButton("输出换乘最少路线");
		b3=new JButton("输出花费最底路线");
		b4=new JButton("清空输出");
		//监听
		b1.addActionListener(new b1Listener());
		b2.addActionListener(new b2Listener());
		b3.addActionListener(new b3Listener());
		b4.addActionListener(new b4Listener());
		//布局
		l1.setBounds(185, 70, 110, 20);
		ta1.setBounds(295, 70, 120, 20);
		l2.setBounds(185, 95, 110, 20);
		ta2.setBounds(295, 95, 120, 20);
		ta3.setBounds(185, 120, 630, 250);
		b1.setBounds(485, 70, 150, 20);
		b2.setBounds(485, 95, 150, 20);
		b3.setBounds(665, 70, 150, 20);
		b4.setBounds(665, 95, 150, 20);
		//加入
		add(l1);add(l2);
		add(ta1);add(ta2);add(ta3);
		add(b1);add(b2);add(b3);add(b4);
		//初始化
		lines=new ArrayList<ArrayList<Integer>>();
		spend=new ArrayList<Integer>();
		time=new ArrayList<Double>();
		change=new ArrayList<Integer>();
		
		m=new Map();
		f=new Find();
		c=new Core();
	}
	//时间最少监听
	private class b1Listener implements ActionListener
	{
	    public void actionPerformed(ActionEvent e)
		{   
	    	//空输入
	    	if(ta1.getText().equals("")||ta2.getText().equals(""))
	    	{
	    		ta3.append("请输入起点、终点~\r\n");
	    	}
	    	else //不为空
	    	{
	    		//初始化
	    		time.clear();
	    		lines.clear();
	    		ta3.setText("");
	    		//得到起点终点
	    		int Start=(int)ta1.getText().charAt(0)-97;
	    		int End=(int)ta2.getText().charAt(0)-97;
	    		if(Start>m.busLine.length||End>m.busLine.length)//超范围
	    		{
	    			ta3.append("输入有误~");
	    		}
	    		else
	    		{
	    			//调用方法
	    			lines=c.LeastTime(Start, End);
	    			time=c.times;
	    			//显示信息
	    			for(int i=0;i<lines.size();i++)
	    			{    
	    				for(int j=0;j<lines.get(i).size();j++)
	    				{
	    					if(j==0)//显示花费
	    					{
	    						DecimalFormat df = new DecimalFormat("#.00");
	    						ta3.append("需要时间："+df.format(time.get(i))+"h    ");
	    					}
	    					
	    					int p=lines.get(i).get(j)+65;//大写char值
	    					String pp=String.valueOf((char)p);//转化String
	    					ta3.append(pp);//显示路径
	    					
	    					if(j!=lines.get(i).size()-1)//显示箭头
	    						ta3.append("--->");
	    					if(j==lines.get(i).size()-1)
	    					{
	    						if(i==0||time.get(i)==time.get(0))
	    							ta3.append("    （最优）");//提示
	    						ta3.append("\r\n");//换行
	    					}
	    				}
	    			} 
	    		}
	    	}
		}
	}
	//换乘最少监听
	private class b2Listener implements ActionListener
	{
	    public void actionPerformed(ActionEvent e)
		{  
	    	//空输入
	    	if(ta1.getText().equals("")||ta2.getText().equals(""))
	    	{
	    		ta3.append("请输入起点、终点~\r\n");
	    	}
	    	else //不为空
	    	{
	    		//初始化
	    		change.clear();
	    		lines.clear();
	    		ta3.setText("");
	    		//得到起点终点
	    		int Start=(int)ta1.getText().charAt(0)-97;
	    		int End=(int)ta2.getText().charAt(0)-97;
	    		
	    		if(Start>m.busLine.length||End>m.busLine.length)//超范围
	    		{
	    			ta3.append("输入有误~");
	    		}
	    		else
	    		{
	    			//调用方法
	    			lines=c.LeastChange(Start, End);
	    			change=c.changes;
	    			//显示信息
	    			for(int i=0;i<lines.size();i++)
	    			{    
	    				for(int j=0;j<lines.get(i).size();j++)
	    				{
	    					if(j==0)//显示换乘次数
	    					{
	    						ta3.append("换乘次数："+change.get(i)+"次    ");
	    					}
	    					
	    					int p=lines.get(i).get(j)+65;//大写char值
	    					String pp=String.valueOf((char)p);//转化String
	    					ta3.append(pp);//显示路径
	    					
	    					if(j!=lines.get(i).size()-1)//显示箭头
	    						ta3.append("--->");
	    					if(j==lines.get(i).size()-1)
	    					{
	    						if(i==0||change.get(i)==change.get(0))
	    							ta3.append("    （最优）");//提示
	    						ta3.append("\r\n");//换行
	    					}
	    				}
	    			}
	    		}
	    		
	    	}
		}
	}
	//花费最少
	private class b3Listener implements ActionListener
	{
	    public void actionPerformed(ActionEvent e)
		{  
	    	//空输入
	    	if(ta1.getText().equals("")||ta2.getText().equals(""))
	    	{
	    		ta3.append("请输入起点、终点~\r\n");
	    	}
	    	else //不为空
	    	{

	    		//初始化
	    		spend.clear();
	    		lines.clear();
	    		ta3.setText("");
	    		//得到起点终点
	    		int Start=(int)ta1.getText().charAt(0)-97;
	    		int End=(int)ta2.getText().charAt(0)-97;
	    		
	    		if(Start>m.busLine.length||End>m.busLine.length)//超范围
	    		{
	    			ta3.append("输入有误~");
	    		}
	    		else
	    		{
	    			//调用方法
	    			lines=c.LeastSpend(Start, End);
	    			spend=c.spends;
	    			//显示信息
	    			for(int i=0;i<lines.size();i++)
	    			{    
	    				for(int j=0;j<lines.get(i).size();j++)
	    				{
	    					if(j==0)//显示换乘次数
	    					{
	    						ta3.append("花费金额："+spend.get(i)+"元    ");
	    					}
	    					
	    					int p=lines.get(i).get(j)+65;//大写char值
	    					String pp=String.valueOf((char)p);//转化String
	    					ta3.append(pp);//显示路径
	    					
	    					if(j!=lines.get(i).size()-1)//显示箭头
	    						ta3.append("--->");
	    					if(j==lines.get(i).size()-1)
	    					{
	    						if(i==0||spend.get(i)==spend.get(0))
	    							ta3.append("    （最优）");//提示
	    						ta3.append("\r\n");//换行
	    					}
	    				}
	    			}
	    		}
			}
		}
	}
	//清空监听
	private class b4Listener implements ActionListener
	{
	    public void actionPerformed(ActionEvent e)
		{  
	    	//初始化
	    	lines.clear();
	    	spend.clear();
	    	time.clear();
	    	change.clear();
	    	lines.clear();
	    	ta1.setText("");
	    	ta2.setText("");
	    	ta3.setText("");

		}
	}
	
}

//class map*******************************************************
class Map {
	//设坐标
    String position[]={"0,0","3,1","4,3","2,5","7,4","10,2","9,8","12,7"};
 
    //距离邻接矩阵
	int station[][] = {
			{100, 100, 100, 100, 100, 100, 100, 100},
			{100, 100, 100, 100, 100, 100, 100, 100},
			{100, 100, 100, 100, 100, 100, 100, 100},
			{100, 100, 100, 100, 100, 100, 100, 100},
			{100, 100, 100, 100, 100, 100, 100, 100},
			{100, 100, 100, 100, 100, 100, 100, 100},
			{100, 100, 100, 100, 100, 100, 100, 100},
			{100, 100, 100, 100, 100, 100, 100, 100}
			};
	//线路一：A->D->G->F
	//线路二：A->C->F->H
	//线路三：A->B->E->H
	//线路四：B->C->D
	
	//公交线路号
	int l1=0,l2=1,l3=2,l4=3,l5=4;
	//公交速度
	int[]v={6,3,4,7,10};
	//公交线路的价格
	int[] money={5,6,2,8,15};
	
	//为速度建立邻接矩阵
	int busLine[][] = {
			{0,l3,l2,l1,0,0,0,0},
			{0,0,l4,0,l3,0,0,0},
			{0,0,0,l4,0,l2,0,0},
			{0,0,0,0,0,0,l1,0},
			{0,0,0,0,0,l5,0,l3},
			{0,0,0,0,0,0,0,l2},
			{0,0,0,0,0,l1,0,0},
			{0,0,0,0,0,0,0,0}
			};
	//构造函数
	public Map() 
	{
		//为距离邻接矩阵赋值
		station[0][1]=(int)Distance(1,0);
		station[0][2]=(int)Distance(2,0);
		station[0][3]=(int)Distance(3,0);
		station[1][2]=(int)Distance(2,1);
		station[1][4]=(int)Distance(4,1);
		station[2][3]=(int)Distance(3,2);
		station[2][5]=(int)Distance(5,2);
		station[3][6]=(int)Distance(6,3);
		station[4][5]=(int)Distance(5,4);
		station[4][7]=(int)Distance(7,4);
		station[5][7]=(int)Distance(7,5);
		station[6][5]=(int)Distance(6,5);
		
	}
	//用坐标计算距离
	public double Distance(int j,int i)
	{
		
		String cacheJ[]=position[j].split(",");
		String cacheI[]=position[i].split(",");
		double x1=Integer.parseInt(cacheI[0]),x2=Integer.parseInt(cacheJ[0]);
		double y1=Integer.parseInt(cacheI[1]),y2=Integer.parseInt(cacheJ[1]);
		double distance=Math.sqrt((x1-x2)*(x1-x2)+(y1-y2)*(y1-y2));
		return distance;
	}
	//得到map邻接矩阵
	public int[][] getMap()
	{
		return station;
	}
	//得到速度邻接矩阵
	public int [][]getSpeed()
	{
		return busLine;
	}
}
//查询类class Find-----------------------------------------------------------------------
class Find {
	Map m;
	ArrayList<Integer> line;
	ArrayList<ArrayList<Integer>> lines;
	//构造函数
	public Find() 
	{
		//初始化对象
		m = new Map();
		line = new ArrayList<Integer>();
		lines = new ArrayList<ArrayList<Integer>>();
	}

	//找到s到e所有路径
	public void Search(int s, int e) 
	{
		int a[][] = m.getMap();//得到邻接矩阵
		for (int i = 0; i < 8; i++)
			if (a[s][i] < 100)
			{
				if (i == e)// 已到达终点，储存路线
				{
					line.add(i);//储存点i
					ArrayList<Integer> cache = new ArrayList<Integer>();//缓存
					for (int j = 0; j < line.size(); j++) 
					{
						cache.add(line.get(j));
					}
					lines.add(cache);//加入路线
					line.remove(line.size() - 1);
				} 
				else// 未到达终点，继续
				{
					line.add(i);
					Search(i, e);
					line.remove(line.size() - 1);
				}
			}
	}
	public void Clear()
	{
		lines.clear();
		line.clear();
	}
}
//主要优化排序显示------------------------------------------------------------------------------
class Core
{
	Map m;//地图数据类
	Find f;//搜寻类
	ArrayList<Double> times;
	ArrayList<Integer> spends;
	ArrayList<Integer> changes;
	
	public Core()
	{
		m=new Map();
		f=new Find();
		times=new ArrayList<Double>();
		spends=new ArrayList<Integer>();
		changes=new ArrayList<Integer>();
		
	}
	//最少时间排序并返回
	public ArrayList<ArrayList<Integer>> LeastTime(int Start,int End)//最少时间
	{
		f.Clear();
		f.line.add(Start);//找出所有路径
		f.Search(Start, End);
		f.line.remove(0);
		//算时间
		for(int i=0;i<f.lines.size();i++)
		{
			double time=0;
			for(int j=0;j<f.lines.get(i).size()-1;j++)
			{
				int s=m.station[f.lines.get(i).get(j)][f.lines.get(i).get(j+1)];
				int v=m.v[m.busLine[f.lines.get(i).get(j)][f.lines.get(i).get(j+1)]];
				double hh=(double)s/v;
				time+=hh;
//				
			}
			times.add(time);//存入时间
		}
		//排序
		for(int i=times.size();i>1;i--)
		{
			for(int j=0;j<(times.size()-1);j++)
			{
				if(times.get(j)>times.get(j+1))
				{
					double temp=times.get(j);
					times.set(j, times.get(j+1));
					times.set(j+1, temp);
					
					ArrayList <Integer>temp2=f.lines.get(j);
					f.lines.set(j, f.lines.get(j+1));
					f.lines.set(j+1, temp2);
					}
				}
		}
		return f.lines;
		
	}
	
	//最少花费排序并返回
	public ArrayList<ArrayList<Integer>> LeastSpend(int Start,int End)//最少金钱
	{
		f.Clear();
		f.line.add(Start);//找出所有路径
		f.Search(Start, End);
		f.line.remove(0);
		
		for(int i=0;i<f.lines.size();i++)
		{
			int spend=0;//第一站的价钱
			spend+=m.money[m.busLine[f.lines.get(i).get(0)][f.lines.get(i).get(1)]];
			for(int j=1;j<f.lines.get(i).size()-1;j++)
			{
				//换乘的价钱
				if(m.busLine[f.lines.get(i).get(j)][f.lines.get(i).get(j+1)]!=m.busLine[f.lines.get(i).get(j-1)][f.lines.get(i).get(j)])
				{
					spend+=m.money[m.busLine[f.lines.get(i).get(j)][f.lines.get(i).get(j+1)]];
				}
				
			}
			spends.add(spend);//存入花费
		}
		//排序
		for(int i=spends.size();i>1;i--)
		{
			for(int j=0;j<(spends.size()-1);j++)
			{
				if(spends.get(j)>spends.get(j+1))
				{
					int temp=spends.get(j);
					spends.set(j, spends.get(j+1));
					spends.set(j+1, temp);
					
					ArrayList <Integer>temp2=f.lines.get(j);
					f.lines.set(j, f.lines.get(j+1));
					f.lines.set(j+1, temp2);
				}
			}
		}
		return f.lines;
	}
	//最少换乘排序并返回
	public ArrayList<ArrayList<Integer>> LeastChange(int Start,int End)//最少换乘
	{
		f.Clear();
		f.line.add(Start);//找出所有路径
		f.Search(Start, End);
		f.line.remove(0);
		
		for(int i=0;i<f.lines.size();i++)
		{
			int change=0;
			for(int j=0;j<f.lines.get(i).size()-2;j++)//速度不一样等于换乘
			{
				if(m.busLine[f.lines.get(i).get(j)][f.lines.get(i).get(j+1)]!=m.busLine[f.lines.get(i).get(j+1)][f.lines.get(i).get(j+2)])
					change++;
			}
			changes.add(change);//存入换乘数
		}
		//排序
		for(int i=changes.size();i>1;i--)
		{
			for(int j=0;j<(changes.size()-1);j++)
			{
				if(changes.get(j)>changes.get(j+1))
				{
					int temp=changes.get(j);
					changes.set(j, changes.get(j+1));
					changes.set(j+1, temp);
					
					ArrayList <Integer>temp2=f.lines.get(j);
					f.lines.set(j, f.lines.get(j+1));
					f.lines.set(j+1, temp2);
				}
			}
		}
		return f.lines;
	}
	
}











