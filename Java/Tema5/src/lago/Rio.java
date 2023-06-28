package lago;

public class Rio extends Thread {
	private Lago lago;
	private int id;
	
	public Rio(int id, Lago lago) {
		this.id = id;
		this.lago = lago;
	}
	
	public void run() {
		for(int i= 0; i < 1000; i++){
			if(id == 0)
				lago.incRio0();
			else
				lago.incRio1();
		}
	}
}
