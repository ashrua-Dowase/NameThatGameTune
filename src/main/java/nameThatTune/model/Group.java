package nameThatTune.model;

public class Group {
	Player first,second,third,fourth;
	boolean allSelected = false;
	public Group(Player one, Player two, Player three, Player four){
		first = one;
		second = two;
		third = three;
		fourth = four;
	}
	public Player getFirst() {
		return first;
	}
	public void setFirst(Player first) {
		this.first = first;
	}
	public Player getSecond() {
		return second;
	}
	public void setSecond(Player second) {
		this.second = second;
	}
	public Player getThird() {
		return third;
	}
	public void setThird(Player third) {
		this.third = third;
	}
	public Player getFourth() {
		return fourth;
	}
	public void setFourth(Player fourth) {
		this.fourth = fourth;
	}
	public synchronized boolean getAllSelected() {
		return allSelected;
	}
	public synchronized void setAllSelected(Boolean allselected) {
		this.allSelected = allselected;
	}
	public synchronized Player getPlayer(int i) {
		if (first.getPosition() == i) {
			return first;
		}
		else if(second.getPosition() == i){
			return second;
		}
		else if (third.getPosition() == i) {
			return third;
		}
		else if (fourth.getPosition() == i) {
			return fourth;
		}
	return first;
	}
}
