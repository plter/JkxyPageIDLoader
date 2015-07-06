package com.plter.jkxypageidloader;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by plter on 7/6/15.
 */
public class CellData {

    public CellData(String title, int pageId, String url){
        setTitle(title);
        setPageId(pageId);
        setUrl(url);
    }

    private String title="",url=null;
    private int pageId=0;

    public void setPageId(int pageId) {
        this.pageId = pageId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPageId() {
        return pageId;
    }

    public String getTitle() {
        return title;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void openPage(){
        try {
            Desktop.getDesktop().browse(new URI(getUrl()));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
