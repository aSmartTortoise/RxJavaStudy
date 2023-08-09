package com.wyj.rxjava.model;

import java.util.List;

public class ArticleList {
    private int curPage;
    private int offset;
    private int size;
    private int total;
    private int pageCount;
    private List<ArticleInfo> datas;

    public int getCurPage() {
        return curPage;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public List<ArticleInfo> getDatas() {
        return datas;
    }

    public void setDatas(List<ArticleInfo> datas) {
        this.datas = datas;
    }
}
