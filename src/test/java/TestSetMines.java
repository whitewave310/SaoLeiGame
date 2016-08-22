import java.util.Random;

/**
 * Created by whitewave on 2015/9/2.
 */
public class TestSetMines {
    public static void main(String[] args) {
        int[] mineCount;
        mineCount=new int[10];
        Random random = new Random();
        for (int n=0;n<10;n++){
            int temp=random.nextInt(81);
            for (int m=0;m<mineCount.length;m++){
                if (mineCount[m]==temp){
                    temp=random.nextInt(81);
                    m=0;
                }
            }
            mineCount[n]=temp;
            int x=mineCount[n]/10;
            int y=mineCount[n]%10;
            System.out.print(x);
            System.out.println(y);
        }


    }
}
