package demoThread;

public class FileDowload implements Runnable{
    private String fileName;

    public FileDowload(String fileName) {
        this.fileName = fileName;
    }

    public FileDowload() {
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void run() {
        System.out.println("[Dowload] đang chuẩn bị tải bài hát");
        for (int i = 20; i <= 100; i+=20) {
            System.out.println("Đang tải bài hát...");
            try{
                Thread.sleep(1000);
            }catch(InterruptedException IE){
                System.out.println(IE.getMessage());
            }
        }
    }
}
