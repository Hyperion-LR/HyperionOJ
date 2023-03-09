package com.hyperionoj.judge.constants;

/**
 * @author Hyperion
 * @date 2021/12/9
 */
public enum Verdict {
    AC("AC", "Accept"),
    WA("WA", "Wrong answer"),
    TLE("TLE", "Time limit exceeded"),
    MLE("MLE", "Memory limit exceeded"),
    CE("CE", "Compilation error"),
    RE("RE", "Runtime Error"),
    PE("PE", "Presentation Error"),
    SE("SE", "System Error");

    private String verdict;

    private String msg;

    Verdict(String verdict, String msg) {
        this.verdict = verdict;
        this.msg = msg;
    }

    public String getVerdict() {
        return verdict;
    }

    public String getMsg() {
        return msg;
    }

}
