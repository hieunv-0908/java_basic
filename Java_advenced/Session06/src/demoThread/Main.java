package demoThread;

public class Main {
    public static void main(String[] args) {
        // luồng nghe nhạc (kế thừa Thread)
        MusicPlayer musicPlayer = new MusicPlayer("Lạc trôi.");
        musicPlayer.start();

        // luồng tải bài nhạc (triển khai Runnable)
        FileDowload fileDowload = new FileDowload("Người tình mùa đông.");
        fileDowload.run();
    }
}