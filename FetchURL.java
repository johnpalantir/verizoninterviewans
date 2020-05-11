import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
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

        System.out.println("Pls enter: Number of Threads");
        int numOfThreads = in.nextInt();

        System.out.println("Pls enter: Number of Request ");
        int numOfRequests = in.nextInt();

        int threadPoolSize = 3;

        ExecutorService executor = Executors.newFixedThreadPool(threadPoolSize);
        URL siteURL = new URL(url);
        int code = 200;

        List<String> result = new LinkedList<>();

        for (int i = 0; i < numOfThreads; i++) {

            for (int j = 0; j < numOfRequests; j++) {

                int count = i;
                executor.submit(() -> {
                    long startTime = System.currentTimeMillis();
                    try {

                        HttpURLConnection connection = (HttpURLConnection) siteURL.openConnection();
                        connection.setRequestMethod("GET");
                        connection.connect();

                        if (connection.getResponseCode() == code) {
                            long time = System.currentTimeMillis() - startTime;

                            result.add("request/" + count + Thread.currentThread().getName().replace("pool-1-thread", "Thread") + ",HTTP " + code + ", " + time + " milliseconds");
                        }

                    } catch (IOException e) {
                        System.out.println("Not able to process request " + count);
                        e.printStackTrace();
                    }
                });

            }

        }

        // wait until all threads will be finished
        executor.shutdown();
        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.HOURS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("============================");

        printFetchDetails(url, numOfThreads, numOfRequests);
        System.out.println("Request call Results");
        result.forEach(System.out::println);
        System.out.println("============================");
    }

    private static void printFetchDetails(String url, long numOfThreads, long numOfRequests){
        StringBuilder sb = new StringBuilder();
        sb.append("Fetch Result Details:\n");
        sb.append("URL: ").append(url+"\n");
        sb.append("Total Threads: ").append(numOfThreads+"\n");
        sb.append("Total Requests: ").append(numOfRequests+"\n");
        System.out.println("\n"+ sb.toString());
    }
}

