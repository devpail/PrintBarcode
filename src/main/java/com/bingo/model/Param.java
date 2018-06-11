package com.bingo.model;

public class Param {
    //ID
    private String id;
    //上边距
    private int paddingTop;
    private int paddingBottom;
    private int paddingLeft;
    private int paddingRight;
    private int columns;
    private int rows;
    private int spaceColumn;
    private int spaceRow;
    //初始编号
    private int startNumber;
    //编号长度
    private int length;
    //打印几张
    private int pages;

    private int codeWidth;
    private int codeHeight;

    private String createTime;

    public Param(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getPaddingTop() {
        return paddingTop;
    }

    public void setPaddingTop(int paddingTop) {
        this.paddingTop = paddingTop;
    }

    public int getPaddingBottom() {
        return paddingBottom;
    }

    public void setPaddingBottom(int paddingBottom) {
        this.paddingBottom = paddingBottom;
    }

    public int getPaddingLeft() {
        return paddingLeft;
    }

    public void setPaddingLeft(int paddingLeft) {
        this.paddingLeft = paddingLeft;
    }

    public int getPaddingRight() {
        return paddingRight;
    }

    public void setPaddingRight(int paddingRight) {
        this.paddingRight = paddingRight;
    }

    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getSpaceColumn() {
        return spaceColumn;
    }

    public void setSpaceColumn(int spaceColumn) {
        this.spaceColumn = spaceColumn;
    }

    public int getSpaceRow() {
        return spaceRow;
    }

    public void setSpaceRow(int spaceRow) {
        this.spaceRow = spaceRow;
    }

    public int getStartNumber() {
        return startNumber;
    }

    public void setStartNumber(int startNumber) {
        this.startNumber = startNumber;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getCodeWidth() {
        return codeWidth;
    }

    public void setCodeWidth(int codeWidth) {
        this.codeWidth = codeWidth;
    }

    public int getCodeHeight() {
        return codeHeight;
    }

    public void setCodeHeight(int codeHeight) {
        this.codeHeight = codeHeight;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
