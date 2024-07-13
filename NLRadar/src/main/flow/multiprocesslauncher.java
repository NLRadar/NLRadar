import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static utils.util.getapk;

public class multiprocesslauncher {

    static class ProcessTask implements Callable<Integer> {
        private List<String> command;
    
        public ProcessTask(List<String> command) {
            this.command = command;
        }
    
        @Override
        public Integer call() throws Exception {
            try {
                System.out.println("command begin: " + command);
                ProcessBuilder processBuilder = new ProcessBuilder(command);
                Process process = processBuilder.start();
                
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        System.out.println(line);
                    }
                }

                int exitCode = process.waitFor();
                System.out.println("command end: " + command + ", exitCode: " + exitCode);
                return exitCode;
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
                return -1;
            }
        }
    }


    public static void main(String[] args) {
        String homepath = "/home/dataset/apk/";
        String jarpath = "/home/NLRadar/target/flflowowdroid-icc-1.0-SNAPSHOT.jar";
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
            commands.add(command);
        }
        ExecutorService executorService = Executors.newFixedThreadPool(80);
        List<Future<Integer>> futures = new ArrayList<>();
        for (List<String> command : commands) {
            ProcessTask processTask = new ProcessTask(command);
            executorService.submit(processTask);
        }

        
        executorService.shutdown();
    }
}
