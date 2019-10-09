package com.example.information.Bean;

import java.util.List;

public class Bean_weekly {
    private List<subjectsdata> subjects;
    private String title;

    public List<subjectsdata> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<subjectsdata> subjects) {
        this.subjects = subjects;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public static class subjectsdata{
       private Bean_video.Subjectdata subject;
       private int rank;
       private int delta;

        public Bean_video.Subjectdata getSubject() {
            return subject;
        }

        public void setSubject(Bean_video.Subjectdata subject) {
            this.subject = subject;
        }

        public int getRank() {
            return rank;
        }

        public void setRank(int rank) {
            this.rank = rank;
        }

        public int getDelta() {
            return delta;
        }

        public void setDelta(int delta) {
            this.delta = delta;
        }
    }

}
