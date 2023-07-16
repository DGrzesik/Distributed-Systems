package subscribers;

public class SubscriberStats {
    private Integer failedAttempts;
    private Boolean status;

    public SubscriberStats(Integer failedAttempts, Boolean status) {
        this.failedAttempts = failedAttempts;
        this.status = status;
    }
    public void fail(){
        failedAttempts+=1;
    }
    public void setStatus(Boolean status){
        this.status=status;
    }

    public Integer getFailedAttempts() {
        return failedAttempts;
    }

    public Boolean getStatus() {
        return status;
    }
}
