package yong.board.vo;


public class BoardVO {

    private int bno;
    private String subject;
    private String content;
    private String writer;
    private String regDate;
    private int idx;
    private String auth;
    private String recparam;
    private String id;
    private int reCnt;
    private String luCount;


    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }


    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

    public int getBno() {
        return bno;
    }

    public void setBno(int bno) {
        this.bno = bno;
    }


    public int getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }



    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public String getRecparam() {
        return recparam;
    }

    public void setRecparam(String recparam) {
        this.recparam = recparam;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getReCnt() {
        return reCnt;
    }

    public void setReCnt(int reCnt) {
        this.reCnt = reCnt;
    }

    public String getLuCount() {
        return luCount;
    }

    public void setLuCount(String luCount) {
        this.luCount = luCount;
    }
}
