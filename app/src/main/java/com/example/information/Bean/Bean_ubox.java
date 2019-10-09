package com.example.information.Bean;

import org.json.JSONObject;

import java.util.List;

public class Bean_ubox {

    private  List<subjectsdatauxbox> subjects;

    public List<subjectsdatauxbox> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<subjectsdatauxbox> subjects) {
        this.subjects = subjects;
    }

    public static class subjectsdatauxbox{
       private Bean_video.Subjectdata subject;

        public Bean_video.Subjectdata getSubject() {
            return subject;
        }

        public void setSubject(Bean_video.Subjectdata subject) {
            this.subject = subject;
        }
    }

}
