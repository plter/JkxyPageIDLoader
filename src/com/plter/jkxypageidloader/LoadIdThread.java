package com.plter.jkxypageidloader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Created by plter on 4/8/15.
 */
public class LoadIdThread {

    private int startId,endId;
    private boolean running = false;
    private IFoundTitleListener foundTitleListener = null;


    public LoadIdThread(int startId,int endId,IFoundTitleListener foundTitleListener){
        this.startId = startId;
        this.endId = endId;
        setFoundTitleListener(foundTitleListener);
    }


    public void start(){

        running = true;

        new Thread(){
            @Override
            public void run() {
                super.run();

                for (int id = endId;id>=startId&&running;id--){
                    String currentUrl = String.format("%s%d.html",Config.BASE_URL,id);
                    try {
                        InputStream in = new URL(currentUrl).openStream();

                        StringBuffer content = new StringBuffer();
                        BufferedReader br = new BufferedReader(new InputStreamReader(in,"utf-8"));
                        String line = null;
                        while ((line=br.readLine())!=null){
                            content.append(line);
                        }
                        in.close();

                        findTitleInPageContent(content.toString(),id,currentUrl);
                    } catch (IOException e) {
                    }
                }

                running = false;
            }
        }.start();
    }

    private void findTitleInPageContent(String pageContent,int currentId,String url){
        int titleStart = pageContent.indexOf("<h1 ");
        titleStart = pageContent.indexOf(">",titleStart)+1;
        if (titleStart>-1){
            int titleEnd = pageContent.indexOf("</h1>",titleStart);

            String title = pageContent.substring(titleStart,titleEnd);
            title = title.trim();

            if (getFoundTitleListener()!=null){
                getFoundTitleListener().foundPage(title, currentId ,url);
            }
        }
    }

    public boolean isRunning() {
        return running;
    }

    public void stop() {
        running = false;
    }

    public void setFoundTitleListener(IFoundTitleListener foundTitleListener) {
        this.foundTitleListener = foundTitleListener;
    }

    public IFoundTitleListener getFoundTitleListener() {
        return foundTitleListener;
    }

    public static interface IFoundTitleListener{
        void foundPage(String title, int id,String url);
    }
}

