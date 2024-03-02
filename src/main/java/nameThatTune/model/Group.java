package nameThatTune.model;

public class Group {
	Player first,second,third,fourth;
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
}
