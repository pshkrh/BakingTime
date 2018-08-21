package com.pshkrh.bakingtime.Model;

public class Step {
    public int stepId;
    public String shortDesc, desc, videoUrl, thumbnailUrl;

    public Step(int stepId, String shortDesc, String desc, String videoUrl, String thumbnailUrl) {
        this.stepId = stepId;
        this.shortDesc = shortDesc;
        this.desc = desc;
        this.videoUrl = videoUrl;
        this.thumbnailUrl = thumbnailUrl;
    }

    public int getStepId() {
        return stepId;
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public String getDesc() {
        return desc;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }
}
