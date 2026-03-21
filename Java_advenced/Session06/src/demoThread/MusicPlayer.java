package demoThread;

public class MusicPlayer extends Thread{
    private String musicName;

    public MusicPlayer() {
    }

    public MusicPlayer(String musicName) {
        this.musicName = musicName;
    }

    public String getMusicName() {
        return musicName;
    }

    public void setMusicName(String musicName) {
        this.musicName = musicName;
    }

    @Override
    public void run() {
        System.out.println("[Music player] Đang chuẩn bị bài hát.");
        for (int i = 0; i < 5; i++) {
            System.out.println("[Music Player]" + musicName + "Đang phát nhạc.");
            try{
                Thread.sleep(1000);
            }catch(InterruptedException IE){

            }
        }
        System.out.println("[Music player] đã phát xong bài nhạc.");
    }
}
