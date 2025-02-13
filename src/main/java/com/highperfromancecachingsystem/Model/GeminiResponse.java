package com.highperfromancecachingsystem.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

public class GeminiResponse {

    @JsonProperty("candidates")
    public ArrayList<Candidate> candidates;

    public ArrayList<Candidate> getCandidates() {
        return candidates;
    }

    public void setCandidates(ArrayList<Candidate> candidates) {
        this.candidates = candidates;
    }


    public static class Candidate {
        @JsonProperty("content")
        public content content;

        public GeminiResponse.content getContent() {
            return content;
        }

        public void setContent(GeminiResponse.content content) {
            this.content = content;
        }
    }
    public static class content{
        @JsonProperty("parts")
        public ArrayList<parts> part;

        public ArrayList<parts> getPart() {
            return part;
        }

        public void setPart(ArrayList<parts> part) {
            this.part = part;
        }
    }
    public static class parts{
        @JsonProperty("text")
        public String text;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }
}
