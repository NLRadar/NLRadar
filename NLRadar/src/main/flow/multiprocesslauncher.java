import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Vector;

import static utils.util.*;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.Semaphore;

import java.util.List;

public class multiprocesslauncher {

    private static Semaphore semaphore = new Semaphore(80);

    static class ProcessTask implements Callable<Integer> {
        private List<String> command;
    
        public ProcessTask(List<String> command) {
            this.command = command;
        }
    
        @Override
        public Integer call() throws Exception {
            try {
                // execute command
                System.out.println("command begin: " + command);
                ProcessBuilder processBuilder = new ProcessBuilder(command);
                processBuilder.redirectErrorStream(true);

                Process process = processBuilder.start();
                
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        //System.out.println(line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                int exitCode = process.waitFor();
                System.out.println("command over: " + command + ", exitCode: " + exitCode);
                semaphore.release();
                return exitCode;
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
                semaphore.release();
                return -1; // error code
            } finally {
                semaphore.release();
            }
        }
    }


    public static void main(String[] args) {
        String homepath = "apkpath/";
        String jarpath = "NLRadar.jar";
        Vector<String> app_name_vec = new Vector<String>();
        app_name_vec = getapk(homepath);
        List<List<String>> commands = new ArrayList<>();
        for(int i=0;i<app_name_vec.size();i++){
            String apk = homepath+app_name_vec.get(i);
            List<String> command = new ArrayList<>();
            command.add("java");
            command.add("-jar");
            command.add(jarpath);
            command.add(apk);
            command.add("/android/sdk/platforms");
            commands.add(command);
        }
        ExecutorService executorService = Executors.newFixedThreadPool(80);
        System.out.println(commands.size());
        for (List<String> command : commands) {
            try{
                semaphore.acquire();
                ProcessTask processTask = new ProcessTask(command);
                executorService.submit(processTask);
            }catch (Exception e) {
                e.printStackTrace();
                System.out.println(e);
            }
            
        } 
        executorService.shutdown();
    }
}
