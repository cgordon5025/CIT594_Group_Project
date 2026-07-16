package edu.upenn.cit5940.common.dto;

/**
 * @author nanzheng
 */

public class TopTopicInfo {
    private String topicName;
    private int mentionCount;

    public TopTopicInfo(String topicName, int mentionCount) {
        this.topicName = topicName;
        this.mentionCount = mentionCount;
    }

    public String getTopicName() { return topicName; }
    public int getMentionCount() { return mentionCount; }
}
