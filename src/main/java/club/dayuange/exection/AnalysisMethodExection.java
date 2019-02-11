package club.dayuange.exection;

public class AnalysisMethodExection extends Exception{
    private String message;

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public AnalysisMethodExection(String message) {
        this.message=message;
    }


}