import java.util.Arrays;

public class Main {
    public static void main(String[] args) {

        System.out.println(Arrays.equals(arrayFuller(),arrayFullerFaster()));

    }

    static public float[] arrayFuller() {
        final int SIZE = 10000000;
        final int HALF = SIZE / 2;
        float[] arr = new float[SIZE];

        for (int i = 0; i < arr.length; i++) {
            arr[i]++;
        }


        long a = System.currentTimeMillis();
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (float)(arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
        System.out.println(System.currentTimeMillis()-a);
        System.out.println("Длинна массива: " +arr.length);
        return arr;
    }

    static public float[] arrayFullerFaster () {
        final int SIZE = 10000000;
        final int HALF = SIZE / 2;
        float[] arr = new float[SIZE];
        float[] arr1=new float[HALF];
        float[] arr2=new float[HALF];

        long a = System.currentTimeMillis();
        //первая половина
        Thread FirstHalf = new Thread(new Runnable() {
            @Override
            public void run() {

                System.arraycopy(arr,0,arr1,0,HALF);

                for(int i = 0; i<arr1.length;i++) {
                    arr1[i]++;
                }


                for (int i = 0; i < arr.length; i++) {
                    arr[i] = (float)(arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
                }



            }
              });
        FirstHalf.start();
        
         //вторая половина
        Thread SecondHalf = new Thread(new Runnable() {

            @Override
            public void run() {

                System.arraycopy(arr, HALF, arr2, 0, HALF);

                for(int i = 0; i<arr1.length;i++) {
                    arr1[i]++;
                }

                long a = System.currentTimeMillis();
                for (int i = 0; i < arr.length; i++) {
                    arr[i] = (float)(arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
                }
               // System.out.println(System.currentTimeMillis()-a);


            }
        });
        SecondHalf.start();

        System.arraycopy(arr1, 0, arr, 0, HALF);
        System.arraycopy(arr2, 0, arr, HALF, HALF);

        System.out.println(System.currentTimeMillis()-a);
        System.out.println("Длинна массива: "+arr.length);
        return arr;

    }

}
