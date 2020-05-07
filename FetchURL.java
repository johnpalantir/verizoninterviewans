import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.Scanner;

public class FetchURL {

    public static void main(String[] args) throws MalformedURLException {

        System.out.println("Start Running Fetch URL Java program");

        Scanner in = new Scanner(System.in);
        System.out.println("Pls enter: URL Address ");
        String url = in.nextLine();

        System.out.println("pls enter: No of Parallel Threads that will do the fetch  ");
        int noOfParallelThreads = in.nextInt();

        System.out.println("Pls enter: Number of times each thread will fetch ");
        int noOfTimeThreadFetch = in.nextInt();

        int totalCall = noOfParallelThreads * noOfTimeThreadFetch;
        ExecutorService executor = Executors.newFixedThreadPool(noOfParallelThreads);
        URL siteURL = new URL(url);
        int code = 200;
        
        for (int i = 0; i < totalCall; i++) {

            int count = i;
            executor.submit(() -> {
                long startTime = System.currentTimeMillis();

               
                try {

                    HttpURLConnection connection = (HttpURLConnection) siteURL.openConnection();
                    connection.setRequestMethod("GET");
                    connection.connect();

                    if (connection.getResponseCode() == code) {
                        long time = System.currentTimeMillis() - startTime;

                        System.out.println("request/" + count + Thread.currentThread().getName().replace("pool-1-thread", "Thread") + ",HTTP " + code + ", " + time + " milliseconds");
                    }

                } catch (IOException e) {
                    System.out.println("Not able to process request " + count);
                    e.printStackTrace();
                }
            });

        }

        // wait until all threads will be finished
        executor.shutdown();
        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}