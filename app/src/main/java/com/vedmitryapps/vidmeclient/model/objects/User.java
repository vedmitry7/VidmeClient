
package com.vedmitryapps.vidmeclient.model.objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("full_url")
    @Expose
    private String fullUrl;
    @SerializedName("avatar")
    @Expose
    private String avatar;
    @SerializedName("avatar_url")
    @Expose
    private String avatarUrl;
    @SerializedName("avatar_ai")
    @Expose
    private String avatarAi;
    @SerializedName("cover")
    @Expose
    private String cover;
    @SerializedName("cover_url")
    @Expose
    private String coverUrl;
    @SerializedName("cover_ai")
    @Expose
    private String coverAi;
    @SerializedName("displayname")
    @Expose
    private String displayname;
    @SerializedName("follower_count")
    @Expose
    private Integer followerCount;
    @SerializedName("likes_count")
    @Expose
    private String likesCount;
    @SerializedName("video_count")
    @Expose
    private Integer videoCount;
    @SerializedName("video_views")
    @Expose
    private String videoViews;
    @SerializedName("videos_scores")
    @Expose
    private Integer videosScores;
    @SerializedName("comments_scores")
    @Expose
    private Integer commentsScores;
    @SerializedName("bio")
    @Expose
    private String bio;
    @SerializedName("enabled")
    @Expose
    private String enabled;
    @SerializedName("ga_id")
    @Expose
    private Object gaId;
    @SerializedName("is_following")
    @Expose
    private Boolean isFollowing;
    @SerializedName("is_followed_by")
    @Expose
    private Boolean isFollowedBy;
    @SerializedName("receive_notifications_following")
    @Expose
    private Boolean receiveNotificationsFollowing;
    @SerializedName("receive_notifications_followed")
    @Expose
    private Boolean receiveNotificationsFollowed;
    @SerializedName("is_subscribed")
    @Expose
    private Boolean isSubscribed;
    @SerializedName("is_subscribed_by")
    @Expose
    private Boolean isSubscribedBy;
    @SerializedName("vip")
    @Expose
    private Boolean vip;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullUrl() {
        return fullUrl;
    }

    public void setFullUrl(String fullUrl) {
        this.fullUrl = fullUrl;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getAvatarAi() {
        return avatarAi;
    }

    public void setAvatarAi(String avatarAi) {
        this.avatarAi = avatarAi;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public String getCoverAi() {
        return coverAi;
    }

    public void setCoverAi(String coverAi) {
        this.coverAi = coverAi;
    }

    public String getDisplayname() {
        return displayname;
    }

    public void setDisplayname(String displayname) {
        this.displayname = displayname;
    }

    public Integer getFollowerCount() {
        return followerCount;
    }

    public void setFollowerCount(Integer followerCount) {
        this.followerCount = followerCount;
    }

    public String getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(String likesCount) {
        this.likesCount = likesCount;
    }

    public Integer getVideoCount() {
        return videoCount;
    }

    public void setVideoCount(Integer videoCount) {
        this.videoCount = videoCount;
    }

    public String getVideoViews() {
        return videoViews;
    }

    public void setVideoViews(String videoViews) {
        this.videoViews = videoViews;
    }

    public Integer getVideosScores() {
        return videosScores;
    }

    public void setVideosScores(Integer videosScores) {
        this.videosScores = videosScores;
    }

    public Integer getCommentsScores() {
        return commentsScores;
    }

    public void setCommentsScores(Integer commentsScores) {
        this.commentsScores = commentsScores;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getEnabled() {
        return enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }

    public Object getGaId() {
        return gaId;
    }

    public void setGaId(Object gaId) {
        this.gaId = gaId;
    }

    public Boolean getIsFollowing() {
        return isFollowing;
    }

    public void setIsFollowing(Boolean isFollowing) {
        this.isFollowing = isFollowing;
    }

    public Boolean getIsFollowedBy() {
        return isFollowedBy;
    }

    public void setIsFollowedBy(Boolean isFollowedBy) {
        this.isFollowedBy = isFollowedBy;
    }

    public Boolean getReceiveNotificationsFollowing() {
        return receiveNotificationsFollowing;
    }

    public void setReceiveNotificationsFollowing(Boolean receiveNotificationsFollowing) {
        this.receiveNotificationsFollowing = receiveNotificationsFollowing;
    }

    public Boolean getReceiveNotificationsFollowed() {
        return receiveNotificationsFollowed;
    }

    public void setReceiveNotificationsFollowed(Boolean receiveNotificationsFollowed) {
        this.receiveNotificationsFollowed = receiveNotificationsFollowed;
    }

    public Boolean getIsSubscribed() {
        return isSubscribed;
    }

    public void setIsSubscribed(Boolean isSubscribed) {
        this.isSubscribed = isSubscribed;
    }

    public Boolean getIsSubscribedBy() {
        return isSubscribedBy;
    }

    public void setIsSubscribedBy(Boolean isSubscribedBy) {
        this.isSubscribedBy = isSubscribedBy;
    }

    public Boolean getVip() {
        return vip;
    }

    public void setVip(Boolean vip) {
        this.vip = vip;
    }

}
