package matrix;
import java.util.Scanner;
public class Mat {
	public static int n=0;
	public static void main(String[] args) 
	{
		Scanner input = new Scanner(System.in);
		double data_test[][]={{1.4,4.51,1},{3,4,5.7},{2,3.25,1}}; 
		System.out.println("For example:");
		System.out.println("输入的矩阵为");
		System.out.println("1.4   4.51   1");
		System.out.println("3   4   5.7");
		System.out.println("2   3.25   1");
		n=3;
		getN(data_test);
		System.out.println("请输入待求矩阵的阶数");
		//n=3;
		n=input.nextInt();
		double[][] data=new double[n][n];
		System.out.println("请输入矩阵");
		for(int i=0;i<n;i++)
		{
			for(int j=0;j<n;j++)
				data[i][j]=input.nextDouble();
		}
		if(n==1)
		{
			//Print(frac(data[0][0]));
			System.out.println("逆矩阵为:");
			Print(div(frac(1.0),frac(data[0][0])));
			return;
		}
		double k=data[0][0]*data[1][1]-data[0][1]*data[1][0];
		if(n>2)
			getN(data);
		else
		{
			System.out.println("逆矩阵为:");
			Print(div(frac(data[1][1]),frac(k)));
			System.out.print("   ");
			Print(div(frac(-data[0][1]),frac(k)));
			System.out.println();
			Print(div(frac(-data[1][0]),frac(k)));
			System.out.print("   ");
			Print(div(frac(data[0][0]),frac(k)));
			//System.out.println(data[1][1]/k+"   "+(0-data[0][1]/k));
			//System.out.println(0-data[1][0]/k+"   "+(data[0][0]/k));
		}
	}
	public static int[][][] getA_T(int[][][] A) 
	{
		int h = n;
		int v = n;
		int[][][] A_T = new int[v][h][2];
		for (int i = 0; i < v; i++) 
		{
			for (int j = 0; j < h; j++) 
			{
				//A_T[j][i] = A[i][j];
				A_T[j][i][0] = A[i][j][0];
				A_T[j][i][1] = A[i][j][1];
			}
		}
		return A_T;
	}
	public static int[][][] getN(double[][] data) 
	{
		//System.out.println(data[0][1]);
		int[] A = getHL(data);
		//System.out.println(A[0]+"/"+A[1]);
		int[][][] newData = new int[data.length][data.length][2];
		for (int i = 0; i < data.length; i++) 
		{
			for (int j = 0; j < data.length; j++) 
			{
				int[] num = {0,1};
				if ((i + j) % 2 == 0) 
				{
					num[0] = getHL(getDY(data, i + 1, j + 1))[0];
					num[1] = getHL(getDY(data, i + 1, j + 1))[1];
				} 
				else 
				{
					//num = -getHL(getDY(data, i + 1, j + 1));
					num[0] = -getHL(getDY(data, i + 1, j + 1))[0];
					num[1] = getHL(getDY(data, i + 1, j + 1))[1];
				}
				newData[i][j][0] = num[0]*A[1];
				newData[i][j][1] = num[1]*A[0];
			}
		}
		System.out.println("逆矩阵为:");
		newData = getA_T(newData);
		for (int i = 0; i < data.length; i++) 
		{
			for (int j = 0; j < data.length; j++) 
			{
				if(newData[i][j][0]==-0.0||newData[i][j][0]==-0.0)
				{
					System.out.print(0);
					newData[i][j][0]=0;
				}
				if(newData[i][j][0]<0&&newData[i][j][1]<0)
				{
					newData[i][j][0]=-newData[i][j][0];
					newData[i][j][1]=-newData[i][j][1];
				}
				//System.out.print(/*"newData[" + i + "][" + j + "]= "
				//		+ */newData[i][j] + "   ");
				int Gcd=gcd(newData[i][j][0],newData[i][j][1]);
				System.out.print(newData[i][j][0]/Gcd+"/"+newData[i][j][1]/Gcd + "   ");
			}
			System.out.println();
		}

		return newData;
	}
	public static int[] getHL(double[][] data) 
	{
		if (data.length == 2) {  
            return minus(mult(frac(data[0][0]),frac(data[1][1])),mult(frac(data[0][1]),frac(data[1][0])));//data[0][0] * data[1][1] - data[0][1] * data[1][0];  
        }  
		int[] total={0,1};
		int[] temp={0,1};
		int num = data.length;
		//System.out.println(num);
		int[][] nums = new int[num][2];
		for (int i = 0; i < num; i++) 
		{
			if (i % 2 == 0) 
			{
				nums[i][0] = mult(frac(data[0][i]),getHL(getDY(data, 1, i + 1)))[0];
				nums[i][1] = mult(frac(data[0][i]),getHL(getDY(data, 1, i + 1)))[1];
			} 
			else 
			{
				nums[i][0] = -mult(frac(data[0][i]),getHL(getDY(data, 1, i + 1)))[0];
				nums[i][1] = mult(frac(data[0][i]),getHL(getDY(data, 1, i + 1)))[1];
			}
			//System.out.println(nums[i][0]+"/"+nums[i][1]);
			//System.out.println(getHL(getDY(data, 1, i + 1))[0]+"/"+getHL(getDY(data, 1, i + 1))[1]);
			//System.out.println(getDY(data, 1, i + 1)[0][0]);
			//System.out.println(frac(data[0][i])[0]+"/"+frac(data[0][i])[1]);
			//System.out.println(mult(frac(data[0][i]),frac(0.0))[0]+" "+mult(frac(data[0][i]),frac(0.0))[1]);
		}
		for (int i = 0; i < num; i++) 
		{
			//total += nums[i];
			temp[0]=plus(total,nums[i])[0];
			temp[1]=plus(total,nums[i])[1];
			total[0]=temp[0];
			total[1]=temp[1];
		}
		//System.out.println("total=" + total);
		//System.out.println(total[0]+" "+total[1]);
		return total;
	}
	public static double[][] getDY(double[][] data, int h, int v) 
	{
		int H = data.length;
		int V = data[0].length;
		//System.out.println(H+" "+V+" "+data[0][0]);
		double[][] newData = new double[H - 1][V - 1];
		for (int i = 0; i < newData.length; i++) 
		{
			if (i < h - 1) 
			{
				for (int j = 0; j < newData[i].length; j++) 
				{
					if (j < v - 1) 
					{
						newData[i][j] = data[i][j];
					} 
					else 
					{
						newData[i][j] = data[i][j + 1];
					}
				}
			} else 
			{
				for (int j = 0; j < newData[i].length; j++) 
				{
					if (j < v - 1) 
					{
						newData[i][j] = data[i + 1][j];
					} 
					else 
					{
						newData[i][j] = data[i + 1][j + 1];
					}
				}

			}
		}
		//if(H-1>0) System.out.println(newData[0][0]);
		return newData;
	}
	public static int gcd(int a,int b)
	{
		if(a<0)
		{
			a=-a;
		}
		if(b<0)
		{
			b=-b;
		}
		if(Math.abs(a)<0.0000001)
		{
			return 1;
		}
		if(a<b)
		{
			int t=b;
			b=a;
			a=t;
		}
		while(a%b>0.0000001)
		{
			int temp=a%b;
			a=b;
			b=temp;
			//System.out.println(temp);
		}
		return b;
	}
	public static int[] frac(double s)
	{
		int[] num = new int[2];
		double a=s;
		int g=0,t=1;
		boolean pl=false;
		if(s<0)
		{
			pl=true;
			s=-s;
			a=-a;
		}
		else
			a=a+0.000000000000001;
		while(Math.abs((int)a-a)>0.0000001)
		{
			a=a*10;
			t*=10;
			//System.out.println(a);
		}
		num[0]=(int)a;
		num[1]=t;
		g=gcd(num[0],num[1]);
		//System.out.println(g+" "+num[0]+" "+num[1]);
		num[0]/=(int)g;
		num[1]/=(int)g;
		if(pl)
		{
			num[0]=-num[0];
		}
		//System.out.println(num[1]);
		return num;
	}
	public static int[] plus(int a[],int b[])
	{
		int[] result=new int[2];
		result[0]=a[0]*b[1]+a[1]*b[0];
		result[1]=a[1]*b[1];
		int g=gcd(result[0],result[1]);
		result[0]/=(int)g;
		result[1]/=(int)g;
		return result;
	}
	public static int[] minus(int a[],int b[])
	{
		int[] result=new int[2];
		result[0]=a[0]*b[1]-a[1]*b[0];
		result[1]=a[1]*b[1];
		int g=gcd(result[0],result[1]);
		result[0]/=(int)g;
		result[1]/=(int)g;
		return result;
	}
	public static int[] mult(int a[],int b[])
	{
		int[] result=new int[2];
		result[0]=a[0]*b[0];
		result[1]=a[1]*b[1];
		int g=gcd(result[0],result[1]);
		result[0]/=(int)g;
		result[1]/=(int)g;
		return result;
	}
	public static int[] div(int a[],int b[])
	{
		int[] result=new int[2];
		result[0]=a[0]*b[1];
		result[1]=a[1]*b[0];
		int g=gcd(result[0],result[1]);
		result[0]/=(int)g;
		result[1]/=(int)g;
		return result;
	}
	public static void Print(int[] a)
	{
		if(a[1]==1||a[1]==-1)
		{
			if(a[1]==1)
				System.out.print(a[0]);
			else
				System.out.print(-a[0]);
		}
		else
		{
			if(a[1]>0)
				System.out.print(a[0]+"/"+a[1]);
			else
				System.out.print(-a[0]+"/"+(-a[1]));
		}
	}
}